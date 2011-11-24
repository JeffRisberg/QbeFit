package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.OrganizationType;
import com.incra.services.OrganizationTypeService;

public class OrganizationTypePropertyEditor extends PropertyEditorSupport {
    private final OrganizationTypeService organizationTypeService;

    public OrganizationTypePropertyEditor(OrganizationTypeService organizationTypeService) {
        this.organizationTypeService = organizationTypeService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(organizationTypeService.findEntityById(Integer.parseInt(text)));
    }

    @Override
    public String getAsText() {
        Object value = getValue();

        if (value instanceof OrganizationType) {
            return String.valueOf(((OrganizationType) value).getId());
        } else {
            return super.getAsText();
        }
    }
}
