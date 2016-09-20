package se.fnord.either;

@FunctionalInterface
public interface ThrowingSupplier<V> {
    V get() throws Exception;
}

