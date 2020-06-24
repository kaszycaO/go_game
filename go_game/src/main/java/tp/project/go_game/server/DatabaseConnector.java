package tp.project.go_game.server;

import tp.project.go_game.database.GameDAO;
import tp.project.go_game.database.GameDB;

import javax.swing.*;
import java.util.ArrayList;

public class DatabaseConnector {

    private int currID;
    private GameDAO gameDAO;

    public DatabaseConnector(){

        gameDAO = new GameDAO();
        currID = getNextid();


    }

    public void initializeSave(int turnCounter, String message){

        //x y stan
        ArrayList<String> convertedValues = new ArrayList<>();

        for(int i = 2; i < message.length(); i++){
            String helper = "";
            while (message.charAt(i) != ' '){
                helper += message.charAt(i);
                i++;
            }
            i++;
            int X = Integer.parseInt(helper);
            helper = "";
            while (message.charAt(i) != ' '){
                helper += message.charAt(i);
                i++;
            }
            i++;
            int Y = Integer.parseInt(helper);
            if (message.charAt(i)=='w'){
                i+=5;
                helper = "w";
            } else if(message.charAt(i) == 'b'){
                i+=5;
                helper = "b";
            } else {
                i+=4;
                helper = "n";
            }
            saveMove(turnCounter, X, Y, helper);
        }


    }


    public void saveMove(int id_ruchu, int X, int Y, String state){

        GameDB gameDB = new GameDB(currID,id_ruchu, X, Y, state);
        gameDAO.save(gameDB);

    }


    public void getMove(){}

    private int getNextid(){
        return gameDAO.getMAXID() + 1;

    }

}
