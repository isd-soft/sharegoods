package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.common.Constants;
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

    public void addOnlineUser(User user, String sessionId) {
        if (!ChatService.onlineUsers.containsValue(user.getId())) {
            ChatService.onlineUsers.put(sessionId, user.getId());
            System.out.println("User " + user.getId() + " added to Online Users Map. Current Map: " + ChatService.onlineUsers);
        } else {
            System.out.println("User " + user.getId() + " already exists in Online Users Map");
        }
        updateUserStatus(user.getId(), Constants.userOnline);
    }

    public void removeOnlineUser(String sessionId) {
        Long userId = ChatService.onlineUsers.get(sessionId);
        ChatService.onlineUsers.remove(sessionId);
        updateUserStatus(userId, Constants.userOffline);

        System.out.println("User " + userId + " with SessionID " + sessionId + " removed from Online User Map. Current Map: " + ChatService.onlineUsers);

        // Will never delete rooms.
        // If no server-side sessions then should have some timeout, e.g. scheduled worker to clean the rooms
        // removeAllRooms(userId);
    }

    public void addChatRoom(Long userId, Long otherUserId) {
        User sender = userService.getUserById(userId);
        User receiver = userService.getUserById(otherUserId);

        long leftLimit = 1L;
        long rightLimit = 100000000L;
        chatRoomId = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        ChatRoom newChatRoom = new ChatRoom(chatRoomId, sender, receiver);

        if (!checkIfRoomExists(newChatRoom)) {
            ChatService.openChatRooms.add(newChatRoom);
            System.out.println("New chat room " + newChatRoom.getId() + " with Sender " + newChatRoom.getSender().getId() + " and Receiver " + newChatRoom.getReceiver().getId());
        }
        chatController.sendChatRoom(newChatRoom);
    }

    public boolean checkIfRoomExists(ChatRoom newChatRoom) {
        Long newChatRoomId = newChatRoom.getId();
        Long newChatRoomFirstUserId = newChatRoom.getSender().getId();
        Long newChatRoomSecondUserId = newChatRoom.getReceiver().getId();

        Iterator i = ChatService.openChatRooms.iterator();
        while (i.hasNext()) {

            ChatRoom chatRoom = (ChatRoom) i.next();
            Long chatRoomId = chatRoom.getId();
            Long chatRoomFirstUserId = chatRoom.getSender().getId();
            Long chatRoomSecondUserId = chatRoom.getReceiver().getId();

            if (chatRoomId == newChatRoomId ||
                (chatRoomFirstUserId == newChatRoomFirstUserId && chatRoomSecondUserId == newChatRoomSecondUserId) ||
                (chatRoomFirstUserId == newChatRoomSecondUserId && chatRoomSecondUserId == newChatRoomFirstUserId)) {
                System.out.println("Room exists");
                return true;
            }
        }
        System.out.println("Room does not exist");
        return false;
    }

    public void sendUserRoomsIfExist(User user) {
        Long userId = user.getId();
        Iterator i = ChatService.openChatRooms.iterator();
        while (i.hasNext()) {
            ChatRoom chatRoom = (ChatRoom) i.next();
            System.out.println("Room ID: " + chatRoom.getId() + " with Sender " + chatRoom.getSender().getEmail() + " and Receiver " + chatRoom.getReceiver().getEmail());
            if (chatRoom.getSender().getId() == userId || chatRoom.getReceiver().getId() == userId) {
                chatController.sendChatRoom(chatRoom);
                System.out.println("Sent room " + chatRoom.getId() + " to users " + chatRoom.getSender().getEmail() + " and " + chatRoom.getReceiver().getEmail());
            }
        }
    }

    public void removeAllRooms(Long userId) {
        int index = 0;
        Long informUserId = 0L;

        for (Iterator<ChatRoom> iter = ChatService.openChatRooms.iterator(); iter.hasNext(); ) {
            // This can be moved to FOR 3rd parameter?
            ChatRoom openChatRoom = iter.next();

            if (openChatRoom.getReceiver().getId() == userId) {
                informUserId = openChatRoom.getSender().getId();
                ChatService.openChatRooms.remove(index);
                chatController.sendRemoveChatRoom(openChatRoom.getId(), informUserId);
            }

            if (openChatRoom.getSender().getId() == userId) {
                informUserId = openChatRoom.getReceiver().getId();
                ChatService.openChatRooms.remove(index);
                chatController.sendRemoveChatRoom(openChatRoom.getId(), informUserId);
            }
            index++;
            System.out.println("Rooms Updated: " + ChatService.openChatRooms);
        }
    }

    public void updateUserStatus(Long userId, String status) {

        Long informUserId = 0L;

        for (Iterator<ChatRoom> iter = ChatService.openChatRooms.iterator(); iter.hasNext(); ) {

            ChatRoom openChatRoom = iter.next();
            if (openChatRoom.getReceiver().getId() == userId) {
                informUserId = openChatRoom.getSender().getId();
                chatController.sendUserStatus(userId, status, informUserId);
            }
            if (openChatRoom.getSender().getId() == userId) {
                informUserId = openChatRoom.getReceiver().getId();
                chatController.sendUserStatus(userId, status, informUserId);
            }
            System.out.println("User: " + userId + " Status: " + status);
        }
    }

    public boolean isAuthorOnline(Long id) {
        return onlineUsers.containsValue(id);
    }

    public User getUserBySessionId(String sessionId) {
        Long userId = onlineUsers.get(sessionId);
        UserService userService = new UserService();
        return userService.getUserById(userId);
    }

}
