package eu.smartpeg.gedclient;

/**
 * Numero protocollo su GED
 * @author smartpeg
 *
 */
public class NumeroProtocolloGED {
	private String tipoProtocollo;
	private Integer annoProtocollo;
	private Integer numeroProgressivoProtocollo;
	
	
	/**
	 * Metodo Factory per inizializzare un oggetto di tipo NumeroProtocolloGED
	 * @param tipoProtocollo
	 * @param annoProtocollo
	 * @param numeroProgressivoProtocollo
	 * @return
	 */
	public static NumeroProtocolloGED CreaNumeroProtocollo(String tipoProtocollo, Integer annoProtocollo,
			Integer numeroProgressivoProtocollo) {
		
		NumeroProtocolloGED numeroProtocollo = new NumeroProtocolloGED();
		
		if(tipoProtocollo==null || tipoProtocollo.isEmpty()) {
			throw new IllegalArgumentException("tipoProtocollo");
		}		
		
		if(annoProtocollo==null) {
			throw new IllegalArgumentException("annoProtocollo");
		}
		
		if(numeroProgressivoProtocollo==null) {
			throw new IllegalArgumentException("numeroProgressivoProtocollo");
		}
		
		numeroProtocollo.setAnnoProtocollo(annoProtocollo);
		numeroProtocollo.setTipoProtocollo(tipoProtocollo);
		numeroProtocollo.setNumeroProgressivoProtocollo(numeroProgressivoProtocollo);
			
		return  numeroProtocollo;
	}
		

	private NumeroProtocolloGED() {
		super();		
	}
	
	public String getTipoProtocollo() {
		return tipoProtocollo;
	}
	private void setTipoProtocollo(String tipoProtocollo) {
		this.tipoProtocollo = tipoProtocollo;
	}
	public Integer getAnnoProtocollo() {
		return annoProtocollo;
	}
	private void setAnnoProtocollo(Integer annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public Integer getNumeroProgressivoProtocollo() {
		return numeroProgressivoProtocollo;
	}
	private  void setNumeroProgressivoProtocollo(Integer numeroProgressivoProtocollo) {
		this.numeroProgressivoProtocollo = numeroProgressivoProtocollo;
	}
	
	@Override
	public String toString() {
		return "NumeroProtocolloGED [tipoProtocollo=" + tipoProtocollo + ", annoProtocollo=" + annoProtocollo + ", numeroProgressivoProtocollo=" + numeroProgressivoProtocollo + "]";
	}
}
