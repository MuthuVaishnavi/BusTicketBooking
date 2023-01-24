package com.busticket;

import java.util.ArrayList;

public class Row {

	public ArrayList<Column> columns = new ArrayList<Column>();

	public String tableName;

	public String variable;


	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public ArrayList<Column> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void addColumn(Column column) {
		this.columns.add(column);
	}


}
