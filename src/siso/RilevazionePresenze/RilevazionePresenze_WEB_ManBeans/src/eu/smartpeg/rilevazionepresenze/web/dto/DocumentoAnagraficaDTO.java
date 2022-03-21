package eu.smartpeg.rilevazionepresenze.web.dto;

import java.util.Date;


public class DocumentoAnagraficaDTO {
	
	private long id;

	private Date dataScadenza;
	private Long idTipologiaDocumento;
	private String note;
	private String numeroDocumento;
	private Long idAnagrafica;

	public DocumentoAnagraficaDTO() {
//		this.tipoDocumento = new TipoDocumento();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	public String getNote() {
		return this.note;
	}

	public Long getIdTipologiaDocumento() {
		return idTipologiaDocumento;
	}

	public void setIdTipologiaDocumento(Long idTipologiaDocumento) {
		this.idTipologiaDocumento = idTipologiaDocumento;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Long getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}
}
