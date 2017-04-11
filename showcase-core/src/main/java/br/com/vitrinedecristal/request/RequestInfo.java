package br.com.vitrinedecristal.request;

import java.util.UUID;

public class RequestInfo {

	/**
	 * Tracking id da requisição
	 */
	private UUID trackingId;

	/**
	 * Ip do usuário
	 */
	private String userIP;

	/**
	 * Primeiros 6 dígitos do Tracking id
	 */
	private String tid;

	/**
	 * Primeiros 6 dígitos do {@link #trackingId}
	 */
	private String shortTrackingId;

	public RequestInfo() {
		trackingId = UUID.randomUUID();
		shortTrackingId = trackingId.toString().substring(0, 6);
		tid = "[TID: " + shortTrackingId + "] - ";
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public UUID getTrackingId() {
		return trackingId;
	}

	public String getTid() {
		return tid;
	}

	public String getShortTrackingId() {
		return shortTrackingId;
	}

}