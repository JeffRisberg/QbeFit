package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.User;
import com.incra.services.UserService;

public class UserPropertyEditor extends PropertyEditorSupport {
	private final UserService userService;

	public UserPropertyEditor(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(userService.findEntityById(Integer.parseInt(text)));
	}

	@Override
	public String getAsText() {
		Object value = getValue();

		if (value instanceof User) {
			return String.valueOf(((User) value).getId());
		} else {
			return super.getAsText();
		}
	}
}
