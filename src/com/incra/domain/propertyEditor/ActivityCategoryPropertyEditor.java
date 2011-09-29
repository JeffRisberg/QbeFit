package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.ActivityCategory;
import com.incra.services.ActivityCategoryService;

public class ActivityCategoryPropertyEditor extends PropertyEditorSupport {
    private final ActivityCategoryService activityCategoryService;

    public ActivityCategoryPropertyEditor(ActivityCategoryService activityCategoryService) {
        this.activityCategoryService = activityCategoryService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(activityCategoryService.findEntityById(Integer.parseInt(text)));
    }

    @Override
    public String getAsText() {
        Object value = getValue();

        if (value instanceof ActivityCategory) {
            return String.valueOf(((ActivityCategory) value).getId());
        } else {
            return super.getAsText();
        }
    }
}
