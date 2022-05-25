<%@page import="it.webred.siso.ws.client.anag.marche.client.PersonaResult"%>
<%@page import="it.webred.siso.ws.client.agag.marche.dto.RicercaAnagraficaDTO"%>
<%@page import="it.webred.siso.ws.client.anag.marche.client.RicercaAnagraficaClient"%>
<%@page import="it.webred.siso.ws.client.anag.marche.client.RicercaPersonaResult"%>
<%@page import="java.util.List"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	     String filtro = request.getParameter("p_filtro");	
	     String cognome = request.getParameter("p_cognome");	
	     String nome = request.getParameter("p_nome");
	     String sesso = request.getParameter("p_sesso");	
	     String annoNascita = request.getParameter("p_annoNascita");
	     String codiceFiscale = request.getParameter("p_codiceFiscale");
	     
	     String jksPath = "C:\\Load\\SISO\\certificati\\anan_san\\cacerts.jks";
	     String jksPwd = "changeit";
	       
	RicercaAnagraficaDTO ricAnagDTO = new RicercaAnagraficaDTO();
	if(filtro != null && !filtro.equals("") ){
		ricAnagDTO.setFiltro(filtro);
	}
	if(cognome != null && !cognome.equals("") ) 
		ricAnagDTO.setCognome(cognome);
	if(nome != null && !nome.equals(""))
		ricAnagDTO.setNome(nome);
	if(sesso != null && !sesso.equals(""))
		ricAnagDTO.setSesso(sesso);
	if(annoNascita != null && !annoNascita.equals(""))
		ricAnagDTO.setAnnoNascita(annoNascita);
	if(codiceFiscale != null && !codiceFiscale.equals(""))
		ricAnagDTO.setCf(codiceFiscale); 
	
	RicercaAnagraficaClient rc = new RicercaAnagraficaClient(jksPath,jksPwd,true); 
	RicercaPersonaResult rpr = rc.ricercaPerDatiAnagrafici(ricAnagDTO);
	
	out.println("Codice operazione " + rpr.getCodice());
	out.print("<br/>");
	out.println("Messaggio " + rpr.getMessaggio());
	out.print("<br/>");
	Integer i = 1;
	List<PersonaResult> ListaPersoneResult  = rpr.getElencoAssisiti();
	if(ListaPersoneResult != null){
		out.println("Numero risultati "+ ListaPersoneResult.size());
		out.print("<br/>");
		for(PersonaResult pr : ListaPersoneResult){
			out.println("*************** PERSONA (" + i + ")");
			out.print("<br/>");
			out.println("*************** assistito ID (" + pr.getAssistitoId() + ")");
			out.print("<br/>");
			out.println("*************** CF (" + pr.getCodfisc() + ")");
			out.print("<br/>");
			out.println("*************** Stato Civile (" + (pr.getStatoCivile() != null ?  pr.getStatoCivile() : "ASSENTE ") + ")");
			out.print("<br/>");
			out.println("*************** Documento Sanitario N° (" + (pr.getDocumentoSanitario() != null ?  pr.getDocumentoSanitario() : "ASSENTE ") + ")");
			out.print("<br/>");
			out.println("*************** Documento Sanitario Scadenza (" + (pr.getDocumentoSanitarioScadenza() != null ?  pr.getDocumentoSanitarioScadenza() : "ASSENTE ") + ")");
			out.print("<br/>");
			out.println("*************** nome (" + pr.getNome()  + ")");
			out.print("<br/>");
			out.println("*************** cognome (" + pr.getCognome() + ")");
			out.print("<br/>");
			out.println("*************** recapito telefonico 1 (" + (pr.getRecaptelefonico() != null ? pr.getRecaptelefonico() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** recapito telefonico 2 (" + (pr.getRecaptelefonicoSecondario() != null ? pr.getRecaptelefonicoSecondario() : "ASSENTE")  + ")");
			out.print("<br/>");
			out.println("*************** indirizzo residenza (" + (pr.getIndirizzoResidenza() != null ? pr.getIndirizzoResidenza() : " ASSENTE ") + ")");
			out.print("<br/>");
			out.println("*************** CAP residenza (" + (pr.getCapRes()   != null ? pr.getCapRes() : " ASSENTE ") + ")");
			out.print("<br/>");
			out.println("*************** sigla prov residenza (" + (pr.getSiglaProvRes() != null ? pr.getSiglaProvRes() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** sigla prov nascita (" + (pr.getSiglaProvNas() != null ? pr.getSiglaProvNas() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** sigla prov domicilio (" + (pr.getSiglaProvDomicilio() != null ? pr.getSiglaProvDomicilio() : "ASSENTE") + ")");
			
			out.print("<br/>");
			out.println("*************** indirizzo domicilio (" + (pr.getIndirizzoDomicilio() != null ? pr.getIndirizzoDomicilio() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** data nascita (" + ( pr.getDataNascita() != null ? pr.getDataNascita().toString() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** data MORTE (" + ( pr.getDataMor()  != null ? pr.getDataMor().toString() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** sesso (" + (pr.getSesso() != null ? pr.getSesso() : "ASSENTE") + ")");
			out.print("<br/>");
			
			out.println("*************** MEDICO CF (" + (pr.getMedicoCodiceFiscale() != null ? pr.getMedicoCodiceFiscale() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** MEDICO COGNOME NOME (" + (pr.getMedicoCognomeNome() != null ? pr.getMedicoCognomeNome() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** MEDICO DATA SCELTA (" + (pr.getMedicoDataScelta() != null ? pr.getMedicoDataScelta().toString() : "ASSENTE") + ")");
			out.print("<br/>");
			out.print("----------------------------------------------------------------------------------------------------------------------------------");
			out.print("<br/>");
			i++;
		}
	}
	%>
</body>
</html>