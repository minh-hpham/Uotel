<%@ page language="java" import="cs5530.*" errorPage="error.jsp"%>
<html>
<head>
<title>Favorite Housing</title>

</head>
<body>

	<%
		String searchAttribute = request.getParameter("searchAttribute");
		if (searchAttribute == null) {
	%>

	Enter the name of your favorite housing:
	<form name="user_search" method=post action="favorite.jsp">
		<input type=text name="attributeValue" length=11 required> <input
			type=submit name="searchAttribute" value="Favorite">
	</form>
	<BR>
	<a href="success.jsp"> Back to Home </a>
	<%
		} else {
			String username = (String) session.getAttribute("userid");
			String name = request.getParameter("attributeValue");
			Connector connector = new Connector();
			cs5530.Favorite favorite = new cs5530.Favorite();
			String output = favorite.addFavorite(name, username, connector.stmt);
			if(output == "") output += "Invalid input(s). Please try again";
			out.println(output);
			connector.closeStatement();
			connector.closeConnection();
			out.println("<br><a href='success.jsp'>Back to Home</a>");
		} // We are ending the braces for else here
	%>

</body>