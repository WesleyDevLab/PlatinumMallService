package Plat.Hibernate.Util;

/**
 * Created by MontaserQasem on 1/15/17.
 */
public class ResponseMessage {
    private String responseMessage;

    public ResponseMessage(String responseMessage) {
        String flag = "{\"response\": \"" + responseMessage + "\"}";
        this.responseMessage = flag;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public static String createSimpleObject(String key, String value) {
        return "{\"" + key + "\":\"" + value + "\"}";
    }
}
