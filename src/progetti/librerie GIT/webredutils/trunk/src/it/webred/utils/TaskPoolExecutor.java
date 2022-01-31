package it.webred.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

public class TaskPoolExecutor  {
	ForkJoinPool pool = new ForkJoinPool();
	List<ForkJoinTask> tasks = new ArrayList<ForkJoinTask>();
	
	public void addTask(ForkJoinTask task) {
		tasks.add(task);
	}

	
	/**
	 * 
	 * @return true if one or more tasks completed abnormally
	 * @throws InterruptedException
	 */
	public boolean execute() throws InterruptedException {
		boolean finito;
		boolean abnormal;
		
		if(tasks.isEmpty()){
			finito = true;
			abnormal = false;
		}else{
		
			for (ForkJoinTask forkJoinTask : tasks) {
				pool.execute(forkJoinTask);
			}
		
			 do
		      {
				 
	/*			 System.out.printf("******************************************\n");
		         System.out.printf("Parallelism: %d\n", pool.getParallelism());
		         System.out.printf("PoolSize: %d\n", pool.getPoolSize());
		         System.out.printf("Active Threads: %d\n", pool.getActiveThreadCount());
		         System.out.printf("Task Count: %d\n", pool.getQueuedTaskCount());
		         System.out.printf("Steal Count: %d\n", pool.getStealCount());
		         System.out.printf("******************************************\n");*/
		         
		 		finito=false;
		 		abnormal = false;
	            TimeUnit.SECONDS.sleep(1);
	    		for (ForkJoinTask forkJoinTask : tasks) {
	    			finito = forkJoinTask.isDone();
	    			if (forkJoinTask.isCompletedAbnormally())
	    				abnormal = true;
	    			if(!finito)
	    				break;
	    		}
		      } while (!finito) ;
			
	 		for (ForkJoinTask forkJoinTask : tasks) {
				finito = forkJoinTask.isDone();
				if(!finito)
					break;
			}
		}
		pool.shutdown(); 
		
		return abnormal;
		

	}
}
