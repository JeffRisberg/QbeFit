package com.incra.controllers.dto;

import java.util.Date;

/**
 * The <i>TrackSummaryColumn</i> holds the data used in one row of the
 * presentation.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
public class TrackSummaryColumn {
	private Date fromDate;
	private Date toDate;
	private String label;

	/** Constructor */
	public TrackSummaryColumn(Date fromDate, Date toDate, String label) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.label = label;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public String getLabel() {
		return label;
	}
}
