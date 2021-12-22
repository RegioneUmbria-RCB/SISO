package it.webred.cs.sociosan.ejb.ejb;

import it.marche.regione.pddnica.client.DatiRichiestaISEE;
import it.marche.regione.pddnica.client.ISEESoapClient;
import it.marche.regione.pddnica.client.PrestazioneDaErogare;
import it.marche.regione.pddnica.client.RicercaCF;
import it.marche.regione.pddnica.client.StatoDomanda;
import it.siso.isee.obj.Attestazione;
import it.siso.isee.obj.ComponenteMinorenne;
import it.siso.isee.obj.Esito;
import it.siso.isee.obj.ISEEMin;
import it.siso.isee.obj.ISEEOrdinario;
import it.siso.isee.obj.Ordinario;
import it.siso.isee.obj.SicurezzaIdentificazioneMittente;
import it.siso.isee.obj.StudenteUniversitario;
import it.siso.isee.obj.Valori;
import it.webred.cs.base.CsBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.sociosan.ejb.client.RicercaIseeSessionBeanRemote;
import it.webred.cs.sociosan.ejb.dto.isee.DatiIsee;
import it.webred.cs.sociosan.ejb.dto.isee.RicercaIseeParams;
import it.webred.cs.sociosan.ejb.dto.isee.RicercaIseeResult;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;


@Stateless
public class RicercaIseeSessionBean extends CsBaseSessionBean implements RicercaIseeSessionBeanRemote  {

	private static final long serialVersionUID = 1L;

	@EJB(mappedName = "java:global/CarSocialeA/CarSocialeA_EJB/AccessTableConfigurazioneSessionBean")
	protected AccessTableConfigurazioneSessionBeanRemote configurazioneService;
	
	@Override 
	public RicercaIseeResult ricercaIsee(RicercaIseeParams params){
		logger.debug(params.stampaParametri()); 
		if(this.isRicercaIseeAbilitata()){
			return this.consultaAttestazioneIseeMarche(params);
		}
		else return null;
	}
			
	public RicercaIseeResult consultaAttestazioneIseeMarche(RicercaIseeParams params){	
		
		RicercaIseeResult result = new RicercaIseeResult();
		try {
			CredenzialiIseeWS utente = this.getCredenzialiIseeWS();
			URL url = this.getISEEDichiarazioneEndpointURL();
			if(isRicercaIseeAbilitata() &&  utente!=null && url!=null){
				
				 String par = !StringUtils.isBlank(params.getCodPrestazione()) ? params.getCodPrestazione().replaceAll("\\.", "")  : null;
				 PrestazioneDaErogare prest = null;
				 try{
					 prest = par!=null ? PrestazioneDaErogare.valueOf(par)  : null;
				 }catch(IllegalArgumentException e){
					 logger.warn("Prestazione "+params.getCodPrestazione()+" non prevista, verrà usata Z9.99 per la chiamata al web service");
				 }
				
				 RicercaCF ricercaCF = new RicercaCF();
				 ricercaCF.setCodiceFiscale(params.getCf());
				 ricercaCF.setDataValidita(params.getDataValidita()!=null ? ddMMyyyy.format(params.getDataValidita()) : null);
				 ricercaCF.setPrestazioneDaErogareEnum(prest!=null ? prest : PrestazioneDaErogare.Z999);
				 ricercaCF.setProtocolloDomandaEnteErogatore("ID Domanda: "+params.getIdCasoErogazione());
				 ricercaCF.setStatoDomandaPrestazioneEnum(StatoDomanda.DA_EROGARE);
				 
				logger.debug("consultaAttestazioneIseeMarche "
						+ "DATA_VALIDITA["+ricercaCF.getDataValidita()+"] "
						+ "CF["+ricercaCF.getCodiceFiscale()+"] "
						+ "COD.PRESTAZIONE["+ricercaCF.getPrestazioneDaErogare()+"] "
						+ "PROTOCOLLO["+ricercaCF.getProtocolloDomandaEnteErogatore()+"] "
						+ "STATO_DOMANDA["+ricercaCF.getStatodomandaPrestazione()+"] ");
				
				 DatiRichiestaISEE datiRichiesta = new DatiRichiestaISEE();
				 datiRichiesta.setAction("");
				 
				 SicurezzaIdentificazioneMittente sicurezzaIdentificazioneMittente = new SicurezzaIdentificazioneMittente();
				 sicurezzaIdentificazioneMittente.setCfOperatore(utente.getUsername());
				 sicurezzaIdentificazioneMittente.setCodiceEnte(utente.getEnte());
				 sicurezzaIdentificazioneMittente.setCodiceUfficio(utente.getUfficio());
				 
				 datiRichiesta.setMittente(sicurezzaIdentificazioneMittente);
				 
				Esito esito = null;
						
				datiRichiesta.setEndPoint(url+"/ConsultazioneAttestazione");
				ISEESoapClient client = new ISEESoapClient(datiRichiesta);
				esito =  client.consultaAttestazioneCF(ricercaCF); 
				
				logger.debug("consultaAttestazioneIseeMarche ESITO:"+esito.getDescErrore());
				result.setEccezione(esito.getEccezione());
				result.setMessaggio(esito.getDescErrore());
				
				Attestazione a = esito.getAttestazione();
				List<DatiIsee> elencoAttestazioni = new ArrayList<DatiIsee>();
			
				if(a!=null){
					CeTBaseObject cet = new CeTBaseObject();
					cet.setEnteId(params.getEnteId());
					cet.setSessionId(params.getSessionId());
					cet.setUserId(params.getUserId());
					List<CsTbTipoIsee> lstTipi = configurazioneService.getListaTipoIsee(cet);
					
					Date dtPresentazione = yyyyMMdd.parse(a.getDataPresentazione());
					Date dtValidita = yyyyMMdd.parse(a.getDataValidita());
					String numProtocolloDSU = a.getNumeroProtocolloDSU();
					Ordinario o = a.getOrdinario();
					if(o!=null){
						
						ISEEOrdinario iseeOrd = o.getIseeOrdinario();
						Valori valIseeCorrente = iseeOrd.getDatiIsee().getValori();
						if(iseeOrd!=null && iseeOrd.getDatiIsee()!=null && iseeOrd.getDatiIsee().getValori()!=null){
							DatiIsee iseeCorrente = initIseeDto(dtPresentazione, dtValidita, numProtocolloDSU, valIseeCorrente, DataModelCostanti.TipoIsee.ORDINARIO, lstTipi);
							elencoAttestazioni.add(iseeCorrente);
						}
						
					   ISEEMin min = o.getIseeMin();	
					   if(min!=null && min.getComponentiMinoorenni()!=null){
						 for(ComponenteMinorenne s : min.getComponentiMinoorenni()){
							 
							 if(params.getCf().equalsIgnoreCase(s.getAttributi().getCodiceFiscale())){
								 Valori val = null;
								 /*ISEEORD Caso che coincide con ISEEOrdinario non c’è ulteriore specificazione degli indicatori perché 
								  *        già indicato nell’elemento <ISEEOrdinario> tabella 1) dell’Attestazione*/
								 if(s.getIseeOrd()!=null) val = valIseeCorrente;

								 
								 /*ISEEMinAttr
									Caso del minore con genitore non convivente che entra come componente attratta, i dati del
									genitore attratto sono indicati nell’elemento ComponenteAttratta che di ti tipo
									TypeAggiuntiva, il calcolo è indicato nell’elemento ModalitaCalcoloISEEMinAttr che
									rappresenta i dati della seguente tabella 2) dell’attestazione:*/
								 if(s.getIseeMinAttr()!=null) val = s.getIseeMinAttr().getValori();
								 
								 /*ISEEMinAgg
									 Caso del minore con genitore non convivente che entra come componente aggiuntiva, i dati
									 del genitore che entra come componente aggiuntiva sono indicati nell’elemento
									 ComponenteAggiuntiva che di ti tipo TypeAggiuntiva, il calcolo è indicato nell’elemento
									 ModalitaCalcoloISEEMinAgg che rappresenta i dati della seguente tabella 3)
									 dell’attestazione:*/
								 if(s.getIseeMinAgg()!=null) val = s.getIseeMinAgg().getValori();
								 
								 /*ISEENonCalcolabile
								 Caso del minore con genitore non convivente che entra come componente aggiuntiva, ma i
								 dati della componente aggiuntiva non sono stai forniti.*/
								 if(s.getIseeNonCalcolabile()!=null){}
								 
								 if(val!=null){
									 DatiIsee isee = initIseeDto(dtPresentazione, dtValidita, numProtocolloDSU, valIseeCorrente, DataModelCostanti.TipoIsee.MINORENNI, lstTipi);
									 elencoAttestazioni.add(isee);
								 }
							 }
					   }
						}
						
						List<StudenteUniversitario> lst = o.getStudenteUniversitario();
						if(lst!=null && !lst.isEmpty()){
							for(StudenteUniversitario s : lst){
								if(params.getCf().equalsIgnoreCase(s.getAttributi().getCodiceFiscale())){
									Valori val = null;
									 /*ISEEORD Caso che coincide con ISEEOrdinario non c’è ulteriore specificazione degli indicatori perché 
									  *        già indicato nell’elemento <ISEEOrdinario> tabella 1) dell’Attestazione*/
									 if(s.getIseeOrd()!=null) val = valIseeCorrente;
									 
									 /*ISEEUniAttr
										Caso dello studente con genitore non convivente che entra come componente attratta, i dati
										del genitore attratto sono indicati nell’elemento ComponenteAttratta che di ti tipo
										TypeAggiuntiva, il calcolo è indicato nell’elemento ModalitaCalcoloISEEMinAttr che
										rappresenta i dati della tabella di calcolo 2) dell’attestazione, già indicata sopra per il
										Minorenne.*/
									 if(s.getIseeUniAttr()!=null) val = s.getIseeUniAttr().getValori();
									 
									 /*ISEEUniAgg
										 Caso dello studente con genitore non convivente che entra come componente aggiuntiva, i
										 dati del genitore che entra come componente aggiuntiva sono indicati nell’elemento
										 ComponenteAggiuntiva che di ti tipo TypeAggiuntiva, il calcolo è indicato nell’elemento
										 ModalitaCalcoloISEEUniAgg che rappresenta i dati della tabella di calcolo 3)
										 dell’attestazione, già indicata sopra per il Minorenne.*/
									 if(s.getIseeUniAgg()!=null) val = s.getIseeUniAgg().getValori();
									 
									 /*ISEEUniStudAttratto
										Caso dello studente che viene aggregato nel nucleo dei genitori, il calcolo è indicato
										nell’elemento ModalitaCalcoloISEEUniStudAttratto che rappresenta i dati della seguente
										tabella 4) dell’attestazione:*/
									 if(s.getIseeUniStudAttratto()!=null) val = s.getIseeUniStudAttratto().getValori();
									 
									 /*ISEEUniStudAttrattoCompAttratta
										Caso dello studente che viene aggregato nel nucleo dei genitori, ma uno dei genitori è non
										convivente, pertanto entra come componente attratta nel nucleo del genitore di riferimento,
										il calcolo è indicato nell’elemento ModalitaCalcoloISEEUniStudAttrattoCompAttratta
										che rappresenta i dati della seguente tabella 5) dell’attestazione:*/
									 if(s.getIseeUniStudAttrattoCompAttratta()!=null) val = s.getIseeUniStudAttrattoCompAttratta().getValori();
									 
									 /*ISEEUniStudAttrattoCompAggiuntiva
										Caso dello studente che viene aggregato nel nucleo dei genitori, ma uno dei genitori è non
										convivente, pertanto entra come componente aggiuntiva nel nucleo del genitore di
										riferimento, il calcolo è indicato nell’elemento
										ModalitaCalcoloISEEUniStudAttrattoCompAggiuntiva che rappresenta i dati della
										seguente tabella 6) dell’attestazione:*/
									 if(s.getIseeUniStudAttrattoCompAggiuntiva()!=null) val = s.getIseeUniStudAttrattoCompAggiuntiva().getValori();

									 
									 /*ISEENonCalcolabile
									 Caso del minore con genitore non convivente che entra come componente aggiuntiva, ma i
									 dati della componente aggiuntiva non sono stai forniti.*/
									 if(s.getIseeNonCalcolabile()!=null){}
									
									 if(val!=null){
										 DatiIsee isee = initIseeDto(dtPresentazione, dtValidita, numProtocolloDSU, valIseeCorrente, DataModelCostanti.TipoIsee.UNIVERSITARIO, lstTipi);
										 elencoAttestazioni.add(isee);
									 }
								}
							}
						}
					  
					/* TODO: dato non presente nel bean di arrivo
					ISEESocioSanRes ssr = o.getIseeSocioSanRes();
					if(ssr!=null){}*/
					
					result.setElencoAttestazioni(elencoAttestazioni);
				}
			}
		}else result.setMessaggio("Non è stato possibile effettuare la ricerca in anagrafe sanitaria: parametri di connessione non impostati");
		} catch (java.lang.IllegalArgumentException iarex){
			logger.error(iarex.getMessage(), iarex);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setEccezione(e);
			result.setMessaggio(e.getMessage());
		}
		return result;
	}
	
	private DatiIsee initIseeDto(Date dataPresentazione, Date dataValidita, String sprotDSU, Valori val, Long tipoVal, List<CsTbTipoIsee> lstTipi){
		DatiIsee isee = new DatiIsee();
		isee.setDataPresentazioneIsee(dataPresentazione);
		isee.setDataScadenzaIsee(dataValidita);
		
		String[] protDSU = !StringUtils.isBlank(sprotDSU) ? sprotDSU.split("-") : null;
		if(protDSU!=null && protDSU.length==5){
			isee.setProtDSU_prefisso(protDSU[0]+"-"+protDSU[1]+"-");
			isee.setProtDSU_anno(new Integer(protDSU[2]));
			isee.setProtDSU_numero(protDSU[3]);
			isee.setProtDSU_progressivo(protDSU[4]);
		}else
			logger.error("ricercaIseeMarche: non è stato possibile splittare il numero protocollo DSU ["+sprotDSU+"]");
		
		if(val!=null){
			isee.setIse(!StringUtils.isBlank(val.getISE()) ? new BigDecimal(val.getISE()) : null);
			isee.setIsee(!StringUtils.isBlank(val.getISEE()) ? new BigDecimal(val.getISEE()) : null);
			isee.setScalaEquivalenza(!StringUtils.isBlank(val.getScalaEquivalenza()) ? new BigDecimal(val.getScalaEquivalenza()) : null);
		}
		
		int i=0;
		CsTbTipoIsee tipo = null;
		while(i<lstTipi.size() && tipo==null){
			CsTbTipoIsee tipoSel = lstTipi.get(i);
			if(tipoSel.getId().equals(tipoVal)) tipo = tipoSel;
			i++;
		}
		
		isee.setTipo(tipo);
		
		return isee;
	}
			
}