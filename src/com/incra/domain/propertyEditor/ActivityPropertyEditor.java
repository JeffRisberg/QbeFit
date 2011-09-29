package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.Activity;
import com.incra.services.ActivityService;

public class ActivityPropertyEditor extends PropertyEditorSupport {
	private final ActivityService activityService;

	public ActivityPropertyEditor(ActivityService activityService) {
		this.activityService = activityService;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(activityService.findEntityById(Integer.parseInt(text)));
	}

	@Override
	public String getAsText() {
		Object value = getValue();

		if (value instanceof Activity) {
			return String.valueOf(((Activity) value).getId());
		} else {
			return super.getAsText();
		}
	}
}
