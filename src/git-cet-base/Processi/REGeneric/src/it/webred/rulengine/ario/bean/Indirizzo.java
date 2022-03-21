package it.webred.rulengine.ario.bean;

/**
 * La classe è stata mantenuta nel progetto come utility 
 * 
 * @author webred
 *
 */
public class Indirizzo<E,F> {
	
	private E sed;
    private F ind;
    
	public Indirizzo() {
	}

	public Indirizzo(E sed, F ind) {
		super();
		this.sed = sed;
		this.ind = ind;
	}

	public String toString(){
        return "("+ ind + "," + sed + ")";
    }

	public E getSed() {
		return sed;
	}

	public void setSed(E sed) {
		this.sed = sed;
	}

	public F getInd() {
		return ind;
	}

	public void setInd(F ind) {
		this.ind = ind;
	}
	
	
}
