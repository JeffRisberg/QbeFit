package com.incra.controllers.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The <i>UserActivityRowDTO</i> holds the data used in one row of the
 * presentation.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
public class TrackSummaryRow {
	private String name;
	private int activityId;
	private int userActivityId;
	private List<Boolean> flags;

	public TrackSummaryRow(String name, int activityId, int userActivityId) {
		this.name = name;
		this.activityId = activityId;
		this.userActivityId = userActivityId;
		this.flags = new ArrayList<Boolean>();
	}

	public void addFlag(boolean flag) {
		flags.add(flag);
	}

	public String getName() {
		return name;
	}

	public int getActivityId() {
		return activityId;
	}

	public int getUserActivityId() {
		return userActivityId;
	}

	public List<Boolean> getFlags() {
		return flags;
	}
}
