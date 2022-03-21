package it.webred.ct.data.access.basic.docfa.dto;

import it.webred.ct.data.model.docfa.DocfaDatiCensuari;
import it.webred.ct.data.model.docfa.DocfaInParteDueH;
import it.webred.ct.data.model.docfa.DocfaPlanimetrie;
import it.webred.ct.data.model.docfa.DocfaUiu;

import java.io.Serializable;
import java.util.List;

public class DettaglioDocfaImmobileDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private DocfaUiu docfaUiu;
	private DocfaDatiCensuari datiCensuari;
	private List<DocfaPlanimetrie> planimetrie;
	private DocfaInParteDueH parteDueH;
	public DocfaUiu getDocfaUiu() {
		return docfaUiu;
	}
	public void setDocfaUiu(DocfaUiu docfaUiu) {
		this.docfaUiu = docfaUiu;
	}
	public DocfaDatiCensuari getDatiCensuari() {
		return datiCensuari;
	}
	public void setDatiCensuari(DocfaDatiCensuari datiCensuari) {
		this.datiCensuari = datiCensuari;
	}
	
	public DocfaInParteDueH getParteDueH() {
		return parteDueH;
	}
	public void setParteDueH(DocfaInParteDueH parteDueH) {
		this.parteDueH = parteDueH;
	}
	public List<DocfaPlanimetrie> getPlanimetrie() {
		return planimetrie;
	}
	public void setPlanimetrie(List<DocfaPlanimetrie> planimetrie) {
		this.planimetrie = planimetrie;
	}
}
