package com.android.vending.billing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.android.vending.billing.test.FakePurchase;
import com.android.vending.billing.util.IabException;
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
	private List<String> testProductIds = new ArrayList<String>();
	
	public AndroidBillingService(Activity ctx)
	{
		this.context = ctx;	
		handler = new Handler();
		testProductIds.add("android.test.purchased");
		testProductIds.add("android.test.canceled");
		testProductIds.add("android.test.refunded");
		testProductIds.add("android.test.item_unavailable");
	}
	
	@Override
	public void startSetup(String licenseKey, final BillingServiceSetupFinishedListener listener) {
		helper = new IabHelper(context, licenseKey);
		helper.enableDebugLogging(true, "BILLING");
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
				if (result.isSuccess())
				{
					listener.onQueryInventoryFinished(new AndroidBillingResult(result), new AndroidBillingInventory(inv));
				}
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
	public void launchPurchaseFlow(final String productId, final int requestCode,
			final BillingOnPurchaseFinishedListener listener) {
		handler.post(new Runnable(){public void run(){
			helper.launchPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener()
			{
				@Override
				public void onIabPurchaseFinished(IabResult result, Purchase info) 
				{
					info = checkForTestPurchase(productId, info);
					BillingResult billingResult = checkForTestPurchaseBillingResult(productId, new AndroidBillingResult(result));
					listener.onBillingPurchaseFinished(billingResult, new AndroidBillingPurchase(info));
				}
			});
		}});
	}

	@Override
	public void launchPurchaseFlow(final String productId, final int requestCode,
			final BillingOnPurchaseFinishedListener listener, final String extraData) {
		handler.post(new Runnable(){public void run(){
			helper.launchPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener() {
				@Override
				public void onIabPurchaseFinished(IabResult result, Purchase info) {
					info = checkForTestPurchase(productId, info);
					BillingResult billingResult = checkForTestPurchaseBillingResult(productId, new AndroidBillingResult(result));
					listener.onBillingPurchaseFinished(billingResult, new AndroidBillingPurchase(info));
				}
			}, extraData);
		}});
	}

	@Override
	public void launchSubscriptionPurchaseFlow(final String productId,
			final int requestCode, final BillingOnPurchaseFinishedListener listener) {
		handler.post(new Runnable(){public void run(){
			helper.launchSubscriptionPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener() {
				
				@Override
				public void onIabPurchaseFinished(IabResult result, Purchase info) {
					info = checkForTestPurchase(productId, info);
					BillingResult billingResult = checkForTestPurchaseBillingResult(productId, new AndroidBillingResult(result));
					listener.onBillingPurchaseFinished(billingResult, new AndroidBillingPurchase(info));
				}
			});
		}});
	}

	@Override
	public void launchSubscriptionPurchaseFlow(final String productId,
			final int requestCode, final BillingOnPurchaseFinishedListener listener,
			final String extraData) {
		handler.post(new Runnable(){public void run(){
			helper.launchSubscriptionPurchaseFlow(context, productId, requestCode, new OnIabPurchaseFinishedListener() {
				
				@Override
				public void onIabPurchaseFinished(IabResult result, Purchase info) {
					info = checkForTestPurchase(productId, info);
					BillingResult billingResult = checkForTestPurchaseBillingResult(productId, new AndroidBillingResult(result));
					listener.onBillingPurchaseFinished(billingResult, new AndroidBillingPurchase(info));
				}
			}, extraData);
		}});
	}

	@Override
	public void launchPurchaseFlow(final String productId, final String itemType,
			final int requestCode, final BillingOnPurchaseFinishedListener listener,
			final String extraData) {
		handler.post(new Runnable(){public void run(){
			helper.launchPurchaseFlow(context, productId, itemType, requestCode, new OnIabPurchaseFinishedListener() {
				
				@Override
				public void onIabPurchaseFinished(IabResult result, Purchase info) {
					info = checkForTestPurchase(productId, info);
					BillingResult billingResult = checkForTestPurchaseBillingResult(productId, new AndroidBillingResult(result));
					listener.onBillingPurchaseFinished(billingResult, new AndroidBillingPurchase(info));
				}
			}, extraData);
		}});
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
	public void queryInventoryAsync(final boolean queryProductIdDetails,
			final List<String> moreProductIds,
			final BillingQueryInventoryFinishedListener listener) {
		handler.post(new Runnable(){public void run(){
			helper.queryInventoryAsync(queryProductIdDetails, moreProductIds, new QueryInventoryFinishedListener() {
				
				@Override
				public void onQueryInventoryFinished(IabResult result, Inventory inv) {
					listener.onQueryInventoryFinished(new AndroidBillingResult(result), new AndroidBillingInventory(inv));
				}
			});
		}});
	}

	@Override
	public void queryInventoryAsync(final boolean queryProductIdDetails,
			final BillingQueryInventoryFinishedListener listener) {
		handler.post(new Runnable(){public void run(){
			helper.queryInventoryAsync(queryProductIdDetails, new QueryInventoryFinishedListener() {
				
				@Override
				public void onQueryInventoryFinished(IabResult result, Inventory inv) {
					listener.onQueryInventoryFinished(new AndroidBillingResult(result), new AndroidBillingInventory(inv));
				}
			});
		}});
	}

	@Override 
	public BillingResult consume(BillingPurchase purchase)
	{
		if (purchase instanceof AndroidBillingPurchase && 
				purchase != null && ((AndroidBillingPurchase) purchase).getPurchase() != null)
		{
			try {
				helper.consume(((AndroidBillingPurchase) purchase).getPurchase());
				return new AndroidBillingResult(new IabResult(BillingResult.BILLING_RESPONSE_RESULT_OK, "Successful consume of product id " + purchase.getProductId()));
			} catch (IabException e) {
				return new AndroidBillingResult(e.getResult());
			}
		}
		return new AndroidBillingResult(new IabResult(BillingResult.BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE, "Purchase not knwon"));
	}
	
	@Override
	public void consumeAsync(final BillingPurchase purchase,
			final BillingOnConsumeFinishedListener listener) {
		handler.post(new Runnable(){public void run(){
			if (purchase instanceof AndroidBillingPurchase && 
					purchase != null && ((AndroidBillingPurchase) purchase).getPurchase() != null)
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
		}});
	}

	@Override
	public void consumeAsync(final List<BillingPurchase> purchases,
			final BillingOnConsumeMultiFinishedListener listener) {
		handler.post(new Runnable(){public void run(){
			List<Purchase> ps = new ArrayList<Purchase>();
			for (BillingPurchase purchase : purchases) {
				if (purchase instanceof AndroidBillingPurchase  && purchase != null)
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
		}});
	}

	private void sendConsumeMultiFailure(
			final BillingOnConsumeMultiFinishedListener listener) {
		List<BillingResult> rs = new ArrayList<BillingResult>();
		rs.add(failureResult);
		listener.onConsumeMultiFinished(null, rs);
	}
	
	private Purchase checkForTestPurchase(final String productId, Purchase info) {
		Purchase purchase = null;
		if (testProductIds.contains(productId))
		{
			try {
				FakePurchase purchased = new FakePurchase();
				purchased.setItemType(IabHelper.ITEM_TYPE_INAPP);
				purchased.setSku(productId);
				purchased.setPackageName(context.getPackageName());				
				purchase = purchased;
			} catch (Exception e) {
				Log.e("BILLING TESTING FAILURE" , e.getMessage());
			}
		}
		else
		{
			purchase = info;
		}
		
		return purchase == null ? info : purchase;
	}
	
	private BillingResult checkForTestPurchaseBillingResult(String productId, BillingResult result)
	{
		BillingResult ret;
		if (testProductIds.contains(productId))
		{
			ret = successResult;
		}
		else
		{
			ret = result;
		}
		return ret;
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
	
	private BillingResult successResult = new BillingResult()
	{

		@Override
		public boolean isFailure() {
			return false;
		}

		@Override
		public boolean isSuccess() {
			return true;
		}

		@Override
		public String getMessage() {
			return "BillingPurchase success";
		}

		@Override
		public int getResponse() {
			return BillingResult.BILLING_RESPONSE_RESULT_OK;
		}
		
	};
	private Handler handler;

	public IabHelper getHelper() {
		return helper;
	}
}
