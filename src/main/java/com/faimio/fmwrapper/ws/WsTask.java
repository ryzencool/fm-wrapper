package com.faimio.fmwrapper.ws;

import com.faimio.fmwrapper.schedule.HaScheduler;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


@Slf4j
@Component
public class WsTask implements InitializingBean {

    public final static Map<Integer, Consumer<JsonNode>> EXECUTE_MAP = new HashMap<>();


    public static WebSocketClient CLIENT = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        WebSocketClient client = new HaWebsocketClient(new URI("ws://192.168.31.180:8123/api/websocket"));
        client.connectBlocking();
        CLIENT = client;
    }
}
