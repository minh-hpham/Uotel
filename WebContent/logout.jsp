<%
session.setAttribute("userid", null);
session.setAttribute("reservation", null);
session.invalidate();
response.sendRedirect("index.jsp");
%>