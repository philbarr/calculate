package com.simplyapped.calculate;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

import com.android.vending.billing.AndroidBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.GameStateFactory.GameStateType;
import com.simplyapped.libgdx.ext.ui.AndroidOSDialog;

public class CalculateGameActivity extends AndroidApplication {

	private AndroidBillingService billing;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        GameStateFactory.setType(GameStateType.PERSISTENT);
        
        
        CalculateGame calculateGame = new CalculateGame();
        billing = new AndroidBillingService(this);
		calculateGame.setBilling(billing);
        calculateGame.setDialog(new AndroidOSDialog(this));
		initialize(calculateGame, cfg);
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pass on the activity result to the helper for handling
        if (!billing.getHelper().handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}