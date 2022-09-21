package com.lemzeeyyy.whatsappclone.model;

public class Users {
    private String id;
    private String imageUrl;
    private String username;
    private String status;

    public Users() {
    }

    public Users(String id, String imageUrl, String username, String status) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.username = username;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
