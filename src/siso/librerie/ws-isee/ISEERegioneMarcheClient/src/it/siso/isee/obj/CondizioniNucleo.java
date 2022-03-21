package it.siso.isee.obj;

import java.io.Serializable;

public class CondizioniNucleo implements Serializable {
public boolean isFlagLavoroGenitori() {
		return flagLavoroGenitori;
	}
	public void setFlagLavoroGenitori(boolean flagLavoroGenitori) {
		this.flagLavoroGenitori = flagLavoroGenitori;
	}
	public boolean isFlagUnicoGenitore() {
		return flagUnicoGenitore;
	}
	public void setFlagUnicoGenitore(boolean flagUnicoGenitore) {
		this.flagUnicoGenitore = flagUnicoGenitore;
	}
	public Integer getNumeroFigli() {
		return numeroFigli;
	}
	public void setNumeroFigli(Integer numeroFigli) {
		this.numeroFigli = numeroFigli;
	}
	public Integer getNumeroFigliConviventi() {
		return numeroFigliConviventi;
	}
	public void setNumeroFigliConviventi(Integer numeroFigliConviventi) {
		this.numeroFigliConviventi = numeroFigliConviventi;
	}
/***
 * Flag che indica se nel nucleo familiare,
 *  in presenza di figli minorenni, entrambi i genitori, 
 *  o l’unico genitore presente, 
 *  hanno svolto attività di lavoro o di impresa per almeno sei mesi nell’anno di riferimento dei redditi dichiarati.
 *  OBBLIGATORIO
 */
	private boolean flagLavoroGenitori;
/***
 * Flag che indica se il nucleo è composto esclusivamente da genitore solo con i suoi figli minorenni	
 */
	private boolean flagUnicoGenitore;
	
	//Numero dei figli maggiori di due
	private Integer numeroFigli;
	//Numero dei figli indicati nell’attributo precedente NumeroFigli che sono conviventi Tipo Dati: unsignedInt
	private Integer numeroFigliConviventi;
	
	
	
}
