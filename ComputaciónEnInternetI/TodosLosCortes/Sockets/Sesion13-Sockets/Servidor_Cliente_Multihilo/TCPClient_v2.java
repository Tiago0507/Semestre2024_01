import java.net.*;
import java.io.*;

public class TCPClient_v2 {
    public static void main(String[] args) throws IOException {
        String serverHostname = "localhost"; //127.0.0.1
        Socket socket = new Socket(serverHostname, 6789);
        PrintWriter out;
        BufferedReader in; 
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        //mensaje 1
        out.println("Hola, servidor.");      
        String respuestaServidor = in.readLine();
        System.out.println("Respuesta del servidor: " + respuestaServidor);
        //mensaje 2
        out.println("Esperando...."); 
        respuestaServidor = in.readLine();
        System.out.println("Respuesta del servidor: " + respuestaServidor);

        socket.close();
    }
}
