package com.android.vending.billing;

import com.android.vending.billing.util.Purchase;
import com.simplyapped.libgdx.ext.billing.BillingPurchase;

public class AndroidBillingPurchase implements BillingPurchase {

	private Purchase purchase;

	public AndroidBillingPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	@Override
	public String getItemType() {
		return purchase.getItemType();
	}

	@Override
	public String getOrderId() {
		return purchase.getOrderId();
	}

	@Override
	public String getPackageName() {
		return purchase.getPackageName();
	}

	@Override
	public String getProductId() {
		return purchase.getSku();
	}

	@Override
	public long getPurchaseTime() {
		return purchase.getPurchaseTime();
	}

	@Override
	public BillingPurchase.PURCHASE_STATE getPurchaseState() {
		switch (purchase.getPurchaseState())
		{
		case 0:
			return BillingPurchase.PURCHASE_STATE.PURCHASED;
		case 1:
			return BillingPurchase.PURCHASE_STATE.CANCELLED;
		case 2:
			return BillingPurchase.PURCHASE_STATE.REFUNDED;
		}
		return null;
	}

	@Override
	public String getDeveloperPayload() {
		return purchase.getDeveloperPayload();
	}

	@Override
	public String getToken() {
		return purchase.getToken();
	}

	@Override
	public String getOriginalJson() {
		return purchase.getOriginalJson();
	}

	@Override
	public String getSignature() {
		return purchase.getSignature();
	}

}
