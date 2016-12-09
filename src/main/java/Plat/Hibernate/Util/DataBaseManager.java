package Plat.Hibernate.Util;

import Plat.Hibernate.Entities.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MontaserQasem on 11/12/16.
 */
public class DataBaseManager {
    protected static DataBaseManager manager;
    protected SessionFactory sessionFactory;

    public DataBaseManager() {
    }

    public static DataBaseManager getInstance() {
        if (manager == null) {
            manager = new DataBaseManager();
            manager.init();
        }
        return manager;
    }


    private void init() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Admins.class);
        configuration.addAnnotatedClass(Brand.class);
        configuration.addAnnotatedClass(Cart.class);
        configuration.addAnnotatedClass(Categories.class);
        configuration.addAnnotatedClass(ItemHits.class);
        configuration.addAnnotatedClass(Items.class);
        configuration.addAnnotatedClass(Orders.class);
        configuration.addAnnotatedClass(OrderItem.class);
        configuration.addAnnotatedClass(Photos.class);
        configuration.addAnnotatedClass(Store.class);
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Privileges.class);
        configuration.addAnnotatedClass(WishList.class);
        configuration.addAnnotatedClass(Guests.class);
        configuration.addAnnotatedClass(ItemHits.class);
        configuration.addAnnotatedClass(Specifications.class);
        configuration.addAnnotatedClass(Log.class);
        configuration.configure();

        ServiceRegistry factory = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();


        sessionFactory = configuration.configure().buildSessionFactory(factory);
    }

    public void saveList(List<DataBaseObject> objects) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (int i = 0; i < objects.size(); i++)
                if (objects.get(i) != null)
                    session.persist(objects.get(i));
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

    }

    public void save(DataBaseObject object) {
        List<DataBaseObject> objects = new ArrayList<DataBaseObject>();
        objects.add(object);
        saveList(objects);
    }

    public void merge(DataBaseObject object) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(object);
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

    public List<DataBaseObject> findAll(List<RuleObject> rules, Class cls) {
        List<DataBaseObject> result = null;
        Transaction transaction = null;
        Query query = null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(cls);
        try {
            transaction = session.beginTransaction();
            if (rules != null) {
                for (int i = 0; i < rules.size(); i++)
                    criteria.add((Criterion) HibernateUtil.getRestriction(rules.get(i)));
                result = criteria.list();
            } else {
                query = session.createQuery("from " + HQLStatements.getEntityName(cls));
                result = query.list();
            }
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        Set<DataBaseObject> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs);

        return result;
    }


    public List<DataBaseObject> find(RuleObject rule, Class cls) {
        List<RuleObject> rules = new ArrayList<RuleObject>();
        List<DataBaseObject> result = null;
        if (rule != null) {
            rules.add(rule);
            result = findAll(rules, cls);
        } else
            result = findAll(null, cls);

        return result;
    }


    public void updateList(List<DataBaseObject> objects) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            for (int i = 0; i < objects.size(); i++)
                if (objects.get(i) != null)
                    session.update(objects.get(i));
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }

    }

    public void update(DataBaseObject object) {
        List<DataBaseObject> objects = new ArrayList<DataBaseObject>();
        objects.add(object);
        updateList(objects);
    }

    public void deleteList(List<DataBaseObject> objects) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (int i = 0; i < objects.size(); i++)
                if (objects.get(i) != null)
                    session.delete(objects.get(i));
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void delete(DataBaseObject object) {
        List<DataBaseObject> objects = new ArrayList<DataBaseObject>();
        objects.add(object);
        deleteList(objects);
    }
}
