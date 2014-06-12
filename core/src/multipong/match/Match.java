package multipong.match;

import multipong.board.Board;
import multipong.board.BoardUpdater;
import multipong.board.boardobjects.Player;
import multipong.screens.KeyMap;
import multipong.settings.Settings;

public class Match {

	Player leftPlayer;
	Player rightPlayer;

	public Board board;

	boolean paused = true;
	boolean pauseWhenRoundWon = false;

	public float stateTime = 0;

	public Match() {
		board = new Board();
	}

	public Match(float x, float y, float width, float height) {
		recalculateBoardGeometry(x, y, width, height);
	}

	public void addLeftPlayer(KeyMap firstPlayerKeyMap) {
		leftPlayer = new Player("leftPlayer", 0, firstPlayerKeyMap.upKey,
				firstPlayerKeyMap.downKey);
		board.leftPlayer = leftPlayer;
	}

	public void addRightPlayer(KeyMap secondPlayerKeyMap) {
		rightPlayer = new Player("rightPlayer", 0, secondPlayerKeyMap.upKey,
				secondPlayerKeyMap.downKey);
		board.setPlayers(leftPlayer, rightPlayer);
		paused = true;
	}

	public boolean hasLeftPlayer() {
		return leftPlayer != null;
	}

	public boolean hasRightPlayer() {
		return rightPlayer != null;
	}

	public boolean isCountingDown() {
		return stateTime <= Settings.matchStartCountDownFrom;
	}

	public boolean isFinished() {
		if (leftPlayer == null || rightPlayer == null) {
			return false;
		}
		return leftPlayer.score == Settings.scoreToWinMatch
				|| rightPlayer.score == Settings.scoreToWinMatch;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isPlayable() {
		return board != null && leftPlayer != null && rightPlayer != null;
	}

	public void pauseWhenRoundWon() {
		pauseWhenRoundWon = true;
	}

	public void recalculateBoardGeometry(float x, float y, float width,
			float height) {
		// TODO: maybe set pads to previous place if applicable...
		board = new Board(x, y, width, height);
		board.setPlayers(leftPlayer, rightPlayer);

	}

	public void resume() {
		pauseWhenRoundWon = false;
		paused = false;
	}

	public void update(float deltaTime) {
		if (!paused) {

			stateTime += deltaTime;

			if (isCountingDown()) {
				return;
			}

			if (isFinished()) {
				return;
			}

			BoardUpdater.update(board, deltaTime);
			Player winner = BoardUpdater.getWinner(board);

			if (winner != null) {
				if (winner == leftPlayer) {
					leftPlayer.incrementScore();
					board.ball.resetWithRightPlayerDirection();
				} else {
					rightPlayer.incrementScore();
					board.ball.resetWithLeftPlayerDirection();
				}
			}
			if (winner != null && pauseWhenRoundWon) {
				paused = true;
			}
		}
	}

}
