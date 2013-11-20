package com.simplyapped.calculate;

import android.media.AudioManager;
import android.os.Bundle;

import com.android.vending.billing.AndroidBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.GameStateFactory.GameStateType;
import com.simplyapped.libgdx.ext.billing.BillingService;

public class MainActivity extends AndroidApplication {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        GameStateFactory.setType(GameStateType.PERSISTENT);
        
        
        CalculateGame calculateGame = new CalculateGame();
        calculateGame.setBilling(new AndroidBillingService(this));
		initialize(calculateGame, cfg);
    }
}