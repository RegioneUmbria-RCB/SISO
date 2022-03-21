<%@page import="it.roma.comune.servizi.test.ClientAnagrafeRoma"%>
<%@page import="it.roma.comune.servizi.dto.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  
 
	<%
	   RicercaResult rr = null;
	   String tipoRicerca =  request.getParameter("tipoRicerca");
	   String cognome = request.getParameter("p_cognome");	
	   String nome = request.getParameter("p_nome");
	   String sesso = request.getParameter("p_sesso");	
	   String annoNascita = request.getParameter("p_annoNascita");
	   String annoIniziale = request.getParameter("p_annoIniziale");
	   String annoFinale = request.getParameter("p_annoFinale");
	   String meseNascita = request.getParameter("p_meseNascita");
	   String giornoNascita = request.getParameter("p_giornoNascita");
	   String codiceFiscale = request.getParameter("p_codiceFiscale");
	   String codiceIndividuale = request.getParameter("p_individuale");
	   String URL  = "https://www.servizi.comune.roma.it/pa/papublic/pri/verifiche/nva.asmx";
	   //String URL  = "http://10.173.2.133/pa/pri/verifiche/nva.asmx";
	   System.out.println("inizio jsp TEST ANAGRAFICA ROMA ...: " );
			if(tipoRicerca.equals("BASE")){
				//https://wwww.servizi.comune.roma.it/pa/papublic/pri/verifiche/nva.asmx
				  rr = new ClientAnagrafeRoma().verificaDatiAnagrafici("qyZ2DgSN7C4LTMxYkRo7Gi7Do5GxzfCt", URL, cognome, nome,sesso.toUpperCase(), annoNascita, meseNascita, giornoNascita, codiceFiscale);   
				 
			}else if(tipoRicerca.equals("ESTESA")){
				// https://wwww.servizi.comune.roma.it/pa/papublic/pri/verifiche/nva.asmx
				rr = new ClientAnagrafeRoma().eseguiRicercaAnagraficaEstesa("qyZ2DgSN7C4LTMxYkRo7Gi7Do5GxzfCt", URL, cognome, nome,sesso, annoIniziale, annoFinale );   
				
			}else if(tipoRicerca.equals("STATO_FAMIGLIA")){
				  rr = new ClientAnagrafeRoma().eseguiRicercaStatoFamigliaConv("qyZ2DgSN7C4LTMxYkRo7Gi7Do5GxzfCt", "http://10.173.2.133/pa/pri/verifiche/nva.asmx", codiceIndividuale, codiceFiscale);   
			}
			else if(tipoRicerca.equals("CODICE_INDIVIDUALE")){
				System.out.println("CHIAMATA PER CODICE INDIVIDUALE ...: " );
				// https://wwww.servizi.comune.roma.it/pa/papublic/pri/verifiche/nva.asmx
				rr = new ClientAnagrafeRoma().ricercaPerCodiceIndividuale("qyZ2DgSN7C4LTMxYkRo7Gi7Do5GxzfCt", URL, codiceIndividuale );   
				System.out.println("FINE CHIAMATA PER CODICE INDIVIDUALE ...: " );
			}
			if(rr == null)
					System.out.println("RICERCA RESULT NULLO !!!");
			if(rr!= null){
				
				out.println("Esito : " + rr.getEsito());
				out.println("Numero record individuati " +  rr.getElencoPersona() == null ? "Assenti! " : rr.getElencoPersona().length);
				out.println("Famiglia ? " +  rr.getFamiglia() == null ? "Assente! " : "STATO FAMIGLIA PRESENTE");
				
						for(int i=0; i<rr.getElencoPersona().length; i++){
							if(rr.getElencoPersona()[i].getDatiAnagrafeRoma() != null){
								DatiAnagrafeRoma dar = rr.getElencoPersona()[i].getDatiAnagrafeRoma();
								//out.println("Codice Famiglia : " + dar.getCodiceFamiglia());
								out.println("Codice Individuale : " + dar.getCodiceIndividuale());
								out.println("Descrizione : " + dar.getDescrizione());
								out.println("Flag Presenza in Famiglia : " + dar.getFlagPresenzaInFamiglia());
								out.println("Flag Vivo residente : " + dar.getFlagVivoResidente());
								out.println("Residente : " + dar.getResidente());
								out.println("Vivo : " + dar.getVivo());
							}
							if(rr.getElencoPersona()[i].getDatiDiNascita() != null){
								Nascita nasc = rr.getElencoPersona()[i].getDatiDiNascita();
								out.println("Anno Nascita : " + nasc.getAnno());
								out.println("Codice Comune ISTAT : " + nasc.getCodiceComuneISTAT());
								out.println("Codice Provincia ISTAT : " + nasc.getCodiceProvinciaISTAT());
								out.println("Codice Stato ISTAT : " + nasc.getCodiceStatoISTAT());
								out.println("Giorno Nascita : " + nasc.getGiorno());
								out.println("Mese Nascita : " + nasc.getMese());
								out.println("Comune : " + nasc.getNomeComune());
								out.println("Stato : " + nasc.getNomeStato());
								out.println("Sigla Provincia : " + nasc.getSiglaProvincia() );
								
								
							}
							if(rr.getElencoPersona()[i].getDatiIndirizzo() != null){
								DatiIndirizzo ind = rr.getElencoPersona()[i].getDatiIndirizzo();
								out.println("Indirizzo Breve : " + ind.getIndirizzoBreve());
								out.println("Interno : " + ind.getInterno());
								out.println("Municipio : " + ind.getMunicipio());
								out.println("Numero : " + ind.getNumero());
								out.println("Piano : " + ind.getPiano());
								out.println("Scala : " + ind.getScala());
								out.println("Toponimo : " + ind.getToponimo());
							 	
								
							}
							if(rr.getElencoPersona()[i].getPersonaCompleta() != null){
								PersonaCompleta pc = rr.getElencoPersona()[i].getPersonaCompleta();
								out.println("Persona Completa : Codice Fiscale : " + pc.getCodiceFiscale());
								out.println("Persona Completa Codice Stato ISTAT: " + pc.getCodiceStatoISTAT());
								out.println("Persona Completa Cognome: " + pc.getCognome());
								out.println("Persona Completa Cittadinanza: " + pc.getDescrizioneCittadinanza());
								out.println("Persona Completa Nome: " + pc.getNome());
								out.println("Persona Completa Sesso :  " + pc.getSesso());
								out.println("Persona Completa Stato Civile : " + pc.getStatoCivile());
							 	
								
							}
							
							out.println("*********************************************" );
							out.println("*********************************************" );
					}
				
				if(rr.getFamiglia() != null){
					out.println("******************** STATO DI FAMIGLIA *************************" );
					
					if(rr.getFamiglia().getDatiIndirizzo() != null){
						out.println("******************** DATI INDIRIZZO *************************" );
						DatiIndirizzo ind = rr.getFamiglia().getDatiIndirizzo();
						out.println("Indirizzo Breve : " + ind.getIndirizzoBreve());
						out.println("Interno : " + ind.getInterno());
						out.println("Municipio : " + ind.getMunicipio());
						out.println("Numero : " + ind.getNumero());
						out.println("Piano : " + ind.getPiano());
						out.println("Scala : " + ind.getScala());
						out.println("Toponimo : " + ind.getToponimo());
					}
					if(rr.getFamiglia().getFlagFamigliaConvivenza() != null){
						
					 	out.println("CONVIVENZA   : " + rr.getFamiglia().getFlagFamigliaConvivenza());
 
					}
					if(rr.getFamiglia().getComponenti() != null){
						for(int k=0; k< rr.getFamiglia().getComponenti().length; k++){
							
							PersonaCompleta pc = rr.getFamiglia().getComponenti()[k].getPersonaCompleta();
							out.println("******************** PERSONA COMPLETA  *************************" );
							out.println("Persona Completa : Codice Fiscale : " + pc.getCodiceFiscale());
							out.println("Persona Completa Codice Stato ISTAT: " + pc.getCodiceStatoISTAT());
							out.println("Persona Completa Cognome: " + pc.getCognome());
							out.println("Persona Completa Cittadinanza: " + pc.getDescrizioneCittadinanza());
							out.println("Persona Completa Nome: " + pc.getNome());
							out.println("Persona Completa Sesso :  " + pc.getSesso());
							out.println("Persona Completa Stato Civile : " + pc.getStatoCivile());
							
							Nascita nasc = rr.getFamiglia().getComponenti()[k].getNascita();
							out.println("******************** DATI NASCITA   *************************" );
							out.println("Anno Nascita : " + nasc.getAnno());
							out.println("Codice Comune ISTAT : " + nasc.getCodiceComuneISTAT());
							out.println("Codice Provincia ISTAT : " + nasc.getCodiceProvinciaISTAT());
							out.println("Codice Stato ISTAT : " + nasc.getCodiceStatoISTAT());
							out.println("Giorno Nascita : " + nasc.getGiorno());
							out.println("Mese Nascita : " + nasc.getMese());
							out.println("Comune : " + nasc.getNomeComune());
							out.println("Stato : " + nasc.getNomeStato());
							out.println("Sigla Provincia : " + nasc.getSiglaProvincia() );
							
							out.println("Rapporto di parentela : " +  rr.getFamiglia().getComponenti()[k].getRapportoParentela());
						}
					 	 
 
					}
				}
			}else{

				out.println("Attenzione il result è nulloo !" );
				
				
			}	
		 	
	%>

</body>
</html>