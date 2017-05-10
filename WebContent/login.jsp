<%@ page language="java" import="cs5530.*"%>
<%@ page language="java" import="java.sql.*,java.util.ArrayList"%>

<%
	String userid = request.getParameter("uname");
	String pwd = request.getParameter("pass");

	cs5530.Connector con = new cs5530.Connector();
	ResultSet rs;
	rs = con.stmt.executeQuery("select * from Users where login='" + userid + "' and password='" + pwd + "'");
	if (rs.next()) {
		session.setAttribute("userid", userid);
		session.setAttribute("reservation", new ArrayList<String>());
		session.setAttribute("stay", new ArrayList<String>());
		//out.println("welcome " + userid);
		//out.println("<a href='logout.jsp'>Log out</a>");
		response.sendRedirect("success.jsp");
		con.closeStatement();
		con.closeConnection();
	} else {
		out.println("Invalid password <a href='index.jsp'>try again</a>");
	}
%>