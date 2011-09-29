package com.incra.domain.enums;

/**
 * The <i>ProjectStage</i> enum holds values Proposed, Approved, In-progress,
 * Completed.
 * 
 * @author Jeff Risberg
 * @since 12/29/10
 */
public enum ProjectStage {
	/* 0 */PROPOSED("Proposed"),
	/* 1 */APPROVED("Approved"),
	/* 2 */INPROGRESS("In-Progress"),
	/* 3 */COMPLETED("Completed");

	private String name;

	private ProjectStage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
