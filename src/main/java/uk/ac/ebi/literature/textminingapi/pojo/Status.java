package uk.ac.ebi.literature.textminingapi.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum  Status {

	PENDING,
	FAILED,
	SUCCESS;
	
	private static Logger logger = LoggerFactory.getLogger(Status.class);
	
	public String getLabel() {
		return this.name().toLowerCase();
	}
	
	public static Status getStatusByLabel(String label) {
		Status val = null;
		try {
			val =Status.valueOf(label.toUpperCase());
		}catch (Exception e) {
			logger.error("status value not recognized {}", label);
			val = null;
		}
		return val;
	}
}
