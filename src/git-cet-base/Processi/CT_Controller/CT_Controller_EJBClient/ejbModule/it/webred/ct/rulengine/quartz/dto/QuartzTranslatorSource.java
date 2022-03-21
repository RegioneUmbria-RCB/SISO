package it.webred.ct.rulengine.quartz.dto;

import java.io.Serializable;
import java.util.Date;

public class QuartzTranslatorSource implements Serializable {
	
	private Date targetTime;
	private Integer intervallo;
	
	
	public QuartzTranslatorSource(Date targetTime, Integer intervallo) {
		super();
		this.targetTime = targetTime;
		this.intervallo = intervallo;
	}
	
	
	public Date getTargetTime() {
		return targetTime;
	}
	public void setTargetTime(Date targetTime) {
		this.targetTime = targetTime;
	}
	public Integer getIntervallo() {
		return intervallo;
	}
	public void setIntervallo(Integer intervallo) {
		this.intervallo = intervallo;
	}
	
	
}
