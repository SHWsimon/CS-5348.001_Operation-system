/*
 * CS 5348.001
 * Team 13
 * Hao-Lun Lo
 * Hengyu Xiang
 * Shih-Han Wang
 * Xinhe Chen
 */

package edu.utdallas.taskExecutorImpl;

import java.util.ArrayList;

import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;


public class TaskExecutorImpl implements TaskExecutor
{
	private ArrayList<Thread> threadPool=new ArrayList<Thread>();
	
	
	BlockingFIFO blockingFifoQueue = null;
	TaskRunner runner= new TaskRunner();
	
	public TaskExecutorImpl(int num) {
		blockingFifoQueue=runner.getQueue();
		for(int i=0;i<num;i++) {

			threadPool.add(new Thread(runner));
			threadPool.get(i).setName("TaskThread"+i);
			threadPool.get(i).start();
		}
	}
	@Override
	public void addTask(Task task)
	{
		try {
			blockingFifoQueue.put(task);	
		}
		catch(Throwable th) {
			
		}
	}
	
	

}
