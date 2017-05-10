<%@ page language="java" import="cs5530.*,java.util.ArrayList"%>
<html>
<head>
<title>Visit Recording</title>
</head>
<body>

	<%
		String searchAttribute = request.getParameter("add");
		String submitAttribute = request.getParameter("submit");
		String confirmAttribute = request.getParameter("confirm");
		ArrayList<String> list = (ArrayList<String>) session.getAttribute("stay");
		if (searchAttribute == null && submitAttribute == null && confirmAttribute == null) {
	%>

	Enter Information Here:
	<form method=post action="stay.jsp">
		<table>
			<tbody>
				<tr>
					<td>Housing Name</td>
					<td><input type="text" name="THname" required /></td>
				</tr>
				<tr>
					<td>Start Date</td>
					<td><input type="text" name="startDate" 
						placeholder="YYYY-MM-DD" required/></td>
				</tr>
				<tr>
					<td>End Date</td>
					<td><input type="text" name="endDate" placeholder="YYYY-MM-DD" required/></td>
				</tr>
				<tr>
					<td>Cost</td>
					<td><input type="text" name="cost" required/></td>
				</tr>
				<tr>
					<td>People</td>
					<td><input type="text" name="people" required/></td>
				</tr>
				<tr>
					<td><input type="submit" name="add" value="Add More Visits" /></td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="Submit" /></td>
				</tr>
			</tbody>
		</table>
	</form>
	<BR>
	<%
		} else if (searchAttribute != null || submitAttribute != null) {
			//request.setAttribute("searchAttribute", null);
			String name = request.getParameter("THname");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String cost = request.getParameter("cost");
			String people = request.getParameter("people");

			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append(",");
			sb.append(startDate);
			sb.append(",");
			sb.append(endDate);
			sb.append(",");
			sb.append(cost);
			sb.append(",");
			sb.append(people);

			list.add(sb.toString());
			if (searchAttribute != null && submitAttribute == null) {
	%>
	Enter Information Here:
	<form name="stays" method=post action="stay.jsp">
		<table>
			<tbody>
				<tr>
					<td>Housing Name</td>
					<td><input type="text" name="THname" required/></td>
				</tr>
				<tr>
					<td>Start Date</td>
					<td><input type="text" name="startDate" 
						placeholder="YYYY-MM-DD" required/></td>
				</tr>
				<tr>
					<td>End Date</td>
					<td><input type="text" name="endDate"  placeholder="YYYY-MM-DD" required/></td>
				</tr>
				<tr>
					<td>Cost</td>
					<td><input type="text" name="cost" required/></td>
				</tr>
				<tr>
					<td>People</td>
					<td><input type="text" name="people" required/></td>
				</tr>
				<tr>
					<td><input type="submit" name="add" value="Add More Visits" /></td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="Submit" /></td>
				</tr>
			</tbody>
		</table>
	</form>
	<%
		} else if (searchAttribute == null && submitAttribute != null) {
	%>
	<form method="post" action="stay.jsp">
		<%
			for (String r : list) {
						String[] values = r.split(",");
		%>
		<table>
			<thead>
				<tr>
					<th colspan="2">Review Visit</th>
				</tr>
			</thead>
			<tr>
				<td>Housing's name</td>
				<td><%=values[0]%></td>
			</tr>
			<tr>
				<td>From</td>
				<td><%=values[1]%></td>
			</tr>
			<tr>
				<td>End</td>
				<td><%=values[2]%></td>
			</tr>
			<tr>
				<td>Cost</td>
				<td><%=values[3]%></td>
			</tr>
			<tr>
				<td>People</td>
				<td><%=values[4]%></td>
			</tr>
		</table>
		<br>
		<%
			}
		%>
		<input type="submit" name="confirm" value="Confirm">
	</form>
	<%
		}
		} else if (searchAttribute == null && submitAttribute == null && confirmAttribute != null) {
			Connector con = new Connector();
			Stays r = new Stays();
			String username = (String) session.getAttribute("userid");
			try {
				for (String s : list) {
					String[] split = s.split(",");
					out.println(
							r.recordVisit(username, split[0], split[1], split[2], split[3], split[4], con.stmt));
				}
				session.setAttribute("stay", new ArrayList<String>());
				response.sendRedirect("success.jsp");
				con.closeStatement();
				con.closeConnection();
			} catch (Exception e) {
				out.println("Oops! Something went wrong. <a href='reserve.jsp'>Try again</a>");
			}
		}
	%>

</body>