package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoUtils.SumDTO;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InterventoErogazHistoryBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");

	private HashMap<Long,List<InterventoErogazAttrBean>> mappaAttrsErog;
	//private  List<InterventoErogazAttrBean> attrsErog;
	private InterventoErogazHistoryHeaderBean header;
	private List<InterventoErogazHistoryRowBean> rows;
	private List<InterventoErogazHistoryRowBean> deleteErogaz;
	private List<String> totals = new LinkedList<String>();

	//private Integer totalsValuesSize = 0;
	private boolean visualizzaStorico = false;
	private Long csIInterventoId;
	private Long idErogazioneMaster;
	private CsCTipoIntervento csCTipoIntervento;
	private Long categoriaSocialeId;
	
	private CsCTipoInterventoCustom csCTipoInterventoCustom;
	//frida
	private List<CsIInterventoEseg> listaInterEseg =new ArrayList<CsIInterventoEseg>();
	
	public CsCTipoIntervento getCsCTipoIntervento() {
		return csCTipoIntervento;
	}	

	public CsCTipoInterventoCustom getCsCTipoInterventoCustom() {
		return csCTipoInterventoCustom;
	}

	public InterventoErogazHistoryBean() {
		deleteErogaz = deleteErogaz==null ?  new LinkedList<InterventoErogazHistoryRowBean>() : deleteErogaz;
		rows = rows==null ?  new LinkedList<InterventoErogazHistoryRowBean>() : rows;
		//attrsErog = attrsErog==null ?  new LinkedList<InterventoErogazAttrBean>() : attrsErog;
		this.mappaAttrsErog = mappaAttrsErog==null ?  new HashMap<Long, List<InterventoErogazAttrBean>>() : mappaAttrsErog;
	
	}

	//frida
	public List<CsIInterventoEseg> initialize(HashMap<Long,List<InterventoErogazAttrBean>> attrsErog, CsCTipoIntervento tipoIntervento, CsCTipoInterventoCustom tipoInterventoCustom, Long categoriaSocialeId) {
		csIInterventoId = null;
		this.csCTipoIntervento = tipoIntervento;
		if(tipoInterventoCustom!=null) this.csCTipoInterventoCustom=tipoInterventoCustom;
		this.categoriaSocialeId = categoriaSocialeId;
		setup(attrsErog);
		return listaInterEseg;
	}
	
	public List<CsIInterventoEseg> initialize(HashMap<Long,List<InterventoErogazAttrBean>> attrsErog, CsIIntervento csIIntervento) {
		csIInterventoId = csIIntervento.getId();
		
		return initialize(attrsErog, csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento(), 
				          csIIntervento.getCsIInterventoCustom(), 
				          csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCCategoriaSociale().getId() );
	}
	
	public List<CsIInterventoEseg> initialize(HashMap<Long,List<InterventoErogazAttrBean>> attrsErog, CsIInterventoEsegMast master) {
		this.idErogazioneMaster = master.getId();
		
		return initialize(attrsErog, master.getCsCTipoIntervento(), 
									 master.getCsIInterventoCustom(), 
									 master.getCategoriaSocialeId());
	}

	private void setup(HashMap<Long,List<InterventoErogazAttrBean>> attrsErog) {
		this.deleteErogaz = new LinkedList<InterventoErogazHistoryRowBean>();
		this.mappaAttrsErog = attrsErog;
		//this.attrsErog = attrsErog;
		buildHistoryHeader();
		builHistoryRows();
		
		//finally force change UnitaMisuras to avoid different values with different measure unit
		calcTotals();
		forceSingolaUnitaMisura(attrsErog);
	}

	private void buildHistoryHeader() {
		this.header = new InterventoErogazHistoryHeaderBean();
		this.header.initialize(mappaAttrsErog);
	}

	public void rimuoviRiga(InterventoErogazHistoryRowBean row) {
		if (!row.isNuovaErogazione()) {	//BUG FIX ERR1:2016/10/26  SISO-495
			this.deleteErogaz.add(row);			
		}
		rows.remove(row);
		calcTotals();
	}

	protected void forceSingolaUnitaMisura(List<InterventoErogazAttrBean> erogaziones) {
		for (InterventoErogazAttrBean attr : erogaziones) {
			if( attr.isMultipleUnitaMisura() && attr.getValue() != null && attr.getSelectedUnitaMisura() != null )
				attr.forceSingolaUnitaMisura( attr.getSelectedUnitaMisura() );
		}
	}
	
	protected void forceSingolaUnitaMisura(HashMap<Long,List<InterventoErogazAttrBean>> mappa) {
		Iterator<Long> it = mappa.keySet().iterator();
		while(it.hasNext()){
			Long statoID = it.next();
			List<InterventoErogazAttrBean> erogaziones = mappa.get(statoID);
			if(erogaziones!=null){
				forceSingolaUnitaMisura(erogaziones);
				mappa.put(statoID, erogaziones);
			}
		}
	}

	protected void calcTotals() {
  
		HashMap<String, SumDTO> mappaSomme = new HashMap<String, SumDTO>();
		
		for (InterventoErogazHistoryRowBean row : rows) {
			List<InterventoErogazAttrBean> cells = row.getCells();
			for (InterventoErogazAttrBean cell : cells) {
				IntEsegAttrBean attr = cell.getAttr();
				if( attr != null && attr.getCalcTotale()){
					String label = attr.getAttributo().getLabel();
					SumDTO sold = mappaSomme.get(label)!=null ? mappaSomme.get(label) : new SumDTO();
					SumDTO sum = ErogazioneInterventoUtils.sum( cell, sold );
					mappaSomme.put(label, sum);
				}
			}
		}
		
		
		this.totals = new LinkedList<String>();
		for( String s : header.getColumns()){
			SumDTO sum = mappaSomme.get(s);
			this.totals.add( sum!=null ? sum.getTotaleUnitaMisura() : null );
		}
	}

	private void builHistoryRows() {
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);

		this.rows = new LinkedList<InterventoErogazHistoryRowBean>();
		
		if(this.csIInterventoId != null && this.idErogazioneMaster==null){
			bDto.setObj(csIInterventoId);
			idErogazioneMaster = this.interventoService.getCsIInterventoEsegMastIdByInterventoId(bDto);
		}
		
		if(this.idErogazioneMaster!=null){
			bDto.setObj(this.idErogazioneMaster);
			bDto.setObj2(true); //loadDettagli
			listaInterEseg = this.interventoService.getInterventoEsegByMasterId(bDto);
		}else{return;}
				
		Long currTipoIntervento = this.csCTipoInterventoCustom!=null ?  csCTipoInterventoCustom.getId() : (this.getCsCTipoIntervento()!=null ? this.getCsCTipoIntervento().getId() : null);
		bDto.setObj(currTipoIntervento);
		HashMap<Long, ErogStatoCfgDTO>  mappa = interventoService.findConfigIntEsegByTipoIntervento(bDto);
		
		this.rows = new LinkedList<InterventoErogazHistoryRowBean>();
		for (CsIInterventoEseg csIInterventoEseg : listaInterEseg) {
			CsIQuota quota = csIInterventoEseg.getCsIInterventoEsegMast().getCsIQuota();
			String um = quota!=null ? quota.getCsTbUnitaMisura().getValore() : null;
			InterventoErogazHistoryRowBean curHistoryRow = new InterventoErogazHistoryRowBean(header, csIInterventoEseg, mappa, um);
			this.rows.add(curHistoryRow);
		}

		visualizzaStorico = this.rows.size() > 0;
	}

	protected boolean canAdd(CsCfgIntEsegStato newStato) {
		if (rows.size() == 0)
			return true;

		boolean bAdd = true;
		InterventoErogazHistoryRowBean row = rows.get(0);
		if (row.getStato() != null) {
			CsCfgIntEsegStato currentStato = row.getStato();
			if (!currentStato.getErogazionePossibile() && DataModelCostanti.TipoStatoErogazione.EROGATIVO.equals(newStato.getTipo())  )
				bAdd = false;
		}

		return bAdd;
	}

	public void addNew(CsOSettore sett, String descrizioneErogazione, CsCfgIntEsegStato stato, CsIInterventoEseg nuovoCsIntervento, HashMap<Long,List<InterventoErogazAttrBean>> mappa, String unitaMisura) {
		if (canAdd(stato)) {
			//TODO
			List<InterventoErogazAttrBean> erogaziones = mappa.get(stato.getId());
			InterventoErogazHistoryRowBean newRow = new InterventoErogazHistoryRowBean(header, sett, stato, nuovoCsIntervento, erogaziones, unitaMisura);
			rows.add(newRow);
			Collections.sort(rows, new InterventoErogazHistoryRowBean.RowComparator());
			calcTotals();
			if (erogaziones != null)
				forceSingolaUnitaMisura(erogaziones);
		} else {
			addError("L'ultimo stato inserito non consente di erogare", "");
		}
	}

	public InterventoErogazHistoryHeaderBean getHeader() {
		return header;
	}

	public List<InterventoErogazHistoryRowBean> getRows() {
		return rows;
	}

	public List<String> getTotals() {
		return totals;
	}

	public AccessTableInterventoSessionBeanRemote getInterventoService() {
		return interventoService;
	}

	public void setInterventoService(AccessTableInterventoSessionBeanRemote interventoService) {
		this.interventoService = interventoService;
	}

	public List<InterventoErogazHistoryRowBean> getDeleteErogaz() {
		return deleteErogaz;
	}

	public void toString(StringBuilder sb) {
		this.header.toString(sb);

		for (InterventoErogazHistoryRowBean row : this.rows) {
			row.toString(sb);
		}
		sb.append("******************* TOTALE ***********************\n");
		for (String total : this.totals) {
			sb.append(String.format("%s\t", total));
		}
		sb.append("\n*************************************************\n");
	}

	public boolean isVisualizzaStorico() {
		return visualizzaStorico;
	}

	public Long getCategoriaSocialeId() {
		return categoriaSocialeId;
	}

	public void setCategoriaSocialeId(Long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public HashMap<Long, List<InterventoErogazAttrBean>> getMappaAttrsErog() {
		return mappaAttrsErog;
	}

	public void setMappaAttrsErog(HashMap<Long, List<InterventoErogazAttrBean>> mappaAttrsErog) {
		this.mappaAttrsErog = mappaAttrsErog;
	}
	
	//SISO - 883
	public List<InterventoErogazHistoryRowBean> getNewRows(){
			List<InterventoErogazHistoryRowBean> currRows=new ArrayList<InterventoErogazHistoryRowBean>();
			
			Iterator<InterventoErogazHistoryRowBean> it= this.getRows().iterator();
			while(it.hasNext()){
				InterventoErogazHistoryRowBean el=it.next();
				if(el.isNuovaErogazione()){
					currRows.add(el);
				}
			}
			return currRows;
			
		}
	
	public List<Long> getListaIdInterventiAttivi(){
		List<Long> lst = new ArrayList<Long>();
		for(InterventoErogazHistoryRowBean row : this.rows){
			if(row.getIntEseg()!=null && row.getIntEseg().getId()!=null)
				lst.add(row.getIntEseg().getId());
		}
		return lst;
	}
}

