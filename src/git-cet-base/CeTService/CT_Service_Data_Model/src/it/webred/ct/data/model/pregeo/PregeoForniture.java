package it.webred.ct.data.model.pregeo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the PREGEO_FORNITURE database table.
 * 
 */
@Entity
@Table(name="PREGEO_FORNITURE")
public class PregeoForniture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_FOR")
	private long idFor;

	@Column(name="CONTENUTO_ZIP")
	private String contenutoZip;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FORNITURA")
	private Date dataFornitura;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;

	@Column(name="NOME_FILE_ZIP")
	private String nomeFileZip;

    public PregeoForniture() {
    }

	public long getIdFor() {
		return this.idFor;
	}

	public void setIdFor(long idFor) {
		this.idFor = idFor;
	}

	public String getContenutoZip() {
		return this.contenutoZip;
	}

	public void setContenutoZip(String contenutoZip) {
		this.contenutoZip = contenutoZip;
	}

	public Date getDataFornitura() {
		return this.dataFornitura;
	}

	public void setDataFornitura(Date dataFornitura) {
		this.dataFornitura = dataFornitura;
	}

	public Date getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getNomeFileZip() {
		return this.nomeFileZip;
	}

	public void setNomeFileZip(String nomeFileZip) {
		this.nomeFileZip = nomeFileZip;
	}

}