package com.busticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Bus {
	public void addStops(int busid,int len,String[] stops,String[] time) throws ParseException, SQLException
	{
		int r1=0;
		Time timeValue;
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Row row;

		for(int i=0;i<len;i++)
		{
			row=new Row();
			row.setTableName("busdetails");
			row.addColumn(new Column("busid", busid));
			row.addColumn(new Column("stopno", i+1));
			row.addColumn(new Column("stop", stops[i]));
			timeValue=new Time(formatter.parse(time[i]).getTime());
			row.addColumn(new Column("timedata", timeValue));
			r1=new DbConnectivity().insertRow(row);
		}
		if(r1>0)
		{
			System.out.println("successful");
		}
		//return r1;
	}
	public int getBusId(String name1,String busname) throws SQLException
	{
		Merchant m=new Merchant();
		int r1=0;
		String[] condition=new String[13];
		String[] value=new String[13];
		condition[0]="name";
		condition[1]="busname";
		value[0]=name1;
		value[1]=busname;
		ResultSet rs1 = null;
		rs1=m.displayBuses(condition,value);
		while(rs1.next()) {
			r1=rs1.getInt("busid");
		}
		return r1;
	}

	public ResultSet displayReview(String busname) throws SQLException
	{
		String table="reviewtable";
		String[] column=new String[5];
		column[0]="*";
		String[] condition= {"busname"};
		String[] value= {busname};
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		return rs;
	}

}
