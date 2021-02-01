<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
  <title>TEST JSP</title>
</head>
  	<%String pathWebApp = request.getContextPath(); %>

<body>
  <div id="centrecontent"> 
    <br>
  
    <form name="form1" method="POST" action="<%=pathWebApp%>/RicercaRette" >

      <table class="griglia"  cellpadding="0" cellspacing="0">
        <tr class="header" >
        	<td width="150 px" colspan="3" >ESEGUI RICERCA RETTE</td>
        </tr>
        <tr class="header">
          <td width="150 px">
                    Ente <input type="text" name="ente"/>
                    Codice Fiscale <input type="text" name="codFisc"/>
          </td>
        </tr>
        <tr>
        <td width="100 px">
         <input type="submit" name="submit" value="RicercaRetteSoggetto"/>
         </td>
        </tr>
      </table>
    </form>
  </div>

  <div id="centrecontent"> 
    <br>
  
    <form name="form2" method="POST" action="<%=pathWebApp%>/RicercaRette" >

      <table class="griglia"  cellpadding="0" cellspacing="0">
        <tr class="header" >
        	<td width="150 px" colspan="3" >ESEGUI RICERCA RETTE</td>
        </tr>
        <tr class="header">
          <td width="150 px">
                    Ente <input type="text" name="ente"/>
                    Codice Bolletta <input type="text" name="codBolletta"/>
          </td>
        </tr>
        <tr>
        <td width="100 px">
         <input type="submit" name="submit" value="RicercaRettaPerPagare"/>
         </td>
        </tr>
      </table>
    </form>
  </div>


</body>
</html>
