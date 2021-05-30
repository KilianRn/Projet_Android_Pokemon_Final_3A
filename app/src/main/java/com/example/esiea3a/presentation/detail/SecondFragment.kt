package com.example.esiea3a.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.esiea3a.R
import com.example.esiea3a.databinding.FragmentSecondBinding
import com.example.esiea3a.presentation.Singletons
import com.example.esiea3a.presentation.api.PokemonDetailResponse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var textViewName: TextView

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_PokemonListFragment)
        }

         */
        textViewName = view.findViewById(R.id.pokemon_detail_name)
        callApi()
    }

    private fun callApi() {

        val id = arguments?.getInt("pokemonId") ?: -1
        Singletons.pokeApi.getPokemonDetail(id).enqueue(object : retrofit2.Callback<PokemonDetailResponse>{
            override fun onResponse(call: Call<PokemonDetailResponse>, response: Response<PokemonDetailResponse>) {

                if(response.isSuccessful && response.body() != null){

                    textViewName.text = response.body()!!.name
                }

            }

            override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}