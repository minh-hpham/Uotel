<%@ page language="java" import="cs5530.*"%>
<%@ page language="java" import="java.sql.*,java.util.ArrayList"%>
<html>
<head>
<script LANGUAGE = "javascript">
function check_fields(form_obj){
   if(form_obj.THNameValue.value == "" || form_obj.THNumValue == ""){
       alert("Both fields should be nonempty");
       return false;
   }
}

</script>
</head>
<body>
</body>
<%
String THNameAttribute = request.getParameter("THNameAttribute");
String THNumAttribute = request.getParameter("THNumAttribute");
if(THNameAttribute == null || THNumAttribute == null){
%>
<h1><center>Useful Feedback</center></h1>
Please enter the TH name:
<form name = "th_search" method = get onsubmit = "return check_fields(this)" action = "usefulFeedback.jsp">
                <input type=hidden name="THNameAttribute" value="THName">
                <input type=text name="THNameValue" length=11> <br>
Please enter the number of TH feedbacks you would like to see: <br>
                <input type=hidden name="THNumAttribute" value="THNum">
                <input type=text name="THNumValue" length=11><br><br>
		<input type=submit>
</form>
<%
}
else {
%>
<%
String THNameValue = request.getParameter("THNameValue");
String THNumValue = request.getParameter("THNumValue");
cs5530.Connector con = new cs5530.Connector();
cs5530.Feedback feed = new cs5530.Feedback();
out.println("<h1><center>Useful Feedback for "+ THNameValue+" </center></h1>");
out.println(feed.usefulfb(THNameValue,THNumValue, con.stmt));
%>
<%
}
%>
<br><a href="success.jsp"> Back to Home </a>
</html>
