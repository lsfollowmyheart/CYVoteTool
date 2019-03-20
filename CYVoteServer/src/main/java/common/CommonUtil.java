package common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;
import java.util.Map;

public class CommonUtil {
    public static boolean isNull(String content) {
        if (content == null && content.isEmpty())
        {
            return true;
        }
        return false;
    }
    public static String toJson(Object content) {
        JSONObject json;
        JSONArray jsonArray = new JSONArray();
       if (content instanceof List) {
            jsonArray = jsonArray.fromObject(content);
            return jsonArray.toString();
        }
        else
        {
            json  = JSONObject.fromObject(content);
            return json.toString();
        }
    }
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
    }
}
