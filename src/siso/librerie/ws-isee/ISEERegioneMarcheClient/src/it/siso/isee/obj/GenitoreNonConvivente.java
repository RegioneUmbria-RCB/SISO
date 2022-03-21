package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * (Quadro D – Modulo MB.2)
 * @author Francesco Pellegrini
 *
 */
public class GenitoreNonConvivente extends AnagraficaBase implements Serializable {

	/***
	 * L’elemento Beneficiario può essere ripetuto tante volte quanti sono i beneficiari
	 *  nel caso di genitori non convivente (es. più minori di uno stesso genitore non convivente)
	 *  
	 *  L’elemento Beneficiario è ripetuto tante volte quanti sono i Beneficiari:
		Esempio: se i minori figli del genitori non conviventi sono tre vanno indicati tutti e tre (3 volte l’elemento Beneficiario)
		NB: qui si indicano i figli del genitori non conviventi beneficiari della prestazioni quindi facenti parti del nucleo indicato nel quadro A, 
		no eventuali figli avuti da matrimonio con altra persona
	 */
	private List<String> beneficiario;
	
	private String numeroProtocolloRiferimento;
	
	//Obbligatorio
	private boolean flagGenitoreEscluso;
	//obbligatorio
	private boolean flagConiugatoConPersonaDiversa;
	
	private boolean flagAlcunaCondizione;
	
	private DettaglioGenitoreNonConvivente dettaglioGenitoreNonConvivente;
	
	
	

	public DettaglioGenitoreNonConvivente getDettaglioGenitoreNonConvivente() {
		return dettaglioGenitoreNonConvivente;
	}

	public void setDettaglioGenitoreNonConvivente(DettaglioGenitoreNonConvivente dettaglioGenitoreNonConvivente) {
		this.dettaglioGenitoreNonConvivente = dettaglioGenitoreNonConvivente;
	}

	public List<String> getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(List<String> beneficiario) {
		this.beneficiario = beneficiario;
	}


	public void addBeneficiario( String  beneficiario) {
		if(this.beneficiario == null)
			this.beneficiario = new ArrayList<String>();
		this.beneficiario.add(beneficiario);
	}
	/***
	 * <!-- Gli elementi che seguono sono in choice poiché è possibile inserire il protocollo di una dichiarazione già esistente 
										se il genitore non convivente la possiede o inserire qui i dati del genitore non convivente -->
										<!-- Numero protocollo di riferimento -->
	 * @return
	 */
	public String getNumeroProtocolloRiferimento() {
		return numeroProtocolloRiferimento;
	}

	public void setNumeroProtocolloRiferimento(String numeroProtocolloRiferimento) {
		this.numeroProtocolloRiferimento = numeroProtocolloRiferimento;
	}

	public boolean isFlagGenitoreEscluso() {
		return flagGenitoreEscluso;
	}

	public void setFlagGenitoreEscluso(boolean flagGenitoreEscluso) {
		this.flagGenitoreEscluso = flagGenitoreEscluso;
	}

	public boolean isFlagConiugatoConPersonaDiversa() {
		return flagConiugatoConPersonaDiversa;
	}

	public void setFlagConiugatoConPersonaDiversa(boolean flagConiugatoConPersonaDiversa) {
		this.flagConiugatoConPersonaDiversa = flagConiugatoConPersonaDiversa;
	}

	public boolean isFlagAlcunaCondizione() {
		return flagAlcunaCondizione;
	}

	public void setFlagAlcunaCondizione(boolean flagAlcunaCondizione) {
		this.flagAlcunaCondizione = flagAlcunaCondizione;
	}
	
	
	
}
