import java.util.List;

import javax.swing.JOptionPane;
	/**
	 * Controla o fluxo entre o menu e as funções de chamada.
	 * Serve para checar os dados do menu inicial antes de iniciar a busca no banco de dados
	 * @author JoÃ£o Henrique Schmidt
	 *
	 */
public class Controller {
	/**
	 * Processa as opções do menu e chama as funções apropriadas
	 * <p> topNumeros para requisitar o rank de números absolutos <p/>
	 * <p> topCrescimento para requisitar o rank crescimento <p/>
	 * <p> topNumeros para requisitar o rank de números absolutos <p/>
	 * <p> topNumeros para requisitar o rank de números absolutos <p/>
	 * @param menu
	 */
	public static void Gerar(View menu) {
		if(UDF.isDateValid(menu.getDataInicial())) {
			if(UDF.isDateValid(menu.getDataFinal())) {
				if(menu.getOpcoesListNumbers() != 0) {//checando se ranking de números foi selecionado
					App.topNumeros(menu.getOpcoesListNumbers(), menu.getCheckTSV(), menu.getCheckCSV(),
							menu.getDataInicial(),menu.getDataFinal());
				}
				if(menu.getOpcoesListGrowth() != 0) {//checando se ranking de crescimento foi selecionado
					App.topCrescimento(menu.getOpcoesListNumbers(), menu.getCheckTSV(), menu.getCheckCSV(),
							menu.getDataInicial(),menu.getDataFinal());
				}
				if(menu.getCheckMortality()) {//checando se ranking de mortalidade foi selecionado
					App.topMortalidade(menu.getCheckTSV(), menu.getCheckCSV(),
							menu.getDataInicial(),menu.getDataFinal());
				}
				if(menu.getKm() != null && !menu.getKm().equals("")) { //checando se ranking de locais mais próximos foi selecionado
					if(UDF.isFloat(menu.getKm())) {
					App.topLocal(menu.getCheckTSV(), menu.getCheckCSV(),Float.parseFloat(menu.getKm()),
							menu.getDataInicial(),menu.getDataFinal());
					}else {
						msgBox("Distância não é válida");
					}
				}
			}else {
				msgBox("Data final não é válida.");
			}
		}else {
			msgBox("Data inicial não é válida.");
		}
	}
	private static void msgBox(String s) {
		JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
