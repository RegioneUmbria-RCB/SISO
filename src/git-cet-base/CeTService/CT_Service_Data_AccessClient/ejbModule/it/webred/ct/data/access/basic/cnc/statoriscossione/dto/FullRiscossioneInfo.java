package it.webred.ct.data.access.basic.cnc.statoriscossione.dto;

import it.webred.ct.data.model.cnc.statoriscossione.SQuietanze;
import it.webred.ct.data.model.cnc.statoriscossione.SRiscossioni;
import it.webred.ct.data.model.cnc.statoriscossione.SRiversamenti;

import java.io.Serializable;
import java.util.List;

public class FullRiscossioneInfo implements Serializable {

	private List<SRiscossioni> riscossioniList;
	private List<SRiversamenti> riversamentiList;
	private List<SQuietanze> quitetanzeList;
	
	public List<SRiscossioni> getRiscossioniList() {
		return riscossioniList;
	}
	public void setRiscossioniList(List<SRiscossioni> riscossioniList) {
		this.riscossioniList = riscossioniList;
	}
	public List<SRiversamenti> getRiversamentiList() {
		return riversamentiList;
	}
	public void setRiversamentiList(List<SRiversamenti> riversamentiList) {
		this.riversamentiList = riversamentiList;
	}
	public List<SQuietanze> getQuitetanzeList() {
		return quitetanzeList;
	}
	public void setQuitetanzeList(List<SQuietanze> quitetanzeList) {
		this.quitetanzeList = quitetanzeList;
	}
	
	
}
