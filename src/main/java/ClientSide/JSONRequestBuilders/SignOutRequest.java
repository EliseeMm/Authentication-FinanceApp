package ClientSide.JSONRequestBuilders;

import org.json.JSONObject;

public class SignOutRequest implements Requests {
    private final JSONObject request = new JSONObject();

    @Override
    public JSONObject getRequest() {
        String[] arguments = {};
        request.put("arguments", arguments);
        return request;
    }
}
