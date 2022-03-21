package it.siso.isee.obj;

import java.io.Serializable;
import java.util.List;

/***
 * Elemento ModelloBase (Modello MB o Modello MBrid)
 * @author Francesco Pellegrini
 *
 *una dichiarazione può avere un solo modello Base di tipo ORD
 * (composto dal Nucleo Standard che è unico) e più modelli Base di tipo RID (composto da Nucleo Ristretto) .
 */
public class ModelloBase implements Serializable {

	/***
	 * Può essere di tipo "Standard" o "Ridotto" tale valore è indicato nell'attributo Tipo
	 */
	private String tipo;
	//	<!-- (Quadro A) del modello Base -->
	private List<ComponenteNucleo> nucleoFamiliare;
	
	private CondizioniNucleo condizioniNucleo;
	private Abitazione abitazione;
	private List<Universitario> universitari;
	private List<GenitoreNonConvivente> genitoriNonConviventi;
	private Residenziale residenziale;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public CondizioniNucleo getCondizioniNucleo() {
		return condizioniNucleo;
	}
	public void setCondizioniNucleo(CondizioniNucleo condizioniNucleo) {
		this.condizioniNucleo = condizioniNucleo;
	}
	public Abitazione getAbitazione() {
		return abitazione;
	}
	public void setAbitazione(Abitazione abitazione) {
		this.abitazione = abitazione;
	}
	public List<Universitario> getUniversitari() {
		return universitari;
	}
	public void setUniversitari(List<Universitario> universitari) {
		this.universitari = universitari;
	}
	public List<GenitoreNonConvivente> getGenitoriNonConviventi() {
		return genitoriNonConviventi;
	}
	public void setGenitoriNonConviventi(List<GenitoreNonConvivente> genitoriNonConviventi) {
		this.genitoriNonConviventi = genitoriNonConviventi;
	}
	public Residenziale getResidenziale() {
		return residenziale;
	}
	public void setResidenziale(Residenziale residenziale) {
		this.residenziale = residenziale;
	}
	
	
	
}
