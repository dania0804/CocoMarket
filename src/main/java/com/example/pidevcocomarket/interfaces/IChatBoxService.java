package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.ChatBox;

public interface IChatBoxService {
    ChatBox addChatBox(ChatBox chatBox);
    ChatBox retrieveBoite(Integer id1, Integer id2);
}
