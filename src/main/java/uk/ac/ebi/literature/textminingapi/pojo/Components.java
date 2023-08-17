package uk.ac.ebi.literature.textminingapi.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum  Components {

	DB_MANAGER("DBManager"),
	FETCHER("Fetcher"),
	CONVERTER("Converter"),
	OUTCOMEMANAGER("OutcomeManager"),
	CALLBACKMANAGER("CallbackManager"),
	NORMALIZER("Normalizer"),
	ANNOTATOR("Annotator");
	
	private String label;
	
	private static Logger logger = LoggerFactory.getLogger(Components.class);
	
	private Components(String label) {
		this.label = label;
	}
	public String getLabel() {
		return this.label;
	}
	
	public static Components getComponentByLabel(String label) {
		Components val = null;
		try {
			for (Components comp : Components.values()) {
				if (comp.getLabel().equalsIgnoreCase(label)) {
					return val;
				}
			}
		}catch (Exception e) {
			logger.error("status value not recognized {}", label);
			val = null;
		}
		return val;
	}
}
