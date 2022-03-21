package it.webred.ct.aggregator.ejb.imu.dto;


public class ImuDatiCatastaliDTO extends ImuBaseDTO{
	
	private static final long serialVersionUID = 1L;
	
	private String codFisc="";
	private String denom="";
	private String dtNas="";
	
	private String sede="";
	private String provCat="";
	private String tipImm="";
	private String tit="";
	private String regime="";
	private String ubi="";
	private String foglio="";
	private String num="";
	private String sub="";
	private String rendita="";
	private String partita="";
	private String classe="";
	private String percPoss="";
	private String dataInizio="";
    private String dataFine="";
	private String dataInizioTit="";
    private String dataFineTit="";
    
    //Informazioni solo catasto immobili
	private String cat="";
	private String consistenza="";

    //Informazioni solo catasto terreni
    private String redditoDominicale="";
    private String redditoAgrario="";
    private String area="";
    private String qualita="";
	
	private int ricercaCod=1;
	
	public int getRicercaCod() {
		return ricercaCod;
	}
	public void setRicercaCod(int ricercaCod) {
		this.ricercaCod = ricercaCod;
	}
    
	public String getDataInizioTit() {
		return dataInizioTit;
	}
	public String getDataFineTit() {
		return dataFineTit;
	}
	public void setDataInizioTit(String dataInizioTit) {
		this.dataInizioTit = dataInizioTit;
	}
	public void setDataFineTit(String dataFineTit) {
		this.dataFineTit = dataFineTit;
	}
	
	public String getDenom() {
		return denom;
	}
	public String getSede() {
		return sede;
	}
	public String getProvCat() {
		return provCat;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public String getDtNas() {
		return dtNas;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public void setDtNas(String dtNas) {
		this.dtNas = dtNas;
	}
	public String getTipImm() {
		return tipImm;
	}
	public String getTit() {
		return tit;
	}
	public String getUbi() {
		return ubi;
	}
	public String getFoglio() {
		return foglio;
	}
	public String getNum() {
		return num;
	}
	public String getSub() {
		return sub;
	}
	public String getCat() {
		return cat;
	}
	public String getClasse() {
		return classe;
	}

	public void setDenom(String denom) {
		this.denom = denom;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public void setProvCat(String provCat) {
		this.provCat = provCat;
	}
	public void setTipImm(String tipImm) {
		this.tipImm = tipImm;
	}
	public void setTit(String tit) {
		this.tit = tit;
	}
	public void setUbi(String ubi) {
		this.ubi = ubi;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getConsistenza() {
		return consistenza;
	}
	public String getRendita() {
		return rendita;
	}
	public String getPartita() {
		return partita;
	}
	public String getRedditoDominicale() {
		return redditoDominicale;
	}
	public String getRedditoAgrario() {
		return redditoAgrario;
	}
	public String getArea() {
		return area;
	}
	public String getQualita() {
		return qualita;
	}
	public void setRedditoDominicale(String redditoDominicale) {
		this.redditoDominicale = redditoDominicale;
	}
	public void setRedditoAgrario(String redditoAgrario) {
		this.redditoAgrario = redditoAgrario;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setQualita(String qualita) {
		this.qualita = qualita;
	}
	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}
	public void setRendita(String rendita) {
		this.rendita = rendita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public String getPercPoss() {
		return percPoss;
	}
	public void setPercPoss(String percPoss) {
		this.percPoss = percPoss;
	}

	public String stampaRecord(){
		String r ="";
		if(tipImm.equals("F"))
			r =codFisc+"|"+denom+"|"+ dtNas +"|"+ sede+"|"+provCat+"|"+tipImm+"|"+tit+"|"+regime+"|"+ubi+"|"+foglio+"|"+num+"|"+sub+"|"+
				cat+"|"+classe+"|"+consistenza+"|"+rendita+"|"+partita+"|"+percPoss+"|"+dataInizio+"|"+
				dataFine+"|"+dataInizioTit+"|"+dataFineTit+"|"+ricercaCod+"|"+super.stampaRecord();
		else
			r =codFisc+"|"+denom+"|"+ dtNas +"|"+ sede+"|"+provCat+"|"+tipImm+"|"+tit+"|"+regime+"|"+ubi+"|"+foglio+"|"+num+"|"+sub+"|"+
				qualita+"|"+classe+"|"+redditoDominicale+"|"+redditoAgrario+"|"+rendita+"|"+area+"|"+
				partita+"|"+percPoss+"|"+dataInizio+"|"+dataFine+"|"+dataInizioTit+"|"+dataFineTit+"|"+ricercaCod+"|"+super.stampaRecord();
		return r;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getRegime() {
		return regime;
	}
	public void setRegime(String regime) {
		this.regime = regime;
	}

}
