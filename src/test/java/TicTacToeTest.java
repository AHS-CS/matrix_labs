import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Boards are 9 characters, read row by row (first 3 = top row, and so on).
 * Winner messages are checked loosely: the winning letter plus the word
 * horizontal / vertical / diagonal, and "no winner" or "cat" for a tie.
 */
@Timeout(5)
class TicTacToeTest
{
	private static void assertWin(String board, char winner, String direction)
	{
		char loser = winner == 'X' ? 'O' : 'X';
		String result = new TicTacToe(board).getWinner();
		assertNotNull(result);
		assertTrue(result.indexOf(winner) >= 0,
			"Expected " + winner + " to win for board " + board + " but got: '" + result + "'");
		assertTrue(result.indexOf(loser) < 0,
			"Expected only " + winner + " to be named for board " + board + " but got: '" + result + "'");
		assertTrue(result.toLowerCase().contains(direction),
			"Expected a " + direction + " win for board " + board + " but got: '" + result + "'");
	}

	private static void assertTie(String board)
	{
		String result = new TicTacToe(board).getWinner();
		assertNotNull(result);
		String lower = result.toLowerCase();
		assertTrue(lower.contains("no winner") || lower.contains("cat"),
			"Expected a tie for board " + board + " but got: '" + result + "'");
		assertTrue(result.indexOf('X') < 0 && result.indexOf('O') < 0,
			"A tie should not name a winner. Got: '" + result + "'");
	}

	// ---- horizontal ----
	@Test void xWinsTopRow()      { assertWin("XXXOOXXOO", 'X', "horizontal"); }
	@Test void xWinsMiddleRow()   { assertWin("OOXXXXOXO", 'X', "horizontal"); }
	@Test void xWinsBottomRow()   { assertWin("OXOXOOXXX", 'X', "horizontal"); }
	@Test void oWinsTopRow()      { assertWin("OOOXXOOXX", 'O', "horizontal"); }
	@Test void oWinsMiddleRow()   { assertWin("XXOOOOXOX", 'O', "horizontal"); }
	@Test void oWinsBottomRow()   { assertWin("XOXOXXOOO", 'O', "horizontal"); }

	// ---- vertical ----
	@Test void xWinsLeftColumn()   { assertWin("XOXXOOXXO", 'X', "vertical"); }
	@Test void xWinsMiddleColumn() { assertWin("OXOOXXXXO", 'X', "vertical"); }
	@Test void xWinsRightColumn()  { assertWin("OOXXOXOXX", 'X', "vertical"); }
	@Test void oWinsLeftColumn()   { assertWin("OXOOXXOOX", 'O', "vertical"); }
	@Test void oWinsMiddleColumn() { assertWin("XOXXOOOOX", 'O', "vertical"); }
	@Test void oWinsRightColumn()  { assertWin("XXOOXOXOO", 'O', "vertical"); }

	// ---- diagonal ----
	@Test void xWinsDownDiagonal() { assertWin("XOOOXOOXX", 'X', "diagonal"); }
	@Test void xWinsUpDiagonal()   { assertWin("OOXOXOXOO", 'X', "diagonal"); }
	@Test void oWinsDownDiagonal() { assertWin("OXXXOXXOO", 'O', "diagonal"); }
	@Test void oWinsUpDiagonal()   { assertWin("XXOXOXOXX", 'O', "diagonal"); }

	// ---- ties ----
	@Test void tieGameOne() { assertTie("XOXXOOOXX"); }
	@Test void tieGameTwo() { assertTie("XXOOOXXOX"); }

	@Test
	void getWinnerGivesSameAnswerWhenCalledTwice()
	{
		assertWin("XXXOOXXOO", 'X', "horizontal");
		TicTacToe game = new TicTacToe("XXXOOXXOO");
		assertEquals(game.getWinner(), game.getWinner());
	}

	@Test
	void differentGamesGiveDifferentResults()
	{
		String x = new TicTacToe("XXXOOXXOO").getWinner();
		String o = new TicTacToe("OOOXXOOXX").getWinner();
		String tie = new TicTacToe("XOXXOOOXX").getWinner();
		assertNotEquals(x, o);
		assertNotEquals(x, tie);
		assertNotEquals(o, tie);
	}

	// ---- toString ----
	@Test
	void toStringShowsTheBoardInThreeRows()
	{
		String out = new TicTacToe("XOXXOOOXX").toString();
		java.util.List<String> lines = TestUtil.lines(out);
		assertEquals(3, lines.size(), "Expected 3 lines, one per row");
		assertEquals("XOX", lines.get(0).replaceAll("\\s", ""));
		assertEquals("XOO", lines.get(1).replaceAll("\\s", ""));
		assertEquals("OXX", lines.get(2).replaceAll("\\s", ""));
	}

	@Test
	void toStringReflectsTheGameThatWasPassedIn()
	{
		String a = new TicTacToe("XXXOOXXOO").toString();
		String b = new TicTacToe("OOOXXOOXX").toString();
		assertNotEquals(a, b);
		assertEquals("XXXOOXXOO", a.replaceAll("\\s", ""));
	}
}
