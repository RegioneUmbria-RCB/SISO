package it.webred.cs.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CS_A_DATI_ESTERNI")
public class CsADatiEsterni {

	@Id
	@SequenceGenerator(name = "CS_A_DATI_ESTERNI_ID_GENERATOR", sequenceName = "SQ_CS_DATI_ESTERNI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_A_DATI_ESTERNI_ID_GENERATOR")
	private Long id;

	@Column(name = "NOME_FILE")
	private String nomeFile;

	@Lob
	private byte[] contenuti;

	@Column(name = "DATA_IMPORTAZIONE")
 	@Temporal(TemporalType.TIMESTAMP)
	private Date dataImportazione;

	@Column(name = "TIPOLOGIA")
	private String tipologia;
	
	@Column(name = "CONFIGURAZIONE_FILE")
	private String configurazioneFile;
	
	 
	
	
	public String getConfigurazioneFile() {
		return configurazioneFile;
	}

	public void setConfigurazioneFile(String configurazioneFile) {
		this.configurazioneFile = configurazioneFile;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public byte[] getContenuti() {
		return contenuti;
	}

	public void setContenuti(byte[] contenuti) {
		this.contenuti = contenuti;
	}

	public Date getDataImportazione() {
		return dataImportazione;
	}

	public void setDataImportazione(Date dataImportazione) {
		this.dataImportazione = dataImportazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CsADatiEsterni other = (CsADatiEsterni) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CsADatiEsterni [id=" + id + ", nomeFile=" + nomeFile + ", contenuti=[size: " + contenuti.length + "], dataImportazione="
				+ dataImportazione + "]";
	}

}
