package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatiEsterniSoggettoViewDTO implements Serializable {

	private static final long serialVersionUID = 3070559612761383162L;

	public static class DatiEsterni implements Serializable {

		private static final long serialVersionUID = -5314234423989566864L;

		private String nomeCampo;
		private String valore;

		public DatiEsterni(String nomeCampo, String valore) {
			this.nomeCampo = nomeCampo;
			this.valore = valore;
		}

		public String getNomeCampo() {
			return nomeCampo;
		}

		public void setNomeCampo(String nomeCampo) {
			this.nomeCampo = nomeCampo;
		}

		public String getValore() {
			return valore;
		}

		public void setValore(String valore) {
			this.valore = valore;
		}

		@Override
		public String toString() {
			return "D.Ext [n=" + nomeCampo + ", v=" + valore + "]";
		}

	}

	public static class RigaDatiEsterni implements Serializable {

		private static final long serialVersionUID = -5667577416851656356L;

		private List<DatiEsterni> contenuti;

		public RigaDatiEsterni() {
			contenuti = new ArrayList<DatiEsterniSoggettoViewDTO.DatiEsterni>();
		}

		public List<DatiEsterni> getContenuti() {
			return contenuti;
		}

		public void setContenuti(List<DatiEsterni> contenuti) {
			this.contenuti = contenuti;
		}

		public boolean addDatiEsterni(DatiEsterni e) {
			return contenuti.add(e);
		}
	}

	private String nomeFile;
	private Date dataImportazione;
	private String tipologia;
	private List<RigaDatiEsterni> righeDatiEsterni;

	public DatiEsterniSoggettoViewDTO() {
		righeDatiEsterni = new ArrayList<RigaDatiEsterni>();
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Date getDataImportazione() {
		return dataImportazione;
	}

	
	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public void setDataImportazione(Date dataImportazione) {
		this.dataImportazione = dataImportazione;
	}

	public List<RigaDatiEsterni> getRigheDatiEsterni() {
		return righeDatiEsterni;
	}

	public void setRigheDatiEsterni(List<RigaDatiEsterni> righeDatiEsterni) {
		this.righeDatiEsterni = righeDatiEsterni;
	}

	public void addRigaDatiEsterni(RigaDatiEsterni rde) {
		righeDatiEsterni.add(rde);
	}

	@Override
	public String toString() {
		return "DatiEsterniSoggettoViewDTO [nomeFile=" + nomeFile + ", dataImportazione=" + dataImportazione + ", righeDatiEsterni="
				+ righeDatiEsterni + "]";
	}

}
