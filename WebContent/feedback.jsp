<%@ page language="java" import="cs5530.*"%>
<%@ page language="java" import="java.sql.*,java.util.ArrayList"%>
<html>
<head>
<%
String thname = request.getParameter("THValue");
%>
<script LANGUAGE = "javascript">
function check_fields(form_obj){
//alert(form_obj.scoreAttribute.value + "='"+form_obj.scoreValue.value+"'");
  if(form_obj.scoreValue.value == "" || form_obj.THValue.value == ""){
      alert("Score and TH name field should be nonempty");
      return false;
  }
alert("Your Feedback has been Saved")
return true;
}
</script>
<body>
<%
if(thname == null){

%>

<big>Feedback</big> <br><br>
<form name = "addFeedback" method=get onsubmit = "return check_fields(this)" action = "feedback.jsp">
	   Enter the TH name 
	   <input type=hidden name="THAttribute" value="TH">
           <input type=text name="THValue" length = 20><br><br>
      	   What would you like to say about this TH?  <br>
           <input type=hidden name="ratingAttribute" value="ratingText">
           <textarea type=text name="ratingValue" row = "30" cols="50" ></textarea>
	   <br>
	   Enter the number rating 0-10 where 10 is excellent:
	   <br>
	   <input type=hidden name="scoreAttribute" value="score">
           <input type=text name="scoreValue" length=11>
           
	   <br>
	   <input type=submit>
</form>
<br><br>
<a href="success.jsp"> Back to Home </a>
<% }
else {
  String uname = (String) session.getAttribute("userid");
  String THValue = request.getParameter("THValue");
  String ratingValue = request.getParameter("ratingValue");
  String scoreValue = request.getParameter("scoreValue");
  Connector con = new Connector(); 
  Feedback feed = new Feedback();
%>
<br> Your feedback was saved.<br>
<%
out.println(feed.recordfb(uname, THValue, ratingValue, scoreValue, con.stmt));
out.println("Feedback has been added");
response.sendRedirect("success.jsp");
}
%>
</body>

