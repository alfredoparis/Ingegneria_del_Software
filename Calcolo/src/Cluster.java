
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AD-TEAM
 */
public class Cluster {
     
	
	public void insertcluster(Connection c) throws SQLException{
           Random r=new Random();
           String  sql = "insert ignore into cluster VALUES (?,?)";
           
           PreparedStatement preparedStmt = c.prepareStatement(sql);
        //Crea e inserice 100 cluster nel DB
           for (int i=1;i<101;i++){    
                  preparedStmt.setInt (1,i);
                  preparedStmt.setInt (2,0);
                  preparedStmt.addBatch();
              }   
            preparedStmt.executeBatch();
            
            
	}






/**
     * Questo metodo per prima cosa ogni minuto elimina(se ci sono) i dati piu vecchi di un'ora
     * poi prende l'ultimo stato aggiornato di ogni robot di ogni cluster e controllo se c'è un robot in stato di down.
     * Se c'è inserisce un nuovo record nella tabella hourly_cluster 
     * 
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void hourly_cluster (String user, String pass, String url) throws ClassNotFoundException, SQLException {
        //Connessione DB
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection c=DriverManager.getConnection(url,user,pass);
    
        c.setAutoCommit(false);
        //Toglie i record piu vecchi di 1 ora
        String d="DELETE FROM hourly_cluster WHERE time<(CURRENT_TIMESTAMP-INTERVAL 1 HOUR)";
        PreparedStatement del=c.prepareStatement(d);
        del.execute();
        String sql4="Insert into hourly_cluster values(?,?,?)";
        String sql3="SELECT s1,s2,s3,s4,s5,s6,s7 FROM `fullstate` where  idCluster=(?)  ORDER BY `time` ASC";
        PreparedStatement pr=c.prepareStatement(sql4);
        PreparedStatement pre=c.prepareStatement(sql3);
        pre.setFetchSize(5000);
        int s1=0;
        int s2=0;
        int s3=0;
        int s4=0;
        int s5=0;
        int s6=0;
        int s7=0;
        //Vede ogni minuto lo stato dei robot di ogni cluster e inserisce 1 alla hourly_cluster se almeno un robot è down
        for(int i=1;i<101;i++){
            pre.setInt(1, i);
            ResultSet result=pre.executeQuery();
            pr.setInt(1, i);
            pr.setInt(2,0);
            pr.setTimestamp(3,new java.sql.Timestamp(new java.util.Date().getTime()));
            while(result.next()){
                if(result.getInt(1)==0 ||result.getInt(2)==0 ||result.getInt(3)==0 ||result.getInt(4)==0 ||result.getInt(5)==0 ||result.getInt(6)==0 ||result.getInt(7)==0){
                    pr.setInt(2, 1);
                    pr.addBatch();
                    break;
                }
                /*ArrayList a=new ArrayList(7);
                for(int ii=0;ii<7;ii++){
                    a.add(result.getInt(ii+1));
                }
                if(a.contains(0)){
                    pr.setInt(2, 1);
                    pr.addBatch();
                    break;
                }*/
                
                
                
            }
            
    }
        pr.executeBatch();
        pr.close();
        pre.close();
        del.close();
        c.commit();
        c.close();
        System.out.println("Inserimento in tabella CLUSTER oraria...");
        
}
        

/**
 * Metodo che si occupa del calcolo dell'ir per cluster
 * 
 * @throws SQLException
 * @throws ClassNotFoundException 
 */
public void ir_cluster(String user, String pass, String url) throws SQLException, ClassNotFoundException{
        //Connessione DB
      
        Class.forName("com.mysql.jdbc.Driver");
        Connection c=DriverManager.getConnection(url,user,pass);
        c.setAutoCommit(false);
        String sq="INSERT into cluster (idCluster) VALUES(?) on duplicate key UPDATE IRcluster=(?)";
        String sq2="select * from hourly_cluster  ORDER BY `idCluster` ASC";
        PreparedStatement p2=c.prepareStatement(sq2);
        PreparedStatement p=c.prepareStatement(sq);
        p2.setFetchSize(5000);
        //Prendo dalla hourly_cluster i dati sullo stato di ogni cluster nell'ultima ora
        ResultSet rs=p2.executeQuery();
        double t=0;
        //Calcolo ir cluster 
        rs.first();
        int idc=rs.getInt(1);
        t=1;
     
        while(rs.next()){
            int idc2=rs.getInt(1);
            if(idc==idc2){
                t=t+1; 
            }
            else{
                t=((t/60)*100);
                p.setInt(1,idc);
                p.setInt(2,(int)t);
                p.addBatch();
                idc=idc2;
                t=1;
            }
        }
        t=((t/60*100));
        p.setInt(1, idc);
        p.setInt(2, (int)t);
        p.addBatch();
        p2.close();
        p.executeBatch();
        p.close();
        System.out.println("IR Cluster Calcolato!");
        System.out.println("Fine della routine, in attesa della prossima...");
        System.out.println("...");
        c.close();
}
}
