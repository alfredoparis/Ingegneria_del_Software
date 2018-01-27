
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AD-TEAM
 */
public class MySQL {
    
  
    
    
    /**
     * Metodo che si occupa di aprire una connessione con il DB
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection connection(String url,String user, String pass ) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection c=DriverManager.getConnection(url,user,pass);
        return c;
    }
    
    
    /**
     * 
     * Metodo che si occupa di chiudere una connessione con il DB
     * @param c
     * @throws SQLException 
     */
    public void close(Connection c) throws SQLException{
        c.close();
    }
     
    
    /**
     * Questo metodo si occupa del riempimento della tabella fullstates che contiene l'ultimo stato aggiornato di ogni robot
     * Ogni minuto preleva i dati che arrivano dal Sender alla tabella Calcolo e o li inserisce nella fullstates(se si tratta di un nuovo robot
     * oppure fa un update se si tratta di un cambio stato di un robot gia esistente
     * 
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void writefull(String user, String pass, String url) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection c=DriverManager.getConnection(url,user,pass);
        c.setAutoCommit(false);
        String fullstatenew1 = "INSERT INTO `fullstate`(`idRobot`, `idCluster`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE s1=?";
        PreparedStatement preparedStmt1 = c.prepareStatement(fullstatenew1);
        String fullstatenew2 = "INSERT INTO `fullstate`(`idRobot`, `idCluster`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE s2=?";
        PreparedStatement preparedStmt2 = c.prepareStatement(fullstatenew2);
        String fullstatenew3 = "INSERT INTO `fullstate`(`idRobot`, `idCluster`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE s3=?";
        PreparedStatement preparedStmt3 = c.prepareStatement(fullstatenew3);
        String fullstatenew4 = "INSERT INTO `fullstate`(`idRobot`, `idCluster`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE s4=?";
        PreparedStatement preparedStmt4 = c.prepareStatement(fullstatenew4);
        String fullstatenew5 = "INSERT INTO `fullstate`(`idRobot`, `idCluster`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE s5=?";
        PreparedStatement preparedStmt5 = c.prepareStatement(fullstatenew5);
        String fullstatenew6 = "INSERT INTO `fullstate`(`idRobot`, `idCluster`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE s6=?";
        PreparedStatement preparedStmt6 = c.prepareStatement(fullstatenew6);
        String fullstatenew7 = "INSERT INTO `fullstate`(`idRobot`, `idCluster`, `s1`, `s2`, `s3`, `s4`, `s5`, `s6`, `s7`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE s7=?";
        PreparedStatement preparedStmt7 = c.prepareStatement(fullstatenew7);
        String sq2="select * FROM calcolo  ORDER BY `idrobot` ASC ,`time` ASC";
        PreparedStatement p2=c.prepareStatement(sq2);
        p2.setFetchSize(5000);
        int idr; //id robot
        int idc;// id cluster
        int s1;
  	    int s2;
  	    int s3;   
  	    int s4;
  	    int s5;
  	    int s6; 
  	    int s7;
        Timestamp time=null;
        ResultSet r=p2.executeQuery();
        if(!r.first()){System.out.println("Calcolo vuota");}else{
        while(r.next()){
            idr=r.getInt(1);
            idc=r.getInt(2);
            s1=r.getInt(3);
            s2=r.getInt(4);
            s3=r.getInt(5);
            s4=r.getInt(6);
            s5=r.getInt(7);
            s6=r.getInt(8);
            s7=r.getInt(9);
            Timestamp t=r.getTimestamp(10);
            if(time==null||t.after(time)){
              time=t;
            }
            
                
            if(s1!=404){
                preparedStmt1.setInt(1, idr);
	 	            preparedStmt1.setInt(2, idc);
            	 	preparedStmt1.setInt(3, s1);
            	 	preparedStmt1.setInt(4, 1);
            	 	preparedStmt1.setInt(5, 1);
            	 	preparedStmt1.setInt(6, 1);
            	 	preparedStmt1.setInt(7, 1);
            	 	preparedStmt1.setInt(8, 1);
            	 	preparedStmt1.setInt(9, 1);
            	 	preparedStmt1.setInt(10, s1);
            	 	preparedStmt1.addBatch();
            
            }
            
            if(s2!=404){
                preparedStmt2.setInt(1, idr);
            	 	preparedStmt2.setInt(2, idc);
            	 	preparedStmt2.setInt(3, 1);
            	 	preparedStmt2.setInt(4, 1);
            	 	preparedStmt2.setInt(5, 1);
            	 	preparedStmt2.setInt(6, 1);
            	 	preparedStmt2.setInt(7, 1);
            	 	preparedStmt2.setInt(8, 1);
            	 	preparedStmt2.setInt(9, 1);
            	 	preparedStmt2.setInt(10, s2);
            	 	preparedStmt2.addBatch();
            
            }
            
            if(s3!=404){
                preparedStmt3.setInt(1, idr);
            	 	preparedStmt3.setInt(2, idc);
            	 	preparedStmt3.setInt(3, 1);
            	 	preparedStmt3.setInt(4, 1);
            	 	preparedStmt3.setInt(5, 1);
            	 	preparedStmt3.setInt(6, 1);
            	 	preparedStmt3.setInt(7, 1);
            	 	preparedStmt3.setInt(8, 1);
            	 	preparedStmt3.setInt(9, 1);
            	 	preparedStmt3.setInt(10, s3);
            	 	preparedStmt3.addBatch();
            
            }
            
            if(s4!=404){
                preparedStmt4.setInt(1, idr);
            	 	preparedStmt4.setInt(2, idc);
            	 	preparedStmt4.setInt(3, 1);
            	 	preparedStmt4.setInt(4, 1);
            	 	preparedStmt4.setInt(5, 1);
            	 	preparedStmt4.setInt(6, 1);
            	 	preparedStmt4.setInt(7, 1);
            	 	preparedStmt4.setInt(8, 1);
            	 	preparedStmt4.setInt(9, 1);
            	 	preparedStmt4.setInt(10, s4);
            	 	preparedStmt4.addBatch();
            
            }
            
            if(s5!=404){
                preparedStmt5.setInt(1, idr);
            	 	preparedStmt5.setInt(2, idc);
            	 	preparedStmt5.setInt(3, 1);
            	 	preparedStmt5.setInt(4, 1);
            	 	preparedStmt5.setInt(5, 1);
            	 	preparedStmt5.setInt(6, 1);
            	 	preparedStmt5.setInt(7, 1);
            	 	preparedStmt5.setInt(8, 1);
            	 	preparedStmt5.setInt(9, 1);
            	 	preparedStmt5.setInt(10, s5);
            	 	preparedStmt5.addBatch();
            
            }
            
            if(s6!=404){
                preparedStmt6.setInt(1, idr);
            	 	preparedStmt6.setInt(2, idc);
            	 	preparedStmt6.setInt(3, 1);
            	 	preparedStmt6.setInt(4, 1);
            	 	preparedStmt6.setInt(5, 1);
            	 	preparedStmt6.setInt(6, 1);
            	 	preparedStmt6.setInt(7, 1);
            	 	preparedStmt6.setInt(8, 1);
            	 	preparedStmt6.setInt(9, 1);
            	 	preparedStmt6.setInt(10, s6);
            	 	preparedStmt6.addBatch();
            
            }
            
            if(s7!=404){
                preparedStmt7.setInt(1, idr);
            	 	preparedStmt7.setInt(2, idc);
            	 	preparedStmt7.setInt(3, 1);
            	 	preparedStmt7.setInt(4, 12);
            	 	preparedStmt7.setInt(5, 1);
            	 	preparedStmt7.setInt(6, 1);
            	 	preparedStmt7.setInt(7, 1);
            	 	preparedStmt7.setInt(8, 1);
            	 	preparedStmt7.setInt(9, 1);
            	 	preparedStmt7.setInt(10,s7);
            	 	preparedStmt7.addBatch();
            
            }
        }
        }
        r.close();
        preparedStmt1.executeBatch();
      	preparedStmt2.executeBatch();
      	preparedStmt3.executeBatch();
      	preparedStmt4.executeBatch();
      	preparedStmt5.executeBatch();
      	preparedStmt6.executeBatch();
      	preparedStmt7.executeBatch();
        System.out.println("Aggiornamento stack completo robot...");
        String deletestatemnt = "DELETE FROM calcolo WHERE time<=time"; //In questo caso va bene così, perchè l'invio avviene in sequenza
        PreparedStatement preparedDelete = c.prepareStatement(deletestatemnt); //nel caso reale, delete timestamp < ultimo timestamp utile
      	preparedDelete.execute();
      	System.out.println("Svuotamento tabella calcolo: ");
        preparedDelete.close();
        preparedStmt1.close();
        c.close();
}
}
