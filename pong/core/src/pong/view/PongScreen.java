package pong.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class PongScreen implements Screen {

	Game game;

	public PongScreen(Game game) {
		this.game = game;
	}

	@Override
	public abstract void render(float delta);

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		game.dispose();
	}

}