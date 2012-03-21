package com.Team4.client;

@SuppressWarnings("serial")
public class EntryNotPresentException extends Exception {
	
	public EntryNotPresentException() {
		super();
	}
	
	public EntryNotPresentException(String message) {
		super(message);
	}

}
