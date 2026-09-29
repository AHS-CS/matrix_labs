import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(5)
class MagicSquare1Test
{
	// Classic 3x3 magic square (every row, column and diagonal sums to 15)
	private static final String LO_SHU = "8 1 6 3 5 7 4 9 2";
	// 4x4 magic square (everything sums to 34)
	private static final String DURER = "16 3 2 13 5 10 11 8 9 6 7 12 4 15 14 1";
	// Not magic; all sums are different, and the two diagonals differ (16 and 15)
	private static final String NOT_MAGIC = "1 2 3 4 5 6 7 8 10";

	@Test
	void sumRowAddsUpEachRow()
	{
		MagicSquare1 m = new MagicSquare1(3, NOT_MAGIC);
		assertEquals(6, m.sumRow(0));
		assertEquals(15, m.sumRow(1));
		assertEquals(25, m.sumRow(2));
	}

	@Test
	void sumColAddsUpEachColumn()
	{
		MagicSquare1 m = new MagicSquare1(3, NOT_MAGIC);
		assertEquals(12, m.sumCol(0));
		assertEquals(15, m.sumCol(1));
		assertEquals(19, m.sumCol(2));
	}

	@Test
	void sumDownDiagGoesTopLeftToBottomRight()
	{
		assertEquals(16, new MagicSquare1(3, NOT_MAGIC).sumDownDiag()); // 1 + 5 + 10
	}

	@Test
	void sumUpDiagGoesBottomLeftToTopRight()
	{
		assertEquals(15, new MagicSquare1(3, NOT_MAGIC).sumUpDiag()); // 7 + 5 + 3
	}

	@Test
	void sumsWorkOnFourByFour()
	{
		MagicSquare1 m = new MagicSquare1(4, DURER);
		assertEquals(34, m.sumRow(3));
		assertEquals(34, m.sumCol(2));
		assertEquals(34, m.sumDownDiag());
		assertEquals(34, m.sumUpDiag());
	}

	@Test
	void recognizesThreeByThreeMagicSquare()
	{
		assertTrue(new MagicSquare1(3, LO_SHU).isMagicSquare());
	}

	@Test
	void recognizesFourByFourMagicSquare()
	{
		assertTrue(new MagicSquare1(4, DURER).isMagicSquare());
	}

	@Test
	void rejectsSquareWithDifferentSums()
	{
		MagicSquare1 m = new MagicSquare1(3, NOT_MAGIC);
		assertEquals(6, m.sumRow(0)); // sanity check that the numbers loaded correctly
		assertFalse(m.isMagicSquare());
	}

	@Test
	void rejectsWhenColumnsDiffer()
	{
		// every row and both diagonals add to 9, but the columns do not
		MagicSquare1 m = new MagicSquare1(3, "1 2 6 1 2 6 1 2 6");
		assertEquals(3, m.sumCol(0));
		assertFalse(m.isMagicSquare());
	}

	@Test
	void rejectsWhenRowsDiffer()
	{
		// every column and both diagonals add to 9, but the rows do not
		MagicSquare1 m = new MagicSquare1(3, "1 1 1 2 2 2 6 6 6");
		assertEquals(3, m.sumRow(0));
		assertFalse(m.isMagicSquare());
	}

	@Test
	void rejectsWhenOnlyADiagonalDiffers()
	{
		// every row, column and the down diagonal add to 6, but the up diagonal adds to 9
		MagicSquare1 m = new MagicSquare1(3, "1 2 3 2 3 1 3 1 2");
		assertEquals(6, m.sumDownDiag());
		assertEquals(9, m.sumUpDiag());
		assertFalse(m.isMagicSquare());
	}

	@Test
	void rejectsWhenOnlyTheDownDiagonalDiffers()
	{
		// every row, column and the up diagonal add to 6, but the down diagonal adds to 9
		MagicSquare1 m = new MagicSquare1(3, "3 2 1 1 3 2 2 1 3");
		assertEquals(9, m.sumDownDiag());
		assertEquals(6, m.sumUpDiag());
		assertFalse(m.isMagicSquare());
	}

	@Test
	void toStringShowsNumbersInRowOrder()
	{
		String out = new MagicSquare1(3, LO_SHU).toString();
		assertEquals(Arrays.asList("8", "1", "6", "3", "5", "7", "4", "9", "2"), TestUtil.tokens(out));
	}

	@Test
	void toStringPutsEachRowOnItsOwnLine()
	{
		String out = new MagicSquare1(3, LO_SHU).toString();
		assertEquals(3, TestUtil.lines(out).size(), "Expected one line per row");
		assertEquals(Arrays.asList("8", "1", "6"), TestUtil.tokens(TestUtil.lines(out).get(0)));
	}
}
