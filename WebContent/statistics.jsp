<%@ page language="java" import="cs5530.*"%>
<%@ page language="java" import="java.sql.*,java.util.ArrayList"%>
<html>
<head>
<script LANGUAGE = "javascript">

function check_all_fields(form_obj){
        if( form_obj.THNumValue.value == ""){
                alert("Search field should be nonempty");
                return false;
        }
        if(form_obj.OptionValue.value == ""){
                alert("Search field should be nonempty");
                return false;
        }
        return true;
}
</script>
</head>
<body>
<%
String optionAttribute = request.getParameter("OptionAttribute");
String thNumAttribute = request.getParameter("THNumAttribute");
if(optionAttribute == null || thNumAttribute == null){

%>
Please enter the number of TH's you want to see
<br>
        <form name="user_search" method=get onsubmit="return check_all_fields(this)" action="statistics.jsp">
                <input type=hidden name="THNumAttribute" value="THNum">
                <input type=text name="THNumValue" length=11>
		<br><br>
		Most Popular TH:
                <input type=hidden name="OptionAttribute" value="mostPopular">
                <input type=radio name="OptionValue" length=11 value="mostPopular">
		<br>
		Most Expensive TH
	        <input type=hidden name="OptionAttribute" value="mostExpensive">
	        <input type=radio name="OptionValue"  length=11 value="mostExpensive">		
		<br>
		Most Highly Rated PH
		<input type=hidden name="OptionAttribute" value="mostHighlyRated">
                <input type=radio name="OptionValue" length=11 value="mostHighlyRated">
                <input type=submit>
        </form>
<br><br>
<%
}
else {
String thNumValue = request.getParameter("THNumValue");
String optionChosen = request.getParameter("OptionValue");

cs5530.Connector con = new cs5530.Connector();
cs5530.Statistic stat = new cs5530.Statistic();
%>
<%
if(optionChosen.equals("mostPopular")) 
	out.println(stat.mostPopular(thNumValue,con.stmt));
else if (optionChosen.equals("mostExpensive"))
      out.println(stat.mostExpensive(thNumValue,con.stmt));
else if (optionChosen.equals("mostHighlyRated"))
     out.println(stat.highlyRated(thNumValue,con.stmt));
 %>



<%
        con.closeStatement();
        con.closeConnection();
}
%>
<br> <a href = "statistics.jsp"> New query </a>
<br>
<a href="success.jsp"> Back to Home </a>
</body>
</html>