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
        
        
    }

}