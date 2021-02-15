package com.pruebas123.petagram.restApi.model;

public class InstagramApiResponse {
    private String username;
    private int media_count;
    private Data media;
    private String id;

    public InstagramApiResponse(String username, int media_count, Data media, String id) {
        this.username = username;
        this.media_count = media_count;
        this.media = media;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMedia_count() {
        return media_count;
    }

    public void setMedia_count(int media_count) {
        this.media_count = media_count;
    }

    public Data getMedia() {
        return media;
    }

    public void setMedia(Data media) {
        this.media = media;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
