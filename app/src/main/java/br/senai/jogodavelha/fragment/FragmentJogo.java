package br.senai.jogodavelha.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.jogodavelha.R;
import br.senai.jogodavelha.activity.MainActivity;
import br.senai.jogodavelha.databinding.FragmentJogoBinding;
import br.senai.jogodavelha.util.PrefsUtil;


public class FragmentJogo extends Fragment {
    //variavel para acessar os elementos do layout, ao inves do findbyid....
    private FragmentJogoBinding binding;
    //vetor para agrupar os botões
    private Button[] botoes;
    //variavel que representa o tabuleiro
    private String[][] tabuleiro;
    //variavel para os simbolos
    private String simbjog1, simbjog2, simbolo, nome1, nome2;
    //variavel random para sortear quem começa
    private Random random;
    //variavel para contar os numeros de jogadas
    private int numJogadas = 0;
    //variaveis para o placar
    private int placarJog1  = 0, placarJog2 = 0, velha = 0;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //habilita o menu neste fragment
        setHasOptionsMenu(true);

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
        //instancia o tabuleiro
        tabuleiro = new String[3][3];

        //preencher o tabuleiro com ""
        for(String[] vetor : tabuleiro){
            Arrays.fill(vetor, "");
        }
        //faz a mesma coisa que acima
      //  for(int 1 = 0; 1 < 3; i++){
           // for(int i < 0; 1 < 3; i++){
               // tabuleiro[i][i] > ""

        //instancia o random
        random = new Random();

        //define os simbolos dos jogadores
        simbjog1 = PrefsUtil.getSimboloJog1(getContext());
        simbjog2 = PrefsUtil.getSimboloJog2(getContext());

        nome1 =  PrefsUtil.getNomeJog1(getContext());
        nome2 = PrefsUtil.getNomeJog2(getContext());
        //altera o simbolo do jogador no placar
        binding.jogador1.setText(getResources().getString(R.string.jogador1, nome1, simbjog1));
        binding.jogador2.setText(getResources().getString(R.string.jogador2, nome2, simbjog2));




        //sorteia quem inicia po jogo
        sorteia();

        //atualizar a vez
        atualizaVez();


        //retorna a view do fragment
        return binding.getRoot();


    }

    private void sorteia(){
        //caso o random gere um valor verdadeiro o jogador 1 começa
        //caso contrario o jogador 2 começa
        if(random.nextBoolean()){
            simbolo = simbjog1;
        }
        else{
            simbolo = simbjog2;

        }
    }

    private void atualizaVez(){
        //verifica de quem é a vez e pinta o placar do jogador em questão
        if(simbolo.equals(simbjog1)){
            binding.idPai.setBackgroundColor(getResources().getColor(R.color.teal_700));
            binding.id2.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            binding.id2.setBackgroundColor(getResources().getColor(R.color.teal_700));
            binding.idPai.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
    private void limparjogo(){
        for(String[] vetor : tabuleiro){
            Arrays.fill(vetor, "");

        }
    //percorre o vetor de botoes resetando-os
        for (Button botao : botoes){
            botao.setBackgroundColor(getResources().getColor(R.color.vermelho_escuro));
            botao.setClickable(true);
            botao.setText("");
        }
        //sorteia quem ira iniciar o proximo jogo
        sorteia();
        //atualiza a vez no placar
        atualizaVez();
        //zerar o numero de jogadas
        numJogadas = 0;




    }

    private boolean venceu(){
        //verifica se venceu nas linhas
        for(int i = 0; i < 3; i++){
            if (tabuleiro[i][0].equals(simbolo) && tabuleiro[i][1].equals(simbolo) && tabuleiro[i][2].equals(simbolo)){
                return true;
            }
        }
        //verifica se venceu na coluna
        for(int i = 0; i < 3; i++){
            if (tabuleiro[0][i].equals(simbolo) && tabuleiro[1][i].equals(simbolo) && tabuleiro[2][i].equals(simbolo)){
                return true;
            }
        }
        //verifica se venceu nas diagonais
        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)){
                return true;
        }
        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)){
                 return true;
        }
        return false;
    }

    private void atualizarPlacar(){
        binding.placar1.setText(placarJog1 + "");
        binding.placar2.setText(placarJog2 + "");
        binding.placarVelha.setText(velha + "");
    }

    public void  alert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.certezaReset).setTitle(R.string.tituloCerteza).setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                limparjogo();
                atualizarPlacar();
            }
        });
        builder.setNegativeButton(R.string.Nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //verifica qual botão foi clicado no menu
        switch (item.getItemId()){
            //caso tenha clicado no resetar
            case R.id.menu_resetar:
                placarJog1 = 0;
                placarJog2 = 0;
                velha = 0;
                alert();
                break;
                //caso tenha clicado na preferencias
            case R.id.menu_preference:
                NavHostFragment.findNavController(FragmentJogo.this).navigate(R.id.action_fragmentJogo_to_prefFragment);
                    break;
            case R.id.menu_inicio:
                NavHostFragment.findNavController(FragmentJogo.this).navigate(R.id.action_fragmentJogo_to_fragmentInicio);
                    break;

        }

        return true;
    }

    private View.OnClickListener listenerBotoes = btPress ->{
        //incrementa as jogadas
        numJogadas++;

       //pega o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai os dois últimos caracteres do nomeBotao

        String posicao = nomeBotao.substring(nomeBotao.length()-2);
        //extrai a posição em linha e coluna
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
        //marca no tabuleiro o simbolo que foi jogado
        tabuleiro[linha][coluna] = simbolo;
        //faz um casting de view pra button
        Button botao = (Button) btPress;
        //troca o texto dos botoes que foi clicado
        botao.setText(simbolo);
        //desabilitar o botao
        botao.setClickable(false);
        //troca o background do botao
        botao.setBackgroundColor(Color.WHITE);

        //verifica se venceu
        if(numJogadas >= 5 && venceu()){
            //exibe um Toast informando que o jogador veneu


            //verifica quem venceu e atualiza o placar
            if(simbolo.equals(simbjog1)){
                placarJog1++;
                Toast.makeText(getContext(), R.string.vitoriajog1, Toast.LENGTH_LONG).show();
            }else{
                placarJog2++;
                Toast.makeText(getContext(), R.string.vitoriajog2, Toast.LENGTH_LONG).show();
            }
            atualizarPlacar();
            //resetar o tabuleiro
            limparjogo();

        }else if(numJogadas == 9){
            velha++;
            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_LONG).show();
            atualizarPlacar();
            limparjogo();

        }else {
            //inverte a vez
            simbolo = simbolo.equals(simbjog1) ? simbjog2 : simbjog1;

            atualizaVez();

        }
    };

    @Override
    public void onStart() {
        // sumir com o toolBar
        //pegar uma referencia do tipo appCompatActivity
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        //oculta a actionBar
        minhaActivity.getSupportActionBar().show();
        //retira o botao de voltar da action bar
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        super.onStart();
    }
}