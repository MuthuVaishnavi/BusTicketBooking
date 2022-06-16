package com.busticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnectivity {

	public int insertRow(Row row) {
		String jdbcURL="jdbc:postgresql://localhost:5432/busticketbooking";
		String username="postgres";
		String password1="Vaishu123*";
		int r=0,len=0;
		try
		{
			Connection connection=DriverManager.getConnection(jdbcURL,username,password1);
			System.out.println("Connection Established");
			//String sql="insert into "+row.getTableName() +"("+row.columns.get(0).getColumnName()+","+row.columns.get(1).getColumnName()+")"+"values(?,?)";
			String sql1="insert into "+row.getTableName()+"("+row.columns.get(0).getColumnName();
			len=row.columns.size();
			for(int i=1;i<len;i++)
			{
				sql1=sql1+","+row.columns.get(i).getColumnName();
			}
			sql1=sql1+")"+"values(?";
			for(int i=1;i<len;i++)
			{
				sql1=sql1+",?";
			}
			sql1=sql1+")";
			PreparedStatement statement=connection.prepareStatement(sql1);
			/*if(row.columns.get(0).getColumnName()=="name"&&row.columns.get(1).getColumnName()=="password")
			{
				n=(String) row.columns.get(0).getValue();
				p=(String) row.columns.get(1).getValue();
			}
			statement.setString(1, n);
			statement.setString(2, p);*/
			for(int i=0;i<len;i++)
			{
				statement.setObject(i+1, row.columns.get(i).getValue());	
			}
			System.out.println(statement);
			r=statement.executeUpdate();
			connection.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		return r;

	}
	public ResultSet select(String table, String[] columns, Criteria c)
	{
		String jdbcURL="jdbc:postgresql://localhost:5432/busticketbooking";
		String username="postgres";
		String password1="Vaishu123*";
		String result=null;
		int len=0,len1=0;
		ResultSet rs=null;
		try
		{
			Connection connection=DriverManager.getConnection(jdbcURL,username,password1);
			System.out.println("Connection Established");
			//String sql1="select "+row.getVariable()+" from " +row.getTableName()+" where "+(String) row.columns.get(0).getColumnName()+"=?";
			//String sql1="select "+row.getVariable()+" from " +row.getTableName()+" where "+(String) row.columns.get(0).getColumnName()+"=?";
			//len=row.columns.size();
			/*for(int i=1;i<len;i++)
			{
				sql1=sql1+" and "+(String) row.columns.get(i).getColumnName()+"=?";
			}*/

			/*for(int i=0;i<len;i++)
			{
				statement.setObject(i+1,row.columns.get(i).getValue());
			}*/

			String sql1="select "+columns[0];
			for (int i = 0; i < 5; i++) {
				if (columns[i] != null) {
					len++;
				}
			}
			for (int i = 0; i < 13; i++) {
				if (c.condition[i] != null) {
					len1++;
				}
			}


			for(int i=1;i<len;i++)
			{
				sql1=sql1+" ,"+columns[i];
			}
			sql1=sql1+" from "+table+" where "+c.condition[0]+"=?";
			for(int i=1;i<len1;i++)
			{
				sql1=sql1+" and "+c.condition[i]+"=?";
			}
			System.out.println(sql1);
			PreparedStatement statement=connection.prepareStatement(sql1);
			
			for(int i=0;i<len1;i++)
			{
				if(c.condition[i].equals("fare")||c.condition[i].equals("seats"))
				{
					int value1=Integer.parseInt(c.value[i]);
					statement.setObject(i+1, value1);
				}
				else 
				{
					String value1=c.value[i];
					statement.setObject(i+1, value1);
				}
				
				
			}

			System.out.println(statement);
			rs =statement.executeQuery();

			connection.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		return rs;
	}
	public int update(Row old,Row change)
	{
		String jdbcURL="jdbc:postgresql://localhost:5432/busticketbooking";
		String username="postgres";
		String password1="Vaishu123*";
		int len1,len2,r=0;
		len1=old.columns.size();
		len2=change.columns.size();
		try
		{
			Connection connection=DriverManager.getConnection(jdbcURL,username,password1);
			System.out.println("Connection Established");
			String sql="update "+old.getTableName()+" set "+(String) change.columns.get(0).getColumnName()+"=?";
			for(int i=1;i<len2;i++)
			{
				sql=sql+","+(String) change.columns.get(i).getColumnName()+"= ?";
			}
			sql=sql+" where "+(String) old.columns.get(0).getColumnName()+"=?";
			for(int i=1;i<len1;i++)
			{
				sql=sql+" and "+(String) old.columns.get(i).getColumnName()+"=?";
			}
			PreparedStatement statement=connection.prepareStatement(sql);
			System.out.println(sql);
			for(int i=0;i<len2+1;i++)
			{
				if(i<len2)
				{
					statement.setObject(i+1,change.columns.get(i).getValue());	
				}
				else if(i==len2)
				{
					for(int j=0;j<len1;j++)
					{statement.setObject(len2+j+1,old.columns.get(j).getValue());}
				}
			}

			System.out.println(statement);
			r=statement.executeUpdate();
			connection.close();



		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		return r;
	}
}
