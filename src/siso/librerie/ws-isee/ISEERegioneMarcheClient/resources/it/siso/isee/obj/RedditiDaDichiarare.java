package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro FC4 – Modulo FC.1)
 * @author franc
 *
 */
public class RedditiDaDichiarare implements Serializable {

	//Valore dei redditi assoggettati ad imposta sostitutiva o a ritenuta a titolo d’imposta.
	private Integer redditoImpSost;
	//Valore dei redditi esenti da imposta.
	private Integer redditiEsentiImposta;
	//Valore dei proventi agrari da dichiarazione irap.
	private Integer redditiIRAP;
	//Valore del redditi fondiari.
	private Integer redditiFondiari;
	//Valore dei redditi da lavoro dipendente tassati esclusivamente all’estero.
	private Integer redditiTassatiEstero;
	//Valore dei redditilordi dichiarati ai fini fiscali dai residenti all’estero.
	private Integer redditAIRE;
	//Valore dei redditi fondiari di beni situati all’estero non locati soggetti alla disciplina 17 dell’IVIE.
	private Integer redditiFondiariEstero;
	//Valore di trattamenti Assistenziali, previdenziali e indennitari non soggetti ad IRPEF e non erogati dall’INPS.
	private Integer trattamentiAssistenziali;
	public Integer getRedditoImpSost() {
		return redditoImpSost;
	}
	public void setRedditoImpSost(Integer redditoImpSost) {
		this.redditoImpSost = redditoImpSost;
	}
	public Integer getRedditiEsentiImposta() {
		return redditiEsentiImposta;
	}
	public void setRedditiEsentiImposta(Integer redditiEsentiImposta) {
		this.redditiEsentiImposta = redditiEsentiImposta;
	}
	public Integer getRedditiIRAP() {
		return redditiIRAP;
	}
	public void setRedditiIRAP(Integer redditiIRAP) {
		this.redditiIRAP = redditiIRAP;
	}
	public Integer getRedditiFondiari() {
		return redditiFondiari;
	}
	public void setRedditiFondiari(Integer redditiFondiari) {
		this.redditiFondiari = redditiFondiari;
	}
	public Integer getRedditiTassatiEstero() {
		return redditiTassatiEstero;
	}
	public void setRedditiTassatiEstero(Integer redditiTassatiEstero) {
		this.redditiTassatiEstero = redditiTassatiEstero;
	}
	public Integer getRedditAIRE() {
		return redditAIRE;
	}
	public void setRedditAIRE(Integer redditAIRE) {
		this.redditAIRE = redditAIRE;
	}
	public Integer getRedditiFondiariEstero() {
		return redditiFondiariEstero;
	}
	public void setRedditiFondiariEstero(Integer redditiFondiariEstero) {
		this.redditiFondiariEstero = redditiFondiariEstero;
	}
	public Integer getTrattamentiAssistenziali() {
		return trattamentiAssistenziali;
	}
	public void setTrattamentiAssistenziali(Integer trattamentiAssistenziali) {
		this.trattamentiAssistenziali = trattamentiAssistenziali;
	}
	
	
	
}
