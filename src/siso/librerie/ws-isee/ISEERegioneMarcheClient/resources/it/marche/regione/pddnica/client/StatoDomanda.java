package it.marche.regione.pddnica.client;

public enum StatoDomanda {
	DA_EROGARE("Da Erogare"),
	EROGATA("Erogata");

	 private final String text;
    
	   StatoDomanda(final String text) {
	        this.text = text;
	    }

	    
	    @Override
	    public String toString() {
	        return text;
	    }
}
