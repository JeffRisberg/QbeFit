package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.TimeZone;
import com.incra.services.TimeZoneService;

public class TimeZonePropertyEditor extends PropertyEditorSupport {
    private final TimeZoneService timeZoneService;

    public TimeZonePropertyEditor(TimeZoneService timeZoneService) {
        this.timeZoneService = timeZoneService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(timeZoneService.findEntityById(text));
    }

    @Override
    public String getAsText() {
        Object value = getValue();

        if (value instanceof TimeZone) {
            return String.valueOf(((TimeZone) value).getId());
        } else {
            return super.getAsText();
        }
    }
}
