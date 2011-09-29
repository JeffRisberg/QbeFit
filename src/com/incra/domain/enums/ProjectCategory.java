package com.incra.domain.enums;

/**
 * The <i>ProjectCategory</i> enum holds three values: Educational,
 * Transportation, Environmental.
 * 
 * @author Jeff Risberg
 * @since 01/02/11
 */
public enum ProjectCategory {
	/* 0 */EDUCATIONAL("Educational"),
	/* 1 */TRANSPORTATION("Transportation"),
	/* 2 */ENVIRONMENTAL("Environmental");

	private String name;

	private ProjectCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
