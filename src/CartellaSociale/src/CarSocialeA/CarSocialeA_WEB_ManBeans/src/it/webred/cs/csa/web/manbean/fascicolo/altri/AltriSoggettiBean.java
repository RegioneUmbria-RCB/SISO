package it.webred.cs.csa.web.manbean.fascicolo.altri;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.jsf.interfaces.IAltriSoggetti;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;

import java.util.ArrayList;
import java.util.List;

public class AltriSoggettiBean extends CsUiCompBaseBean implements
		IAltriSoggetti {

	private AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb(
			"CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	private AnagrafeService anagrafeService = (AnagrafeService) getEjb(
			"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb(
			"CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");

	private CsASoggettoLAZY csASoggetto;
	private List<CsCCategoriaSocialeBASIC> catsocCorrenti;

	private List<CsASoggettoLAZY> listaAltriSoggetti;
	private List<CsASoggettoLAZY> listaAltriSoggettiDaFamiglia;
	private String labelSoggetto;

	public void initialize(CsASoggettoLAZY soggetto,
			List<CsCCategoriaSocialeBASIC> catsocCorrenti) {

		try {

			if (soggetto != null) {
				csASoggetto = soggetto;

				labelSoggetto = csASoggetto.getCsAAnagrafica().getCognome()
						+ " " + csASoggetto.getCsAAnagrafica().getNome();
				this.catsocCorrenti = catsocCorrenti;

				caricaAltriSoggetti();
			}

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}

	private void caricaAltriSoggetti() {

		if (listaAltriSoggetti == null)
			listaAltriSoggetti = new ArrayList<CsASoggettoLAZY>();
		if (listaAltriSoggettiDaFamiglia == null)
			listaAltriSoggettiDaFamiglia = new ArrayList<CsASoggettoLAZY>();

		BaseDTO b = new BaseDTO();
		fillEnte(b);

		RicercaSoggettoAnagrafeDTO rs = new RicercaSoggettoAnagrafeDTO();
		fillEnte(rs);
		rs.setCodFis(csASoggetto.getCsAAnagrafica().getCf());
		List<SitDPersona> listaFamiglia_anagrafe = anagrafeService.getFamigliaByCF(rs);

		BaseDTO dtoFindFamiglia = new BaseDTO();
		fillEnte(dtoFindFamiglia);
		dtoFindFamiglia.setObj(csASoggetto.getAnagraficaId());
		CsAFamigliaGruppo famigliaGruppo = schedaService
				.findFamigliaAllaDataBySoggettoId(dtoFindFamiglia);

		for (SitDPersona persona : listaFamiglia_anagrafe) {
			b.setObj(persona.getCodfisc());
			CsASoggettoLAZY altroSoggetto = soggettoService.getSoggettoByCF(b);
			if (altroSoggetto != null)
				listaAltriSoggetti.add(altroSoggetto);
		}

		if(famigliaGruppo!=null){
			for (CsAComponente componente : famigliaGruppo.getCsAComponentes()) {
				b.setObj(componente.getCsAAnagrafica().getCf());
				CsASoggettoLAZY altroSoggetto = soggettoService.getSoggettoByCF(b);
				// se lo trovo ha una cartella
				if (altroSoggetto != null)
					// listaAltriSoggetti.add(altroSoggetto);
					listaAltriSoggettiDaFamiglia.add(altroSoggetto);
			}
		}else
			logger.warn("Nessuna famiglia gruppo per "+csASoggetto.getCsAAnagrafica().getCf());

	}

	public List<CsCCategoriaSocialeBASIC> getCatsocCorrenti() {
		return catsocCorrenti;
	}

	public void setCatsocCorrenti(List<CsCCategoriaSocialeBASIC> catsocCorrenti) {
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

}
