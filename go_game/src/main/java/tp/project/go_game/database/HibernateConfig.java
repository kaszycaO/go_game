package tp.project.go_game.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    public static String username;
    public static String password;
    private Configuration configuration = new Configuration();

    public HibernateConfig(){
        setHibernateConfiguration();
    }

    public SessionFactory getSessionFactory() {

        configuration.configure();

        StandardServiceRegistryBuilder registryBuilder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory =  configuration.buildSessionFactory(registryBuilder.build());
        return sessionFactory;
    }

    public void setHibernateConfiguration(){
        configuration.setProperty("hibernate.connection.driver_class","com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/goGame");
        configuration.setProperty("hibernate.connection.username",username);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.show_sql","true");
        configuration.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        configuration.addAnnotatedClass(tp.project.go_game.database.GameDB.class);

    }

    public Configuration getConfiguration(){
        return this.configuration;
    }
}
