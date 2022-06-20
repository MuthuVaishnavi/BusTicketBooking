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
	public ResultSet[] displayBuses(String[] condition,String[] value,String[] condition1,String[] value1) throws SQLException
	{
		Customer cus=new Customer();
		ResultSet rs= null;
		ResultSet rs1[]=new ResultSet[10];
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


		/*for(int i=1;i<=len;i++)
	    {
	    	if(result[i]==2|| result[i]>2)
	    	{
	    		rs=cus.disp(i);
	    	}
	    }*/

		int k=1;
		for(int i=1;i<=len;i++)
		{
			if(result[i]==2|| result[i]>2)
			{
				rs=cus.disp(i,condition,value);
				if(rs!=null)
				{rs1[k]=disp(i,condition,value);
				k++;
				}

			}
		}



		/*Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);*/
		return rs1;
	}

	private ResultSet disp(int i,String[] condition,String[] value) {
		String table="bus inner join busdetails on bus.busid=busdetails.busid ";
		String[] column=new String[5];
		int len1=0;
		column[0]="*";
		Criteria c=new Criteria();
		String[] condition1=new String[5];
		String[] value1=new String[5];

		condition1[0]="bus.busid";
		value1[0]=Integer.toString(i);

		int k=1;
		for(int i1=0;i1<3;i1++)
		{
			if(condition[i1].equals("type")&&(!value[i1].equals("n")))
			{
				condition1[k]="bus.type";
				value1[k]=value[i1];
				k++;
			}
			else if(condition[i1].equals("ac_nonac")&&(!value[i1].equals("n")))
			{
				condition1[k]="bus.ac_nonac";
				value1[k]=value[i1];
				k++;
			}
			else if(condition[i1].equals("fare")&&(!value[i1].equals("n")))
			{
				condition1[k]="bus.fare between 0 and ";
				value1[k]=value[i1];
				k++;
			}
			else
			{
				continue;
			}
		}

		condition1[k]="bus.seats !=";
		value1[k]="0";

		c.setCondition(condition1);
		c.setValue(value1);
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

	public int bookTicket(String busname,int tickets,String source,String dest,String customer) throws SQLException
	{
		String table="bus";
		int result=0,s=0;
		String[] condition=new String[13];
		String[] value=new String[13];
		String[] column=new String[5];
		String name=null;
		int fare=0;
		column[0]="*";
		condition[0]="busname";
		value[0]=busname;
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		while(rs.next())
		{
			result=rs.getInt("seats");
			name=rs.getString("name");
			fare=rs.getInt("fare");
		}
		if(result!=0)
		{
			result=result-tickets;
			Row row1=new Row();
			Row row2=new Row();
			Row row3=new Row();
			row1.setTableName("bus");
			row1.addColumn(new Column("busname", busname));
			row2.addColumn(new Column("seats", result));
			s=new DbConnectivity().update(row1,row2);
			row3.setTableName("ticketdetails");
			row3.addColumn(new Column("vendor", name));
			row3.addColumn(new Column("busname", busname));
			row3.addColumn(new Column("source", source));
			row3.addColumn(new Column("destination", dest));
			row3.addColumn(new Column("ticket_count", tickets));
			row3.addColumn(new Column("fare", fare*tickets));
			row3.addColumn(new Column("customer_name", customer));
			s=new DbConnectivity().insertRow(row3);
		}
		return fare*tickets;


	}

	public ResultSet showBookedTickets(String name)
	{
		String table="ticketdetails";
		String[] column=new String[5];
		column[0]="*";
		String[] condition= {"customer_name"};
		String[] value= {name};
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		return rs;
	}

	public int Review(String name,String busname,String message)
	{
		int r1;
		Row row=new Row();
		row.setTableName("reviewtable");
		row.addColumn(new Column("name", name));
		row.addColumn(new Column("busname", busname));
		row.addColumn(new Column("review", message));
		r1=new DbConnectivity().insertRow(row);
		return r1;
	}
}
