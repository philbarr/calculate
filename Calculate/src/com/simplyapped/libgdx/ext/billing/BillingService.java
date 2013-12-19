package com.simplyapped.libgdx.ext.billing;

import java.util.List;

import com.simplyapped.libgdx.ext.billing.listeners.BillingOnConsumeFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingOnConsumeMultiFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingOnPurchaseFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingQueryInventoryFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingServiceSetupFinishedListener;



public interface BillingService {
	/**
	 * Starts the setup process. This will start up the setup process asynchronously.
	 * You will be notified through the listener when the setup process is complete.
	 * This method is safe to call from a UI thread.
	 *
	 * @param listener The listener to notify when the setup process is complete.
	 */
	public abstract void startSetup(String licenseKey, BillingServiceSetupFinishedListener listener);

	/**
	 * Dispose of object, releasing resources. It's very important to call this
	 * method when you are done with this object. It will release any resources
	 * used by it such as service connections. Naturally, once the object is
	 * disposed of, it can't be used again.
	 */
	public abstract void dispose();

	/** Returns whether subscriptions are supported. */
	public abstract boolean subscriptionsSupported();

	public abstract void launchPurchaseFlow(String productId,
			int requestCode, BillingOnPurchaseFinishedListener listener);

	public abstract void launchPurchaseFlow(String productId,
			int requestCode, BillingOnPurchaseFinishedListener listener,
			String extraData);

	public abstract void launchSubscriptionPurchaseFlow(
			String productId, int requestCode, BillingOnPurchaseFinishedListener listener);

	public abstract void launchSubscriptionPurchaseFlow(
			String productId, int requestCode,
			BillingOnPurchaseFinishedListener listener, String extraData);

	/**
	 * Initiate the UI flow for an in-app purchase. Call this method to initiate an in-app purchase,
	 * which will involve bringing up the Google Play screen. The calling activity will be paused while
	 * the user interacts with Google Play, and the result will be delivered via the activity's
	 * {@link android.app.Activity#onActivityResult} method, at which point you must call
	 * this object's {@link #handleActivityResult} method to continue the purchase flow. This method
	 * MUST be called from the UI thread of the Activity.
	 *
	 * @param act The calling activity.
	 * @param productId The productId of the item to purchase.
	 * @param itemType indicates if it's a product or a subscription (ITEM_TYPE_INAPP or ITEM_TYPE_SUBS)
	 * @param requestCode A request code (to differentiate from other responses --
	 *     as in {@link android.app.Activity#startActivityForResult}).
	 * @param listener The listener to notify when the purchase process finishes
	 * @param extraData Extra data (developer payload), which will be returned with the purchase data
	 *     when the purchase completes. This extra data will be permanently bound to that purchase
	 *     and will always be returned when the purchase is queried.
	 */
	public abstract void launchPurchaseFlow(String productId,
			String itemType, int requestCode,
			BillingOnPurchaseFinishedListener listener, String extraData);

	public abstract BillingInventory queryInventory(boolean queryProductIdDetails,
			List<String> moreProductIds) throws Exception;

	/**
	 * Queries the inventory. This will query all owned items from the server, as well as
	 * information on additional productIds, if specified. This method may block or take long to execute.
	 * Do not call from a UI thread. For that, use the non-blocking version {@link #refreshInventoryAsync}.
	 *
	 * @param queryProductIdDetails if true, Product details (price, description, etc) will be queried as well
	 *     as purchase information.
	 * @param moreItemProductIds additional PRODUCT ProductIds to query information on, regardless of ownership.
	 *     Ignored if null or if queryProductIdDetails is false.
	 * @param moreSubscriptionProductIds additional SUBSCRIPTIONS ProductIds to query information on, regardless of ownership.
	 *     Ignored if null or if queryProductIdDetails is false.
	 * @throws IabException if a problem occurs while refreshing the inventory.
	 */
	public abstract BillingInventory queryInventory(boolean queryProductIdDetails,
			List<String> moreItemProductIds, List<String> moreSubscriptionProductIds)
			throws Exception;

	/**
	 * Asynchronous wrapper for inventory query. This will perform an inventory
	 * query as described in {@link #queryInventory}, but will do so asynchronously
	 * and call back the specified listener upon completion. This method is safe to
	 * call from a UI thread.
	 *
	 * @param queryProductIdDetails as in {@link #queryInventory}
	 * @param moreProductIds as in {@link #queryInventory}
	 * @param listener The listener to notify when the refresh operation completes.
	 */
	public abstract void queryInventoryAsync(boolean queryProductIdDetails,
			List<String> moreProductIds, BillingQueryInventoryFinishedListener listener);

	public abstract void queryInventoryAsync(
			BillingQueryInventoryFinishedListener listener);

	public abstract void queryInventoryAsync(boolean queryProductIdDetails,
			BillingQueryInventoryFinishedListener listener);

	/**
	 * Asynchronous wrapper to item consumption. Works like {@link #consume}, but
	 * performs the consumption in the background and notifies completion through
	 * the provided listener. This method is safe to call from a UI thread.
	 *
	 * @param purchase The purchase to be consumed.
	 * @param listener The listener to notify when the consumption operation finishes.
	 */
	public abstract void consumeAsync(BillingPurchase purchase,
			BillingOnConsumeFinishedListener listener);

	/**
	 * Same as {@link consumeAsync}, but for multiple items at once.
	 * @param purchases The list of PurchaseInfo objects representing the purchases to consume.
	 * @param listener The listener to notify when the consumption operation finishes.
	 */
	public abstract void consumeAsync(List<BillingPurchase> purchases,
			BillingOnConsumeMultiFinishedListener listener);
}
