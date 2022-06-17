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
	public ResultSet displayBuses(String[] condition,String[] value,String[] condition1,String[] value1) throws SQLException
	{
		Customer cus=new Customer();
		ResultSet rs= null;
		int[] stops=new int[10];
		int[] result=new int[10];
		int[] buses=new int[10];
		String table="bus";
		String[] column=new String[5];
		column[0]="*";
	    int len=cus.getId("bus",null,0,"busid");
	   /* for(int i=0;i<len;i++)
	    {
	    	stops[i]=cus.getId("busdetails","busid",i+1,"stopno");
	    }*/
	    for(int i=1;i<=len;i++)
	    {
	    	result[i]=cus.checkBusStops(i,value1);
	    }
	    
	    
	    for(int i=1;i<=len;i++)
	    {
	    	if(result[i]==2|| result[i]>2)
	    	{
	    		rs=cus.disp(i);
	    	}
	    }
	    
		/*Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);*/
		return rs;
	}
	
	private ResultSet disp(int i) {
		String table="bus inner join busdetails on bus.busid=busdetails.busid ";
		String[] column=new String[5];
		column[0]="*";
		Criteria c=new Criteria();
		String[] condition=new String[1];
		String[] value=new String[1];
		condition[0]="bus.busid";
		value[0]=Integer.toString(i);
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		return rs;
		
		
	}
	public int getId(String tablename,String condition1,int value1,String col) throws SQLException
	{
		int[] result=new int[10];
		int len=0,flag=0;
		String table=tablename;
		String[] column=new String[5];
		column[0]="*";
		Criteria c=new Criteria();
		String[] condition=new String[1];
		String[] value=new String[1];
		if(value1!=0)
		{condition[0]=condition1;
		value[0]=Integer.toString(value1);}
		else
		{
			condition[0]=null;
			value[0]=null;
		}
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		int i=0;
		while(rs.next()) {
			result[i]=rs.getInt(col);
			i++;
		}
		len=result[0];
		for(i=1;i<10;i++)
		{
			if(result[i-1]<result[i])
			{
				len=result[i];
			}
			
		}
		return len;
		
	}
	
	public int checkBusStops(int busid,String[] stops) throws SQLException
	{
		String table="busdetails";
		int flag=0;
		String[] column=new String[5];
		column[0]="*";
		String[] condition= {"busid","stop"};
		String[] value= {Integer.toString(busid),stops[0]};
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		while(rs.next()) {				
			if(rs.getInt("busid")==busid)
			{flag++;}
		}
		value[1]=stops[1];
		rs=new DbConnectivity().select(table,column,c);
		while(rs.next()) {
			if(rs.getInt("busid")==busid)
			{flag++;}
		}
		return flag;
		
		
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
