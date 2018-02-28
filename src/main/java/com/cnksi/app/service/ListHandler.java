package com.cnksi.app.service;

import java.util.List;
import java.util.Map;
import com.cnksi.app.service.pms.IListHandler;

public abstract class ListHandler implements IListHandler {
	private Object[] args;
	
	public ListHandler(Object... args){
		this.args = args;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getConstructPara(int i){
		return (T) args[i];
	}

	@Override
	public abstract void handel(List<Map<String, Object>> list);

}
