package com.simplyapped.libgdx.ext.transition;

import com.badlogic.gdx.scenes.scene2d.Actor;


public interface Transition
{

	void applyTransition(Actor current, Actor next);
}
