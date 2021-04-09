package com.example.tictactoe


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private var activePlayer = false
    private var pointsX = 0
    private var pointsO = 0
    private var multiplayer = false
    private var gameEnd = 0
    private var tieFinder = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a1 = findViewById<ImageButton>(R.id.imageButton)
        val b1 = findViewById<ImageButton>(R.id.imageButton2)
        val c1 = findViewById<ImageButton>(R.id.imageButton3)
        val a2 = findViewById<ImageButton>(R.id.imageButton5)
        val b2 = findViewById<ImageButton>(R.id.imageButton6)
        val c2 = findViewById<ImageButton>(R.id.imageButton7)
        val a3 = findViewById<ImageButton>(R.id.imageButton8)
        val b3 = findViewById<ImageButton>(R.id.imageButton9)
        val c3 = findViewById<ImageButton>(R.id.imageButton10)
        val switch1 = findViewById<Switch>(R.id.switch1)
        val reset = findViewById<Button>(R.id.button)
        var gameResult = findViewById<TextView>(R.id.textView2)
        var scoreO = findViewById<TextView>(R.id.textView3)
        var scoreX = findViewById<TextView>(R.id.textView4)
        var nameInput = findViewById<EditText>(R.id.plain_text_input)
        val nameConfirm = findViewById<Button>(R.id.button2)
        var nameInputTxt = findViewById<TextView>(R.id.textView7)
        var field = arrayOf(
                arrayOf(0,0,0),
                arrayOf(0,0,0),
                arrayOf(0,0,0)
        )

        nameConfirm.setOnClickListener{
            var name = nameInput.text.toString()
            nameInputTxt.setText("Hello, " + name + "!")
        }

        fun clear(){
            a1.setImageResource(R.drawable.blank);
            b1.setImageResource(R.drawable.blank);
            c1.setImageResource(R.drawable.blank);
            a2.setImageResource(R.drawable.blank);
            b2.setImageResource(R.drawable.blank);
            c2.setImageResource(R.drawable.blank);
            a3.setImageResource(R.drawable.blank);
            b3.setImageResource(R.drawable.blank);
            c3.setImageResource(R.drawable.blank);
            field=arrayOf(
                    arrayOf(0,0,0),
                    arrayOf(0,0,0),
                    arrayOf(0,0,0)
            )
            activePlayer = false
            gameEnd = 0
            gameResult.setText("")
        }

                                                                                                    //ai moves based entirely on random number generator
        fun opponentMove(field: Array<Array<Int>>): Array<Array<Int>> {
            var aiMoveX = (0..2).random()
            var aiMoveY = (0..2).random()
            var spaceCheck = false
            for(i in 0..2){
                for(j in 0..2) {
                    if(field[i][j]==0) spaceCheck = true
                }
            }
            if(spaceCheck){
                if (field[aiMoveX][aiMoveY]==0){
                    if(aiMoveX==0){
                        if(aiMoveY==0) a1.setImageResource(R.drawable.o1);
                        else if (aiMoveY==1) b1.setImageResource(R.drawable.o1);
                        else if (aiMoveY==2) c1.setImageResource(R.drawable.o1);
                    }
                    else if(aiMoveX==1){
                        if(aiMoveY==0) a2.setImageResource(R.drawable.o1);
                        else if (aiMoveY==1) b2.setImageResource(R.drawable.o1);
                        else if (aiMoveY==2) c2.setImageResource(R.drawable.o1);
                    }
                    else if(aiMoveX==2){
                        if(aiMoveY==0) a3.setImageResource(R.drawable.o1);
                        else if (aiMoveY==1) b3.setImageResource(R.drawable.o1);
                        else if (aiMoveY==2) c3.setImageResource(R.drawable.o1);
                    }
                    field[aiMoveX][aiMoveY]=2
                }
                else opponentMove(field)
            }
            return field
        }

                                                                                                    //check if someone won
        fun winCheck(field: Array<Array<Int>>) {
            for(i in 0..2) {
                if(field[i][0]==field[i][1] && field[i][1]==field[i][2] && field[i][2]==1 ||
                        field[0][i]==field[1][i] && field[1][i]==field[2][i] && field[2][i]==1 ||
                        field[0][0]==field[1][1] && field[1][1]==field[2][2] && field[2][2]==1 ||
                        field[0][2]==field[1][1] && field[1][1]==field[2][0] && field[2][0]==1) {
                    gameEnd=1
                } else if(field[i][0]==field[i][1] && field[i][1]==field[i][2] && field[i][2]==2 ||
                        field[0][i]==field[1][i] && field[1][i]==field[2][i] && field[2][i]==2 ||
                        field[0][0]==field[1][1] && field[1][1]==field[2][2] && field[2][2]==2 ||
                        field[0][2]==field[1][1] && field[1][1]==field[2][0] && field[2][0]==2){
                    gameEnd=2
                }
            }
            if(gameEnd==1){
                gameResult.setText("X WINS!")
                pointsX++
            }
            else if(gameEnd==2){
                gameResult.setText("O WINS!")
                pointsO++

            }
            else if(gameEnd==0){
                for(i in 0..2){
                    for(j in 0..2) {
                        if(field[i][j]==0) tieFinder=false
                    }
                }
                if(tieFinder) gameResult.setText("IT'S A TIE!")
                else{
                    tieFinder = true
                }
            }
                                                                                                        scoreO.setText(pointsO.toString())
                                                                                                        scoreX.setText(pointsX.toString())

        }

                                                                                                    //switch between multiplayer and singleplayer (with board reset, otherwise player can get stuck playing as O vs O)
        switch1.setOnCheckedChangeListener ({ _, isChecked ->
            if(isChecked) multiplayer = true else multiplayer = false
            clear()

        })

                                                                                                    //board reset
        reset.setOnClickListener{
            clear()
        }

                                                                                                    //main game
        a1.setOnClickListener{
            if(gameEnd==0){
                if(field[0][0]==0){
                    if(!activePlayer){
                        a1.setImageResource(R.drawable.x1);
                        field[0][0]=1
                    }
                    else{
                        a1.setImageResource(R.drawable.o1);
                        field[0][0]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        b1.setOnClickListener{
            if(gameEnd==0){
                if(field[0][1]==0){
                    if(!activePlayer){
                        b1.setImageResource(R.drawable.x1);
                        field[0][1]=1
                    }
                    else{
                        b1.setImageResource(R.drawable.o1);
                        field[0][1]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        c1.setOnClickListener{
            if(gameEnd==0){
                if(field[0][2]==0){
                    if(!activePlayer){
                        c1.setImageResource(R.drawable.x1);
                        field[0][2]=1
                    }
                    else{
                        c1.setImageResource(R.drawable.o1);
                        field[0][2]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        a2.setOnClickListener{
            if(gameEnd==0){
                if(field[1][0]==0){
                    if(!activePlayer){
                        a2.setImageResource(R.drawable.x1);
                        field[1][0]=1
                    }
                    else{
                        a2.setImageResource(R.drawable.o1);
                        field[1][0]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        b2.setOnClickListener{
            if(gameEnd==0){
                if(field[1][1]==0){
                    if(!activePlayer){
                        b2.setImageResource(R.drawable.x1);
                        field[1][1]=1
                    }
                    else{
                        b2.setImageResource(R.drawable.o1);
                        field[1][1]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        c2.setOnClickListener{
            if(gameEnd==0){
                if(field[1][2]==0){
                    if(!activePlayer){
                        c2.setImageResource(R.drawable.x1);
                        field[1][2]=1
                    }
                    else{
                        c2.setImageResource(R.drawable.o1);
                        field[1][2]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        a3.setOnClickListener{
            if(gameEnd==0){
                if(field[2][0]==0){
                    if(!activePlayer){
                        a3.setImageResource(R.drawable.x1);
                        field[2][0]=1
                    }
                    else{
                        a3.setImageResource(R.drawable.o1);
                        field[2][0]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        b3.setOnClickListener{
            if(gameEnd==0){
                if(field[2][1]==0){
                    if(!activePlayer){
                        b3.setImageResource(R.drawable.x1);
                        field[2][1]=1
                    }
                    else{
                        b3.setImageResource(R.drawable.o1);
                        field[2][1]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
        c3.setOnClickListener{
            if(gameEnd==0){
                if(field[2][2]==0){
                    if(!activePlayer){
                        c3.setImageResource(R.drawable.x1);
                        field[2][2]=1
                    }
                    else{
                        c3.setImageResource(R.drawable.o1);
                        field[2][2]=2
                    }
                    if(multiplayer) {
                        field=opponentMove(field)
                    } else activePlayer = !activePlayer
                    winCheck(field)
                }
            }
            else{
                clear()
            }
        }
    }
}