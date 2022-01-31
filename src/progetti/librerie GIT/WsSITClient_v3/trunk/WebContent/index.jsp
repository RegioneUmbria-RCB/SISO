<%@page import="org.tempuri.Toponomastica40Stub"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Client</title>
</head>
<body>
test WS Topo4
<br>
<%
			
Toponomastica40Stub proxy = new Toponomastica40Stub();
		
//proxy._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
// proxy._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, basicAuthentication);

Toponomastica40Stub.SitGetCivicoStory civStory = new Toponomastica40Stub.SitGetCivicoStory();
Toponomastica40Stub.SitGetCivicoStoryResponse civStoResp = new Toponomastica40Stub.SitGetCivicoStoryResponse();
%>
			
Step 1
<br>
<%
civStory.setPData1(null);
civStory.setPToken("gw4RMc7J");
%>
Step 2
<br>
<%
try{
	civStoResp= proxy.sitGetCivicoStory(civStory);
%>
Step 3
<br>
<%	
}catch(Exception e){
 
%> ERRORE:<%=e.getMessage()%><br>

Fine errore
<br>
 <%
	
}
%>
</body>
</html>