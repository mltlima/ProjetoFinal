import java.awt.BorderLayout;
import java.util.concurrent.TimeUnit;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.text.ParseException;
import java.util.HashMap;
//GUI
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Main Class
 *
 */
public class App {
	
	// Testnando UTF8 ãñóõòáÀàü
    public static void main( String[] args ) throws ParseException{    
        
/*
        
        new View();

        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View menu = new View();
					menu.setFrameVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        */
     
    DadosApi teste = new DadosApi();
    teste.start();
    teste.copy();
    //teste.test();
    teste.rankingMortalidade();
    /*try {
		TimeUnit.SECONDS.sleep(60);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
    }

	public static void topNumeros(char opcoes, boolean tsv, boolean csv, String dataInicial, String dataFinal) {
		// TODO Auto-generated method stub
		System.out.println("topNumeros funcionando:");
		System.out.println("opcoes = " + (int) opcoes);
		System.out.println("tsv = " + tsv);
		System.out.println("csv = " + csv);
		System.out.println("dataInicial" + dataInicial);
		System.out.println("dataFinal" + dataFinal);
		System.out.println();
	}
	public static void topCrescimento(char opcoes, boolean tsv, boolean csv, String dataInicial, String dataFinal) {
		// TODO Auto-generated method stub
		System.out.println("topCrescimento funcionando");
		System.out.println("opcoes = " + (int) opcoes);
		System.out.println("tsv = " + tsv);
		System.out.println("csv = " + csv);
		System.out.println("dataInicial" + dataInicial);
		System.out.println("dataFinal" + dataFinal);
		System.out.println();
	}

	public static void topMortalidade(boolean tsv, boolean csv, String dataInicial, String dataFinal) {
		// TODO Auto-generated method stub
		System.out.println("topMortalidade funcionando");
		System.out.println("tsv = " + tsv);
		System.out.println("csv = " + csv);
		System.out.println("dataInicial" + dataInicial);
		System.out.println("dataFinal" + dataFinal);
		System.out.println();
	}

	public static void topLocal(boolean tsv, boolean csv, double km, String dataInicial, String dataFinal) {
		// TODO Auto-generated method stub
		System.out.println("topLocal funcionando");
		System.out.println("tsv = " + tsv);
		System.out.println("csv = " + csv);
		System.out.println("dataInicial" + dataInicial);
		System.out.println("dataFinal" + dataFinal);
	}
}