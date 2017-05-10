<title>Home</title>
<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
You are not logged in<br/>
<a href="index.jsp">Please Login</a>
<%} else {
%>
Welcome <%=session.getAttribute("userid")%>. What would you like to do today?<br><br>
<a href='reserve.jsp'>Add Reservation</a><br>
<a href='newTH.jsp'>Record New Housing</a><br>
<a href='updateTH.jsp'>Update an Housing</a><br>
<a href='stay.jsp'>Add Visit</a><br>
<a href = 'favorite.jsp'> Add Favorite </a><br>
<a href = 'feedback.jsp'> Give Feedback </a><br>
<a href = 'usefulnessrating.jsp'> Rate a Feedback </a><br>
<a href = 'trust.jsp'> Trust Recording </a><br>
<a href = 'thBrowsing.jsp'> TH Browsing </a><br>
<a href = 'usefulFeedback.jsp'> Get Useful Feedback </a><br>
<a href= 'statistics.jsp'> Statistics </a> <br>
<a href='logout.jsp'>Log out</a>

<%
    }
%>