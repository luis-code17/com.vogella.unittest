package com.vogella.unittest.services;

import com.vogella.unittest.model.Movie;
import com.vogella.unittest.model.Race;
import com.vogella.unittest.model.Ring;
import com.vogella.unittest.model.TolkienCharacter;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.vogella.unittest.model.Race.HOBBIT;
import static com.vogella.unittest.model.Race.MAIA;
import static org.junit.jupiter.api.Assertions.*;

class DataServiceTest {

    DataService dataService;

    @BeforeEach
    void setup() {
        dataService = new DataService();
    }

    @Test
    void ensureThatInitializationOfTolkeinCharactorsWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        assertEquals(33, frodo.age, "Frodo should be 33");
        assertEquals("Frodo", frodo.getName(), "Frodos character has wrong name");
        assertNotEquals("Frodon", frodo.getName(), "Frodos character has wrong name");
    }

    @Test
    void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {
        TolkienCharacter fellowshipCharacter = dataService.getFellowshipCharacter("Lars");
        assertNull(fellowshipCharacter);
    }

    @Test
    void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {
        TolkienCharacter fellowshipCharacter = dataService.getFellowshipCharacter("Frodo");
        assertNotNull(fellowshipCharacter);
    }

    @Test
    void ensureThatEqualsWorksForCharaters() {
        Object jake = new TolkienCharacter("Jake", 43, HOBBIT);
        Object sameJake = jake;
        Object jakeClone = new TolkienCharacter("Jake", 12, HOBBIT);

        assertEquals(jake, sameJake);
        assertNotEquals(jake, jakeClone);
    }

    @Test
    void checkInheritance() {
        TolkienCharacter tolkienCharacter = dataService.getFellowship().get(0);
        assertFalse(Movie.class.isAssignableFrom(tolkienCharacter.getClass()));
        assertTrue(TolkienCharacter.class.isAssignableFrom(tolkienCharacter.getClass()));
    }

    @Test
    void ensureThatFrodoAndGandalfArePartOfTheFellowsip() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        TolkienCharacter gandalf = new TolkienCharacter("Gandalf", 2020, MAIA);

        assertTrue(fellowship.contains(frodo));
        assertTrue(fellowship.contains(gandalf));
    }

    @Test
    void ensureThatOneRingBearerIsPartOfTheFellowship() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        Map<Ring, TolkienCharacter> ringBearers = dataService.getRingBearers();
        assertTrue(ringBearers.values().stream().anyMatch(ringBearer -> fellowship.contains(ringBearer)));
    }

    @RepeatedTest(1000)
    @Tag("slow")
    @DisplayName("Ensure that we can call getFellowShip multiple times")
    void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
        dataService = new DataService();
        assertNotNull(dataService.getFellowship());
    }

    @Test
    void ensureOrdering() {
        // ensure that the order of the fellowship is:
        // frodo, sam, merry,pippin, gandalf,legolas,gimli,aragorn,boromir
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        assertEquals(dataService.getFellowshipCharacter("Frodo"), fellowship.get(0));
        assertEquals(dataService.getFellowshipCharacter("Sam"), fellowship.get(1));
        assertEquals(dataService.getFellowshipCharacter("Merry"), fellowship.get(2));
        assertEquals(dataService.getFellowshipCharacter("Pippin"), fellowship.get(3));
        assertEquals(dataService.getFellowshipCharacter("Gandalf"), fellowship.get(4));
        assertEquals(dataService.getFellowshipCharacter("Legolas"), fellowship.get(5));
        assertEquals(dataService.getFellowshipCharacter("Gimli"), fellowship.get(6));
        assertEquals(dataService.getFellowshipCharacter("Aragorn"), fellowship.get(7));
        assertEquals(dataService.getFellowshipCharacter("Boromir"), fellowship.get(8));

    }

    @Test
    void ensureAge() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        // test to ensure that all hobbits and men are younger than 100 years
        // also ensure that the elfs, dwars the maia are all older than 100 years

        assertTrue(fellowship.stream()
                .filter(fellow -> fellow.getRace().equals(HOBBIT) || fellow.getRace().equals(Race.MAN))
                .allMatch(fellow -> fellow.age < 100));

        assertTrue(
                fellowship
                        .stream().filter(fellow -> fellow.getRace().equals(Race.ELF)
                                || fellow.getRace().equals(Race.DWARF) || fellow.getRace().equals(Race.MAIA))
                        .allMatch(fellow -> fellow.age > 100));
    }

    @Test
    void ensureThatFellowsStayASmallGroup() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        assertThrows(IndexOutOfBoundsException.class, () -> fellowship.get(20));
    }
    @Test
    public void ensureServiceDoesNotRunToLong() {
        assertTimeout(Duration.ofSeconds(3),()-> dataService.update());
    }
}