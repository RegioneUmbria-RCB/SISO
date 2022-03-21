package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.AttributoDTO;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegAttUm;
import it.webred.cs.data.model.CsTbUnitaMisura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IntEsegAttrBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected AttributoDTO attributo;
	protected boolean calcTotale;
	protected boolean abilitato;
	protected List<CsTbUnitaMisura> unitaMisuras = new LinkedList<CsTbUnitaMisura>();
	private List<Long> attrUnitaMisuraIDs = new ArrayList<Long>();
	
	public IntEsegAttrBean(){}
	
	public IntEsegAttrBean(CsCfgIntEsegAttUm csCfgIntEsegAttUm ) {
		CsCfgAttrUnitaMisura cfgAttrUm = csCfgIntEsegAttUm.getCsCfgAttrUm();
		calcTotale = csCfgIntEsegAttUm.getCalcTotale()!=null ? csCfgIntEsegAttUm.getCalcTotale() : false;
		attributo = new AttributoDTO(cfgAttrUm.getCsCfgAttributo());
		
		if (cfgAttrUm.getCsTbUnitaMisura() != null)
			unitaMisuras.add(cfgAttrUm.getCsTbUnitaMisura());
		//attrUnitaMisuras.add(cfgAttrUm);
		attrUnitaMisuraIDs.add(cfgAttrUm.getId());
		abilitato = csCfgIntEsegAttUm.getAbilitato()!=null ? csCfgIntEsegAttUm.getAbilitato() : false;
	}
	
	public IntEsegAttrBean(CsCfgAttrUnitaMisura cfgAttrUm, Boolean calcTotale ) {
		this.calcTotale = calcTotale;
		attributo = new AttributoDTO(cfgAttrUm.getCsCfgAttributo());
		
		if (cfgAttrUm.getCsTbUnitaMisura() != null)
			unitaMisuras.add(cfgAttrUm.getCsTbUnitaMisura());
		//attrUnitaMisuras.add(cfgAttrUm);
		attrUnitaMisuraIDs.add(cfgAttrUm.getId());
		
	}

	public AttributoDTO getAttributo() {
		return attributo;
	}

	public List<CsTbUnitaMisura> getUnitaMisuras() {
		return unitaMisuras;
	}

	public boolean getCalcTotale() {
		return calcTotale;
	}

	public boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	public void addUnitaMisura(CsCfgAttrUnitaMisura um){
		this.unitaMisuras.add(um.getCsTbUnitaMisura());
		//this.attrUnitaMisuras.add(um);
		attrUnitaMisuraIDs.add(um.getId());
	}
	
	public Boolean getCalcTotale_ifMatchingAttUM(Long currCsCfgAttrUnitaMisuraID) {
		if(currCsCfgAttrUnitaMisuraID!=null){	
			for(Long attumID : attrUnitaMisuraIDs){
					if( attumID.longValue() == currCsCfgAttrUnitaMisuraID.longValue())
						return calcTotale;
				}
		}
			return null;
	}

}
