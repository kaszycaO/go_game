package tp.project.go_game.mainpackage;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.spi.ServiceException;
import tp.project.go_game.database.HibernateConfig;
import tp.project.go_game.server.AppServer;

import javax.swing.*;

/**
 * 
 * @author Oliwier Kaszyca & Dominika Szydlo
 * 
 * Główna klasa uruchamiająca serwer
 *
 */
public class HostMain 
{
    public static void main( String[] args )
    {


		try{
			HibernateConfig.username = "DO";
			HibernateConfig.password = "root";
			HibernateConfig factory = new HibernateConfig();

			StandardServiceRegistryBuilder registryBuilder =
					new StandardServiceRegistryBuilder().applySettings(factory.getConfiguration().getProperties());
			SessionFactory sessionFactory =  factory.getConfiguration().buildSessionFactory(registryBuilder.build());
			sessionFactory.close();
			JOptionPane.showMessageDialog(null,"Connected!");

		}catch (ServiceException ex){
			JOptionPane.showMessageDialog(null,"ERROR!");
		}




		AppServer server = new AppServer();
		try {
			server.listenSocket();
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
}
