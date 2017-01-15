/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getip;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfernandes
 */
public class GetIP {

    static int timeOut = 3600;
    static String siteParaPegarIP = "http://meuip.ciasc.gov.br/";
    static String salvarEm = "/home/mfernandes/meuip.txt";

    public static String mailDe = "casamentodemiqueiasealda@gmail.com";
    public static String mailPara = "contatomiqueiasfernandes.postarnosite@blogger.com, contatomiqueiasfernandes@gmail.com";
    public static String tipo = "text/html";
    public static String mailPassword = "********";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here

        System.out.println("Digite a senha para o email " + mailDe + " e aperte ENTER:");

        Scanner sc = new Scanner(System.in);

        mailPassword = sc.nextLine();

        int time = 0;

        while (true) {

            time++;

            if (time != 1) {
                Thread.sleep(1000);
                if (time > 3600) {
                    time = 0;
                }
                System.out.println("atualizando IP em " + (timeOut - time));
                continue;
            }

            System.out.println("baixando arquivo");
            try {
                InputStream s = Runtime.getRuntime().exec("wget " + siteParaPegarIP + " -O " + salvarEm).getInputStream();
                sc = new Scanner(s);
                while (sc.hasNext()) {
                    String next = sc.next();
                    System.out.println(">" + next);
                }
            } catch (IOException ex) {
                Logger.getLogger(GetIP.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("imprimindo arquivo");
            Scanner s;
            String ip = "erro";
            try {
                s = new Scanner(new FileReader(salvarEm));
                while (s.hasNext()) {
                    String next = s.next();
                    if (next.matches(".*\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}")) {
                        System.err.println(next);
                        ip = next;
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GetIP.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("ip: " + ip);

            JavaMailApp.sendMainl("ip autalizado para " + ip, "<script>window.location.href =\"http://" + ip + ":8080\";</script>");

        }
    }

}
