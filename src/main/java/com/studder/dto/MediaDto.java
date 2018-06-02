package com.studder.dto;

public class MediaDto {

	private String filename;
	private String contentType;
	private long size;
	private byte[] bytes;
	
	public MediaDto() {
	}
	
	public MediaDto(String filename, String contentType, long size, byte[] bytes) {
		this.filename = filename;
		this.contentType = contentType;
		this.size = size;
		this.bytes = bytes;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
