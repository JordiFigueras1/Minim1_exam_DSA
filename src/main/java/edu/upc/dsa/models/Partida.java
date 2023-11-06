package edu.upc.dsa.models;

public class Partida {
    String idJuego;

    String idPartida;
    int nivel;
    int puntos;
    String fecha;

    public Partida(){

    }
    public Partida(String idJuego,int nivel,int puntos, String fecha, String idPartida){
        this.idJuego=idJuego;
        this.idPartida=idPartida;
        this.nivel=nivel;
        this.puntos=puntos;
        this.fecha=fecha;
    }


    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public String getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(String idPartida) {
        this.idJuego = idPartida;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
