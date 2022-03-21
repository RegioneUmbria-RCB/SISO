package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsPaiAffidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;

	private Long diarioPaiId;

	private String numeroDecreto;

	private Date dataDecreto;

	private String  codiceFormeAffidamento;

	private String codiceCollocamento;

	private String codiceEntitaAffido;

	private String descEntitaAffidoParziale;

	private String codiceTipoAccoglienza;

	private String codiceNaturaAccoglienza;
	
	//SISO-981 Inizio
	private String codiceAutoritaProvvedimento;
	private String codiceInserimentoResidenziale;
	
	private Boolean affidamentoProfessionale=false;
	
	private String codiceAdottabile;

	private Boolean presenzaRetiDiFamiglie=false;

	private String codiceFrequenzaContattiMinore;

	private String codiceConvivenzaOrigineAffidataria;

	private String impegnoFamigliaOrigine;

	private String impegnoFamigliaAffidataria;

	private String impegnoMinore;

	private String impegnoServizioSociale;

	private String impegnoAltriSoggetti;

	private String codiceTipoAffido;
	
	//Inizio SISO-1172
	private Boolean affidamentoLungoTermine = false; 
	
	private String modalitaRapportoNucleoOrigine;
	
	private String modalitaRapportoOrigAff;
	
	private String codiceFrequenzaContattiAff;

	private String modalitaRappEsperienzaOrig;
	
	private String modalitaRappEsperienzaFamAff;
	
	private String noteGestioneSanita;
	
	private Boolean presenzaTutore = false ;
	private Boolean presenzaCuratore = false;
	
	//Fine SISO-1172
	private CsPaiAffidoFamigliaOrigineDTO famigliaOrigine = new CsPaiAffidoFamigliaOrigineDTO();

	private CsPaiAffidoFamigliaAffidatariaDTO famigliaAffidataria = new CsPaiAffidoFamigliaAffidatariaDTO();

	private List<CsPaiAffidoStatoDTO> statiAffido = new ArrayList<CsPaiAffidoStatoDTO>();

	private List<CsPaiAffidoSoggettoDTO> soggettiAffido = new ArrayList<CsPaiAffidoSoggettoDTO>();
	
	public CsPaiAffidoDTO() {
		super();
	}

	public CsPaiAffidoDTO(PaiAffidoStatoEnum stato, String codiceNaturaAccoglienza) {
		CsPaiAffidoStatoDTO statoIniziale = new CsPaiAffidoStatoDTO(stato.getValore(), stato.getDescrizione());
		this.codiceNaturaAccoglienza = codiceNaturaAccoglienza;
		statiAffido.add(statoIniziale);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public String getNumeroDecreto() {
		return numeroDecreto;
	}

	public void setNumeroDecreto(String numeroDecreto) {
		this.numeroDecreto = numeroDecreto;
	}

	public Date getDataDecreto() {
		return dataDecreto;
	}

	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	//SISO-1172
	public String getCodiceFormeAffidamento() {
		return codiceFormeAffidamento;
	}
     
	public void setCodiceFormeAffidamento(String codiceFormeAffidamento) {
		this.codiceFormeAffidamento = codiceFormeAffidamento;
	}

	public String getCodiceCollocamento() {
		return codiceCollocamento;
	}

	public void setCodiceCollocamento(String codiceCollocamento) {
		this.codiceCollocamento = codiceCollocamento;
	}

	public String getCodiceEntitaAffido() {
		return codiceEntitaAffido;
	}

	public void setCodiceEntitaAffido(String codiceEntitaAffido) {
		this.codiceEntitaAffido = codiceEntitaAffido;
	}

	public String getDescEntitaAffidoParziale() {
		return descEntitaAffidoParziale;
	}

	public void setDescEntitaAffidoParziale(String descEntitaAffidoParziale) {
		this.descEntitaAffidoParziale = descEntitaAffidoParziale;
	}

	public String getCodiceTipoAccoglienza() {
		return codiceTipoAccoglienza;
	}

	public void setCodiceTipoAccoglienza(String codiceTipoAccoglienza) {
		this.codiceTipoAccoglienza = codiceTipoAccoglienza;
	}

	public String getCodiceNaturaAccoglienza() {
		return codiceNaturaAccoglienza;
	}

	public void setCodiceNaturaAccoglienza(String codiceNaturaAccoglienza) {
		this.codiceNaturaAccoglienza = codiceNaturaAccoglienza;
	}

	public Boolean getAffidamentoProfessionale() {
		return affidamentoProfessionale;
	}

	public void setAffidamentoProfessionale(Boolean affidamentoProfessionale) {
		this.affidamentoProfessionale = affidamentoProfessionale;
	}

	public String getCodiceAdottabile() {
		return codiceAdottabile;
	}

	public void setCodiceAdottabile(String codiceAdottabile) {
		this.codiceAdottabile = codiceAdottabile;
	}

	public Boolean getPresenzaRetiDiFamiglie() {
		return presenzaRetiDiFamiglie;
	}

	public void setPresenzaRetiDiFamiglie(Boolean presenzaRetiDiFamiglie) {
		this.presenzaRetiDiFamiglie = presenzaRetiDiFamiglie;
	}

	public String getCodiceFrequenzaContattiMinore() {
		return codiceFrequenzaContattiMinore;
	}

	public void setCodiceFrequenzaContattiMinore(
			String codiceFrequenzaContattiMinore) {
		this.codiceFrequenzaContattiMinore = codiceFrequenzaContattiMinore;
	}

	public String getCodiceConvivenzaOrigineAffidataria() {
		return codiceConvivenzaOrigineAffidataria;
	}

	public void setCodiceConvivenzaOrigineAffidataria(
			String codiceConvivenzaOrigineAffidataria) {
		this.codiceConvivenzaOrigineAffidataria = codiceConvivenzaOrigineAffidataria;
	}

	public String getImpegnoFamigliaOrigine() {
		return impegnoFamigliaOrigine;
	}

	public void setImpegnoFamigliaOrigine(String impegnoFamigliaOrigine) {
		this.impegnoFamigliaOrigine = impegnoFamigliaOrigine;
	}

	public String getImpegnoFamigliaAffidataria() {
		return impegnoFamigliaAffidataria;
	}

	public void setImpegnoFamigliaAffidataria(String impegnoFamigliaAffidataria) {
		this.impegnoFamigliaAffidataria = impegnoFamigliaAffidataria;
	}

	public String getImpegnoMinore() {
		return impegnoMinore;
	}

	public void setImpegnoMinore(String impegnoMinore) {
		this.impegnoMinore = impegnoMinore;
	}

	public String getImpegnoServizioSociale() {
		return impegnoServizioSociale;
	}

	public void setImpegnoServizioSociale(String impegnoServizioSociale) {
		this.impegnoServizioSociale = impegnoServizioSociale;
	}

	public String getImpegnoAltriSoggetti() {
		return impegnoAltriSoggetti;
	}

	public void setImpegnoAltriSoggetti(String impegnoAltriSoggetti) {
		this.impegnoAltriSoggetti = impegnoAltriSoggetti;
	}

	public String getCodiceTipoAffido() {
		if (codiceTipoAffido == null) {
			//SISO-1172 La tipologia Affido può essere solo di tipo Familiare
			//codiceTipoAffido = PaiAffidoDominiEnum.TIPO_AFFIDO.name() + "_ACCOGLIENZA";
			codiceTipoAffido = PaiAffidoDominiEnum.TIPO_AFFIDO.name() + "_FAMILIARE";
		}
		return codiceTipoAffido;
	}

	public void setCodiceTipoAffido(String codiceTipoAffido) {
		this.codiceTipoAffido = codiceTipoAffido;
	}
	
	public CsPaiAffidoFamigliaOrigineDTO getFamigliaOrigine() {
		return famigliaOrigine;
	}

	public void setFamigliaOrigine(CsPaiAffidoFamigliaOrigineDTO famigliaOrigine) {
		this.famigliaOrigine = famigliaOrigine;
	}

	public CsPaiAffidoFamigliaAffidatariaDTO getFamigliaAffidataria() {
		return famigliaAffidataria;
	}

	public void setFamigliaAffidataria(
			CsPaiAffidoFamigliaAffidatariaDTO famigliaAffidataria) {
		this.famigliaAffidataria = famigliaAffidataria;
	}

	public List<CsPaiAffidoStatoDTO> getStatiAffido() {
		return statiAffido;
	}

	public void setStatiAffido(List<CsPaiAffidoStatoDTO> statiAffido) {
		this.statiAffido = statiAffido;
	}

	public List<CsPaiAffidoSoggettoDTO> getSoggettiAffido() {
		return soggettiAffido;
	}

	public void setSoggettiAffido(List<CsPaiAffidoSoggettoDTO> soggettiAffido) {
		this.soggettiAffido = soggettiAffido;
	}
	
	// SISO-981 Inizio
	
		public String getCodiceAutoritaProvvedimento() {
			return codiceAutoritaProvvedimento;
		}

		public void setCodiceAutoritaProvvedimento(String codiceAutoritaProvvedimento) {
			this.codiceAutoritaProvvedimento = codiceAutoritaProvvedimento;
		}

		public String getCodiceInserimentoResidenziale() {
			return codiceInserimentoResidenziale;
		}

		public void setCodiceInserimentoResidenziale(
				String codiceInserimentoResidenziale) {
			this.codiceInserimentoResidenziale = codiceInserimentoResidenziale;
		}
		// SISO-981 Fine
		
     //SISO-1172		

		public Boolean getAffidamentoLungoTermine() {
			return affidamentoLungoTermine;
		}

		public void setAffidamentoLungoTermine(Boolean affidamentoLungoTermine) {
			this.affidamentoLungoTermine = affidamentoLungoTermine;
		}
		
		
		
	public String getModalitaRapportoNucleoOrigine() {
			return modalitaRapportoNucleoOrigine;
		}

		public void setModalitaRapportoNucleoOrigine(
				String modalitaRapportoNucleoOrigine) {
			this.modalitaRapportoNucleoOrigine = modalitaRapportoNucleoOrigine;
		}

	public String getModalitaRapportoOrigAff() {
			return modalitaRapportoOrigAff;
		}

		public void setModalitaRapportoOrigAff(String modalitaRapportoOrigAff) {
			this.modalitaRapportoOrigAff = modalitaRapportoOrigAff;
		}

		public String getCodiceFrequenzaContattiAff() {
			return codiceFrequenzaContattiAff;
		}

		public void setCodiceFrequenzaContattiAff(String codiceFrequenzaContattiAff) {
			this.codiceFrequenzaContattiAff = codiceFrequenzaContattiAff;
		}
 
		public String getModalitaRappEsperienzaOrig() {
			return modalitaRappEsperienzaOrig;
		}

		public void setModalitaRappEsperienzaOrig(String modalitaRappEsperienzaOrig) {
			this.modalitaRappEsperienzaOrig = modalitaRappEsperienzaOrig;
		}

		public String getModalitaRappEsperienzaFamAff() {
			return modalitaRappEsperienzaFamAff;
		}

		public void setModalitaRappEsperienzaFamAff(String modalitaRappEsperienzaFamAff) {
			this.modalitaRappEsperienzaFamAff = modalitaRappEsperienzaFamAff;
		}

		public String getNoteGestioneSanita() {
			return noteGestioneSanita;
		}

		public void setNoteGestioneSanita(String noteGestioneSanita) {
			this.noteGestioneSanita = noteGestioneSanita;
		}
		
		public Boolean getPresenzaTutore() {
			return presenzaTutore;
		}

		public void setPresenzaTutore(Boolean presenzaTutore) {
			this.presenzaTutore = presenzaTutore;
		}

		public Boolean getPresenzaCuratore() {
			return presenzaCuratore;
		}

		public void setPresenzaCuratore(Boolean presenzaCuratore) {
			this.presenzaCuratore = presenzaCuratore;
		}
		
	//FINE SISO-1172		

	public Integer getCodiceStatoAttuale(){

		Integer toReturn = null;

		for (CsPaiAffidoStatoDTO stato : statiAffido) {
			if (stato.getDataA() == null) {
				toReturn = stato.getCodice();
				break;
			}
		}

		return toReturn;
	}


	public Date getDataStatoAffidoAttuale() {

		Date toReturn = null;

		for (CsPaiAffidoStatoDTO stato : statiAffido) {
			if (stato.getDataA() == null) {
				toReturn = stato.getDataStatoAffido();
				break;
			}
		}

		return toReturn;
	}

}
