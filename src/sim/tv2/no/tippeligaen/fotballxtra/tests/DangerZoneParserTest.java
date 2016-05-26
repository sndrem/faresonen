package sim.tv2.no.tippeligaen.fotballxtra.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sim.tv2.no.tippeligaen.fotballxtra.parser.DangerZoneParser;

public class DangerZoneParserTest {
	private DangerZoneParser parser = new DangerZoneParser();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetNextMatches() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDangerZonePlayers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTopscorers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTable() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEvenNumber() {
		int evenNumber = 2;
		assertTrue(parser.isEvenNumber(evenNumber));
		
		evenNumber = 3;
		assertFalse(parser.isEvenNumber(evenNumber));
	}

}
