package multipong.screens;

import java.util.List;

import multipong.match.Match;
import multipong.settings.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class MatchRenderer {

	BitmapFont font = Fonts.fontSmall;
	protected SpriteBatch batch = new SpriteBatch();
	protected ShapeRenderer renderer = new ShapeRenderer();
	List<Match> visibleMatches;

	Camera camera;

	protected Color foregroundObjectColor = Color.WHITE;
	protected Color backgroundObjectColor = Color.GRAY;
	protected Color backgroundColor = Color.BLACK;

	public MatchRenderer(Camera camera, List<Match> visibleMatches) {
		this.camera = camera;
		this.visibleMatches = visibleMatches;
	}

	public void dispose() {
		renderer.dispose();
		batch.dispose();
		font.dispose();
	}

	public void renderBoards(float deltaTime) {

		renderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g,
				backgroundColor.b, backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Match match : visibleMatches) {

			renderFields(match);

			if (match.isFinished()) {
				renderRoundWinner(match);

			} else if (match.isPlayable()) {
				renderLeftName(match);
				renderRightName(match);
				renderScores(match);
				renderLeftPad(match);
				renderRightPad(match);

				if (match.hasStartCountDown()) {
					float number = (Settings.timeMatchStartCountDownFrom + 1 - match.stateTime);
					renderCountDown(number, match.board.midPointX,
							match.board.midPointY);
				} else if (match.hasRedrawCountDown()) {
					float number = (match.redrawCountDown + 1);
					renderCountDown(number, match.board.midPointX,
							match.board.midPointY);
				} else {
					renderBall(match);
				}

			} else if (match.hasLeftPlayer() && !match.hasRightPlayer()) {
				// Show "waiting for other player" on right side.
				renderRightSideWaiting(match);
				renderLeftPad(match);
				renderLeftName(match);

			} else if (!match.hasLeftPlayer() && !match.hasLeftPlayer()) {
				// Show "waiting for player" on both sides.
				renderLeftSideWaiting(match);
				renderRightSideWaiting(match);

			}

		}
	}

	private void renderBall(Match match) {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.WHITE);
		renderer.rect(match.board.ball.getX(), match.board.ball.getY(),
				match.board.ball.getWidth(), match.board.ball.getHeight());
		renderer.end();
	}

	private void renderCountDown(float number, float midPointX, float midPointY) {
		batch.begin();
		font = Fonts.fontLarge;
		Color c = foregroundObjectColor;
		font.setColor(c.r, c.g, c.b, number - (int) number);

		String str = Integer.toString((int) number);
		float xOffset = midPointX - (font.getBounds(str).width / 2);
		float yOffset = midPointY + (font.getBounds(str).height / 2);

		font.draw(batch, str, xOffset, yOffset);
		font = Fonts.fontSmall;
		batch.end();
	}

	private void renderFields(Match match) {
		renderer.begin(ShapeType.Line);
		renderer.setColor(foregroundObjectColor);
		// Draw field
		renderer.rect(match.board.field.getX() + 1, match.board.field.getY(),
				match.board.field.getWidth() - 1,
				match.board.field.getHeight() - 1);

		renderer.setColor(Color.GRAY);
		for (Vector2 separator : match.board.separatorPos) {
			renderer.rect(separator.x, separator.y, 0,
					match.board.separatorLength);
		}
		renderer.end();
	}

	private void renderLeftName(Match match) {
		font.setColor(backgroundObjectColor);
		font = Fonts.fontSmall;

		batch.begin();
		float xOffset = match.board.leftMidPointX
				- (font.getBounds(match.board.leftPlayer.name).width / 2);
		font.draw(batch, match.board.leftPlayer.name, xOffset,
				match.board.leftPlayerNamePos.y);
		batch.end();
	}

	private void renderLeftPad(Match match) {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(foregroundObjectColor);
		// Draw pad
		renderer.rect(match.board.leftPlayerPad.getX(),
				match.board.leftPlayerPad.getY(),
				match.board.leftPlayerPad.getWidth(),
				match.board.leftPlayerPad.getHeight());
		renderer.end();
	}

	private void renderLeftSideWaiting(Match match) {
		font.setColor(foregroundObjectColor);
		font = Fonts.fontSmall;

		String str = "Waiting for player...";
		float xOffset = match.board.leftMidPointX
				- (font.getBounds(str).width / 2);
		float yOffset = match.board.midPointY;
		batch.begin();
		font.draw(batch, str, xOffset, yOffset);
		batch.end();
	}

	private void renderRightName(Match match) {
		font.setColor(backgroundObjectColor);
		font = Fonts.fontSmall;

		batch.begin();
		float xOffset = match.board.rightMidPointX
				- (font.getBounds(match.board.rightPlayer.name).width / 2);
		font.draw(batch, match.board.rightPlayer.name, xOffset,
				match.board.rightPlayerNamePos.y);
		batch.end();
	}

	private void renderRightPad(Match match) {
		renderer.setColor(foregroundObjectColor);
		renderer.begin(ShapeType.Filled);
		// Draw pad
		renderer.rect(match.board.rightPlayerPad.getX(),
				match.board.rightPlayerPad.getY(),
				match.board.rightPlayerPad.getWidth(),
				match.board.rightPlayerPad.getHeight());
		renderer.end();
	}

	private void renderRightSideWaiting(Match match) {
		font.setColor(foregroundObjectColor);
		font = Fonts.fontSmall;

		String str = "Waiting for player...";
		float xOffset = match.board.rightMidPointX
				- (font.getBounds(str).width / 2);
		float yOffset = match.board.midPointY;
		batch.begin();
		font.draw(batch, str, xOffset, yOffset);
		batch.end();
	}

	private void renderRoundWinner(Match match) {
		font.setColor(foregroundObjectColor);
		font = Fonts.fontSmall;

		batch.begin();

		String str = match.getMatchWinner().name + " is the winner!";
		float xOffset = match.board.midPointX - (font.getBounds(str).width / 2);
		float yOffset = match.board.midPointY
				- (font.getBounds(str).height / 2);

		font.draw(batch, str, xOffset, yOffset);
		batch.end();

	}

	private void renderScores(Match match) {
		font = Fonts.fontSmall;
		font.setColor(backgroundObjectColor);

		batch.begin();
		// Draw scores
		font.draw(batch, Integer.toString(match.board.leftPlayer.score),
				match.board.leftPlayerScore.x, match.board.leftPlayerScore.y);
		font.draw(batch, Integer.toString(match.board.rightPlayer.score),
				match.board.rightPlayerScore.x, match.board.rightPlayerScore.y);
		batch.end();
	}
}