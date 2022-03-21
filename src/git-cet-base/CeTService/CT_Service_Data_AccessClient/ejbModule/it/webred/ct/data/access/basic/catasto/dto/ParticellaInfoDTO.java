package it.webred.ct.data.access.basic.catasto.dto;

public class ParticellaInfoDTO extends ParticellaKeyDTO{
	
	private static final long serialVersionUID = 1L;
	private String[] subalterni;
	private IndirizzoDTO[] indirizzi;
	private InfoPerCategoriaDTO[] infoPerCategoria;
	
	public void setSubalterni(String[] subalterni) {
		this.subalterni = subalterni;
	}
	public String[] getSubalterni() {
		return subalterni;
	}
	public void setIndirizzi(IndirizzoDTO[] indirizzi) {
		this.indirizzi = indirizzi;
	}
	public IndirizzoDTO[] getIndirizzi() {
		return indirizzi;
	}
	public void setInfoPerCategoria(InfoPerCategoriaDTO[] infoPerCategoria) {
		this.infoPerCategoria = infoPerCategoria;
	}
	public InfoPerCategoriaDTO[] getInfoPerCategoria() {
		return infoPerCategoria;
	}
	
}
