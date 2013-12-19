package com.simplyapped.libgdx.ext.billing.listeners;

import com.simplyapped.libgdx.ext.billing.BillingPurchase;
import com.simplyapped.libgdx.ext.billing.BillingResult;

public interface BillingOnConsumeFinishedListener {
	/**
     * Called to notify that a consumption has finished.
     *
     * @param purchase The purchase that was (or was to be) consumed.
     * @param result The result of the consumption operation.
     */
    public void onConsumeFinished(BillingPurchase purchase, BillingResult result);
}
