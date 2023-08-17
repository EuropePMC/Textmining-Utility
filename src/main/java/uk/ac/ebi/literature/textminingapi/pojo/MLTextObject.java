package uk.ac.ebi.literature.textminingapi.pojo;

import java.io.Serializable;

import javax.annotation.Generated;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Generated("jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
public class MLTextObject implements Serializable, Cloneable {

	private static final long serialVersionUID = -5248563576437744452L;

	@JsonProperty("ft_id")
	@org.codehaus.jackson.annotate.JsonProperty("ft_id")
	String ftId;
	@JsonProperty("user")
	@org.codehaus.jackson.annotate.JsonProperty("user")
	String user;
	@JsonProperty("filename")
	@org.codehaus.jackson.annotate.JsonProperty("filename")
	String filename;
	@JsonProperty("url")
	@org.codehaus.jackson.annotate.JsonProperty("url")
	String url;
	@JsonProperty("status")
	@org.codehaus.jackson.annotate.JsonProperty("status")
	String status;
	
	@JsonProperty("error")
	@org.codehaus.jackson.annotate.JsonProperty("error")
	String error;

	@JsonProperty("error_component")
	@org.codehaus.jackson.annotate.JsonProperty("error_component")
	String errorComponent;
	
	@JsonProperty("processingFilename")
	@org.codehaus.jackson.annotate.JsonProperty("processingFilename")
	String processingFilename;

	public MLTextObject() {
		super();
	}

	public String getFtId() {
		return ftId;
	}

	public void setFtId(String ftId) {
		this.ftId = ftId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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


	public String getProcessingFilename() {
		return processingFilename;
	}

	public void setProcessingFilename(String processingFile) {
		this.processingFilename = processingFile;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MLTextObject other = (MLTextObject) obj;
		if (errorComponent == null) {
			if (other.errorComponent != null)
				return false;
		} else if (!errorComponent.equals(other.errorComponent))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (ftId == null) {
			if (other.ftId != null)
				return false;
		} else if (!ftId.equals(other.ftId))
			return false;
		if (processingFilename == null) {
			if (other.processingFilename != null)
				return false;
		} else if (!processingFilename.equals(other.processingFilename))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(MLTextObject.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("ftId");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.ftId, "<null>"));
		sb.append(',');
		sb.append("user");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.user, "<null>"));
		sb.append(',');
		sb.append("filename");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.filename, "<null>"));
		sb.append(',');
		sb.append("url");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.url, "<null>"));
		sb.append(',');
		sb.append("status");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.status, "<null>"));
		sb.append(',');
		sb.append("error");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.error, "<null>"));
		sb.append(",");
		sb.append("errorComponent");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.errorComponent, "<null>"));
		sb.append(",");
		sb.append("processingFilename");
		sb.append('=');
		sb.append(ObjectUtils.defaultIfNull(this.processingFilename, "<null>"));
		sb.append(']');

		return sb.toString();
	}

	@Override
	public MLTextObject clone() {
		MLTextObject mlTextObject = new MLTextObject();
		mlTextObject.setError(this.getError());
		mlTextObject.setErrorComponent(this.getErrorComponent());
		mlTextObject.setFilename(this.getFilename());
		mlTextObject.setProcessingFilename(this.getProcessingFilename());
		mlTextObject.setFtId(this.getFtId());
		mlTextObject.setUrl(this.getUser());
		mlTextObject.setUrl(this.getUrl());
		mlTextObject.setStatus(this.getStatus());
		return mlTextObject;
	}
	
}
