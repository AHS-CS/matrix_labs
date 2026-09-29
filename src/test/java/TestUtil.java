import java.util.ArrayList;
import java.util.List;

/**
 * Small helpers shared by the lab tests.
 * Output-format checks ignore extra spaces and blank lines so students are not
 * penalized for harmless formatting differences.
 */
final class TestUtil
{
	private TestUtil() {}

	/** Splits on any whitespace and drops empty pieces. */
	static List<String> tokens(String s)
	{
		List<String> result = new ArrayList<>();
		for (String t : s.trim().split("\\s+"))
			if (!t.isEmpty())
				result.add(t);
		return result;
	}

	/** Non-blank lines, each trimmed. */
	static List<String> lines(String s)
	{
		List<String> result = new ArrayList<>();
		for (String line : s.split("\\R"))
			if (!line.trim().isEmpty())
				result.add(line.trim());
		return result;
	}
}
