package it.webred.cs.csa.ejb.dto.erogazioni.configurazione;

import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.data.model.CsCfgIntEsegStato;

import java.io.Serializable;
import java.util.List;

public class ErogStatoCfgDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String nome;                  //Label dello Stato   
	private String tipo;                  //P (preliminare) o E (erogativo)
	private boolean erogazionePossibile;  //A questo stato possono seguire stati di tipo EROGAZIONE
	private boolean flagChiudi;           //Lo stato corrente è di CHIUSURA
	private List<IntEsegAttrBean> listaAttributi;
	
	public ErogStatoCfgDTO(){}
	
	public ErogStatoCfgDTO(CsCfgIntEsegStato s, List<IntEsegAttrBean> lstAttributi){
		id = s.getId();
		nome = s.getNome();
		tipo = s.getTipo();
		erogazionePossibile = s.getErogazionePossibile()!=null ? s.getErogazionePossibile() : false;
		flagChiudi = s.getFlagChiudi()!=null ? s.getFlagChiudi() : false;
		listaAttributi = lstAttributi;
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isErogazionePossibile() {
		return erogazionePossibile;
	}

	public boolean isFlagChiudi() {
		return flagChiudi;
	}

	public List<IntEsegAttrBean> getListaAttributi() {
		return listaAttributi;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setErogazionePossibile(boolean erogazionePossibile) {
		this.erogazionePossibile = erogazionePossibile;
	}

	public void setFlagChiudi(boolean flagChiudi) {
		this.flagChiudi = flagChiudi;
	}

	public void setListaAttributi(List<IntEsegAttrBean> listaAttributi) {
		this.listaAttributi = listaAttributi;
	}

}
