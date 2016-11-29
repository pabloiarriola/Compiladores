/**
 * Nombre del archivo: TresVeintitresParser.java
 * Universidad del Valle de Guatemala
 * Pablo Arriola 131115
 *Main
**/

import java.util.Scanner;
import javax.swing.JOptionPane;

public class TresVeintitresParserMain {

/**
* @param args the command line arguments
 */
	public static void main(String[] args) {
		// TODO code application logic here
		String input;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Ingrese text a parsear");
		input = JOptionPane.showInputDialog("Ingrese texto a parsear: ");
		TresVeintitresParser objParser = new TresVeintitresParser(input);
		objParser.procesoParseo(input);
	}
}
