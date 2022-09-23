package com.lemzeeyyy.whatsappclone.model;

public class Chats {
    private String senderId;
    private String receiverId;
    private String message;
    private boolean isseen;

    public Chats() {
    }

    public Chats(String senderId, String receiverId, String message, boolean isseen) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.isseen = isseen;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
