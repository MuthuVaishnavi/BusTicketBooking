package com.busticket;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
	public int check1(String[] condition,String[] value) throws SQLException
	{
		String table="customer",result=null;
		String[] column=new String[5];
		column[0]="*";
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		while(rs.next()) {
			result=rs.getString("name");

		}
		if(result.equals(value[0]))
		{
			return 10;
		}
		return -1;
	}
	public ResultSet displayBuses(String[] condition,String[] value) throws SQLException
	{
		String table="bus";
		String[] column=new String[5];
		column[0]="*";
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		return rs;
	}
	public int bookTicket(String busname,int tickets) throws SQLException
	{
		String table="bus";
		int result=0;
		String[] condition=new String[13];
		String[] value=new String[13];
		String[] column=new String[5];
		column[0]="seats";
		condition[0]="busname";
		value[0]=busname;
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		while(rs.next())
		{
			result=rs.getInt("seats");
		}
		result=result-tickets;
		Row row1=new Row();
		Row row2=new Row();
		row1.setTableName("bus");
		row1.addColumn(new Column("busname", busname));
		row2.addColumn(new Column("seats", result));
		int s=new DbConnectivity().update(row1,row2);
		return s;

	}

}
