package it.webred.cs.csa.web.manbean.fascicolo;

import it.webred.cs.data.model.CsDDiario;

public abstract class FascicoloCompSecondoLivello extends FascicoloCompBaseBean {

	protected Boolean toSaveSecondoLivello=false; //SISO-812
	
	//SISO-972
	protected Boolean abilitaControlloFSEPor = true;
	protected void valorizzaSecondoLivello(CsDDiario diario){
		diario.setVisSecondoLivello(this.toSaveSecondoLivello ? getCurrentOpSettore().getCsOSettore().getId() : null);
	}
	
	//SISO-812
	public void salvaSecondoLivello(){
		toSaveSecondoLivello=true;
		save();
	}
	
	public void salva() {
		toSaveSecondoLivello=false;
		save();
	}
	public void salvaFSE() {
		abilitaControlloFSEPor=false;
		save();
	}
	protected abstract void save();

}
