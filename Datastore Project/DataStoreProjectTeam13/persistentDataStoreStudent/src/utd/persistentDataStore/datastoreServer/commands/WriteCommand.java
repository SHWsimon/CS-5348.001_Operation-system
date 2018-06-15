package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.FileUtil;

import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand{
	
	@Override
	public void run() {
		try {
			//read in file name, data length
			String fileName = StreamUtil.readLine(inputStream);
			String dataLength = StreamUtil.readLine(inputStream);
			
			//read in data
			byte data[] = StreamUtil.readData(Integer.parseInt(dataLength), inputStream);
			
			//output "ok"
			StreamUtil.writeLine("ok", outputStream);
			
			//Write data to file
			FileUtil.writeData(fileName, data);
			
		}
		catch (IOException ex)
		{
			StreamUtil.sendError(ex.getMessage(), outputStream);
		}
	}
}