package com.cnksi.util.wordUtil.model;

/**
 * 可以带回车的String
 *
 */
public class CRString {
	private String string;
	
	public CRString(String string){
		this.string = string;
	}
	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@Override
	public String toString(){
		return string;
	}
	
}
