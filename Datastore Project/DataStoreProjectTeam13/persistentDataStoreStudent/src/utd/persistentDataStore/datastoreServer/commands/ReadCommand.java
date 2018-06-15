package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.datastoreClient.ClientException;
import utd.persistentDataStore.datastoreClient.ConnectionException;
import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class ReadCommand extends ServerCommand{
	@Override
	public void run()  {
		try {
			//read in file name
			String fileName = StreamUtil.readLine(inputStream);
			
			//read in data
			byte data[] = FileUtil.readData(fileName);
			
			
			//output "ok", data length, and data
			
			StreamUtil.writeLine("ok\n",outputStream);
			StreamUtil.writeLine(Integer.toString(data.length),outputStream);
			StreamUtil.writeData(data, outputStream);
		}
		catch(IOException | ServerException ex)
		{
			try {
				throw new ClientException(ex.getMessage(),ex);
			} catch (ClientException e) {
				StreamUtil.sendError(e.getMessage(), outputStream);
			}
			//StreamUtil.sendError(ex.getMessage(), outputStream);
		}
			
		
		
	}
}
