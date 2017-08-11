import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.Log;

public class FT{
	//file path and suffix,should be different
	public static final String PICPATH = "/home/mottled/openpose/examples/media/";
	public static final String PIC_SUFFIX = ".jpg";
	public static final String VERIFYPATH = "/home/mottled/openpose/verifypic/";
	public static final String VERIFY_SUFFIX = ".txt";
	public static final String JSON_PATH = "/home/mottled/openpose/keyp/";
	public static final String JSON_SUFFIX = "_keypoints.json";
//	public static final String PICPATH = "D:/pic/";
//	public static final String PIC_SUFFIX = ".jpeg";
//	public static final String VERIFYPATH = "D:/verify/";
//	public static final String VERIFY_SUFFIX = ".txt";
//	public static final String JSON_PATH = "D:/json/";
//	public static final String JSON_SUFFIX = "_keypoints.json";
	public static final int SOCKET_ACCEPT = 200;
	public static final int SOCKET_REFUSE = 404;
	public static final int NOT_EXISTS = 400;
	long mCounter=0;//how much picture server has received
	boolean isRun ;//enable server 
	int port;//server port
	ServerSocket ssocket;//server socket
	//read and write stream
	
	Socket clientSocket;
	
	public static void main(String[] args) {
		Log.setLogPriority(1);
		FT mServer = new FT(9999);
		mServer.start();
	}
	
	public FT(int port){
		isRun = true;
		this.port = port;
		Log.out("The server is set up.", 5);
	}
	/**
	 * to turn on the server.
	 * accept socket request,but only one,print SOCKET_ACCEPT.
	 * print SOCKET_REFUSE to the socket which was refused.
	 */
	public void start(){
		Log.out("Start Service...listening on " + port, 5);
		try{
			//set up
			ssocket = new ServerSocket(port);
		}catch(Exception e){
			Log.out("Can't open the port", 5);
			e.printStackTrace();
		}
		try{
			while(isRun){
				//accept socket request,but only one.
				Socket socket = ssocket.accept();
				Log.out("Got a socket from "+socket.getLocalAddress(), 5);
				//if no client now
				if(clientSocket == null){
					Log.out("Handle as client.", 4);
					clientSocket = socket;
					new Thread(new ServiceReciever(this, socket)).start();
					
				}else{
					Log.out("The server is already in use,refuse the request.",2);
					new DataOutputStream(socket.getOutputStream()).writeInt(SOCKET_REFUSE);
				}

			}
			
		}catch(Exception e){
			Log.out("There are something wrong when get the socket.", 5);
			e.printStackTrace();
		}

	}
	

	
	/**
	 * close the server,but it will still open until it complete the last request
	 */
	public void safeClose(){
		this.isRun = false;
	}
	
	/**
	 * 
	 */
	public void addID(){
		mCounter++;
	}
	
	public long getID(){
		return mCounter;
	}
	
	public void closeClient(){
		if(clientSocket!=null&&!clientSocket.isClosed())
		try {
			clientSocket.close();
			Log.d("socket close");
		} catch (IOException e) {
			Log.e("error on close socket");
		}
		clientSocket = null;
	}
	
}
