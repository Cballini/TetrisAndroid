package com.project.cecib.dawin_project;

/**
 * Created by cecib on 19/05/2017.
 */

public interface mouvement_possible {
    public boolean outOfgrid(int gridH, int gridW);
    public boolean collisionDown(int[][] gridM, int gridH, int gridW);
    public boolean collisionLeft(int[][] gridM);
    public boolean collisionRight(int[][] gridM, int gridW);
    public boolean rotationOk(int gridH, int gridW);
}
