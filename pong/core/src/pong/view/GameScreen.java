package pong.view;

import pong.Board;
import pong.BoardRenderer;
import pong.Player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends PongScreen {

	Board match1;
	BoardRenderer renderer1;

	Board match2;
	BoardRenderer renderer2;

	Board match3;
	BoardRenderer renderer3;

	Board match4;
	BoardRenderer renderer4;

	int x, y, width, height, bWidth, bHeight;

	public GameScreen(Game game, int x, int y, int width, int height) {
		super(game);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		bWidth = width / 2;
		bHeight = height / 2;
	}

	@Override
	public void show() {

		Player player0 = new Player("player0", 0, Keys.Q, Keys.A);
		Player player1 = new Player("player1", 0, Keys.W, Keys.S);
		Player player2 = new Player("player2", 0, Keys.E, Keys.D);
		Player player3 = new Player("player3", 0, Keys.R, Keys.F);
		Player player4 = new Player("player4", 0, Keys.T, Keys.G);
		Player player5 = new Player("player5", 0, Keys.Y, Keys.H);
		Player player6 = new Player("player6", 0, Keys.U, Keys.J);
		Player player7 = new Player("player7", 0, Keys.I, Keys.K);

		match1 = new Board(0, 0, bWidth, bHeight, player0, player1);

		match2 = new Board(bWidth, 0, bWidth, bHeight, player2, player3);

		match3 = new Board(0, bHeight, bWidth, bHeight, player4, player5);

		match4 = new Board(bWidth, bHeight, bWidth, bHeight, player6, player7);

		renderer1 = new BoardRenderer(match1);
		renderer2 = new BoardRenderer(match2);
		renderer3 = new BoardRenderer(match3);
		renderer4 = new BoardRenderer(match4);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		deltaTime = Math.min(0.06f, Gdx.graphics.getDeltaTime());

		match1.update(deltaTime);
		match2.update(deltaTime);
		match3.update(deltaTime);
		match4.update(deltaTime);

		renderer1.render(deltaTime);
		renderer2.render(deltaTime);
		renderer3.render(deltaTime);
		renderer4.render(deltaTime);
	}

	@Override
	public void hide() {
		renderer1.dispose();
		renderer2.dispose();
		renderer3.dispose();
		renderer4.dispose();
	}
}