package com.android.vending.billing;

import com.android.vending.billing.util.SkuDetails;
import com.simplyapped.libgdx.ext.billing.BillingProductDetails;

public class AndroidBillingProductDetails implements BillingProductDetails {

	private SkuDetails details;

	public AndroidBillingProductDetails(SkuDetails details) {
		this.details = details;
	}
	
	@Override
	public String getProductId() {
		return details.getSku();
	}

	@Override
	public String getType() {
		return details.getType();
	}

	@Override
	public String getPrice() {
		return details.getPrice();
	}

	@Override
	public String getTitle() {
		return details.getTitle();
	}

	@Override
	public String getDescription() {
		return details.getDescription();
	}

}
