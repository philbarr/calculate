package com.simplyapped.calculate.screen.tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;

public class TransitionScreen implements Screen
{
    private boolean complete;
 
    private float inX;
    private float inY;
    private float outX;
    private float outY;
 
    private Screen inScreen;
    private Screen outScreen;
    private Group inSceneRoot;
    private Group outSceneRoot;
 
    private int durationMillis;
    private TweenEquation easeEquation;
 
    /**
     * Enter handler makes a note of scene contents position.
     *
     */
    @Override
    public void show()
    {
        this.complete = false;
 
        inX = inSceneRoot.getX();
        inY = inSceneRoot.getY();
 
        outX = outSceneRoot.getX();
        outY = outSceneRoot.getY();
    }
 
    /**
     * Exit handler resets scene contents positions.
     *
     */
    public void exit()
    {
        this.complete = true;
 
        inSceneRoot.setX(inX);
        inSceneRoot.setY(inY);
 
        outSceneRoot.setX(outX);
        outSceneRoot.setY(outY);
    }
 
    /**
     * Draw both scenes as we animated contents.
     *
     */
    @Override
    public void render(float delta)
    {
        // Move
        inSceneRoot.act(delta);
        outSceneRoot.act(delta);
 
        // Draw
        if (!complete)
        {
            outSceneRoot.draw(null, delta);
        }
        inSceneRoot.draw(null, delta);
    }
 
    /**
     * Transition complete.
     *
     * @return The transition complete handler.
     */
    public boolean isComplete()
    {
        return complete;
    }

	public void onEvent(int type, BaseTween<?> source)
	{
		switch (type)
        {
        case 1:
       //     Director.instance().setScene(this.inScene);
            break;
        default:
            break;
        }
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
}