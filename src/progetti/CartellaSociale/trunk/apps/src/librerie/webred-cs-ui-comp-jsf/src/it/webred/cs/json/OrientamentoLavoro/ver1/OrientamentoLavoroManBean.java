package it.webred.cs.json.OrientamentoLavoro.ver1;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbProfessione;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.OrientamentoLavoro.OrientamentoLavoroManBaseBean;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.primefaces.context.RequestContext;

public class OrientamentoLavoroManBean extends OrientamentoLavoroManBaseBean {

	private static final long serialVersionUID = 1L;

	// **Services**//
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb( "AccessTableConfigurazioneSessionBean");

	// **FIELDS**///
	private List<SelectItem> lstProfessioni;

	private OrientamentoLavoroController controller;

	private List<SelectItem> lstCondLavorativa;
	private List<SelectItem> lstProfessioniIta;
	private List<SelectItem> lstProfessioniEstero;
	private List<SelectItem> lstProfessioniAltri;
	private List<SelectItem> lstProfessioniAttuale;

	private List<SelectItem> motivoRicorso;
	private List<SelectItem> lstYesNoRadio;
	private List<SelectItem> lstLipoPatente;
	private List<SelectItem> lstDisponiblita;
	private List<SelectItem> disponibilitaSpostamento;

	public OrientamentoLavoroManBean()
	{
		initOrientamentoLavoroManBean();
	}

	protected void initOrientamentoLavoroManBean()
	{
		controller = new OrientamentoLavoroController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());

		loadListaProfessioni();
		loadCondizioneLavorativa();
		loadListStrings();

	}

	private void loadListaProfessioni()
	{

		if (lstProfessioni == null)
		{
			lstProfessioniIta = new LinkedList<SelectItem>();
			lstProfessioniEstero = new LinkedList<SelectItem>();
			lstProfessioniAltri = new LinkedList<SelectItem>();
			lstProfessioniAttuale = new LinkedList<SelectItem>();
			lstProfessioni = new LinkedList<SelectItem>();

			lstProfessioni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbProfessione> lst = confService.getProfessioni(bo);
			if (lst != null) {
				for (CsTbProfessione obj : lst) {
					String desc = obj.getDescrizione();
					lstProfessioni.add(new SelectItem(obj.getId(), desc));

				}
			}

			getLstProfessioniIta().addAll(lstProfessioni);
			getLstProfessioniAltri().addAll(lstProfessioni);
			getLstProfessioniAttuale().addAll(lstProfessioni);
			getLstProfessioniEstero().addAll(lstProfessioni);
		}
	}

	private void loadCondizioneLavorativa()
	{
		if(lstCondLavorativa == null){
			lstCondLavorativa = new ArrayList<SelectItem>();
			CeTBaseObject  xo = new CeTBaseObject();
			fillEnte(xo);
			TreeMap<String, List<CsTbCondLavoro>> tree = confService.getMappaCondLavoro(xo);
			for(String str : tree.keySet()){
				List<CsTbCondLavoro> lst = tree.get(str);
				if (lst != null && !lst.isEmpty()) {
					SelectItemGroup gr = new SelectItemGroup(lst.get(0).getCsTbIngMercato().getDescrizione());
					List<SelectItem> siList = new ArrayList<SelectItem>();
					for (CsTbCondLavoro obj : lst) {
						SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
						boolean abilitato = obj.getAbilitato()!=null ? obj.getAbilitato().booleanValue() : Boolean.FALSE;
					    si.setDisabled(!abilitato);
						siList.add(si);
					}
					gr.setSelectItems(siList.toArray(new SelectItem[siList.size()]));
					lstCondLavorativa.add(gr);
				}		
			}
		}
	}

	private void loadListStrings()
	{

		if (motivoRicorso == null)
		{
			motivoRicorso = new LinkedList<SelectItem>();
			motivoRicorso.add(new SelectItem("Ricerca Lavoro", "Ricerca Lavoro"));
			motivoRicorso.add(new SelectItem("Controversia", "Controversia"));
			motivoRicorso.add(new SelectItem("Orientamento al lavoro Autonomo", "Orientamento al lavoro Autonomo"));

		}
		if (lstLipoPatente == null)
		{
			lstLipoPatente = new LinkedList<SelectItem>();
			lstLipoPatente.add(new SelectItem(null, "- seleziona -"));
			lstLipoPatente.add(new SelectItem("AM", "AM"));
			lstLipoPatente.add(new SelectItem("A1", "A1"));
			lstLipoPatente.add(new SelectItem("A1", "A1"));
			lstLipoPatente.add(new SelectItem("A(A3)", "A(A3)"));
			lstLipoPatente.add(new SelectItem("B1", "B1"));
			lstLipoPatente.add(new SelectItem("B", "B"));
			lstLipoPatente.add(new SelectItem("BE", "BE"));
			lstLipoPatente.add(new SelectItem("C1", "C1"));
			lstLipoPatente.add(new SelectItem("C1E", "C1E"));
			lstLipoPatente.add(new SelectItem("D1", "D1"));
			lstLipoPatente.add(new SelectItem("D1E", "D1E"));
			lstLipoPatente.add(new SelectItem("D", "D"));
			lstLipoPatente.add(new SelectItem("DE", "DE"));

		}
		if (lstDisponiblita == null)
		{
			lstDisponiblita = new LinkedList<SelectItem>();
			lstDisponiblita.add(new SelectItem("Part-Time", "Part-Time"));
			lstDisponiblita.add(new SelectItem("Full-Time", "Full-Time"));
			lstDisponiblita.add(new SelectItem("Week-End", "Week-End"));
			lstDisponiblita.add(new SelectItem("Notti", "Notti"));
			lstDisponiblita.add(new SelectItem("Giorno/Notte", "Giorno/Notte"));

		}
		if (disponibilitaSpostamento == null)
		{
			disponibilitaSpostamento = new LinkedList<SelectItem>();

			disponibilitaSpostamento.add(new SelectItem("Nella provincia", "Nella provincia"));
			disponibilitaSpostamento.add(new SelectItem("Nella regione", "Nella regione"));
			disponibilitaSpostamento.add(new SelectItem("In altre regioni", "In altre regioni"));
			disponibilitaSpostamento.add(new SelectItem("Estero", "Estero"));

		}
		if (lstYesNoRadio == null)
		{
			lstYesNoRadio = new LinkedList<SelectItem>();
			lstYesNoRadio.add(new SelectItem(true, "Si"));
			lstYesNoRadio.add(new SelectItem(false, "No"));

		}

	}

	public OrientamentoLavoroBean getJsonCurrent() {

		return controller.getJsonCurrent();
	}

	// **PRIMEFACES EVENTS**//

	public void onChangeBpatente() {
		if (this.getJsonCurrent().isbPatente())
			getLstLipoPatente();
		else
			this.getJsonCurrent().setTipoPatente(null);
	}

	public void onChangeNessunaRic() {
		if (this.getJsonCurrent().isbNessuno())
		{
			this.getJsonCurrent().setbCpi(false);
			this.getJsonCurrent().setbAmiciConoscenti(false);
			this.getJsonCurrent().setbAmiciConStranieri(false);
			this.getJsonCurrent().setbIntermediariInformali(false);
			this.getJsonCurrent().setbInterStranieri(false);
			this.getJsonCurrent().setbIntermPrivatiAutorizzati(false);
			this.getJsonCurrent().setbAltro(false);

		}
		this.getJsonCurrent().setAltroSpec("");
	}

	public void onChangeAltro()
	{
		if (!this.getJsonCurrent().isbAltro())
			this.getJsonCurrent().setAltroSpec(" ");
	}

	public void onChangeOnly()
	{
		// for onchange do no nothing only valorized bean
	}

	@Override
	public void onChangeRivoltoAqualcuno() {
		if (!getJsonCurrent().isbRivoltoAqualcuno())
		{
			this.getJsonCurrent().setbCpi(false);
			this.getJsonCurrent().setbNessuno(false);
			this.getJsonCurrent().setbAmiciConoscenti(false);
			this.getJsonCurrent().setbAmiciConStranieri(false);
			this.getJsonCurrent().setbIntermediariInformali(false);
			this.getJsonCurrent().setbInterStranieri(false);
			this.getJsonCurrent().setbIntermPrivatiAutorizzati(false);
			this.getJsonCurrent().setbAltro(false);
			this.getJsonCurrent().setAltroSpec("");
		}

	}

	// ****GETTERS AND SETTERS***///
	@Override
	public List<SelectItem> getMotivoRicorso() {
		return motivoRicorso;
	}

	public void setMotivoRicorso(List<SelectItem> motivoRicorso) {
		this.motivoRicorso = motivoRicorso;
	}

	@Override
	public List<SelectItem> getLstProfessioniIta() {
		return lstProfessioniIta;
	}

	public void setLstProfessioniIta(List<SelectItem> lstProfessioniIta) {
		this.lstProfessioniIta = lstProfessioniIta;
	}

	@Override
	public List<SelectItem> getLstProfessioniEstero() {
		return lstProfessioniEstero;
	}

	public void setLstProfessioniEstero(List<SelectItem> lstProfessioniEstero) {
		this.lstProfessioniEstero = lstProfessioniEstero;
	}

	@Override
	public List<SelectItem> getLstProfessioniAltri() {
		return lstProfessioniAltri;
	}

	public void setLstProfessioniAltri(List<SelectItem> lstProfessioniAltri) {
		this.lstProfessioniAltri = lstProfessioniAltri;
	}

	@Override
	public List<SelectItem> getLstProfessioniAttuale() {
		return lstProfessioniAttuale;
	}

	public void setLstProfessioniAttuale(List<SelectItem> lstProfessioniAttuale) {
		this.lstProfessioniAttuale = lstProfessioniAttuale;
	}

	@Override
	public List<SelectItem> getLstCondLavorativa() {
		return lstCondLavorativa;
	}

	public void setLstCondLavorativa(List<SelectItem> lstCondLavorativa) {
		this.lstCondLavorativa = lstCondLavorativa;
	}

	@Override
	public List<SelectItem> getLstLipoPatente() {
		return lstLipoPatente;
	}

	public void setLstLipoPatente(List<SelectItem> lstLipoPatente) {
		this.lstLipoPatente = lstLipoPatente;
	}

	@Override
	public List<SelectItem> getLstDisponiblita() {
		return lstDisponiblita;
	}

	public void setLstDisponiblita(List<SelectItem> lstDisponiblita) {
		this.lstDisponiblita = lstDisponiblita;
	}

	@Override
	public List<SelectItem> getLstYesNoRadio() {
		return lstYesNoRadio;
	}

	public void setLstYesNoRadio(List<SelectItem> lstYesNoRadio) {
		this.lstYesNoRadio = lstYesNoRadio;
	}

	@Override
	public List<SelectItem> getDisponibilitaSpostamento() {
		return disponibilitaSpostamento;
	}

	public void setDisponibilitaSpostamento(List<SelectItem> disponibilitaSpostamento) {
		this.disponibilitaSpostamento = disponibilitaSpostamento;
	}

	// *******Methods*******

	@Override
	public void init(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			Long idCaso = scheda.getCsDDiario().getCsACaso() != null ? scheda.getCsDDiario().getCsACaso().getId() : null;
			setIdCaso(idCaso);
			setIdSchedaUdc(scheda.getCsDDiario().getSchedaId());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}

	@Override
	public void init(ISchedaValutazione bean){
		try{
			controller.load((OrientamentoLavoroBean)bean.getSelected());
			setIdCaso(bean.getIdCaso());
			setIdSchedaUdc(bean.getIdSchedaUdc());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public boolean save(Long visSecondoLivello){
		this.controller.setVisSecondoLivello(visSecondoLivello);
		return this.save();
	}
	
	@Override
	public boolean save() {
		boolean ok = false;
		try {
			if(!validaData())
			 return ok;

			controller.save(this.getClass().getName());

			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			ok = true;

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public boolean elimina() {
		boolean ok = false;
		try {
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
	public void restore() {
		controller.restore();

	}


	@Override
	public <T extends JsonBaseBean> T getSelected() {
		return controller.getJsonCurrent();
	}

	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);

	}

	public OrientamentoLavoroController getController() {
		return this.controller;
	}

	@Override
	public void setIdCasoController(Long idCaso) {
		controller.setCasoId(idCaso);

	}

	@Override
	public boolean isNew() {
		return !(controller.getDiarioId() != null && controller.getDiarioId().longValue() > 0);
	}

	@Override
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}

	@Override
	public boolean validaData() {
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = controller.validaData();
		if( messagges.size() > 0 ) {
			addWarning(controller.getDescrizioneScheda(), messagges);
			validazioneOk &= false;
		}
		return validazioneOk;
	}

	@Override
	public void preValorizzaLavoro(BigDecimal idLavoro) {
		this.getJsonCurrent().setSelectedCondLavorativa(idLavoro);
	}

	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}
}
