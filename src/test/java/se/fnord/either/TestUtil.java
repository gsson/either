package se.fnord.either;

import static org.junit.Assert.assertEquals;

public class TestUtil {
    public static <T> T leftValue(Either<T, ?> either) {
        return either.leftOption().orElseThrow(() -> new AssertionError("missing left value"));
    }

    public static <T> T rightValue(Either<?, T> either) {
        return either.rightOption().orElseThrow(() -> new AssertionError("missing right value"));
    }

    public static <T> void assertLeft(T value, Either<T, ?> either) {
        assertEquals(value, leftValue(either));
    }

    public static <T> void assertRight(T value, Either<?, T> either) {
        assertEquals(value, rightValue(either));
    }
}
