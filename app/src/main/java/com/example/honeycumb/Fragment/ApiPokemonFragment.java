package com.example.honeycumb.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.honeycumb.R;
import com.example.honeycumb.activities.Utils.models.AdapterApi.ListaPokemonAdapter;
import com.example.honeycumb.activities.Utils.models.Pokemon;
import com.example.honeycumb.activities.Utils.models.PokemonRespuesta;
import com.example.honeycumb.activities.pokeApi.PokeaApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApiPokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApiPokemonFragment extends Fragment {

    private Retrofit retrofit;
    private static final String TAG="POKEDEX";
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    View view;
    private int offset; // variable que trae todos los objetos del api
    private boolean aptoparacargar;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApiPokemonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FiltersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApiPokemonFragment newInstance(String param1, String param2) {
        ApiPokemonFragment fragment = new ApiPokemonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
// metodo de control de las vistas
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment //organiza la vista en el recycleview

        view=inflater.inflate(R.layout.fragment_api, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewApi);
        listaPokemonAdapter =new ListaPokemonAdapter(getContext());//si es una activiti seria this.activity
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);// trae los elementos como un mapeo, en un arreglo
        GridLayoutManager layoutManager=new GridLayoutManager(requireContext(),3);//cuantos cuadros va tener la recycleview
        recyclerView.setLayoutManager(layoutManager);

        //metodo de desplazamiento del recycle view

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {//scroll es mover de arriba abajo, se pasa un escuchador al recicle view
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy); //scroll en eje x and y
                //quien hace el scroll

                if(dy>0){
                    //necesito que sean visibles 3 item
                    int visibleItemCount= layoutManager.getItemCount(); //visualizar
                    int totalItemCount=layoutManager.getItemCount();//dimensiones
                    int pastVisibleitems=layoutManager.findFirstVisibleItemPosition();// guardar posiciones

                    if(aptoparacargar){
                        if((visibleItemCount+ pastVisibleitems) >= totalItemCount) {
                            Log.i(TAG, "final api");
                            //se llega al final del consumo pero se debe incrementar el valor del offset para que continue el consumo del api
                            aptoparacargar=false;
                            offset +=22;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });

        retrofit=new Retrofit.Builder()//se llama retrofit para consumir el api
                .baseUrl("https://pokeapi.co/api/v2/")//consulta la url
                .addConverterFactory(GsonConverterFactory.create()) //pasar a Gson para obtener respuesta//
                .build();//obtiene las respuestas

        aptoparacargar=true;//nueva consulta para permitir una sola ejecucion
        offset=0;
        obtenerDatos(offset);

        return view;
    }


//obtiene los datos de el api
    public void obtenerDatos (int offset) {
        PokeaApiService service=retrofit.create(PokeaApiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall= service.obtenerListaPokemon(22, offset);//limiti de datos traidos del api

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {//enqueue metodo exclusivo de android que maneja resultados para que android maneje las respuestas
            @Override//metodo que maneja los resultados con enqueue para que android envie las respuestas
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                aptoparacargar=true;
                if(response.isSuccessful()){//metodo si toma bien la url
                    PokemonRespuesta pokemonRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon= pokemonRespuesta.getResults();//obtiene los datos y los recorre
                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);//metodo conectado con el adapter
                    for(int i=0; i<listaPokemon.size(); i++){
                        Pokemon p=listaPokemon.get(i);//guarda los datos en modelo pokemon
                        Log.i(TAG, "pokemon"+p.getName());
                    }
                }else {
                    Log.e(TAG,"Onresponse"+response.errorBody());
                }
            }


            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {//metodo si la url no funciona
                aptoparacargar=true;
                Log.e(TAG,"onFailure"+t.getMessage());
            }
        });
    }
}