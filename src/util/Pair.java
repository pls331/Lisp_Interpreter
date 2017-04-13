package util;

/**
 * Created by lenovo1 on 2017/3/14.
 */
public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second){
        this.first = first;
        this.second = second;
    }

    public T1 getFirst(){ return this.first; }
    public T2 getSecond() { return this.second; }

    @Override
    public String toString() {
        return String.format("<%s, %s>", first.toString(), second.toString());
    }
}
