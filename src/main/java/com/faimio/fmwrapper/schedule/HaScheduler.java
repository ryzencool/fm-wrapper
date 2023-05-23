package com.faimio.fmwrapper.schedule;

import com.faimio.fmwrapper.common.MessageUtil;
import com.faimio.fmwrapper.service.HaService;
import com.faimio.fmwrapper.ws.WsTask;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HaScheduler {

    private final HaService haService;

    public HaScheduler(HaService haService) {
        this.haService = haService;
    }


    @Scheduled(fixedRate = 1000, initialDelay = 10000)
    public void sendWs() {
        var client = WsTask.CLIENT;
        listArea(client);
        listDevice(client);
        listEntity(client);
        listState(client);
    }

    private void listState(WebSocketClient client) {
        var msgId = MessageUtil.getId();
        WsTask.EXECUTE_MAP.put(msgId, data -> {
            if (data != null) {
                var arr = (ArrayNode) data;
                haService.batchInsertState(arr);
            }

        });
        log.info("获取state发送：{}", msgId);

        client.send("""
                   { "id": %d, 
                    "type": "get_states"
                }""".formatted(msgId));
    }

    private void listEntity(WebSocketClient client) {
        var msgId = MessageUtil.getId();
        WsTask.EXECUTE_MAP.put(msgId, data -> {
            var arr = (ArrayNode) data;
            haService.batchInsertEntity(arr);

        });
        log.info("获取enitity发送：{}", msgId);

        client.send("""
                {
                    "id": %d,
                    "type": "config/entity_registry/list"
                }""".formatted(msgId));
    }

    private void listArea(WebSocketClient client) {
        var msgId = MessageUtil.getId();
        WsTask.EXECUTE_MAP.put(msgId, data -> {
            var arr = (ArrayNode) data;

            haService.batchInsertArea(arr);

        });
        log.info("获取area发送：{}", msgId);

        client.send("""
                {
                    "id": %d, 
                    "type": "config/area_registry/list"
                }""".formatted(msgId));
    }


    private void listDevice(WebSocketClient client) {
        var msgId = MessageUtil.getId();
        WsTask.EXECUTE_MAP.put(msgId, (data) -> {
            var arr = (ArrayNode) data;

            haService.batchInsertDevice(arr);

        });
        log.info("获取device发送：{}", msgId);

        var msg = """
                {
                    "id": %d,
                    "type": "config/device_registry/list"
                }
                """.formatted(msgId);
        client.send(msg);
    }


    public void addArea() {

    }
}
