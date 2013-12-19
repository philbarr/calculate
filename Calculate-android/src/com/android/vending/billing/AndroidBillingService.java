package com.android.vending.billing;

import android.content.Context;

import com.android.vending.billing.util.IabHelper;
import com.android.vending.billing.util.IabHelper.QueryInventoryFinishedListener;
import com.android.vending.billing.util.IabResult;
import com.android.vending.billing.util.Inventory;
import com.simplyapped.libgdx.ext.billing.BillingService;
import com.simplyapped.libgdx.ext.billing.listeners.BillingQueryInventoryFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingServiceSetupFinishedListener;

public class AndroidBillingService implements BillingService {

	com.android.vending.billing.util.BillingService mHelper;
	Context context;
	private boolean isSetup;
	
	public AndroidBillingService(Context ctx)
	{
		this.context = ctx;	
	}
	
	@Override
	public void startSetup(String licenseKey, final BillingServiceSetupFinishedListener listener) {
		mHelper = new IabHelper(context, licenseKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
     	   public void onIabSetupFinished(IabResult result) {
     		  if (result.isSuccess())
     		 {
     			  isSetup = true;
     		  }
     		  else
     		  {
     			  isSetup = false;
     		  }
     	      listener.onSetupFinished(new AndroidBillingResult(result));  
     	   }
     	});
	}

	@Override
	public void dispose() {
		if (mHelper != null)
		{
			mHelper.dispose();
		}
		mHelper = null;
	}

	@Override
	public void queryInventoryAsync(final BillingQueryInventoryFinishedListener listener) {
		mHelper.queryInventoryAsync(new QueryInventoryFinishedListener() {
			
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				listener.onQueryInventoryFinished(new AndroidBillingResult(result), new AndroidBillingInventory(inv));
			}
		});
	}

	public boolean isSetup() {
		return isSetup;
	}
}
