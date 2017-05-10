<%@ page language="java" import="cs5530.*"%>
<html>
<head>
<title>Rate Feedback's Usefulness</title>

</head>
<body>

	<%
		String searchAttribute = request.getParameter("searchAttribute");
		if (searchAttribute == null) {
	%>

	<form method="post" action="usefulnessrating.jsp">
		<table >
			<thead>
				<tr>
					<th colspan="2">Enter Information Here</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>* Housing Name</td>
					<td><input type="text" name="THname" required /></td>
				</tr>
				<tr>
					<td>* Contributor:</td>
					<td><input type="text" name="user" required /></td>
					<td>Not your :)</td>
				</tr>
				<tr>
					<td>* Rating</td>
					<td><input type="text" name="rating" required /></td>
					<td>0-2 (0=useless,1=useful,2=very useful)</td>
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
			String name = request.getParameter("THname");
			String rating = request.getParameter("rating");
			String user = request.getParameter("user");
			Connector connector = new Connector();
			UsefulnessRating ur = new UsefulnessRating();
			ur.AddUsefulnessRating(name, user, login, rating, connector.stmt);
			connector.closeStatement();
			connector.closeConnection();
			out.println("succeed");
		} // We are ending the braces for else here
	%>
	<BR>
	<a href="success.jsp"> Back to Home </a>
</body>