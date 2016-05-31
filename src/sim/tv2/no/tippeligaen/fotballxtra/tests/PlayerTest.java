package sim.tv2.no.tippeligaen.fotballxtra.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sim.tv2.no.tippeligaen.fotballxtra.player.Player;

public class PlayerTest {
	private Player player;

	@Before
	public void setUp() throws Exception {
		player = new Player("11", "Sindre Moldeklev", "Manchester United", 3, 15, "2.5");
	}

	@Test
	public void testGetLastName() {
		String lastName = player.getLastName().trim();
		assertEquals("Moldeklev", lastName);
	}

}
