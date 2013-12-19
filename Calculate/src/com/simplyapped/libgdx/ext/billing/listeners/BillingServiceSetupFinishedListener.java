package com.simplyapped.libgdx.ext.billing.listeners;

import com.simplyapped.libgdx.ext.billing.BillingResult;

public interface BillingServiceSetupFinishedListener {
    /**
     * Called to notify that setup is complete.
     *
     * @param result The result of the setup process.
     */
	void onSetupFinished(BillingResult result);
}
