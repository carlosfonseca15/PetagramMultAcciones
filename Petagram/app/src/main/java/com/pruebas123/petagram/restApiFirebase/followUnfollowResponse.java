package com.pruebas123.petagram.restApiFirebase;

public class followUnfollowResponse {
    private String estado;

    public followUnfollowResponse() {
    }

    public followUnfollowResponse(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

