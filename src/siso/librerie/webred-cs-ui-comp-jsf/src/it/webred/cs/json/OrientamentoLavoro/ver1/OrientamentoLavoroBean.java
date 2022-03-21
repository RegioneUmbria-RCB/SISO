package it.webred.cs.json.OrientamentoLavoro.ver1;

import it.webred.cs.json.dto.JsonBaseBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrientamentoLavoroBean extends JsonBaseBean {

	// **FIELDS**//
	private static final String  JsnonName="OrientamentoLavoroBean";
	
    private List<String> selMotivoRicorso = new LinkedList<String>();

	private String descrizioneServizio;

	private BigDecimal selProfessioneIta;
	private BigDecimal selProfessioneItaEstero;
	private BigDecimal selProfessioneItaAltri;
	private BigDecimal selProfessioneItaAttuale;
	private BigDecimal selectedCondLavorativa;

	private boolean bCentroImpego = false;
    private boolean bRivoltoAqualcuno=false;
	private boolean bPatente = false;

	private String tipoPatente;
	private boolean automunito = false;
	private String mansioni;

	private List<String> selDisponibilita = new LinkedList<String>();

	private List<String> selDispSpostamento = new LinkedList<String>();

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

	
	public List<String> getSelMotivoRicorso() {
		return selMotivoRicorso;
	}

	public boolean isbRivoltoAqualcuno() {
		return bRivoltoAqualcuno;
	}

	public void setbRivoltoAqualcuno(boolean bRivoltoAqualcuno) {
		this.bRivoltoAqualcuno = bRivoltoAqualcuno;
	}

	public void setSelMotivoRicorso(List<String> selMotivoRicorso) {
		this.selMotivoRicorso = selMotivoRicorso;
	}

	public String getDescrizioneServizio() {
		return descrizioneServizio;
	}

	public void setDescrizioneServizio(String descrizioneServizio) {
		this.descrizioneServizio = descrizioneServizio;
	}

	public BigDecimal getSelProfessioneIta() {
		return selProfessioneIta;
	}

	public void setSelProfessioneIta(BigDecimal selProfessioneIta) {
		this.selProfessioneIta = selProfessioneIta;
	}

	public BigDecimal getSelProfessioneItaEstero() {
		return selProfessioneItaEstero;
	}

	public void setSelProfessioneItaEstero(BigDecimal selProfessioneItaEstero) {
		this.selProfessioneItaEstero = selProfessioneItaEstero;
	}

	public BigDecimal getSelProfessioneItaAltri() {
		return selProfessioneItaAltri;
	}

	public void setSelProfessioneItaAltri(BigDecimal selProfessioneItaAltri) {
		this.selProfessioneItaAltri = selProfessioneItaAltri;
	}

	public BigDecimal getSelProfessioneItaAttuale() {
		return selProfessioneItaAttuale;
	}

	public void setSelProfessioneItaAttuale(BigDecimal selProfessioneItaAttuale) {
		this.selProfessioneItaAttuale = selProfessioneItaAttuale;
	}

	public BigDecimal getSelectedCondLavorativa() {
		return selectedCondLavorativa;
	}

	public void setSelectedCondLavorativa(BigDecimal selectedCondLavorativa) {
		this.selectedCondLavorativa = selectedCondLavorativa;
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

	public String getTipoPatente() {
		return tipoPatente;
	}

	public void setTipoPatente(String tipoPatente) {
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

	public List<String> getSelDisponibilita() {
		return selDisponibilita;
	}

	public void setSelDisponibilita(List<String> selDisponibilita) {
		this.selDisponibilita = selDisponibilita;
	}

	public List<String> getSelDispSpostamento() {
		return selDispSpostamento;
	}

	public void setSelDispSpostamento(List<String> selDispSpostamento) {
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

	// /**JsonBaseBean Methods*///
	@Override
	public List<String> checkObbligatorieta() {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

}
