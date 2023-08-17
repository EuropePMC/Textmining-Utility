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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@CompoundIndex(def = "{'ftId':1, 'user':1, 'filename':1}", name = "ftId_user_filename_index", unique = true, background = true)
@Document(collection = "#{@environment.getProperty('mongoDb.annotationsCollection')}")
public class AnnotationsData implements Serializable {

	@Id
	@JsonProperty("_id")
	@org.codehaus.jackson.annotate.JsonProperty("_id")
	public ObjectId _id;

	@JsonProperty("ft_id")
	@org.codehaus.jackson.annotate.JsonProperty("ft_id")
	private String ftId;

	@JsonProperty("filename")
	@org.codehaus.jackson.annotate.JsonProperty("filename")
	private String filename;

	@JsonProperty("user")
	@org.codehaus.jackson.annotate.JsonProperty("user")
	private String user;

	@JsonProperty("anns")
	@org.codehaus.jackson.annotate.JsonProperty("anns")
	private AnnotationInfo[] anns;

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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String file) {
		this.filename = file;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public AnnotationInfo[] getAnns() {
		return anns;
	}

	public void setAnns(AnnotationInfo[] anns) {
		this.anns = anns;
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
		return "AnnotationsData [ftId=" + ftId + ", file=" + filename + ", user=" + user + ", anns="
				+ Arrays.toString(anns) + "]";
	}

}
