package com.simplyapped.calculate;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.simplyapped.calculate.numbers.Operator;
import com.simplyapped.calculate.numbers.generator.FakeGenerator;
import com.simplyapped.calculate.numbers.generator.GeneratorFactory;
import com.simplyapped.calculate.numbers.generator.RandomGenerator;
import com.simplyapped.calculate.screen.game.GameScreen;
import com.simplyapped.calculate.screen.loser.LoserScreen;
import com.simplyapped.calculate.screen.mainmenu.MainMenuScreen;
import com.simplyapped.calculate.screen.shop.ShopScreen;
import com.simplyapped.calculate.screen.solution.ViewSolutionScreen;
import com.simplyapped.calculate.screen.stageintro.StageIntroScreen;
import com.simplyapped.calculate.screen.stageselect.StageSelectScreen;
import com.simplyapped.calculate.screen.winner.WinnerScreen;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.LevelDetails;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.billing.BillingInventory;
import com.simplyapped.libgdx.ext.billing.BillingPurchase;
import com.simplyapped.libgdx.ext.billing.BillingResult;
import com.simplyapped.libgdx.ext.billing.BillingService;
import com.simplyapped.libgdx.ext.billing.listeners.BillingOnConsumeFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingQueryInventoryFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingServiceSetupFinishedListener;
import com.simplyapped.libgdx.ext.ui.OSDialog;

public class CalculateGame extends DefaultGame {

	public final static String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq1vMWnPcNoqiZBGovCp9iI6vJkcrv09KEQMqkk9uwUL7xjfSzMpKJfn9tgyWhcRh1lZV6+01kWC7t1g0YnG8861g7Mu0OTLsGjNVX4K3EB1DJcDD9U+YE5LyqaNnDceXhPb0ujHD20W14Ua//WQcpeXoz9thbZIwufWNUjnlvbzLAWkzol1C7ftokUuWcG1A4o2tsr1bG+OGmtDEJiO9FCRbBWzyha/R4BR9BexaUb2QXi4ygb6h39G+3yaU90j0W+Byjf43KBKTVfGbi2KNFLgw+V4yQsUf7B0nxVM6DzlsVhFxZITI/AUJj6Gi4Ef7OY2s8CyMnVO3xfu76IfYhQIDAQAB";
	public final static String PRODUCT_ID_TEN_SOLUTIONS = "com.simplyapped.calculate.tensolutions";
	public final static String PRODUCT_ID_TWENTY_FIVE_SOLUTIONS = "com.simplyapped.calculate.twentyfivesolutions";
	public final static String PRODUCT_ID_FIFTY_SOLUTIONS = "com.simplyapped.calculate.fiftysolutions";
	
	public final static String PRODUCT_ID_TEST_PURCHASED = "android.test.purchased";
	public final static String PRODUCT_ID_TEST_CANCELLED = "android.test.canceled";
	public final static String PRODUCT_ID_TEST_REFUNDED = "android.test.refunded";
	public final static String PRODUCT_ID_TEST_UNAVAILABLE = "android.test.item_unavailable";
	
	public final static int SCREEN_HEIGHT = 800;
	public final static int SCREEN_WIDTH = 600;
	public final static String MAIN_MENU_SCREEN = "MainMenuScreen";
	public final static String GAME_SCREEN = "GameScreen";
	public final static String STAGE_SELECT_SCREEN = "StageSelectScreen";
	public final static String STAGE_INTRO_SCREEN = "StageIntroScreen";
	public final static String WINNER_SCREEN = "WinnerScreen";
	public final static String LOSER_SCREEN = "LoserScreen";
	public final static String VIEW_SOLUTION_SCREEN = "ViewSolutionScreen";
	public final static String SHOP_SCREEN = "ShopScreen";
	public final static int STARTING_SOLUTIONS = 10;
	public static final String NUMBER_STRIP_ALTAS = "data/numberspinner.atlas";
	public static final String NUMBER_STRIP_REGION = "numberstrip";
	
	public static boolean DEBUG = false;
	
	private BillingService billing;
	private OSDialog dialog;
	@Override
	public void create() {
		final FakeGenerator fake = new FakeGenerator();
		fake.pushNumber(50);
		fake.pushOperator(Operator.MINUS);
		fake.pushNumber(2);
		fake.pushOperator(Operator.MINUS);
		fake.pushNumber(9);
		fake.pushOperator(Operator.MINUS);
		fake.pushNumber(4);
		fake.pushOperator(Operator.MINUS);
		fake.pushNumber(6);
		fake.pushOperator(Operator.PLUS);
		fake.pushNumber(3);
		fake.pushOperator(Operator.MINUS);
		fake.pushNumber(5);
		fake.pushOperator(Operator.MULTIPLY);
		fake.pushNumber(7);
		//GeneratorFactory.setGenerator(fake);
		
		
		GeneratorFactory.setGenerator(new RandomGenerator());
		
		GameState state = GameStateFactory.getInstance();
		
		LevelDetails levelDetails = state.getLevelDetails(1);
		levelDetails.setLocked(false);
		state.saveLevelDetails(1, levelDetails);
		state.resetCurrentGameInfo();
//		final Equation eq = new Equation(state.selectBigNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber());
//		state.setCurrentEquation(eq);
		
		checkPurchasedProducts();

		TransitionFixtures.setInterpolation(Interpolation.pow5);
		
		AssetManager assets = getAssets();
		assets.load("data/mainmenuscreen.json", Skin.class);
		assets.finishLoading(); // ensure assets are available for the main menu screen
		
		addScreen(MAIN_MENU_SCREEN, MainMenuScreen.class);
		setScreen(MAIN_MENU_SCREEN);

		assets.load("data/gamescreen.json", Skin.class);
		assets.load("data/stageintroscreen.json", Skin.class);
		assets.load("data/loserscreen.json", Skin.class);
		assets.load(CalculateGame.NUMBER_STRIP_ALTAS, TextureAtlas.class);
		assets.load("data/stageselectscreen.json", Skin.class);
		assets.load("data/winnerscreen.json", Skin.class);

		addScreen(GAME_SCREEN, GameScreen.class);
		addScreen(STAGE_SELECT_SCREEN, StageSelectScreen.class);
		addScreen(STAGE_INTRO_SCREEN, StageIntroScreen.class);
		addScreen(WINNER_SCREEN, WinnerScreen.class);
		addScreen(LOSER_SCREEN, LoserScreen.class);
		addScreen(VIEW_SOLUTION_SCREEN, ViewSolutionScreen.class);
		addScreen(SHOP_SCREEN, ShopScreen.class);
	}
	
	@Override
	public void render() {
		super.render();
		
	}

	private void checkPurchasedProducts() {
		if (billing != null && !DEBUG)
		{
			billing.startSetup(CalculateGame.LICENSE_KEY, new BillingServiceSetupFinishedListener() {
				
				@Override
				public void onSetupFinished(BillingResult result) {
					if (result.isSuccess())
					{
						billing.queryInventoryAsync(new BillingQueryInventoryFinishedListener() {
							
							@Override
							public void onQueryInventoryFinished(BillingResult result,
									BillingInventory inventory) {
								checkProduct(inventory, PRODUCT_ID_TEST_PURCHASED, 0);
								checkProduct(inventory, PRODUCT_ID_TEN_SOLUTIONS, 10);
								checkProduct(inventory, PRODUCT_ID_TWENTY_FIVE_SOLUTIONS, 25);
								checkProduct(inventory, PRODUCT_ID_FIFTY_SOLUTIONS, 50);
							}

							private void checkProduct(
									final BillingInventory inventory,
									final String productId,
									final int productSolutionCount) {
								if (inventory.hasPurchase(productId))
								{
									billing.consumeAsync(inventory.getPurchase(productId), new BillingOnConsumeFinishedListener() {
										
										@Override
										public void onConsumeFinished(BillingPurchase purchase, BillingResult result) {
											if (result.isSuccess())
											{
												GameStateFactory.getInstance().increaseRemainingSolutions(productSolutionCount);
												inventory.erasePurchase(purchase.getProductId());
											}
										}
									});
								}
							}
						});
					}
					else if (dialog != null)
					{
						dialog.showLongToast("Could not to check for purchased items");
					}
				}
			});
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (billing != null)
		{
			billing.dispose();
		}
		if (assets != null)
		{
			assets.dispose();
			MainMenuScreen.assetsLoaded = false;
		}
	}

	public BillingService getBilling() {
		if (DEBUG)
		{
			//return null;
		}
		return billing;
	}

	public void setBilling(BillingService billing) {
		this.billing = billing;
	}

	public OSDialog getDialog() {
		return dialog;
	}

	public void setDialog(OSDialog dialog) {
		this.dialog = dialog;
	}
}
