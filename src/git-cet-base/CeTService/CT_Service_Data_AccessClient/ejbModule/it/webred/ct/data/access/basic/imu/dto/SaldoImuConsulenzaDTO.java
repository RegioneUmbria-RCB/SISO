package it.webred.ct.data.access.basic.imu.dto;

import it.webred.ct.data.access.basic.anagrafe.dto.DatiAnagrafeDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaldoImuConsulenzaDTO extends CeTBaseObject{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date dtConsulenza;
	private DatiAnagrafeDTO dichiarante;
	private List<XmlImmobileDTO> lstImmobili;
	private List<XmlF24DTO> lstF24;
	
	public DatiAnagrafeDTO getDichiarante() {
		return dichiarante;
	}
	public void setDichiarante(DatiAnagrafeDTO dichiarante) {
		this.dichiarante = dichiarante;
	}
	public List<XmlImmobileDTO> getLstImmobili() {
		return lstImmobili;
	}
	public void setLstImmobili(List<XmlImmobileDTO> lstImmobili) {
		this.lstImmobili = lstImmobili;
	}
	public List<XmlF24DTO> getLstF24() {
		return lstF24;
	}
	public void setLstF24(List<XmlF24DTO> lstF24) {
		this.lstF24 = lstF24;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getDtConsulenza() {
		return dtConsulenza;
	}
	public void setDtConsulenza(Date dtConsulenza) {
		this.dtConsulenza = dtConsulenza;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
