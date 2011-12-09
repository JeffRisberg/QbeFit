package com.incra.domain.enums;

/**
 * The <i>QuestionType</i> enumeration...
 * 
 * @author Jeff Risberg
 * @since 11/22/11
 */
public enum QuestionType {
    /** These are patterned after survey-management software */
    /* 0 */String("String"),
    /* 1 */Number("Number"),
    /* 2 */Boolean("Boolean"),
    /* 3 */Range("Range"),
    /* 4 */MultipleChoice("Multiple Choice"); /* display as drop down */

    private String name;

    QuestionType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
