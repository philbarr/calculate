package com.simplyapped.libgdx.ext.billing.googleplay.listeners;

import com.simplyapped.libgdx.ext.billing.googleplay.BillingInventory;
import com.simplyapped.libgdx.ext.billing.googleplay.BillingResult;

public interface BillingQueryInventoryFinishedListener {
    /**
     * Called to notify that an inventory query operation completed.
     *
     * @param result The result of the operation.
     * @param inventory The inventory.
     */
	void onQueryInventoryFinished(BillingResult result, BillingInventory inventory);
}
