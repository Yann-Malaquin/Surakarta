package fr.surakarta.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

/**
 * <b>Class établissant un serveur en local</b>
 *
 * @author Billy MORTREUX, Yann MALAQUIN
 */
public class Server {
    public static void main(String[] args) {


        try {
            //on récupère l'adresse local
            InetAddress add = InetAddress.getLocalHost();
            //on précise le port, le nombre de joueur pouvant s'y connecté et bien sûr l'adresse
            ServerSocket sS = new ServerSocket(4242, 2, add);
            //au bout de 20 secondes sans réponse, le serveur se coupe
            sS.setSoTimeout(20000);

            Socket s = sS.accept();

            //message envoyé lors de la connexion au serveur
            PrintWriter out = new PrintWriter(s.getOutputStream());
            out.println("Réponse du server");
            out.flush();

            sS.close();
            s.close();

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
