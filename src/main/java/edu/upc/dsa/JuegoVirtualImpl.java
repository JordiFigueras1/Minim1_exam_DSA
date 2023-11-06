package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class JuegoVirtualImpl implements JuegoVirtual{
    private static JuegoVirtual instance;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Juego> listaJuegos;



    public JuegoVirtualImpl(){
        this.listaUsuarios = new ArrayList<>();
        this.listaJuegos = new ArrayList<>();
    }
    public static JuegoVirtual getInstance() {
        if (instance==null) instance = new JuegoVirtualImpl();
        return instance;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return this.listaUsuarios;
    }

    public ArrayList<Juego> getListaJuegos() {
        return this.listaJuegos;
    }

    @Override
    public void crearJuego(String gameId, String description, int numLevels) {
        Juego juego = new Juego(gameId, description, numLevels);
        this.listaJuegos.add(juego);
    }

    @Override
    public void addUsuario(String userId) {
        Usuario usuario = new Usuario(userId);
        this.listaUsuarios.add(usuario);
    }
    @Override
    public Integer iniciarPartida(String userId, String gameId, String partidaId) {
        boolean userFound = false;
        boolean gameFound = false;
        int i = 0;
        while ((!userFound) && (i < listaUsuarios.size())) {
            if (Objects.equals(listaUsuarios.get(i).getIdUsuario(), userId)) {
                userFound = true;
            } else {
                i++;
            }
        }
        int x = 0;
        while ((!gameFound) && (x < listaJuegos.size())) {
            if (Objects.equals(listaJuegos.get(x).getIDjuego(), gameId)) {
                gameFound = true;
            } else {
                x++;
            }
        }
        if ((userFound) && (gameFound) && (!listaUsuarios.get(i).isJugando())) {
            Usuario user = listaUsuarios.get(i);
            user.setJugando(true);
            user.setIdJuegoActual(gameId);
            user.setIdPartidaActual(partidaId);
            user.setPuntosActuales(50);
            user.setNivelActual(1);
            Juego newGame = new Juego(gameId, listaJuegos.get(x).getDescripcionJuego(), listaJuegos.get(x).numNiveles);
            user.addListaJuegos(newGame);
            listaUsuarios.set(i, user);
            Partida partida = new Partida(gameId, 1, 50, null, partidaId);
            listaUsuarios.get(i).getListaJuegos().get(x).addListaPartidas(partida);
            return 0;
        } else if (!userFound) {
            return -1;
        } else if (!gameFound) {
            return -2;
        } else {
            return -3;
        }
    }

    @Override
    public int consultaNivelActual(String userId) {
        boolean userFound = false;
        int i = 0;
        while ((!userFound) && (i < listaUsuarios.size())) {
            if (Objects.equals(listaUsuarios.get(i).getIdUsuario(), userId)) {
                userFound = true;
            } else {
                i++;
            }
        }
        if ((userFound) && listaUsuarios.get(i).isJugando()) {
            return listaUsuarios.get(i).getNivelActual();
        } else if (!userFound) {
            return -1;
        } else {
            return -2;
        }
    }

    public int consultaPuntuacionActual(String userId) {
        boolean userFound = false;
        int i = 0;
        while ((!userFound) && (i < listaUsuarios.size())) {
            if (Objects.equals(listaUsuarios.get(i).getIdUsuario(), userId)) {
                userFound = true;
            } else {
                i++;
            }
        }
        if ((userFound) && listaUsuarios.get(i).isJugando()) {
            return listaUsuarios.get(i).getPuntosActuales();
        } else if (!userFound) {
            return -1;
        } else {
            return -2;
        }
    }


    @Override
    public Integer pasarNivel(String userId, int pointsEarned, String date) {
        boolean userFound = false;
        int i = 0;
        while ((!userFound) && (i < listaUsuarios.size())) {
            if (Objects.equals(listaUsuarios.get(i).getIdUsuario(), userId)) {
                userFound = true;
            } else {
                i++;
            }
        }
        if ((userFound) && listaUsuarios.get(i).jugando) {
            int level = listaUsuarios.get(i).getNivelActual();
            int points = listaUsuarios.get(i).getPuntosActuales() + pointsEarned;
            String currentGameId = listaUsuarios.get(i).getIdJuegoActual();
            String currentPartidaId = listaUsuarios.get(i).getIdPartidaActual();
            Usuario user = listaUsuarios.get(i);
            boolean gameFound = false;
            int x = 0;
            while ((!gameFound) && (x < user.getListaJuegos().size())) {
                if (Objects.equals(user.getListaJuegos().get(x).getIDjuego(), currentGameId)) {
                    gameFound = true;
                } else {
                    x++;
                }
            }
            int m=0;
            boolean partidaFound = false;
            while ((!partidaFound) && (m < user.getListaJuegos().get(x).getListaPartidas().size())) {
                if (Objects.equals(user.getListaJuegos().get(x).getListaPartidas().get(m).getIdPartida(), currentPartidaId)) {
                    partidaFound = true;
                } else {
                    m++;
                }
            }
            if (gameFound) {
                if (level == user.getListaJuegos().get(x).getNumNivel()) {
                    Partida partida = new Partida(currentGameId, level, points + 100, date, currentPartidaId);
                    user.getListaJuegos().get(x).getListaPartidas().set(m, partida);
                    user.setJugando(false);
                    user.setPuntosActuales(points);
                    listaUsuarios.set(i, user);
                    return 1;
                } else {
                    Partida partida = new Partida(currentGameId, level, points, date, currentPartidaId);
                    user.getListaJuegos().get(x).getListaPartidas().set(m, partida);
                    user.setNivelActual(level + 1);
                    user.setPuntosActuales(points);
                    listaUsuarios.set(i, user);
                    return 0;
                }
            } else {
                return -3;
            }
        } else if (!userFound) {
            return -1;
        } else {
            return -2;
        }
    }

    @Override
    public Integer finalizarPartida(String userId) {
        boolean userFound = false;
        int i = 0;
        while ((!userFound) && (i < listaUsuarios.size())) {
            if (Objects.equals(listaUsuarios.get(i).getIdUsuario(), userId)) {
                userFound = true;
            } else {
                i++;
            }
        }
        if ((userFound) && listaUsuarios.get(i).isJugando()) {
            Usuario user = listaUsuarios.get(i);
            user.setJugando(false);
            listaUsuarios.set(i, user);
            return 0;
        } else if (!userFound) {
            return -1;
        } else {
            return -2;
        }
    }



    public List<Usuario> consultaListaUsuariosJuego(String gameId) {
        boolean gameFound = false;
        int k = 0;
        while ((!gameFound) && (k < listaUsuarios.size())) {
            if (Objects.equals(listaJuegos.get(k).getIDjuego(), gameId)) {
                gameFound = true;
            } else {
                k++;
            }
        }
        if (gameFound) {
            List<Usuario> sortedUsers = new ArrayList<>();
            List<Integer> pointsList = new ArrayList<>();
            int y = 0;
            boolean playerAdded = false;
            while ((y < listaUsuarios.size()) && (!playerAdded)) {
                List<Juego> userGamesList = listaUsuarios.get(y).getListaJuegos();
                boolean gameFoundByUser = false;
                int x = 0;
                while ((!gameFoundByUser) && (x < userGamesList.size())) {
                    if (Objects.equals(userGamesList.get(x).getIDjuego(), gameId)) {
                        gameFoundByUser = true;
                    } else {
                        x++;
                    }
                }
                if (gameFoundByUser) {
                    sortedUsers.add(listaUsuarios.get(y));
                    List<Partida> userGamePartidas = userGamesList.get(x).getListaPartidas();
                    int points = userGamePartidas.get(userGamePartidas.size() - 1).getPuntos();
                    pointsList.add(points);
                    playerAdded = true;
                } else {
                    y++;
                }
            }
            for (int i = y + 1; i < listaUsuarios.size(); i++) {
                List<Juego> userGamesList = listaUsuarios.get(i).getListaJuegos();
                boolean gameFoundByUser = false;
                int x = 0;
                while ((!gameFoundByUser) && (x < userGamesList.size())) {
                    if (Objects.equals(userGamesList.get(x).getIDjuego(), gameId)) {
                        gameFoundByUser = true;
                    } else {
                        x++;
                    }
                }
                List<Partida> userGamePartidas = userGamesList.get(x).getListaPartidas();
                int points = userGamePartidas.get(userGamePartidas.size() - 1).getPuntos();
                boolean lower = false;
                boolean placed = false;
                int z = 0;
                while ((!lower) && (z < pointsList.size())) {
                    if (pointsList.get(z) > points) {
                        pointsList.add(z, points);
                        sortedUsers.add(z, listaUsuarios.get(y));
                        lower = true;
                        placed = true;
                    } else {
                        z++;
                    }
                }
                if (!placed) {
                    pointsList.add(z, points);
                    sortedUsers.add(z, listaUsuarios.get(y));
                }
            }
            return sortedUsers;
        } else {
            return null;
        }
    }



    @Override
    public List<Partida> consultaActividadJuego(String userId, String gameId) {
        boolean userFound = false;
        int i = 0;
        while ((!userFound) && (i < listaUsuarios.size())) {
            if (Objects.equals(listaUsuarios.get(i).getIdUsuario(), userId)) {
                userFound = true;
            } else {
                i++;
            }
        }
        if (userFound) {
            int j = 0;
            while (j < listaUsuarios.get(i).getListaJuegos().size()) {
                if (Objects.equals(listaUsuarios.get(i).getListaJuegos().get(j).getIDjuego(), gameId)) {
                    return listaUsuarios.get(i).getListaJuegos().get(j).getListaPartidas();
                } else {
                    j++;
                }
            }
        }
        return null;
    }

}
