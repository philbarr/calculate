package com.simplyapped.calculate;

import com.badlogic.gdx.math.Interpolation;
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
import com.simplyapped.libgdx.ext.billing.BillingResult;
import com.simplyapped.libgdx.ext.billing.BillingService;
import com.simplyapped.libgdx.ext.billing.listeners.BillingQueryInventoryFinishedListener;
import com.simplyapped.libgdx.ext.billing.listeners.BillingServiceSetupFinishedListener;
import com.simplyapped.libgdx.ext.ui.OSDialog;

public class CalculateGame extends DefaultGame {

	public final static String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq1vMWnPcNoqiZBGovCp9iI6vJkcrv09KEQMqkk9uwUL7xjfSzMpKJfn9tgyWhcRh1lZV6+01kWC7t1g0YnG8861g7Mu0OTLsGjNVX4K3EB1DJcDD9U+YE5LyqaNnDceXhPb0ujHD20W14Ua//WQcpeXoz9thbZIwufWNUjnlvbzLAWkzol1C7ftokUuWcG1A4o2tsr1bG+OGmtDEJiO9FCRbBWzyha/R4BR9BexaUb2QXi4ygb6h39G+3yaU90j0W+Byjf43KBKTVfGbi2KNFLgw+V4yQsUf7B0nxVM6DzlsVhFxZITI/AUJj6Gi4Ef7OY2s8CyMnVO3xfu76IfYhQIDAQAB";
	public final static String PRODUCT_ID_TEN_SOLUTIONS = "com.simplyapped.calculate.tensolutions";
	public final static String PRODUCT_ID_TWENTY_FIVE_SOLUTIONS = "com.simplyapped.calculate.twentyfivesolutions";
	public final static String PRODUCT_ID_FIFTY_SOLUTIONS = "com.simplyapped.calculate.fiftysolutions";
	
	public final static int SCREEN_HEIGHT = 800;
	public final static int SCREEN_WIDTH = 600;
	public final static String MAIN_MENU_SCREEN = "MainMenuScreen";
	public final static String GAME_SCREEN = "GameScreen";
	public final static String STAGE_SELECT_SCREEN = "StageSelectScreen";
	public final static String STAGE_INTRO_SCREEN = "StageIntroScreen";
	public final static String WINNER_SCREEN = "WinnerScreen";
	public final static String LOSER_SCREEN = "LoserScreen";
	public final static String VIEW_SOLUTION_SCREEN = "ViewSolutionScreen";
	public final static String SHOP_SCREEN = "ViewSolutionScreen";
	public final static int STARTING_SOLUTIONS = 10;
	
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
		
		TransitionFixtures.setInterpolation(Interpolation.pow5);
		
		addScreen(MAIN_MENU_SCREEN, new MainMenuScreen(this));
		addScreen(GAME_SCREEN, new GameScreen(this));
		addScreen(STAGE_SELECT_SCREEN, new StageSelectScreen(this));
		addScreen(STAGE_INTRO_SCREEN, new StageIntroScreen(this));
		addScreen(WINNER_SCREEN, new WinnerScreen(this));
		addScreen(LOSER_SCREEN, new LoserScreen(this));
		addScreen(VIEW_SOLUTION_SCREEN, new ViewSolutionScreen(this));
		addScreen(SHOP_SCREEN, new ShopScreen(this));
		 
		setScreen(MAIN_MENU_SCREEN);
		
		if (billing != null)
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
								if (inventory.hasPurchase(PRODUCT_ID_TEN_SOLUTIONS))
								{
									billing.
								}
							}
						});
					}
					else if (dialog != null)
					{
						dialog.showLongToast("Failed to check any purchased items state");
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
	}

	public BillingService getBilling() {
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
