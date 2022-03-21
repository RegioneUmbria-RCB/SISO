package it.webred.ct.data.model.cnc.flusso750;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;


// Verifica se usare mappedsuperclass sulla ChiaveULRuolo per mappare i campi

@Embeddable
public class ChiaveULPartita extends ChiaveULRuolo {
	
	@Column(name="COD_TIPO_UFFICIO")
	private String codTipoUfficio;

	@Column(name="COD_UFFICIO")
	private String codUfficio;

	@Column(name="CODICE_PARTITA")
	private String codicePartita;

	@Column(name="ANNO_RIFERIMENTO")
	private String annoRiferimento;

	public String getCodTipoUfficio() {
		return codTipoUfficio;
	}

	public void setCodTipoUfficio(String codTipoUfficio) {
		this.codTipoUfficio = codTipoUfficio;
	}

	public String getCodUfficio() {
		return codUfficio;
	}

	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}

	public String getCodicePartita() {
		return codicePartita;
	}

	public void setCodicePartita(String codicePartita) {
		this.codicePartita = codicePartita;
	}

	public String getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	

}
