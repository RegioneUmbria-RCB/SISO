package it.umbriadigitale.interscambio.enums;

public enum EGuarantor {
	ATS_RESIDENZA_ASSISTITO(EGuarantorOrganization.ORG, "2.16.840.1.113883.2.9.2.30.4.11"),
	ASST_RESIDENZA_ASSISTITO(EGuarantorOrganization.ORG, "2.16.840.1.113883.2.9.2.30.4.11"),
	AMBITO_RESIDENZA_ASSISTITO(EGuarantorOrganization.DEFAULT, "2.16.840.1.113883.2.9.2.30.3.2.3.1.1.8");
	
	
	private EGuarantorOrganization guarantorOrganizationType;
	private String id_Root;
	
	
	private EGuarantor(EGuarantorOrganization guarantorOrganizationType, String id_Root) {
		this.guarantorOrganizationType = guarantorOrganizationType;
		this.id_Root = id_Root;
	}
	
	
	// indica che tipo di elemento guarantorOrganization viene creato
	public enum EGuarantorOrganization {
		DEFAULT,
		ORG;
	}
	
	
	public EGuarantorOrganization getGuarantorOrganizationType() {
		return guarantorOrganizationType;
	}

	public String getId_Root() {
		return id_Root;
	}
}
