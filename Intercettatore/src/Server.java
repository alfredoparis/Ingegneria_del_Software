/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

/**
 *
 * @author AD_TEAM
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        FileInputStream fis = null;
       
          String path = "./Set_Connessione.txt";
          fis = new FileInputStream(path);
          BufferedReader inn = new BufferedReader(new InputStreamReader(fis));
          String url;
          String username;
          String password;
          url=inn.readLine() ;
          username= inn.readLine();
          password= inn.readLine();
        Random r1=new Random();
        Class.forName("com.mysql.jdbc.Driver");
        Connection c=(Connection) DriverManager.getConnection(url,username,password);
        c.setAutoCommit(false);
        String sql="insert into calcolo VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = (PreparedStatement) c.prepareStatement(sql);
        String sql2="INSERT IGNORE INTO robot VALUES(?,?,?)";
        PreparedStatement prep=(PreparedStatement) c.prepareStatement(sql2);
       System.out.println("[0-]Inizializzo il Server");
       ServerSocket s_server;
       ServerSocket s2;
       int port=7777;
       int port_number = 7776;
       s2=new ServerSocket(port);
       s_server=new ServerSocket(port_number);
       while(true){
            System.out.println("[1]- Server pronto, in ascolto sulla porta :"+port_number);
            Socket client=s_server.accept();
            Socket client2=s2.accept();
            DataInputStream in=new DataInputStream(client2.getInputStream());
            int j=in.readInt();
            System.out.println("[3]-Numero cicli ricevuto: "+j);
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            for(int i=0;i<j;i++){
                System.out.println("[2]Connessione stabilita con il client");
                System.out.println("[3]Aspetto un messaggio dal client..");
                Robot r=(Robot)ois.readObject();
                System.out.print("Letto :" + r.info()); 
                int idrobot=r.getIDROBOT();
                prep.setInt(2, idrobot);
                preparedStmt.setInt(1,idrobot);
                int idc=r1.nextInt(100)+1;
                preparedStmt.setInt(2,idc);
                prep.setInt(1, idc);
                prep.setInt(3, 0);
                prep.addBatch();
                int []a=r.getSensori();
                preparedStmt.setInt(3,a[0]);
                preparedStmt.setInt(4,a[1]);
                preparedStmt.setInt(5,a[2]);
                preparedStmt.setInt(6,a[3]);
                preparedStmt.setInt(7,a[4]);
                preparedStmt.setInt(8,a[5]);
                preparedStmt.setInt(9,a[6]);
                preparedStmt.setTimestamp(10,new java.sql.Timestamp(new java.util.Date().getTime()));
                preparedStmt.addBatch();  
            }
            preparedStmt.executeBatch();
            prep.executeBatch();
             ois.close();
       client.close();
       }
    }
    
}
