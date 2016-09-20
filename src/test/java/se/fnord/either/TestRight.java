package se.fnord.either;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static se.fnord.either.TestUtil.assertLeft;
import static se.fnord.either.TestUtil.assertRight;

public class TestRight {
    @Test
    public void testLeftMap() {
        assertRight("R", Either.right("R").leftMap(s -> s + "L"));
    }

    @Test
    public void testRightMap() {
        assertRight("RR", Either.right("R").map(s -> s + "R"));
        assertRight("RR", Either.right("R").rightMap(s -> s + "R"));
    }

    @Test
    public void testBiMap() {
        assertRight("RR", Either.right("R").bimap(l -> l + "L", r -> r + "R"));
    }


    @Test
    public void testRightFlatMap() {
        assertRight("RR", Either.right("R").flatMap(s -> Either.right(s + "R")));
        assertLeft("RL", Either.right("R").flatMap(s -> Either.left(s + "L")));

        assertRight("RR", Either.right("R").rightFlatMap(s -> Either.right(s + "R")));
        assertLeft("RL", Either.right("R").rightFlatMap(s -> Either.left(s + "L")));
    }

    @Test
    public void testLeftFlatMap() {
        assertRight("R", Either.right("R").leftFlatMap(s -> Either.left(s + "L")));
    }

    @Test
    public void testBiFlatMap() {
        assertRight("RR", Either.right("R").biFlatMap(l -> Either.left(l + "L"), r -> Either.right(r + "R")));
    }

    @Test
    public void testFold() {
        assertEquals("RR", Either.right("R").fold(l -> l + "L", r -> r + "R"));
    }

    @Test
    public void testIsLeft() {
        assertFalse(Either.right("R").isLeft());
    }

    @Test
    public void testIsRight() {
        assertTrue(Either.right("R").isRight());
    }

    @Test
    public void testLeftOption() {
        assertEquals(Optional.empty(), Either.right("R").leftOption());
    }
    @Test
    public void testRightOption() {
        assertEquals(Optional.of("R"), Either.right("R").option());
        assertEquals(Optional.of("R"), Either.right("R").rightOption());
    }

    @Test
    public void testLeftStream() {
        assertArrayEquals(new String[] {}, Either.right("R").leftStream().toArray(String[]::new));
    }

    @Test
    public void testRightStream() {
        assertArrayEquals(new String[] {"R"}, Either.right("R").stream().toArray(String[]::new));
        assertArrayEquals(new String[] {"R"}, Either.right("R").rightStream().toArray(String[]::new));
    }

    @Test
    public void testOrElse() {
        assertEquals("R", Either.right("R").orElse(l -> l + "R"));
        assertEquals("R", Either.right("R").orElse("R"));
    }

    @Test
    public void testSwap() {
        assertEquals(Either.left("V"), Either.right("V").swap());
    }

    @Test
    public void testToString() {
        assertEquals("right<R>", Either.right("R").toString());
    }

    @Test
    public void testEquals() {
        assertEquals(Either.right("R"), Either.right("R"));
        assertNotEquals(Either.left("L"), Either.right("R"));
        assertNotEquals(Either.right("X"), Either.right("R"));
    }

    @Test
    public void testHashCode() {
        assertEquals("R".hashCode(), Either.right("R").hashCode());
    }
}
