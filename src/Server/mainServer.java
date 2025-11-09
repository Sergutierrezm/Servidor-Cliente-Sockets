package Server;

import java.io.*;
import java.net.*;

public class mainServer {

    public static void main(String[] args) {

        final int PORT = 5050;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado, el puerto es: " + PORT + ".Esperando conexiones....");

            //bucle para aceptar clientes uno a uno
            while (true) {

                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + clienteSocket.getInetAddress());

                //creamos los stream de entrada y salida para comunicarnos con el cliente
                //Leer la operacion del cliente
                //Enviar respuesta
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream())); 
                    PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true)
                    
                        ) {
                    
                    String linea = in.readLine(); //Lee la operacion del ciente
                        System.out.println("Mensaje recibido: " + linea);

                        //Valida si la entrada es nula
                        if (linea == null) {
                            System.out.println("ERROR: entrada vacia");
                            continue;
                        }

                        //normalizar, quita espacios al inicio/fin
                        linea = linea.trim();

                        //Buscar operador
                        int pos = -1;
                        char operador = ' ';
                        for (int i = 0; i < linea.length(); i++) {
                            char c = linea.charAt(i);
                            if (c == '+' || c == '-' || c == '*') {
                                pos = i;
                                operador = c;
                                break;
                            }
                        }

                        if (pos == -1) {
                            out.println("ERROR: Operador no encontrado");
                            System.out.println("Respuesta enviada: operador no encontrado");
                            continue;
                        }

                        //Extraer los digitos y eliminar espacios
                        String s1 = linea.substring(0, pos).trim();
                        String s2 = linea.substring(pos + 1).trim();

                        //validar que sean numeros de un digito (0-9)
                        if (s1.length() != 1 || s2.length() != 1 || !Character.isDigit(s1.charAt(0)) || !Character.isDigit(s2.charAt(0))) {
                            out.println("ERROR: Los digitos deben ser numeros entre 0 y 9");
                            System.out.println("Respuesta enviada: Digitos invalidados");
                            continue;
                        }
                        
                        // Convierte los caracteres en valores numericos
                        int num1 = Character.getNumericValue(s1.charAt(0));
                        int num2 = Character.getNumericValue(s2.charAt(0));
                        
                        //Filtra el operador dependiendo lo que se indique y realiza la operacion
                        int resultado = 0;
                        switch (operador) {
                            case '+':
                                resultado = num1 + num2;
                                break;
                            case '-':
                                resultado = num1 - num2;
                                break;
                            case '*':
                                resultado = num1 * num2;
                                break;
                            default:
                                out.println("Error: operador no encontrado");
                                continue;

                        }
                        
                        //Envio de la respuesta

                        String respuesta = "Resultado: " + resultado;
                        out.println(respuesta);
                        System.out.println("Respuesta enviada: " + respuesta);

                    }catch (IOException e) {
                        System.out.println("Error cliente" + e.getMessage());
                 }finally {
                    try{
                         clienteSocket.close();
                         }catch (IOException e){
                         
                         }
                         System.out.println("Conexion con el cliente cerrada");
                         }

                }

            }catch (IOException e){
                System.out.println("No se pudo iniciar el servidor: " + e.getMessage());
                }

        }

    }
