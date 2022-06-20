package com.busticket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	public int SignUp(String n1,String p1) throws SQLException
	{
		int r1;
		Row row=new Row();
		row.setTableName("accounts");
		row.addColumn(new Column("name", n1));
		row.addColumn(new Column("password", p1));
		r1=new DbConnectivity().insertRow(row);
		return r1;
	}
	public int SignIn(String n1,String p1) throws SQLException
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
		if(result.equals(n1))
		{
			return 1;
		}
		else
		{
			return 0;
		}

	}
	public int Update(String n1,String p1,String p2) throws SQLException
	{
		Row row1=new Row();
		Row row2=new Row();
		row1.setTableName("accounts");
		row1.addColumn(new Column("name", n1));
		row1.addColumn(new Column("password", p1));
		row2.addColumn(new Column("password", p2));
		int s=new DbConnectivity().update(row1,row2);
		return s;

	}
	public int detail(String name1,int ag,String gen,String emailid,String phoneno,int identity) throws SQLException
	{
		Row row=new Row();
		if(identity==1)
		{
			row.setTableName("merchant");
		}
		else if(identity==2)
		{
			row.setTableName("customer");
		}
		row.addColumn(new Column("name", name1));
		row.addColumn(new Column("age", ag));
		row.addColumn(new Column("gender", gen));
		row.addColumn(new Column("email", emailid));
		row.addColumn(new Column("phone", phoneno));
		int s=new DbConnectivity().insertRow(row);
		return s;

	}

}
