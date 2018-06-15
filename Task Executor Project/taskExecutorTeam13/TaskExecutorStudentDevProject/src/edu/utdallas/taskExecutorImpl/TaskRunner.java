/*
 * CS 5348.001
 * Team 13
 * Hao-Lun Lo
 * Hengyu Xiang
 * Shih-Han Wang
 * Xinhe Chen
 */


package edu.utdallas.taskExecutorImpl;
import edu.utdallas.blockingFIFO.BlockingFIFO;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable{

	BlockingFIFO blockingFifoQueue = new BlockingFIFO();
	
	@Override
	public void run() {
		while(true) {
			Task newTask = blockingFifoQueue.take();
			try {
				newTask.execute();
			}
			catch(Throwable th) {
				System.out.println("Error");
			}
		}
	}
	
	public BlockingFIFO getQueue() {
		return blockingFifoQueue;
	}
}
