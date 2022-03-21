package it.webred.ct.proc.ario.bean;

	public class Indirizzo<E,F>{
	    private E sed;
	    private F ind;

	    public Indirizzo(E sed, F ind){
	        this.ind = ind;
	        this.sed = sed;
	    }

	    public Indirizzo() {
			// TODO Auto-generated constructor stub
		}

		public F getInd(){return ind;}
	    public E getSed(){return sed;}

	    public String toString(){
	        return "("+ ind + "," + sed + ")";
	    }

		public void setInd(F ind) {
			this.ind = ind;
		}
	    
	}