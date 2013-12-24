package com.android.vending.billing.test;

import com.android.vending.billing.util.Purchase;

public class FakePurchase extends Purchase {

	private String itemType;
	private String orderId;
	private String packageName;

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setPurchaseTime(long purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public void setPurchaseState(int purchaseState) {
		this.purchaseState = purchaseState;
	}

	public void setDeveloperPayload(String developerPayload) {
		this.developerPayload = developerPayload;
	}

	public void setOriginalJson(String originalJson) {
		this.originalJson = originalJson;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	private String sku;
	private long purchaseTime;
	private int purchaseState;
	private String developerPayload;
	
	private String originalJson;
	private String signature;

	public FakePurchase() {
	}

	public String getItemType() {
		return itemType;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getSku() {
		return sku;
	}

	public long getPurchaseTime() {
		return purchaseTime;
	}

	public int getPurchaseState() {
		return purchaseState;
	}

	public String getDeveloperPayload() {
		return developerPayload;
	}

	public String getToken() {
		return getItemType() + ":" + getPackageName() + ":" + getSku();
	}

	public String getOriginalJson() {
		return originalJson;
	}

	public String getSignature() {
		return signature;
	}
}
