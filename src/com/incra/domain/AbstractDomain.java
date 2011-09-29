package com.incra.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * The <i>AbstractDomain</i> class is the superclass of entities. It provides
 * standard fields for tracking usage.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@MappedSuperclass
public abstract class AbstractDomain {
	private Date dateCreated;
	private Date dateUpdated;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
}
