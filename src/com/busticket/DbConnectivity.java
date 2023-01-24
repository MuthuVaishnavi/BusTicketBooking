package com.busticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnectivity {

	public int insertRow(Row row) throws SQLException {
		String jdbcURL="jdbc:postgresql://localhost:5433/busticketbooking";
		String username="root";
		String password1="postgres";
		int r=0,len=0;
		System.out.println(row.getTableName());
		Connection connection = null;
		try
		{
			connection=DriverManager.getConnection(jdbcURL,username,password1);
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
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		finally
		{
			connection.close();
		}
		return r;

	}
	public ResultSet select(String table, String[] columns, Criteria c) throws SQLException
	{
		String jdbcURL="jdbc:postgresql://localhost:5433/busticketbooking";
		String username="root";
		String password1="postgres";
		String result=null;
		int len=0,len1=0;
		ResultSet rs=null;
		Connection connection=null;
		try
		{
		    connection=DriverManager.getConnection(jdbcURL,username,password1);
			//System.out.println("Connection Established");
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

			for (int i = 0; i < c.condition.length; i++) {
				if (c.condition[i] != null) {
					len1++;
				}
			}
			/*System.out.println(len1);
			System.out.println(c.condition.length);
			for(int i=0;i<c.condition.length;i++)
			{
				if (c.value[i] != null) {
					System.out.println(c.value[i]);
				}
			}*/


			for(int i=1;i<len;i++)
			{
				sql1=sql1+" ,"+columns[i];
			}
			sql1=sql1+" from "+table;
			//System.out.println(len1);

			if(len1!=0)
			{sql1=sql1+" where "+c.condition[0]+"=?";}

			for(int i=1;i<len1;i++)
			{
				if(c.condition[i]!=null)
				{
					if(c.condition[i].equals("bus.fare between 0 and ")||c.condition[i].equals("bus.seats !="))
					{
						sql1=sql1+" and "+c.condition[i]+"?";	
					}
					else
					{sql1=sql1+" and "+c.condition[i]+"=?";}
				}
			}
			System.out.println(sql1);
			PreparedStatement statement=connection.prepareStatement(sql1);

			for(int i=0;i<len1;i++)
			{
				if(c.value[i]!=null)
				{
					if(c.condition[i].equals("bus.fare between 0 and ")||c.condition[i].equals("bus.seats !=")||c.condition[i].equals("bus.busid")||c.condition[i].equals("busid"))
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
			}
			
			/*for(int i=0;i<len1;i++)
			{
				statement.setObject(i+1, c.value[i]);
			}*/
			

			System.out.println(statement);
			rs =statement.executeQuery();

			
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		finally
		{
			connection.close();
		}
		return rs;
	}
	public int update(Row old,Row change) throws SQLException
	{
		String jdbcURL="jdbc:postgresql://localhost:5433/busticketbooking";
		String username="root";
		String password1="postgres";
		int len1,len2,r=0;
		len1=old.columns.size();
		len2=change.columns.size();
		Connection connection=null;
		try
		{
			connection=DriverManager.getConnection(jdbcURL,username,password1);
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
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		finally
		{
			connection.close();
		}
		return r;
	}
}
