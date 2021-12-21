package eu.smartpeg.rilevazionepresenze.data.model.pai;

import java.io.Serializable;
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
@Table(name = "AR_CS_PAI_PTI_RICH_DISP")
public class RichiestaDisponibilitaPaiPti implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5593088681790054890L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "PTI_RICH_DISP_ID_GENERATOR", sequenceName = "SQ_PAI_PTI_RICH_DISP", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PTI_RICH_DISP_ID_GENERATOR")
	private Long id;

	@Column(name = "ID_PROGETTO_INDIVIDUALE")
	private Long idProgettoIndividuale;

	@Column(name = "ID_STRUTTURA")
	private String idStruttura;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_RICHESTA")
	private Date dataRichiesta;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_ULT_MOD")
	private Date dataUltimaModifica;

	@Column(name = "STATO_RICH")
	private String statoRichiesta;

	@Column(name = "MOTIVO_RIFIUTO")
	private String motivoRifiuto;

	@Lob
	@Column(name = "DOC_PTI")
	private byte[] documento;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INIZIO_PERM")
	private Date dataInizioPermanenza;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE_PERM")
	private Date dataFinePermanenza;

	@Column(name = "DETTAGLIO_PTI")
	private String dettaglioPTI;

	@Column(name = "DETTAGLIO_MINORE")
	private String dettaglioMinore;
	
	@Column(name = "NOME_DOC")
	private String nomeDocumento;
	
	@Column(name = "DATA_ACC_STRUTT")
	private Date dataAccStruttura;
	
	@Column(name = "RICH_ATTIVA")
	private Boolean richAttiva;

	@Column(name = "COD_ROUTING")
	private String codRouting;
	
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
	
	

}
