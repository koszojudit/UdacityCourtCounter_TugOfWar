package com.example.android.a3_tugofwar;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity; //it will work in 2020 since v7 code is now not working and showing error
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String SCORE_TEAM_A = "scoreTeamA";
    private static final String SCORE_TEAM_B = "scoreTeamB";
    private static final String ROUNDS_REMAINING = "roundsRemaining";
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int roundsRemaining = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // lways call the superclass first - the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // recovering the instance state
        if (savedInstanceState != null) {
            roundsRemaining = savedInstanceState.getInt(ROUNDS_REMAINING, 10);
            displayRounds(roundsRemaining);
            scoreTeamA = savedInstanceState.getInt(SCORE_TEAM_A, 0);
            displayForTeamA(scoreTeamA);
            scoreTeamB = savedInstanceState.getInt(SCORE_TEAM_B, 0);
            displayForTeamB(scoreTeamB);

        } else {
            displayRounds(10);
            displayForTeamA(0);
            displayForTeamB(0);
        }
    }

    /**
     * Displays the number of rounds remaining.
     */

    public void displayRounds(int roundsRemaining) {
        TextView roundsView = (TextView) findViewById(R.id.rounds_remaining_number_view);
        roundsView.setText(String.valueOf(roundsRemaining));
    }

    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_A_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_B_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Increase the score for Team A by 1 point.
     */
    public void addOneForTeamA(View v) {
        scoreTeamA = scoreTeamA + 1;
        roundsRemaining = roundsRemaining - 1;
        if (roundsRemaining == 0) {
            declareWinner(scoreTeamA, scoreTeamB);
        }
        displayRounds(roundsRemaining);
        displayForTeamA(scoreTeamA);
    }

    /**
     * Increase the score for Team B by 1 point.
     */
    public void addOneForTeamB(View v) {
        scoreTeamB = scoreTeamB + 1;
        roundsRemaining = roundsRemaining - 1;
        if (roundsRemaining == 0) {
            declareWinner(scoreTeamA, scoreTeamB);
        }
        displayRounds(roundsRemaining);
        displayForTeamB(scoreTeamB);
    }

    /**
     * Increase the score for Team B by 1 point.
     */
    public void minusOneRound(View v) {
        roundsRemaining = roundsRemaining - 1;
        if (roundsRemaining == 0) {
            declareWinner(scoreTeamA, scoreTeamB);
        }
        displayRounds(roundsRemaining);
    }

    /**
     * Resets the score for both teams to 0.
     */
    public void resetScore(View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        roundsRemaining = 10;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayRounds(roundsRemaining);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(SCORE_TEAM_A, scoreTeamA);
        savedInstanceState.putInt(SCORE_TEAM_B, scoreTeamB);
        savedInstanceState.putInt(ROUNDS_REMAINING, roundsRemaining);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Method to display the popup message in order to declare winner.
     */
    protected void declareWinner (int scoreTeamA, int scoreTeamB){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View winnerView = inflater.inflate(R.layout.winner_popup,null);
        final PopupWindow mPopupWindow = new PopupWindow(
                winnerView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(6.0f);
        }

        Button resetButton = (Button) winnerView.findViewById(R.id.dismiss);
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mPopupWindow.dismiss();
                resetScore(view);
            }
        }
        );

        TextView winnerText = (TextView) winnerView.findViewById(R.id.winner_textview);
        if (scoreTeamA < scoreTeamB) {
            winnerText.setText(getString(R.string.team_name_B));
        } else if (scoreTeamA == scoreTeamB) {
            winnerText.setText(getString(R.string.draw_match));
        } else {
            winnerText.setText(getString(R.string.team_name_A));
        }
        mPopupWindow.showAtLocation(findViewById(R.id.activity_main), Gravity.BOTTOM,0,0);
    }
}
