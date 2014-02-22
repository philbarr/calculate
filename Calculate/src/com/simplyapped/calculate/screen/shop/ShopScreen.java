package com.simplyapped.calculate.screen.shop;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.billing.googleplay.BillingInventory;
import com.simplyapped.libgdx.ext.billing.googleplay.BillingPurchase;
import com.simplyapped.libgdx.ext.billing.googleplay.BillingResult;
import com.simplyapped.libgdx.ext.billing.googleplay.BillingService;
import com.simplyapped.libgdx.ext.billing.googleplay.listeners.BillingOnConsumeFinishedListener;
import com.simplyapped.libgdx.ext.billing.googleplay.listeners.BillingOnPurchaseFinishedListener;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class ShopScreen extends DefaultScreen{
	
	private static final String YOU_HAVE_S_SOLUTIONS_REMAINING = "You Have\n%s\nSolutions\nRemaining";
	private static final String BUY_S_SOLUTIONS = "Buy %s Solutions";
	private Skin skin;
	private Table window;
	private float buttonHeight;
	private float buttonWidth;
	private Label label;
	private Table buttonBorder;
	

	public ShopScreen(DefaultGame game) {
		super(game);
		skin = game.getAssets().get("data/gamescreen.json");
	}

	@Override
	public void show() {
		Gdx.app.log(ShopScreen.class.toString(), "SHOWING SHOP SCREEN");
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, true);
		stage.addListener(new ClickListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE)
				{
					if (GameStateFactory.getInstance().isViewingSolution() &&
							GameStateFactory.getInstance().getRemainingSolutions()>0)
					{
						game.transitionTo(CalculateGame.GAME_SCREEN, TransitionFixtures.UnderlapRight());
					}
					else 
					{
						game.transitionTo(CalculateGame.MAIN_MENU_SCREEN, TransitionFixtures.UnderlapRight());
					}
					return true;
				}
				return false;
			}
		});
		window = new Table();	    
	    window.setFillParent(true);
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
	    
	    buttonHeight = stage.getHeight() / 7;
	    buttonWidth = stage.getWidth()/ 1.5f;
	    float height = stage.getHeight()/13f;
	    int padding = 20;

		label = new Label("", skin, "text");
		label.setAlignment(Align.center);
		label.setFontScale(0.8f);
		label.setSize(buttonWidth + padding*2, stage.getHeight()/4.5f);
		label.setPosition(stage.getWidth()/2-buttonWidth/2-padding, stage.getHeight()/1.4f);
		label.getStyle().background=(skin.getDrawable("title"));
		
	    buttonBorder = new Table();
		buttonBorder.setPosition(stage.getWidth()/2-buttonWidth/2-padding, height - padding);
	    buttonBorder.setSize(buttonWidth + padding*2, height + (buttonHeight*3) + (padding*2) + (height));
	    buttonBorder.setBackground(skin.getDrawable("buttonborder"));
	    
	    stage.addActor(window);
	    stage.addActor(label); 
	    stage.addActor(buttonBorder);
	    addButton(height*3 + buttonHeight*2, CalculateGame.PRODUCT_ID_TEN_SOLUTIONS, 10);
		addButton(height*2+buttonHeight, CalculateGame.PRODUCT_ID_TWENTY_FIVE_SOLUTIONS, 25);
		addButton(height, CalculateGame.PRODUCT_ID_FIFTY_SOLUTIONS, 50);
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	    updateTitle();
	}

	private void updateTitle() {
		try {
			label.setText(String.format(YOU_HAVE_S_SOLUTIONS_REMAINING, GameStateFactory.getInstance().getRemainingSolutions()));
			buttonBorder.setBackground(skin.getDrawable("buttonborder"));
		} catch (Exception e) {
			Gdx.app.log("ERROR", "Failed to update solutions label");
		}
	}

	@Override
	public void resume() {
		super.resume();
		updateTitle();
	}
	
	private void addButton(float height, final String productId, final int solutionCount) {
		TextButton purchase = new TextButton(String.format(BUY_S_SOLUTIONS, solutionCount), skin, "green");
		purchase.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				try
				{
				final int requestCode = new Random().nextInt(1000) + 10000;
				final BillingService billing = game.getBilling();
				if (billing != null)
				{
					billing.launchPurchaseFlow(productId, requestCode, new BillingOnPurchaseFinishedListener() {
						
						@Override
						public void onBillingPurchaseFinished(BillingResult result,
								BillingPurchase thePurchase) {
							
							if (result.isSuccess())
							{
								if (thePurchase.getPurchaseState() == BillingPurchase.PURCHASE_STATE.PURCHASED)
								{
									consume(solutionCount, billing, thePurchase);
								}
								else if (thePurchase.getPurchaseState() == BillingPurchase.PURCHASE_STATE.CANCELLED)
								{
									game.getDialog().showLongToast("Purchase Cancelled");
									erasePurchase(billing, thePurchase);
								}
								else if (thePurchase.getPurchaseState() == BillingPurchase.PURCHASE_STATE.REFUNDED)
								{
									game.getDialog().showLongToast("Your Refund Request\nHas Been Processed");
								}
							}
							else
							{
								if (result.getResponse() == BillingResult.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED &&
										thePurchase != null)
								{
									consume(solutionCount, billing, thePurchase);
								}
								else
								{
									Gdx.app.log("BILLING", "responseCode: " + result.getResponse() + ", message" + result.getMessage());
								}
							}
						}

						private void consume(final int solutionCount,
								final BillingService billing,
								BillingPurchase purchase) {
							
							billing.consumeAsync(purchase, new BillingOnConsumeFinishedListener() {
								
								@Override
								public void onConsumeFinished(BillingPurchase purchase, BillingResult result) {
									if (result.isSuccess())
									{
										GameStateFactory.getInstance().increaseRemainingSolutions(solutionCount);
										game.getDialog().showLongToast(solutionCount + " Solutions Purchased!");
										updateTitle();
										erasePurchase(billing, purchase);
									}
									else
									{
										Gdx.app.error("BILLING", result.getMessage());
									}
								}
							});
						}
						
						private void erasePurchase(
								final BillingService billing,
								final BillingPurchase purchase) {
							
							BillingInventory inventory;
							try {
								inventory = billing.queryInventory(false, null);
								if (inventory != null)
								{
									inventory.erasePurchase(purchase.getProductId());
								}
							} catch (Exception e) {
								// well at least we tried to clean up the inventory
							}
						}
					});
				}
				}
				catch(Throwable e)
				{
					Gdx.app.log("BILLING", e.getMessage(), e);
				}
				
			}
		});
		purchase.getLabel().setFontScale(0.8f);
	    purchase.setSize(buttonWidth, buttonHeight);
		purchase.setPosition(stage.getWidth()/2 - purchase.getWidth()/2, height);
	    stage.addActor(purchase);
	}
}
