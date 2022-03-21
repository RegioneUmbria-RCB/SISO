package it.webred.ct.rulengine.quartz.dto;

import java.io.Serializable;

import org.quartz.DateBuilder.IntervalUnit;

public class TriggerIntervalInfo implements Serializable {
		
	private org.quartz.DateBuilder.IntervalUnit type;
	private Integer value;
	
	private boolean isImmediate;
	private String immediateCronExpression;
	
	
	public TriggerIntervalInfo(IntervalUnit type, Integer value) {
		super();
		this.type = type;
		this.value = value;
	}

	public TriggerIntervalInfo(boolean isImmediate,String immediateCronExpression) {
		super();
		this.isImmediate = isImmediate;
		this.immediateCronExpression = immediateCronExpression;
	}

	public org.quartz.DateBuilder.IntervalUnit getType() {
		return type;
	}

	public void setType(org.quartz.DateBuilder.IntervalUnit type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public boolean isImmediate() {
		return isImmediate;
	}

	public void setImmediate(boolean isImmediate) {
		this.isImmediate = isImmediate;
	}

	public String getImmediateCronExpression() {
		return immediateCronExpression;
	}

	public void setImmediateCronExpression(String immediateCronExpression) {
		this.immediateCronExpression = immediateCronExpression;
	}
	
	
}
