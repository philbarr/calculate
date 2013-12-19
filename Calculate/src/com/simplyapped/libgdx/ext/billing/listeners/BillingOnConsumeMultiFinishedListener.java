package com.simplyapped.libgdx.ext.billing.listeners;

import java.util.List;

import com.simplyapped.libgdx.ext.billing.BillingPurchase;
import com.simplyapped.libgdx.ext.billing.BillingResult;

public interface BillingOnConsumeMultiFinishedListener {
    /**
     * Called to notify that a consumption of multiple items has finished.
     *
     * @param purchases The purchases that were (or were to be) consumed.
     * @param results The results of each consumption operation, corresponding to each
     *     sku.
     */
    public void onConsumeMultiFinished(List<BillingPurchase> purchases, List<BillingResult> results);
}
