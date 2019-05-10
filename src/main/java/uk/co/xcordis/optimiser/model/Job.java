package uk.co.xcordis.optimiser.model;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import uk.co.xcordis.optimiser.util.ApplicationTableConstants;

/**
 * The <code>Job</code> model is used to store the job related data details in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Table(name = ApplicationTableConstants.TABLENAME_JOB)
public class Job implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PartitionKey(0)
	private UUID jobId;

	private String jobName;

	private String jobClassName;

	private String createdDate;

	private String jobDuration;

	private String lastRunTime;

	private Boolean status;

	private Boolean active;

	private String auditTimeStamp;

	/**
	 * @return the jobId
	 */
	public UUID getJobId() {

		return jobId;
	}

	/**
	 * @param jobId
	 *            the jobId to set
	 */
	public void setJobId(final UUID jobId) {

		this.jobId = jobId;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {

		return jobName;
	}

	/**
	 * @param jobName
	 *            the jobName to set
	 */
	public void setJobName(final String jobName) {

		this.jobName = jobName;
	}

	/**
	 * @return the jobClassName
	 */
	public String getJobClassName() {

		return jobClassName;
	}

	/**
	 * @param jobClassName
	 *            the jobClassName to set
	 */
	public void setJobClassName(final String jobClassName) {

		this.jobClassName = jobClassName;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {

		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(final String createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * @return the jobDuration
	 */
	public String getJobDuration() {

		return jobDuration;
	}

	/**
	 * @param jobDuration
	 *            the jobDuration to set
	 */
	public void setJobDuration(final String jobDuration) {

		this.jobDuration = jobDuration;
	}

	/**
	 * @return the lastRunTime
	 */
	public String getLastRunTime() {

		return lastRunTime;
	}

	/**
	 * @param lastRunTime
	 *            the lastRunTime to set
	 */
	public void setLastRunTime(final String lastRunTime) {

		this.lastRunTime = lastRunTime;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {

		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final Boolean status) {

		this.status = status;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {

		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(final Boolean active) {

		this.active = active;
	}

	/**
	 * @return the auditTimeStamp
	 */
	public String getAuditTimeStamp() {

		return auditTimeStamp;
	}

	/**
	 * @param auditTimeStamp
	 *            the auditTimeStamp to set
	 */
	public void setAuditTimeStamp(String auditTimeStamp) {

		this.auditTimeStamp = auditTimeStamp;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
