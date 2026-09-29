import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(5)
class BiggestTest
{
	@Test
	void singleElement()
	{
		assertEquals(7, Biggest.getBig(new int[][]{{7}}));
	}

	@Test
	void biggestAtStart()
	{
		assertEquals(9, Biggest.getBig(new int[][]{{9, 2}, {3, 4}}));
	}

	@Test
	void biggestInMiddle()
	{
		assertEquals(50, Biggest.getBig(new int[][]{{1, 2, 3}, {4, 50, 6}, {7, 8, 9}}));
	}

	@Test
	void biggestAtEnd()
	{
		assertEquals(99, Biggest.getBig(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 99}}));
	}

	@Test
	void biggestInLastColumnOfFirstRow()
	{
		assertEquals(30, Biggest.getBig(new int[][]{{1, 2, 30}, {4, 5, 6}}));
	}

	@Test
	void nonSquareMoreRowsThanColumns()
	{
		assertEquals(12, Biggest.getBig(new int[][]{{1, 2}, {3, 4}, {5, 12}, {7, 8}}));
	}

	@Test
	void nonSquareMoreColumnsThanRows()
	{
		assertEquals(6, Biggest.getBig(new int[][]{{1, 2, 3, 4}, {5, 6, 0, 1}}));
	}

	@Test
	void allNegativeNumbers()
	{
		assertEquals(-2, Biggest.getBig(new int[][]{{-5, -2}, {-9, -3}}),
			"All values are negative - make sure your starting 'biggest' is not 0");
	}

	@Test
	void biggestIsZero()
	{
		assertEquals(0, Biggest.getBig(new int[][]{{-1, 0}, {-3, -4}}));
	}

	@Test
	void allValuesEqual()
	{
		assertEquals(4, Biggest.getBig(new int[][]{{4, 4}, {4, 4}}));
	}

	@Test
	void extremeValues()
	{
		assertEquals(Integer.MIN_VALUE, Biggest.getBig(new int[][]{{Integer.MIN_VALUE}}));
		assertEquals(Integer.MAX_VALUE, Biggest.getBig(new int[][]{{0, Integer.MAX_VALUE}, {5, -5}}));
	}

	@Test
	void doesNotModifyTheMatrix()
	{
		int[][] m = {{3, 8}, {1, 2}};
		assertEquals(8, Biggest.getBig(m));
		assertArrayEquals(new int[]{3, 8}, m[0]);
		assertArrayEquals(new int[]{1, 2}, m[1]);
	}
}
