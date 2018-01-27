import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
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
class Sender{

/**
     * Questo metodo simula il Sender.
     * Invia ogni minuto dei pacchetti contenenti o il cambio stato di un robot o i dati di un nuovo robot da inserire nel sistema
     * 
     * 
     * @throws java.io.IOException
     */
    public void change() throws IOException, ClassNotFoundException{
     Random r=new Random();
     int j=0;
     int port=7777;
     int port_number = 7776;
     int t = 0;
     System.out.println("[0]Iniziallizzo  il client");
     Socket server=new Socket(InetAddress.getLocalHost(),port_number);
     Socket server2=new Socket(InetAddress.getLocalHost(),port);
     ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
     DataOutputStream out=new DataOutputStream(server2.getOutputStream());
     System.out.println("[1]-Connesso al server...");
     int w=r.nextInt(30001)+60000;
     out.writeInt(w);
     //String m=Integer.toString(w);
        for(int i=0;i<w;i++){
            t=r.nextInt(7);
            System.out.println("[--]-Numero cicli inviato:"+w);
            j=j+1;
            int idrobot=r.nextInt(90001)+1;
            int idc=r.nextInt(100)+1;
            int []a =new int[7];
            for(int x=0;x<7;x++){
                a[x]=404;
            }
            if(j<6000){
                a[t]=1;
            }else{
                a[t]=0;
                j=0;
            }
            
            Robot robo=new Robot(idrobot,idc,a);
            System.out.println("Robot creato");
            oos.writeObject(robo);
            System.out.println("Robot inviato: ");
        //}
        //oos.writeObject("CIAO");
        //out.writeBytes("Ciao io sono il client\n");
        System.out.println("[2]-Scritto al server");
        /*ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
        String ricevuto=(String) ois.readObject();
        System.out.println("[3]-Ricevuto: "+ricevuto);*/
        //ois.close();
        }
        oos.close();
        
    }
        
}

