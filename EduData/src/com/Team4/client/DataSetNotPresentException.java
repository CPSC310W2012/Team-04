package com.Team4.client;

import com.google.gwt.user.client.Window;

@SuppressWarnings("serial")
public class DataSetNotPresentException extends Exception {
	
	public DataSetNotPresentException() {
		super();
	}
	
	public DataSetNotPresentException(String message) {
		Window.alert(message);
	}

}
