package se.fnord.either;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static se.fnord.either.TestUtil.*;

public class TestEitherOps {
    @Test
    public void testFromOption() {
        Function<Optional<? extends String>, Either<String, String>> f1 =
                EitherOps.fromOption("L");

        assertLeft("L", f1.apply(Optional.empty()));
        assertRight("R", f1.apply(Optional.of("R")));

        Function<Optional<? extends String>, Either<String, String>> f2 =
                EitherOps.fromOption(() -> "L");

        assertLeft("L", f2.apply(Optional.empty()));
        assertRight("R", f2.apply(Optional.of("R")));
    }

    @Test
    public void testFromSupplier() {
        Supplier<Either<RuntimeException, String>> f1 =
                EitherOps.fromSupplier(() -> "R");

        assertRight("R", f1.get());

        Supplier<Either<RuntimeException, String>> f2 =
                EitherOps.fromSupplier(() -> { throw new RuntimeException("L"); });

        assertThat(leftValue(f2.get()), CoreMatchers.isA(RuntimeException.class));
    }

    @Test
    public void testLeftMap() {
        EitherF<String, String, String, String> f = EitherOps.leftMap(s -> s + "L");

        assertLeft("LL", f.apply(Either.left("L")));
        assertRight("R", f.apply(Either.right("R")));
    }

    @Test
    public void testRightMap() {
        EitherF<String, String, String, String> f1 = EitherOps.rightMap(s -> s + "R");

        assertLeft("L", f1.apply(Either.left("L")));
        assertRight("RR", f1.apply(Either.right("R")));

        EitherF<String, String, String, String> f2 = EitherOps.map(s -> s + "R");

        assertLeft("L", f2.apply(Either.left("L")));
        assertRight("RR", f2.apply(Either.right("R")));
    }

    @Test
    public void testBiMap() {
        EitherF<String, String, String, String> f = EitherOps.bimap(l -> l + "L", r -> r + "R");

        assertLeft("LL", f.apply(Either.left("L")));
        assertRight("RR", f.apply(Either.right("R")));
    }

    @Test
    public void testRightFlatMap() {
        EitherF<String, String, String, String> f1 = EitherOps.rightFlatMap(s -> Either.right(s + "R"));

        assertLeft("L", f1.apply(Either.left("L")));
        assertRight("RR", f1.apply(Either.right("R")));

        EitherF<String, String, String, String> f2 = EitherOps.flatMap(s -> Either.right(s + "R"));

        assertLeft("L", f2.apply(Either.left("L")));
        assertRight("RR", f2.apply(Either.right("R")));
    }

    @Test
    public void testLeftFlatMap() {
        EitherF<String, String, String, String> f1 = EitherOps.leftFlatMap(s -> Either.left(s + "L"));

        assertLeft("LL", f1.apply(Either.left("L")));
        assertRight("R", f1.apply(Either.right("R")));
    }

    @Test
    public void testBiFlatMap() {
        EitherF<String, String, String, String> f = EitherOps.biFlatMap(l -> Either.left(l + "L"), r -> Either.right(r + "R"));

        assertLeft("LL", f.apply(Either.left("L")));
        assertRight("RR", f.apply(Either.right("R")));
    }

    @Test
    public void testFold() {
        FoldF<String, String, String> f = EitherOps.fold(l -> l + "L", r -> r + "R");
        assertEquals("LL", f.apply(Either.left("L")));
        assertEquals("RR", f.apply(Either.right("R")));
    }

    @Test
    public void testOrElse() {
        RightF<String, String> f1 = EitherOps.orElse(l -> l + "V");
        assertEquals("LV", f1.apply(Either.left("L")));
        assertEquals("R", f1.apply(Either.right("R")));

        RightF<String, String> f2 = EitherOps.orElse("V");
        assertEquals("V", f2.apply(Either.left("L")));
        assertEquals("R", f2.apply(Either.right("R")));
    }
}
