package it.webred.cs.json.provvedimentiMinori.ver1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.provvedimentiMinori.ProvvedimentiMinoriManBaseBean;
import it.webred.cs.json.provvedimentiMinori.ProvvedimentiMinoriRowBean;
import it.webred.cs.json.provvedimentiMinori.ver1.tabs.DatiProvvedimentoMan;
import it.webred.cs.json.provvedimentiMinori.ver1.tabs.IntSpecialistiTempiChiusureMan;
import it.webred.cs.json.provvedimentiMinori.ver1.tabs.InterventiTutelaMan;
import it.webred.cs.json.provvedimentiMinori.ver1.tabs.PrescrizioniSocioEducativeMan;
import it.webred.cs.json.provvedimentiMinori.ver1.tabs.PrescrizioniSpecialisticheMan;

public class ProvvedimentiMinoriManBean extends ProvvedimentiMinoriManBaseBean {

	private static final long serialVersionUID = 1L;

	AccessTableSchedaSessionBeanRemote schedaService;
	private ProvvedimentiMinoriController controller;

	private List<SelectItem> lstParenti = null;

	// Manager dei TABS
	private DatiProvvedimentoMan datiProv;
	private InterventiTutelaMan interTutela;
	private PrescrizioniSpecialisticheMan prescSpec;
	private PrescrizioniSocioEducativeMan prescSocEd;
	private IntSpecialistiTempiChiusureMan intSpecTemp;

	public ProvvedimentiMinoriManBean() {
		controller = new ProvvedimentiMinoriController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
	
		//Inizializzazione dei manager dei tabs  
			datiProv = new DatiProvvedimentoMan();
			interTutela = new InterventiTutelaMan();
			prescSocEd=new PrescrizioniSocioEducativeMan();
			intSpecTemp=new IntSpecialistiTempiChiusureMan();
		if(getUser()!= null && getCurrentOpSettore() != null){//SE NON CHIAMATO DAL TASK GIORNALIERO
			prescSpec=new PrescrizioniSpecialisticheMan();
		}		

	}

	private void loadCommonList() {
		if (this.lstParenti == null) {
			List<CsAComponente> lstComponenti = new ArrayList<CsAComponente>();
			this.lstParenti = new ArrayList<SelectItem>();
			
			lstComponenti = CsUiCompBaseBean.caricaParenti(soggetto.getAnagraficaId(), null);
			
			for (CsAComponente item : lstComponenti) {
				if (item.getCsTbTipoRapportoCon() != null && item.getCsTbTipoRapportoCon().getParente())
					this.lstParenti.add(new SelectItem(item.getId(), item.getCsTbTipoRapportoCon().getDescrizione()));
			}
		}

	}

	public ProvvedimentiMinoriBean getJsonCurrent() {

		return controller.getJsonCurrent();
	}

	@Override
	public void init(ISchedaValutazione bean) {
		try {
			controller.load((ProvvedimentiMinoriBean) bean.getSelected());
			valorizzaDatiBase(bean.getIdCaso(), bean.getIdSchedaUdc());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void init(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId());
			this.prescSpec.loadDocumento(diario);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void initRowList(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	private void valorizzaDatiBase(Long idCaso, Long idSchedaUdc){
		setIdCaso(idCaso);
		setIdSchedaUdc(idSchedaUdc);
		this.datiProv.valorizzaComponenteMan(this.getJsonCurrent().getDatiProvvedimenti());
		//TODO:Valorizzare gli altri Manager
		
	}
	
	/*Serve per il Task Giornaliero*/
	@Override
	public void initRowListTask(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			setIdCaso(idCaso);
			setIdSchedaUdc(diario.getSchedaId());
		} catch (Exception e) {
			logger.error("initRowListTask [SCHEDA:"+scheda.getCsDDiario().getId()+"]:"+e.getMessage(), e);
			//addErrorFromProperties("caricamento.error");
		}
	}

	@Override
	public JsonBaseBean getSelected() {
		return getJsonCurrent();
	}

	@Override
	public boolean elimina() {
		boolean ok = false;
		try {
			prescSpec.eliminaDocumenti(controller.getDataModel().getCsDDiario());
			controller.elimina();
			addInfoFromProperties("elimina.ok");
			ok = true;
		} catch (CarSocialeServiceException cse) {
			addMessage("Errore di eliminazione", cse.getMessage(), FacesMessage.SEVERITY_ERROR);
			logger.error(cse.getMessage(), cse);
		} catch (Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public void setIdCasoController(Long idCaso) {
		controller.setCasoId(idCaso);
	}

	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);
	}

	@Override
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}

	@Override
	public boolean isNew() {
		return !(controller.getDiarioId() != null && controller.getDiarioId().longValue() > 0);
	}

	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}

	public ProvvedimentiMinoriController getController() {
		return this.controller;
	}

	@Override
	public DatiProvvedimentoMan getDatiProv() {
		return datiProv;
	}

	@Override
	public PrescrizioniSpecialisticheMan getPrescSpec() {
		return prescSpec;
	}

	@Override
	public PrescrizioniSocioEducativeMan getPrescSocEd() {

		return prescSocEd;
	}

	@Override
	public InterventiTutelaMan getInterTutela() {

		return interTutela;
	}

	@Override
	public IntSpecialistiTempiChiusureMan getIntSpecTemp() {

		return intSpecTemp;
	}

	public Date getScadenzaAdempimento(){
		
		return getJsonCurrent().getInterventiTempiChiusure().getScadenzaAdempimento();
	}
	
	public Date  getScadenzaAggiornamento(){
		
		return getJsonCurrent().getInterventiTempiChiusure().getScadenzaAggiornamento();
	}
	
	@Override
	public boolean save(Long visSecondoLivello){
		this.controller.setVisSecondoLivello(visSecondoLivello);
		return this.save();
	}
	
	@Override
	public boolean save() {

		datiProv.valorizzaJson(this.getJsonCurrent().getDatiProvvedimenti());

		boolean ok = false;
		try {
			if (validaData()) {

				controller.save(this.getClass().getName());
				ok = true;
				
				this.prescSpec.salvaDocumento(controller.getDataModel().getCsDDiario());
				
				// ora salva
				addInfoFromProperties("salva.ok");
			}
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public boolean validaData() {

		return true;
	}

	@Override
	public void restore() {
		controller.restore();
	}

	@Override
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
		this.datiProv.init(soggetto.getAnagraficaId());
		// setIdCaso(soggetto.getCsACaso().getId());
		loadCommonList();
		this.interTutela.setLstParenti(this.lstParenti);
	}

	public void valorizzaRowList(ProvvedimentiMinoriRowBean row){
		String[] tipoAffidamento =  this.getJsonCurrent().getDatiProvvedimenti().getSelTipoAffidamento();
		row.setAffidoComune(false);
		for (String s : tipoAffidamento) {
			if (s.equals(DatiProvvedimentoMan.AFFIDAMENTO_COMUNE))
				row.setAffidoComune(true);
		}
		row.setDataProvv(this.getJsonCurrent().getDatiProvvedimenti().getDataProtocolloProvv());
		row.setTipoProvv(this.getJsonCurrent().getDatiProvvedimenti().getSelTipoProvv());
		row.setScadenzaAdempimento(this.getJsonCurrent().getInterventiTempiChiusure().getScadenzaAdempimento());
		row.setnProvvedimento(this.getJsonCurrent().getDatiProvvedimenti().getNumProvv());
	}

}
