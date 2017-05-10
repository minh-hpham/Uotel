<%@ page language="java" import="cs5530.*"%>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String name = request.getParameter("fullname");
	String address = request.getParameter("address");
	String phone = request.getParameter("phone");

	Connector con = new Connector();
	cs5530.Registration registration = new cs5530.Registration();
		String result = registration.register(username, password, name, address, phone, con.stmt);
		session.setAttribute("userid", username);
		response.sendRedirect("index.jsp");
		con.closeStatement();
		con.closeConnection();

%>