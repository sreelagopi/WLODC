package net.atos.awl.tes.cert.vo;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer resultId;
	private int examId;
	private String isPass;
	private Double percentile;
	private Date enrolledOn;
	private Date submittedOn;
	/**
	 * @return the resultId
	 */
	public Integer getResultId() {
		return resultId;
	}
	/**
	 * @param resultId the resultId to set
	 */
	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}
	/**
	 * @return the examId
	 */
	public int getExamId() {
		return examId;
	}
	/**
	 * @param examId the examId to set
	 */
	public void setExamId(int examId) {
		this.examId = examId;
	}
	

	/**
	 * @return the isPass
	 */
	public String getIsPass() {
		return isPass;
	}
	/**
	 * @param isPass the isPass to set
	 */
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	/**
	 * @return the percentile
	 */
	public Double getPercentile() {
		return percentile;
	}
	/**
	 * @param percentile the percentile to set
	 */
	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}
	/**
	 * @return the enrolledOn
	 */
	public Date getEnrolledOn() {
		return enrolledOn;
	}
	/**
	 * @param enrolledOn the enrolledOn to set
	 */
	public void setEnrolledOn(Date enrolledOn) {
		this.enrolledOn = enrolledOn;
	}
	/**
	 * @return the submittedOn
	 */
	public Date getSubmittedOn() {
		return submittedOn;
	}
	/**
	 * @param submittedOn the submittedOn to set
	 */
	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}
}
