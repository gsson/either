package se.fnord.either;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;


public interface Either<L, R> {
    /**
     * Creates an Either value containing either the value returned by the supplier as the right value, or the exception thrown as the left value
     * @param supplier a {@link java.util.function.Supplier} that, when invoked, returns a value of type R or throws an exception
     * @param <V> The return type of the supplier
     * @return an Either value containing either the value returned by the supplier, or the exception thrown
     */
    static <V> Either<RuntimeException, V> fromSupplier(Supplier<? extends V> supplier) {
        try {
            return right(supplier.get());
        }
        catch (RuntimeException e) {
            return left(e);
        }
    }

    /**
     * Creates an Either value containing either the value returned by the supplier as the right value, or the exception thrown as the left value
     * @param supplier a {@link se.fnord.either.ThrowingSupplier} that, when invoked, returns a value of type R or throws an exception
     * @param <V> The return type of the supplier
     * @return an Either value containing either the value returned by the supplier, or the exception thrown
     */
    static <V> Either<Exception, V> fromSupplier(ThrowingSupplier<? extends V> supplier) {
        try {
            return right(supplier.get());
        }
        catch (Exception e) {
            return left(e);
        }
    }

    /**
     * Creates an Either value containing either the value returned by the function as the right value, or the exception thrown as the left value
     * @param function a {@link se.fnord.either.ThrowingFunction} that, when invoked with a value of type T, returns a value of type R or throws an exception
     * @param <T> The parameter type of the function
     * @param <R> The return type of the function
     * @return an Either value containing either the value returned by the supplier, or the exception thrown
     */
    static <T, R> Either<Exception, R> fromFunction(ThrowingFunction<? super T, ? extends R> function, T t) {
        try {
            return right(function.apply(t));
        }
        catch (Exception e) {
            return left(e);
        }
    }

    /**
     * Creates an Either value containing either the value returned by the function as the right value, or the exception thrown as the left value
     * @param function a {@link java.util.function.Function} that, when invoked with a value of type T, returns a value of type R or throws an exception
     * @param <T> The parameter type of the function
     * @param <R> The return type of the function
     * @return an Either value containing either the value returned by the supplier, or the exception thrown
     */
    static <T, R> Either<RuntimeException, R> fromFunction(Function<? super T, ? extends R> function, T t) {
        try {
            return right(function.apply(t));
        }
        catch (RuntimeException e) {
            return left(e);
        }
    }

    /**
     * Creates an Either value containing either the value returned by the supplier as the right value, or the exception thrown as the left value
     * @param function a {@link se.fnord.either.ThrowingBiFunction} that, when invoked with a value of type T and U, returns a value of type R or throws an exception
     * @param <T> The parameter type of the function
     * @param <U> The second parameter type of the function
     * @param <R> The return type of the function
     * @return an Either value containing either the value returned by the supplier, or the exception thrown
     */
    static <T, U, R> Either<Exception, R> fromBiFunction(ThrowingBiFunction<? super T, ? super U, ? extends R> function, T t, U u) {
        try {
            return right(function.apply(t, u));
        }
        catch (Exception e) {
            return left(e);
        }
    }

    /**
     * Creates an Either value containing either the value returned by the supplier as the right value, or the exception thrown as the left value
     * @param function a {@link java.util.function.BiFunction} that, when invoked with a value of type T and U, returns a value of type R or throws an exception
     * @param <T> The first parameter type of the function
     * @param <U> The second parameter type of the function
     * @param <R> The return type of the function
     * @return an Either value containing either the value returned by the supplier, or the exception thrown
     */
    static <T, U, R> Either<RuntimeException, R> fromBiFunction(BiFunction<? super T, ? super U, ? extends R> function, T t, U u) {
        try {
            return right(function.apply(t, u));
        }
        catch (RuntimeException e) {
            return left(e);
        }
    }

    /**
     * Creates an Either value containing either the value contained in the optional as the right value, or the value returned by the <code>orElse</code> {@link java.util.function.Supplier} as the left value.
     * @apiNote the orElse supplier must not throw an exception.
     * @param value a {@link java.util.Optional} containing the value.
     * @param orElse a {@link java.util.function.Supplier} that, when invoked, returns a value of type LL.
     * @param <LL> The type of the Either left value
     * @param <RR> The type of the Either right value
     * @return an Either value containing either the value contained in the Optional, or the value returned by the <code>orElse</code> supplier
     */
    static <LL, RR> Either<LL, RR> fromOption(Optional<? extends RR> value, Supplier<? extends LL> orElse) {
        return value.isPresent() ? right(value.get()) : left(orElse.get());
    }

    /**
     * Creates an Either value containing either the value contained in the optional as the right value, or <code>orElse</code> value as the left value.
     * @apiNote the orElse supplier must not throw an exception.
     * @param value a {@link java.util.Optional} containing the value.
     * @param orElse the fallback left value
     * @param <LL> The type of the Either left value
     * @param <RR> The type of the Either right value
     * @return an Either value containing either the value contained in the Optional, or the <code>orElse</code> value
     */
    static <LL, RR> Either<LL, RR> fromOption(Optional<? extends RR> value, LL orElse) {
        return value.isPresent() ? right(value.get()) : left(orElse);
    }

    /**
     * Creates a left Either value
     * @param value the value.
     * @param <LL> The type of the Either left value
     * @param <RR> The type of the Either right value
     * @return a left Either value
     */
    static <LL, RR> Either<LL, RR> left(LL value) {
        return new Left<>(value);
    }

    /**
     * Creates a right Either value
     * @param value the value.
     * @param <LL> The type of the Either left value
     * @param <RR> The type of the Either right value
     * @return a right Either value
     */
    static <LL, RR> Either<LL, RR> right(RR value) {
        return new Right<>(value);
    }

    default <RR> Either<L, RR> map(Function<? super R, ? extends RR> f) {
        return rightMap(f);
    }

    default <RR> Either<L, RR> rightMap(Function<? super R, ? extends RR> f) {
        return bimap(Function.identity(), f);
    }
    default <LL> Either<LL, R> leftMap(Function<? super L, ? extends LL> f) {
        return bimap(f, Function.identity());
    }

    <LL, RR> Either<LL, RR> bimap(Function<? super L, ? extends LL> fl, Function<? super R, ? extends RR> fr);

    default <RR> Either<L, RR> flatMap(Function<? super R, Either<? extends L, ? extends RR>> f) {
        return rightFlatMap(f);
    }

    <RR> Either<L, RR> rightFlatMap(Function<? super R, Either<? extends L, ? extends RR>> f);
    <LL> Either<LL, R> leftFlatMap(Function<? super L, Either<? extends LL, ? extends R>> f);

    <LL, RR> Either<LL, RR> biFlatMap(Function<? super L, Either<? extends LL, ? extends RR>> fl, Function<? super R, Either<? extends LL, ? extends RR>> fr);

    /**
     * Swaps the left and right values
     *
     * @return an Either with the left and right values swapped
     */
    Either<R, L> swap();

    <V> V fold(Function<? super L, ? extends V> fl, Function<? super R, ? extends V> fr);


    default Stream<R> stream() {
        return rightStream();
    }

    Stream<R> rightStream();
    Stream<L> leftStream();


    default Optional<R> option() {
        return rightOption();
    }

    Optional<R> rightOption();
    Optional<L> leftOption();

    R orElse(Function<? super L, ? extends R> f);
    R orElse(R f);

    boolean isLeft();
    boolean isRight();

}
