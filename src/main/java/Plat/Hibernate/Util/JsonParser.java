package Plat.Hibernate.Util;

import Plat.Hibernate.Entities.Address;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

/**
 * Created by MontaserQasem on 1/15/17.
 */
public class JsonParser {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String parse(List<DataBaseObject> objects) {
        if (objects == null || objects.size() == 0) return "[ ]";
        String result = "[";
        try {
            for (int i = 0; i < objects.size(); i++) {
                result += mapper.writeValueAsString(objects.get(i));
                if (i + 1 != objects.size())
                    result += ",";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            result += "]";
            return result;
        }
    }
}
