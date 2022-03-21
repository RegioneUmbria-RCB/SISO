package it.siso.isee.obj;

import java.io.Serializable;

public class VariazioneBase  implements Serializable {

 
	    
	 
	    protected String tipovariazione;
	    protected String dataVariazione;
	    
	    
	    /**
	     *    
	     * In Caso di dichiarazione  vale A,B,C
	     * In caso di attestazione vale : 
	     *  "Contestazione" 
	     *  "Ricalcolo"  
	     *  "Integrazione" 
	     * @return
	     */
		public String getTipovariazione() {
			return tipovariazione;
		}
		public void setTipovariazione(String tipovariazione) {
			this.tipovariazione = tipovariazione;
		}
		public String getDataVariazione() {
			return dataVariazione;
		}
		public void setDataVariazione(String dataVariazione) {
			this.dataVariazione = dataVariazione;
		}
		 

		
}
