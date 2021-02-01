<%@page import="it.roma.comune.servizi.client.CallWS"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% 
	
	//new CallWS("http://localhost:8099/ClientRomaWS/services/ClientAnagrafeRoma","qyZ2DgSN7C4LTMxYkRo7Gi7Do5GxzfCt").eseguiRicercaAnagraficaEstesa("http://10.173.2.133/pa/pri/verifiche/nva.asmx", "pellegrini", "francesco", "1975", "1976", "M");  
	new CallWS("http://localhost:8099/ClientRomaWS/services/ClientAnagrafeRoma","qyZ2DgSN7C4LTMxYkRo7Gi7Do5GxzfCt").eseguiRicercaStatoFamigliaConv("http://10.173.2.133/pa/pri/verifiche/nva.asmx", "pellegrini", "CRMXBR71A03XXXU");  
	
	
	%>
</body>
</html>