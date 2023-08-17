package uk.ac.ebi.literature.textminingapi.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import uk.ac.ebi.literature.textminingapi.pojo.Components;
import uk.ac.ebi.literature.textminingapi.pojo.MLTextObject;
import uk.ac.ebi.literature.textminingapi.pojo.Status;

public class Utility {

	
	/**
	 * Method to define if an objext is null (or empty for String and List)
	 * @param val
	 * @return true if val is empty/null, false otherwise
	 */
	public static boolean isEmpty(Object val){
		if (val == null){
			return true;
		}
		
		if (val instanceof String){
			if ("".equalsIgnoreCase(((String)val).trim())){
				return true;
			}
		}

		if (val instanceof List) {
			return ((List) val).size() == 0;
		}

		return false;
	}

	/**
	 * Method to parse a json Object from a string
	 * @param <T> type of the object
	 * @param json string to parse
	 * @param modelClass type of the object
	 * @return the object populated with value in Json string if the Json string could be parsed and mapped to the object
	 * @throws IOException
	 */
	public static <T> T parseJsonObject(String json, Class<T> modelClass) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		T result = mapper.readValue(json, modelClass);
		return result;
	}

	/**
	 * Method to parse a list of json Object from a string
	 * @param <T> type of the object in the list
	 * @param json string to parse
	 * @param classList type of the object in the list
	 * @return the list of objects populated with value in Json string if the Json string could be parsed and mapped to a list of objects of specified tyoe
	 * @throws IOException
	 */
	public static <T> List<T> parseJsonList(String json, Class<T> classList) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<T> result = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, classList));
		return result;
	}
	
	/**
	 * Method to serialize an Object values into a Json String
	 * @param obj object to serialize
	 * @return the Json string representation of the object
	 */
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * Method to parse a json Object from a array of bytes
	 * @param <T> type of the object
	 * @param json bytes to parse
	 * @param modelClass type of the object
	 * @return the object populated with value in Json bytes if the Json bytes could be parsed and mapped to the object
	 * @throws IOException
	 */
	public static <T> T parseJsonObject(byte[] json, Class<T> modelClass) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		T result = mapper.readValue(json, modelClass);
		return result;
	}
	
	/**
	 * Method to get a RabbitMq message encapsulating the MLTextObject bean
	 * @param any Object as payload
	 * @param addHeader tue if we want to add message headers
	 * @param list values of the messages headers to add eventually
	 * @return a RabbitMq message encapsulating the MLTextObject bean with eventual headers specified
	 */
	public static Message getMessageForQueue(Object payload, boolean addHeader, Map<String, Object> paramsHeader) {
		SimpleMessageConverter smc = new SimpleMessageConverter();
		MessageProperties msgProps = new MessageProperties();
		if (addHeader) {
			if (Utility.isEmpty(paramsHeader)==false) {
				paramsHeader.entrySet().stream().forEach(headerInfo -> msgProps.setHeader(headerInfo.getKey(), headerInfo.getValue()));
			}
		}
		return smc.toMessage(payload, msgProps);
	}
	
	/**
	 * Metohd to extrapolate from  a RabbitMq message the inner MlTextObject bean
	 * @param message
	 * @return the inner MlTextObject bean
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T castMessage(Message message, Class<T> clazz) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
		ObjectInput objectInput = new ObjectInputStream(bis);
		var obj = clazz.cast(objectInput.readObject());
		return obj;
	}
	
	/**
	 * Method to mark a failure in the RabbitMq message
	 * @param mlTextObject RabbitMq message
	 * @param component Component in which the failure happened. It will use the defualt error message associated with that component
	 */
	public static void markMessageAsFailed(MLTextObject mlTextObject, Components component) {
		markMessageAsFailed(mlTextObject, component, null);
	}
	
	/**
	 * Method to mark a failure in the RabbitMq message
	 * @param mlTextObject RabbitMq message
	 * @param component Component in which the failure happened.
	   @param errorInput error to store in the error field
	 */
	public static void markMessageAsFailed(MLTextObject mlTextObject, Components component, String errorInput) {
		
		String error = errorInput;
		
		if (Utility.isEmpty(error)) {
			switch (component) {
			case FETCHER:
				error = "Impossible to retrieve file content from " + mlTextObject.getUrl();
				break;
			case CONVERTER:
				error = "Failure in converting the file to text for file "+mlTextObject.getFilename();
				break;
			case DB_MANAGER:
				error = "Impossible to store annotations for the file " + mlTextObject.getFilename();
				break;
			case NORMALIZER:
				error = "Impossible to normalize the file " + mlTextObject.getFilename();
				break;	
			default:
				break;
			}
		}
		mlTextObject.setStatus(Status.FAILED.toString().toLowerCase());
		mlTextObject.setError(error);
		mlTextObject.setErrorComponent(component.getLabel());
		mlTextObject.setProcessingFilename(null);
	}

}
