package com.busticket;
import java.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Merchant {
	public int addBus(String n1,String busname,String[] stops,String arr_time,String dept_time,int seats,int fare,String type,String ac_nonac)
	{
		int r1;
		Row row=new Row();
		row.setTableName("bus");
		row.addColumn(new Column("busname", busname));
		row.addColumn(new Column("name", n1));
		row.addColumn(new Column("stop1", stops[0]));
		row.addColumn(new Column("stop2", stops[1]));
		row.addColumn(new Column("stop3", stops[2]));
		row.addColumn(new Column("stop4", stops[3]));
		row.addColumn(new Column("stop5", stops[4]));
		row.addColumn(new Column("arrival", arr_time));
		row.addColumn(new Column("departure", dept_time));
		row.addColumn(new Column("seats", seats));
		row.addColumn(new Column("fare", fare));
		row.addColumn(new Column("type", type));
		row.addColumn(new Column("ac_nonac", ac_nonac));

		r1=new DbConnectivity().insertRow(row);
		return r1;
	}

	public int updateBusDetails(String n1,String busname,String[] columnname,String[] value,int len)
	{
		Row row1=new Row();
		Row row2=new Row();
		row1.setTableName("bus");
		row1.addColumn(new Column("name", n1));
		row1.addColumn(new Column("busname", busname));
		for(int i=0;i<len;i++)
		{
			if(columnname[i].equals("fare")|| columnname[i].equals("seats"))
			{
				int value1=Integer.parseInt(value[i]);
				row2.addColumn(new Column((String)columnname[i], value1));
			}
			else 
			{
				String value1=value[i];
				row2.addColumn(new Column((String)columnname[i], value1));
			}

		}
		int s=new DbConnectivity().update(row1,row2);
		return s;
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
	public int check(String[] condition,String[] value) throws SQLException
	{
		String table="merchant",result=null;
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

}

