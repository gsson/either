package se.fnord.either;

import java.util.function.Function;

@FunctionalInterface
public interface FoldF<L, R, V> extends Function<Either<L, R>, V> {
}
