package com.faimio.fmwrapper.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;

import java.util.Objects;


@Slf4j
public class HaWebsocketClient extends WebSocketClient {

    public HaWebsocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("open");

    }

    @SneakyThrows
    @Override
    public void onMessage(String s) {
        log.info("data:{}", s.substring(0, 20));
        var om = new ObjectMapper();
        JsonNode node = om.readTree(s);
        String type = node.get("type").asText();
        if (Objects.equals(type, "auth_required")) {
            this.send("""
                    {
                        "type": "auth",
                        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIzMDU3MGZiMjAxN2E0M2YxODQ1OTUyNDQ0YmI4ZTI2OCIsImlhdCI6MTY3NzQ4NjI3OSwiZXhwIjoxOTkyODQ2Mjc5fQ._benzHPJzAdtHwi3CitfcHRw97LM7x5GHaZ3UNLTroo"
                    }
                    """);
        }
        if (Objects.equals(type, "result")) {
            var id = (Integer) node.get("id").asInt();
            var result = node.get("result");
            WsTask.EXECUTE_MAP.get(id).accept(result);
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
