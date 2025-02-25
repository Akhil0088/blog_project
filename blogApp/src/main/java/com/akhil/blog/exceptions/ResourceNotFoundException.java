package com.akhil.blog.exceptions;

public class ResourceNotFoundException  extends RuntimeException{
	
	String resourceName;
	String fieldname;
	long fieldValue;
	
	
	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getFieldname() {
		return fieldname;
	}


	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}


	public long getFieldValue() {
		return fieldValue;
	}


	public void setFieldValue(long fieldValue) {
		this.fieldValue = fieldValue;
	}


	public ResourceNotFoundException(String resourceName, String fieldname, long fieldValue) {
		super(String.format("%s not found with %s: %s", resourceName, fieldname,fieldValue));
		this.resourceName = resourceName;
		this.fieldname = fieldname;
		this.fieldValue = fieldValue;
	}

	
	
	
	

}
