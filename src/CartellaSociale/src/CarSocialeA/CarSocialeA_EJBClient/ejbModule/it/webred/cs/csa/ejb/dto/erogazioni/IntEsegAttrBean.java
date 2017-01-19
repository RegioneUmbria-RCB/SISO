package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegAttUm;
import it.webred.cs.data.model.CsTbUnitaMisura;

import java.util.LinkedList;
import java.util.List;

public class IntEsegAttrBean {

	protected Boolean calcTotale;
	protected Boolean abilitato;
	protected CsCfgAttr attributo;
	protected List<CsTbUnitaMisura> unitaMisuras = new LinkedList<CsTbUnitaMisura>();
	protected List<CsCfgAttrUnitaMisura> attrUnitaMisuras = new LinkedList<CsCfgAttrUnitaMisura>();
	protected List<CsCfgAttrOption> attrOptions = new LinkedList<CsCfgAttrOption>();
	
//	public IntEsegAttrBean(CsCfgAttr csCfgAttributo) {
//		calcTotale = false;
//		attributo = csCfgAttributo;
//		unitaMisuras = new LinkedList<CsTbUnitaMisura>();
//		attrUnitaMisuras=new LinkedList<CsCfgAttrUnitaMisura>();
//	}
//
	public IntEsegAttrBean(CsCfgIntEsegAttUm csCfgIntEsegAttUm ) {
		calcTotale = csCfgIntEsegAttUm.getCalcTotale();
		attributo = csCfgIntEsegAttUm.getCsCfgAttrUm().getCsCfgAttributo();
		
		if (csCfgIntEsegAttUm.getCsCfgAttrUm().getCsTbUnitaMisura() != null)
			unitaMisuras.add(csCfgIntEsegAttUm.getCsCfgAttrUm().getCsTbUnitaMisura());
		attrUnitaMisuras.add(csCfgIntEsegAttUm.getCsCfgAttrUm());
		abilitato = csCfgIntEsegAttUm.getAbilitato();
	}
	
	public IntEsegAttrBean(CsCfgAttrUnitaMisura csCfgAttrUnitaMisura, Boolean calcTotale ) {
		this.calcTotale = calcTotale;
		attributo = csCfgAttrUnitaMisura.getCsCfgAttributo();
		
		if (csCfgAttrUnitaMisura.getCsTbUnitaMisura() != null)
			unitaMisuras.add(csCfgAttrUnitaMisura.getCsTbUnitaMisura());
		attrUnitaMisuras.add(csCfgAttrUnitaMisura);
		
	}

	public CsCfgAttr getCsCfgAttr() {
		return attributo;
	}

	public List<CsCfgAttrUnitaMisura> getAttrUnitaMisuras() {
		return attrUnitaMisuras;
	}

	public List<CsTbUnitaMisura> getUnitaMisuras() {
		return unitaMisuras;
	}

	public Boolean getCalcTotale() {
		return calcTotale;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
}
