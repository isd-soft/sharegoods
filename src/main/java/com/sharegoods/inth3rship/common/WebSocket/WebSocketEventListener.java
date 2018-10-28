package com.sharegoods.inth3rship.common.WebSocket;

import com.sharegoods.inth3rship.models.ChatMessage;
import com.sharegoods.inth3rship.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static java.lang.String.format;


@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ChatService chatService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection.");

        /* PIZDETS STARTS HERE */
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String headersRaw = event.getMessage().getHeaders().toString();
        int loginStartPosition = headersRaw.indexOf("login=[");
        int loginEndPosition = headersRaw.indexOf("], passcode");
        String login = headersRaw.substring(loginStartPosition + 7,loginEndPosition);
        System.out.println(login);
        /* PIZDETS ENDS HERE */

        chatService.addOnlineUser(login, accessor.getSessionId());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        chatService.removeOnlineUser(sessionId);
    }
}