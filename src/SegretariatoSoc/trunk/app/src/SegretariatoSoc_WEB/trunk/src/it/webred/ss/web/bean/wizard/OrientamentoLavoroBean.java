package it.webred.ss.web.bean.wizard;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

public class OrientamentoLavoroBean extends CsUiCompBaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// **FIELDS**//
	

	private List<SelectItem> motivoRicorso = new LinkedList<SelectItem>();
	private List<Integer> selMotivoRicorso =new LinkedList<Integer>();

	private String descrizioneServizio;

	private List<SelectItem> lstProfessioniIta=new LinkedList<SelectItem>();
	private BigDecimal selProfessioneIta;

	private List<SelectItem> lstProfessioniEstero=new LinkedList<SelectItem>();
	private BigDecimal selProfessioneItaEstero;

	private List<SelectItem> lstProfessioniAltri=new LinkedList<SelectItem>();
	private BigDecimal selProfessioneItaAltri;

	private List<SelectItem> lstProfessioniAttuale=new LinkedList<SelectItem>();
	private BigDecimal selProfessioneItaAttuale;

	private List<SelectItem> lstCondLavorativa=new LinkedList<SelectItem>();
	private BigDecimal selectedCondLavorativa;

	private List<SelectItem> lstYesNoRadio=new LinkedList<SelectItem>();

	private boolean bCentroImpego = false;

	private boolean bPatente = false;
	private List<SelectItem> lstLipoPatente=new LinkedList<SelectItem>();
	private Integer tipoPatente;
	private boolean automunito = false;
	private String mansioni;

	private List<SelectItem> lstDisponiblita=new LinkedList<SelectItem>();
	private List<Integer> selDisponibilita=new LinkedList<Integer>();

	private List<SelectItem> disponibilitaSpostamento=new LinkedList<SelectItem>();
	private List<Integer> selDispSpostamento=new LinkedList<Integer>();
	
	private boolean bNessuno = true;
	private boolean bEnableRicerca = false;
	private boolean bCpi;

	private boolean bAmiciConoscenti;
	private boolean bAmiciConStranieri = false;

	private boolean bIntermediariInformali;
	private boolean bInterStranieri = false;
	private boolean bIntermPrivatiAutorizzati;
	private boolean bAgenzieSpec;
	private boolean bAltro;

	private String altroSpec;

	public OrientamentoLavoroBean()
	{
		
	}

	// /*** GETTERS AND SETTERS*****///
	
	public List<SelectItem> getMotivoRicorso() {
		return motivoRicorso;
	}

	public void setMotivoRicorso(List<SelectItem> motivoRicorso) {
		this.motivoRicorso = motivoRicorso;
	}

	public List<Integer> getSelMotivoRicorso() {
		return selMotivoRicorso;
	}

	public void setSelMotivoRicorso(List<Integer> selMotivoRicorso) {
		this.selMotivoRicorso = selMotivoRicorso;
	}

	public String getDescrizioneServizio() {
		return descrizioneServizio;
	}

	public void setDescrizioneServizio(String descrizioneServizio) {
		this.descrizioneServizio = descrizioneServizio;
	}

	public List<SelectItem> getLstProfessioniIta() {
		return lstProfessioniIta;
	}

	public void setLstProfessioniIta(List<SelectItem> lstProfessioniIta) {
		this.lstProfessioniIta = lstProfessioniIta;
	}

	public BigDecimal getSelProfessioneIta() {
		return selProfessioneIta;
	}

	public void setSelProfessioneIta(BigDecimal selProfessioneIta) {
		this.selProfessioneIta = selProfessioneIta;
	}

	public List<SelectItem> getLstProfessioniEstero() {
		return lstProfessioniEstero;
	}

	public void setLstProfessioniEstero(List<SelectItem> lstProfessioniEstero) {
		this.lstProfessioniEstero = lstProfessioniEstero;
	}

	public BigDecimal getSelProfessioneItaEstero() {
		return selProfessioneItaEstero;
	}

	public void setSelProfessioneItaEstero(BigDecimal selProfessioneItaEstero) {
		this.selProfessioneItaEstero = selProfessioneItaEstero;
	}

	public List<SelectItem> getLstProfessioniAltri() {
		return lstProfessioniAltri;
	}

	public void setLstProfessioniAltri(List<SelectItem> lstProfessioniAltri) {
		this.lstProfessioniAltri = lstProfessioniAltri;
	}

	public BigDecimal getSelProfessioneItaAltri() {
		return selProfessioneItaAltri;
	}

	public void setSelProfessioneItaAltri(BigDecimal selProfessioneItaAltri) {
		this.selProfessioneItaAltri = selProfessioneItaAltri;
	}

	public List<SelectItem> getLstProfessioniAttuale() {
		return lstProfessioniAttuale;
	}

	public void setLstProfessioniAttuale(List<SelectItem> lstProfessioniAttuale) {
		this.lstProfessioniAttuale = lstProfessioniAttuale;
	}

	public BigDecimal getSelProfessioneItaAttuale() {
		return selProfessioneItaAttuale;
	}

	public void setSelProfessioneItaAttuale(BigDecimal selProfessioneItaAttuale) {
		this.selProfessioneItaAttuale = selProfessioneItaAttuale;
	}

	public List<SelectItem> getLstCondLavorativa() {
		return lstCondLavorativa;
	}

	public void setLstCondLavorativa(List<SelectItem> lstCondLavorativa) {
		this.lstCondLavorativa = lstCondLavorativa;
	}

	public BigDecimal getSelectedCondLavorativa() {
		return selectedCondLavorativa;
	}

	public void setSelectedCondLavorativa(BigDecimal selectedCondLavorativa) {
		this.selectedCondLavorativa = selectedCondLavorativa;
	}

	public List<SelectItem> getLstYesNoRadio() {
		return lstYesNoRadio;
	}

	public void setLstYesNoRadio(List<SelectItem> lstYesNoRadio) {
		this.lstYesNoRadio = lstYesNoRadio;
	}

	public boolean isbCentroImpego() {
		return bCentroImpego;
	}

	public void setbCentroImpego(boolean bCentroImpego) {
		this.bCentroImpego = bCentroImpego;
	}

	
	public boolean isbPatente() {
		return bPatente;
	}

	public void setbPatente(boolean bPatente) {
		this.bPatente = bPatente;
	}

	public List<SelectItem> getLstLipoPatente() {
		return lstLipoPatente;
	}

	public void setLstLipoPatente(List<SelectItem> lstLipoPatente) {
		this.lstLipoPatente = lstLipoPatente;
	}

	public Integer getTipoPatente() {
		return tipoPatente;
	}

	public void setTipoPatente(Integer tipoPatente) {
		this.tipoPatente = tipoPatente;
	}

	public boolean isAutomunito() {
		return automunito;
	}

	public void setAutomunito(boolean automunito) {
		this.automunito = automunito;
	}

	public String getMansioni() {
		return mansioni;
	}

	public void setMansioni(String mansioni) {
		this.mansioni = mansioni;
	}

	public List<SelectItem> getLstDisponiblita() {
		return lstDisponiblita;
	}

	public void setLstDisponiblita(List<SelectItem> lstDisponiblita) {
		this.lstDisponiblita = lstDisponiblita;
	}

	public List<Integer> getSelDisponibilita() {
		return selDisponibilita;
	}

	public void setSelDisponibilita(List<Integer> selDisponibilita) {
		this.selDisponibilita = selDisponibilita;
	}

	public List<SelectItem> getDisponibilitaSpostamento() {
		return disponibilitaSpostamento;
	}

	public void setDisponibilitaSpostamento(List<SelectItem> disponibilitaSpostamento) {
		this.disponibilitaSpostamento = disponibilitaSpostamento;
	}

	public List<Integer> getSelDispSpostamento() {
		return selDispSpostamento;
	}

	public void setSelDispSpostamento(List<Integer> selDispSpostamento) {
		this.selDispSpostamento = selDispSpostamento;
	}

	public boolean isbNessuno() {
		return bNessuno;
	}

	public void setbNessuno(boolean bNessuno) {
		this.bNessuno = bNessuno;
	}

	public boolean isbEnableRicerca() {
		return bEnableRicerca;
	}

	public void setbEnableRicerca(boolean bEnableRicerca) {
		this.bEnableRicerca = bEnableRicerca;
	}

	public boolean isbCpi() {
		return bCpi;
	}

	public void setbCpi(boolean bCpi) {
		this.bCpi = bCpi;
	}

	public boolean isbAmiciConoscenti() {
		return bAmiciConoscenti;
	}

	public void setbAmiciConoscenti(boolean bAmiciConoscenti) {
		this.bAmiciConoscenti = bAmiciConoscenti;
	}

	public boolean isbAmiciConStranieri() {
		return bAmiciConStranieri;
	}

	public void setbAmiciConStranieri(boolean bAmiciConStranieri) {
		this.bAmiciConStranieri = bAmiciConStranieri;
	}

	public boolean isbIntermediariInformali() {
		return bIntermediariInformali;
	}

	public void setbIntermediariInformali(boolean bIntermediariInformali) {
		this.bIntermediariInformali = bIntermediariInformali;
	}

	public boolean isbInterStranieri() {
		return bInterStranieri;
	}

	public void setbInterStranieri(boolean bInterStranieri) {
		this.bInterStranieri = bInterStranieri;
	}

	public boolean isbIntermPrivatiAutorizzati() {
		return bIntermPrivatiAutorizzati;
	}

	public void setbIntermPrivatiAutorizzati(boolean bIntermPrivatiAutorizzati) {
		this.bIntermPrivatiAutorizzati = bIntermPrivatiAutorizzati;
	}

	public boolean isbAgenzieSpec() {
		return bAgenzieSpec;
	}

	public void setbAgenzieSpec(boolean bAgenzieSpec) {
		this.bAgenzieSpec = bAgenzieSpec;
	}

	public boolean isbAltro() {
		return bAltro;
	}

	public void setbAltro(boolean bAltro) {
		this.bAltro = bAltro;
	}

	public String getAltroSpec() {
		return altroSpec;
	}

	public void setAltroSpec(String altroSpec) {
		this.altroSpec = altroSpec;
	}

	
	
}
