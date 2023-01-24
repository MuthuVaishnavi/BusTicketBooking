package com.busticket;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConnectClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ConnectClass() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name1="hello";
		Cookie cookies[]=request.getCookies();
		for(Cookie c:cookies)
		{
			if(c.getName().equals("username"))
				{
					name1=c.getValue();
				}
		}
		System.out.println(name1);
		response.sendRedirect("busdetails.html");
		
	}


}
