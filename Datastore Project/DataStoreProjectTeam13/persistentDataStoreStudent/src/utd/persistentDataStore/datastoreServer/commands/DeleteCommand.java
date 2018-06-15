package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.datastoreClient.ClientException;
import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DeleteCommand extends ServerCommand{
	
	@Override
	public void run(){
		try {
			//read in file name
			String fileName = StreamUtil.readLine(inputStream);
			
			//delete data
			FileUtil.deleteData(fileName);
			
			//output "ok" if deleted
			StreamUtil.writeLine("ok\n", outputStream);
			
		}
		catch (IOException | ServerException ex) 
		{
			StreamUtil.sendError(ex.getMessage(), outputStream);
		}
	}
}
