package com.android.vending.billing;

import com.android.vending.billing.util.Inventory;
import com.simplyapped.libgdx.ext.billing.BillingInventory;

public class AndroidBillingInventory implements BillingInventory{

	private Inventory inventory;

	public AndroidBillingInventory(Inventory inv) {
		this.inventory = inv;
	}

}
