package tp.project.go_game.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "games")
@Entity
public class GameDB implements Serializable {
    @Id
    @Column(name = "id_gry")
    private int gameID;
    @Id
    @Column(name = "id_ruchu")
    private int moveID;
    @Column(name = "X")
    private int coordX;
    @Column(name = "Y")
    private int coordY;
    @Column(name = "stan")
    private String state;

    public GameDB(){}

    public GameDB(int gameID, int moveID, int coordX, int coordY, String state){
        this.gameID = gameID;
        this.moveID = moveID;
        this.coordX = coordX;
        this.coordY = coordY;
        this.state = state;
    }


    public String getState() {
        return state;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getMoveID() {
        return moveID;
    }

    public int getGameID() {
        return gameID;
    }
}
