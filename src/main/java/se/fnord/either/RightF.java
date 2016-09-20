package se.fnord.either;

@FunctionalInterface
public interface RightF<L, R> extends FoldF<L, R, R> {
}
