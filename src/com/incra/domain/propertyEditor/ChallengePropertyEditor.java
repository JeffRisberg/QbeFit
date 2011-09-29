package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.Challenge;
import com.incra.services.ChallengeService;

public class ChallengePropertyEditor extends PropertyEditorSupport {
    private final ChallengeService challengeService;

    public ChallengePropertyEditor(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(challengeService.findEntityById(Integer.parseInt(text)));
    }

    @Override
    public String getAsText() {
        Object value = getValue();

        if (value instanceof Challenge) {
            return String.valueOf(((Challenge) value).getId());
        } else {
            return super.getAsText();
        }
    }
}
