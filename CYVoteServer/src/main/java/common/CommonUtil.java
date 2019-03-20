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

    /**
     * 将对象转化为json字符串，方便存取
     * @param content
     * @return
     */
    public static String toJsonString(Object content) {
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

    /**
     * 将json字符串转化为对象
     * @param content
     * @param clazz
     * @return
     */
    public static Object fromJsonString(String content, Class clazz){
        JSONObject json = JSONObject.fromObject(content);
        return JSONObject.toBean(json, clazz);
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

    /**
     * 这个方法用来校验at使用
     * @param username
     * @param accessToken
     * @return
     */
    public static boolean checkAT(String username, String accessToken){

        return true;
    }
}
