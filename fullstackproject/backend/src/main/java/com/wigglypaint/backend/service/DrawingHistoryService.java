package com.wigglypaint.backend.service;

import com.wigglypaint.backend.model.DrawingMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DrawingHistoryService {
    
    // Stores history of DrawingMessages per roomId
    private final Map<String, List<DrawingMessage>> roomHistories = new ConcurrentHashMap<>();

    public void addMessage(String roomId, DrawingMessage message) {
        // We only persist DRAW_START and DRAW_END or full shapes.
        // For drawing experience, usually persisting START/MOVE/END is common
        // but for high performance we might want to consolidate.
        // For now, we store everything except DRAW_MOVE and DRAW_END (which are heavy)
        // or we store consolidated strokes.
        
        // Simpler implementation: Store all DRAW_xxx types to perfectly reconstruct.
        roomHistories.computeIfAbsent(roomId, k -> Collections.synchronizedList(new ArrayList<>()))
                     .add(message);
    }

    public List<DrawingMessage> getHistory(String roomId) {
        return roomHistories.getOrDefault(roomId, Collections.emptyList());
    }

    public void clearHistory(String roomId) {
        roomHistories.remove(roomId);
    }
}
