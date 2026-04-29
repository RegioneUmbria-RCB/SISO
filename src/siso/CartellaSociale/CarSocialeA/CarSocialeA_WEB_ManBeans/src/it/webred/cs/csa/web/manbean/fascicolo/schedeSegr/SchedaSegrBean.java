package it.webred.cs.csa.web.manbean.fascicolo.schedeSegr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiSchedeSegr;
import it.webred.cs.data.DataModelCostanti.Scheda;
import it.webred.cs.data.DataModelCostanti.TabUDC;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsSchedeAltraProvenienza;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.interfaces.ISchedaSegr;
import it.webred.cs.jsf.manbean.ConsensoPrivacyMan;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.jsf.manbean.por.DatiPorSchedaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;
import it.webred.cs.json.OrientamentoLavoro.OrientamentoLavoroManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.intermediazione.IIntermediazioneAb;
import it.webred.cs.json.intermediazione.IntermediazioneManBaseBean;
import it.webred.cs.json.mediazioneculturale.IMediazioneCult;
import it.webred.cs.json.mediazioneculturale.MediazioneCultManBaseBean;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;
import it.webred.cs.json.orientamentoistruzione.OrientamentoIstruzioneManBaseBean;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.ct.config.model.AmTabComuni;
//import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.SchedaUdcDTO;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;

/**
 * 
 * <h1>SchedaSegrBean.java</h1>
 *
 * <p>
 * </p>
 *
 * @since 1.26.12
 * @version 1.0.1
 * 
 * @lastUpdate 2025-11-12 - DDV
 */
@ManagedBean
@SessionScoped
public class SchedaSegrBean extends CsUiCompBaseBean implements ISchedaSegr {
	
	protected HashMap<String, String> mappaLabelUDC;
	private SchedaUdcDTO scheda;
	
	private FormazioneLavoroMan formLavoroSegnalato;
	private IStranieri stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	private CsSchedeAltraProvenienza vistaCasiAltri; // SISO-938
	
	private ConsensoPrivacyMan consensoPrivacyMan;

	private DatiPorSchedaMan iDatiPor;
	
	private List<ISchedaValutazione> lstServiziRichiesti; // SISO-438-Possibilità di allegare documenti in UdC
	
	private CsTbCittadinanzaAcq cittadinanzaAcq;
	
	private AmTabComuni comuneSegnalante;
	
	private SsSchedaSessionBeanRemote ssSchedaSegrService = (SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	private	AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	private AccessTableDatiPorSessionBeanRemote porService = (AccessTableDatiPorSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDatiPorSessionBean");
	
	public void initialize(Long sId) {
		
		logger.debug("*** INIZIO chiamata SchedaSegrBean.initialize");
		this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		this.scheda = null;
		this.vistaCasiAltri = null;
		this.comuneSegnalante = new AmTabComuni();
		
/*		lavoroSegnalato = new CsTbCondLavoro();
		professioneSegnalato = new CsTbProfessione();
		tipoFamigliaSegnalato = new CsTbTipologiaFamiliare();*/

		//Serve per la stampa
		if (sId != null) {
			BaseDTO baseDTO = new BaseDTO();
			fillEnte(baseDTO);
			baseDTO.setObj(sId);
			
			CsSsSchedaSegr csSsSchedaSegr = this.schedaSegrService.findSchedaSegrCreataByIdAnagrafica(baseDTO);
			if (csSsSchedaSegr != null) {
				if (csSsSchedaSegr.getProvenienza().equals(DataModelCostanti.SchedaSegr.PROVENIENZA_SS)) {
					caricaDettagliSchedaSegr(csSsSchedaSegr.getSchedaId());
				} else {
					caricaDettagliAltri(csSsSchedaSegr.getSchedaId(), csSsSchedaSegr.getProvenienza());
				}
			}
			
		} else {
			logger.warn("SchedaSegrBean- initialize() - IdAnagrafica non valorizzato.");
		}
		
		logger.debug("*** FINE chiamata SchedaSegrBean.initialize");
	}
	
	/**
	 * 
	 * <h1>caricaDettagliSchedaSegr</h1>
	 *
	 * <p>
	 * SISO-938: action Info per PROVENIENZA == 'SS'
	 * </p>
	 *
	 * @param idSchedaSegr
	 *
	 * @since 1.26.12
	 * @version 1.0.1
	 * 
	 * @lastUpdate 2025-11-11 - DDV
	 */
	public void caricaDettagliSchedaSegr(Long idSchedaSegr) {
		logger.debug("INIT caricaDettagliSchedaSegr " + idSchedaSegr);
		
		try {
		
			it.webred.ss.ejb.dto.BaseDTO bDto = new it.webred.ss.ejb.dto.BaseDTO();
			fillEnte(bDto);
			bDto.setObj(idSchedaSegr);
			/*Per ricerca noteDiario*/
			bDto.setOrganizzazione(getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId());
			bDto.setObj2(canReadDiarioSS());
			this.scheda = this.ssSchedaSegrService.loadSchedaUdcCompleta(bDto);

			if (this.scheda != null) {
				if (this.scheda.getScheda()!=null && 
						this.scheda.getScheda().getSegnalante() != null && 
							this.scheda.getScheda().getSegnalante().getComune() != null) {
					this.comuneSegnalante = luoghiService.getComuneItaByIstat(this.scheda.getScheda().getSegnalante().getComune());
				}

				SsSchedaSegnalato ssSchedaSegnalato = this.scheda.getSegnalato();
				if (ssSchedaSegnalato != null && ssSchedaSegnalato.getAnagrafica() != null) {
					
					this.formLavoroSegnalato = new FormazioneLavoroMan();
					this.formLavoroSegnalato.setIdCondLavorativa(ssSchedaSegnalato.getCondLavoroId());
					this.formLavoroSegnalato.setIdProfessione(ssSchedaSegnalato.getProfessioneId());
					this.formLavoroSegnalato.setIdTitoloStudio(ssSchedaSegnalato.getTitoloStudioId());
					this.formLavoroSegnalato.setIdSettoreImpiego(ssSchedaSegnalato.getSettImpiegoId());
					
					this.stranieriMan = super.getSchedaJsonStranieri(idSchedaSegr);
					this.abitazioneMan = super.getSchedaJsonAbitazione(idSchedaSegr);
					this.famConviventiMan = super.getSchedaJsonFamConviventi(idSchedaSegr);
					
					List<CsDValutazione> listCsDValutazione = getSchedeJsonInterventiCustom(idSchedaSegr);
					this.lstServiziRichiesti = new ArrayList<ISchedaValutazione>();
					
					for (CsDValutazione csDValutazione : listCsDValutazione) {

						if (TipoDiario.INTERMEDIAZIONE_AB_ID == csDValutazione.getCsDDiario().getCsTbTipoDiario().getId()) {
							IIntermediazioneAb intermediazioneAbMan = (IIntermediazioneAb) IntermediazioneManBaseBean.initByModel(csDValutazione);
							if (intermediazioneAbMan != null) {
								this.lstServiziRichiesti.add(intermediazioneAbMan);
							}
						}
						
						if (TipoDiario.ORIENTAMENTO_LAVORO_ID == csDValutazione.getCsDDiario().getCsTbTipoDiario().getId()) {
							IOrientamentoLavoro orientamentoLavMan = (IOrientamentoLavoro) OrientamentoLavoroManBaseBean.initByModel(csDValutazione);
							if (orientamentoLavMan != null) {
								this.lstServiziRichiesti.add(orientamentoLavMan);
							}
						}
						
						if (TipoDiario.MEDIAZIONE_CULT_ID == csDValutazione.getCsDDiario().getCsTbTipoDiario().getId()) {
							IMediazioneCult mediazioneCultMan = MediazioneCultManBaseBean.initByModel(csDValutazione);
							if (mediazioneCultMan != null) {
								this.lstServiziRichiesti.add(mediazioneCultMan);
							} 
						}
						
						if (TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID == csDValutazione.getCsDDiario().getCsTbTipoDiario().getId()) {
							IOrientamentoIstruzione orientamentoIstruzioneMan = (IOrientamentoIstruzione) OrientamentoIstruzioneManBaseBean.initByModel(csDValutazione);
							if (orientamentoIstruzioneMan != null) {
								this.lstServiziRichiesti.add(orientamentoIstruzioneMan);
							}
						}
						
						if (TipoDiario.RICHIESTA_SERVIZIO_ID == csDValutazione.getCsDDiario().getCsTbTipoDiario().getId()) {
							IServizioRichiestoCustom iServizioRichiestoCustom = 
									(IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(csDValutazione, null);
							this.lstServiziRichiesti.add(iServizioRichiestoCustom);
						}
					}
					
//					tipoFamigliaSegnalato = this.valorizzaTipoFamiglia(ssSchedaSegnalato.getTipologia_familiare());

					this.cittadinanzaAcq = this.valorizzaCittadinanzaAcq(ssSchedaSegnalato.getAnagrafica().getCittadinanzaAcq());
				
					Long organizzaioneAccessoId = this.scheda.getScheda().getAccesso().getSsRelUffPcontOrg().getId().getOrganizzazioneId();
					boolean beneficiarioRdC = this.verificaBeneficiarioRdC(ssSchedaSegnalato.getAnagrafica().getCf());
					this.consensoPrivacyMan = new ConsensoPrivacyMan
							( ssSchedaSegnalato.getAnagrafica().getCf()
							, organizzaioneAccessoId
							, ssSchedaSegnalato.getAnagrafica().isAnonimo()
							, beneficiarioRdC
							);
					this.consensoPrivacyMan.setSchedaUdcId(idSchedaSegr);
					
					if (this.scheda.getScheda() != null) {
						Long schedaId = this.scheda.getScheda().getId();
						
						BaseDTO baseDTO = new BaseDTO();
						fillEnte(baseDTO);
						baseDTO.setObj(schedaId);
						
						String enteId = baseDTO.getEnteId();
						BigDecimal idCondizioneLavorativa = this.formLavoroSegnalato.getIdCondLavorativa();
						
						CsExtraFseDatiLavoro csExtraFseDatiLavoro = this.porService.findDatiPorUdcBySchedaId(baseDTO);
						if (csExtraFseDatiLavoro != null) {
							this.iDatiPor = new DatiPorSchedaMan(csExtraFseDatiLavoro, enteId, idCondizioneLavorativa);
						} else {
							this.iDatiPor = new DatiPorSchedaMan(enteId, idCondizioneLavorativa);
						}
					}
					
				}

			} else {
				logger.warn("Nessuna Scheda UdC con id: " + idSchedaSegr);
			}
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
		
		logger.debug("END caricaDettagliSchedaSegr " + idSchedaSegr);
	}

	// SISO-938: action Info per PROVENIENZA == 'SS'
	public void caricaDettagliAltri(Long id, String provenienza) {
		BaseDTO csDto = new BaseDTO();
		fillEnte(csDto);

		csDto.setObj(id);
		csDto.setObj2(provenienza);

		this.vistaCasiAltri = this.schedaSegrService.findVistaCasiAltriBySchedaIdProvenienza(csDto);
	}

	public boolean isRenderSchedaSegr() {
		return checkPermesso(PermessiSchedeSegr.ITEM, PermessiSchedeSegr.VISUALIZZA_SCHEDE_SEGR) && this.scheda.getScheda() != null;
	}

	public CsSchedeAltraProvenienza getVistaCasiAltri() {
		return vistaCasiAltri;
	}
	
	public void setVistaCasiAltri(CsSchedeAltraProvenienza vistaCasiAltri) {
		this.vistaCasiAltri = vistaCasiAltri;
	}

	@Override
	public SsSchedaSegnalato getSsSchedaSegnalato() {
		return this.scheda != null ? this.scheda.getSegnalato() : null;
	}

	@Override
	public AmTabComuni getComuneSegnalante() {
		return comuneSegnalante;
	}

	public void setComuneSegnalante(AmTabComuni comuneSegnalante) {
		this.comuneSegnalante = comuneSegnalante;
	}

	private CsTbCittadinanzaAcq valorizzaCittadinanzaAcq(Long id) {
		try {
			
			if (id != null) {
				BaseDTO baseDTO = new BaseDTO();
				CsUiCompBaseBean.fillEnte(baseDTO);
				baseDTO.setObj(id);
				return confService.getCittadinanzaAcqById(baseDTO);
			}
		
		} catch(Exception e) {
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		return new CsTbCittadinanzaAcq();
	}
	
	public CsTbCittadinanzaAcq getCittadinanzaAcq() {
		return cittadinanzaAcq;
	}

	public void setCittadinanzaAcq(CsTbCittadinanzaAcq cittadinanzaAcq) {
		this.cittadinanzaAcq = cittadinanzaAcq;
	}

	public FormazioneLavoroMan getFormLavoroSegnalato() {
		return formLavoroSegnalato;
	}

	public void setFormLavoroSegnalato(FormazioneLavoroMan formLavoroSegnalato) {
		this.formLavoroSegnalato = formLavoroSegnalato;
	}

	public IStranieri getStranieriMan() {
		return stranieriMan;
	}

	public void setStranieriMan(IStranieri stranieriMan) {
		this.stranieriMan = stranieriMan;
	}

	public IAbitazione getAbitazioneMan() {
		return abitazioneMan;
	}

	public void setAbitazioneMan(IAbitazione abitazioneMan) {
		this.abitazioneMan = abitazioneMan;
	}

	public IFamConviventi getFamConviventiMan() {
		return famConviventiMan;
	}

	public void setFamConviventiMan(IFamConviventi famConviventiMan) {
		this.famConviventiMan = famConviventiMan;
	}
	
	@Override
	public String getStatoCivileSegnalante() {
		return getDescrizioneStatoCivile(this.scheda.getScheda().getSegnalante().getCodStatoCivile());
	}

	@Override
	public String getStatoCivileRiferimento() {
		return getDescrizioneStatoCivile(this.scheda.getScheda().getRiferimento().getCodStatoCivile());
	}
	
	@Override
	public String getRelazioneSegnalante() {
		return getDescrizioneRelazione(this.scheda.getScheda().getSegnalante().getRelazioneId());
	}

	@Override
	public String getRelazioneRiferimento() {
		return getDescrizioneRelazione(this.scheda.getScheda().getRiferimento().getRelazioneId());
	}

	@Override
	public String getInviatoDaAccesso() {
		String s = "";
		if (this.getSsScheda() != null) {
			SsSchedaAccesso accesso = this.getSsScheda().getAccesso();
			s = accesso != null && accesso.getSettoreInviante() != null ? accesso.getSettoreInviante().getNome() : "";
		}
		return s;
	}

	private String getDescrizioneStatoCivile(String codStatoCivile) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if (codStatoCivile != null) {
			dto.setObj(codStatoCivile);
			CsTbStatoCivile csTbStatoCivile = confService.getStatoCivileByCodice(dto);
			
			return csTbStatoCivile != null ? csTbStatoCivile.getDescrizione() : "";
		} else {
			return "";
		}
	}
	
	private String getSettore(String codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if (codice != null) {
			dto.setObj(new Long(codice));
			CsOSettore tb = confEnteService.getSettoreById(dto);
			return tb != null ? tb.getNome() : "";
		}
		return null;
	}
	
	private String getDescrizioneRelazione(Long codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if (codice != null) {
			dto.setObj(codice);
			CsTbTipoRapportoCon tb = confService.getTipoRapportoConByCodice(dto);
			return tb != null ? tb.getDescrizione() : "";
		} else {
			return "";
		}
	}
	
	private CsOSettore getDescrizioneSettore(Long codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if (codice != null) {
			dto.setObj(codice);
			return confEnteService.getSettoreById(dto);
		}
		return null;
	}
	
	public String getMSG_INFO_NOTA_PRIVATA() {
		return DataModelCostanti.UDC_MSG_INFO_NOTA_PRIVATA;
	}

	@Override
	public String getEnteSegnalante() {
		CsOSettore s = getDescrizioneSettore(getSsScheda().getSegnalante().getCsOSettoreId());
		return s != null ? s.getNome() : getSsScheda().getSegnalante().getEnte_servizio();
	}

	@Override
	public boolean isHideSegnalante() {
		return this.scheda != null && getSsScheda() != null && Scheda.Interlocutori.UTENTE.equalsIgnoreCase(getSsScheda().getAccesso().getInterlocutore()) && getSsScheda().getSegnalante() == null;
	}

	public String titoloTabRiferimento(SsSchedaRiferimento riferimento, Integer numRiferimento) {
		String titolo = "Riferimento " + Integer.toString(numRiferimento);
					
		if (riferimento != null) {
			if (riferimento.getNome() != null
					&& !riferimento.getNome().trim().equals("")
					&& riferimento.getCognome() != null
					&& !riferimento.getCognome().trim().equals("")) {

				titolo = riferimento.getCognome() + " " + riferimento.getNome();
			}
		}
		
		return titolo;
	}
	
	public String relazioneRif (SsSchedaRiferimento riferimento) {
		String relazione = "";
		if (riferimento != null) {
			relazione = getDescrizioneRelazione(riferimento.getRelazioneId());
		}
		
		return relazione;
	}
	
	public String statoCivileRif (SsSchedaRiferimento riferimento) {
		String statoCivile = "";
		if (riferimento != null) {
			statoCivile = getDescrizioneStatoCivile(riferimento.getCodStatoCivile());
		}
		
		return statoCivile;
	}
	
	@Override
	public String getLabelAccesso() {
		if (this.mappaLabelUDC == null)
			this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		return this.mappaLabelUDC.get(TabUDC.ACCESSO_TAB);
	}

	@Override
	public String getLabelSegnalante() {
		if (this.mappaLabelUDC == null)
			this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		return this.mappaLabelUDC.get(TabUDC.SEGNALANTE_TAB);
	}

	@Override
	public String getLabelSegnalato() {
		if (this.mappaLabelUDC == null)
			this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		return this.mappaLabelUDC.get(TabUDC.SEGNALATO_TAB);
	}

	@Override
	public String getLabelRiferimento() {
		if (this.mappaLabelUDC == null)
			this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		return this.mappaLabelUDC.get(TabUDC.RIFERIMENTO_TAB);
	}

	@Override
	public String getLabelMotivazione() {
		if (this.mappaLabelUDC == null)
			this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		return this.mappaLabelUDC.get(TabUDC.MOTIVAZIONE_TAB);
	}

	@Override
	public String getLabelInterventi() {
		if (this.mappaLabelUDC == null)
			this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		return this.mappaLabelUDC.get(TabUDC.INTERVENTI_TAB);
	}

	@Override
	public String getLabelChiusura() {
		if (this.mappaLabelUDC == null)
			this.mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		
		return this.mappaLabelUDC.get(TabUDC.CHIUSURA_TAB);
	}

	public ConsensoPrivacyMan getConsensoPrivacyMan() {
		return consensoPrivacyMan;
	}

	public void setConsensoPrivacyMan(ConsensoPrivacyMan consensoPrivacyMan) {
		this.consensoPrivacyMan = consensoPrivacyMan;
	}

	public DatiPorSchedaMan getiDatiPor() {
		return iDatiPor;
	}

	public void setiDatiPor(DatiPorSchedaMan iDatiPor) {
		this.iDatiPor = iDatiPor;
	}
		
	public StreamedContent getFilePrivacy() {
		DatiPrivacyPdfDTO dati = null;
		if (this.scheda != null && this.scheda.getDatiPrivacyPDF() != null) {
			dati = this.scheda.getDatiPrivacyPDF();
		}
		
		ReportBean bean = (ReportBean) CsUiCompBaseBean.getReferencedBean("ReportBean");
		if (bean == null)
			bean = new ReportBean();
		
		return bean.getStampaPrivacy(dati, this.getLabelSegnalante(), this.getLabelSegnalato(), this.getLabelRiferimento());
	}

	public List<ISchedaValutazione> getLstServiziRichiesti() {
		return lstServiziRichiesti;
	}

	public void setLstServiziRichiesti(List<ISchedaValutazione> lstServiziRichiesti) {
		this.lstServiziRichiesti = lstServiziRichiesti;
	}
	
	@Override
	public boolean servizioRendered(ISchedaValutazione schedaValutazione, String tipo) {
		if (tipo.equals("IIntermediazioneAb") && schedaValutazione instanceof IIntermediazioneAb) {
			return true;
		}

		if (tipo.equals("IOrientamentoLavoro") && schedaValutazione instanceof IOrientamentoLavoro) {
			return true;
		}

		if (tipo.equals("IMediazioneCult") && schedaValutazione instanceof IMediazioneCult) {
			return true;
		}

		if (tipo.equals("IOrientamentoIstruzione") && schedaValutazione instanceof IOrientamentoIstruzione) {
			return true;
		}

		if (tipo.equals("IServizioRichiestoCustom") && schedaValutazione instanceof IServizioRichiestoCustom) {
			return true;
		}
		
		return false;
	}

	@Override
	public SchedaUdcDTO getScheda() {
		return scheda;
	}

	public void setScheda(SchedaUdcDTO scheda) {
		this.scheda = scheda;
	}

	@Override
	public SsScheda getSsScheda() {
		return this.scheda != null ? this.scheda.getScheda() : null;
	}
	
}
