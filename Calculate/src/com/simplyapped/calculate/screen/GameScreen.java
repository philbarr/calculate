package com.simplyapped.calculate.screen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.screen.tween.Vector2TweenAccessor;

public class GameScreen implements Screen
{
	private CalculateGame game;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private BitmapFont font;
	private TweenManager tweenManager;
	private Vector2 vector;

	private Tween tween;

	public GameScreen(CalculateGame game)
	{
		this.game = game;
	}

	@Override
	public void render(float delta)
	{
		tween.update(delta);
		
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		sprite.draw(batch);
		font.setColor(Color.BLACK);
		font.setScale(1);
		font.draw(batch, "hello", vector.x, vector.y);
		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void show()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(w,h);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		font = new BitmapFont();
		
		tweenManager = new TweenManager();
		vector = new Vector2(-100,-100);
		Tween.registerAccessor(Vector2.class, new Vector2TweenAccessor());
		tween = Tween.to(vector, 0 , 2).target(100).ease(TweenEquations.easeInBounce).repeatYoyo(Tween.INFINITY, 0).start();
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
		batch.dispose();
		texture.dispose();
	}

}
