package uk.ac.ebi.literature.textminingapi.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnotationTag implements Serializable {
	
	@JsonProperty("name")
	@org.codehaus.jackson.annotate.JsonProperty("name")
	private String name;
	
	@JsonProperty("uri")
	@org.codehaus.jackson.annotate.JsonProperty("uri")
	private String uri;


	@Override
	public String toString() {
		return "AnnotationTag [name=" + name + ", uri=" + uri + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
