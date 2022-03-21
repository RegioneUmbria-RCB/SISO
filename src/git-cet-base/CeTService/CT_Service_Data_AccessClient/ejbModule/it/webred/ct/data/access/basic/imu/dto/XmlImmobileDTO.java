package it.webred.ct.data.access.basic.imu.dto;

import java.util.ArrayList;
import java.util.List;


public class XmlImmobileDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String newId;
	
	private String numFigli;
	private List<XmlFiglioDTO> lstFigli;
	
	private String codiceEnte;
	
	private String catImmBase;
	private String catEstesa;
	private String catDescrizione;
	
	private Double renditaImmobileRiv; //Sommatoria rendite rivalutate
	
	private ValImmobileDTO valAB;
	private ValImmobileDTO valC2;
	private ValImmobileDTO valC6;
	private ValImmobileDTO valC7;
	
	private Double aliquota;
	private String numContitolari;
	
	private Double dovutoStato;
	private Double dovutoComune;
	
	private Double dovutoAccStato;
	private Double dovutoAccComune;
	
	private Double detrazioneAcc;
	private Double detrazioneAccComune;
	private Double detrazioneAccStato;
	
	private Double detrTerrAgr;
	private Double dovutoTerrAgr;
	
	private Double detrTerrAgrComune;
	private Double detrTerrAgrStato;
	
	private String moltiplicatore;
	
	private Double tasso;
	private String immTotali;
	private String immAssegnati;
	
	private boolean terremotato;
	private boolean variazione;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNewId() {
		return newId;
	}
	public void setNewId(String newId) {
		this.newId = newId;
	}
	public String getNumFigli() {
		return numFigli;
	}
	public void setNumFigli(String numFigli) {
		this.numFigli = numFigli;
	}
	public List<XmlFiglioDTO> getLstFigli() {
		return lstFigli;
	}
	public void setLstFigli(List<XmlFiglioDTO> lstFigli) {
		this.lstFigli = lstFigli;
	}
	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public String getCatImmBase() {
		return catImmBase;
	}
	public void setCatImmBase(String catImmBase) {
		this.catImmBase = catImmBase;
	}
	public String getCatEstesa() {
		return catEstesa;
	}
	public void setCatEstesa(String catEstesa) {
		this.catEstesa = catEstesa;
	}
	public String getCatDescrizione() {
		return catDescrizione;
	}
	public void setCatDescrizione(String catDescrizione) {
		this.catDescrizione = catDescrizione;
	}
	
	
	public Double getRenditaImmobileRiv() {
		return renditaImmobileRiv;
	}
	public void setRenditaImmobileRiv(Double renditaImmobileRiv) {
		this.renditaImmobileRiv = renditaImmobileRiv;
	}
	public ValImmobileDTO getValAB() {
		return valAB;
	}
	public void setValAB(ValImmobileDTO valAB) {
		this.valAB = valAB;
	}
	public ValImmobileDTO getValC2() {
		return valC2;
	}
	public void setValC2(ValImmobileDTO valC2) {
		this.valC2 = valC2;
	}
	public ValImmobileDTO getValC6() {
		return valC6;
	}
	public void setValC6(ValImmobileDTO valC6) {
		this.valC6 = valC6;
	}
	public ValImmobileDTO getValC7() {
		return valC7;
	}
	public void setValC7(ValImmobileDTO valC7) {
		this.valC7 = valC7;
	}
	public Double getAliquota() {
		return aliquota;
	}
	public void setAliquota(Double aliquota) {
		this.aliquota = aliquota;
	}
	public String getNumContitolari() {
		return numContitolari;
	}
	public void setNumContitolari(String numContitolari) {
		this.numContitolari = numContitolari;
	}
	public Double getDetrTerrAgr() {
		return detrTerrAgr;
	}
	public void setDetrTerrAgr(Double detrTerrAgr) {
		this.detrTerrAgr = detrTerrAgr;
	}
	public Double getDovutoTerrAgr() {
		return dovutoTerrAgr;
	}
	public void setDovutoTerrAgr(Double dovutoTerrAgr) {
		this.dovutoTerrAgr = dovutoTerrAgr;
	}
	public Double getDovutoStato() {
		return dovutoStato;
	}
	public void setDovutoStato(Double dovutoStato) {
		this.dovutoStato = dovutoStato;
	}
	public Double getDovutoComune() {
		return dovutoComune;
	}
	public void setDovutoComune(Double dovutoComune) {
		this.dovutoComune = dovutoComune;
	}
	public Double getDovutoAccStato() {
		return dovutoAccStato;
	}
	public void setDovutoAccStato(Double dovutoAccStato) {
		this.dovutoAccStato = dovutoAccStato;
	}
	public Double getDovutoAccComune() {
		return dovutoAccComune;
	}
	public void setDovutoAccComune(Double dovutoAccComune) {
		this.dovutoAccComune = dovutoAccComune;
	}
	public Double getDetrazioneAcc() {
		return detrazioneAcc;
	}
	public void setDetrazioneAcc(Double detrazioneAcc) {
		this.detrazioneAcc = detrazioneAcc;
	}
	public Double getDetrazioneAccComune() {
		return detrazioneAccComune;
	}
	public void setDetrazioneAccComune(Double detrazioneAccComune) {
		this.detrazioneAccComune = detrazioneAccComune;
	}
	public Double getDetrazioneAccStato() {
		return detrazioneAccStato;
	}
	public void setDetrazioneAccStato(Double detrazioneAccStato) {
		this.detrazioneAccStato = detrazioneAccStato;
	}
	public Double getDetrTerrAgrComune() {
		return detrTerrAgrComune;
	}
	public void setDetrTerrAgrComune(Double detrTerrAgrComune) {
		this.detrTerrAgrComune = detrTerrAgrComune;
	}
	public Double getDetrTerrAgrStato() {
		return detrTerrAgrStato;
	}
	public void setDetrTerrAgrStato(Double detrTerrAgrStato) {
		this.detrTerrAgrStato = detrTerrAgrStato;
	}
	public String getMoltiplicatore() {
		return moltiplicatore;
	}
	public void setMoltiplicatore(String moltiplicatore) {
		this.moltiplicatore = moltiplicatore;
	}
	public Double getTasso() {
		return tasso;
	}
	public void setTasso(Double tasso) {
		this.tasso = tasso;
	}
	public String getImmTotali() {
		return immTotali;
	}
	public void setImmTotali(String immTotali) {
		this.immTotali = immTotali;
	}
	public String getImmAssegnati() {
		return immAssegnati;
	}
	public void setImmAssegnati(String immAssegnati) {
		this.immAssegnati = immAssegnati;
	}
	public boolean isTerremotato() {
		return terremotato;
	}
	public void setTerremotato(boolean terremotato) {
		this.terremotato = terremotato;
	}
	public boolean isVariazione() {
		return variazione;
	}
	public void setVariazione(boolean variazione) {
		this.variazione = variazione;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ValImmobileDTO[] getListaValori(){
		ValImmobileDTO[] lst= new ValImmobileDTO[4];
		
		this.valAB.setDescCat("Ab.Principale");this.valAB.setCod("AB");
		lst[0]= this.valAB;
		this.valC2.setDescCat("Pertinenza C2");this.valC2.setCod("C2");
		lst[1]= this.valC2;
		this.valC6.setDescCat("Pertinenza C6");this.valC6.setCod("C6");
		lst[2]= this.valC6;
		this.valC7.setDescCat("Pertinenza C7");this.valC7.setCod("C7");
		lst[3]= this.valC7;
		
		return lst;
	}
	
	public boolean isVuotoTerrAgr(){
		
		return !((this.detrTerrAgr!=null && this.detrTerrAgr>0)||(this.dovutoTerrAgr!=null && this.dovutoTerrAgr>0)||
				(this.detrTerrAgrComune!=null && this.detrTerrAgrComune>0)||(this.detrTerrAgrStato!=null && this.detrTerrAgrStato>0));
		
	}
	
	
}
