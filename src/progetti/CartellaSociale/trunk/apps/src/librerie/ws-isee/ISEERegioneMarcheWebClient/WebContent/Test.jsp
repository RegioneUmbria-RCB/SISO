<%@page import="it.marche.regione.pddnica.client.*"%>
<%@page import="it.marche.regione.pddnica.client.RicercaCF"%>
<%@page import="it.marche.regione.pddnica.client.DatiRichiestaISEE"%>

 <%@page import="it.siso.isee.obj.*"%>
<%@page import="java.util.ArrayList"%>
 

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Collegamento ISEE</title>
</head>
<body>
	<%
	
	String codiceFiscale = request.getParameter("p_codiceFiscale");
	/*      String filtro = request.getParameter("p_filtro");	
	     String cognome = request.getParameter("p_cognome");	
	     String nome = request.getParameter("p_nome");
	     String sesso = request.getParameter("p_sesso");	
	     String annoNascita = request.getParameter("p_annoNascita");
	      */
	   
	/*  ISEEClient client = new ISEEClient("http://pddnica.regione.marche.it/PdDRegioneMarche/PD/ERDISMarche_INPS_SPCISEE2015/ConsultazioneAttestazione");
	 client.getConsultazioneAttestazione("", null, ""); */
	 RicercaCF ricercaCF = new RicercaCF();
	 ricercaCF.setCodiceFiscale("LSSGNE97L08A271X");
	 ricercaCF.setDataValidita("2020-09-09T21:59:00Z");
	 ricercaCF.setPrestazioneDaErogare("A1.19");
	 ricercaCF.setProtocolloDomandaEnteErogatore("ID Domanda: 126575");
	 ricercaCF.setStatodomandaPrestazione("Da Erogare");
	 
	 DatiRichiestaISEE datiRichiesta = new DatiRichiestaISEE();
	 datiRichiesta.setEndPoint("http://pddnica.regione.marche.it/PdDRegioneMarche/PD/ERDISMarche_INPS_SPCISEE2015/ConsultazioneDichiarazione");
	 datiRichiesta.setAction("");
	 SicurezzaIdentificazioneMittente sicurezzaIdentificazioneMittente = new SicurezzaIdentificazioneMittente();
	 sicurezzaIdentificazioneMittente.setCfOperatore("CCCRRT57P09L500V");
	 sicurezzaIdentificazioneMittente.setCodiceEnte("DSU00081");
	 sicurezzaIdentificazioneMittente.setCodiceUfficio("DirStud");
	 
	 datiRichiesta.setMittente(sicurezzaIdentificazioneMittente);
	 ISEESoapClient client = new ISEESoapClient(datiRichiesta);
	 client.consultaDichiarazioneCF(ricercaCF); 
 
	%>  
</body>
</html>