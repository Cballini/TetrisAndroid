package com.project.cecib.dawin_project;

import android.graphics.Color;

/**
 * Created by cecib on 19/05/2017.
 */

public class Piece_S extends Piece {
    public Piece_S(){
        height = 2;
        width = 3;
        matrix = new int[][]{{0,1,1},{1,1,0}};
        color = 4;
    }
}
