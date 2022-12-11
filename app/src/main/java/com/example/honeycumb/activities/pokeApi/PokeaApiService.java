package com.example.honeycumb.activities.pokeApi;

import com.example.honeycumb.activities.models.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeaApiService {
    //obtener info de la url
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit,@Query("offset")int offset);//definir limit
    //obtine la lista de pokemon para que sea parametrizable, parametros por medio de Query y el nombre excato de la api
}
