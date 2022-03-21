package it.webred.rulengine.brick.dia.bean.tributidocfa;

import java.util.HashMap;

public class DocfaDatiCensuariBean {
	
	private String dataDocfa;
	private String idUiu;
	private String categoriaDocfa;
	private String supDocfaCens;
	private String classeDocfa;
	private String renditaDocfa;
	private String consistenzaDocfa;
	private String prefissoViaDocfa;
	private String viaDocfa;
	private String zona;
	private String[] civiciDocfa;
	
	public String getViaDocfa() {
		return viaDocfa;
	}

	public void setViaDocfa(String viaDocfa) {
		this.viaDocfa = viaDocfa;
	}

	public DocfaDatiCensuariBean(){
		
	}
	
	public String getIdUiu() {
		return idUiu;
	}
	public void setIdUiu(String idUiu) {
		this.idUiu = idUiu;
	}
	public String getCategoriaDocfa() {
		return categoriaDocfa;
	}
	public void setCategoriaDocfa(String categoriaDocfa) {
		this.categoriaDocfa = categoriaDocfa;
	}
	public String getSupDocfaCens() {
		return supDocfaCens;
	}
	public void setSupDocfaCens(String supDocfaCens) {
		this.supDocfaCens = supDocfaCens;
	}
	public String getConsistenzaDocfa() {
		return consistenzaDocfa;
	}
	public void setConsistenzaDocfa(String consistenzaDocfa) {
		this.consistenzaDocfa = consistenzaDocfa;
	}
	public String getPrefissoViaDocfa() {
		return prefissoViaDocfa;
	}
	public void setPrefissoViaDocfa(String prefissoViaDocfa) {
		this.prefissoViaDocfa = prefissoViaDocfa;
	}
	
	public void setDataDocfa(String dataDocfa) {
		this.dataDocfa = dataDocfa;
	}
	public String getDataDocfa() {
		return dataDocfa;
	}

	
	public void setCiviciDocfa(String[] civiciDocfa) {
		this.civiciDocfa = civiciDocfa;
	}

	public String[] getCiviciDocfa() {
		return civiciDocfa;
	}

	public void setClasseDocfa(String classeDocfa) {
		this.classeDocfa = classeDocfa;
	}

	public String getClasseDocfa() {
		return classeDocfa;
	}

	public void setRenditaDocfa(String renditaDocfa) {
		this.renditaDocfa = renditaDocfa;
	}

	public String getRenditaDocfa() {
		return renditaDocfa;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getZona() {
		return zona;
	}

}
