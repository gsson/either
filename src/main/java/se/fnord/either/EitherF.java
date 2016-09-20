package se.fnord.either;

import java.util.function.Function;

@FunctionalInterface
public interface EitherF<L, R, LL, RR> extends Function<Either<L, R>, Either<LL, RR>> {
}
