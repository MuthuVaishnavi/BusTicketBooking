package com.busticket;

import java.util.Scanner;
import java.sql.*;
import java.text.ParseException;

public class UiPage {
	String name1,pwd,newpwd,gen,emailid,phoneno,arr_time,dept_time,type,ac_nonac,busname,source,dest,message;
	Scanner input= new Scanner(System.in);
	Scanner input1= new Scanner(System.in);
	String ans[]=new String[50];
	User user=new User();
	int flag=0,ag=0,identity=0,fare=0,seats=0,tickets=0,len=0;
	String stops[]=new String[5];
	Merchant merchant=new Merchant();
	Customer customer=new Customer();
	Bus bus=new Bus();

	public void getDetails() throws SQLException 
	{
		System.out.println("Please Enter your details\n");
		System.out.println("Enter your name\n");
		name1=input.next();
		System.out.println("Enter the password\n");
		pwd=input.next();
		flag=user.SignUp(name1, pwd);
		if(flag>0)
		{System.out.println("Your account is successfully created\n");}

	}
	public int fetchDetails() throws SQLException {
		System.out.println("Username\n");
		name1 = input.next();
		System.out.println("Password\n");
		pwd = input.next();

		flag= user.SignIn(name1, pwd);

		if (flag > 0) {
			System.out.println("Login Successful");
		} else {
			System.out.println("Login Failed");
		}
		return flag;

	}
	public void updateData() throws SQLException
	{
		System.out.println("Enter username");
		name1=input.next();
		System.out.println("Enter old password");
		pwd=input.next();
		System.out.println("Enter new password");
		newpwd=input.next();
		flag=user.Update(name1,pwd,newpwd);
		if(flag>0)
		{
			System.out.println("Updated Successfully");
		}
		else
		{
			System.out.println("Please enter the correct details");
		}
	}
	public void additionalData(String n1) throws SQLException
	{
		System.out.println("Welcome!! Please fill the following details!\n");
		System.out.println("Please enter\n 1.Merchant\n 2.Customer");
		identity=input.nextInt();
		System.out.println("Age\n");
		ag=input.nextInt();
		System.out.println("Gender\n");
		gen=input.next();
		System.out.println("Email id\n");
		emailid=input.next();
		System.out.println("Phone number\n");
		phoneno=input.next();
		flag=user.detail(n1,ag,gen,emailid,phoneno,identity);	
		if(flag>0)
		{
			System.out.println("Details added successfully\n");
		}
		else
		{
			System.out.println("Failed to update\n");
		}
	}
	public int busDetails(String n1) throws SQLException
	{
		System.out.println("Please Enter the bus details\n");
		System.out.println("Bus name\n");
		busname=input.next();
		System.out.println("sleeper or semisleeper\n");
		type=input.next();
		System.out.println("ac or nonac");
		ac_nonac=input.next();


		/*for(int i=0;i<5;i++)
		{
			System.out.println("Please enter stop "+(i+1));
			stops[i]=input.next();
		}
		System.out.println("Please enter the departure time\n");
		dept_time=input.next();
		System.out.println("Please enter the arrival time\n");
		arr_time=input.next();*/


		System.out.println("Number of seats available\n");
		seats=input.nextInt();

		System.out.println("Fare of the journey\n");
		fare=input.nextInt();

		flag=merchant.addBus(n1,busname,type,ac_nonac,fare,seats);
		if(flag>0)
		{
			System.out.println("Inserted Successfully");
		}
		else
		{
			System.out.println("Insertion failed");
		}
		return flag;

	}
	public void updateBus(String n1) throws SQLException
	{
		int len=0;
		String[] value=new String[13];
		String[] condition=new String[13];
		System.out.println("Enter the bus name\n");
		busname=input.next();
		System.out.println("Enter the number of conditions\n");
		len=input.nextInt();
		for(int i=0;i<len;i++)
		{
			System.out.println("Enter the condition\n");
			condition[i]=input.next();
			System.out.println("Enter the value\n");
			value[i]=input.next();
		}
		flag=merchant.updateBusDetails(n1,busname,condition,value,len);
		if(flag>0)
		{
			System.out.println("Updated Successfully");
		}
		else
		{
			System.out.println("Updation failed");
		}

	}
	public void displayBus() throws SQLException
	{
		int len=0;
		ResultSet rs1[]=new ResultSet[10];

		String[] value=new String[13];
		String[] condition1=new String[13];
		String[] value1=new String[13];
		String[] columnname= {"name","busname","seats","fare","type","ac_nonac","stop","stopno","timedata"};
		String[] col= {"stop","stopno","timedata"};
		String[] condition= {"type","ac_nonac","fare"};


		condition1[0]="stop";
		condition1[1]="stop";

		System.out.println("Enter the source");
		value1[0]=input.next();
		System.out.println("Enter the destination");
		value1[1]=input.next();

		System.out.println("Do you want to add any additional condition\n If not, Enter n");
		for(int i=0;i<3;i++)
		{
			System.out.println(condition[i]);
			value[i]=input.next();
		}

		rs1=customer.displayBuses(condition,value,condition1,value1);	

		/*if(person.equals("merchant"))
		{
			//rs=merchant.displayBuses(condition,value);

		}
		}
		else
		{
			rs1=customer.displayBuses(condition,value,condition1,value1);	
		}
		 */

		for(int i=0;i<10;i++)
		{
			if(rs1[i]!=null)
			{
				len++;
			}

		}
		System.out.println(len);

		for(int i=1;i<=len;i++)
		{
			while(rs1[i].next())
			{
				for(int k=0;k<9;k++)
				{
					System.out.println(columnname[k]+":");
					System.out.println(rs1[i].getObject(columnname[k])+"\n");
				}
			}
		}
		/*while(rs1[i].next())
		{
			for(int k=0;k<3;k++)
			{
				System.out.println(col[k]+":");
				System.out.println(rs.getObject(col[k])+"\n");
			}
		}*/





	}
	public int toCheckMerchant(String n1) throws SQLException
	{
		String[] condition=new String[13];
		String[] value=new String[13];
		condition[0]="name";
		value[0]=n1;
		return flag=merchant.check(condition,value);

	}

	public int toCheckCustomer(String n1) throws SQLException
	{
		String[] condition=new String[13];
		String[] value=new String[13];
		condition[0]="name";
		value[0]=n1;
		return flag=customer.check1(condition,value);

	}

	public void ticketDetails(String n) throws SQLException
	{

		System.out.println("Enter the bus name");
		busname=input.next();
		System.out.println("Enter the soure");
		source=input.next();
		System.out.println("Enter the destination");
		dest=input.next();
		System.out.println("Enter the number of tickets");
		tickets=input.nextInt();
		flag=customer.bookTicket(busname,tickets,source,dest,n);
		if(flag>0)
		{
			System.out.println(tickets+" tickets Booked Successfully and your total amount is "+flag);
		}
		else
		{
			System.out.println("Can't Book tickets");
		}

	}
	public void addBusStops(int id) throws ParseException, SQLException
	{
		String[] stops=new String[15];
		String[] time=new String[15];
		System.out.println("Enter the number of stops");
		len=input.nextInt();
		for(int i=0;i<len;i++)
		{
			System.out.println("Enter the stop name");
			stops[i]=input.next();
			System.out.println("Enter the time at which the bus arrives the stop");
			time[i]=input.next();
		}
		bus.addStops(id,len,stops,time);

	}
	public int getId(String name1,String busname) throws SQLException 
	{
		flag=bus.getBusId(name1,busname);
		return flag;

	}
	public void showTicket(String n) throws SQLException
	{
		ResultSet rs=customer.showBookedTickets(n);
		while(rs.next())
		{
			System.out.println("Busname :");
			System.out.println(rs.getObject("busname"));
			System.out.println("From :");
			System.out.println(rs.getObject("source"));
			System.out.println("To :");
			System.out.println(rs.getObject("destination"));
			System.out.println("Tickets :");
			System.out.println(rs.getObject("ticket_count"));
			System.out.println("Total amount :");
			System.out.println(rs.getObject("fare"));

		}
	}

	public void showProfit(String n) throws SQLException
	{
		System.out.println("Enter the busname");
		busname=input.next();
		int amt=merchant.calculateProfit(n,busname);
		System.out.println("Your total profit is Rs."+amt);
	}

	public void postReview(String name) throws SQLException
	{
		System.out.println("Enter the busname");
		busname=input.next();
		System.out.println("Post your review :");
		message=input1.nextLine();

		int res=customer.Review(name,busname,message);
		if(res>1)
		{
			System.out.println("Your review is posted successfully!!");
		}

	}

	public void showReview() throws SQLException
	{
		System.out.println("Enter the bus name\n");
		busname=input.next();
		ResultSet rs=bus.displayReview(busname);
		while(rs.next())
		{System.out.println(rs.getObject("review"));}

	}




	public static void main(String[] args) throws SQLException, ParseException {
		int sign,num,choice=0,option=0,confirm1=0,confirm2=0,f=0;
		String b;
		Scanner in=new Scanner(System.in);
		UiPage ui=new UiPage();
		System.out.println("Please Enter your option\n 1.sign up \n 2.sign in\n 3.Change Password\n");
		sign=in.nextInt();
		if(sign==1)
		{
			ui.getDetails();
		}
		else if(sign==2)
		{
			num=ui.fetchDetails();
			if(num>0)
			{
				System.out.println("1.Complete your profile\n2.To continue\n");	
				choice=in.nextInt();
				if(choice==1)
				{
					ui.additionalData(ui.name1);
				}
				else if(choice==2)
				{
					System.out.println("1.Merchant\n2.Customer\n");
					ui.identity=in.nextInt();
					if(ui.identity==1)
					{confirm1=ui.toCheckMerchant(ui.name1);}
					else
					{confirm2=ui.toCheckCustomer(ui.name1);}
					if(ui.identity==1&&confirm1>0)
					{
						System.out.println("Please Enter the option\n 1.Add Bus\n 2.Update Bus detail\n 3.Show list of bus\n 4.Show Profit\n 5.Check Review");
						option=in.nextInt();
						if(option==1)
						{
							f=ui.busDetails(ui.name1);
							ui.addBusStops(f);
						}
						else if(option==2)
						{
							ui.updateBus(ui.name1);
						}
						else if(option==3)
						{
							ui.displayBus();
						}
						else if(option==4)
						{
							ui.showProfit(ui.name1);
						}
						else if(option==5)
						{
							ui.showReview();
						}
					}
					else if(ui.identity==1&&confirm1<0)
					{
						System.out.println("Access Denied");	
					}
					else if(ui.identity==2&&confirm2>0)
					{
						System.out.println("Please Enter the option\n 1.Show Buses\n 2.Book tickets\n 3.Show ticket details\n 4.Post your review\n 5.Show Review");
						option=in.nextInt();
						if(option==1)
						{
							ui.displayBus();
						}
						else if(option==2) 
						{
							ui.ticketDetails(ui.name1);	
						}
						else if(option==3)
						{
							ui.showTicket(ui.name1);
						}
						else if(option==4)
						{
							ui.postReview(ui.name1);
						}
					}
					else if(ui.identity==2&&confirm2<0)
					{
						System.out.println("Please Create a account\n");
					}

				}
			}

		}
		else if(sign==3)
		{
			ui.updateData();
		}

		in.close();
	}

}
