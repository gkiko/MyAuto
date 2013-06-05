package com.example.myauto.event;

import java.util.EventObject;

public class MyChangeEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	public Object source;
	
	public MyChangeEvent(Object source) {
		super(source);
		this.source = source;
	}

}
