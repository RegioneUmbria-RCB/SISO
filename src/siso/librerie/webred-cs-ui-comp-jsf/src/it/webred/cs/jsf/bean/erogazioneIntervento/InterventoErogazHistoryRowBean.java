package it.webred.cs.jsf.bean.erogazioneIntervento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.DataModelCostanti.TipoAttributo;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class InterventoErogazHistoryRowBean implements Serializable {

	private static final long serialVersionUID = 1L;	
	private CsIInterventoEseg intEseg;
	private String descrizioneErogazione;
	private Date dataErogazione;
	private Date dataErogazioneA;
	private CsOSettore settore;
	private CsCfgIntEsegStato stato;
	private Boolean esportata = false;
	protected boolean nuovaErogazione = false;
	private List<InterventoErogazAttrBean> cells;
	private List<InterventoErogazAttrBean> fixedCells;
	private List<InterventoErogazAttrBean> attrCells;
	
	private List<SelectItem> listaSpese;
	public List<SelectItem> listaInformazioni; //SISO-958
	
	public InterventoErogazHistoryRowBean() {
	}
	
	//chiamato per nuove erogazioni
	public InterventoErogazHistoryRowBean(InterventoErogazHistoryHeaderBean header, CsOSettore settErog, 
			CsCfgIntEsegStato stato, CsIInterventoEseg nuovocsIInterventoEseg, List<InterventoErogazAttrBean> valori, String unitaMisura) {
		this.nuovaErogazione = true;
		this.intEseg=nuovocsIInterventoEseg;
		setup(header, settErog, stato, valori, unitaMisura);
	}
	
	public InterventoErogazHistoryRowBean(InterventoErogazHistoryHeaderBean header, CsIInterventoEseg intEseg, HashMap<Long, ErogStatoCfgDTO> mappa, String unitaMisura) {
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

		setup(header, intEseg.getCsIInterventoEsegMast().getSettoreErogante(), intEseg.getStato(), listAttributi, unitaMisura );
	}

	protected void setup(InterventoErogazHistoryHeaderBean header, CsOSettore sett, CsCfgIntEsegStato stato, List<InterventoErogazAttrBean> listAttr, String unitaMisura) {
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
		int index=0;
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, stato != null ? stato.getNome() : "-", header.getColumns().get(index++)));
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, getDataErogazioneValue(this.dataErogazione),  header.getColumns().get(index++)));
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, getDataErogazioneValue(this.dataErogazioneA), header.getColumns().get(index++)));
		//FINE SISO-556
		
		//INIZIO SISO-958 aggiunte le colonne fisse Spesa Totale e Valore Erogato
		fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, intEseg.getSpesa()!=null ? intEseg.getSpesa() + " €" : "-", header.getColumns().get(index++)));
			
		if( intEseg.getCsIValQuota() != null ) {
			if (intEseg.getCsIValQuota().getValQuota() != null){
		     //SISO-806
				if(unitaMisura.equalsIgnoreCase("ore/minuti")){
				
					int ore = intEseg.getCsIValQuota().getValQuota().intValue();
					
					BigDecimal minutiValQuota = intEseg.getCsIValQuota().getValQuota().remainder(BigDecimal.ONE);
					BigDecimal convMinutiValQuota = (minutiValQuota.multiply(new BigDecimal(60))).setScale(0, BigDecimal.ROUND_HALF_UP);
					int minuti = convMinutiValQuota.intValue();

					String strDate = StringUtils.leftPad(String.valueOf(ore), 2, "0")  + ":" + StringUtils.leftPad(String.valueOf(minuti),  2, "0");
					
					fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, strDate + " " + unitaMisura,  header.getColumns().get(index++)));
				}else {
			    // fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, intEseg.getCsIValQuota().getValQuota().toString() + " " + intEseg.getCsIValQuota().getCsIRigaQuota().getCsIQuota().getCsTbUnitaMisura().getValore(),  header.getColumns().get(index++)));
				fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, intEseg.getCsIValQuota().getValQuota().toString() + " " + unitaMisura,  header.getColumns().get(index++)));
				}
			}
			else
			{
				fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, " - ",  header.getColumns().get(index++)));
			}
		}
		else
		{
			fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, " - ",  header.getColumns().get(index++)));
		}		
		//FINE SISO-958
		
		//RIMOSSO A SEGUITO DELL'INTERVENTO SISO-775 (TASK SISO 823) 
		//fixedCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, settore != null ? settore.getNome() : altraOrgErogante, header.getColumns().get(1)));
		
//		InterventoErogazAttrBean fc = new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, StringUtils.isNotEmpty(descrizioneErogazione) ? descrizioneErogazione : "-", header.getColumns().get(index++));
//		fc.setImageURL("webredcs/img/note.png");
//		fixedCells.add(fc);
		
		attrCells = new LinkedList<InterventoErogazAttrBean>();		
	
		for(int j = fixedCells.size(); j<header.getColumns().size(); j++){
			String labelCol = header.getColumns().get(j);
			
				int i = 0;
					while (  i<listAttr.size()) {
						InterventoErogazAttrBean attr = listAttr.get(i);
					
//						attrCells.add(new InterventoErogazAttrBean(TipoAttributo.Enum.STRING, attr.getValue() , labelCol));
						attrCells.add(new InterventoErogazAttrBean(attr.geteValueType(), attr.getStringValue() , labelCol));
						i++;
						}
					
					}
					
		
		for (int k = 0; k < listAttr.size(); k++) {
			
			InterventoErogazAttrBean attr = listAttr.get(k);
		
			if (attr.getValue()!= null)
				attrCells.add(new InterventoErogazAttrBean(attr));
				
		}
		
		cells = new LinkedList<InterventoErogazAttrBean>();
		cells.addAll(fixedCells);
//		cells.addAll(attrCells);
		
//		listaSpese = new ArrayList<SelectItem>();
//		//Entità del servizio
//		/*Dò errore nei nuovi inserimenti
//		 * if(!intEseg.getCs getCsIInterventoEsegMast().getCsIQuota().getCsTbUnitaMisura().getValore().equals("€") && intEseg.getCsIValQuota() != null && intEseg.getCsIValQuota().getValQuota().intValue()>0)
//			listaSpese.add(new SelectItem(intEseg.getCsIInterventoEsegMast().getCsIQuota().getCsTbUnitaMisura().getValore(),intEseg.getCsIValQuota().getValQuota().toString()));*/
//		if(intEseg.getSpesa()!=null)
//			listaSpese.add(new SelectItem("Spesa €", intEseg.getSpesa().toString()));
//		if(intEseg.getPercGestitaEnte()!=null)
//			listaSpese.add(new SelectItem("% quota a carico dell'ente", intEseg.getPercGestitaEnte().toString()));
//		if(intEseg.getValoreGestitaEnte()!=null)
//			listaSpese.add(new SelectItem("Valore quota a carico dell'ente  € ", intEseg.getValoreGestitaEnte().toString()));
//		
		
		//SISO-958
		listaInformazioni = new ArrayList<SelectItem>();
		if(intEseg.getNote()!=null && !intEseg.getNote().isEmpty())
			listaInformazioni.add(new SelectItem("Note", intEseg.getNote()));
		if (intEseg.getCsIValQuota() != null && intEseg.getCsIInterventoEsegMast() != null && intEseg.getCsIInterventoEsegMast().getCsIQuota() != null ) {
			listaInformazioni.add(new SelectItem("Tariffa", intEseg.getCsIInterventoEsegMast().getCsIQuota().getTariffa()  + " €" ));
			//SISO-1602
			if(intEseg.getCsIValQuota().getTariffaCustom()!=null && !intEseg.getCsIValQuota().getTariffaCustom().equals(intEseg.getCsIInterventoEsegMast().getCsIQuota().getTariffa())){
				listaInformazioni.add(new SelectItem("Tariffa Applicata", intEseg.getCsIValQuota().getTariffaCustom()  + " €" ));
			}
		} else {
			if(intEseg.getCsIValQuota()!=null && intEseg.getCsIValQuota().getTariffaCustom()!=null) 
				listaInformazioni.add(new SelectItem("Tariffa Applicata", intEseg.getCsIValQuota().getTariffaCustom()  + " €" ));
		}
			
		
		if(intEseg.getPercGestitaEnte()!=null)
			listaInformazioni.add(new SelectItem("% quota a carico dell'ente", intEseg.getPercGestitaEnte().toString())); 
		if(intEseg.getValoreGestitaEnte()!=null)
			listaInformazioni.add(new SelectItem("Valore quota a carico dell'ente € ", intEseg.getValoreGestitaEnte().toString()));
		if(intEseg.getCompartUtenti()!=null)
			listaInformazioni.add(new SelectItem("Compartecipazione Utenti € ", intEseg.getCompartUtenti().toString()));
		if(intEseg.getCompartSsn()!=null)
			listaInformazioni.add(new SelectItem("Compartecipazione SSN € ", intEseg.getCompartSsn().toString()));
		if(intEseg.getCompartAltre()!=null)
			listaInformazioni.add(new SelectItem("Compartecipazione Altri € ", intEseg.getCompartAltre().toString()));
		if(intEseg.getNomeOperatoreErog()!=null && !intEseg.getNomeOperatoreErog().isEmpty())
			listaInformazioni.add(new SelectItem("Nome Altro Operatore ", intEseg.getNomeOperatoreErog().toString()));
		if(intEseg.getNoteAltreCompart()!=null && !intEseg.getNoteAltreCompart().isEmpty())
			listaInformazioni.add(new SelectItem("Note altro Compart", intEseg.getNoteAltreCompart().toString()));

		//Aggiungo gli attributi non più mappati in colonna ma 
		
		boolean trovato = false;
		int i = 0;
			while (!trovato &&  i<listAttr.size()) {
				InterventoErogazAttrBean attr = listAttr.get(i);
					
				listaInformazioni.add(new SelectItem(attr.getLabel(), attr.getStringValue().toString()));
				
					i++;
			}
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

	//INIZIO SISO-556
	private String getDataErogazioneValue(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataErogazioneValue=null;
		if (data!=null) 
			dataErogazioneValue = sdf.format(data);
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

	public String getUtenteModifica(){
		return CsUiCompBaseBean.getCognomeNomeUtente(this.intEseg.getUserIns());
	}

	public void setDataErogazione(Date dataErogazione) {
		this.dataErogazione = dataErogazione;
	}

	public void setDataErogazioneA(Date dataErogazioneA) {
		this.dataErogazioneA = dataErogazioneA;
	}

	public void setStato(CsCfgIntEsegStato stato) {
		this.stato = stato;
	}

	public List<SelectItem> getListaInformazioni() {
		return listaInformazioni;
	}

	public void setListaInformazioni(List<SelectItem> listaInformazioni) {
		this.listaInformazioni = listaInformazioni;
	}

	public Boolean getEsportata() {
		return esportata;
	}

	public void setEsportata(Boolean esportata) {
		this.esportata = esportata;
	}
	
	public boolean isEsportata() {
		return this.esportata!=null && this.esportata.booleanValue();
	}
}
