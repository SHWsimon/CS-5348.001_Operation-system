package utd.persistentDataStore.datastoreClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DatastoreClientImpl implements DatastoreClient
{
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	private InetAddress address;
	private int port;

	public DatastoreClientImpl(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
		
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.String, byte[])
	 */
	@Override
    public void write(String name, byte data[]) throws ClientException, ConnectionException
	{
		logger.debug("Executing Write Operation");
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Writing data");
			
			StreamUtil.writeLine("write\n", outputStream);
			StreamUtil.writeLine(name,outputStream);
			StreamUtil.writeLine(Integer.toString(data.length), outputStream);
			
			logger.debug("data length "+data.length);
			StreamUtil.writeData(data, outputStream);
			logger.debug("Write Response");
			String result = StreamUtil.readLine(inputStream);
			logger.debug("Response " + result);
			StreamUtil.closeSocket(inputStream);
	
		}
		catch (IOException ex) {
			throw new ConnectionException(ex.getMessage(), ex);
		}
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.String)
	 */
	@Override
    public byte[] read(String name) throws ClientException, ConnectionException
	{
		logger.debug("Executing Read Operation");
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Read data");
			StreamUtil.writeLine("read\n", outputStream);
			StreamUtil.writeLine(name, outputStream);
			
			logger.debug("Reading Response");
			String result = StreamUtil.readLine(inputStream);
			logger.debug("Response " + result);
			if(result.equals("No File Found "+name)) {
				throw new ClientException("No file found");
			}
			String size = null;
			size = StreamUtil.readLine(inputStream);
			logger.debug("Size " + size);
			int	length=Integer.parseInt(size);
			byte bytes[] = new byte[length];
		    for(int i = 0; i < length; i++) {
		    	int ch = inputStream.read();
		    	bytes[i] = (byte)ch;
		    }
		    StreamUtil.closeSocket(inputStream);
		    return bytes;
	
		}
		catch (IOException ex) {
			throw new ConnectionException(ex.getMessage(), ex);
		}
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.String)
	 */
	@Override
    public void delete(String name) throws ClientException, ConnectionException
	{
		logger.debug("Executing Delete Operation");
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("delete data");
			StreamUtil.writeLine("delete\n", outputStream);
			StreamUtil.writeLine(name, outputStream);
			logger.debug("Reading Response");
			String result = StreamUtil.readLine(inputStream);
			if(result.equals("No File Found "+name)) {
				throw new ClientException("No file found");
			}
			logger.debug("Response " + result);
			
			StreamUtil.closeSocket(inputStream);
	
		}
		catch (IOException ex) {
			throw new ConnectionException(ex.getMessage(), ex);
		}
		 
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
	 */
	@Override
    public List<String> directory() throws ClientException, ConnectionException
	{
		logger.debug("Executing Directory Operation");
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("directory");
			StreamUtil.writeLine("directory\n", outputStream);

			logger.debug("Reading Response");
			String result = StreamUtil.readLine(inputStream);
			logger.debug("Response " + result);
			String size = StreamUtil.readLine(inputStream);
			logger.debug("Size " + size);
			List<String> directory = new ArrayList<String>();
		    for(int i = 0; i < Integer.parseInt(size); i++) {
		    String name = StreamUtil.readLine(inputStream);
		    	directory.add(name);
		    }
		    StreamUtil.closeSocket(inputStream);
		    return directory;
	
		}
		catch (IOException ex) {
			throw new ConnectionException(ex.getMessage(), ex);
		}	
	}

}
