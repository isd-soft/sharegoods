package com.sharegoods.inth3rship.controllers;

import com.sharegoods.inth3rship.common.WebSocket.WebSocketEventListener;
import com.sharegoods.inth3rship.models.ChatMessage;
import com.sharegoods.inth3rship.models.ChatRoom;
import com.sharegoods.inth3rship.services.ChatService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import static java.lang.String.format;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
    }


    @MessageMapping("/chat/{roomId}/join")
    public void join(@DestinationVariable String roomId, @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
    }

    public void sendChatRoom(ChatRoom chatRoom) {
        JSONObject chatRoomInfo;
        String userName = chatRoom.getSender().getFirstName() + ' ' + chatRoom.getSender().getLastName();
        String authorName = chatRoom.getReceiver().getFirstName() + ' ' + chatRoom.getReceiver().getLastName();

        JSONObject user = new JSONObject().put("id", chatRoom.getSender().getId()).put("name", userName);
        JSONObject author = new JSONObject().put("id", chatRoom.getReceiver().getId()).put("name", authorName);

        // For initiator
        chatRoomInfo = new JSONObject().put("chatRoomId", chatRoom.getId()).put("user", user).put("otherUser", author).put("type", "ADD");
        messagingTemplate.convertAndSend(format("/channel/user/%s", chatRoom.getSender().getId()), chatRoomInfo.toString());

        // For author
        chatRoomInfo = new JSONObject().put("chatRoomId", chatRoom.getId()).put("otherUser", user).put("user", author).put("type", "ADD");
        messagingTemplate.convertAndSend(format("/channel/user/%s", chatRoom.getReceiver().getId()), chatRoomInfo.toString());
    }

    public void sendRemoveChatRoom(Long chatRoomId, Long informUserId) {
        JSONObject message = new JSONObject().put("chatRoomId", chatRoomId).put("type", "REMOVE");
        messagingTemplate.convertAndSend(format("/channel/user/%s", informUserId), message.toString());

    }

    @MessageMapping("/chat/user/{userId}")
    public void personalChannel(@DestinationVariable String userId) {
        messagingTemplate.convertAndSend(format("/channel/user/%s", userId));
    }
}