package com.example.demo.entity;



public class ResponseModal<T> {
	private ResponseModal<T> data = null;
	private String responseCode = null;
	private String responseMessage = null;
	
	
	public ResponseModal<T> getData() {
		return data;
	}
	public void setData(ResponseModal<T> data) {
		this.data = data;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	
	
}
