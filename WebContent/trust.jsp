<%@ page language="java" import="cs5530.*"%>
<html>
<head>
<title>Rate a User</title>

</head>
<body>

	<%
		String searchAttribute = request.getParameter("searchAttribute");
		if (searchAttribute == null) {
	%>
	
	<form method="post" action="trust.jsp">
		<table >
			<thead>
				<tr>
					<th colspan="2">Do you trust this user?</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>* Username:</td>
					<td><input type="text" name="user" required /></td>
					<td>Not your :)</td>
				</tr>
				<tr>
					<td>* Trust</td>
					<td><input type="text" name="trust" required /></td>
					<td>(yes/no)</td>
				</tr>
				<tr>
					<th >You must fill textbox with (*) </th>
				</tr>
				<tr>
				<td><input type=hidden name="searchAttribute" value="Submit" /></td>
					<td><input type="submit" value="Submit" /></td>
				</tr>

			</tbody>
		</table>
	</form>
	<%
		} else {
			String login = (String) session.getAttribute("userid");
			String trust = request.getParameter("trust");
			String user = request.getParameter("user");
			Connector connector = new Connector();
			Trust t = new Trust();
			t.recordTrust(login, user, trust, connector.stmt);
			connector.closeStatement();
			connector.closeConnection();
			out.println("succeed");
		} // We are ending the braces for else here
	%>
	<BR>
	<a href="success.jsp"> Back to Home </a>
</body>