package com.busticket;
import java.io.IOException;
import java.lang.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Merchant extends HttpServlet {
	 public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
	 String button=request.getParameter("button");
	 if(button.equals("Merchant"))
	 {
		check(request,response); 
	 }
	 else if(button.equals("addbus"))
	 {
		 System.out.println("Im at addbus");
		 addBus(request,response);
	 }
	 }


	public int addBus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{ 
		int seats = 0;
		//System.out.println("Im at addbus1");
		String busname=request.getParameter("busname");
		//System.out.println(busname);
		String type=request.getParameter("type");
		//System.out.println(type);
		String ac_nonac=request.getParameter("ac_nonac");
		//System.out.println(ac_nonac);
		int fare=Integer.parseInt(request.getParameter("fare"));
		//System.out.println(fare);
	    seats=Integer.parseInt(request.getParameter("seats"));
		System.out.println(seats);
		String n1="hello";
		Cookie cookies[]=request.getCookies();
		for(Cookie c:cookies)
		{
			if(c.getName().equals("username"))
				{
					n1=c.getValue();
				}
		}
		System.out.println(n1);
		
		Merchant m=new Merchant();
		int r1 = 0;
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
		//System.out.println(busname+n1+type+ac_nonac+fare+seats);
		condition[0]="name";
		condition[1]="busname";
		value[0]=n1;
		value[1]=busname;
		try {
			r1=new DbConnectivity().insertRow(row);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		ResultSet rs1 = null;
//		if(r1>0)
//		{
//			try {
//				rs1=m.displayBuses(condition,value);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		try {
//			while(rs1.next()) {
//				result=rs1.getInt("busid");
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return result;
	}

	public int updateBusDetails(String n1,String busname,String[] columnname,String[] value,int len) throws SQLException
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
	public int check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String table="merchant",result=null;
		String[] column=new String[5];
		column[0]="*";
	    String condition[]=new String[5];
	    String value[]=new String[5];
	    condition[0]="name";
	    Cookie cookies[]=request.getCookies();
		for(Cookie c:cookies)
		{
			if(c.getName().equals("username"))
				{
					value[0]=c.getValue();
				}
		}
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs = null;
		try {
			rs = new DbConnectivity().select(table,column,c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				result=rs.getString("name");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result.equals(value[0]))
			response.sendRedirect("merchant.html");
		{
			return 10;
		}
	}

	public int calculateProfit(String name,String busname) throws SQLException
	{
		int total=0;
		String table="ticketdetails";
		String[] column=new String[5];
		column[0]="*";
		String[] condition= {"vendor","busname"};
		String[] value= {name,busname};
		Criteria c=new Criteria();
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		while(rs.next())
		{
			total=total+(int)rs.getObject("fare");
		}
		return total;

	}

}

