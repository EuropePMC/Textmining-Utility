package uk.ac.ebi.literature.textminingapi.pojo;

import java.io.Serializable;
import java.util.Arrays;
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
@CompoundIndex(def = "{'ftId':1, 'user':1}", name = "ftId_user_index", unique = true, background = true)
@Document(collection = "#{@environment.getProperty('mongoDb.submissionsCollection')}")
public class SubmissionMessage implements Serializable {

	@Id
	@JsonProperty("_id")
	@org.codehaus.jackson.annotate.JsonProperty("_id")
	public ObjectId _id;

	@JsonProperty("ft_id")
	@org.codehaus.jackson.annotate.JsonProperty("ft_id")
	private String ftId;

	@JsonProperty("callback")
	@org.codehaus.jackson.annotate.JsonProperty("callback")
	private String callback;

	@JsonProperty("user")
	@org.codehaus.jackson.annotate.JsonProperty("user")
	private String user;

	@JsonProperty("status")
	@org.codehaus.jackson.annotate.JsonProperty("status")
	private String status;

	@JsonProperty("files")
	@org.codehaus.jackson.annotate.JsonProperty("files")
	private FileInfo[] files;

	@LastModifiedDate
	public Date dateModified;

	@CreatedDate
	public Date dateInserted;

	public String getFtId() {
		return ftId;
	}

	public void setFtId(String ftId) {
		this.ftId = ftId;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public FileInfo[] getFiles() {
		return files;
	}

	public void setFiles(FileInfo[] files) {
		this.files = files;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	@Override
	public String toString() {
		return "SubmissionMessage [ftId=" + ftId + ", callback=" + callback + ", user=" + user + ", status=" + status
				+ ", files=" + Arrays.toString(files) + "]";
	}

}
