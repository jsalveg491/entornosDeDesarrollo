package calculadora;

import javax.swing.JOptionPane;

public class calculadora {

	public static void main(String[] args) {
		boolean continuar = true;
		
		while(continuar) {
			// Solicitar el primer número
			String input1 = JOptionPane.showInputDialog(null, 
				"Introduce el primer número:", 
				"Calculadora - Primer Número", 
				JOptionPane.QUESTION_MESSAGE);
			
			// Si el usuario cancela, salir
			if(input1 == null) {
				break;
			}
			
			// Solicitar el segundo número
			String input2 = JOptionPane.showInputDialog(null, 
				"Introduce el segundo número:", 
				"Calculadora - Segundo Número", 
				JOptionPane.QUESTION_MESSAGE);
			
			// Si el usuario cancela, salir
			if(input2 == null) {
				break;
			}
			
			try {
				double num1 = Double.parseDouble(input1);
				double num2 = Double.parseDouble(input2);
				
				// Menú de operaciones con botones
				String[] opciones = {"Sumar (+)", "Restar (-)", "Multiplicar (*)", "Dividir (/)"};
				int seleccion = JOptionPane.showOptionDialog(null,
					"Primer número: " + num1 + "\nSegundo número: " + num2 + "\n\n¿Qué operación deseas realizar?",
					"Calculadora - Selecciona Operación",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					opciones,
					opciones[0]);
				
				// Si el usuario cancela, salir
				if(seleccion == -1) {
					break;
				}
				
				double resultado = 0;
				String operacion = "";
				boolean operacionValida = true;
				
				switch(seleccion) {
					case 0: // Sumar
						resultado = sumar(num1, num2);
						operacion = num1 + " + " + num2 + " = " + resultado;
						break;
					case 1: // Restar
						resultado = restar(num1, num2);
						operacion = num1 + " - " + num2 + " = " + resultado;
						break;
					case 2: // Multiplicar
						resultado = multiplicar(num1, num2);
						operacion = num1 + " × " + num2 + " = " + resultado;
						break;
					case 3: // Dividir
						if(num2 != 0) {
							resultado = dividir(num1, num2);
							operacion = num1 + " ÷ " + num2 + " = " + resultado;
						} else {
							JOptionPane.showMessageDialog(null,
								"❌ Error: No se puede dividir por cero.",
								"Error de División",
								JOptionPane.ERROR_MESSAGE);
							operacionValida = false;
						}
						break;
				}
				
				// Mostrar resultado si la operación fue válida
				if(operacionValida) {
					int respuesta = JOptionPane.showConfirmDialog(null,
						"✅ Resultado:\n\n" + operacion + "\n\n¿Deseas realizar otra operación?",
						"Calculadora - Resultado",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
					
					if(respuesta != JOptionPane.YES_OPTION) {
						continuar = false;
					}
				}
				
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
					"❌ Error: Por favor, introduce números válidos.",
					"Error de Formato",
					JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Mensaje de despedida
		JOptionPane.showMessageDialog(null,
			"¡Gracias por usar la calculadora! 👋",
			"Calculadora",
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static double sumar(double a, double b) {
		return a + b;
	}
	
	public static double restar(double a, double b) {
		return a - b;
	}
	
	public static double multiplicar(double a, double b) {
		return a * b;
	}
	
	public static double dividir(double a, double b) {
		return a / b;
	}

}
