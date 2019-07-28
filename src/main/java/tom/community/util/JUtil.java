package tom.community.util;

import com.alibaba.fastjson.JSONObject;

import java.sql.Statement;

public class JUtil {
    public static long SYS_USERID=0;
    public static  String getJSONString(int code){
        JSONObject json=new JSONObject();
        json.put("code",code);
        return json.toJSONString();
    }
    public static  String getJSONString(int code,String msg){
        JSONObject json=new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        return json.toJSONString();
    }
}
