package tp.project.go_game.database;

import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class GameDAO implements DAOInterface<GameDB> {

    static List<GameDB> games;
    private HibernateConfig factory = new HibernateConfig();
    Session session = factory.getSessionFactory().openSession();

    public GameDAO(){
        updateGameList();
    }

    @Override
    public List<GameDB> getAll() {
        return games;
    }

    @Override
    public void save(GameDB gameDB) {

        session = factory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(gameDB);
            session.getTransaction().commit();
            games.add(gameDB);
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            session.close();
            factory.getSessionFactory().close();
        }

    }

    public int getMAXID(){
        session = factory.getSessionFactory().openSession();
        List factureID = session.createSQLQuery("SELECT MAX(id_gry) FROM gogame.games LIMIT 1").list();
        int id;
        if(factureID.get(0) != null)
            id = Integer.parseInt(factureID.get(0).toString());
        else
            id = 0;

        return id;
    }

    public void updateGameList(){
        games = (ArrayList<GameDB>) session.createQuery("from GameDB").list();
    }


}
