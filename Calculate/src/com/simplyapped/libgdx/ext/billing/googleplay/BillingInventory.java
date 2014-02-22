package com.simplyapped.libgdx.ext.billing.googleplay;

/**
 * Represents a block of information about in-app items.
 * An Inventory is returned by such methods as {@link IabHelper#queryInventory}.
 */
public interface BillingInventory {

    /**
     * Erase a purchase (locally) from the inventory, given its product ID. This just
     * modifies the Inventory object locally and has no effect on the server! This is
     * useful when you have an existing Inventory object which you know to be up to date,
     * and you have just consumed an item successfully, which means that erasing its
     * purchase data from the Inventory you already have is quicker than querying for
     * a new Inventory.
     */
	void erasePurchase(String productId);

	/** Return whether or not details about the given product are available. */
	boolean hasDetails(String productId);

	/** Returns whether or not there exists a purchase of the given product. */
	boolean hasPurchase(String productId);

	/** Returns the listing details for an in-app product. */
	BillingProductDetails getProductIdDetails(String productId);

	/** Returns purchase information for a given product, or null if there is no purchase. */
	BillingPurchase getPurchase(String productId);

}
