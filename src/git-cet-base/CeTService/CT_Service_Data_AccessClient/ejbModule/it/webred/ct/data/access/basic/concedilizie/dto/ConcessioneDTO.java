package it.webred.ct.data.access.basic.concedilizie.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.webred.ct.data.model.concedilizie.SitCConcessioni;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class ConcessioneDTO extends CeTBaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String concNumero; //CONCESSIONE_NUMERO oopure PROGRESSIVO_ANNO + PROGRESSIVO_NUMERO
	private Date dataProt;
	private String dataProtStr;
	private String tipoIntervento;
	private String oggetto;
	private Date dataRilascio;
	private String dataRilascioStr;
	private Date dataInizioLavori;
	private String dataInizioLavoriStr;
	private Date dataFineLavori;
	private String dataFineLavoriStr;
	private List<SoggettoConcessioneDTO> listaSoggetti;
	private List<IndirizzoConcessioneDTO> listaIndirizzi;
	private String listaSoggettiHtml;
	private String stringaImmobili;
	
	private String provenienza;
	
	private String progAnno;
	private String progNumero;

	
	public String getProgNumero() {
		return progNumero;
	}
	public void setProgNumero(String progNumero) {
		this.progNumero = progNumero;
	}
	public String getConcNumero() {
		return concNumero;
	}
	public void setConcNumero(String concNumero) {
		this.concNumero = concNumero;
	}
	
	public Date getDataProt() {
		return dataProt;
	}
	public void setDataProt(Date dataProt) {
		this.dataProt = dataProt;
		if (dataProt!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataProtStr= sdf.format(dataProt);	
		}else
			dataProtStr="";
		
	}
	public String getDataProtStr() {
		return dataProtStr;
	}
	
	public String getTipoIntervento() {
		return tipoIntervento;
	}
	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		oggetto = oggetto;
	}
	public Date getDataRilascio() {
		return dataRilascio;
	}
	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
		if (dataRilascio!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataRilascioStr= sdf.format(dataRilascio);	
		}else
			dataRilascioStr="";
	}
	public String getDataRilascioStr() {
		return dataRilascioStr;
	}
	public void setDataRilascioStr(String dataRilascioStr) {
		this.dataRilascioStr = dataRilascioStr;
	}
	public Date getDataInizioLavori() {
		return dataInizioLavori;
	}
	public void setDataInizioLavori(Date dataInizioLavori) {
		this.dataInizioLavori = dataInizioLavori;
		if (dataInizioLavori!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataInizioLavoriStr= sdf.format(dataInizioLavori);	
		}else
			dataInizioLavoriStr="";
	}
	public String getDataInizioLavoriStr() {
		return dataInizioLavoriStr;
	}
	
	public Date getDataFineLavori() {
		return dataFineLavori;
	}
	public void setDataFineLavori(Date dataFineLavori) {
		this.dataFineLavori = dataFineLavori;
		if (dataFineLavori!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataFineLavoriStr= sdf.format(dataFineLavori);	
		}else
			dataFineLavoriStr="";
	}
	public String getDataFineLavoriStr() {
		return dataFineLavoriStr;
	}
	
	public List<SoggettoConcessioneDTO> getListaSoggetti() {
		return listaSoggetti;
	}
	public void setListaSoggetti(List<SoggettoConcessioneDTO> listaSoggetti) {
		this.listaSoggetti = listaSoggetti;
		String listaStr="";
		if (listaSoggetti !=null ){
			for (SoggettoConcessioneDTO sogg: listaSoggetti) {
				if (!listaStr.equals(""))
					listaStr += "; ";
				listaStr += sogg.getTitolo() + ": " + sogg.getDatiAnag();	
			}
		}
		listaSoggettiHtml = listaStr;
	}
	public String getListaSoggettiHtml() {
		return listaSoggettiHtml;
	}
	
	public String getStringaImmobili() {
		return stringaImmobili;
	}
	
	
	public void setStringaImmobili(String stringaImmobili) {
		this.stringaImmobili = stringaImmobili;
	}
	public void valorizzaDatiConc(SitCConcessioni conc) {
		String numConc="";
		if (conc.getProgressivoAnno()!=null && !conc.getProgressivoAnno().equals("") ){
			setProgAnno(conc.getProgressivoAnno());
			numConc = conc.getProgressivoAnno();
		}
		else
			setProgAnno("-");
		
		if (conc.getProgressivoNumero()!=null && !conc.getProgressivoNumero().equals("") ){
			setProgNumero(conc.getProgressivoNumero());
			numConc += conc.getProgressivoNumero();
		}else
			setProgNumero("-");
		
		if (conc.getConcessioneNumero()!=null && !conc.getConcessioneNumero().equals("") )
			numConc = conc.getConcessioneNumero();
		concNumero =numConc;
		setDataProt( conc.getProtocolloData());
		tipoIntervento =conc.getTipoIntervento()!= null ? conc.getTipoIntervento() : "";
		oggetto = conc.getOggetto()!=null ? conc.getOggetto() : "";
		setDataRilascio ( conc.getDataRilascio());
		setDataInizioLavori(getDataInizioLavori());
		setDataFineLavori(getDataFineLavori());
		this.setId(conc.getId());
		
		provenienza = conc.getProvenienza()!= null ? conc.getProvenienza() : "";
		
	}
	
	public void valorizzaDatiConc(SitCConcessioni conc, List<SoggettoConcessioneDTO> listaSogg, String strImm, List<IndirizzoConcessioneDTO> listaIndirizzi ) {
		valorizzaDatiConc(conc);
		setListaSoggetti(listaSogg);
		setStringaImmobili(strImm);
		setListaIndirizzi(listaIndirizzi);
	}
	public String getProgAnno() {
		return progAnno;
	}
	public void setProgAnno(String progAnno) {
		this.progAnno = progAnno;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public List<IndirizzoConcessioneDTO> getListaIndirizzi() {
		return listaIndirizzi;
	}
	public void setListaIndirizzi(List<IndirizzoConcessioneDTO> listaIndirizzi) {
		this.listaIndirizzi = listaIndirizzi;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
