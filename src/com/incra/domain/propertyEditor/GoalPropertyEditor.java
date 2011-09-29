package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.Goal;
import com.incra.services.GoalService;

public class GoalPropertyEditor extends PropertyEditorSupport {
	private final GoalService goalService;

	public GoalPropertyEditor(GoalService goalService) {
		this.goalService = goalService;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(goalService.findEntityById(Integer.parseInt(text)));
	}

	@Override
	public String getAsText() {
		Object value = getValue();

		if (value instanceof Goal) {
			return String.valueOf(((Goal) value).getId());
		} else {
			return super.getAsText();
		}
	}
}
