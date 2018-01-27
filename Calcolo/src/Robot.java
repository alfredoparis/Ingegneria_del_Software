
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AD-TEAM
 */
public class Robot {
   
 /**
    * Questo metodo ha lo stesso compito di hourly_cluster ma lo fa per i Robot
    * 
    * @throws ClassNotFoundException
    * @throws SQLException 
    */
    
    public void hourly_robot(String user, String pass, String url) throws ClassNotFoundException, SQLException{
    
        Class.forName("com.mysql.jdbc.Driver");
        Connection c=DriverManager.getConnection(url,user,pass);
        c.setAutoCommit(false);
        String d="DELETE FROM hourly_robot WHERE time<(CURRENT_TIMESTAMP-INTERVAL 1 HOUR)";
        PreparedStatement del=c.prepareStatement(d);
        del.execute();
        String sql4="Insert into hourly_robot values(?,?,?,?)";
        String sql3="SELECT idCluster,s1,s2,s3,s4,s5,s6,s7 FROM `fullstate` where  idRobot=(?)  ORDER BY `time` ASC";
        PreparedStatement pr=c.prepareStatement(sql4);
        PreparedStatement pre=c.prepareStatement(sql3);
        pre.setFetchSize(5000);
        for(int i=1;i<900001;i++){  //robot count
            pre.setInt(1, i);
            ResultSet result=pre.executeQuery();
            pr.setInt(1, i);
            pr.setInt(2,0);
            pr.setInt(3,0);
            pr.setTimestamp(4,new java.sql.Timestamp(new java.util.Date().getTime()));
            while(result.next()){
                ArrayList a=new ArrayList(7);
                for(int ii=1;ii<8;ii++){
                    a.add(result.getInt(ii+1));
                }
                if(a.contains(0)){
                    pr.setInt(3, 1);
                    pr.setInt(2, result.getInt(1));
                pr.addBatch();
                }
                
            }
        }
        pr.executeBatch();
        System.out.println("Inserimento su tabella ROBOT oraria...");
        pr.close();
        pre.close();
        c.commit();
        c.close();
}


/**
     * Questo metodo esegue il calcolo dell'Ir per ogni Robot
     * 
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void ir_robot(String user, String pass, String url) throws ClassNotFoundException, SQLException{
        //Connessione DB
        Class.forName("com.mysql.jdbc.Driver");
        Connection c=DriverManager.getConnection(url,user,pass);
        c.setAutoCommit(false);
        String sq="INSERT into robot (idrobot, idcluster, IRrobot) VALUES(?,?,?) on duplicate key UPDATE idCluster=(?) ,IRrobot=(?)";
        String sq2="select * from hourly_robot  ORDER BY `idRobot` ASC";
        PreparedStatement p2=c.prepareStatement(sq2);
        PreparedStatement p=c.prepareStatement(sq);
        p2.setFetchSize(5000);
        ResultSet rs=p2.executeQuery();
        double t=0;
        rs.first();
        int idr=rs.getInt(1);
        int idc=rs.getInt(2);
        t=1;
        while(rs.next()){
            int idr2=rs.getInt(1);
            if(idr!=idr2){
                t=((t/60)*100);
                p.setInt(1,idr);
                p.setInt(2,idc);
                 p.setInt(3,(int)t);
                 p.setInt(4,idc);
                p.setInt(5,(int)t);
                p.addBatch();
                idr=idr2;
                t=1; 
            }
            else{t=t+1;}
            idc= rs.getInt(2);
        }
        p.setInt(1, idr);
        p.setInt(2, idc);
        p.setInt(3,(int)t);
        p.setInt(4,idc);
        p.setInt(5,(int)t);
        p.addBatch();
        p2.close();
        p.executeBatch();
        p.close();
        System.out.println("IR Robot calcolato!");
        c.close();
    }



















}
