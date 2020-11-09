import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import exceptions.IncorrectFileNameException;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * Lançamento do menu principal pelo swing do java
 * Daqui sairão todas as opções do input do usuário
 * @author JoÃ£o Henrique Schmidt
 */

public class View{

	private JFrame frame;
	private JTextField km;
	private JTextField textSessions;
	private String[] opcoes = {"casos","mortes","recuperados"};
	private JTextField dataInicial;
	private JTextField dataFinal;
	private JButton buttonSair;
	private JButton buttonGerar;
	private JList<String> listNumbers;
	private JList<String> listGrowth;
	private JCheckBox checkMortality;
	private JCheckBox checkCSV;
	private JCheckBox checkTSV;
	/**
	 * Cria o menu
	 */
	public View() {
		initialize();
	}
	/**
	 * Modifica visibilidade do menu, sem isso o frame não será visível
	 * @param b modifica setVisible() do frame
	 */
	public void setFrameVisible(boolean b){
		frame.setVisible(b);
	}
	/*
	 * inicia o menu e cria as ações
	 * tudo abaixo do initialize() é responsável pela funcionalidade do menu apenas
	 * se quiser a conexão para o resto do programa, vá para buttonGerar
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Projeto Final");
		frame.setBounds(100, 100, 311, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
//////////Lists
		
		listGrowth = new JList<String>(opcoes);
		listGrowth.setVisibleRowCount(3);
		listGrowth.setBackground(Color.WHITE);
		listGrowth.setBounds(30, 153, 232, 54);
		frame.getContentPane().add(listGrowth);
		
		listNumbers = new JList<String>(opcoes);
		listNumbers.setVisibleRowCount(3);
		listNumbers.setBackground(Color.WHITE);
		listNumbers.setBounds(30, 62, 232, 54);
		frame.getContentPane().add(listNumbers);
		
//////////CheckBoxes
		
		checkCSV = new JCheckBox(".CSV");
		checkCSV.setBounds(89, 413, 68, 23);
		frame.getContentPane().add(checkCSV);
		
		checkMortality = new JCheckBox("Maior mortalidade");
		checkMortality.setBounds(30, 214, 154, 23);
		frame.getContentPane().add(checkMortality);
		
		checkTSV = new JCheckBox(".TSV");
		checkTSV.setBounds(30, 413, 52, 23);
		frame.getContentPane().add(checkTSV);
		
//////////Separadores
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 270, 276, 2);
		frame.getContentPane().add(separator);
		
//////////Labels
		
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
		
		JLabel label6 = new JLabel("Maior crescimento de:");
		label6.setBounds(30, 128, 176, 14);
		frame.getContentPane().add(label6);
		
		JLabel label7 = new JLabel("Maior número de:");
		label7.setBounds(30, 36, 136, 14);
		frame.getContentPane().add(label7);
		
		JLabel label8 = new JLabel("Local mais próximo em");
		label8.setBounds(30, 244, 136, 14);
		frame.getContentPane().add(label8);
		
//////////Botões
		
	//////////Gera os arquivos, inicia o programa fora do menu
		buttonGerar = new JButton("Gerar");
		buttonGerar.setBounds(173, 607, 89, 23);
		frame.getContentPane().add(buttonGerar);
		View thisV = this;
		buttonGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Controller(thisV);
			}
		});
		
		buttonSair = new JButton("Sair");
		buttonSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		buttonSair.setBounds(78, 607, 89, 23);
		frame.getContentPane().add(buttonSair);
		
////////////"Date" Fields
		
		CustomDateFilter date = new CustomDateFilter();
		dataInicial = new JTextField();
		dataInicial.setEditable(false);
		dataInicial.setText("DD-MM-YYYY");
		dataInicial.setBounds(30, 308, 232, 20);
		frame.getContentPane().add(dataInicial);
		PlainDocument doc = (PlainDocument) dataInicial.getDocument();
		doc.setDocumentFilter(date);
		dataInicial.addMouseListener(new MouseAdapter() {//Apaga o conteúdo
			@Override
			public void mouseClicked(MouseEvent e) {
				if(dataInicial.getText().equals("DD-MM-YYYY")) {
					dataInicial.setText("");
					dataInicial.setEditable(true);
				}
			}
		});
		
		
		dataFinal = new JTextField();
		dataFinal.setEditable(false);
		dataFinal.setText("DD-MM-YYYY");
		dataFinal.setBounds(30, 361, 232, 20);
		frame.getContentPane().add(dataFinal);
		PlainDocument doc2 = (PlainDocument) dataFinal.getDocument();
		doc2.setDocumentFilter(date);
		dataFinal.addMouseListener(new MouseAdapter() {//Apaga o conteúdo
			@Override
			public void mouseClicked(MouseEvent e) {
				if(dataFinal.getText().equals("DD-MM-YYYY")) {
					dataFinal.setText("");
					dataFinal.setEditable(true);
				}
			}
		});
		
//////////Caixa de Texto para input da distância
		
		km = new JTextField();
		km.setBounds(173, 241, 68, 20);
		frame.getContentPane().add(km);
		km.setColumns(10);
		
//////////Painel de sessões
		
		JPanel sessions = new JPanel();
		sessions.setBorder(new TitledBorder(null, "Sess\u00F5es salvas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sessions.setBounds(10, 443, 276, 153);
		sessions.setLayout(null);
		
		textSessions = new JTextField();
		textSessions.setBounds(10, 22, 155, 20);
		sessions.add(textSessions);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();//Deve ser usado para modificar o listSessions
		readRegistrosName(listModel);
		JList<String> listSessions = new JList<String>(listModel);
		listSessions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Scrollbar
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 155, 91);
		scrollPane.setViewportView(listSessions);
		listSessions.setLayoutOrientation(JList.VERTICAL);
		sessions.add(scrollPane);
		
		
	//////////Botões do painel de sessões	
		
		JButton loadSessions = new JButton("Carregar");
		loadSessions.setBounds(175, 45, 91, 23);
		loadSessions.addActionListener(new ActionListener() { //Ação de apertar o botão carregar
			public void actionPerformed(ActionEvent e) {
				if(listSessions.getSelectedValue() != null) {
					String st;
					File file = new File("registros.txt");
					try {
						file.createNewFile();
						BufferedReader br;
						String[] line = {};
						br = new BufferedReader(new FileReader(file));
						while ((st = br.readLine()) != null) {
							line = st.split(",");
							if(line[0].equals(listSessions.getSelectedValue())) {
								break;
							}
						}
						br.close();
						if(line.length > 0) { //linha vazia será descartada
							listNumbers.clearSelection();
							int a = Integer.parseInt(line[1]);
							//veja opcoes() para entender melhor
							if(a%2==1) //casos
								listNumbers.addSelectionInterval(0,0);
							if((a&0b10) == 0b10) //mortos
								listNumbers.addSelectionInterval(1,1);
							if((a&0b100) == 0b100) //recuperados
								listNumbers.addSelectionInterval(2,2);
							a = Integer.parseInt(line[2]);
							listGrowth.clearSelection();
							if(a%2==1) //casos
								listGrowth.addSelectionInterval(0,0);
							if((a&0b10) == 0b10) //mortos
								listGrowth.addSelectionInterval(1,1);
							if((a&0b100) == 0b100) //recuperados
								listGrowth.addSelectionInterval(2,2);
							if(line[3].equals("true"))
								checkMortality.setSelected(true);
							km.setText(line[4]);
							if(!dataInicial.isEditable())
								dataInicial.setEditable(true);
							dataInicial.setText("");
							dataInicial.setText(line[5]);
							if(!dataFinal.isEditable())
								dataFinal.setEditable(true);
							dataFinal.setText("");
							dataFinal.setText(line[6]);
							if(line[7].equals("true"))
								checkTSV.setSelected(true);
							if(line[8].equals("true"))
								checkCSV.setSelected(true);
						}else {
							throw new IOException();
						}
						br.close();
					} catch (IOException e1) {
						msgWarning("Não foi possível carregar sessão");
					}
				}
			}
		});
		sessions.add(loadSessions);
		
		JButton saveSessions = new JButton("Salvar");
		saveSessions.setBounds(175, 79, 91, 23);
		sessions.add(saveSessions);
		saveSessions.addActionListener(new ActionListener() { //Ação de apertar o botão salvar
			public void actionPerformed(ActionEvent e) {
				File registro = new File("registros.txt");
				String sessao;//salva todas as opções na sessão 
				//sessao = nome, opções do rank número, opções do rank crescimento, rank mortalidade, raio r do rank de locais, data Inicial, data Final, TSV, CSV
				sessao = textSessions.getText() + ",";
				sessao += Integer.toString((int) opcoes(listNumbers)) + ",";
				sessao += Integer.toString((int) opcoes(listGrowth))+ ",";
				sessao += String.valueOf(checkMortality.isSelected())+ ",";
				sessao += km.getText()+ ",";
				sessao += dataInicial.getText()+ ",";
				sessao += dataFinal.getText()+ ",";
				sessao += String.valueOf(checkTSV.isSelected())+ ",";
				sessao += String.valueOf(checkCSV.isSelected())+ System.lineSeparator();
				try {
					if(textSessions.getText().equals("")) {
						if(isOverwritingLine(listSessions.getSelectedValue()))
							sessao = listSessions.getSelectedValue() + sessao;
					}else {
						if(isOverwritingLine(textSessions.getText())) {
							sessao = textSessions.getText() + sessao;
						}else {
							listModel.addElement(textSessions.getText());
						}
					}
					registro.createNewFile();
					FileWriter registroWrite = new FileWriter("registros.txt",true);
					registroWrite.write(sessao);
					registroWrite.close();
				} catch (IOException e1) {
					msgBox("Não foi possível criar o arquivo para registrar os dados.");
				} catch (IncorrectFileNameException e2 ) {
					msgBox(e2.getMessage());
				}
			}
		});
		
		JButton deleteSessions = new JButton("Excluir");
		deleteSessions.setBounds(175, 113, 91, 23);
		sessions.add(deleteSessions);
		deleteSessions.addActionListener(new ActionListener() { //Ação de apertar o botão excluir
			public void actionPerformed(ActionEvent e) {
				//Cria novo arquivo sem a linha selecionada. Processo lento, mas feito mais rápido com os buffers
				//Cria arq temporario tbm
				
				try {
					File inFile = new File("registros.txt");
					inFile.createNewFile();
					File outFile = new File("tempregistros.txt");
					outFile.createNewFile();
					BufferedReader br = new BufferedReader(new FileReader(inFile));
					BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
					String line;
					boolean isOverwriting = true; //caso a linha não tenha sido achada
					while((line = br.readLine()) != null) {//lendo lista de espera
						if(!line.split(",")[0].equals(listSessions.getSelectedValue())) {
							bw.write(line + System.lineSeparator());
						}else {
							isOverwriting = false;
						}
					}
					br.close();
					bw.close();
					if(isOverwriting) {
						outFile.delete();
					}else {
						listModel.removeElement(listSessions.getSelectedValue());
						inFile.delete(); //Deleta arquivo velho
						outFile.renameTo(inFile); //arq temporario é agr arq novo
					}
				} catch (IOException e1) {
					msgBox("Não foi possível criar o arquivo para registrar os dados.");
				}
			}
		});
		frame.getContentPane().add(sessions);
	}
////////////////////////////////////////fim do initialize()////////////////	
	
	private static char opcoes(JList<String> lista) { //É apenas usado no painel de sessões
		/*
		 * usada para resgatar as opções:
		 *  casos =  		2^0
		 *  mortos = 		2^1
		 *  recuperados =	2^2
		 *  retorno = casos + mortos + recuperados
		 */
		char soma = 0;
		List<String> arr = lista.getSelectedValuesList(); 
		if (arr.contains("casos"))
			soma++;
		if(arr.contains("mortes"))
			soma+=2;
		if(arr.contains("recuperados"))
			soma+=4;
		return soma;//0 até 7
	}
	//Usada para alertar o usuário de erros no menu
	private static void msgBox(String s) {
		JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
	}
	//Usada para altertar possíveis problemas
	private static void msgWarning(String s) {
		JOptionPane.showMessageDialog(null, s,"Warning", JOptionPane.WARNING_MESSAGE);
	}
	//retorna os nomes das sessões para a lista de sessões
	private static void readRegistrosName(DefaultListModel<String> listModel) {
		File file;
		String st;
		file = new File("registros.txt");
		try {
			file.createNewFile();
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				listModel.addElement(st.split(",")[0]);
			}
			br.close();
		} catch (IOException e) {
			msgWarning("Não foi possível achar os registros "
					+ "das sessões e portanto as sessões não foram apresentadas");
		}
	}
	
	//Resultado pesquisa
	public void printOutput(List<String> output) {
		
		
		   JScrollPane scrollpane = new JScrollPane(); 
		  
		   JList lista = new JList(output.toArray());
		
		   scrollpane = new JScrollPane(lista);
		
		   JPanel panel = new JPanel(); 
		   panel.add(scrollpane);
		
		   scrollpane.getViewport().add(lista);
		   JOptionPane.showMessageDialog(null, scrollpane, "Output",  
		   JOptionPane.PLAIN_MESSAGE);
		}
	
	
	
	
	//Checa se o usuário está sobrescrevendo uma linha, e deleta essa linha 
	private static boolean isOverwritingLine(String sessionName) {
		//Cria novo arquivo sem a linha selecionada. Processo lento, mas feito mais rápido com os buffers
		//Cria arq temporario tbm
		boolean isOverwriting = false;
		try {
			File inFile = new File("registros.txt");
			inFile.createNewFile();
			File outFile = new File("tempregistros.txt");
			outFile.createNewFile();
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			String line;
			 //caso a linha não tenha sido achada
			System.out.println("sessionName = " + sessionName);
			while((line = br.readLine()) != null) {//lendo lista de espera
				if(!line.split(",")[0].equals(sessionName)) {
					bw.write(line+System.lineSeparator());
					System.out.println("linha escrita : "+line);
				}else {
					System.out.println("linha não escrita : "+line);
					isOverwriting = true;
				}
			}
			br.close();
			bw.close();
			if(!isOverwriting) {
				outFile.delete();
			}else {
				inFile.delete(); //Deleta arquivo velho
				outFile.renameTo(inFile); //arq temporario é agr arq novo
			}
		} catch (IOException e1) {
			msgBox("Não foi possível criar o arquivo para registrar os dados.");
		}
		return isOverwriting;
	}
	//Usado para as ações dos textos
	private class CustomDateFilter extends DocumentFilter{	//isso cuidará de copy/paste e KeyEvents
		@Override //função ao inserir uma string
		public void insertString(FilterBypass fb, int offset, String s, AttributeSet attr) throws BadLocationException {
			// s = string que será inserida
			Document doc = fb.getDocument();
		    StringBuilder sb = new StringBuilder();
		    sb.append(doc.getText(0, doc.getLength()));
		    sb.insert(offset, s);
		    if(isDateInputValid(s) && sb.length() <= 10) {
			    if(doc.getLength() == 2 || doc.getLength() == 5) {
			    	super.insertString(fb, offset, s + "-", attr);//adiciona o hífen "-" à String
			    }else {
			    	super.insertString(fb, offset, s, attr);
			    }
		    }
		}
		
		public void replace(FilterBypass fb, int offset, int length, String s, AttributeSet attr) throws BadLocationException {
			Document doc = fb.getDocument();
		    StringBuilder sb = new StringBuilder();
		    sb.append(doc.getText(0, doc.getLength()));
		    sb.insert(offset, s);
		    if(isDateInputValid(s) && sb.length() <= 10) {//"" também é número
			    if(doc.getLength() == 1 || doc.getLength() == 4) {
			    	super.replace(fb, offset, length, s + "-", attr);//adiciona o hífen "-" à String
			    }else {
			    	super.replace(fb, offset, length, s, attr);
			    	
			    }
		    }
		}
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			if(offset == 2 || offset == 5 && length == 1) {
				super.remove(fb, offset-1, length + 1);
			}else {
				super.remove(fb, offset, length);
			}
			
		}
	}
	private boolean isDateInputValid(String s){
		if(s.matches("[0-9-]*") || s.equals("DD-MM-YYYY")) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Representa a distância do local de maior contágio
	 * <p>Acesso a String da distância, no menu<p/>
	 * @return the km
	 */
	public String getKm() {
		return km.getText();
	}
	/**
	 * Representa a data inicial do período a ser selecionado, como String
	 * <p>Acesso à String da data inicial no menu<p/>
	 * @return the dataInicial
	 */
	public String getDataInicial() {
		return dataInicial.getText();
	}
	/**
	 * Representa a data final do período a ser selecionado, como String
	 * <p>Acesso à String da data final no menu<p/>
	 * @return the dataFinal
	 */
	public String getDataFinal() {
		return dataFinal.getText();
	}
	/**
	 * Representa estatística(s) selecionada(s) da lista de números absolutos
	 * <p> número de casos = 2^0</p>
	 * <p> número de mortes = 2^1</p>
	 * <p> número de recuperados = 2^2</p>
	 * @return casos + mortes + recuperados
	 */
	public char getOpcoesListNumbers() {
		return opcoes(listNumbers);
	}
	/**
	 * Representa estatística(s) selecionada(s) da lista de crescimento
	 * <p> número de casos = 2^0</p>
	 * <p> número de mortes = 2^1</p>
	 * <p> número de recuperados = 2^2</p>
	 * @return casos + mortes + recuperados
	 */
	public char getOpcoesListGrowth() {
		return opcoes(listGrowth);
	}
	/**
	 * Representa estatística da lista mortalidade
	 * <p>Verdadeiro caso esteja selecionado no menu<p/>
	 * @return the checkMortality
	 */
	public boolean getCheckMortality() {
		return checkMortality.isSelected();
	}
	/**
	 * Representa necessidade de exportar para CSV
	 * <p>Verdadeiro caso esteja selecionado no menu<p/>
	 * @return the checkCSV
	 */
	public boolean getCheckCSV() {
		return checkCSV.isSelected();
	}
	/**
	 * Representa necessidade de exportar para TSV
	 * <p>Verdadeiro caso esteja selecionado no menu<p/>
	 * @return the checkTSV
	 */
	public boolean getCheckTSV() {
		return checkTSV.isSelected();
	}
}
