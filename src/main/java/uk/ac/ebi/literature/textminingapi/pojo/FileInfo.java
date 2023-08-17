package uk.ac.ebi.literature.textminingapi.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FileInfo implements Serializable {
	
	@JsonProperty("filename")
	@org.codehaus.jackson.annotate.JsonProperty("filename")
	private String filename;
	
	@JsonProperty("url")
	@org.codehaus.jackson.annotate.JsonProperty("url")
	private String url;
	
	@JsonProperty("url_fetch")
	@org.codehaus.jackson.annotate.JsonProperty("url_fetch")
	private String urlFetch;
	
	@JsonProperty("status")
	@org.codehaus.jackson.annotate.JsonProperty("status")
	private String status;
	
	@JsonProperty("error")
	@org.codehaus.jackson.annotate.JsonProperty("error")
	String error;

	@JsonProperty("error_component")
	@org.codehaus.jackson.annotate.JsonProperty("error_component")
	String errorComponent;
	
	public String getUrlFetch() {
		return urlFetch;
	}

	public void setUrlFetch(String urlFetch) {
		this.urlFetch = urlFetch;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorComponent() {
		return errorComponent;
	}

	public void setErrorComponent(String errorComponent) {
		this.errorComponent = errorComponent;
	}

	@Override
	public String toString() {
		return "FileInfo [filename=" + filename + ", url=" + url + ", urlFetch=" + urlFetch + ", status=" + status
				+ ", error=" + error + ", errorComponent=" + errorComponent + "]";
	}
	
}
