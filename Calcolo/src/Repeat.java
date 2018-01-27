
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AD-TEAM
 */

//Con questa classe sfruttando Quartz ripetiamo l'invio dei pacchetti ogni minuto
public class Repeat {

    public static void schedule_repeat()throws SchedulerException{    
    
    JobDetail job=JobBuilder.newJob(Invio.class).build();
        
        Trigger t1=TriggerBuilder.newTrigger().withIdentity("CronTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever()).build();
        
        Scheduler sc=StdSchedulerFactory.getDefaultScheduler();
        
        sc.start();
        sc.scheduleJob(job,t1);

    }

}