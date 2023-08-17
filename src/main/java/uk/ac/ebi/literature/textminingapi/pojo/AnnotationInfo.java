package uk.ac.ebi.literature.textminingapi.pojo;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnotationInfo implements Serializable {
	
	@JsonProperty("exact")
	@org.codehaus.jackson.annotate.JsonProperty("exact")
	private String exact;
	
	@JsonProperty("type")
	@org.codehaus.jackson.annotate.JsonProperty("type")
	private String type;
	
	@JsonProperty("frequency")
	@org.codehaus.jackson.annotate.JsonProperty("frequency")
	private Integer frequency;

	@JsonProperty("tags")
	@org.codehaus.jackson.annotate.JsonProperty("tags")
	private AnnotationTag[] tags;

	public String getExact() {
		return exact;
	}

	public void setExact(String exact) {
		this.exact = exact;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public AnnotationTag[] getTags() {
		return tags;
	}

	public void setTags(AnnotationTag[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "AnnotationInfo [exact=" + exact + ", type=" + type + ", frequency=" + frequency + ", tags="
				+ Arrays.toString(tags) + "]";
	}
	
	
}
