package it.webred.ss.web.bean.report;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.abitazione.ver1.AbitazioneBean;
import it.webred.cs.json.abitazione.ver1.AbitazioneManBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.cs.json.familiariConviventi.DatiSocialiFamiliariConviventiPdfDTO;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.cs.json.stranieri.ver1.StranieriBean;
import it.webred.cs.json.stranieri.ver1.StranieriManBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.lista.soggetti.SearchBean;
import it.webred.ss.web.bean.report.beans.ArrivoInItalia;
import it.webred.ss.web.bean.report.beans.ArrivoInRegione;
import it.webred.ss.web.bean.report.beans.CondizioneGiuridica;
import it.webred.ss.web.bean.report.beans.ConoscenzaLinguaItaliana;
import it.webred.ss.web.bean.report.beans.JRBeanDataSourceReportScheda;
import it.webred.ss.web.bean.report.beans.LinguaAttestato;
import it.webred.ss.web.bean.report.beans.LinguaAutovalutazione;
import it.webred.ss.web.bean.report.beans.PresenzaInItalia;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@ViewScoped
public class ReportSchedaBean extends ReportBaseBean {

	private static final String JASPER_PATH = "/reports/SS.jasper";

	private Long selSchedaId;
	private Long ufficioId;



	public ReportSchedaBean() {
	}



	public ReportSchedaBean(Long schedaId, Long ufficioId) {
		this.selSchedaId = schedaId;
		this.ufficioId = ufficioId;
	}



	private boolean initPDFScheda() {

		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility
					.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(selSchedaId);
			SsScheda s = schedaService.readScheda(dto);
			IFamConviventi famMan = super.getSchedaJsonFamConviventi(s.getId());

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			this.initLoghiReport(parameters);

			// accesso
			SsSchedaAccesso accesso = s.getAccesso();
			parameters.put("data_colloquio", accesso.getData() != null ? ddMMyyyy.format(accesso.getData()) : "");
			parameters.put("operatore", getCognomeNomeUtente(accesso.getOperatore()));
			parameters.put("sede", accesso.getSsRelUffPcontOrg().getSsUfficio().getNome());
			SsPuntoContatto pc = accesso.getSsRelUffPcontOrg().getSsPuntoContatto();
			parameters.put("puntoContatto", pc.getNome());
			String orgRif = accesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getNome();
			orgRif = accesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getBelfiore() != null ? "Comune di " + orgRif
					: orgRif;
			parameters.put("organizzazione", orgRif);
			parameters.put("modalita", accesso.getModalita());

			parameters.put("motivo", getMotivoDesc(accesso));
			parameters.put("indirizzoPContatto", pc.getIndirLocalizzazione() != null ? pc.getIndirLocalizzazione() : "");
			parameters.put("telefonoPContatto", pc.getTel() != null ? "Tel. " + pc.getTel() : "");
			parameters.put("emailPContatto", pc.getMail() != null ? "e-mail: " + pc.getMail() : "");

			parameters.put("interlocutore", getInterlocutoreDesc(accesso));

			// segnalante
			SsSchedaSegnalante segnalante = s.getSegnalante();
			if (segnalante != null) {
				parameters.put("showInterlocutore", true);
				
				it.webred.cs.csa.ejb.dto.BaseDTO csdto = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(csdto);
				csdto.setObj(segnalante.getCodStatoCivile());
				CsTbStatoCivile csTbStatoCivile = configService.getStatoCivileByCodice(csdto);
				
				csdto.setObj(segnalante.getRelazioneId());
				CsTbTipoRapportoCon csTbTipoRelazine = configService.getTipoRapportoConByCodice(csdto);
				
				
				csdto.setObj(segnalante.getCsOSettoreId());
				CsOSettore settore = configService.getSettoreById(csdto);

				parameters.put("cognome_segnalante", format(segnalante.getCognome()).toUpperCase());
				parameters.put("nome_segnalante", format(segnalante.getNome()).toUpperCase());
				if (segnalante.getVia() != null || segnalante.getComune() != null)
					parameters.put("indirizzo_segnalante",
							format(segnalante.getVia()) + ", " + format(getDescrizioneComune(segnalante.getComune())));
				
				String tel = format(segnalante.getTelefono());
				String cel = format(segnalante.getCel());
				parameters.put("tel_segnalante", (!tel.isEmpty() && !cel.isEmpty()) ? tel+ "/" + cel : (tel+" "+cel).trim() );
				
				parameters.put("sesso_segnalante", format(segnalante.getSesso()));
				parameters.put("luogo_nascita_segnalante", format(segnalante.getLuogoDiNascita()));
				parameters.put("data_nascita_segnalante",
						segnalante.getDataNascita() != null ? ddMMyyyy.format(segnalante.getDataNascita()) : "");
				parameters.put("sc_segnalante", csTbStatoCivile != null ? format(csTbStatoCivile.getDescrizione()) : "");
				parameters.put("email_segnalante", format(segnalante.getEmail()));
				parameters.put("relazione_segnalante", csTbTipoRelazine!=null ? csTbTipoRelazine.getDescrizione() : "");
				parameters.put("ente_segnalante", settore!=null ? settore.getNome() : segnalante.getEnte_servizio());
				parameters.put("ruolo_segnalante", format(segnalante.getRuolo()));
				
			} else
				parameters.put("showInterlocutore", false);

			// segnalato
			dto.setObj(s.getSegnalato());
			SsSchedaSegnalato segnalato = schedaService.readSegnalatoById(dto);
			SsAnagrafica anagrafica = segnalato.getAnagrafica();
			parameters.put("cognome", format(anagrafica.getCognome()).toUpperCase());
			parameters.put("nome", format(anagrafica.getNome()).toUpperCase());

			String residenza = segnalato.getSenzaFissaDimora() != null && segnalato.getSenzaFissaDimora()
					? DataModelCostanti.SENZA_FISSA_DIMORA + " " : "";
			residenza += this.getDescrizioneIndirizzo(segnalato.getResidenza());

			parameters.put("residenza", residenza);

			parameters.put("sesso", anagrafica.getSesso());

			parameters.put("luogo_nascita", format(anagrafica.getLuogoDiNascita()));
			parameters.put("data_nascita",
					anagrafica.getData_nascita() != null ? ddMMyyyy.format(anagrafica.getData_nascita()) : "");

			String tel = format(segnalato.getTelefono());
			tel += segnalato.getTitolareTelefono() != null ? " (" + format(segnalato.getTitolareTelefono()) + ")" : "";
			parameters.put("tel", tel);

			String cel = format(segnalato.getCel());
			cel += segnalato.getTitolareCellulare() != null ? " (" + format(segnalato.getTitolareCellulare()) + ")" : "";
			parameters.put("cel", cel);

			parameters.put("cf", anagrafica.getCf());

			String cittadinanza = anagrafica.getCittadinanza();
			CsTbCittadinanzaAcq cittAcq = this.getCittadinanzaAcq(anagrafica.getCittadinanzaAcq());
			if (cittAcq != null)
				cittadinanza += " (" + cittAcq.getDescrizione() + ")";

			parameters.put("cittadinanza", format(cittadinanza));
			parameters.put("cittadinanza2", format(anagrafica.getCittadinanza2()));

			SearchBean search = new SearchBean();
			parameters.put("conosciuto", format(search.isInEnteEsterno(anagrafica.getCf())));
			parameters.put("in_carico", format(search.isInCs(anagrafica.getCf())));

			FormazioneLavoroMan formLavoroMan = new FormazioneLavoroMan();
			formLavoroMan
					.setIdCondLavorativa(segnalato.getLavoro() != null ? new BigDecimal(segnalato.getLavoro()) : null);
			formLavoroMan.setIdProfessione(
					segnalato.getProfessione() != null ? new BigDecimal(segnalato.getProfessione()) : null);
			formLavoroMan.setIdTitoloStudio(segnalato.getTitoloStudioId());
			formLavoroMan.setIdSettoreImpiego(segnalato.getSettImpiegoId());
			// formLavoroMan.valorizzaDescrizioni();

			parameters.put("condLavoro", format(formLavoroMan.getLavoro()));
			parameters.put("professione", format(formLavoroMan.getProfessione()));
			parameters.put("titoloStudio", format(formLavoroMan.getTitoloStudio()));
			parameters.put("settoreImpiego", format(formLavoroMan.getSettImpiego()));
			parameters.put("medico", format(segnalato.getMedico()));

			// riferimento
			SsSchedaRiferimento rif = s.getRiferimento();
			if (rif != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO dtoCS = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dtoCS);

				dtoCS.setObj(rif.getCodStatoCivile());
				CsTbStatoCivile csTbStatoCivile = configService.getStatoCivileByCodice(dtoCS);

				dtoCS.setObj(rif.getRelazioneId());
				CsTbTipoRapportoCon csTbTipoRelazine = configService.getTipoRapportoConByCodice(dtoCS);

				parameters.put("cognome_rif", format(rif.getCognome()).toUpperCase());
				parameters.put("nome_rif", format(rif.getNome()).toUpperCase());
				parameters.put("problemi_rif", format(rif.getProblemi_desc()));
				parameters.put("indirizzo_rif", format(rif.getRecapito()));
				parameters.put("tel_rif", format(rif.getTelefono()));
				parameters.put("cel_rif", format(rif.getCel()));
				parameters.put("sesso_rif", format(rif.getSesso()));
				parameters.put("luogo_nascita_rif", format(rif.getLuogoDiNascita()));
				parameters.put("data_nascita_rif",
						rif.getDataNascita() != null ? ddMMyyyy.format(rif.getDataNascita()) : "");
				parameters.put("sc_rif", csTbStatoCivile != null ? format(csTbStatoCivile.getDescrizione()) : "");
				parameters.put("parentela_rif", csTbTipoRelazine != null ? format(csTbTipoRelazine.getDescrizione()) : "");
				parameters.put("email_rif", format(rif.getEmail()));
			} else {
				parameters.put("cognome_rif", "");
				parameters.put("nome_rif", "");
				parameters.put("parentela_rif", "");
				parameters.put("problemi_rif", "");
				parameters.put("indirizzo_rif", "");
				parameters.put("tel_rif", "");
				parameters.put("cel_rif", "");
				parameters.put("sesso_rif", "");
				parameters.put("luogo_nascita_rif", "");
				parameters.put("data_nascita_rif", "");
				parameters.put("sc_rif", "");
				parameters.put("email_rif", "");
			}

			// motivo
			String temp = "";
			dto.setObj(s.getMotivazione());
			List<SsMotivazioniSchede> motivazioni = schedaService.readMotivazioniScheda(dto);
			for (SsMotivazioniSchede m : motivazioni)
				temp += m.getMotivazione().getMotivo() + "\n";
			parameters.put("motivi", temp);
			parameters.put("motivo_altro", format(s.getMotivazione().getAltro()));

			// servizi
			temp = "";
			if (s.getInterventi() != null) {
				dto.setObj(s.getInterventi());
				List<SsInterventiSchede> interventi = schedaService.readInterventiScheda(dto);
				for (SsInterventiSchede i : interventi)
					temp += i.getIntervento().getIntervento() + "\n";
				parameters.put("interventi", temp);
				parameters.put("intervento_altro", format(s.getInterventi().getAltro()));
			}

			// tipo
			dto.setObj(s.getTipo());
			SsTipoScheda tipo = schedaService.readTipoSchedaById(dto);
			parameters.put("tipi", format(tipo!=null ? tipo.getTipo() : null));

			// diario
			dto.setObj(anagrafica.getCf());
			List<SsAnagrafica> anagrafiche = schedaService.readAnagraficheByCf(dto);

			String diario = "";
			for (SsAnagrafica ana : anagrafiche) {
				dto.setObj(ana);
				List<SsDiario> note = schedaService.readDiarioSociale(dto);
				for (SsDiario nota : note)
					if (canReadNotaDiario(nota, accesso.getOperatore())) {
						String ente = nota.getEnte() != null ? nota.getEnte().getNome() : "";

						diario += "DATA: " + (nota.getData() != null ? ddMMyyyyhhmmss.format(nota.getData()) : " ") + "\n";
						diario += "AUTORE: " + this.getCognomeNomeUtente(nota.getAutore()) + "\n";
						diario += "ENTE: " + ente + "\n";
						diario += "PUBBLICA: " + format(nota.getPubblica()) + "\n";
						diario += "NOTA: " + nota.getNota() + "\n\n";
					}
			}

			if (!diario.isEmpty())
				parameters.put("diario", diario);

			// siso-437 [sm]
			IAbitazione iab = super.getSchedaJsonAbitazione(s.getId());
			AbitazioneManBean abitazioneManBean = (AbitazioneManBean) iab;
			AbitazioneBean abitazioneBean = abitazioneManBean.getJsonCurrent();

			parameters.put("abitazione_tipologia", abitazioneBean.getTipoAbitazione());
			parameters.put("abitazione_num_vani", abitazioneBean.getNumVani());
			parameters.put("abitazione_titolo_godimento", abitazioneBean.getTitoloGodimento());
			parameters.put("abitazione_prop_o_gestore", abitazioneBean.getProprietarioGestore());
			parameters.put("abitazione_note", abitazioneBean.getNote());

			JRBeanDataSourceReportScheda jrBeanDsReport = new JRBeanDataSourceReportScheda();

			famMan.fillReport(jrBeanDsReport.getFamiliariEConviventi());
	
			// opzionalità della sezione per i cittadini stranieri

			IStranieri iStranieri = getSchedaJsonStranieri(s.getId());
			if (iStranieri != null) {
				parameters.put("presenzaInItalia", true);
				StranieriManBaseBean stranBaseManBean = (StranieriManBaseBean) iStranieri;
				StranieriBean beanStranieri = ((StranieriManBean) stranBaseManBean).getJsonCurrent();

				PresenzaInItalia presenzaInItalia = jrBeanDsReport.getPresenzaInItalia();
				ConoscenzaLinguaItaliana conoscenzaLinguaItaliana = presenzaInItalia.getConoscenzaLinguaItaliana();
				conoscenzaLinguaItaliana.setBambinoInEtaNonScolastica(beanStranieri.isEtaNonScolastica());
				conoscenzaLinguaItaliana.setAttestatoOautovalutazione(beanStranieri.isLinguaItaAttestato());

				if (conoscenzaLinguaItaliana.isAttestatoOautovalutazione()) {
					LinguaAttestato linguaAttestato = conoscenzaLinguaItaliana.getLinguaAttestato();
					linguaAttestato.setComune(beanStranieri.getComuneRilascio().getDenominazione());
					linguaAttestato.setLivello(beanStranieri.getLiguaItaLivello());
					linguaAttestato.setRilasciatoDa(beanStranieri.getIstitutoRilascio());
				} else {
					LinguaAutovalutazione linguaAutovalutazione = conoscenzaLinguaItaliana.getLinguaAutovalutazione();
					linguaAutovalutazione.setComprensione(Integer.toString(beanStranieri.getLinguaItaComprensione()));
					linguaAutovalutazione.setLettura(Integer.toString(beanStranieri.getLinguaItaLettura()));
					linguaAutovalutazione.setParlato(Integer.toString(beanStranieri.getLinguaItaParlato()));
					linguaAutovalutazione.setScrittura(Integer.toString(beanStranieri.getLinguaItaScrittura()));
				}
				conoscenzaLinguaItaliana.setAltreLingueConosciute(beanStranieri.getAltreLingue());

				CondizioneGiuridica condizioneGiuridica = presenzaInItalia.getCondizioneGiuridica();
				if (beanStranieri.getStatus().getDescrizione() != null) {
					condizioneGiuridica.setStatus(beanStranieri.getStatus().getDescrizione());
				}
				condizioneGiuridica.setPresenteDaPiuDi3mesi(beanStranieri.isPresenteDaOltre3Mesi());
				condizioneGiuridica.setTitoloDiSoggiorno("cg titolo di soggiorno");
				if (beanStranieri.getPermesso().getDescrizione() != null) {
					condizioneGiuridica.setTipoDiPermesso(beanStranieri.getPermesso().getDescrizione());
				}
				if (beanStranieri.getIdPraticaPermesso() != null) {
					condizioneGiuridica.setPermessoNumId(beanStranieri.getIdPraticaPermesso());
				}

				Date scadPermessoSogg = beanStranieri.getScadPermessoSogg();
				if (scadPermessoSogg != null) {
					final java.text.DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
					condizioneGiuridica.setPermessoScadenza(DF.format(scadPermessoSogg));
				}

				presenzaInItalia.setMinoreStranieroNonAccompagnato(beanStranieri.getMinoreNonAccompagnato());
				if (beanStranieri.getNazioneOrigine().getDescrizione() != null) {
					presenzaInItalia.setPaeseOrigineNucleoFamiliare(beanStranieri.getNazioneOrigine().getDescrizione());
				}
				presenzaInItalia.setCondizioneGiuridica(condizioneGiuridica);

				ArrivoInItalia arrivoInItalia = presenzaInItalia.getArrivoInItalia();
				arrivoInItalia.setPresenza(beanStranieri.getArrivoItalia());
				arrivoInItalia.setAnnoPrimoArrivo(beanStranieri.getAnnoPrimoArrivoITA());
				arrivoInItalia.setDaSolo(beanStranieri.isDaSoloPrimoArrivoITA());
				arrivoInItalia.setAnnoPrimoPermessoDiSoggiorno(beanStranieri.getAnnoPrimoPermSogg());

				ComuneBean beanComuneValicoDiFrontiera = beanStranieri.getComuneValicoFrontiera();
				if (beanComuneValicoDiFrontiera != null) {
					arrivoInItalia.setValicoDiFrontiera(beanComuneValicoDiFrontiera.getDenominazione());
				}

				KeyValueDTO ultimoPaeseProvenienza = beanStranieri.getUltimaNazioneProvenienza();
				if (ultimoPaeseProvenienza != null && ultimoPaeseProvenienza.getDescrizione() != null) {
					arrivoInItalia.setUltimoPaeseDiProvenienza(ultimoPaeseProvenienza.getDescrizione());
				}

				ArrivoInRegione arrivoInRegione = presenzaInItalia.getArrivoInRegione();
				arrivoInRegione.setPresenza(beanStranieri.getArrivoRegione());
				arrivoInRegione.setRichiedenteOTitolareProtezioneInternazionale(beanStranieri.isProtezioneInternazionale());
				ComuneBean comuneTitolareSprar = beanStranieri.getComuneUltimoArrivoREG();
				if (comuneTitolareSprar != null) {
					arrivoInRegione.setComuneTitolareSprar(comuneTitolareSprar.getDenominazione());
				}
				arrivoInRegione.setStrutturaResidenzialeDiAccoglienza(beanStranieri.getNomeStruttAccoglienza());
				arrivoInRegione.setIndirizzo(beanStranieri.getIndirizzoStruttAccoglienza());
			}

			List<IServizioRichiestoCustom> iServizioRichiestoCustomList = loadSchedaJsonServiziRichiestiCustom(s.getId());

			//delete
			StringBuilder sbListaTipiIntervento = new StringBuilder();
			for (IServizioRichiestoCustom src : iServizioRichiestoCustomList) {
				// siso-659
				ServizioRichiestoCustomManBaseBean srcmb = (ServizioRichiestoCustomManBaseBean)src;
				jrBeanDsReport.getServiziRichiestiCustomList().add(srcmb.getJsonCurrent());
				
				// ~siso-659
				
				//delete
				sbListaTipiIntervento.append(src.getTipoInterventoCustom()).append("\n");
			}
			//delete
			parameters.put("servizi_custom", sbListaTipiIntervento.toString());

			// Bean dataset DATASOURCE			
			Set<JRBeanDataSourceReportScheda> dataSet = new HashSet<JRBeanDataSourceReportScheda>();
			dataSet.add(jrBeanDsReport);
			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(dataSet);			
			
			// FILL
			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(JASPER_PATH);
			jasperprint = JasperFillManager.fillReport(reportPath, parameters, beanDataSource);

			// ~siso-437

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("pdf.error");
		}
		return false;
	}



	private List<IServizioRichiestoCustom> loadSchedaJsonServiziRichiestiCustom(Long id) {
		List<IServizioRichiestoCustom> result = new ArrayList<IServizioRichiestoCustom>();
		try {
			List<CsDValutazione> listValutazione = getSchedeValutazione(id,
					DataModelCostanti.TipoDiario.RICHIESTA_SERVIZIO_ID);
			for (CsDValutazione csDValutazione : listValutazione) {
				IServizioRichiestoCustom man = (IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean
						.initByModel(csDValutazione
								//,false
								);
				result.add(man);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SegretariatoSocBaseBean.addError("lettura.error");
		}

		return result;
	}



	protected List<CsDValutazione> getSchedeValutazione(Long schedaId, int tipoDiario) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		new SegretariatoSocBaseBean().fillUserData(dto);
		dto.setObj(schedaId);
		dto.setObj2(tipoDiario);

		AccessTableDiarioSessionBeanRemote diarioService;
		diarioService = getDiarioCsBean();
		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
		if (schede == null || schede.isEmpty()) {
			schede = new ArrayList<CsDValutazione>();
		}

		return schede;
	}



	public StreamedContent getFile() {
		StreamedContent fileScheda = null;
		if (selSchedaId == null) {
			printSelectError();
		} else {
			boolean canAccessUfficio = this.canAccessUfficio(this.ufficioId);
			if (canAccessUfficio) {
				logAction(print, selSchedaId);

				/*
				 * dataTable = new DataTable();
				 * 
				 * Map<String, String> theFilterValues = dataTable.getFilters();
				 */
				if (initPDFScheda()) {
					try {
						String tempPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(PDF_PATH);
						JasperExportManager.exportReportToPdfFile(jasperprint, tempPath);
						InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext()
								.getContext()).getResourceAsStream(PDF_PATH);
						fileScheda = new DefaultStreamedContent(stream, "application/pdf", "scheda.pdf");
					} catch (JRException e) {
						logger.error(e);
					}
				}
			} else
				printPolicyUfficiError();
		}

		return fileScheda;
	}



	public Long getSelSchedaId() {
		return selSchedaId;
	}



	public Long getUfficioId() {
		return ufficioId;
	}



	public void setSelSchedaId(Long selSchedaId) {
		this.selSchedaId = selSchedaId;
	}



	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}

}
