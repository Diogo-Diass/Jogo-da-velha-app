package br.senai.jogodavelha.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.senai.jogodavelha.R;
import br.senai.jogodavelha.databinding.FragmentInicioBinding;



public class FragmentInicio extends Fragment {
    private FragmentInicioBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
//ação do botão que leva ao fragment do jogo
        binding.botaoInicio.setOnClickListener(v ->{
            NavHostFragment.findNavController(FragmentInicio.this).navigate(R.id.action_fragmentInicio_to_fragmentJogo);
        });
        return binding.getRoot();

    }

    @Override
    public void onStart() {
        // sumir com o toolBar
        //pegar uma referencia do tipo appCompatActivity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        //oculta a actionBar
        minhaActivity.getSupportActionBar().hide();
        super.onStart();
    }
}