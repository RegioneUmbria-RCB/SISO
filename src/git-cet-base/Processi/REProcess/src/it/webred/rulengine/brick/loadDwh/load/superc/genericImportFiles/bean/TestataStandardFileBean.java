package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean;

import java.sql.Timestamp;

public class TestataStandardFileBean implements Testata {

	private Timestamp dataExport;
	private String versioneTracciato;
	private String provenienza;
	public TestataStandardFileBean(Timestamp dataExport2,
			String versioneTracciato2, String provenienzaDato) {
		this.dataExport = dataExport2;
		this.versioneTracciato = versioneTracciato2;
		this.provenienza = provenienzaDato;
	
	}
	public Timestamp getDataExport() {
		return dataExport;
	}
	public void setDataExport(Timestamp dataExport) {
		this.dataExport = dataExport;
	}
	public String getVersioneTracciato() {
		return versioneTracciato;
	}
	public void setVersioneTracciato(String versioneTracciato) {
		this.versioneTracciato = versioneTracciato;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	
}
