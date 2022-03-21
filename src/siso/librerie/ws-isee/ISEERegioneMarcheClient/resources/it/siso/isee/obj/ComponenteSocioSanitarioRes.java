package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponenteSocioSanitarioRes extends ComponenteSocioSanitario implements Serializable {

  
	 
 
	private ISEEBase iseeSocioSanRes = null;
	private ISEEAgg  iseeOrdAgg = null;
	private ISEEAgg  iseeSocioSanResAgg = null;
	

	 

	 

	public ISEEBase getIseeSocioSanRes() {
		return iseeSocioSanRes;
	}

	public void setIseeSocioSanRes(ISEEBase iseeSocioSanRes) {
		this.iseeSocioSanRes = iseeSocioSanRes;
	}

	public ISEEAgg getIseeOrdAgg() {
		return iseeOrdAgg;
	}

	public void setIseeOrdAgg(ISEEAgg iseeOrdAgg) {
		this.iseeOrdAgg = iseeOrdAgg;
	}

	public ISEEAgg getIseeSocioSanResAgg() {
		return iseeSocioSanResAgg;
	}

	public void setIseeSocioSanResAgg(ISEEAgg iseeSocioSanResAgg) {
		this.iseeSocioSanResAgg = iseeSocioSanResAgg;
	}

	

}