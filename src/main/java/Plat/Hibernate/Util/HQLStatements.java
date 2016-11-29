package Plat.Hibernate.Util;

import Plat.Hibernate.Entities.ItemHits;
import Plat.Hibernate.Entities.OrderItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MontaserQasem on 11/17/16.
 */
public class HQLStatements {
    public static String FIND_BY = "from %s as db where db.%s=:value";
    public static String FIND_ALL = "from %s";
    public Map<String, String> entitiesNames = new HashMap<>();

    public static String getEntityName(Class cls) {
        if (cls == ItemHits.class) return "item_hits";
        if (cls == OrderItem.class) return "order_item";
        return cls.getSimpleName().toLowerCase();
    }
}
