package com.simplyapped.calculate;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.GameStateFactory.GameStateType;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        GameStateFactory.setType(GameStateType.PERSISTENT);
        
        initialize(new CalculateGame(), cfg);
    }
}