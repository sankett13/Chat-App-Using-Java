import java.net.*;
import java.util.Scanner;
import java.io.*;

class MyServer extends Thread {
    static Thread t[] = new Thread[5];

    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(1306);

        while (true) {

            Socket s = ss.accept();
            System.out.println("Server Ready to accept new connection from ");

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            Clienthandler c = new Clienthandler(s, dis, dos, br, ss);

            int i = 0;
            t[i] = new Thread(c);
            t[i].start();
            i++;

        }

    }
}

class Clienthandler implements Runnable {

    ServerSocket ss;
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    BufferedReader br;
    Scanner sc = new Scanner(System.in);

    Clienthandler(Socket s, DataInputStream dis, DataOutputStream dos, BufferedReader br, ServerSocket ss) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.br = br;
        this.ss = ss;
    }

    public void run() {
        System.out.println("new client starts here");
        System.out.println("Enter Client Name : ");
        String Username = sc.nextLine();
        String str = "", str2 = "";
        try {
            while (!str.equals("stop")) {

                str = dis.readUTF();
                System.out.println(Username + " says : " + str);
                str2 = br.readLine();
                dos.writeUTF(str2);
                dos.flush();

            }
            dis.close();
            dos.close();
            s.close();
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
