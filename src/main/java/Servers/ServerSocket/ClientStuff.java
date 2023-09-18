package Servers.ServerSocket;

import java.util.UUID;

public interface ClientStuff {

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    String getUsername();

    void setUsername(String username);

    void setUUID(UUID uuid);

    UUID getUUID();

}
