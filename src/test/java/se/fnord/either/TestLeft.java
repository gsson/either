package se.fnord.either;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static se.fnord.either.TestUtil.assertLeft;
import static se.fnord.either.TestUtil.assertRight;

public class TestLeft {
    @Test
    public void testLeftMap() {
        assertLeft("LL", Either.left("L").leftMap(s -> s + "L"));
    }

    @Test
    public void testRightMap() {
        assertLeft("L", Either.left("L").map(s -> s + "R"));
        assertLeft("L", Either.left("L").rightMap(s -> s + "R"));
    }

    @Test
    public void testBiMap() {
        assertLeft("LL", Either.left("L").bimap(l -> l + "L", r -> r + "R"));
    }


    @Test
    public void testRightFlatMap() {
        assertLeft("L", Either.left("L").flatMap(s -> Either.left(s + "L")));
        assertLeft("L", Either.left("L").rightFlatMap(s -> Either.left(s + "L")));
    }

    @Test
    public void testLeftFlatMap() {
        assertLeft("LL", Either.left("L").leftFlatMap(s -> Either.left(s + "L")));
        assertRight("LR", Either.left("L").leftFlatMap(s -> Either.right(s + "R")));
    }

    @Test
    public void testBiFlatMap() {
        assertLeft("LL", Either.left("L").biFlatMap(l -> Either.left(l + "L"), r -> Either.right(r + "R")));
    }

    @Test
    public void testFold() {
        assertEquals("LL", Either.left("L").fold(l -> l + "L", r -> r + "R"));
    }

    @Test
    public void testIsLeft() {
        assertTrue(Either.left("L").isLeft());
    }

    @Test
    public void testIsRight() {
        assertFalse(Either.left("L").isRight());
    }

    @Test
    public void testLeftOption() {
        assertEquals(Optional.of("L"), Either.left("L").leftOption());
    }
    @Test
    public void testRightOption() {
        assertEquals(Optional.empty(), Either.left("L").option());
        assertEquals(Optional.empty(), Either.left("L").rightOption());
    }

    @Test
    public void testLeftStream() {
        assertArrayEquals(new String[] {"L"}, Either.left("L").leftStream().toArray(String[]::new));
    }

    @Test
    public void testRightStream() {
        assertArrayEquals(new String[] {}, Either.left("L").stream().toArray(String[]::new));
        assertArrayEquals(new String[] {}, Either.left("L").rightStream().toArray(String[]::new));
    }

    @Test
    public void testOrElse() {
        assertEquals("LR", Either.left("L").orElse(l -> l + "R"));
        assertEquals("R", Either.left("L").orElse("R"));
    }

    @Test
    public void testSwap() {
        assertEquals(Either.right("V"), Either.left("V").swap());
    }

    @Test
    public void testToString() {
        assertEquals("left<L>", Either.left("L").toString());
    }

    @Test
    public void testEquals() {
        assertEquals(Either.left("L"), Either.left("L"));
        assertNotEquals(Either.left("R"), Either.left("L"));
        assertNotEquals(Either.right("L"), Either.left("L"));
    }

    @Test
    public void testHashCode() {
        assertEquals("L".hashCode(), Either.left("L").hashCode());
    }
}
