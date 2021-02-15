package com.pruebas123.petagram.restApi.model;

public class MediaResponse {
    private String media_url;
    private int like_count;
    private String id;

    public MediaResponse(String media_url, int like_count, String id) {
        this.media_url = media_url;
        this.like_count = like_count;
        this.id = id;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
