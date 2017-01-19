package it.webred.siso.ws.client.anag.model;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Get")
public class Get {
	private boolean useBeanExtraData=true;
	private String objectName="sianc.paziente.PazienteBean";
	private ListKey listKey;
	private SiancPazientePazienteBean pazienteBean;
	

	@XmlAttribute(name = "useBeanExtraData")
	public boolean isUseBeanExtraData() {
		return useBeanExtraData;
	}

	public void setUseBeanExtraData(boolean useBeanExtraData) {
		this.useBeanExtraData = useBeanExtraData;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public ListKey getListKey() {
		return listKey;
	}

	public void setListKey(ListKey listKey) {
		this.listKey = listKey;
	}

	@XmlElement(name = "sianc.paziente.PazienteBean")
	public SiancPazientePazienteBean getPazienteBean() {
		return pazienteBean;
	}

	public void setPazienteBean(SiancPazientePazienteBean pazienteBean) {
		this.pazienteBean = pazienteBean;
	}
	
	@XmlRootElement(name = "listKey")
	@XmlType(propOrder = { "idPaziente", "codiceFiscale", "cognomePaziente", "nomePaziente", "dataNascita", "sesso", "codiceComuneNascita" })
	public static class ListKey {
		private String idPaziente;
		private String codiceFiscale;
		private String cognomePaziente;
		private String nomePaziente;
		private Sesso sesso;
		private String codiceComuneNascita;
		private String dataNascita;
		
		

		

		public String getCodiceFiscale() {
			return codiceFiscale;
		}

		public void setCodiceFiscale(String codiceFiscale) {
			this.codiceFiscale = codiceFiscale;
		}

		public String getCognomePaziente() {
			return cognomePaziente;
		}

		public void setCognomePaziente(String cognomePaziente) {
			this.cognomePaziente = cognomePaziente;
		}

		public String getNomePaziente() {
			return nomePaziente;
		}

		public void setNomePaziente(String nomePaziente) {
			this.nomePaziente = nomePaziente;
		}

		public Sesso getSesso() {
			return sesso;
		}

		public void setSesso(Sesso sesso) {
			this.sesso = sesso;
		}

		public String getCodiceComuneNascita() {
			return codiceComuneNascita;
		}

		public void setCodiceComuneNascita(String codiceComuneNascita) {
			this.codiceComuneNascita = codiceComuneNascita;
		}

		public String getDataNascita() {
			return dataNascita;
		}

		public void setDataNascita(String dataNascita) {
			this.dataNascita = dataNascita;
		}

		public String getIdPaziente() {
			return idPaziente;
		}

		public void setIdPaziente(String idPaziente) {
			this.idPaziente = idPaziente;
		}
		

	}

	public enum Sesso {
		M  ,
	    F   
	}
	
	
	
}