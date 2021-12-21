package eu.smartpeg.rievazionepresenze.dto.pai;

import java.io.Serializable;
import java.util.Date;

import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti.StatiRichiesta.STATO_RICHIESTA;

public class RichiestaDisponibilitaPaiPtiDTO implements Serializable {

	private Long PaiPTIFaseEnum_CHIUSA_OK = new Long(6);
	
	private static final long serialVersionUID = 3579237437791697320L;

	private Long id;

	private Long idProgettoIndividuale;

	private String idStruttura;

	private Date dataRichiesta;

	private Date dataUltimaModifica;

	private String statoRichiesta;

	private String motivoRifiuto;

	private byte[] documento;

	private Date dataInizioPermanenza;

	private Date dataFinePermanenza;

	private String dettaglioPTI;

	private String dettaglioMinore;

	private String nomeDocumento;

	private Date dataAccStruttura;

	private Boolean richAttiva;

	private String codRouting;

	private DettaglioMinore minore;

	private DettaglioPTI pti;

	private String note;

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

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
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

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getStatoRichiesta() {
		return statoRichiesta;
	}

	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

	public String getMotivoRifiuto() {
		return motivoRifiuto;
	}

	public void setMotivoRifiuto(String motivoRifiuto) {
		this.motivoRifiuto = motivoRifiuto;
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

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public Date getDataAccStruttura() {
		return dataAccStruttura;
	}

	public void setDataAccStruttura(Date dataAccStruttura) {
		this.dataAccStruttura = dataAccStruttura;
	}

	public Boolean getRichAttiva() {
		return richAttiva;
	}

	public void setRichAttiva(Boolean richAttiva) {
		this.richAttiva = richAttiva;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public String getStatoRichiestaDescrizione() {
		if (this.richAttiva != null && !this.richAttiva
				&& !this.statoRichiesta.equals(STATO_RICHIESTA.ACCETTATA.getCodice())) {
			return "Accettata da altra struttura";
		}
		STATO_RICHIESTA eStatoRichiesta = STATO_RICHIESTA.valueOf(statoRichiesta);
		return eStatoRichiesta.getDescrizione();

	}

	public String getStatoRichiestaAccettata() {
		if (this.pti != null) {
			if (PaiPTIFaseEnum_CHIUSA_OK.equals(pti.getFaseAttuale())) {
				return "CHIUSA";
			}
		}
		return "APERTA";
	}

	public DettaglioMinore getMinore() {
		return minore;
	}

	public void setMinore(DettaglioMinore minore) {
		this.minore = minore;
	}

	public DettaglioPTI getPti() {
		return pti;
	}

	public void setPti(DettaglioPTI pti) {
		this.pti = pti;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
