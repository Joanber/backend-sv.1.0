package com.backend.sv1.exceptions;

import java.io.Serializable;

public class Response  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5129548252518873915L;
	private String  message;

	public Response(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
