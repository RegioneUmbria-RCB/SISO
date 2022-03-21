package it.webred.ct.data.access.basic.anagrafe.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class DatiCivicoAnagrafeDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int countUnder18;
	private int count18_65;
	private int countOver65;
	private int countFamResidenti;
	private int countFamResidentiProprietarie;
	//private List<ComponenteFamigliaDTO> residenti;
	
	public DatiCivicoAnagrafeDTO(){
		countUnder18 = 0;
		count18_65 = 0;
		countOver65 = 0;
		countFamResidenti = 0;
		countFamResidentiProprietarie = 0;
	}
	
	public int getCountUnder18() {
		return countUnder18;
	}
	public void setCountUnder18(int countUnder18) {
		this.countUnder18 = countUnder18;
	}
	public int getCount18_65() {
		return count18_65;
	}
	public void setCount18_65(int count18_65) {
		this.count18_65 = count18_65;
	}
	public int getCountOver65() {
		return countOver65;
	}
	public void setCountOver65(int countOver65) {
		this.countOver65 = countOver65;
	}
	public int getCountFamResidenti() {
		return countFamResidenti;
	}
	public void setCountFamResidenti(int countFamResidenti) {
		this.countFamResidenti = countFamResidenti;
	}
	
	public void setCountFamResidentiProprietarie(
			int countFamResidentiProprietarie) {
		this.countFamResidentiProprietarie = countFamResidentiProprietarie;
	}

	public int getCountFamResidentiProprietarie() {
		return countFamResidentiProprietarie;
	}
	
}
