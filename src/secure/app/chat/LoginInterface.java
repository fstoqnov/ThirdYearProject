package secure.app.chat;

import java.rmi.*;

public interface LoginInterface extends Remote {
	
	public boolean getLogin(String user, String pass) throws RemoteException;	
}
