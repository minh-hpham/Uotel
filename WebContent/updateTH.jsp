<%@ page language="java" import="cs5530.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Updating Housing</title>
<script LANGUAGE = "javascript">
function check_all_fields(form_obj){
         if (form_obj.THname.value == "") {
  	  alert ("Fill in the Housing Name Attribute");
	  return false;
	  }
alert ("TH was Updated");
return true; 
}
</script>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}
</style>

</head>

<body>
        <%  
                String searchAttribute = request.getParameter("THname");
                if ((searchAttribute == null || searchAttribute.equals(""))) {
        %>
        <form name = "updateTH" method="post" action="" onsubmit = "return check_all_fields(this)">
                <table width="50%">
                        <thead>
                                <tr>
                                        <th colspan="2">Enter Only the Values You Want Updated</th>
                                </tr>
                        </thead>
                        <tbody>
                                <tr>
                                        <td>Housing Name</td>
                                        <td><input style = "width: 200px;"type="text" name="THname"  /></td>
                                </tr>
                                 <tr>
                                        <td>Updated Housing Name</td>
                                        <td><input type="text" style = "width: 200px;" name="updatedName" /></td>
                                </tr>
                                <tr>
                                        <td>Category</td>
                                        <td><input type="text" name="category" style = "width: 200px;"/></td>
                                </tr>
                                <tr>
                                        <td>Address</td>
                                        <td><input type="text" name="address" style = "width: 200px;"/></td>
                                </tr>
                                <tr>
                                        <td>URL</td>
                                        <td><input type="text" name="url" style = "width: 200px;"/></td>
                                </tr>
                                <tr>
                                        <td>Phone Number</td>
                                        <td><input type="text" name="phone" placeholder = "123456789" style = "width: 200px;"/></td>
                                </tr>
                                <tr>
                                        <td>Price per night</td>
                                        <td><input type="text" name="price" style = "width: 200px;"/></td>
                                </tr>
				 <tr>
                                        <td>Year Built</td>
                                        <td><input type="text" name="yearbuilt" placeholder = "2017" style = "width: 200px;"/></td>
                                </tr>
                                <tr>
                                        <td>Start Date</td>
                                        <td><input type="text" name="startDate" placeholder = "yyyy-MM-dd" style = "width: 200px;"/></td>
                                </tr>
                               <tr>
                                        <td>End Date</td>
                                        <td><input type="text" name="endDate" placeholder = "yyyy-MM-dd" style = "width: 200px;"/></td>
                                </tr>
                               <tr>
                                        <td>Add Keywords</td>
                                        <td><input type="text" name="addKeywords" placeholder = "Seperate with ',' e.g.1,2,3" style = "width: 200px;"/></td>
                                </tr>
                               <tr>
                                        <td>Remove Keywords</td>
                                        <td><input type="text" name="removeKeywords" placeholder = "Seperate with ',' e.g.1,2,3" style= "width: 200px;"/></td>
                                </tr>
                                <tr>
                                       <td colspan="2"><center><input type="submit"></center></td>
                                </tr>

                        </tbody>
                </table>
       </form>
<br>
    <%          String username1 = (String) session.getAttribute("userid");
		Connector con1 = new Connector();
                NewTH th1 = new NewTH();
               String getAllTHString = (th1.getAllTH(username1,con1.stmt));
	       String[] getAllTHArray = getAllTHString.split("<br>");
	       //for(String s : getAllTHArray)
	       //   out.println(s+"<br>");
		String getAllTHNames = (th1.getAllTHName(username1,con1.stmt));
		String [] getAllTHNamesArray =getAllTHNames.split(",");
%>

<br><br>
<h3>Your Current Housings</h3>
		<table width="50%">
		  <thead>
		    <tr><td>Housing Name</td>
		    <td>Category</td>
		    <td>URL</td>
		    <td>Address</td>
		    <td>Phone</td>
		    <td>Price</td>
		    <td>Year Built</td>
		    <td>Keywords</td></tr>
		  </thead> 
		  <tbody>
		  
<%
		  String tobePrinted = "";
		   for(int i = 0; i<getAllTHNamesArray.length; i++){
			  // out.println(getAllTHArray[i]);
			  tobePrinted= tobePrinted + "<tr>";
			   String[] getAllTHSubArray = getAllTHArray[i].split(",,,");
	                   for(int j =0; j < getAllTHSubArray.length; j++)
			      tobePrinted =tobePrinted+"<td>"+getAllTHSubArray[j] +"</td>";
			   
			   tobePrinted = tobePrinted+ "<td>"+th1.getTHKeywords(getAllTHSubArray[0], con1.stmt)+"</td>";
			   tobePrinted = tobePrinted+ "</tr>";
		   }
		   out.println(tobePrinted);
		con1.closeStatement();
                con1.closeConnection(); %>
      </tbody></table>
 
 <%
                } else {
                        String name = request.getParameter("THname");
			String updatedName = request.getParameter("updatedName");
                        String category = request.getParameter("category");
                        String address = request.getParameter("address");
                        String url = request.getParameter("url");
                        String price = request.getParameter("price");
                        String phone = request.getParameter("phone");
                        String startDate = request.getParameter("startDate");
                        String endDate = request.getParameter("endDate");
                        String yearbuilt = request.getParameter("yearbuilt");
                        String addKeywords = request.getParameter("addKeywords");
      	                String removeKeywords = request.getParameter("removeKeywords");
		        String username = (String) session.getAttribute("userid");
                        String confirmAttribute = request.getParameter("submit");

                        Connector con = new Connector();
                        NewTH th = new NewTH();
                        try {
                                String result = th.updateTH(username, name,updatedName,category,address,url,phone,yearbuilt,startDate,endDate, price,addKeywords,removeKeywords, con.stmt);
				out.println(result);
                               response.sendRedirect("success.jsp");
                                con.closeStatement();
                                con.closeConnection();
                        } catch (Exception e) {
                                out.println("Oops! Something went wrong. <a href='reserve.jsp'>Try again</a>");
                        }
               }
        %>
</body>
</html>

