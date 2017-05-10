
<%@ page language="java" import="cs5530.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add reservations</title>
<script LANGUAGE="javascript">
	function check_all_fields(form_obj) {
		alert(form_obj.searchAttribute.value + "='"
				+ form_obj.attributeValue.value + "'");
		if (form_obj.attributeValue.value == "") {
			alert("Search field should be nonempty");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<%
		String searchAttribute = request.getParameter("add");
		String submitAttribute = request.getParameter("submit");
		String confirmAttribute = request.getParameter("confirm");
		ArrayList<String> list = (ArrayList<String>) session.getAttribute("reservation");
		if (searchAttribute == null && submitAttribute == null && confirmAttribute == null) {
	%>
	<form method="post" action="reserve.jsp">
		<table width="50%">
			<thead>
				<tr>
					<th colspan="2">Enter Information Here</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Housing Name</td>
					<td><input type="text" name="THname" /></td>
				</tr>
				<tr>
					<td>Start Date</td>
					<td><input type="text" name="startDate"
						placeholder="YYYY-MM-DD" /></td>
				</tr>
				<tr>
					<td>End Date</td>
					<td><input type="text" name="endDate" placeholder="YYYY-MM-DD" /></td>
				</tr>
				<tr>
					<td>Cost</td>
					<td><input type="text" name="cost" /></td>
				</tr>
				<tr>
					<td><input type="submit" name="add" value="Add More Reservation" /></td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="Submit" /></td>
				</tr>

			</tbody>
		</table>
	</form>
	<%
		} else if (searchAttribute != null || submitAttribute != null) {
			//request.setAttribute("searchAttribute", null);
			String name = request.getParameter("THname");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String cost = request.getParameter("cost");

			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append(",");
			sb.append(startDate);
			sb.append(",");
			sb.append(endDate);
			sb.append(",");
			sb.append(cost);

			list.add(sb.toString());
			if (searchAttribute != null && submitAttribute == null) {
	%>
	<form method="post" action="reserve.jsp">
		<table width="50%">
			<thead>
				<tr>
					<th colspan="2">Enter Information Here</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Housing Name</td>
					<td><input type="text" name="THname" /></td>
				</tr>
				<tr>
					<td>Start Date</td>
					<td><input type="text" name="startDate"
						placeholder="YYYY-MM-DD" /></td>
				</tr>
				<tr>
					<td>End Date</td>
					<td><input type="text" name="endDate" placeholder="YYYY-MM-DD" /></td>
				</tr>
				<tr>
					<td>Cost</td>
					<td><input type="text" name="cost" /></td>
				</tr>
				<tr>
					<td><input type="submit" name="add"
						value="Add More Reservation" /></td>
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
	<form method="post" action="reserve.jsp">
		<%
		              for (String r : list) {
                                String[] values = r.split(",");
		%>

		<table>
			<thead>
				<tr>
					<th colspan="2">Review Reservation</th>
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

		</table>
		<%
			}
%>
<br><br>
<h3>Similar Users Looked At These Housings:</h3>
<%
 		        Connector con1 = new Connector();
                        THsuggestion sug = new THsuggestion();
                       String username = (String) session.getAttribute("userid");
		       ArrayList<String> recommendedTH = new ArrayList<String>();
                        for (String r : list) {
                                String[] values = r.split(",");
				String suggestedTHString = sug.suggestTH(username,values[0],con1.stmt);
				String[] suggestTHArray = suggestedTHString.split("<br>");
				for(String suggested: suggestTHArray){
				if(!recommendedTH.contains(suggested))   recommendedTH.add(suggested);
				}
			    } 
			    for(String print: recommendedTH)  out.println(print+"<br>");
			        con1.closeStatement();
                                con1.closeConnection();
				
		%>
<br>
		<input type="submit" name="confirm" value="Confirm">
	</form>

	<%
		}
		} else if (searchAttribute == null && submitAttribute == null && confirmAttribute !=null) {
			//request.setAttribute("searchAttribute", null);
			Connector con = new Connector();
			Reserve r = new Reserve();
			//THsuggestion sug = new THsuggestion();
			//String username = "a";
			String username = (String) session.getAttribute("userid");
			try {
				for (String s : list) {
					String[] split = s.split(",");
					out.println(r.reserve(username, split[1], split[2], split[0], split[3], con.stmt));
					//out.println(sug.suggestTH(username,split[0],con.stmt));
				}
				session.setAttribute("reservation", new ArrayList<String>());
				response.sendRedirect("success.jsp");
				con.closeStatement();
				con.closeConnection();
			} catch (Exception e) {
				out.println("Oops! Something went wrong. <a href='reserve.jsp'>Try again</a>");
			}
		}
	%>
	<BR>
	<a href="success.jsp"> Back to Home </a>
</body>
</html>