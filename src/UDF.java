import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

/**
 * Utilitarios e funções definidas pelo usuário (UDF)
 * É mais útil ler outros códigos por ai do que explorar 
 * mais desse arquivo. As funções são feitas para serem 
 * usadas de forma independente uma das outras.
 * @author JoÃ£o Henrique Schmidt
 *
 */
public class UDF {
	/**
	 * Retira o último character da string
	 * @param s String desejada
	 * @return String final
	 */
	public static String chop(String s) {
		if(s.length() > 0) {
			return s.substring(0,s.length() -1);
		}else {
			return s;
		}
	}
	/**
	 * Testa se String pode ser número.
	 * @param s string que deve representar um número
	 * @return true caso paramtro seja verdadeiro. Senão falso.
	 */
	public static boolean isNumber(String s) {
		if(s.matches("\\d*")) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Testa se a data informada é válida
	 * @param s Data em forma de string
	 * @return true caso a data seja verdadeira. Senão false
	 */
	public static boolean isDateValid(String s) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		df.setLenient(false); //impede datas absurdas e.g MM = 20;
		try {
			df.parse(s);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	/**
	 * Testa se String pode ser float.
	 * @param s string que deve representar um número
	 * @return true caso paramtro seja verdadeiro. Senão falso.
	 */
	public static boolean isFloat(String s) {
		try {
			Float.parseFloat(s);
		}catch (NumberFormatException | NullPointerException e) {
			return false;
		}
		return true;
	}
	/**
	 * Usada para alertar o usuário sobre erros
	 * @param s Mensagem de erro
	 */
	public static void msgBox(String s) {
		JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * Usada para altertar possíveis problemas
	 * @param s Mensagem de alerta
	 */
	public static void msgWarning(String s) {
		JOptionPane.showMessageDialog(null, s,"Warning", JOptionPane.WARNING_MESSAGE);
	}
}
