package com.project.cecib.dawin_project;

import android.graphics.Color;

/**
 * Created by cecib on 19/05/2017.
 */

public class Piece_L extends Piece {
    public Piece_L(){
        height = 3;
        width = 2;
        matrix = new int[][]{{1,0},{1,0},{1,1}};
        color = 3;
    }
}
