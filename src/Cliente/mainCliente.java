package Cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class mainCliente {

   
    public static void main(String[] args) {
       final String HOST  = "localhost";
       final int PORT = 5050;
       
       try (Socket socket = new Socket (HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in)) {
           
           System.out.println("Conectado al servidor" + HOST + PORT);
           System.out.println("Introduce una operacion");
           String operacion = sc.nextLine().trim();
           
           //Enviamos la operacion al servidor
           out.println(operacion);
           
           //Leemos la respuesta del servidor
           
          String respuesta = in.readLine();
          if (respuesta != null){
              System.out.println("Servidor dice:  " + respuesta);
          }else {
              System.out.println("No se recibio respuesta del servidor");
          }
            
       
    }catch (IOException e){
        System.out.println("Error de conexion: " + e.getMessage());
}
    
}
}
