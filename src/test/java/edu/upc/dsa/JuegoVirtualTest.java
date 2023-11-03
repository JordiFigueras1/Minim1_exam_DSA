package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JuegoVirtualTest {
    JuegoVirtual tm;

    @Before
    public void setUP() {
        tm = new JuegoVirtualImpl();

        tm.crearJuego("idGame1", "Primer Juego, consta de 7 niveles", 7);
        tm.crearJuego("idGame2", "Segundo Juego, consta de 2 niveles", 2);

        tm.addUsuario("idUser1");
        tm.addUsuario("idUser2");

    }

    @After
    public void tearDown() {
        this.tm = null;
    }

    @Test
    public void testBefore() {
        //Analizar tamaños
        Assert.assertEquals(2, this.tm.getListaUsuarios().size());
        Assert.assertEquals(2, this.tm.getListaJuegos().size());
        //Analizar IDs
        Assert.assertEquals("idUser1", this.tm.getListaUsuarios().get(0).getIdUsuario());
        Assert.assertEquals("idUser2", this.tm.getListaUsuarios().get(1).getIdUsuario());
        Assert.assertEquals("idGame1", this.tm.getListaJuegos().get(0).getIDjuego());
        Assert.assertEquals("idGame2", this.tm.getListaJuegos().get(1).getIDjuego());
        //Analizar niveles
        Assert.assertEquals(7, this.tm.getListaJuegos().get(0).getNumNivel());
        Assert.assertEquals(2, this.tm.getListaJuegos().get(1).getNumNivel());
    }

    @Test
    public void testIniciarPartida() throws Exception {
        int i = this.tm.iniciarPartida("idUser1", "idGame1");
        Assert.assertEquals(0, i);
        Assert.assertEquals(50, this.tm.getListaUsuarios().get(0).getPuntosActuales());
        Assert.assertEquals(1, this.tm.getListaUsuarios().get(0).getNivelActual());
        Assert.assertEquals(true, this.tm.getListaUsuarios().get(0).isJugando());

        i = this.tm.iniciarPartida("idUser3", "idGame1");
        Assert.assertEquals(-1, i);

        i = this.tm.iniciarPartida("idUser2", "idGame3");
        Assert.assertEquals(-2, i);

        i = this.tm.iniciarPartida("idUser1", "idGame1");
        Assert.assertEquals(-3, i);
    }

    @Test
    public void testConsultaNivelActual() throws Exception {
        this.tm.iniciarPartida("idUser1", "idGame1");

        int i = this.tm.consultaNivelActual("idUser1");
        Assert.assertEquals(1, i);

        i = this.tm.consultaNivelActual("idUser3");
        Assert.assertEquals(-1, i);

        i = this.tm.consultaNivelActual("idUser2");
        Assert.assertEquals(-2, i);
    }

}
