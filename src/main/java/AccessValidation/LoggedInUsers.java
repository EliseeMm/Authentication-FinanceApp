package AccessValidation;

import java.util.HashMap;
import java.util.UUID;

public class LoggedInUsers {

    public static HashMap<String, String> users = new HashMap<>();

    public static void addUser(UUID uuid, String accountNumber) {
        users.put(String.valueOf(uuid), accountNumber);
    }

    public static void removeUser(UUID uuid) {
        users.remove(uuid.toString());
    }

    public static boolean isUserLoggedIn(UUID uuid) {
        return users.containsKey(uuid.toString());
    }

    public static HashMap<String, String> getUsers() {
        return users;
    }

    public static String getAccountNumber(UUID uuid) {
        for (String uuidUsers : users.keySet()) {
            UUID uuidMain = UUID.fromString(uuidUsers);
            if (uuidMain.equals(uuid)) {
                return users.get(uuidUsers);
            }
        }
        return null;
    }
}
