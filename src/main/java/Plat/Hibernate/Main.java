package Plat.Hibernate;

import Plat.Hibernate.Entities.Store;
import Plat.Hibernate.Util.DataBaseManager;

/**
 * Created by MontaserQasem on 11/29/16.
 */
public class Main {
    public static void main(String args[]) {
        DataBaseManager manager = DataBaseManager.getInstance();
        manager.find(null, Store.class);
    }
}
