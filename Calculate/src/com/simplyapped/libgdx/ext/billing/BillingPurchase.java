package com.simplyapped.libgdx.ext.billing;

public interface BillingPurchase {
    String getItemType();
    String getOrderId();     
    String getPackageName(); 
    String getProductId();         
    long getPurchaseTime();
    PURCHASE_STATE getPurchaseState();  
    String getDeveloperPayload(); 
    String getToken(); 
    String getOriginalJson(); 
    String getSignature();
    
    enum PURCHASE_STATE
    {
    	PURCHASED,
    	CANCELLED,
    	REFUNDED
    }
}
