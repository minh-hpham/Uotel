<%@ page language="java" import="cs5530.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adding New Housing</title>
<script LANGUAGE = "javascript">
  function check_fields(form_obj){
  if(form_obj.THname.value == ""||form_obj.category.value == ""||form_obj.address.value == ""){
     alert("Please fill in the Housing name, category and address. All others are optional");
     return false;
     }
     // check dates & that name is unique
   /* String uniqueTHName = form_obj.THname.value;//request.getParameter("THname");
        alert(uniqueTHName);
        cs5530.Connector con = new cs5530.Connector();
        ResultSet rs;
        rs = con.stmt.executeQuery("select * from TH WHERE name = '"+uniqueTHName+"';
        if (rs.next()) {
            alert("This TH name is already taken please select another");
            return false;
        }
        con.closeStatement();
        con.closeConnection();*/
      alert("Your TH has been added");
     return true; 
}

</script>

</head>
<body>
	<%
		String searchAttribute = request.getParameter("THname");
		String categoryAttribute = request.getParameter("category");
		String addressAttribute = request.getParameter("address");
		if (searchAttribute == null || categoryAttribute == null || addressAttribute ==null || searchAttribute == "" || categoryAttribute == "" || addressAttribute =="") {
//		out.println(searchAttribute);
	%>
	<form name= "AddTH" method="post"  onsubmit="return check_fields(this)">
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
					<td>Category</td>
					<td><input type="text" name="category" /></td>
				</tr>
				<tr>
					<td>Address</td>
					<td><input type="text" name="address" /></td>
				</tr>
				<tr>
					<td>URL</td>
					<td><input type="text" name="url" /></td>
				</tr>
				<tr>
					<td>Phone Number</td>
					<td><input type="text" name="phone" placeholder = "123456789"/></td>
				</tr>
				<tr>
					<td>Price per night</td>
					<td><input type="text" name="price" /></td>
				</tr>
				<tr>
					<td>Year Built</td>
					<td><input type="text" name="yearbuilt" placeholder = "2017"/></td>
				</tr>
	                        <tr>
                                        <td>Start Date</td>
                                        <td><input type="text" name="startDate" placeholder = "yyyy-MM-dd"/></td>
                                </tr>
                               <tr>
                                        <td>End Date</td>
                                        <td><input type="text" name="endDate" placeholder = "yyyy-MM-dd"/></td>
                                </tr>
                               <tr>
                                        <td>Keywords</td>
                                        <td><input type="text" name="keywords" placeholder = "Seperate with ',' e.g.item1, item2, item3"/></td>
                                </tr>
				<tr>
					<td><input type="submit" /></td>
				</tr>

			</tbody>
		</table>
	</form>
	<%
		} else { 
		       out.println("error");
		        String name = request.getParameter("THname");
		        if(name !=null){
			String category = request.getParameter("category");
			String address = request.getParameter("address");
			String url = request.getParameter("url");
			String prices = request.getParameter("price");
			String phone = request.getParameter("phone");
			String year_built = request.getParameter("yearbuilt");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String keywords = request.getParameter("keywords");
			String username = (String) session.getAttribute("userid");

			if(phone.isEmpty()) phone = null;
			if(url.isEmpty()) url = null;
			if(prices.isEmpty()) prices=null;
			if(year_built.isEmpty()) year_built = null;
			if(startDate.isEmpty()) startDate	= null;
			if(endDate.isEmpty())	endDate= null;
			


			cs5530.Connector con = new cs5530.Connector();
			cs5530.NewTH th = new cs5530.NewTH();
			th.createTH(username,name,category,address,url,phone,year_built,startDate,endDate,prices,keywords,con.stmt);
				response.sendRedirect("success.jsp");
			//	out.println(prices.length());
				con.closeStatement();
				con.closeConnection();
		}
}
	%>
</body>
</html>