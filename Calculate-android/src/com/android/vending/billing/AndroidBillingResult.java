package com.android.vending.billing;

import com.android.vending.billing.util.IabResult;
import com.simplyapped.libgdx.ext.billing.BillingResult;

public class AndroidBillingResult extends BillingResult {

	public AndroidBillingResult(IabResult result) {
		this.setMessage(result.getMessage());
		switch (result.getResponse())
		{
			
		}
	}

}
