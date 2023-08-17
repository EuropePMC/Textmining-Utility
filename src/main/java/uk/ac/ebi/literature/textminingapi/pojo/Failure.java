package uk.ac.ebi.literature.textminingapi.pojo;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
@CompoundIndex(def = "{'ftId':1, 'user':1, 'fileName':1}", name = "ftId_user_fileName_index", unique = true, background = true)
@Document(collection = "#{@environment.getProperty('mongoDb.failureCollection')}")
public class Failure implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("_id")
	@org.codehaus.jackson.annotate.JsonProperty("_id")
	public ObjectId _id;

	@JsonProperty("ft_id")
	@org.codehaus.jackson.annotate.JsonProperty("ft_id")
	private String ftId;

	@JsonProperty("user")
	@org.codehaus.jackson.annotate.JsonProperty("user")
	private String user;

	@JsonProperty("fileName")
	@org.codehaus.jackson.annotate.JsonProperty("fileName")
	private String fileName;

	@JsonProperty("component")
	@org.codehaus.jackson.annotate.JsonProperty("component")
	private String component;

	@JsonProperty("component")
	@org.codehaus.jackson.annotate.JsonProperty("payload")
	private Object payload;

	@LastModifiedDate
	public Date dateModified;

	@CreatedDate
	public Date dateInserted;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getDateInserted() {
		return dateInserted;
	}

	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}

	@Override
	public String toString() {
		return "Failure [_id=" + _id + ", ftId=" + ftId + ", user=" + user + ", fileName=" + fileName + ", component="
				+ component + ", payload=" + payload + ", dateModified=" + dateModified + ", dateInserted="
				+ dateInserted + "]";
	}

	/**
	 * 
	 * @param ftId
	 * @param user
	 * @param fileName
	 * @param component
	 * @param payload
	 * @return
	 */
	public static Failure valueOf(String ftId, String user, String fileName, String component, Object payload) {
		Failure failure = new Failure();
		failure.setFtId(ftId);
		failure.setUser(user);
		failure.setPayload(payload);
		failure.setFileName(fileName);
		failure.setComponent(component);
		return failure;
	}

}
