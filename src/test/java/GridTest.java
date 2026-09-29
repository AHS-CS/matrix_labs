import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(5)
class GridTest
{
	private static List<List<String>> rowsOf(Grid g)
	{
		List<List<String>> rows = new ArrayList<>();
		for (String line : TestUtil.lines(g.toString()))
			rows.add(TestUtil.tokens(line));
		return rows;
	}

	private static List<String> cellsOf(Grid g)
	{
		return TestUtil.tokens(g.toString());
	}

	@Test
	void toStringHasCorrectNumberOfRowsAndColumns()
	{
		Grid g = new Grid(3, 4, new String[]{"a", "b", "c"});
		List<List<String>> rows = rowsOf(g);
		assertEquals(3, rows.size(), "Expected 3 rows (one per line)");
		for (List<String> row : rows)
			assertEquals(4, row.size(), "Expected 4 values in each row");
	}

	@Test
	void rowsAndColumnsAreNotSwapped()
	{
		Grid g = new Grid(2, 7, new String[]{"x", "y"});
		List<List<String>> rows = rowsOf(g);
		assertEquals(2, rows.size());
		assertEquals(7, rows.get(0).size());
	}

	@Test
	void everyCellIsOneOfTheGivenValues()
	{
		List<String> allowed = Arrays.asList("a", "b", "c", "d");
		for (int i = 0; i < 50; i++)
		{
			Grid g = new Grid(4, 5, allowed.toArray(new String[0]));
			assertEquals(20, cellsOf(g).size(), "Expected 4 x 5 = 20 values");
			for (String cell : cellsOf(g))
				assertTrue(allowed.contains(cell), "Unexpected value in grid: " + cell);
		}
	}

	@Test
	void everyCellIsFilled()
	{
		Grid g = new Grid(5, 5, new String[]{"q"});
		assertEquals(25, cellsOf(g).size());
		for (String cell : cellsOf(g))
			assertEquals("q", cell);
	}

	@Test
	void gridIsFilledRandomly()
	{
		// With 2 values and 9 cells, always getting only one value 100 times in a row
		// is essentially impossible if the grid is really random.
		Set<String> seen = new HashSet<>();
		for (int i = 0; i < 100; i++)
			seen.addAll(cellsOf(new Grid(3, 3, new String[]{"a", "b"})));
		assertEquals(new HashSet<>(Arrays.asList("a", "b")), seen,
			"Both values should show up across many random grids");
	}

	@Test
	void findMaxWithOnlyOneValue()
	{
		String[] vals = {"only"};
		assertEquals("only", new Grid(4, 4, vals).findMax(vals));
	}

	@Test
	void findMaxOnSingleCell()
	{
		String[] vals = {"a", "b", "c"};
		Grid g = new Grid(1, 1, vals);
		assertEquals(cellsOf(g).get(0), g.findMax(vals));
	}

	@Test
	void findMaxReturnsAMostFrequentValue()
	{
		String[] vals = {"a", "b", "c", "d"};
		for (int i = 0; i < 200; i++)
		{
			Grid g = new Grid(4, 5, vals);

			Map<String, Integer> counts = new HashMap<>();
			for (String cell : cellsOf(g))
				counts.merge(cell, 1, Integer::sum);
			int best = Collections.max(counts.values());

			String result = g.findMax(vals);
			assertEquals(best, counts.getOrDefault(result, 0),
				"findMax returned '" + result + "' but the grid was:\n" + g);
		}
	}

	@Test
	void findMaxWorksOnLargeGrid()
	{
		String[] vals = {"red", "green", "blue"};
		Grid g = new Grid(20, 30, vals);
		Map<String, Integer> counts = new HashMap<>();
		for (String cell : cellsOf(g))
			counts.merge(cell, 1, Integer::sum);
		String result = g.findMax(vals);
		assertEquals(Collections.max(counts.values()), counts.getOrDefault(result, 0));
	}
}
