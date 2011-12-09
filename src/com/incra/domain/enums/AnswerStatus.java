package com.incra.domain.enums;

/**
 * The <i>AnswerStatus</i> enumeration...
 * 
 * @author Jeff Risberg
 * @since 11/28/11
 */
public enum AnswerStatus {
    /* 0 */Considering("Considering"),
    /* 1 */InProgress("InProgress"),
    /* 2 */Completed("Completed");

    private String name;

    AnswerStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
