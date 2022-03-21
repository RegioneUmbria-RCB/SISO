package it.webred.rulengine.brick.superc.importDati;

/**
 * Rappresenta l'item corrente nel metodo elabora della classe ImportDatiDb
 * 
 * pRATICAMENTE HA IL VALORE DELA TABELLA CH SI STA SCRIVENDO ATTRAVERSO IL METODO ELABORA E LA CHIAMATA A EXECUTE DELLA CLASSE FIGLIA
 * 
 * @author MarcoPort
 *
 */
public class CurrentItem {
	public String name;
	
	public CurrentItem (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
