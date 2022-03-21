<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body style="padding:0;	margin:0;font-family: Arial, Verdana, sans-serif;">

   <table width="100%" cellpadding="0" cellspacing="0" align="center" border="0" height="50" 
   			style="border-bottom:6px solid #4A75B5; background-color:#F3F2F2; padding:0px;">
   		<tr><td><font size='5' color="#4A75B5" style="font-weight: bold;padding-left: 5px;">Indice
di correlazione Login</font></td></tr>
   </table>
   <br><br>

<div align="center">
<form action='j_security_check' method='post' style="width: 600px">

<table bgcolor="#F3F2F2" border="1px" bordercolor="#C0C0C0"
	style="border-collapse: collapse; font-size: 10pt;">
	<tr>
		<td align="right" style="padding: 5px">UserName:</td>
		<td style="padding: 5px"><input type='text' name='j_username'></td>
	</tr>
	<tr>
		<td align="right" style="padding: 5px">Password:</td>
		<td style="padding: 5px">
		<div style="float: left;"><input type='password'
			name='j_password' size='8'></div>
		</td>
	</tr>
</table>
<br>
<input type='submit' value='Login' class="login"
	style="color: white; background-color: #4A75B5; border-bottom: 1px solid #606060; border-right: 1px solid #606060; border-top: 1px solid #c0c0c0; border-left: 1px solid #c0c0c0;">
</form>
</div>
</body>
</html>