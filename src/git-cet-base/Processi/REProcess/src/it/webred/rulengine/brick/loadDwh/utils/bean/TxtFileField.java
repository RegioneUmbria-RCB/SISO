package it.webred.rulengine.brick.loadDwh.utils.bean;

import java.io.Serializable;

public class TxtFileField implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idx;
	private String name;	
	private int length;
	private char paddingChar;
	private boolean paddingLeft;
	
	public TxtFileField() {
		super();
	}
	
	public TxtFileField(int idx, String name, int length, char paddingChar, boolean paddingLeft) {
		super();
		this.idx = idx;
		this.name = name;
		this.length = length;
		this.paddingChar = paddingChar;
		this.paddingLeft = paddingLeft;
	}
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public char getPaddingChar() {
		return paddingChar;
	}

	public void setPaddingChar(char paddingChar) {
		this.paddingChar = paddingChar;
	}

	public boolean isPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(boolean paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

}
