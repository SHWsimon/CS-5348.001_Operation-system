package utd.persistentDataStore.datastoreServer.commands;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utd.persistentDataStore.utils.FileUtil;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DirectoryCommand extends ServerCommand{
	
	@Override
	public void run() {
		try {
			//read in directory
			List<String> directory = new ArrayList<String>();
			directory = FileUtil.directory();
			
			//output "ok", number of file names, and file names
			StreamUtil.writeLine("ok\n", outputStream);
			StreamUtil.writeLine(Integer.toString(directory.size()), outputStream);
			for (int i=0; i<directory.size();i++) {
				StreamUtil.writeLine(directory.get(i), outputStream);
			}
			

		}
		catch (IOException | ServerException ex)
		{
			StreamUtil.sendError(ex.getMessage(), outputStream);
		}
	}
}