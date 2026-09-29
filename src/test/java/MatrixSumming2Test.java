import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * sum(r, c) is the value at (r, c) plus all of its neighbors (up, down, left, right
 * and the four diagonals). Neighbors that fall off the edge of the matrix do not exist
 * and are skipped.
 *
 * These tests do not hard-code the matrix values. They read the matrix back from
 * toString() and work out the expected sum for EVERY cell, so they keep working if
 * the matrix is changed to match the handout (any size, including ones with interior cells).
 */
@Timeout(5)
class MatrixSumming2Test
{
	/** Reads the student's toString() back into a 2D array. */
	private static int[][] readMatrix(MatrixSumming2 m)
	{
		List<String> lines = TestUtil.lines(m.toString());
		assertFalse(lines.isEmpty(), "toString() should show the matrix, one row per line");
		int[][] result = new int[lines.size()][];
		for (int r = 0; r < lines.size(); r++)
		{
			List<String> tokens = TestUtil.tokens(lines.get(r));
			result[r] = new int[tokens.size()];
			for (int c = 0; c < tokens.size(); c++)
				result[r][c] = Integer.parseInt(tokens.get(c));
		}
		return result;
	}

	private static int expectedSum(int[][] a, int r, int c)
	{
		int sum = 0;
		for (int dr = -1; dr <= 1; dr++)
			for (int dc = -1; dc <= 1; dc++)
			{
				int rr = r + dr, cc = c + dc;
				if (rr >= 0 && rr < a.length && cc >= 0 && cc < a[rr].length)
					sum += a[rr][cc];
			}
		return sum;
	}

	@Test
	void toStringShowsARectangularMatrix()
	{
		int[][] a = readMatrix(new MatrixSumming2());
		for (int[] row : a)
		{
			assertTrue(row.length > 0, "Each row should have values");
			assertEquals(a[0].length, row.length, "Every row should have the same number of values");
		}
	}

	@Test
	void sumMatchesExpectedForEveryCell()
	{
		MatrixSumming2 m = new MatrixSumming2();
		int[][] a = readMatrix(m);
		for (int r = 0; r < a.length; r++)
			for (int c = 0; c < a[r].length; c++)
				assertEquals(expectedSum(a, r, c), m.sum(r, c),
					"Wrong sum(" + r + ", " + c + ") for matrix:\n" + m);
	}

	@Test
	void sumWorksInCorners()
	{
		MatrixSumming2 m = new MatrixSumming2();
		int[][] a = readMatrix(m);
		int lastR = a.length - 1, lastC = a[0].length - 1;
		assertEquals(expectedSum(a, 0, 0), m.sum(0, 0), "top-left corner");
		assertEquals(expectedSum(a, 0, lastC), m.sum(0, lastC), "top-right corner");
		assertEquals(expectedSum(a, lastR, 0), m.sum(lastR, 0), "bottom-left corner");
		assertEquals(expectedSum(a, lastR, lastC), m.sum(lastR, lastC), "bottom-right corner");
	}

	@Test
	void sumWorksOnEdgesThatAreNotCorners()
	{
		MatrixSumming2 m = new MatrixSumming2();
		int[][] a = readMatrix(m);
		boolean checked = false;
		for (int r = 0; r < a.length; r++)
			for (int c = 0; c < a[r].length; c++)
			{
				boolean rowEdge = r == 0 || r == a.length - 1;
				boolean colEdge = c == 0 || c == a[r].length - 1;
				if (rowEdge != colEdge) // on exactly one edge, so not a corner
				{
					assertEquals(expectedSum(a, r, c), m.sum(r, c), "edge cell (" + r + ", " + c + ")");
					checked = true;
				}
			}
		// a matrix with no non-corner edge cells (like 2 wide) is fine; nothing more to check
		assertTrue(checked || a.length <= 2 || a[0].length <= 2);
	}

	@Test
	void sumWorksOnInteriorCellsWhenTheyExist()
	{
		MatrixSumming2 m = new MatrixSumming2();
		int[][] a = readMatrix(m);
		for (int r = 1; r < a.length - 1; r++)
			for (int c = 1; c < a[r].length - 1; c++)
				assertEquals(expectedSum(a, r, c), m.sum(r, c), "interior cell (" + r + ", " + c + ")");
	}

	@Test
	void sumDoesNotChangeTheMatrix()
	{
		MatrixSumming2 m = new MatrixSumming2();
		String before = m.toString();
		int first = m.sum(0, 0);
		assertEquals(first, m.sum(0, 0), "Calling sum twice should give the same answer");
		assertEquals(before, m.toString(), "sum should not modify the matrix");
		assertNotEquals(0, first, "sum(0, 0) should include the cell itself");
	}
}
