<%@page import="it.webred.WsSitClient.dto.RequestDTO"%>
<%@page import="it.webred.WsSitClient.WsSITClient"%>
<%@page import="it.webred.WsSitClient.dto.ResponseDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="" method="post" >
<table>
<tr>
<td>
	Parametro
</td>
<td>
	Valore
</td>
</tr>

<tr>
<td>
	Parametro
</td>
<td>
	Valore
</td>
</tr>

</table>



</form>

<%
ResponseDTO resp = null;
try
{
	WsSITClient wsSitCli = new WsSITClient();

	RequestDTO rdto= new RequestDTO();
	rdto.setToken("gw4RMc7J");
	rdto.setTokenAuth("9f6a18cf97c855a8c5d8023d2127e54c");

	resp = wsSitCli.sitGetVieFtopo(rdto);
	//resp= wsSitCli.sitGetInfo(rdto);
//	wsSitCli.getToken();
 	if (resp!=null && resp.getErrCode()!=null){
 		System.out.println("ErrCode="+resp.getErrCode());
 		System.out.println("<br>");
 		System.out.println("ErrMess="+resp.getErrDescrizione());
 	}else{
 		System.out.println("Num. Vie Trovate="+resp.getListaVie().size());
 	}
}catch (Exception e)
{
	e.printStackTrace();	
}
%>


</body>
</html>