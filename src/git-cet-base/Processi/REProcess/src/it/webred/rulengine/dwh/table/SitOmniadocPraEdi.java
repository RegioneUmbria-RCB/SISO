package it.webred.rulengine.dwh.table;


public class SitOmniadocPraEdi extends TabellaDwhMultiProv
{
	/*
	private String identificativoImpianto;
	private BigDecimal generatoriNumero;
	private DataDwh dtFileCsv;
	*/
	private String tipo_prot_capofila;
	private String nr_prot_capofila;
	private String anno_prot_capofila;
	private String registro_prot_di_settore;
	private String sub_nr_prot;
	private String anno_prat_onlyone;
	private String nr_prat_onlyone;
	private String cod_via;
	private String num_civico;
	private String appendice_civico;
	private String tipo_prot_documento;
	private String num_prot_documento;
	private String anno_prot_documento;
	private String registro_prot_set_doc;
	private String sub_num_prot_documento;
	private String richiedente_istanza;
	private String faldone;
	private String pdf_file;
	private String f;
	private String m;
	private String s;
	private String oggetto;
	private String segnalazioni;
	private String descrizione_via;
	private String pagine;
	  
	
	@Override
	public String getValueForCtrHash(){
		return tipo_prot_capofila +
		nr_prot_capofila +
		anno_prot_capofila +
		registro_prot_di_settore +
		sub_nr_prot +
		anno_prat_onlyone +
		nr_prat_onlyone +
		cod_via +
		num_civico +
		appendice_civico +
		tipo_prot_documento +
		num_prot_documento +
		anno_prot_documento +
		registro_prot_set_doc +
		sub_num_prot_documento +
		richiedente_istanza +
		faldone +
		pdf_file +
		f +
		m +
		s +
		oggetto +
		segnalazioni +
		descrizione_via +
		pagine;
	}


	

	public String getTipo_prot_capofila() {
		return tipo_prot_capofila;
	}


	public void setTipo_prot_capofila(String tipo_prot_capofila) {
		this.tipo_prot_capofila = tipo_prot_capofila;
	}


	public String getNr_prot_capofila() {
		return nr_prot_capofila;
	}


	public void setNr_prot_capofila(String nr_prot_capofila) {
		this.nr_prot_capofila = nr_prot_capofila;
	}


	public String getAnno_prot_capofila() {
		return anno_prot_capofila;
	}


	public void setAnno_prot_capofila(String anno_prot_capofila) {
		this.anno_prot_capofila = anno_prot_capofila;
	}


	public String getRegistro_prot_di_settore() {
		return registro_prot_di_settore;
	}


	public void setRegistro_prot_di_settore(String registro_prot_di_settore) {
		this.registro_prot_di_settore = registro_prot_di_settore;
	}


	public String getSub_nr_prot() {
		return sub_nr_prot;
	}


	public void setSub_nr_prot(String sub_nr_prot) {
		this.sub_nr_prot = sub_nr_prot;
	}


	public String getAnno_prat_onlyone() {
		return anno_prat_onlyone;
	}


	public void setAnno_prat_onlyone(String anno_prat_onlyone) {
		this.anno_prat_onlyone = anno_prat_onlyone;
	}


	public String getNr_prat_onlyone() {
		return nr_prat_onlyone;
	}


	public void setNr_prat_onlyone(String nr_prat_onlyone) {
		this.nr_prat_onlyone = nr_prat_onlyone;
	}


	public String getCod_via() {
		return cod_via;
	}


	public void setCod_via(String cod_via) {
		this.cod_via = cod_via;
	}


	public String getNum_civico() {
		return num_civico;
	}


	public void setNum_civico(String num_civico) {
		this.num_civico = num_civico;
	}


	public String getAppendice_civico() {
		return appendice_civico;
	}


	public void setAppendice_civico(String appendice_civico) {
		this.appendice_civico = appendice_civico;
	}


	public String getTipo_prot_documento() {
		return tipo_prot_documento;
	}


	public void setTipo_prot_documento(String tipo_prot_documento) {
		this.tipo_prot_documento = tipo_prot_documento;
	}


	public String getNum_prot_documento() {
		return num_prot_documento;
	}


	public void setNum_prot_documento(String num_prot_documento) {
		this.num_prot_documento = num_prot_documento;
	}


	public String getAnno_prot_documento() {
		return anno_prot_documento;
	}


	public void setAnno_prot_documento(String anno_prot_documento) {
		this.anno_prot_documento = anno_prot_documento;
	}


	public String getRegistro_prot_set_doc() {
		return registro_prot_set_doc;
	}


	public void setRegistro_prot_set_doc(String registro_prot_set_doc) {
		this.registro_prot_set_doc = registro_prot_set_doc;
	}


	public String getSub_num_prot_documento() {
		return sub_num_prot_documento;
	}


	public void setSub_num_prot_documento(String sub_num_prot_documento) {
		this.sub_num_prot_documento = sub_num_prot_documento;
	}


	public String getRichiedente_istanza() {
		return richiedente_istanza;
	}


	public void setRichiedente_istanza(String richiedente_istanza) {
		this.richiedente_istanza = richiedente_istanza;
	}


	public String getFaldone() {
		return faldone;
	}


	public void setFaldone(String faldone) {
		this.faldone = faldone;
	}


	public String getPdf_file() {
		return pdf_file;
	}


	public void setPdf_file(String pdf_file) {
		this.pdf_file = pdf_file;
	}


	public String getF() {
		return f;
	}


	public void setF(String f) {
		this.f = f;
	}


	public String getM() {
		return m;
	}


	public void setM(String m) {
		this.m = m;
	}


	public String getS() {
		return s;
	}


	public void setS(String s) {
		this.s = s;
	}


	public String getOggetto() {
		return oggetto;
	}


	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}


	public String getSegnalazioni() {
		return segnalazioni;
	}


	public void setSegnalazioni(String segnalazioni) {
		this.segnalazioni = segnalazioni;
	}


	public String getDescrizione_via() {
		return descrizione_via;
	}


	public void setDescrizione_via(String descrizione_via) {
		this.descrizione_via = descrizione_via;
	}


	public String getPagine() {
		return pagine;
	}


	public void setPagine(String pagine) {
		this.pagine = pagine;
	}

	
	
}
