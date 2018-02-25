package secure.app.chat;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginServer {
	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.createRegistry(1094);
			LoginImplement lp = new LoginImplement();
			reg.rebind("login", lp);
			System.out.println("Server is Running");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
