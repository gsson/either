package se.fnord.either;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

final class Right<L, R> implements Either<L, R> {
    private final R r;

    Right(R r) {
        this.r = r;
    }

    @Override
    public <LL, RR> Either<LL, RR> bimap(Function<? super L, ? extends LL> fl, Function<? super R, ? extends RR> fr) {
        return Either.right(fr.apply(r));
    }

    @Override
    public Either<R, L> swap() {
        return Either.left(r);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <RR> Either<L, RR> rightFlatMap(Function<? super R, Either<? extends L, ? extends RR>> f) {
        return (Either<L, RR>) f.apply(r);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <LL> Either<LL, R> leftFlatMap(Function<? super L, Either<? extends LL, ? extends R>> f) {
        return (Either<LL, R>) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <LL, RR> Either<LL, RR> biFlatMap(Function<? super L, Either<? extends LL, ? extends RR>> fl, Function<? super R, Either<? extends LL, ? extends RR>> fr) {
        return (Either<LL, RR>) fr.apply(r);
    }

    @Override
    public <V> V fold(Function<? super L, ? extends V> fl, Function<? super R, ? extends V> fr) {
        return fr.apply(r);
    }

    @Override
    public Stream<R> rightStream() {
        return Stream.of(r);
    }

    @Override
    public Stream<L> leftStream() {
        return Stream.empty();
    }

    @Override
    public Optional<R> rightOption() {
        return Optional.of(r);
    }

    @Override
    public Optional<L> leftOption() {
        return Optional.empty();
    }

    @Override
    public R orElse(Function<? super L, ? extends R> f) {
        return r;
    }

    @Override
    public R orElse(R v) {
        return r;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public String toString() {
        return "right<" + r + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Right<?, ?> right = (Right<?, ?>) o;

        return Objects.equals(r, right.r);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(r);
    }
}
