package cn.com.chinalife.shop.apigateway.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RequestVo {
    // Head
    private String action;
    private String method;
    private String state;
    // Security
    private String id;
    private String name;
    private String password;
    private String uuid;
    private String session;

    private String appVersion;
    private String proVersion;
    private String enterprise;

    // QueryInfo
    private HashMap<Object, Object> info = new HashMap<>();


    public void addInfo(Object key, Object value) {
        info.put(key, value);
    }

    public String getInfo(String key) {
        return info.get(key).toString();
    }

    public Object getInfoObj(String key) {
        return info.get(key);
    }

    public String toString() {
        if (info.isEmpty()) {
            return "info is null.";
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Object, Object> entry : info.entrySet()) {
            result.append("[")
                    .append(entry.getKey())
                    .append(" - ")
                    .append(entry.getValue())
                    .append("]");
        }
        return result.toString();
    }
}
