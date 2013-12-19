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
		return getPurchase().getItemType();
	}

	@Override
	public String getOrderId() {
		return getPurchase().getOrderId();
	}

	@Override
	public String getPackageName() {
		return getPurchase().getPackageName();
	}

	@Override
	public String getProductId() {
		return getPurchase().getSku();
	}

	@Override
	public long getPurchaseTime() {
		return getPurchase().getPurchaseTime();
	}

	@Override
	public BillingPurchase.PURCHASE_STATE getPurchaseState() {
		switch (getPurchase().getPurchaseState())
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
		return getPurchase().getDeveloperPayload();
	}

	@Override
	public String getToken() {
		return getPurchase().getToken();
	}

	@Override
	public String getOriginalJson() {
		return getPurchase().getOriginalJson();
	}

	@Override
	public String getSignature() {
		return getPurchase().getSignature();
	}

	public Purchase getPurchase() {
		return purchase;
	}

}
