package Plat.Hibernate.Util;


import org.hibernate.criterion.Restrictions;

/**
 * Created by MontaserQasem on 11/12/16.
 */
public class HibernateUtil {
    public static final int GREATER_THAN = 0;
    public static final int GREATER_THAN_OR_EQUAL = 1;
    public static final int LESS_THAN = 2;
    public static final int LESS_THAN_OR_EQUAL = 3;
    public static final int LIKE = 4;
    public static final int LIKE_CASE_SENSITIVE = 5;
    public static final int EQUAL = 6;

    public static Object getRestriction(RuleObject rule) {
        int operation = rule.getOperation();
        String key = rule.getKey();
        switch (operation) {
            case GREATER_THAN:
                return Restrictions.gt(key, rule.getValue());

            case GREATER_THAN_OR_EQUAL:
                return Restrictions.ge(key, rule.getValue());

            case LESS_THAN:
                return Restrictions.lt(key, rule.getValue());

            case LESS_THAN_OR_EQUAL:
                return Restrictions.le(key, rule.getValue());

            case LIKE:
                return Restrictions.like(key, ("%"+rule.getValue()+"%"));

            case LIKE_CASE_SENSITIVE:
                return Restrictions.ilike(key, ("%" + rule.getValue() + "%"));

            case EQUAL:
                return Restrictions.eq(key, rule.getValue());

            default:
                return null;
        }
    }
}