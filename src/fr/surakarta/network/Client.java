package fr.surakarta.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * <b>Class représentant un client souhaitant se connecter au serveur en local</b>
 *
 * @author Billy MORTREUX, Yann MALAQUIN
 */
public class Client {
    public static void main(String[] args) {

        System.out.println("Server");
        try {
            InetAddress add = InetAddress.getLocalHost();
            Socket s = new Socket(add, 4242);

            //pour récupérer l'envoie du message fourni par le serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(in.readLine());
            s.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
