
import java.io.IOException;
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
@Override
public void execute(JobExecutionContext jec) throws JobExecutionException {
    Sender s=new Sender();
    try {
        s.change();
    } catch (IOException ex) {
        Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(Invio.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
}
    

