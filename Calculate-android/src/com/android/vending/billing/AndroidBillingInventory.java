package com.android.vending.billing;

import com.android.vending.billing.util.Inventory;
import com.simplyapped.libgdx.ext.billing.BillingInventory;
import com.simplyapped.libgdx.ext.billing.BillingProductDetails;
import com.simplyapped.libgdx.ext.billing.BillingPurchase;

public class AndroidBillingInventory implements BillingInventory{

	private Inventory inventory;

	public AndroidBillingInventory(Inventory inv) {
		this.inventory = inv;
	}
	
	@Override
	public BillingPurchase getPurchase(String productId)
	{
		return new AndroidBillingPurchase(inventory.getPurchase(productId));
	}
	
    
    @Override
	public BillingProductDetails getProductIdDetails(String productId) {
        return new AndroidBillingProductDetails(inventory.getSkuDetails(productId));
    }

    
    @Override
	public boolean hasPurchase(String productId) {
        return inventory.hasPurchase(productId);
    }

    
    @Override
	public boolean hasDetails(String productId) {
        return inventory.hasDetails(productId);
    }


    @Override
	public void erasePurchase(String productId) {
        inventory.erasePurchase(productId);
    }
}
