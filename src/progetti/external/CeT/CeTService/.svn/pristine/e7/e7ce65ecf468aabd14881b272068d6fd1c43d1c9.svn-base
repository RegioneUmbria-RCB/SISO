package it.webred.ct.data.model.pregeo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the PREGEO_INFO database table.
 * 
 */
@Entity
@Table(name="PREGEO_INFO")
public class PregeoInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_INFO")
	private long idInfo;

	@Column(name="CODICE_PREGEO")
	private String codicePregeo;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_PREGEO")
	private Date dataPregeo;

	private String denominazione;

	private String foglio;

	@Column(name="NOME_FILE_PDF")
	private String nomeFilePdf;

	private String particella;

	@Column(name="RELAZIONE_TECNICA")
	private String relazioneTecnica;

	private String tecnico;

	@Column(name="TIPO_TECNICO")
	private String tipoTecnico;

	@Column(name="NOTA")
	private String nota = "";

    public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PregeoInfo() {
    }

	public long getIdInfo() {
		return this.idInfo;
	}

	public void setIdInfo(long idInfo) {
		this.idInfo = idInfo;
	}

	public String getCodicePregeo() {
		return this.codicePregeo;
	}

	public void setCodicePregeo(String codicePregeo) {
		this.codicePregeo = codicePregeo;
	}

	public Date getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataPregeo() {
		return this.dataPregeo;
	}

	public void setDataPregeo(Date dataPregeo) {
		this.dataPregeo = dataPregeo;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNomeFilePdf() {
		return this.nomeFilePdf;
	}

	public void setNomeFilePdf(String nomeFilePdf) {
		this.nomeFilePdf = nomeFilePdf;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getRelazioneTecnica() {
		return this.relazioneTecnica;
	}

	public void setRelazioneTecnica(String relazioneTecnica) {
		this.relazioneTecnica = relazioneTecnica;
	}

	public String getTecnico() {
		return this.tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getTipoTecnico() {
		return this.tipoTecnico;
	}

	public void setTipoTecnico(String tipoTecnico) {
		this.tipoTecnico = tipoTecnico;
	}

}