<%@ page language="java" import="cs5530.*"%>
<%@ page language="java" import="java.sql.*,java.util.ArrayList"%>
<html>
<head>
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


<script LANGUAGE = "javascript">
functioncheck_fields(form_obj){
alert("priceStart: "+ form_obj.THNumValue.value);
   return true;
}
</script>
</head>
<body>
<% 
String check = request.getParameter("priceStartValue");
 if(check == null){
%>
Enter TH information <br><br>
<form name = user_search" method = get onsubmit= "return check_fields(this)" action = "thBrowsing.jsp">
      
      <table width="30%">
      <thead> 
      <tr><th colspan="2">Enter Information Here</th> </tr>
      </thead>
      <tbody>
      <tr>
      <td> Price Start:</td>
         <td>
         <input type=text name="priceStartValue" length=11></td>
	</tr> 
	<tr><td> Price End:</td>
	  <td><input type=text name="priceEndValue" length=11></td>
	  </tr>
	  <tr><td> Enter a state or city:</td>
	  <td><input type=text name="addressValue" length=11></td>
         </tr>
	 <tr><td> Category</td>
         <td><input type=text name="categoryValue" length=11></td>
	 </tr>
	 <tr> <td>Keywords: Seperate by a comma</td>
	 <td><input type=text name="keywordValue" length=11><br></td>
	</tr></tbody> </table>
	 <br><br>
	 How would you like the information sorted?


      <br>
      
      	Price <input type=radio name="OptionValue" length=11 value="Price">
     <br>  Feedback Score<input type=radio name="OptionValue" length=11 value="feedbackScore">
     <br>  Trusted Feedback Score<input type=radio name="OptionValue" length=11 value="trustedFeedbackScore">
      <br><br>
        <input type=submit>
</form> <br>
<a href="success.jsp"> Back to Home </a>
<%
}
else {
String priceStart = request.getParameter("priceStartValue");

String priceEnd	  = request.getParameter("priceEndValue");
String address = request.getParameter("addressValue");
String category  = request.getParameter("categoryValue");
String keywordsString=request.getParameter("keywordValue");
String[] array = keywordsString.split(",");
ArrayList<String> keywords = new ArrayList<String>();
if(!keywordsString.isEmpty()){
for(String i: array){
   keywords.add(i);
}
}
String optionValue = request.getParameter("OptionValue");
 cs5530.Connector connector = new cs5530.Connector();
 THBrowsing browse = new THBrowsing();
if(optionValue.equals("Price"))
out.println(browse.THBrowse(priceStart, priceEnd,address,category,keywords,"1", connector.stmt)); 
if(optionValue.equals("feedbackScore"))
out.println(browse.THBrowse(priceStart, priceEnd,address,category,keywords,"2", connector.stmt));
if(optionValue.equals("trustedFeedbackScore"))
out.println(browse.THBrowse(priceStart, priceEnd,address,category,keywords,"3", connector.stmt));

connector.closeStatement();
 connector.closeConnection();
%>
<a href="success.jsp"> Back to Home </a>
<%
}
%>
</body>

</html>