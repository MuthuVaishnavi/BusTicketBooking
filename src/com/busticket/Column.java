package com.busticket;
public class Column {

	public Column(String columnName, Object value) {
		this.columnName = columnName;
		this.value = value;
	}

	public String columnName;

	public Object value;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}


}
