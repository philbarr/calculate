package com.simplyapped.libgdx.ext.billing.googleplay.listeners;

import com.simplyapped.libgdx.ext.billing.googleplay.BillingPurchase;
import com.simplyapped.libgdx.ext.billing.googleplay.BillingResult;

public interface BillingOnPurchaseFinishedListener {
    /**
     * Called to notify that an in-app purchase finished. If the purchase was successful,
     * then the project id parameter specifies which item was purchased. If the purchase failed,
     * the project id and extraData parameters may or may not be null, depending on how far the purchase
     * process went.
     *
     * @param result The result of the purchase.
     * @param info The purchase information (null if purchase failed)
     */
    public void onBillingPurchaseFinished(BillingResult result, BillingPurchase info);
}
