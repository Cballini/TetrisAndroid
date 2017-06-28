package com.project.cecib.dawin_project;

/**
 * Created by cecib on 15/05/2017.
 */

public class Piece implements mouvement, mouvement_possible {
    protected static int height;
    protected static int width;
    protected static int[][] matrix;
    protected static int pos_x;
    protected static int pos_y;
    protected static int color;
    protected static boolean oldPiece;

    public Piece(){ oldPiece = false;}

    public Piece(Piece p){
        height = p.getHeight();
        width = p.getWidth();
        matrix = p.getMatrix();
        pos_x = p.getPos_x();
        pos_y = p.getPos_y();
        color = p.getColor();
        oldPiece = p.getOldPiece();
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static boolean getOldPiece() {
        return oldPiece;
    }

    public static void setOldPiece(boolean oldPiece) {
        Piece.oldPiece = oldPiece;
    }

    @Override
    public void rotate() {
        int[][] rotation = new int[width][height];

        for(int x = 0; x<height; x++){
            for (int y=0; y<width;y++){
                rotation[y][x]=matrix[height-1-x][y];
            }
        }
        matrix =rotation;
        int widthTemp = width;
        width = height;
        height = widthTemp;
    }

    @Override
    public void left() {
        pos_x = pos_x-1;
    }

    @Override
    public void right() {
        pos_x = pos_x+1;
    }

    @Override
    public void down() {
        pos_y = pos_y+1;
    }

    @Override
    public boolean outOfgrid(int gridH, int gridW) {
        //limite gauche, limite droite, limite haute, limite basse
        if(pos_x<0 || pos_x+width>gridW || pos_y<0 || pos_y+height>=gridH){
            return true;
        }
        return false;
    }

    @Override
    public boolean collisionDown(int[][] gridM, int gridH, int gridW) {
        //collision bas
        for (int x = 0; x<width;x++){
            //vérification ligne du bas
            if (matrix[height-1][x] == 1) {
                if(pos_y+height< gridH){
                    if(gridM[pos_x+x][pos_y+height] !=0){
                        return true;
                    }
                }
            }
            else {
                //si 0 sur ligne du bas alors vérification au dessus
                for (int y = 0; y<height;y++){
                    if(matrix[y][x] == 1){
                        if(pos_y+y-1< gridH && pos_x+x+1<gridW){
                            if(gridM[pos_x+x+1][pos_y+y+1] !=0){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean collisionLeft(int[][] gridM) {
        //collision gauche
        for (int y =0; y<height; y++){
            if (matrix[y][0] == 1){
                if(pos_x >=0){
                    if(gridM[pos_x][pos_y+y] !=0){
                        return true;
                    }
                }
            }
            else {
                for (int x=1; x<width; x++){
                    if(matrix[y][x] == 1){
                        if(pos_x+x >=0){
                            /*System.out.println("****************" + y);
                            System.out.println("gridM[pos_x+x][pos_y+y]" + gridM[pos_x+x][pos_y+y]);
                            System.out.println("gridM[pos_x+x+1][pos_y+y]" + gridM[pos_x+x+1][pos_y+y]);
                            System.out.println("gridM[pos_x+x][pos_y+y+1]" + gridM[pos_x+x][pos_y+y+1]);
                            System.out.println("gridM[pos_x+x+1][pos_y+y+1]" + gridM[pos_x+x+1][pos_y+y+1]);
                            System.out.println("gridM[pos_x+x-1][pos_y+y]" + gridM[pos_x+x-1][pos_y+y]);
                            System.out.println("gridM[pos_x+x][pos_y+y-1]" + gridM[pos_x+x][pos_y+y-1]);
                            System.out.println("gridM[pos_x+x-1][pos_y+y-1]" + gridM[pos_x+x-1][pos_y+y-1]);*/

                            if(gridM[pos_x+x][pos_y+y+1] !=0){
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean collisionRight(int[][] gridM, int gridW) {
        //collision droite
        for (int y =height-1; y>=0; y--){
            if (matrix[y][width-1] == 1){
                if(pos_x+width-1 <gridW){
                    if(gridM[pos_x+width-1][pos_y+y] !=0){
                        return true;
                    }
                }
            }
            else {
                //A re tester
                for (int x=0; x<width; x++){
                    if(matrix[y][x] == 1){
                        if(pos_x+x <gridW){
                            if(gridM[pos_x+x][pos_y+y+1] !=0){
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean rotationOk(int gridH, int gridW){
        if(pos_x+height>gridW || pos_y+width>gridH){
            return false;
        }
        return true;
    }
}
