package uk.ac.ebi.literature.textminingapi.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.DeleteResult;

import uk.ac.ebi.literature.textminingapi.pojo.AnnotationsData;
import uk.ac.ebi.literature.textminingapi.pojo.Failure;
import uk.ac.ebi.literature.textminingapi.pojo.SubmissionMessage;

import java.util.Date;
import java.util.List;

@Service
@ConditionalOnProperty(name = "mongo.enable", havingValue = "true")
public class MongoService {

	private final MongoTemplate mongoTemplate;

	private static Logger logger = LoggerFactory.getLogger(MongoService.class);

	public MongoService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Methods to store a new submission or completely replace it (upsert). It will
	 * be first delete the eventual annotations connected with eventual pre existing
	 * submission and later upsert the submission. It is operating in a
	 * transactional way
	 * 
	 * @param submissionMessage
	 * @return the Submission object just inserted into DB
	 * @throws Exception
	 */
	@Transactional
	public SubmissionMessage storeSubmission(SubmissionMessage submissionMessage) throws Exception {
		Query query = getSubmissionQuery(submissionMessage.getFtId(), submissionMessage.getUser());
		DeleteResult del = mongoTemplate.remove(query, AnnotationsData.class);
		logger.info("Deleted {} annotations records for ftId {} and user {}", del.getDeletedCount(),
				submissionMessage.getFtId(), submissionMessage.getUser());
		return mongoTemplate.save(submissionMessage);
	}

	/**
	 * Equivalent method of storeSubmission but in a not transactional way
	 * 
	 * @param submissionMessage
	 * @return
	 * @throws Exception
	 */
	public SubmissionMessage storeSubmissionNoTransactional(SubmissionMessage submissionMessage) throws Exception {
		return this.storeSubmissionInternal(submissionMessage);
	}

	private SubmissionMessage storeSubmissionInternal(SubmissionMessage submissionMessage) throws Exception {
		Query query = getSubmissionQuery(submissionMessage.getFtId(), submissionMessage.getUser());
		DeleteResult del = mongoTemplate.remove(query, AnnotationsData.class);
		logger.info("Deleted {} annotations records for ftId {} and user {}", del.getDeletedCount(),
				submissionMessage.getFtId(), submissionMessage.getUser());
		return mongoTemplate.save(submissionMessage);
	}

	/**
	 * Method to find a submission related to a spoecifi Ft_id and user
	 * 
	 * @param ftId
	 * @param user
	 * @return the data of thee submission if it is found or null if there is no
	 *         submission with that ft_id for the user specified
	 */
	public SubmissionMessage findSubmission(String ftId, String user) {
		Query query = getSubmissionQuery(ftId, user);
		return mongoTemplate.findOne(query, SubmissionMessage.class);
	}

	/**
	 * Methods to delete all annotations and the submission itself from the DB for a
	 * specifi ft_id and user. It operates in a transactional way
	 * 
	 * @param ftId
	 * @param user
	 * @throws Exception
	 */
	@Transactional
	public void deleteSubmission(String ftId, String user) throws Exception {
		Query query = getSubmissionQuery(ftId, user);
		DeleteResult del = mongoTemplate.remove(query, SubmissionMessage.class);
		logger.info("Deleted {} submission record for ftId {} and user {}", del.getDeletedCount(), ftId, user);
		del = mongoTemplate.remove(query, AnnotationsData.class);
		logger.info("Deleted {} annotations records for ftId {} and user {}", del.getDeletedCount(), ftId, user);
	}

	/**
	 * Equivalent method of deleteSubmission operating in a not transactional way
	 * 
	 * @param ftId
	 * @param user
	 * @throws Exception
	 */
	public void deleteSubmissionNoTransactional(String ftId, String user) throws Exception {
		this.internalDeletion(ftId, user);
	}

	private void internalDeletion(String ftId, String user) throws Exception {
		Query query = getSubmissionQuery(ftId, user);
		DeleteResult del = mongoTemplate.remove(query, SubmissionMessage.class);
		logger.info("Deleted {} submission record for ftId {} and user {}", del.getDeletedCount(), ftId, user);
		del = mongoTemplate.remove(query, AnnotationsData.class);
		logger.info("Deleted {} annotations records for ftId {} and user {}", del.getDeletedCount(), ftId, user);
	}

	/**
	 * Method to store Annotations related to a file of a submission into the
	 * database. It replaces entirely the annotations for this file if they were
	 * already in the DB. It operates in a transactional way
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Transactional
	public void storeAnnotations(AnnotationsData data) throws Exception {
		Query query = getFileQuery(data.getFtId(), data.getUser(), data.getFilename());
		DeleteResult del = mongoTemplate.remove(query, AnnotationsData.class);
		logger.info("Deleted {} annotation record for ftId {} and user {} and file {}", del.getDeletedCount(),
				data.getFtId(), data.getUser(), data.getFilename());
		mongoTemplate.save(data);
	}

	/**
	 * Equivalent method of storeAnnotations operating in a not transational way
	 * 
	 * @param data
	 * @throws Exception
	 */
	public void storeAnnotationsNoTransactional(AnnotationsData data) throws Exception {
		this.internalStoreAnnotations(data);
	}

	private void internalStoreAnnotations(AnnotationsData data) throws Exception {
		Query query = getFileQuery(data.getFtId(), data.getUser(), data.getFilename());
		DeleteResult del = mongoTemplate.remove(query, AnnotationsData.class);
		logger.info("Deleted {} annotation record for ftId {} and user {} and file {}", del.getDeletedCount(),
				data.getFtId(), data.getUser(), data.getFilename());
		mongoTemplate.save(data);
	}

	private Query getSubmissionQuery(String ftId, String user) {
		Criteria criteria = Criteria.where("ftId").is(ftId);
		criteria.and("user").is(user);
		Query query = new Query();
		query.addCriteria(criteria);
		return query;
	}

	private Query getFileQuery(String ftId, String user, String filename) {
		Criteria criteria = Criteria.where("ftId").is(ftId);
		criteria.and("user").is(user);
		if(StringUtils.isNotBlank(filename))
			criteria.and("filename").is(filename);
		Query query = new Query();
		query.addCriteria(criteria);
		return query;
	}

	/**
	 * Method to find annotations for a specific file connected with a submission
	 * 
	 * @param ftId
	 * @param user
	 * @param filename
	 * @return the annotations data related to the file if they are there in DB,
	 *         otherwise null
	 */
	public AnnotationsData findAnnotations(String ftId, String user, String filename) {
		Query query = getFileQuery(ftId, user, filename);
		return mongoTemplate.findOne(query, AnnotationsData.class);
	}
	
	/**
	 * 
	 * @param ftId
	 * @param user
	 * @return
	 */
	public List<AnnotationsData> findAnnotations(String ftId, String user){
		Query query = getFileQuery(ftId, user, null);
		return mongoTemplate.find(query, AnnotationsData.class);
	}

	/**
	 * Methods to update a submission. It will be updating only an existing
	 * submissions metadata without touching the eventual annotations. It operates
	 * in a transactional way
	 * 
	 * @param submissionMessage
	 * @return the Submission object just updated into DB
	 * @throws Exception
	 */
	@Transactional
	public SubmissionMessage updateSubmission(SubmissionMessage submissionMessage) throws Exception {
		return mongoTemplate.save(submissionMessage);
	}

	/**
	 * Equivalent method of updateSubmission but in a not transactional way
	 * 
	 * @param submissionMessage
	 * @return
	 * @throws Exception
	 */
	public SubmissionMessage updateSubmissionNoTransactional(SubmissionMessage submissionMessage) throws Exception {
		return mongoTemplate.save(submissionMessage);
	}

	/**
	 * Stores failure
	 * 
	 * @param failure
	 * @return
	 * @throws Exception
	 */
	public Failure storeFailure(Failure failure) throws Exception {
		Query query = getFailureQuery(failure.getFtId(), failure.getUser(), failure.getFileName());
		DeleteResult del = mongoTemplate.remove(query, Failure.class);
		logger.info("Deleted {} failure records for ftId {} and user {} and fileName {}", del.getDeletedCount(),
				failure.getFtId(), failure.getUser(), failure.getFileName());
		return mongoTemplate.save(failure);
	}

	/**
	 * Find a failure by ftId, user and a fileName
	 * 
	 * @param ftId
	 * @param user
	 * @param fileName
	 * @return
	 */
	public Failure findFailure(String ftId, String user, String fileName) {
		Query query = getFailureQuery(ftId, user, fileName);
		return mongoTemplate.findOne(query, Failure.class);
	}

	/**
	 * 
	 * @param ftId
	 * @param user
	 * @param fileName
	 * @return
	 */
	private Query getFailureQuery(String ftId, String user, String fileName) {
		Criteria criteria = Criteria.where("ftId").is(ftId).and("user").is(user).and("fileName").is(fileName);
		Query query = new Query();
		query.addCriteria(criteria);
		return query;
	}

	/**
	 * Method to find failed submissions since lastRunDate
	 *
	 * @param lastRunDate
	 * @return the data of all submission that have failed status since lastRunDate
	 */
	public List<SubmissionMessage> findFailedSubmission(Date lastRunDate) {
		Query query = getSubmissionQueryByDate(lastRunDate);
		return mongoTemplate.find(query, SubmissionMessage.class);
	}

	private Query getSubmissionQueryByDate(Date dateModified) {
		Criteria criteria = Criteria.where("dateModified").gte(dateModified).and("status").is("failed");

		Query query = new Query();
		query.addCriteria(criteria);
		return query;
	}
}
