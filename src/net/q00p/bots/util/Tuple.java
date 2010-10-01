package net.q00p.bots.util;

public class Tuple<T1, T2> {
  private final T1 first;
  private final T2 second;

  public Tuple(T1 first, T2 second) {
    this.first = first;
    this.second = second;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Tuple)) return false;
    Tuple t = (Tuple) o;
    return first.equals(t.first) && second.equals(t.second);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 37 * result + first.hashCode();
    result = 37 * result + second.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "{" + first + ", " + second + "}";
  }
}
