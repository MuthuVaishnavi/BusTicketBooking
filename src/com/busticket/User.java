package com.busticket;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class User extends HttpServlet{
	 public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
	     String button=request.getParameter("button");
	     if(button.equals("Signup"))
		 {signUp(request,response);}
	     else if(button.equals("Signin"))
	     {
	    	 try {
				signIn(request,response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	     }
	     else if(button.equals("update"))
	     {
	    	Update(request,response);
	     }
	     else if(button.equals("add_details"))
	     {
	    	 detail(request,response);
	     }
	        
	 }
	protected void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int r1 = 0;
		String n1= request.getParameter("username");
		String p1= request.getParameter("pwd");
		Row row=new Row();
		row.setTableName("accounts");
		row.addColumn(new Column("name", n1));
		row.addColumn(new Column("password", p1));
		try {
			System.out.println("Hi");
			r1=new DbConnectivity().insertRow(row);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(r1>0)
		{
			System.out.println("Success");
			response.sendRedirect("index.html");
		}
		//return r1;
	}
	public void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		/*Row row1=new Row();
		String[] s=new String[10];
		row1.setTableName("accounts");
		row1.setVariable("password");
		row1.addColumn(new Column("name", n1));
		row1.addColumn(new Column("password", p1));
		s=new DbConnectivity().select(row1);
		int f=PwdCompare.Compare(s,p1);
		return f;*/
		
		System.out.println("Hi, Im at signin");
		String n1= request.getParameter("username");
		String p1= request.getParameter("pwd");
		System.out.println("Hii, Im at signin");
		String table="accounts",result=null;
		String[] column=new String[5];
		column[0]="name";
		String[] condition=new String[13];
		String[] value=new String[13];
		for(int i=0;i<10;i++)
		{
			condition[i]=null;
			value[i]=null;
		}
		Criteria c=new Criteria();
		condition[0]="password";
		value[0]=p1;
		c.setCondition(condition);
		c.setValue(value);
		ResultSet rs=new DbConnectivity().select(table,column,c);
		while(rs.next())
		{result=rs.getString("name");}
		System.out.println(result);
		if(result.equals(n1))
		{
			System.out.println("Success");
			Cookie cookie = new Cookie("username",n1);
			response.addCookie(cookie);
			response.sendRedirect("features.html");
			//return 1;
		}
			
		else
		{
			response.sendRedirect("index.html");
			//return 0;
			
		}

	}
	public int Update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String n1= request.getParameter("uname");
		String p1= request.getParameter("pass1");
		String p2=request.getParameter("pass2");
		Row row1=new Row();
		Row row2=new Row();
		row1.setTableName("accounts");
		row1.addColumn(new Column("name", n1));
		row1.addColumn(new Column("password", p1));
		row2.addColumn(new Column("password", p2));
		int s = 0;
		try {
			s = new DbConnectivity().update(row1,row2);
			response.sendRedirect("features.html");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;

	}
	public int detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int ag= Integer.parseInt(request.getParameter("age"));
		String emailid=request.getParameter("email");
		String phoneno=request.getParameter("phone_no");
		String name1=null;
		String identity= request.getParameter("profile");
		String gen=request.getParameter("gender");
		Cookie cookies[]=request.getCookies();
		for(Cookie c:cookies)
		{
			if(c.getName().equals("username"))
				{
					name1=c.getValue();
				}
		}
		
		
		Row row=new Row();
		if(identity.equals("merchant"))
		{
			row.setTableName("merchant");
		}
		else if(identity.equals("customer"))
		{
			row.setTableName("customer");
		}
		row.addColumn(new Column("name", name1));
		row.addColumn(new Column("age", ag));
		row.addColumn(new Column("gender", gen));
		row.addColumn(new Column("email", emailid));
		row.addColumn(new Column("phone", phoneno));
		int s = 0;
		try {
			s = new DbConnectivity().insertRow(row);
			response.sendRedirect("features.html");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;

	}

}
