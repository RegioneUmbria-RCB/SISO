<div align="left"
	style="position:absolute;
	top:0;
	width:150px;
	height:70px;
	border-bottom:1px solid #000000;
	overflow:hidden;
	color: #000000;
	padding:5px;
	z-index: 100;
	right: 0">
	
  	<%String pathWebApp = request.getContextPath(); %>

<script type="text/javascript">
   function caricaUtenti() {	
    var browser=navigator.appName;
    if(browser.indexOf("Microsoft")==-1)
    	window.open("CaricaUtenti","Gestione Utenti","width=1000,height=800,top=0,scrollbars=yes,resizable=yes,status=yes");
    else 
    	window.open("CaricaUtenti","Gestione_Utenti","width=1000,height=800,top=0,scrollbars=yes,resizable=yes,status=yes");		
   }
	
  function cambiaPassword(uN) {
    var browser=navigator.appName;
    if(browser.indexOf("Microsoft") == -1)
    	window.open("<%=pathWebApp%>/SalvaUtente?mode=pwd&flPwdValida=on&userName=" + uN,"Cambia password","width=800,height=350,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");
    else
    	window.open("<%=pathWebApp%>/SalvaUtente?mode=pwd&flPwdValida=on&userName=" + uN,"Cambia_password","width=800,height=350,left=300,top=0,scrollbars=yes,resizable=yes,status=yes");	
  }
</script>

  <p align="left">UTENTE:<font color="red"> <%=request.getUserPrincipal().getName()%></font><br>
  	<a href="<%=pathWebApp%>/Logout" >Cambia utente</a> <br>
  	<a href="javascript:void(0);" onclick="cambiaPassword('<%=request.getUserPrincipal().getName()%>')">Cambia password</a> <br>
  
  	<%if ((String) request.getAttribute("gestPerm") != null){%> 
      <a href="javascript:void(0);" onclick="caricaUtenti()" >Gestione Utenti/Gruppi</a> 
    <%}%>
  </p>
</div>
