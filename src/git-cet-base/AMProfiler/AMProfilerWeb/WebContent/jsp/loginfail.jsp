<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>  	
    <title>Login - Errore!</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
  
    <table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" style="border-bottom:6px solid #4A75B5;">    		
  		<tr>
 			<td style="text-align: center;">
 				<img src="img/git.jpg" width="165" height="43" style="padding: 5px 10px 5px 0px; vertical-align: middle;"/>
 				<font size='5' color="#4A75B5" style="font-weight: bold; vertical-align: middle;">Login</font>
 			</td>
 		</tr>
    </table>
    <br>
  
    <div align="center" style="margin-top: 50px">
      <font size='4' color='red' >
        Errore di validazione per utente o password<br/><br/>
        <a href='<%= response.encodeURL("/AMProfiler/index.html") %>'>Riprova</a>
      </font>
    </div>
  </body>
</html>