package it.webred.ss.web.bean.importazione;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipiCategoriaSociale;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaResult;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaMotivazione;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.util.Soggetto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

@ManagedBean
@ViewScoped
public class ImportBean extends SegretariatoSocBaseBean {
	
	HashMap<String, Long> cfToId = new HashMap<String, Long>(); //segnalato CF to idDB
	HashMap<Long, Long> idToId = new HashMap<Long, Long>(); //segnalato  idCSV to idDB
	HashMap<Long,String> idToName = new HashMap<Long, String>(); //assistenti idASCSV to String
	HashMap<Long,Long> idAnaToInd = new HashMap<Long, Long>(); //residenza
	
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
				
			SsIndirizzo domicilio = new SsIndirizzo();
			//SitDPersona result = readSoggettoFromAnagrafeByCf(ana.getCf());
		
			SsIndirizzo residenza = ricercaIndirizzoAllProvenienza(ana.getCf(), null);
			if (residenza != null) {
		
				dto = new BaseDTO();
				dto.setObj(residenza);
				fillUserData(dto);
				residenza.setId(schedaService.writeIndirizzo(dto));
				
				domicilio.setComuneCod(residenza.getComuneCod());
				domicilio.setVia(DOMICILIO);
				dto.setObj(domicilio);
				domicilio.setId(schedaService.writeIndirizzo(dto));
				
				logger.info("RESIDENZA: " + residenza.getVia() + " COD: " + residenza.getComuneCod());
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
			
			//dto.setObj(schedaAccesso);
			//schedaAccesso.setId(schedaService.saveAccesso(dto));
			
			
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
			try {
				AccessTableSchedaSegrSessionBeanRemote schedaSegrService =
					(AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	
				SchedaSegrDTO cartellaDto = new SchedaSegrDTO();
				fillUserData(cartellaDto);
				cartellaDto.setId(scheda.getId()); 
				cartellaDto.setProvenienza(DataModelCostanti.SchedaSegr.PROVENIENZA_SS);	// SISO-938 default a "SS"
				
				cartellaDto.setNuovoInserimento(Boolean.TRUE);
				cartellaDto.setEnteDestinatario(cartellaDto.getEnteId());
				cartellaDto.setIdCatSociale(TipiCategoriaSociale.ADULTI_ID); // categoria adulti 
				
				SsTipoScheda tipos = readTipoSchedaFromIdTipoScheda(scheda.getTipo());
				boolean pic =  tipos != null ? tipos.getPresa_in_carico() : false;
				cartellaDto.setTipoSchedaPropostaPic(pic);
				cartellaDto.setCf(segnalato.getAnagrafica().getCf().toUpperCase());
				cartellaDto.setEnteSchedaAccessoId(schedaAccesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getId());
				schedaSegrService.salvaSchedaSegr(cartellaDto);
				
			} catch (Exception e) {
				logger.error(e);
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
				}else{
					logger.info("CF che stiamo cercando = ["+CF+"]");
					List <PersonaDettaglio> listAnagReg=new ArrayList <PersonaDettaglio>();
					
					RicercaAnagraficaParams rab= new RicercaAnagraficaParams(DataModelCostanti.TipoRicercaSoggetto.DEFAULT,true);
					fillEnte(rab);
					rab.setCf(CF);
					try {
						RicercaAnagraficaResult result = CsUiCompBaseBean.ricercaPerDatiAnagrafici(rab);
						
						List<PersonaDettaglio> elenco = result.getElencoAssistiti();
						if(StringUtils.isBlank(result.getMessaggio()) && elenco!=null)
							listAnagReg.addAll(elenco);
						
						if(!StringUtils.isBlank(result.getMessaggio()))
							logger.error(result.getMessaggio(), result.getEccezione());
						
						//Recupero il primo
						if(listAnagReg.size()>0){
							PersonaDettaglio s = listAnagReg.get(0);
							
							Soggetto sogg = new Soggetto(s.getProvenienzaRicerca(), s.getIdentificativo(), null, s.getCognome(), s.getNome(), s.getCodfisc(), s.getDataNascita(), s.getDataMorte(), s.getSesso());
							
							SsAnagrafica ana = new SsAnagrafica();
							ana.setCf(s.getCodfisc()!=null ? s.getCodfisc().toUpperCase() : null);
							ana.setCognome(s.getCognome());
							ana.setNome(s.getNome());
							ana.setIdOrigWs(DataModelCostanti.TipoRicercaSoggetto.DEFAULT+"@"+(s.getIdentificativo()!=null ? s.getIdentificativo() : ""));
							ana.setData_nascita(s.getDataNascita());
							ana.setSesso(s.getSesso());
							ana.setStato_civile(null);
							
							if(s.getComuneNascita()!=null) {
								ana.setComuneNascitaCod(s.getComuneNascita().getCodIstatComune());
								ana.setComuneNascitaDes(s.getComuneNascita().getDenominazione());
								ana.setProvNascitaCod(s.getComuneNascita().getSiglaProv());
							} else if(s.getNazioneNascita()!=null) {
								ana.setStatoNascitaCod(s.getNazioneNascita().getCodIstatNazione());
								ana.setStatoNascitaDes(s.getComuneNascita().getDenominazione());
							}
							
							if(s.getIndirizzoResidenza()!=null){
								IndirizzoAnagDTO ind = new IndirizzoAnagDTO();
								ind.setIndirizzo(s.getIndirizzoResidenza());
								ind.setCivicoNumero(s.getCivicoResidenza());
								if(s.getComuneResidenza()!=null){
									ind.setComCod(s.getComuneResidenza().getCodIstatComune());
									ind.setComDes(s.getComuneResidenza().getDenominazione());
									ind.setProv(s.getComuneResidenza().getSiglaProv());
								}
								if(s.getNazioneResidenza()!=null){
									ind.setStatoCod(s.getNazioneResidenza().getCodIstatNazione());
									ind.setStatoDes(s.getNazioneResidenza().getNazione());
								}
								sogg.setIndirizzo(ind);
							}
							
							ana.setCittadinanza(s.getCittadinanza());
	
							BaseDTO dto = new BaseDTO();
							fillUserData(dto);
							dto.setObj(ana);
							Long id = schedaService.saveAnagrafica(dto);
							idToId.put(new Long(fields[1]), id);
							cfToId.put(CF, id);
							
						}
					} catch (SocioSanitarioException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
}
