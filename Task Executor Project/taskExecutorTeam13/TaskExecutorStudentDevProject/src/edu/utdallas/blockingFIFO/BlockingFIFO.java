/*
 * CS 5348.001
 * Team 13
 * Hao-Lun Lo
 * Hengyu Xiang
 * Shih-Han Wang
 * Xinhe Chen
 */

package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;
//import java.util.concurrent.BlockingQueue;

public class BlockingFIFO {
	
	//create array buffer size of 100
	int BUFFER_SIZE=100;
	Task[] buffer= new Task[BUFFER_SIZE];
	int nextin, nextout;
	int count=0;
	Object notfull = new Object();
	Object notempty = new Object();
	
	public void put(Task task)
	{
		while(true) {
			if(count == BUFFER_SIZE) {
				synchronized(notfull) {
				try {
					notfull.wait();
				} catch (InterruptedException ex) {
					System.err.println(ex.getLocalizedMessage());
				}
				}
			}
			
			else {
				synchronized(this){
					buffer[nextin] = task;
					nextin = (nextin+1)%BUFFER_SIZE;
					count++;	
				}
				synchronized(notempty){
					notempty.notify();
					}
				break;
			}
		}
	}
	
	public Task take(){
		Task result;
		while(true) {
			if(count==0) {
				synchronized(notempty) {
					try {
						notempty.wait();
					} 
					catch (InterruptedException ex) {
						System.out.println("need to wait, but sth went wrong");
					}
				}
			}
			else {
				synchronized(this) {
					result = buffer[nextout];
					nextout = (nextout+1)%BUFFER_SIZE;
					count--;
				}
			synchronized(notfull) {
				notfull.notify();
				}
			return result;
			}	
		}
	}
}
