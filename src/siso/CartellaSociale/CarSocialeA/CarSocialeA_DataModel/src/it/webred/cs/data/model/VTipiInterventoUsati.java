package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = " V_TIPI_INTERVENTO_USATI")
public class VTipiInterventoUsati implements Serializable, Comparable<VTipiInterventoUsati> {

	private static final long serialVersionUID = -5545505365471923435L;

	@Id
	private  Long id;
	
	@Column(name = "SCTI_TIPO_INTERVENTO_ID")
	private long idTipoIntervento;
	
	@Column(name = "CODICE_MEMO")
	private String codiceTipoIntervento;
	
	@Column(name = "DESCRIZIONE")
	private String descrizioneTipoIntervento;
	
	@Column(name = "TIPO_INTERVENTO_CUSTOM_ID")
	private Long idTipoInterventoCustom;
	
	@Column(name = "DESCRIZIONE_CUSTOM")
	private String descrizioneTipoInterventoCustom;
	
	@Column(name = "CATEGORIA_SOCIALE_ID")
	private Long idCategoriaSociale;
	
	@Column(name = "CATEGORIA_SOCIALE_DESCRIZIONE")
	private String descrizioneCategoriaSociale;

	@Column(name = "N_OCCORRENZE")
	private int occorrenze;

	public VTipiInterventoUsati() {
		super();
	}

	public int getOccorrenze() {
		return occorrenze;
	}

	public void setOccorrenze(int occorrenze) {
		this.occorrenze = occorrenze;
	}


	public String getValue() {
		String value = Long.toString(this.idTipoIntervento) + SEPARATOR + (StringUtils.isBlank(this.codiceTipoIntervento) ? "" : this.codiceTipoIntervento)
				+ SEPARATOR + (StringUtils.isBlank(this.descrizioneTipoIntervento) ? "" : this.descrizioneTipoIntervento) + SEPARATOR
				+ (this.idTipoInterventoCustom == null ? "" : Long.toString(this.idTipoInterventoCustom)) + SEPARATOR
				+ (StringUtils.isBlank(this.descrizioneTipoInterventoCustom) ? "" : this.descrizioneTipoInterventoCustom) + SEPARATOR
				+ Long.toString(this.idCategoriaSociale) + SEPARATOR
				+ (StringUtils.isBlank(this.descrizioneCategoriaSociale) ? "" : this.descrizioneCategoriaSociale);
		return value;
	}


	public String getLabel() {
		return this.descrizioneCategoriaSociale + " » " + this.descrizioneTipoIntervento
				+ (StringUtils.isBlank(this.descrizioneTipoInterventoCustom) ? "" : " » " + this.descrizioneTipoInterventoCustom);
	}

	public static final String SEPARATOR = "|";

	public static VTipiInterventoUsati decode(String valore) throws Exception {
		if (StringUtils.isBlank(valore))
			return null;

		VTipiInterventoUsati ir = new VTipiInterventoUsati();
        String[] t = valore.split("\\|");
        
        ir.setIdTipoIntervento(!StringUtils.isBlank(t[0]) ? Long.parseLong(t[0]) : null);
		ir.setCodiceTipoIntervento(t[1]);
		ir.setDescrizioneTipoIntervento(t[2]);
		ir.setIdTipoInterventoCustom(!StringUtils.isBlank(t[3]) ? Long.parseLong(t[3]) : null);
		ir.setDescrizioneTipoInterventoCustom(t[4]);
		ir.setIdCategoriaSociale(!StringUtils.isBlank(t[5]) ? Long.parseLong(t[5]) : null);
		ir.setDescrizioneCategoriaSociale(t[6]);
        
		return ir;
	}

	@Override
	public int compareTo(VTipiInterventoUsati o) {
		boolean val1 = this.getDescrizioneCategoriaSociale().equals(o.getDescrizioneCategoriaSociale());
		boolean val2 = this.getDescrizioneTipoIntervento().equals(o.getDescrizioneTipoIntervento());
		boolean val3 = false;
		if (this.getDescrizioneTipoInterventoCustom()==null && o.getDescrizioneTipoInterventoCustom()==null)
			val3 = true;
		else if (this.getDescrizioneTipoInterventoCustom()!=null && o.getDescrizioneTipoInterventoCustom()!=null) {
				val3 = this.getDescrizioneTipoInterventoCustom().equals(o.getDescrizioneTipoInterventoCustom());
			} else {
				val3 = false;
			}
		if (val1 && val2 && val3) 
			return 0;
		else
			return -1;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public long getIdTipoIntervento() {
		return idTipoIntervento;
	}



	public void setIdTipoIntervento(long idTipoIntervento) {
		this.idTipoIntervento = idTipoIntervento;
	}



	public String getCodiceTipoIntervento() {
		return codiceTipoIntervento;
	}



	public void setCodiceTipoIntervento(String codiceTipoIntervento) {
		this.codiceTipoIntervento = codiceTipoIntervento;
	}



	public String getDescrizioneTipoIntervento() {
		return descrizioneTipoIntervento;
	}



	public void setDescrizioneTipoIntervento(String descrizioneTipoIntervento) {
		this.descrizioneTipoIntervento = descrizioneTipoIntervento;
	}



	public Long getIdTipoInterventoCustom() {
		return idTipoInterventoCustom;
	}



	public void setIdTipoInterventoCustom(Long idTipoInterventoCustom) {
		this.idTipoInterventoCustom = idTipoInterventoCustom;
	}



	public String getDescrizioneTipoInterventoCustom() {
		return descrizioneTipoInterventoCustom;
	}



	public void setDescrizioneTipoInterventoCustom(
			String descrizioneTipoInterventoCustom) {
		this.descrizioneTipoInterventoCustom = descrizioneTipoInterventoCustom;
	}



	public Long getIdCategoriaSociale() {
		return idCategoriaSociale;
	}



	public void setIdCategoriaSociale(Long idCategoriaSociale) {
		this.idCategoriaSociale = idCategoriaSociale;
	}



	public String getDescrizioneCategoriaSociale() {
		return descrizioneCategoriaSociale;
	}



	public void setDescrizioneCategoriaSociale(String descrizioneCategoriaSociale) {
		this.descrizioneCategoriaSociale = descrizioneCategoriaSociale;
	}
	

}
