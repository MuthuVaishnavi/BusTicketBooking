//$Id$
package com.busticket;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Test {
	

public static void main(String[] args) throws ParseException {
Time timeValue;
DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
String d;
System.out.println("Hello");
Scanner in=new Scanner(System.in);
d=in.next();
timeValue=new Time(formatter.parse(d).getTime());
System.out.println(timeValue);
}
}
