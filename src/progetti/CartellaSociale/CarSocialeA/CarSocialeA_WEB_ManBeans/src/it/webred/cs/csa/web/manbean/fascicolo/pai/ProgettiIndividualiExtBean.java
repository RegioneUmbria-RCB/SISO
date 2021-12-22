package it.webred.cs.csa.web.manbean.fascicolo.pai;

import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.DatiProgettoBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.InterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.relazioni.RelazioniBean;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.ComuneNascitaMan;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;
 
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;

@ManagedBean(name = "progettiInvidualiExt")
@SessionScoped
public class ProgettiIndividualiExtBean extends CsUiCompBaseBean {

	protected InterventiBean interventiBean;
	protected RelazioniBean relazioniBean;
	protected PaiBean paiBean;
	// beneficiari
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan = null;
	private DatiProgettoBean datiProgettoBean;

	protected AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb(
			"AccessTableCatSocialeSessionBean");

	public void initializeData() {
		paiBean = new PaiBean();
		paiBean.setFromFascicoloCartellaUtente(false);
		paiBean.initializeData();
		relazioniBean = new RelazioniBean();
		relazioniBean.setFromFascicoloCartellaUtente(false);
		relazioniBean.initializeExternalPai();
		interventiBean = new InterventiBean();
	}

	public void initializeData(CsPaiMastSoggDTO soggettoSelezionato) {
		this.getInterventiBean().initializeDataFromExtPai(soggettoSelezionato);
	}
	
    public void initializeData(CsASoggettoLAZY soggetto, List<CsCCategoriaSociale> catSoc) {
    	this.getInterventiBean().initializeDataFromExtPai(soggetto,catSoc);
	}
    
    public PaiBean getPaiBean() {
			return paiBean;
	}

    public PaiBean getOrInitializePaiBean() {
		if (paiBean == null) {
			paiBean = new PaiBean();
			paiBean.setFromFascicoloCartellaUtente(false);
			paiBean.initializeData();
			relazioniBean.initializeData();
		}
		return paiBean;
	}
	
	public void nuovo(DatiUserSearchBean datiUserSearchBean) {
		getOrInitializePaiBean().inizializeNuovoSoggetto(datiUserSearchBean);
		if (getPaiBean().getSoggRiferimentoPai() != null)
			getRelazioniBean().loadListaTipiIntervento(getPaiBean().getSoggRiferimentoPai().getCf());

		getPaiBean().initRisorsaFamiliareBean(getPaiBean().getSoggRiferimentoPai().getCf());
	}

	public void setPaiBean(PaiBean paiBean) {
		this.paiBean = paiBean;
	}

	public RelazioniBean getRelazioniBean() {
		if (relazioniBean == null) {
			relazioniBean = new RelazioniBean();
			relazioniBean.setFromFascicoloCartellaUtente(false);
			relazioniBean.initializeData();
		}
		return relazioniBean;
	}

	public void setRelazioniBean(RelazioniBean relazioniBean) {
		this.relazioniBean = relazioniBean;
	}

	public InterventiBean getInterventiBean() {
		if (interventiBean == null) {
			interventiBean = new InterventiBean();
			
		}
		return interventiBean;
	}

	public void setInterventiBean(InterventiBean interventiBean) {
		this.interventiBean = interventiBean;
	}

	public void onChangeBeneficiario() {
		if (getPaiBean().getSoggettoSelezionato() == null) {
			this.addWarning("Beneficiari", "Nessun soggetto selezionato.");
			return;
		}
		getPaiBean().aggiornaSoggettoRiferimento();

		// Resetto il valore, perchè dal soggettoErogazioneBean al momento non riesco a
		// recuperare il luogo di nascita
		this.comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();

		CsASoggettoLAZY soggetto = getPaiBean().getSoggRiferimentoPai().getCsASoggetto();
		if (soggetto != null && this.datiProgettoBean.isRenderFSE()) {

			String comCod = soggetto.getCsAAnagrafica().getComuneNascitaCod();
			String statoCod = soggetto.getCsAAnagrafica().getStatoNascitaCod();

			ComuneBean comuneBean = getComuneBean(comCod, soggetto.getCsAAnagrafica().getComuneNascitaDes(),
					soggetto.getCsAAnagrafica().getProvNascitaCod());
			if (comuneBean != null) {
				ComuneNascitaMan comuneNascitaBean = new ComuneNascitaMan();
				comuneNascitaBean.comune = comuneBean;
				this.comuneNazioneNascitaMan.setComuneNascitaMan(comuneNascitaBean);
			} else if (!StringUtils.isBlank(statoCod)) {
				AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(statoCod,
						soggetto.getCsAAnagrafica().getStatoNascitaDes());
				this.comuneNazioneNascitaMan.setNazioneValue();
				this.comuneNazioneNascitaMan.getNazioneMan().setNazione(amTabNazioni);
			}
		}
		datiProgettoBean.setComuneNazioneNascitaBean(comuneNazioneNascitaMan);

		String cfRiferimento = getPaiBean().getSoggRiferimentoPai()!=null ? getPaiBean().getSoggRiferimentoPai().getCf() : null;
		datiProgettoBean.onChangeBeneficiarioRiferimento(cfRiferimento, comuneNazioneNascitaMan);
	}
}
