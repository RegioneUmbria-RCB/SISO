package it.webred.ss.web.bean.wizard;

import java.util.HashMap;
import java.util.List;

import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsSchedaMotivazione;

public class Motivazione {
	
	private Long id;                    //Id Scheda Motivazione
	
	private MotivoClasse classe0;
	private MotivoClasse classe1;
	private MotivoClasse classe2;
	private MotivoClasse classe3;
	private MotivoClasse classe4;
	private MotivoClasse classe5;
	
	private String altro;
	
	public Motivazione(){
		classe0 = new MotivoClasse(0);
	    classe1 = new MotivoClasse(1);
	    classe2 = new MotivoClasse(2);
	    classe3 = new MotivoClasse(3);
	    classe4 = new MotivoClasse(4);
	    classe5 = new MotivoClasse(5);
	}
	
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = altro;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	public void fillModel(SsSchedaMotivazione model) {
		model.setId(id);
		model.setAltro(altro);
		
	}
	public void initFromModel(SsSchedaMotivazione motivazione, List<SsMotivazioniSchede> motivazioniSchede) {
		if(motivazione != null){
			id = motivazione.getId();
			altro = motivazione.getAltro();

		    this.initMotiviClasseFromModel(motivazioniSchede);
		}
		
	}
	
	public void initMotiviClasseFromModel(List<SsMotivazioniSchede> motivazioniSchede){
		classe0.initFromModel(motivazioniSchede);
		classe1.initFromModel(motivazioniSchede);
		classe2.initFromModel(motivazioniSchede);
		classe3.initFromModel(motivazioniSchede);
		classe4.initFromModel(motivazioniSchede);
		classe5.initFromModel(motivazioniSchede);
	}
	
	public MotivoClasse getClasse0() {
		return classe0;
	}
	public void setClasse0(MotivoClasse classe0) {
		this.classe0 = classe0;
	}
	public MotivoClasse getClasse1() {
		return classe1;
	}
	public void setClasse1(MotivoClasse classe1) {
		this.classe1 = classe1;
	}
	public MotivoClasse getClasse2() {
		return classe2;
	}
	public void setClasse2(MotivoClasse classe2) {
		this.classe2 = classe2;
	}
	public MotivoClasse getClasse3() {
		return classe3;
	}
	public void setClasse3(MotivoClasse classe3) {
		this.classe3 = classe3;
	}
	public MotivoClasse getClasse4() {
		return classe4;
	}
	public void setClasse4(MotivoClasse classe4) {
		this.classe4 = classe4;
	}
	public MotivoClasse getClasse5() {
		return classe5;
	}
	public void setClasse5(MotivoClasse classe5) {
		this.classe5 = classe5;
	}
	
	public void refreshDescrizioni(HashMap<String,List<SsMotivazione>> mappaMotivazioni){
		classe1.refreshDescrizioni(mappaMotivazioni.get("1"));
		classe2.refreshDescrizioni(mappaMotivazioni.get("2"));
		classe3.refreshDescrizioni(mappaMotivazioni.get("3"));
		classe4.refreshDescrizioni(mappaMotivazioni.get("4"));
		classe5.refreshDescrizioni(mappaMotivazioni.get("5"));
	}
	
	public MotivoClasse getMotivoClasseI(int index){
		switch (index){ 
			case 0: return classe0; 
			case 1: return classe1; 
			case 2: return classe2; 
			case 3: return classe3; 
			case 4: return classe4; 
			case 5: return classe5;
			default: return null;
		}
	}
	

}
