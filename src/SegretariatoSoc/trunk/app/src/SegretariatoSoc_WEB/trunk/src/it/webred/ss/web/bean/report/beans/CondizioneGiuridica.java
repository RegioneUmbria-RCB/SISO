package it.webred.ss.web.bean.report.beans;

public class CondizioneGiuridica {

	private String status = "-";
	private boolean presenteDaPiuDi3mesi;
	private String titoloDiSoggiorno = "-";
	private String tipoDiPermesso = "-";
	private String permessoNumId = "-";
	private String permessoScadenza = "-";



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public boolean isPresenteDaPiuDi3mesi() {
		return presenteDaPiuDi3mesi;
	}



	public void setPresenteDaPiuDi3mesi(boolean presenteDaPiuDi3mesi) {
		this.presenteDaPiuDi3mesi = presenteDaPiuDi3mesi;
	}



	public String getTitoloDiSoggiorno() {
		return titoloDiSoggiorno;
	}



	public void setTitoloDiSoggiorno(String titoloDiSoggiorno) {
		this.titoloDiSoggiorno = titoloDiSoggiorno;
	}



	public String getTipoDiPermesso() {
		return tipoDiPermesso;
	}



	public void setTipoDiPermesso(String tipoDiPermesso) {
		this.tipoDiPermesso = tipoDiPermesso;
	}



	public String getPermessoNumId() {
		return permessoNumId;
	}



	public void setPermessoNumId(String permessoNumId) {
		this.permessoNumId = permessoNumId;
	}



	public String getPermessoScadenza() {
		return permessoScadenza;
	}



	public void setPermessoScadenza(String permessoScadenza) {
		this.permessoScadenza = permessoScadenza;
	}

}
