package com.incra.domain.enums;

/**
 * The <i>EnumDatatype</i> class is used to specify how a value should be
 * formatted. If there is no datatype specified, then a generic format is used,
 * which is based on simply calling toString() on the value, and showing it left
 * aligned. In the list below, all of the numeric formats will be shown right
 * aligned.
 * 
 * @author Jeff Risberg
 * @since 01/16/11
 */
public enum EnumDatatype {
    /* These are patterned after Excel */
    /* 0 */Hidden("Hidden", "left", false),
    /* 1 */String("String", "left"),
    /* 2 */Integer("Integer", "right"),
    /* 3 */Double("Double", "right"),
    /* 4 */Boolean("Boolean", "center"),
    /* 5 */Date("Date", "left"),
    /* 6 */Financial("Financial", "right"),
    /* 7 */ActivityAmount("ActivityAmount", "right"),
    /* 8 */Identifier("Identifier", "right");

    private String name;
    private String alignment;
    private boolean shown;

    EnumDatatype(final String name, final String alignment) {
        this.name = name;
        this.alignment = alignment;
        this.shown = true;
    }

    EnumDatatype(final String name, final String alignment, final boolean shown) {
        this.name = name;
        this.alignment = alignment;
        this.shown = shown;
    }

    public String getName() {
        return name;
    }

    public String getAlignment() {
        return alignment;
    }

    public boolean isShown() {
        return shown;
    }
}
