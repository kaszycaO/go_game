package tp.project.go_game.database;

import javax.transaction.Transactional;
import java.util.List;

    @Transactional
    public interface DAOInterface<T> {

        List<T> getAll();
        void save(T t);

    }



