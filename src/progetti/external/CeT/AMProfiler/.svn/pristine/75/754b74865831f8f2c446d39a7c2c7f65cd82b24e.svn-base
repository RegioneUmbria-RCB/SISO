<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
<title>PROGETTO GIT</title>

<style type="text/css">
body,div,table {
	font: normal 11px Verdana, Arial, sans-serif;
}

.searchTable {
	background-color:#7C7C7C; 
}

tr:nth-of-type(odd) {
    background-color:#EAF0F8;
}
tr:nth-of-type(even) {
    background-color:#F3F2F2;
}
</style>

</head>
<body>

	<CENTER	>
	<div >
		<h2>Funzionalità rilasciate</h2>
		<br/>
		<table class="searchTable" style="width: 100%;" cellpadding="5" >
			<col width="12%">
	  		<col width="88%">
			<c:forEach items="${lstFeedMsg}" var="ril" varStatus="status">
<%-- 				<c:if test="${ril.version eq param.ver}"> --%>
					<tr >
						<th align="right">Versione: </th>
						<td style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;"><c:out value="${ril.version}" /></td>
					</tr>
					<tr >
						<th align="right">Titolo: </th>
						<td style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;"><c:out value="${ril.title}" /></td>
					</tr>
					<tr >
						<th align="right">Descrizione: </th>
						<td style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;white-space:normal;"><c:out value="${ril.description}" /></td>
					</tr>
					<tr >
						<th align="right">IdTask: </th>
						<td style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;"><c:out value="${ril.guid}" /></td>
					</tr>
					<tr >	
						<th align="right">Data pubblicazione: </th>
						<td style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;"><c:out value="${ril.pubDate}" /></td>
					</tr>
					<c:if test="${(!empty ril.link) && (ril.link ne '')  && (ril.link ne 'link')}">
						<tr >	
							<th align="right">Allegato: </th>
							<td style="padding:1px;font-size:10pt;font-style:normal;padding-bottom:1px;">
								<a href="<c:out value="${ril.link}" />" target="_blank">Allegato</a>
							</td>
						</tr>
					</c:if>
					<tr style="border-collapse: collapse;border: 0;">
						<th align="right" style="background-color: white;" > </th>
						<td style="padding:1px;font-size:5pt;font-style:normal;padding-bottom:1px;background-color: white;">
							<c:out value=" " />
						</td>
					</tr>
<%-- 				</c:if> --%>
			</c:forEach>
		</table>
	</div>
	</CENTER>



</body>
</html>