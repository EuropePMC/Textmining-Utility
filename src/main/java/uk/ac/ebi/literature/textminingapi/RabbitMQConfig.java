package uk.ac.ebi.literature.textminingapi;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.tmExchange}")
    private String TM_EXCHANGE;

    @Value("${rabbitmq.plaintextQueue}")
    private String QUEUE_PLAINTEXT_MESSAGES;

    @Value("${rabbitmq.outcomeQueue}")
    private String QUEUE_OUTCOME_MESSAGES;
    
    @Value("${rabbitmq.rawAnnotationQueue}")
    private String QUEUE_RAWANNOTATION_MESSAGES;
    
    @Value("${rabbitmq.submissionsQueue}")
    private String QUEUE_SUBMISSIONS;
    
    @Value("${rabbitmq.jsonForApiQueue}")
    private String QUEUE_JSON_FOR_API;
    
    @Value("${rabbitmq.callbackQueue}")
    private String QUEUE_CALLBACK;
    
    @Value("${rabbitmq.my-wait-queue.ttl}")
    private Integer WAIT_QUEUE_TTL;

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.plaintextQueue.enable", havingValue = "true")
    Queue plainTextQueue() {
        String QUEUE_MESSAGES_DLQ = QUEUE_PLAINTEXT_MESSAGES + ".dlq";
        return QueueBuilder.durable(QUEUE_PLAINTEXT_MESSAGES)
                .withArgument("x-dead-letter-exchange", TM_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
                .build();
    }

    @Bean
    DirectExchange tmExchange() {
        return new DirectExchange(TM_EXCHANGE);
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.plaintextQueue.enable", havingValue = "true")
    Binding bindingPlainText() {
        return BindingBuilder.bind(plainTextQueue()).to(tmExchange()).with(QUEUE_PLAINTEXT_MESSAGES);
    }


    @Bean
    @ConditionalOnProperty(name = "rabbitmq.plaintextQueue.enable", havingValue = "true")
    Queue deadLetterPlainTextQueue() {
    	String QUEUE_MESSAGES_DLQ = QUEUE_PLAINTEXT_MESSAGES + ".dlq";
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).ttl(WAIT_QUEUE_TTL)
                .deadLetterExchange(TM_EXCHANGE)
                .deadLetterRoutingKey(QUEUE_PLAINTEXT_MESSAGES)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.plaintextQueue.enable", havingValue = "true")
    Binding deadLetterPlainTextBinding() {
        return BindingBuilder.bind(deadLetterPlainTextQueue()).to(tmExchange()).with(QUEUE_PLAINTEXT_MESSAGES + ".dlq");
    }

    
    // outCome Queue binding
    @Bean
    @ConditionalOnProperty(name = "rabbitmq.outcomeQueue.enable", havingValue = "true")
    Queue outcomeQueue() {
        String QUEUE_MESSAGES_DLQ = QUEUE_OUTCOME_MESSAGES + ".dlq";
        return QueueBuilder.durable(QUEUE_OUTCOME_MESSAGES)
                .withArgument("x-dead-letter-exchange", TM_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.outcomeQueue.enable", havingValue = "true")
    Binding bindingOutcome() {
        return BindingBuilder.bind(outcomeQueue()).to(tmExchange()).with(QUEUE_OUTCOME_MESSAGES);
    }


    @Bean
    @ConditionalOnProperty(name = "rabbitmq.outcomeQueue.enable", havingValue = "true")
    Queue deadLetterOutcomeQueue() {
    	String QUEUE_MESSAGES_DLQ = QUEUE_OUTCOME_MESSAGES + ".dlq";
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).ttl(WAIT_QUEUE_TTL)
                .deadLetterExchange(TM_EXCHANGE)
                .deadLetterRoutingKey(QUEUE_OUTCOME_MESSAGES)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.outcomeQueue.enable", havingValue = "true")
    Binding deadLetterOutcomeBinding() {
        return BindingBuilder.bind(deadLetterOutcomeQueue()).to(tmExchange()).with(QUEUE_OUTCOME_MESSAGES + ".dlq");
    }
    
 // raw annotation Queue binding
    @Bean
    @ConditionalOnProperty(name = "rabbitmq.rawannotationQueue.enable", havingValue = "true")
    Queue rawAnnotationQueue() {
        String QUEUE_MESSAGES_DLQ = QUEUE_RAWANNOTATION_MESSAGES + ".dlq";
        return QueueBuilder.durable(QUEUE_RAWANNOTATION_MESSAGES)
                .withArgument("x-dead-letter-exchange", TM_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.rawannotationQueue.enable", havingValue = "true")
    Binding bindingRawAnnotation() {
        return BindingBuilder.bind(rawAnnotationQueue()).to(tmExchange()).with(QUEUE_RAWANNOTATION_MESSAGES);
    }


    @Bean
    @ConditionalOnProperty(name = "rabbitmq.rawannotationQueue.enable", havingValue = "true")
    Queue deadLetterRawAnnotationQueue() {
    	String QUEUE_MESSAGES_DLQ = QUEUE_RAWANNOTATION_MESSAGES + ".dlq";
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).ttl(WAIT_QUEUE_TTL)
                .deadLetterExchange(TM_EXCHANGE)
                .deadLetterRoutingKey(QUEUE_RAWANNOTATION_MESSAGES)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.rawannotationQueue.enable", havingValue = "true")
    Binding deadLetterRawAnnotationBinding() {
        return BindingBuilder.bind(deadLetterRawAnnotationQueue()).to(tmExchange()).with(QUEUE_RAWANNOTATION_MESSAGES + ".dlq");
    }
    
    //submission queue
    @Bean
    @ConditionalOnProperty(name = "rabbitmq.submissionQueue.enable", havingValue = "true")
    Queue submissionsQueue() {
        String QUEUE_SUBMISSIONS_DLQ = QUEUE_SUBMISSIONS + ".dlq";
        return QueueBuilder.durable(QUEUE_SUBMISSIONS)
                .withArgument("x-dead-letter-exchange", TM_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", QUEUE_SUBMISSIONS_DLQ)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.submissionQueue.enable", havingValue = "true")
    Binding bindingSubmissionsQueue() {
        return BindingBuilder.bind(submissionsQueue()).to(tmExchange()).with(QUEUE_SUBMISSIONS);
    }


    @Bean
    @ConditionalOnProperty(name = "rabbitmq.submissionQueue.enable", havingValue = "true")
    Queue deadLetterSubmissionsQueue() {
    	String QUEUE_SUBMISSIONS_DLQ = QUEUE_SUBMISSIONS + ".dlq";
        return QueueBuilder.durable(QUEUE_SUBMISSIONS_DLQ).ttl(WAIT_QUEUE_TTL)
                .deadLetterExchange(TM_EXCHANGE)
                .deadLetterRoutingKey(QUEUE_SUBMISSIONS)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.submissionQueue.enable", havingValue = "true")
    Binding deadLetterSubmissionsQueueBinding() {
        return BindingBuilder.bind(deadLetterSubmissionsQueue()).to(tmExchange()).with(QUEUE_SUBMISSIONS + ".dlq");
    }
    
    //json for Api queue
    @Bean
    @ConditionalOnProperty(name = "rabbitmq.jsonForApiQueue.enable", havingValue = "true")
    Queue jsonForApiQueue() {
        String QUEUE_MESSAGES_DLQ = QUEUE_JSON_FOR_API + ".dlq";
        return QueueBuilder.durable(QUEUE_JSON_FOR_API)
                .withArgument("x-dead-letter-exchange", TM_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.jsonForApiQueue.enable", havingValue = "true")
    Binding bindingjsonForApiQueue() {
        return BindingBuilder.bind(jsonForApiQueue()).to(tmExchange()).with(QUEUE_JSON_FOR_API);
    }


    @Bean
    @ConditionalOnProperty(name = "rabbitmq.jsonForApiQueue.enable", havingValue = "true")
    Queue deadLetterJsonForApiQueue() {
    	String QUEUE_MESSAGES_DLQ = QUEUE_JSON_FOR_API + ".dlq";
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).ttl(WAIT_QUEUE_TTL)
                .deadLetterExchange(TM_EXCHANGE)
                .deadLetterRoutingKey(QUEUE_JSON_FOR_API)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.jsonForApiQueue.enable", havingValue = "true")
    Binding deadLetterJsonForApiBinding() {
        return BindingBuilder.bind(deadLetterJsonForApiQueue()).to(tmExchange()).with(QUEUE_JSON_FOR_API + ".dlq");
    }
    
    //callback queue
    @Bean
    @ConditionalOnProperty(name = "rabbitmq.callbackQueue.enable", havingValue = "true")
    Queue callbackQueue() {
        String QUEUE_MESSAGES_DLQ = QUEUE_CALLBACK + ".dlq";
        return QueueBuilder.durable(QUEUE_CALLBACK)
                .withArgument("x-dead-letter-exchange", TM_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGES_DLQ)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.callbackQueue.enable", havingValue = "true")
    Binding bindingCallbackQueue() {
        return BindingBuilder.bind(callbackQueue()).to(tmExchange()).with(QUEUE_CALLBACK);
    }


    @Bean
    @ConditionalOnProperty(name = "rabbitmq.callbackQueue.enable", havingValue = "true")
    Queue deadLetterCallbackQueue() {
    	String QUEUE_MESSAGES_DLQ = QUEUE_CALLBACK + ".dlq";
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).ttl(WAIT_QUEUE_TTL)
                .deadLetterExchange(TM_EXCHANGE)
                .deadLetterRoutingKey(QUEUE_CALLBACK)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.callbackQueue.enable", havingValue = "true")
    Binding deadLetterCallbackQueueBinding() {
        return BindingBuilder.bind(deadLetterCallbackQueue()).to(tmExchange()).with(QUEUE_CALLBACK + ".dlq");
    }
    
}