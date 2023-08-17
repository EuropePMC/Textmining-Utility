package uk.ac.ebi.literature.textminingapi.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MLQueueSenderService {

	private final RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.waitForPublisherConfirm}")
	private boolean waitForPublisherConfirm;
	
	@Value("${rabbitmq.retry-count}")
	private int retryCount;

	private static Logger logger = LoggerFactory.getLogger(MLQueueSenderService.class);

	public MLQueueSenderService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * It sends a message to a specific exchange/queue
	 * @param <T> type of the message
	 * @param queueName  name of the queue
	 * @param message message of type T to be sent to the queue
	 * @param publishExchange name of the exchange
	 * @return true if the message is sent successfully, otherwise false
	 */
	public <T> Boolean sendMessageToQueue(String queueName, Serializable message, String publishExchange) {
		logger.info("Sending to queue: " + queueName + ", message " + message.toString());

		return this.rabbitTemplate.invoke(t -> {
			try {
				t.convertAndSend(publishExchange, queueName, message);
				if (this.waitForPublisherConfirm) {
					t.waitForConfirmsOrDie(10_000);
				}
			} catch (Exception e) {
				logger.error("Error in sending message " + message.toString() + " to the queue " + queueName, e);
				return false;
			}
			logger.info("Suuccessfully sent message " + message.toString() + " to queue " + queueName);
			return true;
		});
	}

	/**
	 * It checks if a specific message has been exhausted the maximum number of retry processing configured (rabbitmq.retry-count config parameter)
	 * @param message message to be checked
	 * @return true if we have already try to process the message the maximum number of times, otherwise false
	 */
	public final boolean hasExceededRetryCount(Message message) {
		List<Map<String, ?>> xDeathHeader = message.getMessageProperties().getXDeathHeader();
		if (xDeathHeader != null) {
			long count = -1;
			if (!xDeathHeader.isEmpty())
				if (xDeathHeader.get(0).containsKey("count")) {
					count = (Long) xDeathHeader.get(0).get("count");
				} else {
					count = xDeathHeader.stream().filter(dict -> (dict.get("reason").equals("rejected"))).count();
				}
			return count >= retryCount;
		}
		return false;
	}

}
