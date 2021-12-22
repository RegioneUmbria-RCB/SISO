package it.webred.cs.csa.ejb.dto.pai.pti;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.data.DataModelCostanti.StatiRichiesta.STATO_RICHIESTA;

public class RichiestaDisponibilitaPaiPtiDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3579237437791697320L;

	public RichiestaDisponibilitaPaiPtiDTO() {
		super();
	}

	/**
	 * @param paiPTI
	 * @param minore
	 */
	public RichiestaDisponibilitaPaiPtiDTO(CsPaiMastSoggDTO minore) {
		createDettaglioMinore(minore);
	}

	/**
	 * @param paiPTI
	 */
	public void createDettaglioPTI(CsPaiPTIDTO paiPTI) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			this.dettaglioPTI = ow.writeValueAsString(paiPTI);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param minore
	 */
	public void createDettaglioMinore(CsPaiMastSoggDTO minore) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			this.dettaglioMinore = ow.writeValueAsString(minore);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Long id;

	private Long idProgettoIndividuale;

	private Long idStruttura;

	private Date dataRichiesta;

	private Date dataUltimaModifica;

	private String statoRichiesta;

	private String motivoRifiuto;

	private byte[] documento;

	private Date dataInizioPermamenza;

	private Date dataFinePermanenza;

	private String dettaglioPTI;

	private String dettaglioMinore;

	private String nomeDocumento;

	private Date dataAccStruttura;

	private Boolean richAttiva;

	private String codRouting;

	private DettaglioMinore minore;

	private DettaglioPTI pti;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getIdProgettoIndividuale() {
//		return idProgettoIndividuale;
//	}
//
//	public void setIdProgettoIndividuale(Long idProgettoIndividuale) {
//		this.idProgettoIndividuale = idProgettoIndividuale;
//	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public Long getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(Long idStruttura) {
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

	public Long getIdProgettoIndividuale() {
		return idProgettoIndividuale;
	}

	public void setIdProgettoIndividuale(Long idProgettoIndividuale) {
		this.idProgettoIndividuale = idProgettoIndividuale;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
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
	
	
	public String getStatoRichiestaDescrizione() {
		
		STATO_RICHIESTA eStatoRichiesta = STATO_RICHIESTA.valueOf(statoRichiesta);
		return eStatoRichiesta.getDescrizione();

	}
}
