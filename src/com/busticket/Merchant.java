package com.busticket;
import java.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Merchant {

	public int addBus(String n1,String busname,String type,String ac_nonac,int fare,int seats) throws SQLException
	{
		Merchant m=new Merchant();
		int r1;
		String[] condition=new String[13];
		String[] value=new String[13];
		int result=0;
		Row row=new Row();
		row.setTableName("bus");
		row.addColumn(new Column("busname", busname));
		row.addColumn(new Column("name", n1));
		row.addColumn(new Column("type", type));
		row.addColumn(new Column("ac_nonac", ac_nonac));
		row.addColumn(new Column("fare", fare));
		row.addColumn(new Column("seats",seats));
		condition[0]="name";
		condition[1]="busname";
		value[0]=n1;
		value[1]=busname;
		r1=new DbConnectivity().insertRow(row);
		ResultSet rs1 = null;
		if(r1>0)
		{
			rs1=m.displayBuses(condition,value);
		}
		while(rs1.next()) {
			result=rs1.getInt("busid");

		}
		return result;
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

