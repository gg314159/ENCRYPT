package ssl;

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import org.json.JSONException;
//import javax.security.cert.X509Certificate;

import process.UserProcessing;

public class SimpleServer {

    public static void main(String args[]){
        int port = Integer.parseInt("41000");
        String passphrase = "passwords";

        ServerSocket s;
        System.out.println("USAGE: java SimpleServer port passphrase");

        try {
            System.out.println("creating socket...");
            ServerSocketFactory ssf =
		           SimpleServer.getServerSocketFactory(passphrase);
            s = ssf.createServerSocket(port);
            System.out.println("waiting for connection...");           
            while(true){
                new UserProcessing(s.accept()).start();
            }
        } catch (IOException e) {
                System.out.println("SimpleServer died: " + e.getMessage());
	          e.printStackTrace();
        }
   }

   private static ServerSocketFactory getServerSocketFactory(String passwd) {
	    SSLServerSocketFactory ssf = null;
	    try {
		// set up key manager to do server authentication
		SSLContext ctx;
		KeyManagerFactory kmf;
		KeyStore ks;
		char[] passphrase = passwd.toCharArray();

		ctx = SSLContext.getInstance("TLS");
		kmf = KeyManagerFactory.getInstance("SunX509");
		ks = KeyStore.getInstance("JKS");

		ks.load(new FileInputStream("serverkeys"), passphrase);
		kmf.init(ks, passphrase);
		ctx.init(kmf.getKeyManagers(), null, null);

		ssf = ctx.getServerSocketFactory();
		return ssf;
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    return null;
  }
}

