package com.simplyapped.libgdx.ext.billing;

public interface BillingService {
	void startSetup(String licenseKey, BillingServiceSetupFinishedListener listener);
	void queryInventoryAsync(BillingQueryInventoryFinishedListnener listener);
	void dispose();
}
