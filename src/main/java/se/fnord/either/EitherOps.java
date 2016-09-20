package se.fnord.either;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public interface EitherOps {
    static <V> Supplier<Either<RuntimeException, V>> fromSupplier(Supplier<? extends V> supplier) {
        return () -> Either.fromSupplier(supplier);
    }

    static <V> Supplier<Either<Exception, V>> fromThrowingSupplier(ThrowingSupplier<? extends V> supplier) {
        return () -> Either.fromSupplier(supplier);
    }

    static <T, R> Function<T, Either<RuntimeException, R>> fromFunction(Function<? super T, ? extends R> function) {
        return (T t) -> Either.fromFunction(function, t);
    }

    static <T, R> Function<T, Either<Exception, R>> fromThrowingFunction(ThrowingFunction<? super T, ? extends R> function) {
        return (T t) -> Either.fromFunction(function, t);
    }

    static <T, U, R> BiFunction<T, U, Either<RuntimeException, R>> fromBiFunction(BiFunction<? super T, ? super U, ? extends R> function) {
        return (T t, U u) -> Either.fromBiFunction(function, t, u);
    }

    static <T, U, R> BiFunction<T, U, Either<Exception, R>> fromThrowingBiFunction(ThrowingBiFunction<? super T, ? super U, ? extends R> function) {
        return (T t, U u) -> Either.fromBiFunction(function, t, u);
    }

    static <L, R> Function<Optional<? extends R>, Either<L, R>> fromOption(Supplier<? extends L> orElse) {
        return o -> Either.fromOption(o, orElse);
    }

    static <L, R> Function<Optional<? extends R>, Either<L, R>> fromOption(L orElse) {
        return o -> Either.fromOption(o, orElse);
    }

    static <L, R> RightF<L, R> orElse(Function<? super L, ? extends R> f) {
        return e -> e.orElse(f);
    }

    static <L, R> RightF<L, R> orElse(R v) {
        return e -> e.orElse(v);
    }

    static <L, R, RR> EitherF<L, R, L, RR> map(Function<? super R, ? extends RR> f) {
        return e -> e.map(f);
    }

    static <L, R, RR> EitherF<L, R, L, RR> rightMap(Function<? super R, ? extends RR> f) {
        return e -> e.rightMap(f);
    }

    static <L, R, LL> EitherF<L, R, LL, R> leftMap(Function<? super L, ? extends LL> f) {
        return e -> e.leftMap(f);
    }

    static <L, R, LL, RR> EitherF<L, R, LL, RR> bimap(Function<? super L, ? extends LL> fl, Function<? super R, ? extends RR> fr) {
        return e -> e.bimap(fl, fr);
    }

    static <L, R, RR> EitherF<L, R, L, RR> flatMap(Function<? super R, Either<? extends L, ? extends RR>> f) {
        return e -> e.flatMap(f);
    }

    static <L, R, RR> EitherF<L, R, L, RR> rightFlatMap(Function<? super R, Either<? extends L, ? extends RR>> f) {
        return e -> e.rightFlatMap(f);
    }

    static <L, R, LL> EitherF<L, R, LL, R> leftFlatMap(Function<? super L, Either<? extends LL, ? extends R>> f) {
        return e -> e.leftFlatMap(f);
    }

    static <L, R, LL, RR> EitherF<L, R, LL, RR> biFlatMap(Function<? super L, Either<? extends LL, ? extends RR>> fl, Function<? super R, Either<? extends LL, ? extends RR>> fr) {
        return e -> e.biFlatMap(fl, fr);
    }

    static <L, R, V> FoldF<L, R, V> fold(Function<? super L, ? extends V> fl, Function<? super R, ? extends V> fr) {
        return e -> e.fold(fl, fr);
    }
}
