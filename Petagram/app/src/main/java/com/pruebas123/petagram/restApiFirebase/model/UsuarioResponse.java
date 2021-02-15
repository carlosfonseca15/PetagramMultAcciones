package com.pruebas123.petagram.restApiFirebase.model;

public class UsuarioResponse {
    private String id;
    private String token;
    private String userid;

    public UsuarioResponse(String id, String token, String userid) {
        this.id = id;
        this.token = token;
        this.userid = userid;
    }

    public UsuarioResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
