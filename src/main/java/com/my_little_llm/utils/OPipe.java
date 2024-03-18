package com.my_little_llm.utils;

import java.util.function.Consumer;
import java.util.function.Function;

public class OPipe<T> {

  public T value;

  public OPipe(T t) {
    value = t;
  }

  public static <T> OPipe<T> of(T t) {
    return new OPipe<>(t);
  }

  public <R> OPipe<R> map(Function<T, R> f) {
    return new OPipe<>(f.apply(value));
  }

  public OPipe<T> act(Consumer<T> f) {
    f.accept(value);
    return new OPipe<>(value);
  }
}
