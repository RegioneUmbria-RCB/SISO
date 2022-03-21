package eu.smartpeg.rievazionepresenze.dto.pai;

import java.io.Serializable;
import java.util.Date;

public class RichiestaMinorePaiPtiPDTO implements Serializable{

	private static final long serialVersionUID = 3579237437791697320L;

	private Long id;
	
	private Long idProgettoIndividuale;

	private String idStruttura;

	private Date dataRichiesta;

	private Date dataInizioPermanenza;

	private Date dataFinePermanenza;

	private String dettaglioPTI;

	private String dettaglioMinore;
	
	private String codRouting;
	
	private byte[] documento;
	//dati minore
	
	private String nome;
	
	private String cognome;
	
	private int annoNascita;
	
	private String codiceFiscale;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProgettoIndividuale() {
		return idProgettoIndividuale;
	}

	public void setIdProgettoIndividuale(Long idProgettoIndividuale) {
		this.idProgettoIndividuale = idProgettoIndividuale;
	}

	public String getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(String idStruttura) {
		this.idStruttura = idStruttura;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public Date getDataInizioPermanenza() {
		return dataInizioPermanenza;
	}

	public void setDataInizioPermanenza(Date dataInizioPermanenza) {
		this.dataInizioPermanenza = dataInizioPermanenza;
	}

	public Date getDataFinePermanenza() {
		return dataFinePermanenza;
	}

	public void setDataFinePermanenza(Date dataFinePermanenza) {
		this.dataFinePermanenza = dataFinePermanenza;
	}

	public String getDettaglioPTI() {
		return dettaglioPTI;
	}

	public void setDettaglioPTI(String dettaglioPTI) {
		this.dettaglioPTI = dettaglioPTI;
	}

	public String getDettaglioMinore() {
		return dettaglioMinore;
	}

	public void setDettaglioMinore(String dettaglioMinore) {
		this.dettaglioMinore = dettaglioMinore;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}
	
	
}
