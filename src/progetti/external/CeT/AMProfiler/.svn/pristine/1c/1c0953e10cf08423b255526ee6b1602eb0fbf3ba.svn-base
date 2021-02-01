<html>
<head><TITLE>Pagina di errore</TITLE>
<script>
function hiddeunhide()
{
	
	if(document.getElementById('errore').style.display == 'none')
	{
		document.getElementById('errore').style.display = '';
	}
	else
	{
		document.getElementById('errore').style.display = 'none';	
	}
}
</script>
</head>
<body>
    
    <div align="center">
 <strong><FONT color="red"> Nel processare la richiesta si sono verificati degli errori.<br>
    Se il problema persiste contattare l'amministratore.<br>
 </FONT></strong><a href="javascript:history.back(-1);">Torna indietro</a>
   </div>
    <br><br><br>
    <TABLE width="80%" align="center" >
	<TR >
		<td bgcolor="#66CC66">
           Errore:<a href="javascript:hiddeunhide();">
		<%
		out.println(request.getAttribute("ErrorMessage"));
		%></a>    
		</td>
	</TR>
	<tr id="errore">
		<td ><PRE><%out.println(request.getAttribute("ErrorTrace"));%></PRE>
		</td>
	</tr>
</TABLE><script>
document.getElementById('errore').style.display = 'none';
</script>

<jsp:include page="footer.jsp" />
</body>
</html>
