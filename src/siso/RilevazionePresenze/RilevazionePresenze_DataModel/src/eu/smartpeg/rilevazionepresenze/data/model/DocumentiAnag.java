package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


/**
 * The persistent class for the AR_RP_DOCUMENTI_ANAG database table.
 * 
 */
@Entity
@Table(name="AR_RP_DOCUMENTI_ANAG")
@NamedQueries(value={
		@NamedQuery(name="DocumentiAnag.findAll", query="SELECT d FROM DocumentiAnag d"),
		@NamedQuery(name="DocumentiAnag.deleteById", query="DELETE FROM DocumentiAnag d where d.id= :id")
})
public class DocumentiAnag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static String VALIDATION_MSG_DATA_SCADENZA_NULL="Inserire data scadenza ";
	public static String VALIDATION_MSG_NUMERO_DOCUMENTO_NULL="Inserire numero documento ";
	public static String VALIDATION_MSG_TIPOLOGIA_DOCUMENTO="Selezionare una tipologia documento";

	@Id
	@SequenceGenerator(name="AR_RP_DOCUMENTI_ANAG_ID_GENERATOR", sequenceName="SQ_ID"  )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_RP_DOCUMENTI_ANAG_ID_GENERATOR")
	private Long id;

	@Column(name="DATA_SCADENZA")
	private Date dataScadenza;

////	//bi-directional many-to-one association to TipoDocumento
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="ID_TIPOLOGIA_DOCUMENTO")
//	private TipoDocumento tipoDocumento;

	@Column(name="ID_TIPOLOGIA_DOCUMENTO")
	private Long idTipologiaDocumento;
	
	private String note;

	@Column(name="NUMERO_DOCUMENTO")
	private String numeroDocumento;

	//bi-directional many-to-one association to Anagrafica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ANAGRAFICA")
	private Anagrafica arRpAnagrafica;

	public DocumentiAnag() {
//		this.tipoDocumento = new TipoDocumento();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
//	public TipoDocumento getTipoDocumento() {
//		return this.tipoDocumento;
//	}
//
//	public void setTipoDocumento(TipoDocumento tipoDocumento) {
//		this.tipoDocumento = tipoDocumento;
//	}

	
	public String getNote() {
		return this.note;
	}

	public Long getIdTipologiaDocumento() {
		return idTipologiaDocumento;
	}

	public void setIdTipologiaDocumento(Long idTipologiaDocumento) {
		this.idTipologiaDocumento = idTipologiaDocumento;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Anagrafica getArRpAnagrafica() {
		return this.arRpAnagrafica;
	}

	public void setArRpAnagrafica(Anagrafica arRpAnagrafica) {
		this.arRpAnagrafica = arRpAnagrafica;
	}

	public static DocumentiAnag createDocument(Date dataScadenza, String idTipologia, String numero) {
		 DocumentiAnag nuovoDocumento = new DocumentiAnag();
		 nuovoDocumento.setDataScadenza(dataScadenza);
		 if(idTipologia.isEmpty())
			 nuovoDocumento.setIdTipologiaDocumento(null);
		 else
			 nuovoDocumento.setIdTipologiaDocumento(Long.valueOf(idTipologia));
		 nuovoDocumento.setNumeroDocumento(numero);
		 return nuovoDocumento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataScadenza, idTipologiaDocumento, numeroDocumento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentiAnag other = (DocumentiAnag) obj;
		return Objects.equals(dataScadenza, other.dataScadenza)
				&& Objects.equals(idTipologiaDocumento, other.idTipologiaDocumento)
				&& Objects.equals(numeroDocumento, other.numeroDocumento);
	}
	
	/**
	 * Restituisce i messaggi di errore di validazione relativi allo stato dell'oggetto.
	 */
	public List<String> getValidationMessages(){
		List<String> messages = new ArrayList<>();
		//TODO: rifare con validatori su JPA/Hibernate definiti sui singoli campi
		if(dataScadenza==null) {
			messages.add(VALIDATION_MSG_DATA_SCADENZA_NULL);
		}
		
		if(numeroDocumento==null || numeroDocumento.trim().isEmpty()) {
			messages.add(VALIDATION_MSG_NUMERO_DOCUMENTO_NULL);
		}
		
		if(idTipologiaDocumento==null) {
			messages.add(VALIDATION_MSG_TIPOLOGIA_DOCUMENTO);
		}

		return messages;		
	}
}