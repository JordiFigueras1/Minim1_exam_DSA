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
        int i = this.tm.iniciarPartida("idUser1", "idGame1", "Partida1");
        Assert.assertEquals(0, i);
        Assert.assertEquals(50, this.tm.getListaUsuarios().get(0).getPuntosActuales());
        Assert.assertEquals(1, this.tm.getListaUsuarios().get(0).getNivelActual());
        Assert.assertEquals(true, this.tm.getListaUsuarios().get(0).isJugando());

        i = this.tm.iniciarPartida("idUser3", "idGame1", "Partida1");
        Assert.assertEquals(-1, i);

        i = this.tm.iniciarPartida("idUser2", "idGame3", "Partida1" );
        Assert.assertEquals(-2, i);

        i = this.tm.iniciarPartida("idUser1", "idGame1", "Partida2");
        Assert.assertEquals(-3, i);
    }

    @Test
    public void testConsultaNivelActual() throws Exception {
        this.tm.iniciarPartida("idUser1", "idGame1", "Partida1");

        int i = this.tm.consultaNivelActual("idUser1");
        Assert.assertEquals(1, i);

        i = this.tm.consultaNivelActual("idUser3");
        Assert.assertEquals(-1, i);

        i = this.tm.consultaNivelActual("idUser2");
        Assert.assertEquals(-2, i);
    }

    @Test
    public void testPasarNivel() throws Exception {
        this.tm.iniciarPartida("idUser1", "idGame1", "Partida1");

        //Pasamos de nivel 1 al 2, pasamos correctamente y retorna 0
        int i = this.tm.pasarNivel("idUser1", 50, "3/11/2023");
        Assert.assertEquals(0, i);
        //Consultamos el nivel actual
        i = this.tm.consultaNivelActual("idUser1");
        Assert.assertEquals(2, i);

        Assert.assertEquals(100, this.tm.getListaUsuarios().get(0).getPuntosActuales());

        //Pasamos de nivel
        this.tm.pasarNivel("idUser1", 50, "4/11/2023");
        this.tm.pasarNivel("idUser1", 50, "5/11/2023");
        //Miramos si se actualiza el nivel bien
        i = this.tm.consultaNivelActual("idUser1");
        Assert.assertEquals(4, i);
        //Miramos si se actualiza los puntos bien
        this.tm.pasarNivel("idUser1", 50, "6/11/2023");
        Assert.assertEquals(250, this.tm.getListaUsuarios().get(0).getPuntosActuales());
        this.tm.pasarNivel("idUser1", 50, "6/11/2023");
        this.tm.pasarNivel("idUser1", 50, "6/11/2023");
        //Miramos como actua cuando queremos pasar de nivel y estamos en el utlimo nivel y se suma +100
        //Retorna 1 porque ya estamos en el ultimo nivel
        //Tota acumulado de 500
        //El jugador pasa en estado inactivo
        i = this.tm.pasarNivel("idUser1", 50, "3/11/2023");
        Assert.assertEquals(1, i);
        Assert.assertEquals(500, this.tm.getListaUsuarios().get(0).getListaJuegos().get(0).getListaPartidas().get(this.tm.getListaUsuarios().get(0).getListaJuegos().get(0).getListaPartidas().size() - 1).getPuntos());
        Assert.assertEquals(false, this.tm.getListaUsuarios().get(0).jugando);


        //El usuario no existe y retorna -1
        i = this.tm.pasarNivel("idUser3", 50, "3/11/2023");
        Assert.assertEquals(-1, i);
        //El usuario está inactivo y retorna -2
        i = this.tm.pasarNivel("idUser2", 50, "3/11/2023");
        Assert.assertEquals(-2, i);
    }


    @Test
    public void testFinalizarPartida() throws Exception {
        this.tm.iniciarPartida("idUser1", "idGame1", "Partida1");
        Assert.assertEquals(true, this.tm.getListaUsuarios().get(0).isJugando());

        //Miramos si ha finalizado la partida
        //Return 0
        //Estado del jugador inactivo
        int i = this.tm.finalizarPartida("idUser1");
        Assert.assertEquals(0, i);
        Assert.assertEquals(false, this.tm.getListaUsuarios().get(0).isJugando());

        //Usuario no existre, return -1
        i = this.tm.finalizarPartida("idUser3");
        Assert.assertEquals(-1, i);

        //Usuario no esta activo, retrun -2
        i = this.tm.finalizarPartida("idUser2");
        Assert.assertEquals(-2, i);
    }


    @Test
    public void testConsultaActividadJuego() throws Exception {
        
        this.tm.iniciarPartida("idUser1","idGame1", "Partida1");
        this.tm.iniciarPartida("idUser2","idGame1", "Partida1");

        //Pasamos de nivel y finalizamos partida del usuario 1
        this.tm.pasarNivel("idUser1",50,"3/11/2023");
        this.tm.pasarNivel("idUser1",60,"4/11/2022");
        this.tm.finalizarPartida("idUser1");
        //Pasamos de nivel hasta que ya no hay mas niveles
        this.tm.pasarNivel("idUser2",70,"3/11/2023");
        this.tm.pasarNivel("idUser2",80,"4/11/2023");
        this.tm.pasarNivel("idUser2",60,"4/11/2023");
        this.tm.pasarNivel("idUser2",50,"4/11/2023");
        this.tm.pasarNivel("idUser2",40,"4/11/2023");
        this.tm.finalizarPartida("idUser2");

        this.tm.iniciarPartida("idUser2","idGame1", "Partida2");
        this.tm.pasarNivel("idUser2",70,"3/11/2023");
        this.tm.finalizarPartida("idUser2");

        this.tm.iniciarPartida("idUser1","idGame2", "Partida1");
        this.tm.iniciarPartida("idUser2","idGame2", "Partida1");

        this.tm.pasarNivel("idUser1",500,"5/11/2023");
        this.tm.finalizarPartida("idUser1");
        this.tm.finalizarPartida("idUser2");

        //Miramos si nos retorna correctamente las actividades
        List<Partida> listP=this.tm.consultaActividadJuego("idUser1","idGame1");
        Assert.assertEquals(1,listP.size());
        Assert.assertEquals("idGame1",listP.get(0).getIdJuego());
        Assert.assertEquals(160,listP.get(0).getPuntos());

        listP=this.tm.consultaActividadJuego("idUser2","idGame1");
        Assert.assertEquals(2,listP.size());
        Assert.assertEquals("idGame1",listP.get(1).getIdJuego());
        Assert.assertEquals(120,listP.get(1).getPuntos());

        listP=this.tm.consultaActividadJuego("idUser1","idGame2");
        Assert.assertEquals(1,listP.size());
        Assert.assertEquals("idGame2",listP.get(0).getIdJuego());
        Assert.assertEquals(550,listP.get(0).getPuntos());

        listP=this.tm.consultaActividadJuego("idUser2","idGame2");
        Assert.assertEquals(0,listP.size());
        Assert.assertEquals(50,this.tm.getListaUsuarios().get(1).getPuntosActuales());

    }

}
