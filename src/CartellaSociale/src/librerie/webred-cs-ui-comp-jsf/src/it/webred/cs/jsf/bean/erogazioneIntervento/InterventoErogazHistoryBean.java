package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InterventoErogazHistoryBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");

	private List<InterventoErogazAttrBean> attrsErog;
	private InterventoErogazHistoryHeaderBean header;
	private List<InterventoErogazHistoryRowBean> rows;
	private List<InterventoErogazHistoryRowBean> newErogaz;
	private List<InterventoErogazHistoryRowBean> deleteErogaz;
	private List<String> totals = new LinkedList<String>();

	//private Integer totalsValuesSize = 0;
	private boolean visualizzaStorico = false;
	private CsIIntervento csIIntervento;
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
		newErogaz = newErogaz==null ?  new LinkedList<InterventoErogazHistoryRowBean>() : newErogaz;
		deleteErogaz = deleteErogaz==null ?  new LinkedList<InterventoErogazHistoryRowBean>() : deleteErogaz;
		rows = rows==null ?  new LinkedList<InterventoErogazHistoryRowBean>() : rows;
		attrsErog = attrsErog==null ?  new LinkedList<InterventoErogazAttrBean>() : attrsErog;
	
	}

	//frida
	public List<CsIInterventoEseg> initialize(List<InterventoErogazAttrBean> attrsErog, CsCTipoIntervento tipoIntervento, Long categoriaSocialeId) {
		this.csIIntervento = null;
		this.csCTipoIntervento = tipoIntervento;
		this.categoriaSocialeId = categoriaSocialeId;
		setup(attrsErog);
		return listaInterEseg;
	}
	
	//frida
	public List<CsIInterventoEseg> initialize(List<InterventoErogazAttrBean> attrsErog, CsCTipoIntervento tipoIntervento, CsCTipoInterventoCustom tipoInterventoCustom, Long categoriaSocialeId) {
		this.csIIntervento = null;
		this.csCTipoIntervento = tipoIntervento;
		this.categoriaSocialeId = categoriaSocialeId;
		this.csCTipoInterventoCustom=tipoInterventoCustom;
		setup(attrsErog);
		return listaInterEseg;
	}
	
	public List<CsIInterventoEseg> initialize(List<InterventoErogazAttrBean> attrsErog, CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
		this.csCTipoIntervento = csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento();
		this.categoriaSocialeId = csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCCategoriaSociale().getId();
		setup(attrsErog);
		return listaInterEseg;
	}
	
	public List<CsIInterventoEseg> initialize(List<InterventoErogazAttrBean> attrsErog, CsIInterventoEsegMast master) {
		this.idErogazioneMaster = master.getId();
		this.csCTipoIntervento = master.getCsCTipoIntervento();  
		this.categoriaSocialeId = master.getCategoriaSocialeId(); 
		setup(attrsErog);
		return listaInterEseg;
	}
//	public void initialize(List<InterventoErogazAttrBean> attrsErog, CsCTipoIntervento tipoIntervento) {
//		this.csIIntervento = null;
//		this.csCTipoIntervento = tipoIntervento;
//		setup(attrsErog);
//	}
//	
//	//frida
//	public void initialize(List<InterventoErogazAttrBean> attrsErog, CsCTipoIntervento tipoIntervento, CsCTipoInterventoCustom tipoInterventoCustom) {
//		this.csIIntervento = null;
//		this.csCTipoIntervento = tipoIntervento;
//		this.csCTipoInterventoCustom=tipoInterventoCustom;
//		setup(attrsErog);
//	}
//	public void initialize(List<InterventoErogazAttrBean> attrsErog, CsIIntervento csIIntervento) {
//		this.csIIntervento = csIIntervento;
//		this.csCTipoIntervento = csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento();
//		setup(attrsErog);
//	}

	private void setup(List<InterventoErogazAttrBean> attrsErog) {
		this.newErogaz = new LinkedList<InterventoErogazHistoryRowBean>();
		this.deleteErogaz = new LinkedList<InterventoErogazHistoryRowBean>();
		this.attrsErog = attrsErog;
		buildHistoryHeader();
		builHistoryRows();
		
		//finally force change UnitaMisuras to avoid different values with different measure unit
		calcTotals();
		forceSingolaUnitaMisura(attrsErog);
	}

	private void buildHistoryHeader() {
		this.header = new InterventoErogazHistoryHeaderBean();
		this.header.initialize(attrsErog);
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

	protected void calcTotals() {

		ErogazioneInterventoUtils.SumDTO[] dtos = new ErogazioneInterventoUtils.SumDTO[header.getColumns().size()];
		for( int i = 0; i < dtos.length; i++ ) dtos[i] = new ErogazioneInterventoUtils.SumDTO();
		for (InterventoErogazHistoryRowBean row : rows) {
			for (int i = 0; i < row.getCells().size(); i++) {
				IntEsegAttrBean attr = row.getCells().get(i).getAttr();
				if( attr != null && attr.getCalcTotale() )
					dtos[i] = ErogazioneInterventoUtils.sum( row.getCells().get(i), dtos[i] );
			}
		}
		
		this.totals = new LinkedList<String>();
		for( int i = 0; i < dtos.length; i++ )
			this.totals.add( dtos[i].getTotaleUnitaMisura() );
	}

	private void builHistoryRows() {
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);

		this.rows = new LinkedList<InterventoErogazHistoryRowBean>();
		List<CsIInterventoEseg> csIInterventoEsegLst = new ArrayList<CsIInterventoEseg>();
		if (this.csIIntervento != null) {		
			bDto.setObj(this.csIIntervento.getId());
			listaInterEseg = this.interventoService.getInterventoEsegByIntervento(bDto);
		}else if(this.idErogazioneMaster!=null){
			bDto.setObj(this.idErogazioneMaster);
			listaInterEseg = this.interventoService.getInterventoEsegByMasterId(bDto);
		}else{return;}
		
		csIInterventoEsegLst.addAll(listaInterEseg);
		
		bDto.setObj(this.csCTipoIntervento.getId());
		CsCfgIntEseg csCfgIntEseg = interventoService.findConfigIntErogByTipoIntervento(bDto);
		
		this.rows = new LinkedList<InterventoErogazHistoryRowBean>();
		for (CsIInterventoEseg csIInterventoEseg : csIInterventoEsegLst) {

			InterventoErogazHistoryRowBean curHistoryRow = new InterventoErogazHistoryRowBean(header, csIInterventoEseg, csCfgIntEseg);

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

	public void addNew(CsOSettore sett, String descrizioneErogazione, CsCfgIntEsegStato stato, CsIInterventoEseg nuovoCsIntervento, List<InterventoErogazAttrBean> erogaziones) {
		if (canAdd(stato)) {
			//TODO
			InterventoErogazHistoryRowBean newRow = new InterventoErogazHistoryRowBean(header, sett, stato, nuovoCsIntervento, erogaziones);
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

	public List<InterventoErogazAttrBean> getAttrsErog() {
		return attrsErog;
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

}
