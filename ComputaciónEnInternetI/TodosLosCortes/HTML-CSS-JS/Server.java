	import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int portNumber = 8080; // Puerto en el que escuchará el servidor

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Servidor TCP iniciado. Esperando conexión...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());
                    System.out.println();
                    InputStream input = clientSocket.getInputStream();
                    byte[] message = new byte[4096];
                    input.read(message);
                    String resource = getResource(new String(message));
                    // Mensaje de respuesta
                    String responseMessage = response("."+resource);
                    System.out.println(responseMessage);
                    // Enviar mensaje de respuesta al cliente
                    OutputStream output = clientSocket.getOutputStream();
                    output.write(responseMessage.getBytes());
                    System.out.println();
                } catch (IOException e) {
                    System.out.println("Error al aceptar la conexión del cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private static String getResource(String string) {
        System.out.println(string);
        String[] parts = string.split(" ");
        return parts[1];
    }

    public static String response(String file)throws IOException{
        String response = "HTTP/1.1 404 Not found\r\nContent-Type: text/html\r\n\r\n<html><body><h1>404 Not found</h1></body></html>";
        File f = new File(file);
        if(f.isDirectory()){
            System.out.println("Es un directorio");
            f = new File(file + "/index.html");
        }
        if(!f.exists()){
            f = new File(file + ".html");
            if (!f.exists()) {
                return response;
            }
        }
        response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while((line = br.readLine()) != null){
            response += line;
        }
        br.close();
        return response;
    }

}
