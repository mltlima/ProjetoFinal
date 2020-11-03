import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * Camada "View" do projeto, usuário irá colocar o input aqui. Todas as opções que o controle utilizar virão daqui
 * @author JoÃ£o Henrique Schmidt
 *
 */
public class GUI implements ActionListener {
	private static int contador;
	private static JLabel label;
	private static JFrame frame;
	private static JPanel janela;
	private static JButton botao;
	/**
	 * Cria Menu principal que será usado para interação com o usuário
	 */
	public GUI() {
        //"body"
        frame = new JFrame();
        label = new JLabel("Texto bom aqui");
        botao = new JButton("haha botao");
        botao.addActionListener(this);
		
        //Create and set up the window.
        janela = new JPanel();
        janela.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        janela.setLayout(new GridLayout(0,1));
        janela.add(botao);
        janela.add(label);
        
        //"head"
        frame.add(janela, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Projeto Final");
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		contador++;
		label.setText("contador: "+contador);
	}

}
