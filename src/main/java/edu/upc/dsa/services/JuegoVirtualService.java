package edu.upc.dsa.services;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;

import edu.upc.dsa.JuegoVirtual;
import edu.upc.dsa.JuegoVirtualImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/JuegoVirtual", description = "Motor juego virtual DSA UPC")
@Path("/JuegoVirtual")
public class JuegoVirtualService {

    private JuegoVirtual tm;
    public JuegoVirtualService(){
        this.tm = JuegoVirtualImpl.getInstance();
        if(tm.getListaJuegos().size()==0){
            tm.crearJuego("idGame1", "Primer Juego, consta de 7 niveles", 7);
            tm.crearJuego("idGame2", "Segundo Juego, consta de 2 niveles", 2);

            tm.addUsuario("idUser1");
            tm.addUsuario("idUser2");

        }
    }

    @GET
    @ApiOperation(value = "Proporciona lista de juegos", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = JuegoVirtual.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJuegos() {

        List<Juego> listJ = this.tm.getListaJuegos();

        GenericEntity<List<Juego>> entity = new GenericEntity<List<Juego>>(listJ) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @POST
    @ApiOperation(value = "Crea un juego", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Juego.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response JuegoNuevo(Juego juego) {


        if (juego.getIDjuego()==null || juego.getDescripcionJuego()==null || juego.getNumNivel()==0)  return Response.status(500).entity(juego).build();
        this.tm.crearJuego(juego.getIDjuego(),juego.getDescripcionJuego(), juego.getNumNivel());
        return Response.status(201).entity(juego).build();
    }


}