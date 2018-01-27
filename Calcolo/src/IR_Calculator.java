import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.quartz.SchedulerException;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AD-TEAM
 */

public class IR_Calculator{   
    
    /**
     *
     * @param args
     * @throws SchedulerException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    
   public static void main(String args[]) throws SchedulerException, ClassNotFoundException, SQLException, FileNotFoundException, IOException{
       FileInputStream fis = null;
       
          String path = "./Set_Connessione.txt";
          fis = new FileInputStream(path);
          BufferedReader in = new BufferedReader(new InputStreamReader(fis));
          String url;
          String username;
          String password;
          url=in.readLine() ;
          username= in.readLine();
          password= in.readLine();

//Connessione al DataBase
      MySQL my=new MySQL();
      Connection con;
      con=my.connection(url, username, password);
      Cluster c=new Cluster();
      c.insertcluster(con);
      
//Chiude la connessione con il DB e iniziano i cambi stato dei segnali dei robot      
      my.close(con);
      Repeat.schedule_repeat();
}   
}