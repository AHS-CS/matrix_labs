import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * The spiral starts with 1 in the top-left corner and winds COUNTER-clockwise toward
 * the center: down the left side, across the bottom, up the right side, back across
 * the top, and so on.
 *
 *   size 3:    1 8 7     size 4:    1 12 11 10
 *              2 9 6                2 13 16  9
 *              3 4 5                3 14 15  8
 *                                   4  5  6  7
 */
@Timeout(5)
class SpiralMatrixTest
{
	private static final int[][] SIZE_1 = {{1}};
	private static final int[][] SIZE_2 = {{1, 4}, {2, 3}};
	private static final int[][] SIZE_3 = {{1, 8, 7}, {2, 9, 6}, {3, 4, 5}};
	private static final int[][] SIZE_4 = {
		{1, 12, 11, 10},
		{2, 13, 16, 9},
		{3, 14, 15, 8},
		{4, 5, 6, 7}};
	private static final int[][] SIZE_5 = {
		{1, 16, 15, 14, 13},
		{2, 17, 24, 23, 12},
		{3, 18, 25, 22, 11},
		{4, 19, 20, 21, 10},
		{5, 6, 7, 8, 9}};
	private static final int[][] SIZE_6 = {
		{1, 20, 19, 18, 17, 16},
		{2, 21, 32, 31, 30, 15},
		{3, 22, 33, 36, 29, 14},
		{4, 23, 34, 35, 28, 13},
		{5, 24, 25, 26, 27, 12},
		{6, 7, 8, 9, 10, 11}};

	/** Reads the printed spiral back into a 2D array (ignores spacing and blank lines). */
	private static int[][] parse(SpiralMatrix s)
	{
		List<String> lines = TestUtil.lines(s.toString());
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

	private static void assertSpiral(int[][] expected, SpiralMatrix s)
	{
		int[][] actual = parse(s);
		assertEquals(expected.length, actual.length, "Wrong number of rows in:\n" + s);
		for (int r = 0; r < expected.length; r++)
			assertArrayEquals(expected[r], actual[r], "Row " + r + " is wrong in:\n" + s);
	}

	private static SpiralMatrix viaConstructor(int size)
	{
		SpiralMatrix s = new SpiralMatrix(size);
		s.createSpiral();
		return s;
	}

	private static SpiralMatrix viaSetSize(int size)
	{
		SpiralMatrix s = new SpiralMatrix();
		s.setSize(size);
		s.createSpiral();
		return s;
	}

	// ---- constructor with a size ----
	@Test void constructorSize1() { assertSpiral(SIZE_1, viaConstructor(1)); }
	@Test void constructorSize2() { assertSpiral(SIZE_2, viaConstructor(2)); }
	@Test void constructorSize3() { assertSpiral(SIZE_3, viaConstructor(3)); }
	@Test void constructorSize4() { assertSpiral(SIZE_4, viaConstructor(4)); }
	@Test void constructorSize5() { assertSpiral(SIZE_5, viaConstructor(5)); }
	@Test void constructorSize6() { assertSpiral(SIZE_6, viaConstructor(6)); }

	// ---- default constructor + setSize ----
	@Test void setSize3() { assertSpiral(SIZE_3, viaSetSize(3)); }
	@Test void setSize4() { assertSpiral(SIZE_4, viaSetSize(4)); }
	@Test void setSize5() { assertSpiral(SIZE_5, viaSetSize(5)); }

	@Test
	void setSizeCanResizeAnExistingSpiral()
	{
		SpiralMatrix s = viaConstructor(3);
		assertSpiral(SIZE_3, s);
		s.setSize(5);
		s.createSpiral();
		assertSpiral(SIZE_5, s);
		s.setSize(2);
		s.createSpiral();
		assertSpiral(SIZE_2, s);
	}

	@Test
	void createSpiralTwiceGivesSameResult()
	{
		SpiralMatrix s = viaConstructor(4);
		s.createSpiral();
		assertSpiral(SIZE_4, s);
	}

	@Test
	void everyNumberAppearsExactlyOnce()
	{
		for (int size = 1; size <= 12; size++)
		{
			int[][] grid = parse(viaConstructor(size));
			assertEquals(size, grid.length, "Size " + size + ": wrong number of rows");
			boolean[] seen = new boolean[size * size + 1];
			for (int[] row : grid)
			{
				assertEquals(size, row.length, "Size " + size + ": wrong number of columns");
				for (int v : row)
				{
					assertTrue(v >= 1 && v <= size * size, "Size " + size + ": bad value " + v);
					assertFalse(seen[v], "Size " + size + ": " + v + " appears more than once");
					seen[v] = true;
				}
			}
		}
	}

	@Test
	void largeOddAndEvenSpiralsStartAtOneAndEndInTheCenter()
	{
		int[][] odd = parse(viaConstructor(9));
		assertEquals(1, odd[0][0]);
		assertEquals(81, odd[4][4]);
		int[][] even = parse(viaConstructor(10));
		assertEquals(1, even[0][0]);
		assertEquals(100, even[4][5]);
	}
}
