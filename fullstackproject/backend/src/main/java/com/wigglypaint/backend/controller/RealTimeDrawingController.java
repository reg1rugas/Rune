package com.wigglypaint.backend.controller;

import com.wigglypaint.backend.model.DrawingMessage;
import com.wigglypaint.backend.service.DrawingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RealTimeDrawingController {

    @Autowired
    private DrawingHistoryService historyService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/draw/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public DrawingMessage broadcastDrawing(@DestinationVariable String roomId, @Payload DrawingMessage message) {
        // Handle Clear Canvas
        if ("CLEAR".equals(message.getType())) {
            historyService.clearHistory(roomId);
        } else {
            // Persist message for future catch-up
            historyService.addMessage(roomId, message);
        }
        return message;
    }

    // New Endpoint: Clients send a message here to request full history
    @MessageMapping("/draw/{roomId}/sync")
    public void requestSync(@DestinationVariable String roomId, @Payload String clientId) {
        List<DrawingMessage> history = historyService.getHistory(roomId);
        // We only send it back to the specific client if possible, 
        // but for simplicity we can send to a private topic or just /topic/room/{roomId}/sync/{clientId}
        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/sync/" + clientId, history);
    }
}
