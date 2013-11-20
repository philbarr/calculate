package com.android.vending.billing;

import android.content.Context;

import com.android.vending.billing.util.IabHelper;
import com.android.vending.billing.util.IabResult;
import com.simplyapped.libgdx.ext.billing.BillingService;
import com.simplyapped.libgdx.ext.billing.BillingServiceSetupFinishedListener;

public class AndroidBillingService implements BillingService {

	IabHelper mHelper;
	Context context;
	
	public AndroidBillingService(Context ctx)
	{
		this.context = ctx;	
	}
	
	@Override
	public void startSetup(String licenseKey, final BillingServiceSetupFinishedListener listener) {
		mHelper = new IabHelper(context, licenseKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
     	   public void onIabSetupFinished(IabResult result) {
     	      listener.onSetupFinished(new AndroidBillingResult(result));  
     	   }
     	});
	}

	@Override
	public void dispose() {
		if (mHelper != null) mHelper.dispose();
		   mHelper = null;
	}

}
