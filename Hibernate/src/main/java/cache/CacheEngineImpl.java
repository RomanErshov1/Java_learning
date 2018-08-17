package cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {

    private static final int TIME_TRESHOLD_MS = 5;

    private final int maxElements;
    private final long idleTimeMs;
    private final long lifeTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> cache = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long idleTimeMs, long lifeTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.isEternal = isEternal || lifeTimeMs == 0 && idleTimeMs == 0;
    }

    @Override
    public void put(CacheElement<K, V> element) {
        if (cache.size() == maxElements){
            K firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
        }

        K key = element.getKey();
        cache.put(key, new SoftReference<>(element));

        if (!isEternal){
            if (lifeTimeMs != 0){
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0){
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction){
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<K, V> element = cache.get(key).get();
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())){
                    cache.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2){
        return t1 < t2 + TIME_TRESHOLD_MS;
    }

    @Override
    public CacheElement<K, V> get(K key) {
        SoftReference<CacheElement<K, V>> refEelement = cache.get(key);
        CacheElement<K, V> element = null;
        if (refEelement != null) {
            element = refEelement.get();

            if (element != null){
                hit++;
                element.setAccessed();
            }
        }
        else miss++;
        return element;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }
}
