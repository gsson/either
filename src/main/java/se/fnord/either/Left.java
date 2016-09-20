package se.fnord.either;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

final class Left<L, R> implements Either<L, R> {
    private final L l;

    Left(L l) {
        this.l = l;
    }

    @Override
    public <LL, RR> Either<LL, RR> bimap(Function<? super L, ? extends LL> fl, Function<? super R, ? extends RR> fr) {
        return Either.left(fl.apply(l));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <RR> Either<L, RR> rightFlatMap(Function<? super R, Either<? extends L, ? extends RR>> f) {
        return (Either<L, RR>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <LL> Either<LL, R> leftFlatMap(Function<? super L, Either<? extends LL, ? extends R>> f) {
        return (Either<LL, R>) f.apply(l);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <LL, RR> Either<LL, RR> biFlatMap(Function<? super L, Either<? extends LL, ? extends RR>> fl, Function<? super R, Either<? extends LL, ? extends RR>> fr) {
        return (Either<LL, RR>) fl.apply(l);
    }

    @Override
    public Either<R, L> swap() {
        return Either.right(l);
    }

    @Override
    public <V> V fold(Function<? super L, ? extends V> fl, Function<? super R, ? extends V> fr) {
        return fl.apply(l);
    }

    @Override
    public Stream<R> rightStream() {
        return Stream.empty();
    }

    @Override
    public Stream<L> leftStream() {
        return Stream.of(l);
    }

    @Override
    public Optional<R> rightOption() {
        return Optional.empty();
    }

    @Override
    public Optional<L> leftOption() {
        return Optional.of(l);
    }

    @Override
    public R orElse(Function<? super L, ? extends R> f) {
        return f.apply(l);
    }

    @Override
    public R orElse(R v) {
        return v;
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public String toString() {
        return "left<" + l + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Left<?, ?> left = (Left<?, ?>) o;

        return Objects.equals(l, left.l);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(l);
    }
}
