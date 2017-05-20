/**
 * 
 */
package net.atos.awl.tes.cert.vo;

import java.util.Date;

/**
 * @author Ashish Modak
 * 
 */
public class ManagerReport {

	/**
	 * Generated Serial Version ID.
	 */
	private static final long serialVersionUID = 2093614082982461345L;

	private String das_id;

	private String name;

	private Integer last_level;

	private Date submitted_date;

	public String getDas_id() {
		return das_id;
	}

	public void setDas_id(String das_id) {
		this.das_id = das_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLast_level() {
		return last_level;
	}

	public void setLast_level(Integer last_level) {
		this.last_level = last_level;
	}

	public Date getSubmitted_date() {
		return submitted_date;
	}

	public void setSubmitted_date(Date submitted_date) {
		this.submitted_date = submitted_date;
	}

}
