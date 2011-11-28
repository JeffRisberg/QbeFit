package com.incra.domain.enums;

/**
 * The <i>QuestionType</i> enumeration...
 * 
 * @author Jeff Risberg
 * @since 11/22/11
 */
public enum QuestionType {
    /* These are patterned after Excel */
    /* 0 */InputString("InputString"),
    /* 1 */InputNumber("InputNumber"),
    /* 2 */Checkbox("Checkbox"),
    /* 3 */Date("Date"),
    /* 4 */DropDownString("DropDownString"),
    /* 5 */DropDownNumber("DropDownNumber");

    private String name;

    QuestionType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
