package com.android.vending.billing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.android.vending.billing.util.IabHelper;
import com.android.vending.billing.util.IabHelper.OnConsumeFinishedListener;
import com.android.vending.billing.util.IabHelper.OnConsumeMultiFinishedListener;
import com.android.vending.billing.util.IabHelper.OnIabPurchaseFinishedListener;
import com.android.vending.billing.util.IabHelper.QueryInventoryFinishedListener;
import com.android.vending.billing.util.IabResult;
import com.android.vending.billing.util.Inventory;
import com.android.vending.billing.util.Purchase;
import com.simplyapped.libgdx.ext.billing.BillingInventory;
import com.simplyapped.libgdx.ext.billing.BillingPurchase;
import com.simplyapped.libgdx.ext.billing.BillingResult;
import com.simplyapped.libgdx.ext.billing.BillingService;
import com.simplyapped.libgdx.ext.billing.listeners.BillingOnConsumeFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingOnConsumeMultiFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingOnPurchaseFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingQueryInventoryFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingServiceSetupFinishedListener;

public class AndroidBillingService implements BillingService {

	IabHelper helper;
	Activity context;
	private boolean isSetup;
	
	public AndroidBillingService(Activity ctx)
	{
		this.context = ctx;	
	}
	
	@Override
	public void startSetup(String licenseKey, final BillingServiceSetupFinishedListener listener) {
		helper = new IabHelper(context, licenseKey);
        helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
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
		if (helper != null)
		{
			helper.dispose();
		}
		helper = null;
	}

	@Override
	public void queryInventoryAsync(final BillingQueryInventoryFinishedListener listener) {
		helper.queryInventoryAsync(new QueryInventoryFinishedListener() {
			
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				listener.onQueryInventoryFinished(new AndroidBillingResult(result), new AndroidBillingInventory(inv));
			}
		});
	}

	public boolean isSetup() {
		return isSetup;
	}

	@Override
	public boolean subscriptionsSupported() {
		return helper.subscriptionsSupported();
	}

	@Override
	public void launchPurchaseFlow(String productId, int requestCode,
			final BillingOnPurchaseFinishedListener listener) {
		helper.launchPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener()
		{
			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase info) 
			{
				listener.onBillingPurchaseFinished(new AndroidBillingResult(result), new AndroidBillingPurchase(info));
			}
		});
	}

	@Override
	public void launchPurchaseFlow(String productId, int requestCode,
			final BillingOnPurchaseFinishedListener listener, String extraData) {
		helper.launchPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener() {
			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase info) {
				listener.onBillingPurchaseFinished(new AndroidBillingResult(result), new AndroidBillingPurchase(info));
			}
		}, extraData);
	}

	@Override
	public void launchSubscriptionPurchaseFlow(String productId,
			int requestCode, final BillingOnPurchaseFinishedListener listener) {
		helper.launchSubscriptionPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener() {
			
			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase info) {
				listener.onBillingPurchaseFinished(new AndroidBillingResult(result), new AndroidBillingPurchase(info));
			}
		});
	}

	@Override
	public void launchSubscriptionPurchaseFlow(String productId,
			int requestCode, final BillingOnPurchaseFinishedListener listener,
			String extraData) {
		helper.launchSubscriptionPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener() {
			
			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase info) {
				listener.onBillingPurchaseFinished(new AndroidBillingResult(result), new AndroidBillingPurchase(info));
			}
		}, extraData);
	}

	@Override
	public void launchPurchaseFlow(String productId, String itemType,
			int requestCode, final BillingOnPurchaseFinishedListener listener,
			String extraData) {
		helper.launchPurchaseFlow(context, productId, itemType, requestCode, new OnIabPurchaseFinishedListener() {
			
			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase info) {
				listener.onBillingPurchaseFinished(new AndroidBillingResult(result), new AndroidBillingPurchase(info));
			}
		}, extraData);
	}

	@Override
	public BillingInventory queryInventory(boolean queryProductIdDetails,
			List<String> moreProductIds) throws Exception {
		return new AndroidBillingInventory(helper.queryInventory(queryProductIdDetails, moreProductIds));
	}

	@Override
	public BillingInventory queryInventory(boolean queryProductIdDetails,
			List<String> moreItemProductIds,
			List<String> moreSubscriptionProductIds) throws Exception {
		return new AndroidBillingInventory(helper.queryInventory(queryProductIdDetails, moreItemProductIds, moreSubscriptionProductIds));
	}

	@Override
	public void queryInventoryAsync(boolean queryProductIdDetails,
			List<String> moreProductIds,
			final BillingQueryInventoryFinishedListener listener) {
		helper.queryInventoryAsync(queryProductIdDetails, moreProductIds, new QueryInventoryFinishedListener() {
			
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				listener.onQueryInventoryFinished(new AndroidBillingResult(result), new AndroidBillingInventory(inv));
			}
		});
	}

	@Override
	public void queryInventoryAsync(boolean queryProductIdDetails,
			final BillingQueryInventoryFinishedListener listener) {
		helper.queryInventoryAsync(queryProductIdDetails, new QueryInventoryFinishedListener() {
			
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				listener.onQueryInventoryFinished(new AndroidBillingResult(result), new AndroidBillingInventory(inv));
			}
		});
	}

	@Override
	public void consumeAsync(BillingPurchase purchase,
			final BillingOnConsumeFinishedListener listener) {
		if (purchase instanceof AndroidBillingPurchase)
		{
			helper.consumeAsync(((AndroidBillingPurchase)purchase).getPurchase(), new OnConsumeFinishedListener() {
	
				@Override
				public void onConsumeFinished(Purchase purchase, IabResult result) {
					listener.onConsumeFinished(new AndroidBillingPurchase(purchase), new AndroidBillingResult(result));
				}		
			});
		}
		else
		{
			listener.onConsumeFinished(null, failureResult);
		}
	}

	@Override
	public void consumeAsync(List<BillingPurchase> purchases,
			final BillingOnConsumeMultiFinishedListener listener) {
		List<Purchase> ps = new ArrayList<Purchase>();
		for (BillingPurchase purchase : purchases) {
			if (purchase instanceof AndroidBillingPurchase)
			{
				ps.add(((AndroidBillingPurchase)purchase).getPurchase());
			}
		}
		if (ps.size()>0)
		{
			helper.consumeAsync(ps, new OnConsumeMultiFinishedListener(){

				@Override
				public void onConsumeMultiFinished(List<Purchase> purchases,
						List<IabResult> results) {
					List<BillingPurchase> abs = new ArrayList<BillingPurchase>();
					List<BillingResult> res = new ArrayList<BillingResult>();
					if (purchases != null && results != null)
					{
						for(int i = 0; i < purchases.size(); i++)
						{
							abs.add(new AndroidBillingPurchase(purchases.get(i)));
							res.add(new AndroidBillingResult(results.get(i)));
						}
						listener.onConsumeMultiFinished(abs, res);
					}
					else
					{
						sendConsumeMultiFailure(listener);
					}
				}});
		}
		else
		{
			sendConsumeMultiFailure(listener);
		}
	}

	private void sendConsumeMultiFailure(
			final BillingOnConsumeMultiFinishedListener listener) {
		List<BillingResult> rs = new ArrayList<BillingResult>();
		rs.add(failureResult);
		listener.onConsumeMultiFinished(null, rs);
	}
	
	private BillingResult failureResult = new BillingResult()
	{

		@Override
		public boolean isFailure() {
			return true;
		}

		@Override
		public boolean isSuccess() {
			return false;
		}

		@Override
		public String getMessage() {
			return "BillingPurchase is not an instance of AndroidBillingPurchase";
		}

		@Override
		public int getResponse() {
			return BillingResult.BILLING_RESPONSE_RESULT_ERROR;
		}
		
	};
}
