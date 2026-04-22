package com.wigglypaint.backend.model;

public class DrawingMessage {
    private String roomId;
    private String senderId;
    private String type; // DRAW_START, DRAW_MOVE, DRAW_END, CLEAR
    
    private Double x;
    private Double y;
    private Double pressure;
    private String color;
    private Integer size;
    private Double seed;
    private Boolean isEraser;
    private String tool;
    private String text;
    private String font;
    private String shape;
    private String imageData;
    private Boolean isFilled;

    public DrawingMessage() {}

    // Getters and Setters
    public Boolean getIsFilled() { return isFilled; }
    public void setIsFilled(Boolean isFilled) { this.isFilled = isFilled; }
    public String getImageData() { return imageData; }
    public void setImageData(String imageData) { this.imageData = imageData; }
    public String getTool() { return tool; }
    public void setTool(String tool) { this.tool = tool; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getFont() { return font; }
    public void setFont(String font) { this.font = font; }
    public String getShape() { return shape; }
    public void setShape(String shape) { this.shape = shape; }
    public Double getOpacity() { return opacity; }
    public void setOpacity(Double opacity) { this.opacity = opacity; }
    public Boolean getIsEraser() { return isEraser; }
    public void setIsEraser(Boolean isEraser) { this.isEraser = isEraser; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }
    public Double getPressure() { return pressure; }
    public void setPressure(Double pressure) { this.pressure = pressure; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
    public Double getSeed() { return seed; }
    public void setSeed(Double seed) { this.seed = seed; }
}
