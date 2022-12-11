package com.example.honeycumb.activities.pokeApi;

import com.example.honeycumb.activities.models.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeaApiService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon();
}
