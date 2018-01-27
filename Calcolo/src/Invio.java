
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AD-TEAM
 */
public class Invio implements Job{
    
    String url;
    String username;
    String password;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        FileInputStream fis = null;
        String path = "./Set_Connessione.txt";
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        try {
            url=in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            username=in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            password=in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Connessione con il DB
        MySQL my=new MySQL();
        Cluster c=new Cluster();
        Robot r=new Robot();
        Connection con;
        try {
            con = my.connection(url, username, password);
            con.setAutoCommit(false);
            
            //Invia un numero di pacchetti >0 e <=90000 al minuto
            
            my.close(con);
            my.writefull(username,password, url);
            r.hourly_robot(username,password, url);
            r.ir_robot(username,password, url);
            c.hourly_cluster(username,password, url);
            c.ir_cluster(username,password, url);
        } catch (SQLException ex) {
            Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
        }
        //fis.close();
    }
}
    

