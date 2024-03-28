package com.iremse.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button buttonReset, buttonQuit;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;
    //0: Birinci oyuncu (X) tarafından işaretlenmiş.
    //1: İkinci oyuncu (O) tarafından işaretlenmiş.
    //2: Henüz işaretlenmemiş olan hücrelerin durumu.(BAŞLANGIÇTA BU YÜZDEN HEPSİ 2)
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //satırlar
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},//sütunlar
            {0, 4, 8}, {2, 4, 6} //çapraz
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.oyuncu1Skor);
        playerTwoScore = findViewById(R.id.oyuncu2Skor);
        playerStatus = findViewById(R.id.oyuncuDurumu);
        buttonReset = findViewById(R.id.buttonReset);
        buttonQuit = findViewById(R.id.buttonQuit);

        buttons[0] = findViewById(R.id.btn_0);
        buttons[1] = findViewById(R.id.btn_1);
        buttons[2] = findViewById(R.id.btn_2);
        buttons[3] = findViewById(R.id.btn_3);
        buttons[4] = findViewById(R.id.btn_4);
        buttons[5] = findViewById(R.id.btn_5);
        buttons[6] = findViewById(R.id.btn_6);
        buttons[7] = findViewById(R.id.btn_7);
        buttons[8] = findViewById(R.id.btn_8);

// Butonları tek tek tanımladıktan sonra her birine tıklama
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }
        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); //btn_2
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); //2

        // Aktif oyuncunun belirlenmesi ve butonun işaretlenmesi

        if (activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#0475A8"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#E91EAF"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        // Kazananın kontrol edilmesi ve skorların güncellenmesi

        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                ;
                Toast.makeText(this, "1. oyuncu kazandı!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                ;
                Toast.makeText(this, "2. oyuncu kazandı!", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        } else if (rountCount == 9) {
            playAgain();
            Toast.makeText(this, "Kimse Kazanmadı!", Toast.LENGTH_SHORT).show();
        } else {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount>playerTwoScoreCount){
            playerStatus.setText("1. oyuncu kazandı!");
        }
        else if(playerOneScoreCount>playerTwoScoreCount) {
            playerStatus.setText("1. oyuncu kazandı!");
        }
        else {
            playerStatus.setText("");
        }

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Uygulamadan çıkış
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });


    }
    public boolean checkWinner() {
        boolean winnerResult = false;

        for (int[] winingPosition : winningPositions) {
            if (gameState[winingPosition[0]] == gameState[winingPosition[1]] &&
                    gameState[winingPosition[1]] == gameState[winingPosition[2]] &&
                    gameState[winingPosition[0]] != 2) {
                winnerResult=true;
            }

        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString((playerOneScoreCount)));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    //Artık yapacak işlem kalmadığında ya da birisi kazandığında günceller
    public void playAgain(){
        rountCount=0;
        activePlayer=true;

        for(int i= 0; i<buttons.length; i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}