package it.webred.cs.csa.web.manbean.fascicolo.altri;

import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.bean.InitAltriSoggettiBean;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.jsf.interfaces.IAltriSoggetti;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;

import java.util.ArrayList;
import java.util.List;

public class AltriSoggettiBean extends CsUiCompBaseBean implements
		IAltriSoggetti {

	private AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb ( "AccessTableSoggettoSessionBean");
	private AnagrafeService anagrafeService = (AnagrafeService) getEjb("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
	
	private CsASoggettoLAZY csASoggetto;
	private List<CsCCategoriaSociale> catsocCorrenti;

	private List<CsASoggettoLAZY> listaAltriSoggetti;
	private List<CsASoggettoLAZY> listaAltriSoggettiDaFamiglia;
	private String labelSoggetto;

	public void initialize(CsASoggettoLAZY soggetto,List<CsCCategoriaSociale> catsocCorrenti, Object data) {

		try {

			if (soggetto != null) {
				csASoggetto = soggetto;

				labelSoggetto = csASoggetto.getCsAAnagrafica().getDenominazione();
				this.catsocCorrenti = catsocCorrenti;

				caricaAltriSoggetti(data);
			}

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}

	private void caricaAltriSoggetti(Object data) {
		
		InitAltriSoggettiBean altriSoggetti =null;
		if (data!= null) {
			altriSoggetti = (InitAltriSoggettiBean) data;
		}

		if (listaAltriSoggetti == null)
			listaAltriSoggetti = new ArrayList<CsASoggettoLAZY>();
		if (listaAltriSoggettiDaFamiglia == null)
			listaAltriSoggettiDaFamiglia = new ArrayList<CsASoggettoLAZY>();

		BaseDTO b = new BaseDTO();
		fillEnte(b);

		List<CsAComponente> famigliaComponenti = new ArrayList<CsAComponente>();
		List<SitDPersona> listaFamiglia_anagrafe;
		if (altriSoggetti!= null) { // mi è arrivato il dato dal parametro data
			if(altriSoggetti.getListaComponenti()!=null)
				famigliaComponenti = altriSoggetti.getListaComponenti();
			listaFamiglia_anagrafe = altriSoggetti.getListaFamiglia_anagrafe();
		} else {
			RicercaSoggettoAnagrafeDTO rs = new RicercaSoggettoAnagrafeDTO();
			fillEnte(rs);
			rs.setCodFis(csASoggetto.getCsAAnagrafica().getCf());
			listaFamiglia_anagrafe = anagrafeService.getFamigliaByCF(rs);
			famigliaComponenti = CsUiCompBaseBean.caricaParenti(csASoggetto.getAnagraficaId(), null);
		}		

		if(listaFamiglia_anagrafe!=null){
			for (SitDPersona persona : listaFamiglia_anagrafe) {
				b.setObj(persona.getCodfisc());
				CsASoggettoLAZY altroSoggetto = soggettoService.getSoggettoByCF(b);
				if (altroSoggetto != null)
					listaAltriSoggetti.add(altroSoggetto);
			}
		}

		for (CsAComponente componente : famigliaComponenti) {
			b.setObj(componente.getCsAAnagrafica().getCf());
			CsASoggettoLAZY altroSoggetto = soggettoService.getSoggettoByCF(b);
			// se lo trovo ha una cartella
			if (altroSoggetto != null)
				listaAltriSoggettiDaFamiglia.add(altroSoggetto);
		}
	}

	public List<CsCCategoriaSociale> getCatsocCorrenti() {
		return catsocCorrenti;
	}

	public void setCatsocCorrenti(List<CsCCategoriaSociale> catsocCorrenti) {
		this.catsocCorrenti = catsocCorrenti;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public List<CsASoggettoLAZY> getListaAltriSoggetti() {
		return listaAltriSoggetti;
	}

	@Override
	public void setListaAltriSoggetti(List<CsASoggettoLAZY> listaAltriSoggetti) {
		this.listaAltriSoggetti = listaAltriSoggetti;
	}

	@Override
	public String getLabelSoggetto() {
		return labelSoggetto;
	}

	public void setLabelSoggetto(String labelSoggetto) {
		this.labelSoggetto = labelSoggetto;
	}

	public List<CsASoggettoLAZY> getListaAltriSoggettiDaFamiglia() {
		return listaAltriSoggettiDaFamiglia;
	}

	public void setListaAltriSoggettiDaFamiglia(
			List<CsASoggettoLAZY> listaAltriSoggettiDaFamiglia) {
		this.listaAltriSoggettiDaFamiglia = listaAltriSoggettiDaFamiglia;
	}

	@Override
	public boolean isShowPanel() {
		return listaAltriSoggetti.size() > 1 || listaAltriSoggettiDaFamiglia.size() > 0;
	}

}
