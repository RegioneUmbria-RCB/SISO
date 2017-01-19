package it.webred.ss.web.bean.importazione;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaLuogoDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitComune;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaMotivazione;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

@ManagedBean
@ViewScoped
public class ImportBean extends SegretariatoSocBaseBean {
	
	HashMap<String, Long> cfToId = new HashMap<String, Long>(); //segnalato CF to idDB
	HashMap<Long, Long> idToId = new HashMap<Long, Long>(); //segnalato  idCSV to idDB
	HashMap<Long,String> idToName = new HashMap<Long, String>(); //assistenti idASCSV to String
	HashMap<Long,Long> idAnaToInd = new HashMap<Long, Long>(); //residenza

	private SsSchedaSessionBeanRemote schedaService;

	private AnagrafeService anagrafeService;
	
	public ImportBean() {
		try {
			schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
			anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
					"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
		} catch (NamingException e) {
			logger.error(e);
		}
	}
	
	
	public void startImport() {
		logger.info("dentro a Import Globale");
		
		BufferedReader br=null;
		try {
			 br = new BufferedReader(new FileReader("C:\\Sviluppo\\csv\\t_assistenti_mod.csv"));
			importAssistenti(br);
			 br = new BufferedReader(new FileReader("C:\\Sviluppo\\csv\\segnalati2015.csv"));
			importAnagrafiche(br);
			 br = new BufferedReader(new FileReader("C:\\Sviluppo\\csv\\t_segretariato2015.csv"));
			importSchede(br);
			 br = new BufferedReader(new FileReader("C:\\Sviluppo\\csv\\t_diario2015.csv"));
			importDiario(br);
			 
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
	
	private void importDiario(BufferedReader br) throws IOException {
		logger.info("dentro a importDiario");
		String line;
		line = br.readLine();
		assert(line.equals("\"IdDiario\",\"IdSegrCart\",\"Tipo\",\"Intervento\",\"DataIntervento\",\"Operatore\",\"persnind\""));
		while ((line = br.readLine()) != null) {
			line = joinWithFollowingIfMultiLine(line,br);
			String[] fields = removeInternalCommas(line).split(",");
			if (fields.length != 7) {
				logger.debug("empty line in diario");
				continue;
			}
			
			Long OP = new Long(fields[5]); 
			
			String DATA = fields[4]; DATA = DATA.replace("\"", "");
			Date DATAAACCESSO=null;
			try {
				DATAAACCESSO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DATA);
			} catch (ParseException e) {
				logger.error(e);
			}	
			
			String INTERVENTO = fields[3]; INTERVENTO = INTERVENTO.replace("\"", "");
			if (INTERVENTO.length()>1999)
				logger.info(INTERVENTO);
			SsDiario diario = new SsDiario();
			diario.setAutore(idToName.get(OP));
			if (DATAAACCESSO != null)
				diario.setData(DATAAACCESSO);
			diario.setPubblica(false); 
			
			SsOOrganizzazione org = new SsOOrganizzazione();
			org.setId(this.getPreselectedPContatto().getOrganizzazione().getId());
			diario.setEnte(org);
			diario.setNota(INTERVENTO);
			
			Long IDANACSV = new Long(fields[6]);
			Long IDANA = idToId.get(IDANACSV); // ID tabella SS_ANAGRAFICA
			
			// RICARICARE da SS_ANAGRAFICA
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(IDANA);
			SsAnagrafica ana = schedaService.readAnagraficaById(dto);
			
			diario.setSoggetto(ana);
			dto.setObj(diario);
			schedaService.writeNotaDiario(dto);
			
			
			
		}
		logger.info("finito importDiario");
	}
	
	
	private String joinWithFollowingIfMultiLine(String s, BufferedReader br) throws IOException {
		StringBuffer buf = new StringBuffer("");
		boolean inside = false;
		while (true) {
			buf.append(s);
			for (int i=0; i< s.length();i++) {
				if (s.charAt(i)=='"')
					inside = !inside;
			}		
			if (!inside)
				break;
			buf.append('\n');
			s = br.readLine();
		}
		return buf.toString();
	}


	private String removeInternalCommas(String s) {
		StringBuffer buf = new StringBuffer("");
		boolean inside = false;
		for (int i=0; i< s.length();i++) {
			if (s.charAt(i)=='"')
				inside = !inside;
			if (!inside || s.charAt(i)!=',')
				buf.append(s.charAt(i));
			else
				buf.append(' ');
		}
		logger.debug(s);
		logger.debug(buf.toString());
		return buf.toString();
	}
	private void importAssistenti(BufferedReader br) throws IOException {
		logger.info("Dentro a importAssistenti");
		String line;
		line = br.readLine();
		assert(line.equals("\"idAS\",\"NomeAS\",\"CognomeAS\",\"EmailAS\",\"CODFISC\""));
		while ((line = br.readLine()) != null) {
			String[] fields = removeInternalCommas(line).split(",");
			Long ID = new Long(fields[0]);
			//String IDSTR = fields[2]+" "+fields[1]+" ("+fields[4]+")";
			//Sostituisco descrizione intera al solo codice fiscale (05/05/2015)
			String IDSTR = fields[4];
			//TODO:Eventualmente inserire in anagrafica
			IDSTR = IDSTR.replace("\"", "");
			
			idToName.put(ID, IDSTR);
			logger.info("AS translation: "+ID+" -> "+IDSTR);
		}
	}
	
	private void importSchede(BufferedReader br) throws IOException {
		logger.info("dentro a importSchede");
		String line;
		final 	String[] idToAccesso = {"","Spontaneo","Decreto","Inviato da"};
		//Andrebbero prese da tabella 
		final String[] idToFamiglia = {"","Singolo","Coppia","Coppia con figli","Mono genitoriale","Famiglia estesa (2-3 generazioni)","Altro"};	
		line = br.readLine();
		assert(line.equals("\"Id\",\"IdSegnalante\",\"IdComune\",\"IdCartella\",\"DataAccesso\",\"Operatore\",\"ModalitaAccesso\",\"persnind\",\"Telefono\",\"Cellulare\",\"Mail\",\"Scala\",\"Piano\",\"Accesso\",\"CONVERT(AES_DECRYPT(t.`SpecificaAccesso`,'xchggT487rg!') USING latin1)\",\"Ufficio\",\"IdTipoFamiglia\",\"CONVERT(AES_DECRYPT(`SpecificaFamiglia`,'xchggT487rg!') USING latin1)\",\"UtenteConosciuto\",\"TipoInterventiNucleo\",\"CONVERT(AES_DECRYPT(t.`Note`,'xchggT487rg!') USING latin1)\",\"persnindPersonaRif\",\"CognomePersonaRif\",\"NomePersonaRif\",\"Parentela\",\"IdMotivazione\",\"IdServizio\",\"IdIntervento\",\"AltroEnte\",\"AltroServizio\",\"IdStato\",\"Assistente\",\"DataSalvataggio\",\"UtenteInserimento\",\"DataUltimaModifica\",\"UtenteModifica\",\"SegnalanteInterno\",\"IdStatus\",\"Professione\",\"CONVERT(AES_DECRYPT(t.`SpecificaMotivazione`,'xchggT487rg!') USING latin1)\",\"rfamnumf\",\"Domicilio\""));
		while ((line = br.readLine()) != null) {
			//SS_SCHEDA_SEGNALATO
			String[] fields = removeInternalCommas(line).split(",");
			String PNOTE = "";
			String TEL = fields[8]; TEL = TEL.replace("\"", "");	
			if (TEL.length()>14){
				PNOTE += "Note su telefono: "+TEL+"\n";
				TEL="";
			}		
			String CEL = fields[9]; CEL = CEL.replace("\"", "");
			if (CEL.length()>14){
				PNOTE += "Note su cellulare: "+CEL+"\n";
				CEL="";
			}
			String EMAIL = fields[10]; EMAIL = EMAIL.replace("\"", "");
			int id = Integer.parseInt(fields[13]);
		
			try {
				id = Integer.parseInt(fields[16]);
			}catch(Exception e){
				id = 1;
				logger.debug("Errore conversione numero");
			}
			
			String TIP_FAM = idToFamiglia[id];
			String DESC_FAM = fields[17]; DESC_FAM = DESC_FAM.replace("\"", "");
			if (DESC_FAM.length() > 0)
				PNOTE += "Descrizione Nucleo Familiare: "+DESC_FAM+"\n";
			String LAV = fields[38]; LAV = LAV.replace("\"", "");
			//proseguire con Note, Indirizzo, Accesso
			//TESSERA SANITARIA, MEDICO, VIVE SOLO, INVALIDO, PERCENTUALE, STATUS   non sono importati
			Long IDANACSV = new Long(fields[7]);
			Long IDANA = idToId.get(IDANACSV); // ID tabella SS_ANAGRAFICA
			String DOMICILIO = fields[41]; DOMICILIO = DOMICILIO.replace("\"", "");
			
			// RICARICARE da SS_ANAGRAFICA
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(IDANA);
			SsAnagrafica ana = schedaService.readAnagraficaById(dto);
			if (ana == null){
				logger.info(IDANA);
				continue;
			}
				
			SsIndirizzo residenza = new SsIndirizzo();
			SsIndirizzo domicilio = new SsIndirizzo();
			SitDPersona result = readSoggettoFromAnagrafeByCf(ana.getCf());
			if (result != null) {
				IndirizzoAnagrafeDTO ind = getResidenzaFromAnagrafe(result);
				String indirizzo = ind.getIndirizzoCompleto();
				String codComune = "018177";
				residenza.setComune(codComune);
				residenza.setVia(indirizzo);
				
				dto = new BaseDTO();
				dto.setObj(residenza);
				fillUserData(dto);
				residenza.setId(schedaService.writeIndirizzo(dto));
				
				
				domicilio.setComune(codComune);
				domicilio.setVia(DOMICILIO);
				dto.setObj(domicilio);
				domicilio.setId(schedaService.writeIndirizzo(dto));
				
				logger.info("RESIDENZA: " + indirizzo + " COD: " + codComune);
			}
			
			//SCHEDA SEGNALATO
			SsSchedaSegnalato segnalato = new SsSchedaSegnalato();
			segnalato.setTelefono(TEL);
			segnalato.setCel(CEL);
			segnalato.setEmail(EMAIL);
			//segnalato.setTipologia_familiare(TIP_FAM);
			//segnalato.setLavoro(LAV); non corrisponde a possibili diciture, spostato in note
			segnalato.setResidenza(residenza);
			segnalato.setDomicilio(domicilio);
			segnalato.setAnagrafica(ana);

			dto = new BaseDTO();
			dto.setObj(segnalato);
			fillUserData(dto);
			Long idSegnalato = schedaService.saveSegnalato(dto).getId();
			
		
			//ACCESSO
			SsSchedaAccesso schedaAccesso = new SsSchedaAccesso();
			
			String DATA = fields[4]; DATA = DATA.replace("\"", "");
			Date DATAAACCESSO=null;
			try {
				DATAAACCESSO = new SimpleDateFormat("yyyy-MM-dd").parse(DATA);
				schedaAccesso.setData(DATAAACCESSO);
			} catch (ParseException e) {
				logger.error(e);
			}	
			
			Long UFF = new Long(6); // 6 = id categoria dati pregressi
			
			SsRelUffPcontOrg rel = new SsRelUffPcontOrg();
			SsRelUffPcontOrgPK pk = new SsRelUffPcontOrgPK();
			pk.setUfficioId(UFF);
			pk.setOrganizzazioneId(this.getPreselectedPContatto().getOrganizzazione().getId());
			
			pk.setPuntoContattoId(this.getPreselectedPContatto().getIdPContatto());
			rel.setId(pk);
			schedaAccesso.setSsRelUffPcontOrg(rel);
			
			schedaAccesso.setModalita("Di Persona");
			
			Long OP = new Long(fields[5]); 
			schedaAccesso.setOperatore(idToName.get(OP));
			
			schedaAccesso.setInterlocutore("Segnalato");
			
			String MOTIVO = idToAccesso[id];
			String MOTIVO_DESC = fields[14];MOTIVO_DESC = MOTIVO_DESC.replace("\"", "");
		    schedaAccesso.setModalita(MOTIVO);
		    schedaAccesso.setMotivoDesc(MOTIVO_DESC);
			
			dto.setObj(schedaAccesso);
			schedaAccesso.setId(schedaService.saveAccesso(dto));
			
			
			//MOTIVAZIONE
			Object[] motiv = new Object[2];
			String MOT_ALTRO = fields[39]; MOT_ALTRO = MOT_ALTRO.replace("\"", "");
			SsSchedaMotivazione motivazione = new SsSchedaMotivazione();
			if (MOT_ALTRO.length()<100)
				motivazione.setAltro(MOT_ALTRO);
			else
				PNOTE += "Ulteriore Descrizione Motivazione: "+MOT_ALTRO+"\n";
			dto.setObj(motivazione);
			motivazione.setId(schedaService.saveMotivazione(dto));
			motiv[1]=motivazione;
			
			//Vigevano al momento erano sempre POVERTA' (1 -> 0)
			SsMotivazione mot = new SsMotivazione();
			mot.setId(0L);
			motiv[0]=mot;
			dto.setObj(motiv);
			schedaService.writeMotivazioneScheda(dto);
			
			
			
			SsScheda scheda = new SsScheda();
			
			Long TIPOSCHEDA = new Long(fields[27]);
			switch (Integer.parseInt(""+TIPOSCHEDA)) {
			case 2:
				scheda.setTipo(0L);
				break;
			case 3:
				scheda.setTipo(3L);
				break;
			case 4:
				scheda.setTipo(1L);
				break;
			}
			
	
			
			
			// ID_stato  lo usiamo solo come indicatore di scheda completa o meno
			Long IDSTATO = new Long(fields[30]);
			
			scheda.setCompleta(IDSTATO >= 2L);
			
			scheda.setAccesso(schedaAccesso);
			scheda.setSegnalato(idSegnalato);
			scheda.setMotivazione(motivazione);
			scheda.setEliminata(false);
			scheda.setEsterna(false);
			
			dto.setObj(scheda);
			scheda=schedaService.saveScheda(dto);
			
			// segnalazione a cartella sociale se di tipo presa in carico
			if(scheda.getTipo()!=null && scheda.getTipo().equals(3L)) {
				try {
					AccessTableSchedaSegrSessionBeanRemote schedaSegrService =
						(AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
		
					SchedaSegrDTO cartellaDto = new SchedaSegrDTO();
					fillUserData(cartellaDto);
					cartellaDto.setId(scheda.getId());
					String flag = "I";
					cartellaDto.setFlgStato(flag);
					cartellaDto.setIdCatSociale(3L); // categoria adulti
					schedaSegrService.saveSchedaSegr(cartellaDto);
					
					} catch (NamingException e) {
						logger.error(e);
					}
			}
			
			// DIARIO
			if (LAV.length()>0)
				PNOTE += "Lavoro: "+LAV+"\n";
			String Note = fields[20]; Note = Note.replace("\"", "");
			if (Note.length()>0)
				PNOTE += "Altre note:\n\n"+Note;
			
			SsDiario diario = new SsDiario();
			diario.setAutore(idToName.get(OP));
			if (DATAAACCESSO != null)
				diario.setData(DATAAACCESSO);
			diario.setPubblica(false);
			SsOOrganizzazione org = new SsOOrganizzazione();
			org.setId(this.getPreselectedPContatto().getOrganizzazione().getId());
			diario.setEnte(org);
			diario.setNota(PNOTE);
			diario.setSoggetto(ana);
			dto.setObj(diario);
			schedaService.writeNotaDiario(dto);
		}
	}
	
	public void testLetturaResidenzeByCF() {
		 SitDPersona result = readSoggettoFromAnagrafeByCf("CPRLNZ71P43L872C");
		if (result != null) {
			IndirizzoAnagrafeDTO ind = getResidenzaFromAnagrafe(result);
			
			String indirizzo = ind.getIndirizzoCompleto();
			String codComune = "018177";
			
//			SsIndirizzo residenza = new SsIndirizzo();
//			residenza.setComune(codComune);
//			residenza.setVia(indirizzo);
			
			//logger.info("RESIDENZA: " + indirizzo + " COD: " + codComune);
		}
	}
	
	public void importAnagrafiche(BufferedReader br){
		logger.info("dentro a importAnagrafica");
		
		try {
			String line;
			line = br.readLine();
			assert(line.equals("\"id\",\"IdSegnalante\",\"dp_perscogn\",\"dp_persnome\",\"dp_perscodifisc\",\"dp_persnascdata\",\"dp_ccomdesc\",\"sesso\",\"dp_desc_cittadinanza\""));
			
			while ((line = br.readLine()) != null) {
				String[] fields = removeInternalCommas(line).split(",");
				String CF = fields[4]; CF=CF.replace("\"", "");
				String cittadinanza = fields[8]; cittadinanza = cittadinanza.replace("\"", "");
				// prendere anagrafica da git con CF
				if(cfToId.containsKey(CF)) {
					idToId.put(new Long(fields[1]), cfToId.get(CF));
				}
				else  {
					logger.info("CF che stiamo cercando = ["+CF+"]");
					SitDPersona persona = readSoggettoFromAnagrafeByCf(CF);
					if(persona!=null) {
						SsAnagrafica ana = new SsAnagrafica();
						ana.setCf(persona.getCodfisc());
						ana.setCognome(persona.getCognome());
						ana.setNome(persona.getNome());
						//ana.setComune_nascita(getComuneFromIdExt(persona.getIdExtComuneNascita()));
						
				  		ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
						compDto.setPersona(persona);
						fillUserData(compDto);
			    		try {
			    			
			    		AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
			    				"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			    		
						compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);
			    		
			    		} catch(NamingException e) {
			    		}
						
						if("ITALIA".equals(compDto.getDesStatoNas())) {
							ana.setComuneNascitaCod(compDto.getCodComNas());
							ana.setComuneNascitaDes(compDto.getDesComNas());
							ana.setProvNascitaCod(compDto.getDesProvNas());
						} else {
							ana.setStatoNascitaCod(compDto.getIstatStatoNas());
							ana.setStatoNascitaDes(compDto.getDesStatoNas());
						}
						
						ana.setData_nascita(persona.getDataNascita());
						ana.setSesso(persona.getSesso());
						
						ana.setCittadinanza(getCittadinanza(cittadinanza));
						
						if(ana.getCittadinanza().isEmpty()){
						
							// sarebbe da prendere da tabella o forzare corrispondenza
							if(cittadinanza.equals("ITALIA"))
								ana.setCittadinanza("ITALIANA");
							else if(cittadinanza.equals("ROMANIA"))
								ana.setCittadinanza("RUMENA");
							else if(cittadinanza.equals("COSTA D'AVORIO"))
								ana.setCittadinanza("IVORIANA");
							else if(cittadinanza.equals("EGITTO"))
								ana.setCittadinanza("EGIZIANA");
							else if(cittadinanza.equals("MAROCCO"))
								ana.setCittadinanza("MAROCCHINA");
							else if(cittadinanza.equals("TURCHIA"))
								ana.setCittadinanza("TURCA");
							else if(cittadinanza.equals("NIGERIA"))
								ana.setCittadinanza("NIGERIANA");
							else if(cittadinanza.equals("SIRIA"))
								ana.setCittadinanza("SIRIANA");
							else if(cittadinanza.equals("PERU'"))
								ana.setCittadinanza("PERUVIANA");
							else if(cittadinanza.equals("BOLIVIA"))
								ana.setCittadinanza("BOLIVIANA");
							else if(cittadinanza.equals("TUNISIA"))
								ana.setCittadinanza("TUNISINA");
							else if(cittadinanza.equals("MOLDAVIA"))
								ana.setCittadinanza("MOLDAVA");
							else if(cittadinanza.equals("REGNO UNITO"))
								ana.setCittadinanza("BRITANNICA");
							else if(cittadinanza.equals("SRI LANKA"))
								ana.setCittadinanza("SRI LANKA");
							else if(cittadinanza.equals("EL SALVADOR"))
								ana.setCittadinanza("SALVADOREGNA");
							else if(cittadinanza.equals("ECUADOR"))
								ana.setCittadinanza("ECUADOREGNA");
							else if(cittadinanza.equals("ALBANIA"))
								ana.setCittadinanza("ALBANESE");
							else if(cittadinanza.equals("POLONIA"))
								ana.setCittadinanza("POLACCA");
							else if(cittadinanza.equals("BRASILE"))
								ana.setCittadinanza("BRASILIANA");
							else if(cittadinanza.equals("BANGLADESH"))
								ana.setCittadinanza("BANGLADESH");
							else if(cittadinanza.equals("FEDERAZIONE RUSSA"))
								ana.setCittadinanza("RUSSA");
							else if(cittadinanza.equals("BENIN"))
								ana.setCittadinanza("BENINENSE");
							else if(cittadinanza.equals("FILIPPINE"))
								ana.setCittadinanza("FILIPPINA");
							else if(cittadinanza.equals("NICARAGUA"))
								ana.setCittadinanza("NICARAGUENSE");
							else if(cittadinanza.equals("BURKINA FASO"))
								ana.setCittadinanza("BURKINA FASO");
							else if(cittadinanza.equals("REPUBBLICA DI MACEDONIA"))
								ana.setCittadinanza("MACEDONE");
							else if(cittadinanza.equals("BULGARIA"))
								ana.setCittadinanza("BULGARA");
							else if(cittadinanza.equals("MALI"))
								ana.setCittadinanza("MALI");
							else if(cittadinanza.equals("CAMERUN"))
								ana.setCittadinanza("CAMERUNENSE");
							else if(cittadinanza.equals("ALGERIA"))
								ana.setCittadinanza("ALGERINA");
							else if(cittadinanza.equals("INDIA"))
								ana.setCittadinanza("INDIANA");
							else if(cittadinanza.equals("SPAGNA"))
								ana.setCittadinanza("SPAGNOLA");
							else if(cittadinanza.equals("UCRAINA"))
								ana.setCittadinanza("UCRAINA");
							else if(cittadinanza.equals("KENYA"))
								ana.setCittadinanza("KENIOTA");
							else if(cittadinanza.equals("URUGUAY"))
								ana.setCittadinanza("URUGUAYANA");
							else if(cittadinanza.equals("GIAPPONE"))
								ana.setCittadinanza("GIAPPONESE");
							else if(cittadinanza.equals("SENEGAL"))
								ana.setCittadinanza("SENEGALESE");
							else if(cittadinanza.equals("ANGOLA"))
								ana.setCittadinanza("ANGOLANA");
							else if(cittadinanza.equals("CINA REPUBBLICA POPOLARE"))
								ana.setCittadinanza("CINESE");
							else {
								ana.setCittadinanza(cittadinanza);
								logger.warn("Cittadinanza sconosciuta: " + cittadinanza);
							}
						}
						ana.setStato_civile(null);

						BaseDTO dto = new BaseDTO();
						fillUserData(dto);
						dto.setObj(ana);
						Long id = schedaService.saveAnagrafica(dto);
						idToId.put(new Long(fields[1]), id);
						cfToId.put(CF, id);
						
					}

				}

			}
				
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		
	}
	
	private SitDPersona readSoggettoFromAnagrafeByCf(String cf){
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
    	fillUserData(dto);
    	dto.setCodFis(cf);
    	
    	List<SitDPersona> results = anagrafeService.getListaPersoneByCF(dto);
    	if(results != null && !results.isEmpty())
    		return results.get(0);
    		
    	return null;
	}
	
	
    private String getCittadinanza(String stato){
    	try{
			LuoghiService luoghiService = (LuoghiService)  ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
			AmTabNazioni naz = luoghiService.getNazioneByDenominazione(stato);
			return naz.getNazionalita();
		}catch(Exception e){
			logger.error("Errore recupero cittadinanza da nome stato: "+stato);
		}
    	return null;
  
	}

}
