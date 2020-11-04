import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

/**
 * Lançamento do menu principal pelo swing do java
 * Daqui sairão todas as opções do input do usuário
 * @author JoÃ£o Henrique Schmidt
 */

public class MainMenu {

	private JFrame frame;
	private JTextField km;
	private JTextField textSessions;
	private static String[] opcoes = {"casos","mortes","recuperados"};
	private JTextField dataInicial;
	private JTextField dataFinal;
	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Projeto Final");
		frame.setBounds(100, 100, 311, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JList list3 = new JList(opcoes);
		list3.setVisibleRowCount(3);
		list3.setBackground(Color.WHITE);
		list3.setBounds(30, 153, 232, 54);
		frame.getContentPane().add(list3);
		
		JList list4 = new JList(opcoes);
		list4.setVisibleRowCount(3);
		list4.setBackground(Color.WHITE);
		list4.setBounds(30, 62, 232, 54);
		frame.getContentPane().add(list4);
		
		
		
		//CheckBoxes
		
		JCheckBox checkBox1 = new JCheckBox(".CSV");
		checkBox1.setBounds(89, 413, 68, 23);
		frame.getContentPane().add(checkBox1);
		
		JCheckBox checkBox2 = new JCheckBox("Maior número de:");
		checkBox2.setBounds(30, 32, 130, 23);
		frame.getContentPane().add(checkBox2);
		
		JCheckBox checkBox3 = new JCheckBox("Maior crescimento de:");
		checkBox3.setBounds(30, 123, 154, 23);
		frame.getContentPane().add(checkBox3);
		
		JCheckBox checkBox4 = new JCheckBox("Maior mortalidade");
		checkBox4.setBounds(30, 214, 154, 23);
		frame.getContentPane().add(checkBox4);
		
		JCheckBox checkBox5 = new JCheckBox("Local próximo em");
		checkBox5.setActionCommand("Local próximos em");
		checkBox5.setHorizontalAlignment(SwingConstants.LEFT);
		checkBox5.setBounds(30, 240, 136, 23);
		frame.getContentPane().add(checkBox5);
		
		JCheckBox CheckBox6 = new JCheckBox(".TSV");
		CheckBox6.setBounds(30, 413, 52, 23);
		frame.getContentPane().add(CheckBox6);
		
		//Separadores
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 270, 276, 2);
		frame.getContentPane().add(separator);
		
		//Labels
		
		JLabel label1 = new JLabel("Digite período inicial");
		label1.setBounds(30, 283, 176, 14);
		frame.getContentPane().add(label1);
		
		JLabel label2 = new JLabel("Digite período final");
		label2.setBounds(30, 339, 176, 14);
		frame.getContentPane().add(label2);
		
		JLabel label3 = new JLabel("Selecione as estatísticas:");
		label3.setBounds(30, 11, 154, 14);
		frame.getContentPane().add(label3);
		
		JLabel label4 = new JLabel("km");
		label4.setBounds(249, 244, 46, 14);
		frame.getContentPane().add(label4);
		
		JLabel label5 = new JLabel("Exportar");
		label5.setBounds(30, 392, 73, 14);
		frame.getContentPane().add(label5);
		
		//botões
		
		JButton button1 = new JButton("Gerar");
		button1.setBounds(173, 607, 89, 23);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("Sair");
		button2.setBounds(78, 607, 89, 23);
		frame.getContentPane().add(button2);
		
		JPanel sessions = new JPanel();
		sessions.setBorder(new TitledBorder(null, "Sess\u00F5es salvas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sessions.setBounds(10, 443, 276, 153);
		frame.getContentPane().add(sessions);
		sessions.setLayout(null);
		
		//textFields
		
		dataInicial = new JTextField();
		dataInicial.setForeground(Color.GRAY);
		dataInicial.setText("DD-MM-YYYY");
		dataInicial.setColumns(10);
		dataInicial.setBounds(30, 308, 232, 20);
		frame.getContentPane().add(dataInicial);
		
		dataFinal = new JTextField();
		dataFinal.setForeground(Color.GRAY);
		dataFinal.setText("DD-MM-YYYY");
		dataFinal.setColumns(10);
		dataFinal.setBounds(30, 361, 232, 20);
		frame.getContentPane().add(dataFinal);
		
		km = new JTextField();
		km.setBounds(166, 241, 68, 20);
		frame.getContentPane().add(km);
		km.setColumns(10);
		
		//Painel de sessões
		
		textSessions = new JTextField();
		textSessions.setBounds(10, 22, 155, 20);
		sessions.add(textSessions);
		textSessions.setColumns(10);
		
		JButton loadSessions = new JButton("Carregar");
		loadSessions.setBounds(175, 45, 91, 23);
		sessions.add(loadSessions);
		
		JList listSessions = new JList();
		listSessions.setBounds(10, 48, 155, 94);
		sessions.add(listSessions);
		
		JButton saveSessions = new JButton("Salvar");
		saveSessions.setBounds(175, 79, 91, 23);
		sessions.add(saveSessions);
		
		JButton deleteSessions = new JButton("Excluir");
		deleteSessions.setBounds(175, 113, 91, 23);
		sessions.add(deleteSessions);
		
	}
	/**
	 * Modifica visibilidade do frame
	 * @param b modifica setVisible() do frame
	 */
	public void setFrameVisible(boolean b){
		frame.setVisible(b);
	}
}
