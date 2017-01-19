package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.data.DataModelCostanti.TipoAttributo;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsCfgIntEsegAttUm;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

public class InterventoErogazHistoryRowBean implements Serializable {

	private static final long serialVersionUID = 1L;	
	private CsIInterventoEseg intEseg;
	private String descrizioneErogazione;
	private Date dataErogazione;
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

	protected Boolean canCalcTotale( CsIInterventoEsegValore val, CsCfgIntEseg csCfgintEseg ) {
		if(csCfgintEseg!=null){
			List<CsCfgIntEsegAttUm> lstAttrUm = csCfgintEseg.getCsCfgIntEsegAttUms();
			
			for(CsCfgIntEsegAttUm attUm : lstAttrUm )
				if( attUm.getId() == val.getCsAttributoUnitaMisura().getId() )
					return attUm.getCalcTotale();
		}
		return false;
	}
	public InterventoErogazHistoryRowBean(InterventoErogazHistoryHeaderBean header, CsIInterventoEseg intEseg, CsCfgIntEseg csCfgIntEseg ) {
		this.nuovaErogazione = false;
		this.intEseg = intEseg;
		List<InterventoErogazAttrBean> listAttributi = new LinkedList<InterventoErogazAttrBean>();

		List<CsIInterventoEsegValore> erogValori = intEseg.getCsIInterventoEsegValores();
		if (intEseg.getCsIInterventoEsegValores() != null && intEseg.getCsIInterventoEsegValores().size() > 0) {
			for (CsIInterventoEsegValore valore : erogValori)
			{
				Boolean canCalcTotale = canCalcTotale(valore, csCfgIntEseg);
				listAttributi.add(new InterventoErogazAttrBean(valore, canCalcTotale));
			}
		}

		setup(header, intEseg.getCsIInterventoEsegMast().getSettoreErogante(), intEseg.getStato(), listAttributi);
	}

	protected void setup(InterventoErogazHistoryHeaderBean header, CsOSettore sett, CsCfgIntEsegStato stato, List<InterventoErogazAttrBean> listAttr) {
		//this.nuovocsIInterventoEseg=nuovocsIInterventoEseg;
		//TODO frida aggiustare descrizione dettaglio e note dettaglio
		this.dataErogazione = intEseg.getDataEsecuzione();
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
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.DATE, dataErogazione));
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, stato != null ? stato.getNome() : "-"));
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, StringUtils.isNotEmpty(descrizioneErogazione) ? descrizioneErogazione : "-"));
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, settore != null ? settore.getNome() : altraOrgErogante));

		attrCells = new LinkedList<InterventoErogazAttrBean>();
		if (listAttr != null && listAttr.size() > 0) {
			for (InterventoErogazAttrBean attr : listAttr) {
				InterventoErogazAttrBean newAttr = new InterventoErogazAttrBean(attr);
				attrCells.add(newAttr);
			}
		}
		else {
			for (String attr : header.attributes)
				attrCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, "-"));
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



//	public CsOOrganizzazione getAltraOrganizzazione() {
//		return altraOrganizzazione;
//	}
//
//	public CsOOrganizzazione getDettOrganizzazione() {
//		return dettOrganizzazione;
//	}

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

}
