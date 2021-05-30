package com.example.esiea3a.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esiea3a.R
import com.example.esiea3a.databinding.FragmentFirstBinding
import com.example.esiea3a.presentation.Singletons
import com.example.esiea3a.presentation.api.PokemonResponse
import com.example.esiea3a.presentation.api.PokeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PokemonListFragment : Fragment() {

    private lateinit var recyclerView:RecyclerView
    private val adapter = PokemonAdapter(listOf(), ::onClickedPokemon)

    //private val sharedPref = activity?.getSharedPreferences("app", Context.MODE_PRIVATE)


    //private val layoutManager = LinearLayoutManager(context)

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.pokemon_recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        /*
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_PokemonListFragment_to_SecondFragment)
        }
*/
/*
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokeApi: PokeApi = retrofit.create(PokeApi::class.java)
*/
        callApi()

    }


    private fun callApi() {
        Singletons.pokeApi.getPokemonList().enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val pokemonResponse = response.body()!!
                    showList(pokemonResponse.results)
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {

            }

        })
    }

    private fun showList(pokeList: List<Pokemon>) {
        adapter.updateList(pokeList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickedPokemon(id: Int) {
        findNavController().navigate(R.id.action_PokemonListFragment_to_SecondFragment, bundleOf(
                "pokemonId" to (id + 1)
        ))
    }
}