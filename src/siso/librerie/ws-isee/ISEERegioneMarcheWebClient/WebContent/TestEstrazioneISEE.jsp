<%@page import="it.marche.regione.pddnica.client.*"%>
 <%@page import="it.siso.isee.obj.*"%>
<%@page import="java.util.ArrayList"%>
 

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test estrazione dati ISEE</title>
</head>
<body>
	<%
	
	String codiceFiscale = request.getParameter("p_codiceFiscale");

	String tipoOp = request.getParameter("p_tipoOp");
	
	
	 RicercaCF ricercaCF = new RicercaCF();
	 
	 ricercaCF.setCodiceFiscale(codiceFiscale);
	 /*  ricercaCF.setDataValidita("2020-09-09T21:59:00Z"); */
	  ricercaCF.setDataValidita("09/09/2020");
	 //ricercaCF.setPrestazioneDaErogare("A1.19");
	 ricercaCF.setPrestazioneDaErogareEnum(PrestazioneDaErogare.A119);
	 ricercaCF.setProtocolloDomandaEnteErogatore("ID Domanda: 126575");
	/*  ricercaCF.setStatodomandaPrestazione("Da Erogare");  */
	  ricercaCF.setStatoDomandaPrestazioneEnum(StatoDomanda.DA_EROGARE);
	 DatiRichiestaISEE datiRichiesta = new DatiRichiestaISEE();
	 datiRichiesta.setAction("");
	 SicurezzaIdentificazioneMittente sicurezzaIdentificazioneMittente = new SicurezzaIdentificazioneMittente();
	 sicurezzaIdentificazioneMittente.setCfOperatore("CCCRRT57P09L500V");
	 sicurezzaIdentificazioneMittente.setCodiceEnte("DSU00081");
	 sicurezzaIdentificazioneMittente.setCodiceUfficio("DirStud");
	 
	 
	Esito esito = null;
	 if(tipoOp.equals("ATT")){
		
		
		 datiRichiesta.setEndPoint("http://pddnica.regione.marche.it/PdDRegioneMarche/PD/ERDISMarche_INPS_SPCISEE2015/ConsultazioneAttestazione");
		
		 
		 datiRichiesta.setMittente(sicurezzaIdentificazioneMittente);
		 ISEESoapClient client = new ISEESoapClient(datiRichiesta);
		 esito =  client.consultaAttestazioneCF(ricercaCF); 
		 
	 }else{
		 
		  datiRichiesta.setEndPoint("http://pddnica.regione.marche.it/PdDRegioneMarche/PD/ERDISMarche_INPS_SPCISEE2015/ConsultazioneDichiarazione");
		 
		 
		 datiRichiesta.setMittente(sicurezzaIdentificazioneMittente);
		 ISEESoapClient client = new ISEESoapClient(datiRichiesta);
		 esito =  client.consultaDichiarazioneCF(ricercaCF); 
	 }
	   
	 
/* 	 XmlISEEParser parser = new XmlISEEParser(null,true);
	 Attestazione attestazione = null;// parser.estraiAttestazione();
	 Dichiarazione dichiarazione =  parser.estraiDichiarazione();
 */	 
	 
 out.write ("*************	ESITO CONSULTAZIONE :  ******  "+ esito.getConsultazioneEsito() +"   *******");  %>
	<br><br>
	 <%
 
 out.write ("*************	messaggio CONSULTAZIONE :  ******  "+ esito.getDescErrore() +"   *******"); %>
	<br><br>
	 <%
	 Attestazione attestazione = esito.getAttestazione();
     Dichiarazione dichiarazione = esito.getDichiarazione();
 
	 if(dichiarazione != null){
		 out.write ("*************	DICHIARAZIONE  INIZIO  *************");  %>
			<br><br>
			 <%
		 out.println("Codice Fiscale  : " + dichiarazione.getCodiceFiscale() ); %>
			<br><br>
			 <%
			 if(dichiarazione.getOperazione() != null){
				 out.write ("*************	  - OPERAZIONE -----  *************");  %>
					<br><br>
					 <%
				 out.println("NUMERO PRO. RIFERIMENTO  : " + dichiarazione.getOperazione().getNumeroProtocolloRiferimento() );%>
					<br>
					 <%
				 out.println("TIPO  : " + dichiarazione.getOperazione().getTipo() );%>
					<br>
					 <%
				 out.println("CODICE FISCALE RIFERIMENTO  : " + dichiarazione.getOperazione().getCodiceFiscaleRiferimento() );%>
					<br><br>
					 <%
			 }
		if(dichiarazione.getNucleoFamiliare() != null){
			for(int i=0; i< dichiarazione.getNucleoFamiliare().size(); i++){
				 out.write ("*************	COMPONENTE NUCLEO  " + i + "  *************");  %>
					<br> <br>
					 <%
				 out.write ("*************						 CF " + dichiarazione.getNucleoFamiliare().get(i).getCodiceFiscale() + "  *************");  %>
					<br>
					 <%
				 out.write ("*************						 COGNOME " + dichiarazione.getNucleoFamiliare().get(i).getCognome() + "  *************");  %>
					<br>
					 <%
				 out.write ("*************						 NOME " + dichiarazione.getNucleoFamiliare().get(i).getNome() + "  *************");  %>
					<br>
					 <%
				 out.write ("*************						 RAPPORTO CON DICHIARANTE " + dichiarazione.getNucleoFamiliare().get(i).getRapportoConDichiarante() + "  *************");  %>
					<br>
					
					 <%
				 out.write ("*************						 FLG ASSENZA REDDITO " + dichiarazione.getNucleoFamiliare().get(i).isFlagAssenzaReddito() + "  *************");  %>
					<br>
					<%
					 out.write ("******************************************************************************************************************"); 
					%>
					
					<br><br>
					 <%
						if(dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo() != null){
							
							 out.write ("*************		FOGLIO COMPONENTE NUCLEO  " + i + " *************");   %>
								<br><br>
								<%
								
							 out.write ("*************						CF " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getCodiceFiscale()  + "  *************");   %>
								<br>
								<%
								
							 if(dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete() != null){
								 
								 out.write ("*************		DATI COMPONENTE NUCLEO  " + i + " *************");   %>
									<br> <br>
									<%
								 
								 out.write ("*************						ATTIVITA' SOGGETTO " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAttivitaSoggetto()  + "  *************"); %>
									<br>
									<%  
								 
								 if(dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica() != null){
									 out.write ("*************		ANAGRAFICA COMPONENTE NUCLEO " + i + "*************");   %>
										<br><Br>
										<%
									 
									 out.write ("*************						NOME " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getNome()  + "  *************");   %>
										<br>
										<%
									
									 out.write ("*************						COGNOME " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getCognome()  + "  *************");   %>
										<br>
										<%
									
									 out.write ("*************						CF " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getCodiceFiscale()   + "  *************");   %>
										<br>
										<%
									
									 out.write ("*************						COMUNE DI NASCITA " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getComuneNascita()   + "  *************");   %>
										<br>
										<%
										
									 out.write ("*************						DATA DI NASCITA " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getDataNascita()  + "  *************");  %>
										<br>
										<% 
										
									 out.write ("*************						PROVINCIA NASCITA " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getProvinciaNascita()   + "  *************"); %>
										<br>
										<%  
									
									 out.write ("*************						SESSO " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getSesso()   + "  *************");   %>
										<br><br>
										<%
										
								 }
									 if(dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza() != null){
										 
										 out.write ("*************		RESIDENZA COMPONENTE NUCLEO " + i + "*************");   %>  %>
										<br><Br>
										<%
										 
										 out.write ("*************						INDIRIZZO " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getIndirizzo()   + "  *************");    %>
											<br>
											<%  
										 out.write ("*************						PROVINCIA " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getProvincia()   + "  *************"); %>
											<br>
											<%       
										 out.write ("*************						CIVICO    " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getCivico()   + "  *************");   %>
											<br>
											<%   
										 out.write ("*************						COMUNE " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getComune()   + "  *************");   %>
											<br>
											<%    
										 out.write ("*************						CAP " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getCap()   + "  *************");    %>
											<br>
											<%  
										 out.write ("*************						TELEFONO " + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getTelefono()    + "  *************");    %>
											<br>
											<%  
										 out.write ("*************						INDIRIZZO EMAIL" + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getIndirizzoEmail()   + "  *************");  %>
											<br>
											<%     
										 out.write ("*************						CELLULARE" + dichiarazione.getNucleoFamiliare().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getCellulare()   + "  *************");     %>
											<br>
											<%  
												
									 }
											
							 }
						}
			}
			
		}
		 out.write ("*************	DICHIARAZIONE  FINE  *************");  %>
			<br>
			 <%
	 }
	 
	 if(attestazione != null){
		 out.write ("*************	ATTESTAZIONE *************");
		    %>
			<br>
			 <%
		 out.println("Codice Fiscale Dichiarante : " + attestazione.getCodiceFiscaleDichiarante() ); %>
			<br>
			 <%
		 out.println("numeroProtocolloDSU : " + attestazione.getNumeroProtocolloDSU() ); %>
			<br>
			 <%
		 out.println("dataPresentazione : " + attestazione.getDataPresentazione() ); %>
			<br>
			 <%
		 out.println("dataValidita : " + attestazione.getDataValidita() ); %>
			<br>
			 <%
		 out.println("protocolloMittente : " + attestazione.getProtocolloMittente() ); %>
			<br>
			 <%
			 if(attestazione.getOrdinario() != null){
		 		 out.write ("*************	 ORDINARIO *************");%>
		 		  
					<br>
					 <%
		 		 out.println("		Protocollo DSU Modulo Sostitutivo (" + attestazione.getOrdinario().getProtocolloDSUModuloSostitutivo() + ")");%>
		 		  
					<br>
					  <%
					 if(attestazione.getOrdinario().getCorrente() != null){
						 out.write ("*************	ORDINARIO - CORRENTE *************");%>
				 		  
						    <br>
						  <%
						 out.println("		Corrente (" + attestazione.getOrdinario().getCorrente() + ")");%>
		 		  
					    <br>
					  <%
					 }
					  if(attestazione.getOrdinario().getComponenteMinorenne() != null){
							 out.write ("*************	ORDINARIO - COMPONENTI MINORI *************");
							 out.println("		Corrente (" + attestazione.getOrdinario().getCorrente() + ")");%>
			 		  			
						    <br>
						  <%
						  	for(int j=0; j<attestazione.getOrdinario().getComponenteMinorenne().size(); j++){
						  		if(attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi() != null){
						  			 out.write ("*************	ORDINARIO - COMPONENTI MINORI - MINORE  " + j + " *************");%>
					 		  			
									    <br>
									  <%
						  			 out.println("	COGNOME (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getCognome() + ")"); %>
					 		  			
									    <br>
									  <%
						  			 out.println("	NOME (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getNome() + ")");%>
					 		  			
									    <br>
									  <% 
						  			 out.println("	CF (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getCodiceFiscale()  + ")");%>
					 		  			
									    <br>
									  <%
						  			 out.println("	DT NASCITA (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getDataNascita() + ")"); %>
					 		  			
									    <br>
									  <%
						  			 out.println("	COMUNE NASCITA (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getComuneNascita() + ")");%>
					 		  			
									    <br>
									  <%
						  			 out.println("	ISEE (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getISEE() + ")"); %>
					 		  			
									    <br>
									  <%
						  			 out.println("	PROVINCIA NASCITA (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getProvinciaNascita()+ ")"); %>
					 		  			
									    <br>
									  <%
						  			 out.println("	SESSO (" + attestazione.getOrdinario().getComponenteMinorenne().get(j).getAttributi().getSesso() + ")"); %>
					 		  			
									    <br>
									  <%
						  		}
						  	}
						 }
					 if(attestazione.getOrdinario().getIseeOrdinario() != null){
						 out.write ("*************	ORDINARIO - ISEE ORDINARIO *************"); %>
							<br>
							 <%
							 out.println("		Data  rilascio ISEE (" + attestazione.getOrdinario().getIseeOrdinario().getDatiIsee().getDataRilascio() + ")");%>
								<br>
								 <%
							
							 if(attestazione.getOrdinario().getIseeOrdinario().getDatiIsee().getValori() != null){
								 out.write ("*************	ORDINARIO - ISEE ORDINARIO - VALORI ISEE *************");%>
									<br>
									 <%
								 out.println("		 ISE (" + attestazione.getOrdinario().getIseeOrdinario().getDatiIsee().getValori().getISE()  + ")");%>
									<br>
									 <%
								 out.println("		 ISEE (" + attestazione.getOrdinario().getIseeOrdinario().getDatiIsee().getValori().getISEE()   + ")");%>
									<br>
									 <%
								 out.println("		 ISP (" + attestazione.getOrdinario().getIseeOrdinario().getDatiIsee().getValori().getISP()   + ")");%>
									<br>
									 <%
								 out.println("		 ISR (" + attestazione.getOrdinario().getIseeOrdinario().getDatiIsee().getValori().getISR()   + ")");	%>
									<br>
									 <%
								 out.println("		 SCALA EQUIVALENZA (" + attestazione.getOrdinario().getIseeOrdinario().getDatiIsee().getValori().getScalaEquivalenza()   + ")");%>
									<br><br>
									 <%
							 }
							  
							 
					 }
			 if(attestazione.getOrdinario().getComponenteNucleo() != null){
				 for(int i=0; i<attestazione.getOrdinario().getComponenteNucleo().size(); i++){
					 out.println("*************	 ORDINARIO - COMPONENTE NUCLEO  " + i + " *************");
					 %>
					<br>
					 <%
					 out.println("		CF " + attestazione.getOrdinario().getComponenteNucleo().get(i).getCodiceFiscale()); %>
						<br>
						 <%
					 out.println("		COGNOME " + attestazione.getOrdinario().getComponenteNucleo().get(i).getCognome()); %>
						<br>
						 <%
					 out.println("		NOME " + attestazione.getOrdinario().getComponenteNucleo().get(i).getNome()); %>
						<br>
						 <%
					 out.println("		RAPPORTO CON DICHIARANTE " + attestazione.getOrdinario().getComponenteNucleo().get(i).getRapportoConDichiarante()); %>
						<br>
						 <%
					 if(attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo() != null){
						 out.println("*************	  FOGLIO COMPONENTE NUCLEO  " + i + " ************* <br/>"); %>
							<br>
							 <%
						 out.println("*************	  CF  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getCodiceFiscale() + " *************"); %>
							<br>
							 <%
						if( attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete()!= null){
							out.println("*************	 DATI COMPONENTE NUCLEO  " + i + " *************"); %>
							<br>
							 <%
							 out.println("*************	  ATTIVITA SOGGETTO  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAttivitaSoggetto() + " *************"); %>
								<br>
								 <%
							 out.println("*************	  FLAG CONVIVENZA  " +  attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().isFlagConvivenzaAnagrafica() + " *************"); %>
								<br>
								 <%
								
							 if(attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica() != null){
								out.println("*************	 ANAGRAFICA NUCLEO  " + i + " *************"); %>
								<br>
								 <%
								 out.println("*************	 COGNOME  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getCognome() + " *************"); %>
									<br>
									 <%
								 out.println("*************	 NOME  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getNome() + " *************"); %>
									<br>
									 <%
								 out.println("*************	 COMUNE DI NASCITA  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getComuneNascita() + " *************"); %>
									<br>
									 <%
								 out.println("*************	 DATA DI NASCITA  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getDataNascita()  + " *************"); %>
									<br>
									 <%
								 out.println("*************	 CF  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getCodiceFiscale() + " *************"); %>
									<br>
									 <%
								 out.println("*************	 PROVINCIA DI NASCITA  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getProvinciaNascita() + " *************"); %>
									<br>
									 <%
								 out.println("*************	 SESSO  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getAnagrafica().getSesso() + " *************"); %>
									<br>
									 <%
								 out.println("*************	 ***************************************************************** *************");		 %>
									<br><br>
									 <%					
							}
							if(attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza() != null){
								out.println("*************	 RESIDENZA NUCLEO  " + i + " *************"); %>
								<br>
								 <%
								out.println("*************	 INDIRIZZO  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getIndirizzo() + " *************"); %>
								<br>
								 <%
								out.println("*************	 COMUNE  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getComune() + " *************"); %>
								<br>
								 <%
								out.println("*************	 PROVINCIA  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getProvincia() + " *************"); %>
								<br>
								 <%
								out.println("*************	 TELEFONO  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getTelefono()  + " *************"); %>
								<br>
								 <%
								out.println("*************	 CIVICO  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getCivico()  + " *************"); %>
								<br>
								 <%
								out.println("*************	 CAP  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getCap()  + " *************"); %>
								<br>
								 <%
								out.println("*************	 EMAIL  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getIndirizzoEmail()  + " *************"); %>
								<br>
								 <%
								out.println("*************	 CELLULARE  " + attestazione.getOrdinario().getComponenteNucleo().get(i).getFoglioCompopneteNucleo().getDatiComponete().getResidenza().getCellulare()  + " *************"); %>
								<br><br>
								 <%
									
							}
							
					 }
				 }
				 
			 }
		  }
		 }
	 
	 
	 } // Fine IF ATTESTAZIONE
	 

	%>  
</body>
</html>