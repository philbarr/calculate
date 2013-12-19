package com.simplyapped.libgdx.ext.billing.listeners;

import com.simplyapped.libgdx.ext.billing.BillingInventory;
import com.simplyapped.libgdx.ext.billing.BillingResult;

public interface BillingQueryInventoryFinishedListener {
	void onQueryInventoryFinished(BillingResult result, BillingInventory inventory);
}
