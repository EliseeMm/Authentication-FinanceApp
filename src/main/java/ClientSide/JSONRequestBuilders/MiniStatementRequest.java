package ClientSide.JSONRequestBuilders;

import org.json.JSONObject;

public class MiniStatementRequest implements Requests {
    private final JSONObject request = new JSONObject();

    @Override
    public JSONObject getRequest() {
        String[] arguments = {};
        request.put("arguments", arguments);
        return request;
    }
}
