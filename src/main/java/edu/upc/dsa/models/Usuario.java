package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    String idUsuario;
    List<Juego> listaJuegos;
    public boolean jugando;
    String idJuegoActual;

    String idPartidaActual;
    int puntosActuales;
    int nivelActual;

    public Usuario(){

    }
    public Usuario(String idUsuario){
        listaJuegos = new ArrayList<Juego>();
        this.idUsuario = idUsuario;
        this.jugando=false;
    }


    public int getPuntosActuales() {
        return puntosActuales;
    }

    public void setPuntosActuales(int puntos) {
        this.puntosActuales = puntos;
    }

    public String getIdJuegoActual() {
        return idJuegoActual;
    }

    public void setIdJuegoActual(String idJuegoActual) {
        this.idJuegoActual = idJuegoActual;
    }

    public String getIdPartidaActual() {
        return idPartidaActual;
    }

    public void setIdPartidaActual(String idPartidaActual) {
        this.idPartidaActual = idPartidaActual;
    }





    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Juego> getListaJuegos() {
        return listaJuegos;
    }

    public void setListaJuegos(List<Juego> listaJuegos) {
        this.listaJuegos = listaJuegos;
    }
    public void addListaJuegos(Juego juego) {
        this.listaJuegos.add(juego);
    }

    public boolean isJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

}
