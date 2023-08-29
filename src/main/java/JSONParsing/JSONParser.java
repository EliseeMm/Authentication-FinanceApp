package JSONParsing;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {

    private final String requestCommand;
    private final JSONArray arguments;
    public JSONParser(String request){
        JSONObject jsonRequest = new JSONObject(request);
        requestCommand = jsonRequest.getString("Request");
        arguments = jsonRequest.getJSONArray("Arguments");
    }

    public String getRequestCommand() {
        return requestCommand;
    }

    public JSONArray getArguments() {
        return arguments;
    }
}
