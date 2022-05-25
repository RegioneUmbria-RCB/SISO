package it.webred.cs.csa.web.manbean.fascicolo.isee;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.jsf.bean.IseeBean;
import it.webred.cs.jsf.interfaces.IIsee;
import it.webred.cs.jsf.manbean.ProtocolloDsuMan;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.isee.IIseeJson;
import it.webred.cs.json.isee.IseeManBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public class IseeFascBean extends FascicoloCompBaseBean implements IIsee {

	private IIseeJson manIsee;
	private int idxSelected;
	private List<CsTbTipoIsee> listaTooltip;
	private List<IIseeJson> listaIsee;
	
	@Override
	public void initializeData() {
		listaIsee = new ArrayList<IIseeJson>();
		try{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);

			for(CsDIsee jpa : diarioService.findIseeByCaso(dto)){
				IIseeJson man = IseeManBaseBean.initByVersion("1");
				man.init(jpa);
				listaIsee.add(man);
				//listaIsee.add(this.riversaJpaToIsee(jpa));
			}
				
			loadTipologieIsee();
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	@Override
	protected void initializeData(Object data) {
		this.initializeData();
	}
	
	private void loadTipologieIsee() {
		if(listaTooltip == null) {
			listaTooltip = new ArrayList<CsTbTipoIsee>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			listaTooltip = confService.getListaTipoIsee(cet);
		}
	}
	
	//Metodo di prova per JsonController multiversione
	private void loadSchedeJson() throws Exception{
	
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(this.getCsASoggetto().getAnagraficaId());
		dto.setObj2(DataModelCostanti.TipoDiario.ISEE_ID);
		List<CsDValutazione> schede = diarioService.getSchedeValutazioneSoggetto(dto);

		for(CsDValutazione isee : schede){
			IIseeJson manSchedaValutazione = IseeManBaseBean.initByModel(isee);
			listaIsee.add(manSchedaValutazione);
		}
		
	}
	
	@Override
	public void nuovo() {
		String defaultVersion = "1"; //La vers2 è stata creata solo per provare il funzionamento del JsonController
		try {
			manIsee = IseeManBaseBean.initByVersion(defaultVersion);
			manIsee.setIdCaso(idCaso);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void carica() {
		manIsee = listaIsee.get(idxSelected);
		
	}
	
	public void handleChangeDataISEE(javax.faces.event.AjaxBehaviorEvent event) throws ParseException {
		Date data = ((Date)((javax.faces.component.UIInput)event.getComponent()).getValue());
		if(data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			int anno = c.get(Calendar.YEAR);
			String sd = "15/01/"+(anno+1);
			Date scadenza = sdf.parse(sd);
			((IseeBean)manIsee.getSelected()).setDataScadenzaIsee(scadenza);
		}
	}
	
	public void handleChangeAnnoProtocollo(){
		manIsee.onChangeAnnoRiferimento();
	}
	
	public void handleChangeTipologia(){
		manIsee.onChangeTipologia();
	}
	
	//SISO-812
	public void salvaSecondoLivello(){
		if(this.manIsee.save(getCurrentOpSettore().getCsOSettore().getId()))
			initializeData();			
	}
	
	@Override
	public void salva() {
		if(manIsee.save())
			initializeData();
	}
	
	@Override
	public void elimina() {
		if(manIsee.elimina())
			initializeData();
	}
	
	@Override
	public List<CsTbTipoIsee> getListaTooltip(){
		return this.listaTooltip;
	}
	
	public List<SelectItem> getListaTipologie() {
		if(listaTooltip == null)
			this.loadTipologieIsee();
		
		List<SelectItem> listaTipologie = new ArrayList<SelectItem>();	
		for(CsTbTipoIsee tb : listaTooltip){
			SelectItem si = new SelectItem(tb.getId(), tb.getDescrizione());
			si.setDisabled(!tb.getAbilitato());
			listaTipologie.add(si);
		}
		return listaTipologie;
	}

	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	public List<IIseeJson> getListaIsee() {
		return listaIsee;
	}

	public void setListaIsee(List<IIseeJson> listaManIsee) {
		this.listaIsee = listaManIsee;
	}

	@Override
	public <T extends JsonBaseBean> T  getIsee() {
		return manIsee.getSelected();
	};
	
	@Override
	public ProtocolloDsuMan getProtDsuMan() {
		return manIsee!=null ? manIsee.getProtDsuMan() : null;
	}
}
