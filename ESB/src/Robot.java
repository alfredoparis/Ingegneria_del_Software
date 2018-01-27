import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AD-TEAM
 */
public class Robot implements Serializable{
    private final int id_robot;
    private final int id_cluster;
    private int []sensori;
    
    public Robot(){
        this.id_cluster=0;
        this.id_robot=0;
        this.sensori=null;
    }

    public Robot(int id_robot, int id_cluster,int [] l) {
        this.id_robot=id_robot;
        this.id_cluster=id_cluster;
        sensori=l;  
    }

    public String info(){
        return "IDRobot: "+this.getIDROBOT()+"IDCluster: "+this.getIDCLUSTER()+"Sensori:"+this.info_s()+"\n";

    }

    public int getIDROBOT (){
        return this.id_robot;
    }

    public int getIDCLUSTER(){
      return this.id_cluster;
    }
    
    public int[] getSensori(){
        return this.sensori;
    }
    
    public String info_s(){
    int[]a =this.getSensori();
    String s="";
    for(int i=1;i<8;i++){
        s=s+"s"+a[i]+" ";
    }
    return s;
    }

    
   
    
    @Override
    public String toString(){
        return "IDROBOT: "+this.getIDROBOT()+"\n"+"IDcluster: "+this.getIDCLUSTER();
    }

}