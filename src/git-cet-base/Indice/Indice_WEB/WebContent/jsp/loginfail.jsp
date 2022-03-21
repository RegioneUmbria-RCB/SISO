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
    <title>Errore di login</title>
  </head>
  <body style="padding:0;	margin:0;font-family: Arial, Verdana, sans-serif;">
  
   <table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" 
   			style="border-bottom:6px solid #4A75B5; background-color:#F3F2F2; padding:0px;">
   		<tr><td><font size='5' color="#4A75B5" style="font-weight: bold;padding-left: 5px;">Indice
di correlazione Login</font></td></tr>
   </table>
   <br>
  
    <div align="center" style="margin-top: 50px">
      <font size='4' color='red' style="font-family: Arial, Verdana, sans-serif;">
        Errore di validazione per username o password<br/><br/>
        <a href='<%= response.encodeURL("/Indice/") %>'>Riprova</a>
      </font>
    </div>
  </body>
</html>