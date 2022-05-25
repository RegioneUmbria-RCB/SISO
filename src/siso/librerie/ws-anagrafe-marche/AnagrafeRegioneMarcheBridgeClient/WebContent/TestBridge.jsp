<%@page import="java.util.*"%>
<%@page import="it.webred.cs.sociosan.ejb.client.ricercaSoggetto.RicercaSoggettoSessionBeanRemote" %>
<%@page import="it.webred.siso.ws.ricerca.dto.*" %>
<%@page import="it.webred.ct.config.model.*" %>
<%@page import="it.webred.ejb.utility.ClientUtility" %>
<%@page import="javax.naming.NamingException" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	     String id = request.getParameter("p_id");
	   
	RicercaAnagraficaParams ricAnagDTO = new RicercaAnagraficaParams("ANASAN_MARCHE", true);
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
		ricAnagDTO.setAnnoNascitaDa(Integer.getInteger(annoNascita));
	if(codiceFiscale != null && !codiceFiscale.equals(""))
		ricAnagDTO.setCf(codiceFiscale); 
	if(id!=null && !id.trim().isEmpty())
		ricAnagDTO.setIdentificativo(id);
	
	RicercaSoggettoSessionBeanRemote ricercaSoggettoBean = null;

	try {
		
		ricercaSoggettoBean = (RicercaSoggettoSessionBeanRemote)
				ClientUtility.getEjbInterface("SocioSanitario","SocioSanitario_EJB","RicercaSoggettoSessionBean");
		
	} catch (NamingException e) {}
	
	RicercaAnagraficaResult rpr = ricercaSoggettoBean.ricercaPerDatiAnagrafici(ricAnagDTO);
	
	out.println("Codice operazione " + rpr.getCodice());
	out.print("<br/>");
	out.println("Messaggio " + rpr.getMessaggio());
	out.print("<br/>");
	
	Integer i = 1;
	List<PersonaDettaglio> ListaPersoneResult  = rpr.getElencoAssistiti();
	
	if(ListaPersoneResult != null){
		out.println("Numero risultati "+ ListaPersoneResult.size());
		out.print("<br/>");
		for(PersonaDettaglio pr : ListaPersoneResult){
			out.println("*************** PERSONA (" + i + ")");
			out.print("<br/>");
			out.println("*************** assistito ID (" + pr.getIdentificativo() + ")");
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
			out.println("*************** recapito telefonico 1 (" + (pr.getTelefono() != null ? pr.getTelefono() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** recapito telefonico 2 (" + (pr.getRecaptelefonicoSecondario() != null ? pr.getRecaptelefonicoSecondario() : "ASSENTE")  + ")");
			out.print("<br/>");
			out.println("*************** indirizzo residenza (" + (pr.getIndirizzoResidenza() != null ? pr.getIndirizzoResidenza() : " ASSENTE ") + ")");
			out.print("<br/>");
			out.println("*************** CAP residenza (" + (pr.getComuneResidenza()  != null ? pr.getComuneResidenza().getCap() : " ASSENTE ") + ")");
			out.print("<br/>");
			out.println("*************** sigla prov residenza (" + (pr.getComuneResidenza()  != null ? pr.getComuneResidenza().getSiglaProv() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** sigla prov nascita (" + (pr.getComuneNascita()  != null ? pr.getComuneNascita().getSiglaProv() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** sigla prov domicilio (" + (pr.getComuneDomicilio()  != null ? pr.getComuneDomicilio().getSiglaProv() : "ASSENTE") + ")");
			
			out.print("<br/>");
			out.println("*************** indirizzo domicilio (" + (pr.getIndirizzoDomicilio() != null ? pr.getIndirizzoDomicilio() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** data nascita (" + ( pr.getDataNascita() != null ? pr.getDataNascita().toString() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** data MORTE (" + ( pr.getDataMorte()  != null ? pr.getDataMorte().toString() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** sesso (" + (pr.getSesso() != null ? pr.getSesso() : "ASSENTE") + ")");
			out.print("<br/>");
			
			out.println("*************** MEDICO CF (" + (pr.getMedicoCodiceFiscale() != null ? pr.getMedicoCodiceFiscale() : "ASSENTE") + ")");
			out.print("<br/>");
			out.println("*************** MEDICO COGNOME NOME (" + (pr.getMedicoCognome() != null ? pr.getMedicoCognome() + " "+ pr.getMedicoNome() : "ASSENTE") + ")");
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