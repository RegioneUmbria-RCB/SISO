package it.webred.ct.data.model.segnalazionequalificata;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="S_OF_PRATICA")
public class SOfPratica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;

	@Column(name="ACC_ANNOTAZIONI")
	private String accAnnotazioni;

	@Column(name="ACC_CODI_FISC")
	private String accCodiFisc;

	@Column(name="ACC_CODI_PIVA")
	private String accCodiPiva;

	@Column(name="ACC_COGNOME")
	private String accCognome;

	@Column(name="ACC_COMU_NASC")
	private String accComuNasc;

    @Temporal( TemporalType.DATE)
	@Column(name="ACC_DATA_FINE")
	private Date accDataFine;

    @Temporal( TemporalType.DATE)
	@Column(name="ACC_DATA_INIZIO")
	private Date accDataInizio;

    @Temporal( TemporalType.DATE)
	@Column(name="ACC_DATA_NASC")
	private Date accDataNasc;

	@Column(name="ACC_DENOMINAZIONE")
	private String accDenominazione;

	@Column(name="ACC_ESITO")
	private String accEsito;

	@Column(name="ACC_EVIDENZE")
	private String accEvidenze;

	@Column(name="ACC_FINALITA")
	private String accFinalita;

	@Column(name="ACC_NOME")
	private String accNome;

	@Column(name="ACC_STATO")
	private String accStato;
	
	@Column(name="ACC_AZIONE")
	private String accAzione;

	@Column(name="ACC_TIPO_SOGG")
	private String accTipoSogg;

    @Temporal( TemporalType.DATE)
	@Column(name="ADE_DATA_FINE")
	private Date adeDataFine;

	@Column(name="ADE_ESITO")
	private String adeEsito;
	
	@Column(name="ADE_STATO")
	private String adeStato;

	@Column(name="ANNO_REF")
	private String annoRef;

	@Column(name="FLG_LIQ_QUOTE")
	private String flgLiqQuote;

	@Column(name="OPERATORE_ID")
	private String operatoreId;

	@Column(name="RES_CODI_FISC")
	private String resCodiFisc;

	@Column(name="RES_COGNOME")
	private String resCognome;

	@Column(name="RES_EMAIL")
	private String resEmail;

	@Column(name="RES_NOME")
	private String resNome;

	@Column(name="RES_TELEFONO")
	private String resTelefono;

    public SOfPratica() {
    }
    
    public String toUpperCase(String s){
    	if(s!=null)
    		s = s.toUpperCase();
    	return s;
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccAnnotazioni() {
		return this.accAnnotazioni;
	}

	public void setAccAnnotazioni(String accAnnotazioni) {
		this.accAnnotazioni = accAnnotazioni;
	}

	public String getAccCodiFisc() {
		return this.accCodiFisc;
	}

	public void setAccCodiFisc(String accCodiFisc) {
		this.accCodiFisc = toUpperCase(accCodiFisc);
	}

	public String getAccCodiPiva() {
		return this.accCodiPiva;
	}

	public void setAccCodiPiva(String accCodiPiva) {
		this.accCodiPiva = toUpperCase(accCodiPiva);
	}

	public String getAccCognome() {
		return this.accCognome;
	}

	public void setAccCognome(String accCognome) {
		this.accCognome = toUpperCase(accCognome);
	}

	public String getAccComuNasc() {
		return this.accComuNasc;
	}

	public void setAccComuNasc(String accComuNasc) {
		this.accComuNasc = accComuNasc;
	}

	public Date getAccDataFine() {
		return this.accDataFine;
	}

	public void setAccDataFine(Date accDataFine) {
		this.accDataFine = accDataFine;
	}

	public Date getAccDataInizio() {
		return this.accDataInizio;
	}

	public void setAccDataInizio(Date accDataInizio) {
		this.accDataInizio = accDataInizio;
	}

	public Date getAccDataNasc() {
		return this.accDataNasc;
	}

	public void setAccDataNasc(Date accDataNasc) {
		this.accDataNasc = accDataNasc;
	}

	public String getAccDenominazione() {
		return this.accDenominazione;
	}

	public void setAccDenominazione(String accDenominazione) {
		this.accDenominazione = toUpperCase(accDenominazione);
	}

	public String getAccEsito() {
		return this.accEsito;
	}

	public void setAccEsito(String accEsito) {
		this.accEsito = accEsito;
	}

	public String getAccEvidenze() {
		return this.accEvidenze;
	}

	public void setAccEvidenze(String accEvidenze) {
		this.accEvidenze = accEvidenze;
	}

	public String getAccFinalita() {
		return this.accFinalita;
	}

	public void setAccFinalita(String accFinalita) {
		this.accFinalita = accFinalita;
	}

	public String getAccNome() {
		return this.accNome;
	}

	public void setAccNome(String accNome) {
		this.accNome = toUpperCase(accNome);
	}

	public String getAccStato() {
		return this.accStato;
	}

	public void setAccStato(String accStato) {
		this.accStato = accStato;
	}

	public String getAccTipoSogg() {
		return this.accTipoSogg;
	}

	public void setAccTipoSogg(String accTipoSogg) {
		this.accTipoSogg = accTipoSogg;
	}

	public Date getAdeDataFine() {
		return this.adeDataFine;
	}

	public void setAdeDataFine(Date adeDataFine) {
		this.adeDataFine = adeDataFine;
	}

	public String getAdeEsito() {
		return this.adeEsito;
	}

	public void setAdeEsito(String adeEsito) {
		this.adeEsito = adeEsito;
	}

	public String getAnnoRef() {
		return this.annoRef;
	}

	public void setAnnoRef(String annoRef) {
		this.annoRef = annoRef;
	}

	public String getFlgLiqQuote() {
		return this.flgLiqQuote;
	}

	public void setFlgLiqQuote(String flgLiqQuote) {
		this.flgLiqQuote = flgLiqQuote;
	}

	public String getOperatoreId() {
		return this.operatoreId;
	}

	public void setOperatoreId(String operatoreId) {
		this.operatoreId = operatoreId;
	}

	public String getResCodiFisc() {
		return this.resCodiFisc;
	}

	public void setResCodiFisc(String resCodiFisc) {
		this.resCodiFisc = resCodiFisc.toUpperCase();
	}

	public String getResCognome() {
		return this.resCognome;
	}

	public void setResCognome(String resCognome) {
		this.resCognome = toUpperCase(resCognome);
	}

	public String getResEmail() {
		return this.resEmail;
	}

	public void setResEmail(String resEmail) {
		this.resEmail = resEmail;
	}

	public String getResNome() {
		return this.resNome;
	}

	public void setResNome(String resNome) {
		this.resNome = toUpperCase(resNome);
	}

	public String getResTelefono() {
		return this.resTelefono;
	}

	public void setResTelefono(String resTelefono) {
		this.resTelefono = resTelefono;
	}

	public void setAdeStato(String adeStato) {
		this.adeStato = adeStato;
	}

	public String getAdeStato() {
		return adeStato;
	}

	public void setAccAzione(String accAzione) {
		this.accAzione = accAzione;
	}

	public String getAccAzione() {
		return accAzione;
	}

}