package canberra.edu.au;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static grid_class grid = new grid_class();
    public static marker_class marker = new marker_class();

    //INITIALIZE
    public static void initialize(){
        marker.PosX = 0;
        marker.PosY = 0;
        placeOOB();
    }
    public static void placeOOB(){
        Random random = new Random();
        int tempX;
        int tempY;
        for (int pass = 0; pass < grid.outOfBoundAmount; pass++){
            tempX = random.nextInt(grid.size);
            tempY = random.nextInt(grid.size);
            if (grid.getOOB(tempX, tempY) == true){ pass--; }
            else {
                if (tempX == 0 && tempY == 0){ pass--; }
                else if (tempX == grid.size - 1 && tempY == grid.size - 1){ pass--; }
                else { grid.setOOB(tempX, tempY, true);}
            }
        }
    }


    //PATHFIND
    public static void pathFind(int markerPosX,int markerPosY, String path){
        path += ("(" + markerPosX + "," + markerPosY + ") -> ");
        //IF GO
        if (grid.getOOB(markerPosX + 1, markerPosY) == false && markerPosX + 1 <= grid.size){ //test if can go right
            pathFind(markerPosX + 1, markerPosY, path);
        }
        if (grid.getOOB(markerPosX, markerPosY + 1) == false && markerPosY + 1 <= grid.size){ //test if can go down
            if (markerPosY + 1 == grid.size) { }
            pathFind(markerPosX, markerPosY + 1, path);
        }
        //IF STUCK
        if (grid.getOOB(markerPosX + 1, markerPosY) == true && grid.getOOB(markerPosX, markerPosY + 1) == true){
            return;
        }
        //IF END
        if (markerPosX == grid.size - 1 && markerPosY == grid.size - 1){
            path += ("DONE");
            marker.allPaths.add(path);
            return;
        }
    }


    //UTILITES
    public static void printPaths(){
        for (int i = 0; i < marker.allPaths.size() - 1; i++){
            System.out.println(marker.allPaths.get(i));
        }
    }

    //DEBUG
    public static void printGrid(){
        for (int y = 0; y < grid.size; y++){
            for (int x = 0; x < grid.size; x++){
                if (grid.getOOB(x, y) == false){ System.out.print("o "); }
                else { System.out.print("x "); }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    //MAIN
    public static void main(String[] args){
        grid.size = 4;
        grid.outOfBoundAmount = 4;
        if (grid.outOfBoundAmount > grid.size*grid.size - 2){
            System.out.println("ERROR: There cannot be more OutOfBound zones than there are spaces to place them.");
        } else {
            initialize();
            pathFind(marker.PosX, marker.PosY, "");
            printGrid();
            printPaths();
        }
    }
}

class marker_class{
    int PosX;
    int PosY;
    ArrayList<String> allPaths = new ArrayList<String>();

    public void getAllPaths(){
        for (int i = 0; i < allPaths.size(); i++){
            System.out.println(allPaths.get(i));
        }
    }
}
class grid_class{
    boolean[][] outOfBoundLocation = new boolean[50][50];
    int outOfBoundAmount;
    int size;

    public boolean getOOB(int x, int y){ return outOfBoundLocation[x][y]; }
    public void setOOB(int x, int y, boolean element){ outOfBoundLocation[x][y] = element; }
}