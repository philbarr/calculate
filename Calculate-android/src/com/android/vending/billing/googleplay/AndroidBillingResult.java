package com.android.vending.billing.googleplay;

import com.android.vending.billing.googleplay.util.IabHelper;
import com.android.vending.billing.googleplay.util.IabResult;
import com.simplyapped.libgdx.ext.billing.googleplay.BillingResult;

public class AndroidBillingResult implements BillingResult {

	private IabResult result;

	public AndroidBillingResult(IabResult result) {
		this.result = result;
	}
	
    @Override
	public int getResponse() { return result.getResponse();}
    @Override
	public String getMessage() { return result.getMessage(); }
    @Override
	public boolean isSuccess() { return getResponse() == IabHelper.BILLING_RESPONSE_RESULT_OK; }
    @Override
	public boolean isFailure() { return !isSuccess(); }

}
