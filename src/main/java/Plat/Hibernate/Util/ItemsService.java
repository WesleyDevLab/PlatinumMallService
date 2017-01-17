package Plat.Hibernate.Util;

import Plat.Hibernate.Entities.Categories;
import Plat.Hibernate.Entities.Items;
import Plat.Hibernate.Entities.Store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by MontaserQasem on 1/17/17.
 */
public class ItemsService {
    private static DataBaseManager manager = DataBaseManager.getInstance();

    public static List<Items> getItemsByStoreId(int storeId) {
        List<DataBaseObject> storeObject = manager.find(new RuleObject("id", HibernateUtil.EQUAL, storeId), Store.class);
        if (storeObject == null || storeObject.size() == 0)
            return null;

        List<Items> storeItems = new ArrayList<>();
        Store store = (Store) storeObject.get(0);
        for (Categories category : store.getCategories()) {
            category = (Categories) manager.initialize(category, "items");
            for (Items item : category.getItems())
                storeItems.add(item);
        }

        Collections.sort(storeItems, new Comparator<Items>() {
            @Override
            public int compare(Items o1, Items o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return storeItems;
    }

    public static boolean checkItemInStore(Items item, int storeId) {
        return item.getCategory().getStore().getId() == storeId ? true : false;
    }
}
