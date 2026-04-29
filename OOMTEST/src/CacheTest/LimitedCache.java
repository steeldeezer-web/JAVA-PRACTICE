package CacheTest;

import java.util.LinkedHashMap;
import java.util.Map;

public class LimitedCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public LimitedCache(int maxSize){
        //16, начальная емкость
        //0.75f фактор загрузки ( при заполнении на 75% мапа расширяется)
        /*
        режим access order
        если false (по-умолчанию) - порядок зависи
        т от времени вставки
        если true  - порядок зависит от времени доступа. Последний вызванный
        перемещается в конец, а старый ( которым не пользовались) оказывается в начале
         */
        super(16, 0.75f,true);
        this.maxSize = maxSize;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest){
        return size() > maxSize;
    }
}
