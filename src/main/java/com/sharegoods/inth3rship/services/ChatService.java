package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.controllers.ChatController;
import com.sharegoods.inth3rship.models.ChatRoom;
import com.sharegoods.inth3rship.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatController chatController;

    private Long chatRoomId = 0L;
    private static List<ChatRoom> openChatRooms = new CopyOnWriteArrayList<>();
    private static Map<String, Long> onlineUsers = new HashMap<>();

    public void addOnlineUser(String userEmail, String sessionId) {
        User user = userService.findUserByEmail(userEmail);
        ChatService.onlineUsers.put(sessionId, user.getId());
        System.out.println("Users Online Updated: " + ChatService.onlineUsers);
    }

    public void removeOnlineUser(String sessionId) {
        Long userId = onlineUsers.get(sessionId);
        onlineUsers.remove(sessionId);
        System.out.println("Users Online Updated: " + ChatService.onlineUsers);

        removeAllRooms(userId);
    }

    public void addChatRoom(Long userId, Long itemAuthorId) {
        User sender = userService.getUserById(userId);
        User receiver = userService.getUserById(itemAuthorId);

        ChatRoom newChatRoom = new ChatRoom(++chatRoomId, sender, receiver);
        ChatService.openChatRooms.add(newChatRoom);
        chatController.sendChatRoom(newChatRoom);
        System.out.println("New chat room:" +  newChatRoom.getId());
        System.out.println("with sender: " +  newChatRoom.getSender().getId());
        System.out.println("with receiver: " +  newChatRoom.getReceiver().getId());
    }

    public void removeAllRooms(Long userId) {
        int index = 0;
        Long informUserId = 0L;

        for(Iterator<ChatRoom> iter = ChatService.openChatRooms.iterator(); iter.hasNext();)
        {
            ChatRoom openChatRoom = iter.next();

            if(openChatRoom.getReceiver().getId() == userId) {
                informUserId = openChatRoom.getSender().getId();
                ChatService.openChatRooms.remove(index);
                chatController.sendRemoveChatRoom(openChatRoom.getId(),informUserId);
            }

            if(openChatRoom.getSender().getId() == userId) {
                informUserId = openChatRoom.getReceiver().getId();
                ChatService.openChatRooms.remove(index);
                chatController.sendRemoveChatRoom(openChatRoom.getId(),informUserId);
            }
            index++;
            System.out.println("Rooms Updated: " + ChatService.openChatRooms);
        }
    }

    public boolean isAuthorOnline(Long id) {
        return onlineUsers.containsValue(id);
    }

 }
