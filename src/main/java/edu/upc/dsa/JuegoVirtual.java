package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;

import java.util.ArrayList;
import java.util.List;

public interface JuegoVirtual {

    public ArrayList<Usuario> getListaUsuarios();
    public ArrayList<Juego> getListaJuegos();

    public static JuegoVirtual getInstance() {
        return null;
    }

    public void addUsuario(String idUsuario);
    public void crearJuego(String idJuego, String descripcion, int numNiveles);
    public Integer iniciarPartida(String idUsuario, String idJuego) throws Exception;
    public int consultaNivelActual(String idUsuario) throws Exception;

    public int consultaPuntuacionActual(String idUsuario) throws Exception;
    public Integer pasarNivel(String idUsuario,int puntosConseguidos,String fecha);
    public Integer finalizarPartida(String idUsuario);
    public List<Usuario> consultaListaUsuariosJuego(String idJuego);
    public List<Partida> consultaActividadJuego(String idUsuario, String idJuego);


}
