package it.webred.ct.aggregator.ejb.imu.dto;

public class ImuConduzCatastoIntervalloDTO extends ImuBaseDTO{

	private static final long serialVersionUID = 1L;
	
	private String dtIniRif="";
	private String dtFinRif="";
	private String sovraconduzione="0";    //0 = NO, 1 = SI
	private String quota="";
	
	private ImuConduzCatastoDTO[] conduzioni;

	
	
    public String getDtIniRif() {
		return dtIniRif;
	}



	public void setDtIniRif(String dtIniRif) {
		this.dtIniRif = dtIniRif;
	}



	public String getDtFinRif() {
		return dtFinRif;
	}



	public void setDtFinRif(String dtFinRif) {
		this.dtFinRif = dtFinRif;
	}



	public ImuConduzCatastoDTO[] getConduzioni() {
		return conduzioni;
	}



	public void setConduzioni(ImuConduzCatastoDTO[] conduzioni) {
		this.conduzioni = conduzioni;
	}



	public String getSovraconduzione() {
		return sovraconduzione;
	}



	public void setSovraconduzione(String sovraconduzione) {
		this.sovraconduzione = sovraconduzione;
	}



	public String getQuota() {
		return quota;
	}



	public void setQuota(String quota) {
		this.quota = quota;
	}



	public String stampaRecord(){
		String r =  sovraconduzione+"|"+quota+"|"+dtIniRif+"|"+dtFinRif +"|"+super.stampaRecord();
		return r;
	}

}
