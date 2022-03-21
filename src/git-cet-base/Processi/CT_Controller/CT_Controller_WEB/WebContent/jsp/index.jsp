<%

	double val = Math.random();
	String url = "jsp/protected/home.faces?" + val;
	
	response.sendRedirect(url);
%>