<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" href="favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link href="./css/newstyle.css" rel="stylesheet" type="text/css" />
    <title>Login</title>
    <script>
    	function apriInformativa() {
        	var message = "";
        	if (document.forms[0].j_username.value == '') {
        		if (document.forms[0].j_password.value == '') {
                	message = "Inserire UserName e Password"; 
            	} else {
            		message = "Inserire UserName";
            	}
        	} else if (document.forms[0].j_password.value == '') {
            	message = "Inserire Password"; 
        	}
        	if (message != "") {
            	alert(message);
            	return;
        	}
    		document.getElementById('informativa').style.display='';
    	}

    	function doLogout() {
    		document.location="<%=request.getContextPath()%>/Logout";
    	}
    </script>
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
    <br><br>
  
    <form action='j_security_check' method='post'>
    	<div align="center">      
          <table cellpadding="5" style="width: 200px;">
            <tr>
              <td align="right">Utente:</td>
              <td><input type='text' name='j_username' style="width:150px"></td>
            </tr>
            <tr>
              <td align="right">Password:</td>
              <td><div style="float: left;"><input type='password' name='j_password' size='8' style="width:150px"></div></td>
            </tr>
          </table>
          <br>          
          <input type='button' value='Login' onclick="javascript:apriInformativa();">
        </div>
        <div align="center" id="informativa" style="display: none;">
          <br>          
          <br>
          <table cellpadding="5" style="width: 50%;">
          	<tr>
              <td>
              	<table style="width: 100%; border-width: 0px;">
              		<tr>
              			<td style="width: 100%; text-align: center; border-width: 0px; font-weight: bold;">
              				INFORMATIVA PER GLI UTENTI
              			</td>
              		</tr>
              		<tr>
              			<td style="width: 100%; text-align: justify; border-width: 0px;">
              				<p>L'accesso al sistema e la consultazione delle informazioni in esso contenute possono avvenire esclusivamente per finalità 
              				istituzionali e per ragioni strettamente connesse alla propria attività di servizio.</p>
              				<p>L'operatore, procedendo nel collegamento, dichiara di conoscere le vigenti norme a tutela della riservatezza delle informazioni
              				 e dei dati contenuti nel sistema, e di essere pienamente consapevole delle responsabilità connesse all'accesso ai dati illegittimo 
              				 o non autorizzato o non determinato da ragioni di servizio, e alla comunicazione dei dati o al loro utilizzo indebito.</p>
              				 <p>Ogni operazione effettuata viene memorizzata dal sistema informativo.</p>
              			</td>
              		</tr>
              	</table>
              </td>
            </tr>                     
          </table>
          <br>
          <input type='submit' value='Accetta'>
      	  <input type='button' value='Rifiuta' onclick="javascript:doLogout();">
        </div>
     </form>

  </body>
</html>