package eu.smartpeg.rilevazionepresenze.data.model.pai;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.data.model.TipoStruttura;

/**
 * Entity implementation class for Entity: ArCsPaiPTI
 *
 */
@Entity
@Table(name="AR_CS_PAI_PTI")
public class ArCsPaiPTI implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@SequenceGenerator(name="AR_CS_PAI_PTI_ID_GENERATOR", sequenceName="SQ_ID_AR_CS_PAI_PTI",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_CS_PAI_PTI_ID_GENERATOR")
	private Long id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="COGNOME")
	private String cognome;
	
	@Column(name="CF")
	private String cf;
	
	@Column(name="SESSO")
	private String sesso;
	
	@Column(name="CITTADINANZA")
	private String cittadinanza;
	
	@Column(name="ANNO_NASCITA")
	private int annoNascita;
	
	@Column(name="COMUNE_RESIDENZA")
	private String comuneResidenza;
	
	@Column(name="NAZIONE_RESIDENZA")
	private String nazioneResidenza;
	
	@Column(name="VIA_RESIDENZA")
	private String viaResidenza;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_STRUTTURA")
	private Struttura struttura;
	
	@Lob
	@Column(name = "DOC_PAI")
	private byte[] documentoPai;
	
	@Column(name="NOME_DOC_PAI")
	private String nomeDocPai;
	
	@Lob
	@Column(name = "DOC_PTI_EQUI")
	private byte[] documentoPtiEqui;
	
	@Column(name="NOME_DOC_PTI_EQUI")
	private String nomeDocPtiEqui;
	
	@Column(name="STATO")
	private String stato;
	
	@Column(name="COD_ROUTING")
	private String codRouting;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "DATA_INIZIO_PERM")
    private Date dataInizioPermamenza;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_FINE_PERM")
    private Date dataFinePermanenza;
	

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TIPO_STRUTTURA")
    private TipoStruttura tipoStruttura;
    
    @Column(name="DIARIO_PAI_ID")
    private Long diarioPaiId;
    
	public ArCsPaiPTI() {
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getCognome() {
		return cognome;
	}



	public void setCognome(String cognome) {
		this.cognome = cognome;
	}



	public String getCf() {
		return cf;
	}



	public void setCf(String cf) {
		this.cf = cf;
	}



	public String getSesso() {
		return sesso;
	}



	public void setSesso(String sesso) {
		this.sesso = sesso;
	}



	public String getCittadinanza() {
		return cittadinanza;
	}



	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}



	public int getAnnoNascita() {
		return annoNascita;
	}



	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}



	public String getComuneResidenza() {
		return comuneResidenza;
	}



	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}



	public String getNazioneResidenza() {
		return nazioneResidenza;
	}



	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}



	public String getViaResidenza() {
		return viaResidenza;
	}



	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}





	public Struttura getStruttura() {
		return struttura;
	}



	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}



	public byte[] getDocumentoPai() {
		return documentoPai;
	}



	public void setDocumentoPai(byte[] documentoPai) {
		this.documentoPai = documentoPai;
	}



	public String getNomeDocPai() {
		return nomeDocPai;
	}



	public void setNomeDocPai(String nomeDocPai) {
		this.nomeDocPai = nomeDocPai;
	}



	public byte[] getDocumentoPtiEqui() {
		return documentoPtiEqui;
	}



	public void setDocumentoPtiEqui(byte[] documentoPtiEqui) {
		this.documentoPtiEqui = documentoPtiEqui;
	}



	public String getNomeDocPtiEqui() {
		return nomeDocPtiEqui;
	}



	public void setNomeDocPtiEqui(String nomeDocPtiEqui) {
		this.nomeDocPtiEqui = nomeDocPtiEqui;
	}



	public String getStato() {
		return stato;
	}



	public void setStato(String stato) {
		this.stato = stato;
	}



	public String getCodRouting() {
		return codRouting;
	}



	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}



	public Date getDataInizioPermamenza() {
		return dataInizioPermamenza;
	}



	public void setDataInizioPermamenza(Date dataInizioPermamenza) {
		this.dataInizioPermamenza = dataInizioPermamenza;
	}



	public Date getDataFinePermanenza() {
		return dataFinePermanenza;
	}



	public void setDataFinePermanenza(Date dataFinePermanenza) {
		this.dataFinePermanenza = dataFinePermanenza;
	}



	public TipoStruttura getTipoStruttura() {
		return tipoStruttura;
	}



	public void setTipoStruttura(TipoStruttura tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}



	public Long getDiarioPaiId() {
		return diarioPaiId;
	}



	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}
	
	
	
	
	
	
	
	
	
	
   
}
