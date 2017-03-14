package util;

/**
 * Created by lenovo1 on 2017/3/14.
 */
public class Pair<T> {
    private T first;
    private T second;

    public Pair(T first, T second){
        this.first = first;
        this.second = second;
    }

    public T getFirst(){ return this.first; }
    public T getSecond() { return this.second; }

    @Override
    public String toString() {
        return String.format("< %s, %s >", first.toString(), second.toString());
    }
}
