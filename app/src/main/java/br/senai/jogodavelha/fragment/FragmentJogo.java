package br.senai.jogodavelha.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.senai.jogodavelha.R;
import br.senai.jogodavelha.databinding.FragmentJogoBinding;


public class FragmentJogo extends Fragment {
    //variavel para acessar os elementos do layout, ao inves do findbyid....
    private FragmentJogoBinding binding;
    //vetor para agrupar os botões
    private Button[] botoes;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //instancia o binding

        binding = FragmentJogoBinding.inflate(inflater, container, false);

        //instancia o vetor
        botoes = new Button[9];

        //agrupa os botoes no vetor
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        //associa o listener aos botoes

        for (Button bt : botoes){
            bt.setOnClickListener(listenerBotoes);
        }
        return binding.getRoot();
    }

    private View.OnClickListener listenerBotoes = btPress ->{
       //pega o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai os ois últimos caracteres do nomeBotao

        String posicao = nomeBotao.substring(nomeBotao.length()-2);
        //extrai a posição em linha e coluna
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));

    };
}