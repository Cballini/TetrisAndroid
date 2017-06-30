package com.project.cecib.dawin_project;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private int MAX_X = 9;
    private int MAX_Y = 18;
    private Piece lastPiece = new Piece();
    private int nextPiece = 0;
    private ArrayList<Bitmap> imageItems = new ArrayList<>();
    private int[][] gridMatrix =new int[MAX_X][MAX_Y];
    private int score = 0;
    private int speed = 1000;
    private boolean endGame = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomPiece();
        preview();
        gridView = (GridView) findViewById(R.id.gridview);
        pieceManager();
        gridAdapter = new GridViewAdapter(this,R.layout.grid_item, imageItems);
        gridView.setAdapter(gridAdapter);
        TextView scoreTextView = (TextView) findViewById(R.id.score);
        scoreTextView.setText("Score : " + score);

        //déclaration des actions des boutons
        Button mClickButtonLeft = (Button)findViewById(R.id.buttonLeft);
        mClickButtonLeft.setOnClickListener(this);
        Button mClickButtonDown = (Button)findViewById(R.id.buttonDown);
        mClickButtonDown.setOnClickListener(this);
        Button mClickButtonRight = (Button)findViewById(R.id.buttonRight);
        mClickButtonRight.setOnClickListener(this);
        Button mClickButtonRotate = (Button)findViewById(R.id.buttonRotate);
        mClickButtonRotate.setOnClickListener(this);
        Button mClickButtonReload = (Button)findViewById(R.id.buttonReload);
        mClickButtonReload.setOnClickListener(this);

        //descente automatique
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                handler.postDelayed(this, speed);
                if(!endGame) {
                    removePiece(lastPiece);
                    lastPiece.down();
                    if (lastPiece.outOfgrid(MAX_Y, MAX_X) || lastPiece.collisionDown(gridMatrix, MAX_Y, MAX_X)) {
                        lastPiece.setOldPiece(true);
                    }
                    pieceManager();
                    gridAdapter.setData(imageItems);
                    gridView.setAdapter(gridAdapter);

                    //maj affichage score
                    TextView scoreTextView = (TextView) findViewById(R.id.score);
                    scoreTextView.setText("Score : " + score);
                }
                else {
                    pieceManager();
                    gridAdapter.setData(imageItems);
                    gridView.setAdapter(gridAdapter);
                }
            }
        };
        handler.postDelayed(r, speed);
    }


    private void pieceManager() {
        //réinitialisation de la grille
        imageItems = new ArrayList<>();

        //ajout de la pièce dans une matrice
         addPiece(lastPiece);

        //n° lignes
        for (int y = 0; y < MAX_Y; y++) {
            //n° colonnes
            for (int x = 0; x < MAX_X; x++){
                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img4);
                //Bitmap couleur
                Bitmap piece = Bitmap.createBitmap(24,24, Bitmap.Config.RGB_565);

                if(gridMatrix[x][y] !=0){
                     piece.eraseColor(recoverColor(gridMatrix[x][y]));
                }
                else{
                    piece.eraseColor(Color.GRAY);
                }
                imageItems.add(piece);
            }
        }

        //changement de pièce
        if(lastPiece.getOldPiece()){
            //màj score
            updateScore("piece");
            //vérification ligne complète
            tetris();
            //nouvelle pièce aléatoire
            randomPiece();
            preview();
            //vérification fin du jeu
            endGame = defeat(lastPiece);
        }
    }

    //retourne la couleur à appliquer
    public int recoverColor(int type){
        Resources res = getResources();
        int color;
        switch (type) {
            case 1: {
                color = res.getColor(R.color.colorBlock);
                break;
            }
            case 2: {
                color = res.getColor(R.color.colorI);
                break;
            }
            case 3: {
                color = res.getColor(R.color.colorL);
                break;
            }
            case 4: {
                color = res.getColor(R.color.colorS);
                break;
            }
            case 5: {
                color = res.getColor(R.color.colorZ);
                break;
            }
            case 6: {
                color = res.getColor(R.color.colorJ);
                break;
            }
            default: {
                color = res.getColor(R.color.colorBlock);
            }

        }
        return color;
    }

    //prépare une pièce aléatoire
    private void randomPiece(){
        int r = new Random().nextInt(7)+1;

        if(nextPiece == 0) {
            if (r == 1) {
                lastPiece = new Piece_block();
            } else if (r == 2) {
                lastPiece = new Piece_I();
            } else if (r == 3) {
                lastPiece = new Piece_L();
            } else if (r == 4) {
                lastPiece = new Piece_S();
            } else if (r == 5) {
                lastPiece = new Piece_Z();
            } else if (r == 6) {
                lastPiece = new Piece_J();
            }
            r = new Random().nextInt(7)+1;
            nextPiece = r;
        }
        else {
            if (nextPiece == 1) {
                lastPiece = new Piece_block();
            } else if (nextPiece == 2) {
                lastPiece = new Piece_I();
            } else if (nextPiece == 3) {
                lastPiece = new Piece_L();
            } else if (nextPiece == 4) {
                lastPiece = new Piece_S();
            } else if (nextPiece == 5) {
                lastPiece = new Piece_Z();
            } else if (nextPiece == 6) {
                lastPiece = new Piece_J();
            }
            nextPiece = r;
        }

        //Position de départ
        lastPiece.setPos_x(MAX_X/2);
        lastPiece.setPos_y(0);
    }

    //ajout d'une pièce sur la grille
    private void addPiece(Piece p){
        int my = 0;
        for(int y = p.getPos_y(); y < p.getPos_y()+p.getHeight(); y++ ){
            int mx = 0;
            for (int x = p.getPos_x(); x < p.getPos_x()+p.getWidth(); x++){
                //ajout matrice pièce dans matrice grille
                if(p.getMatrix()[my][mx] == 1) {
                    gridMatrix[x][y] = p.getColor();
                }
                mx++;
            }
            my++;
        }
    }

    private void removePiece(Piece p){
        int my = 0;
        for(int y = p.getPos_y(); y < p.getPos_y()+p.getHeight(); y++ ){
            int mx = 0;
            for (int x = p.getPos_x(); x < p.getPos_x()+p.getWidth(); x++){
                //retire la matrice pièce dans matrice grille
                gridMatrix[x][y] = 0;
                mx++;
            }
            my++;
        }

    }

    public void onClick(View v){
        if(!lastPiece.getOldPiece() ) {
            removePiece(lastPiece);
            switch (v.getId()) {
                case R.id.buttonLeft: {
                    if(!endGame) {
                        lastPiece.left();
                        if (lastPiece.outOfgrid(MAX_Y, MAX_X) || lastPiece.collisionLeft(gridMatrix)) {
                            lastPiece.right();
                        }
                    }
                    break;
                }
                case R.id.buttonDown: {
                    if(!endGame) {
                        lastPiece.down();
                        if (lastPiece.outOfgrid(MAX_Y, MAX_X) || lastPiece.collisionDown(gridMatrix, MAX_Y, MAX_X)) {
                            lastPiece.setOldPiece(true);
                        }
                    }
                    break;
                }
                case R.id.buttonRight: {
                    if(!endGame) {
                        lastPiece.right();
                        if (lastPiece.outOfgrid(MAX_Y, MAX_X) || lastPiece.collisionRight(gridMatrix, MAX_X)) {
                            lastPiece.left();
                        }
                    }
                    break;
                }
                case R.id.buttonRotate: {
                    if(!endGame) {
                        if (lastPiece.rotationOk(MAX_Y, MAX_X)) {
                            lastPiece.rotate();
                        }
                        if (lastPiece.outOfgrid(MAX_Y, MAX_X)) {
                            lastPiece.setOldPiece(true);
                        }
                    }
                    break;
                }
                case R.id.buttonReload:{
                    reloadGame();
                    break;
                }
                default: {

                }
            }
            pieceManager();
            gridAdapter.setData(imageItems);
            gridView.setAdapter(gridAdapter);
        }
    }

    public void downAll(int line){
        for (int y = line; y>=1; y--){
            for(int x = 0; x<MAX_X-1; x++){
                gridMatrix[x][y] = gridMatrix[x][y-1];
            }
        }
    }

    public void tetris(){
        int nbBlocks;
        for(int y = 0; y<MAX_Y; y++){
            nbBlocks = 0;
            for (int x=0; x<MAX_X; x++){
                if(gridMatrix[x][y] != 0){
                    nbBlocks = nbBlocks + 1;
                }

                //ligne complète
                if(nbBlocks == MAX_X){
                    downAll(y);
                    updateScore("tetris");
                }
            }
        }
    }

    public boolean defeat(Piece p){
        if(p.collisionDown(gridMatrix, MAX_Y,MAX_X)){
            final TextView defeatTextView = (TextView) findViewById(R.id.defeat);
            defeatTextView.setText(R.string.defeat);
            return true;
        }

        return false;
    }

    public void updateScore(String points){
        switch (points){
            case "piece":{
                score = score + 100;
                break;
            }
            case "tetris":{
                score = score + 1000;
                break;
            }
            default:{

            }
        }

        //adaptation vitesse par palliers
        if(score>1000){
            speed  = 800;
        }
        if(score>5000){
            speed  = 500;
        }
        if(score>10000){
            speed  = 300;
        }
        if(score>15000){
            speed  = 100;
        }
    }

    public void reloadGame(){
        for(int y = 0; y<MAX_Y; y++){
            for(int x = 0; x<MAX_X; x++){
                gridMatrix[x][y] = 0;
            }
        }
        score = 0;
        nextPiece = 0;
        randomPiece();
        endGame = false;
        final TextView defeatTextView = (TextView) findViewById(R.id.defeat);
        defeatTextView.setText("");
    }

    public void preview(){
        ImageView preview = (ImageView) findViewById(R.id.imgPreview);

        if(nextPiece == 1){
            preview.setImageResource(R.drawable.piece_b);
        }
        else if(nextPiece == 2){
            preview.setImageResource(R.drawable.piece_i);
        }
        else if(nextPiece == 3){
            preview.setImageResource(R.drawable.piece_l);
        }
        else if(nextPiece == 4){
            preview.setImageResource(R.drawable.piece_s);
        }
        else if(nextPiece == 5){
            preview.setImageResource(R.drawable.piece_z);
        }
        else if(nextPiece == 6){
            preview.setImageResource(R.drawable.piece_j);
        }
    }
}
