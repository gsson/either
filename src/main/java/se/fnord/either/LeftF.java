package se.fnord.either;

@FunctionalInterface
public interface LeftF<L, R> extends FoldF<L, R, L> {
}
