package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.DataModelCostanti.TipoAttributo;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

public class InterventoErogazHistoryRowBean implements Serializable {

	private static final long serialVersionUID = 1L;	
	private CsIInterventoEseg intEseg;
	private String descrizioneErogazione;
	private Date dataErogazione;
	private Date dataErogazioneA;
	private CsOSettore settore;
	private CsCfgIntEsegStato stato;
	protected boolean nuovaErogazione = false;
	private List<InterventoErogazAttrBean> cells;
	private List<InterventoErogazAttrBean> fixedCells;
	private List<InterventoErogazAttrBean> attrCells;
	
	private List<SelectItem> listaSpese;
	
	public InterventoErogazHistoryRowBean() {
	}
	
	//chiamato per nuove erogazioni
	public InterventoErogazHistoryRowBean(InterventoErogazHistoryHeaderBean header, CsOSettore settErog, 
			CsCfgIntEsegStato stato, CsIInterventoEseg nuovocsIInterventoEseg, List<InterventoErogazAttrBean> valori) {
		this.nuovaErogazione = true;
		this.intEseg=nuovocsIInterventoEseg;
		setup(header, settErog, stato, valori);
	}
	
	public InterventoErogazHistoryRowBean(InterventoErogazHistoryHeaderBean header, CsIInterventoEseg intEseg, HashMap<Long, ErogStatoCfgDTO> mappa ) {
		this.nuovaErogazione = false;
		this.intEseg = intEseg;
		List<InterventoErogazAttrBean> listAttributi = new LinkedList<InterventoErogazAttrBean>();

		List<CsIInterventoEsegValore> erogValori = intEseg.getCsIInterventoEsegValores();
		if (intEseg.getCsIInterventoEsegValores() != null && intEseg.getCsIInterventoEsegValores().size() > 0) {
			for (CsIInterventoEsegValore valore : erogValori)
			{
				Boolean canCalcTotale = canCalcTotale(valore, mappa);
				listAttributi.add(new InterventoErogazAttrBean(valore, canCalcTotale));
			}
		}

		setup(header, intEseg.getCsIInterventoEsegMast().getSettoreErogante(), intEseg.getStato(), listAttributi);
	}

	protected void setup(InterventoErogazHistoryHeaderBean header, CsOSettore sett, CsCfgIntEsegStato stato, List<InterventoErogazAttrBean> listAttr) {
		//this.nuovocsIInterventoEseg=nuovocsIInterventoEseg;
		this.dataErogazione = intEseg.getDataEsecuzione();
		this.dataErogazioneA = intEseg.getDataEsecuzioneA();
		this.settore = sett;	
		
		String altraOrgErogante="";
		if(intEseg.getEnteOperatoreErogante()!=null){
			if(!intEseg.getEnteOperatoreErogante().equals(""))
				altraOrgErogante=intEseg.getEnteOperatoreErogante();
			else
				altraOrgErogante="-";
		}else
			altraOrgErogante="-";
		
		this.stato = stato;
		this.setDescrizioneErogazione(intEseg.getNote());

		fixedCells = new LinkedList<InterventoErogazAttrBean>();

		//INIZIO SISO-556  
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, getDataErogazioneValue(), header.getColumns().get(0)));
		//FINE SISO-556
		
		//RIMOSSO A SEGUITO DELL'INTERVENTO SISO-775 (TASK SISO 823) 
		//fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, settore != null ? settore.getNome() : altraOrgErogante, header.getColumns().get(1)));
		
		InterventoErogazAttrBean fc = new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, StringUtils.isNotEmpty(descrizioneErogazione) ? descrizioneErogazione : "-", header.getColumns().get(1));
		fc.setImageURL("webredcs/img/note.png");
		fixedCells.add(fc);
		
		
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, stato != null ? stato.getNome() : "-", header.getColumns().get(2)));
		
		attrCells = new LinkedList<InterventoErogazAttrBean>();
		
		for(int j = fixedCells.size(); j<header.getColumns().size(); j++){
			String labelCol = header.getColumns().get(j);
			//List<Long> stati = header.getAttributes().get(labelCol);
			//if(stati.contains(stato.getId()) && listAttr != null && listAttr.size() > 0){ //Cerco il parametro da aggiungere che si chiami in questo modo
				boolean trovato = false;
				int i = 0;
					while (!trovato &&  i<listAttr.size()) {
						InterventoErogazAttrBean attr = listAttr.get(i);
						if(attr.getLabel().equalsIgnoreCase(labelCol)){
							trovato = true;
							attrCells.add( new InterventoErogazAttrBean(attr));
						}
						i++;
					}
					
					if(!trovato) 
						attrCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, "-", labelCol));
					
		//	}else
		//		attrCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, "-"));
		}
		
		cells = new LinkedList<InterventoErogazAttrBean>();
		cells.addAll(fixedCells);
		cells.addAll(attrCells);
		
		listaSpese = new ArrayList<SelectItem>();
		if(intEseg.getSpesa()!=null)
			listaSpese.add(new SelectItem("Spesa €", intEseg.getSpesa().toString()));
		if(intEseg.getPercGestitaEnte()!=null)
			listaSpese.add(new SelectItem("% Spesa Gestita Direttamente", intEseg.getPercGestitaEnte().toString()));
		if(intEseg.getValoreGestitaEnte()!=null)
			listaSpese.add(new SelectItem("Valore Spesa Gestita Direttamente € ", intEseg.getValoreGestitaEnte().toString()));
	}
	
	protected Boolean canCalcTotale( CsIInterventoEsegValore val, HashMap<Long, ErogStatoCfgDTO> mappa ) {
		/*Ricerco CsCfgIntEsegAttUm (in cui è specificato se il valore è sommabile) 
		  dal valore ATTR_UM_ID di CS_I_INTERVENTO_ESEG_VALORE */
		
		CsCfgIntEsegStato statoCorrente = val.getCsIInterventoEseg().getStato();
		CsCfgAttrUnitaMisura aumCorrente =  val.getCsAttributoUnitaMisura();
		//CsCfgIntEsegStatoInt cfgStatoInt = findCsCfgIntEsegStato(csCfgintEseg, statoCorrente.getId());
		
		List<IntEsegAttrBean> lst = mappa.get(statoCorrente.getId()).getListaAttributi();
		Boolean canCalc = null;
		int i = 0;
		while( canCalc==null && i<lst.size()){
			IntEsegAttrBean uu = lst.get(i);
			canCalc = uu.getCalcTotale_ifMatchingAttUM(aumCorrente.getId());
			i++;
		}
	
		if(canCalc==null)
			CsUiCompBaseBean.logger.warn("Nessuna configurazione CsCfgIntEsegAttUm trovata per "
				                   + "CsCfgIntEsegStato["+statoCorrente.getId()+"-"+statoCorrente.getNome()+"] "
				                   + "CsCfgIntEsegAttUm["+aumCorrente.getId()+"]");	
		
		return canCalc!= null ? canCalc : false;
			
	}
	
	/*private CsCfgIntEsegStatoInt findCsCfgIntEsegStato(CsCfgIntEseg csCfgintEseg, Long statoCorrente){
		if(csCfgintEseg!=null){
			List<CsCfgIntEsegStatoInt> statoInt = csCfgintEseg.getCsCfgIntEsegStatoInt();
			//recupero la configurazione associata allo stato corrente
			for(CsCfgIntEsegStatoInt sint : statoInt){
				if(sint.getCsCfgIntEsegStato().getId()== statoCorrente)
					return sint;
			}
		}
			
		CsUiCompBaseBean.logger.warn("Nessuna configurazione CsCfgIntEsegStatoInt trovata per ID_STATO="+statoCorrente);	
		return null;	
	}*/

	//INIZIO SISO-556
	private String getDataErogazioneValue() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataErogazioneValue;
		if (dataErogazioneA==null) {
			dataErogazioneValue = sdf.format(dataErogazione);
		} else { 
			dataErogazioneValue = sdf.format(dataErogazione) + "-" + sdf.format(dataErogazioneA);
		} 
		return dataErogazioneValue;
	}
	//FINE SISO-556

	public List<InterventoErogazAttrBean> getAttrCells() {
		return attrCells;
	}

	public List<InterventoErogazAttrBean> getFixedCells() {
		return fixedCells;
	}

	public List<InterventoErogazAttrBean> getCells() {
		return cells;
	}

	public CsIInterventoEseg getIntEseg() {
		return intEseg;
	}	
	

	public void toString(StringBuilder sb) {
		for (InterventoErogazAttrBean cell : cells)
			cell.toString(sb);
		sb.append("\n");
	}

	public CsCfgIntEsegStato getStato() {
		return stato;
	}

	public CsOSettore getSettore() {
		return settore;
	}

	public void setSettore(CsOSettore settore) {
		this.settore = settore;
	}



	public static class RowComparator implements Comparator<InterventoErogazHistoryRowBean> {
		@Override
		public int compare(InterventoErogazHistoryRowBean o1, InterventoErogazHistoryRowBean o2) {
			Date dtIni1 = o1.dataErogazione;
			Date dtIni2 = o2.dataErogazione;
			if (dtIni1.before(dtIni2))
				return 1;
			if (dtIni1.after(dtIni2))
				return -1;
			return 0;
		}
	}

	public Date getDataErogazione() {
		return dataErogazione;
	}

	public boolean isNuovaErogazione() {
		return nuovaErogazione;
	}

	public String getDescrizioneErogazione() {
		return descrizioneErogazione;
	}

	public void setDescrizioneErogazione(String descrizioneErogazione) {
		this.descrizioneErogazione = descrizioneErogazione;
	}

	public List<SelectItem> getListaSpese() {
		return listaSpese;
	}

	public void setListaSpese(List<SelectItem> listaSpese) {
		this.listaSpese = listaSpese;
	}

	public Date getDataErogazioneA() {
		return dataErogazioneA;
	}

}
