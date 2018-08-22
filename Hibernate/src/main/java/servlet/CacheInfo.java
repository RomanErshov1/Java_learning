package servlet;

public class CacheInfo {
    private int size;
    private int hits;
    private int miss;

    public CacheInfo(int size, int hits, int miss) {
        this.size = size;
        this.hits = hits;
        this.miss = miss;
    }

    public int getSize() {
        return size;
    }

    public int getHits() {
        return hits;
    }

    public int getMiss() {
        return miss;
    }
}
