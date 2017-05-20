package net.atos.awl.tes.cert.vo;

import java.io.Serializable;

public class Technology implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String acronym;

	/**
	 * @param name
	 * @param acronym
	 */
	public Technology(String name, String acronym) {
		super();
		this.name = name;
		this.acronym = acronym;
	}

	/**
	 * 
	 */
	public Technology() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @param acronym
	 *            the acronym to set
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
}
