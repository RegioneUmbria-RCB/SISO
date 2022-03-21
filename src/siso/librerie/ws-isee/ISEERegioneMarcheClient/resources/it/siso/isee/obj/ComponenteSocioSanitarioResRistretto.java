package it.siso.isee.obj;

import java.io.Serializable;

public class ComponenteSocioSanitarioResRistretto extends ComponenteSocioSanitario implements Serializable{

	private ISEEBase iseeSocioSanResRistretto = null;
	private ISEEAgg  iseeRistrettoAgg = null;
	private ISEEAgg  iseeSocioSanResRistrettoAgg = null;
	
	
	public ISEEBase getIseeSocioSanResRistretto() {
		return iseeSocioSanResRistretto;
	}
	public void setIseeSocioSanResRistretto(ISEEBase iseeSocioSanResRistretto) {
		this.iseeSocioSanResRistretto = iseeSocioSanResRistretto;
	}
	public ISEEAgg getIseeRistrettoAgg() {
		return iseeRistrettoAgg;
	}
	public void setIseeRistrettoAgg(ISEEAgg iseeRistrettoAgg) {
		this.iseeRistrettoAgg = iseeRistrettoAgg;
	}
	public ISEEAgg getIseeSocioSanResRistrettoAgg() {
		return iseeSocioSanResRistrettoAgg;
	}
	public void setIseeSocioSanResRistrettoAgg(ISEEAgg iseeSocioSanResRistrettoAgg) {
		this.iseeSocioSanResRistrettoAgg = iseeSocioSanResRistrettoAgg;
	}
	
	
	 
}
