package secure.app.chat;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class LoginImplement extends UnicastRemoteObject implements LoginInterface{

	public LoginImplement() throws RemoteException {
		
	}
	
	@Override
	public boolean getLogin(String user, String pass) throws RemoteException {
		boolean found=false;
		try {
			if(user.equals("fs1g15") && pass.equals("test")) {
				return found=true;
			} else if (user.equals("dsku14") && pass.equals("asd")) {
				return found=true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return found;
	}

}
