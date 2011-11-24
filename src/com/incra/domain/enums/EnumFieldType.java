package com.incra.domain.enums;

/**
 * 
 */
public enum EnumFieldType {
	/* These are patterned after Excel */
	/* 0 */InputString("InputString"),
	/* 1 */InputNumber("InputNumber"),
	/* 2 */Checkbox("Checkbox"),
	/* 3 */Date("Date"),
	/* 4 */DropDownString("DropDownString"),
	/* 5 */DropDownNumber("DropDownNumber");
	
	
	private String name;	

	EnumFieldType(final String name) {
		
		this.name = name;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

		
}
