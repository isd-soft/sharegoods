package com.sharegoods.inth3rship.common.WebSocket;

import com.sharegoods.inth3rship.common.Constants;
import com.sharegoods.inth3rship.models.User;
import com.sharegoods.inth3rship.services.ChatService;
import com.sharegoods.inth3rship.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection.");

        /* PIZDETS STARTS HERE */
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String headersRaw = event.getMessage().getHeaders().toString();

        /* Message Headers Example
        {simpMessageType=CONNECT_ACK, simpConnectMessage=GenericMessage [payload=byte[0],
        headers={simpMessageType=CONNECT, stompCommand=CONNECT, nativeHeaders={login=[oxana@gmail.com],
        passcode=[PROTECTED], accept-version=[1.1,1.0], heart-beat=[10000,10000]}, simpSessionAttributes={},
        simpHeartbeat=[J@2efb430e, stompCredentials=[PROTECTED], simpSessionId=subicysg}], simpSessionId=subicysg} */
        System.out.println(headersRaw);

        int loginStartPosition = headersRaw.indexOf("login=[");
        int loginEndPosition = headersRaw.indexOf("], passcode");
        String email = headersRaw.substring(loginStartPosition + 7,loginEndPosition);
        System.out.println("New WS connection for email: " + email);
        /* PIZDETS ENDS HERE */

        User user = userService.findUserByEmail(email);
        if (user != null) {
            System.out.println("User exists");
            chatService.addOnlineUser(user, accessor.getSessionId());
            chatService.updateUserStatus(user.getId(), Constants.userOnline);
        } else {
            System.out.println("User does not exist");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        chatService.removeOnlineUser(sessionId);
    }
}
