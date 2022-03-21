package it.webred.rulengine.dto;

public class StagingDTO implements java.io.Serializable {
	
	
	private Long idFile;
	private String progIscrizione;
	private Long keyDett;
	private Long periodoDa;
	private Long periodoA;
	private Long flgAss;
	private Long servizio;
	private String nomeFile;
	
	public StagingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StagingDTO(Long idFile, String progIscrizione, Long keyDett,
			Long periodoDa, Long periodoA, Long flgAss, Long servizio,
			String nomeFile) {
		super();
		this.idFile = idFile;
		this.progIscrizione = progIscrizione;
		this.keyDett = keyDett;
		this.periodoDa = periodoDa;
		this.periodoA = periodoA;
		this.flgAss = flgAss;
		this.servizio = servizio;
		this.nomeFile = nomeFile;
	}

	

	public Long getIdFile() {
		return idFile;
	}

	public void setIdFile(Long idFile) {
		this.idFile = idFile;
	}

	public String getProgIscrizione() {
		return progIscrizione;
	}

	public void setProgIscrizione(String progIscrizione) {
		this.progIscrizione = progIscrizione;
	}

	public Long getKeyDett() {
		return keyDett;
	}

	public void setKeyDett(Long keyDett) {
		this.keyDett = keyDett;
	}

	public Long getPeriodoDa() {
		return periodoDa;
	}

	public void setPeriodoDa(Long periodoDa) {
		this.periodoDa = periodoDa;
	}

	public Long getPeriodoA() {
		return periodoA;
	}

	public void setPeriodoA(Long periodoA) {
		this.periodoA = periodoA;
	}

	public Long getFlgAss() {
		return flgAss;
	}

	public void setFlgAss(Long flgAss) {
		this.flgAss = flgAss;
	}

	public Long getServizio() {
		return servizio;
	}

	public void setServizio(Long servizio) {
		this.servizio = servizio;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	
}
