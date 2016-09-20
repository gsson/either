package se.fnord.either;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;

public class Examples {
    @Test
    public void parseIntFilterFailing() {
        String[] strings = { "0", "1", "2", "Fnord" };

        int[] ints = Stream.of(strings)
                .map(EitherOps.fromFunction(Integer::parseInt))
                .flatMap(Either::stream)
                .mapToInt(Integer::intValue)
                .toArray();

        assertArrayEquals(ints, new int[] {0, 1, 2});
    }

    @Test
    public void parseIntReplaceFailing() {
        String[] strings = { "0", "1", "2", "Fnord" };

        int[] ints = Stream.of(strings)
                .map(EitherOps.fromFunction(Integer::parseInt))
                .map(EitherOps.orElse(e -> -1))
                .mapToInt(Integer::intValue)
                .toArray();

        assertArrayEquals(ints, new int[] {0, 1, 2, -1});
    }
}
