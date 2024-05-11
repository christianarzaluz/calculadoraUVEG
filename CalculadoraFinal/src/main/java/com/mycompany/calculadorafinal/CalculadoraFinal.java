// Desarrollado por Christian Arzaluz para el R1U1 del  módulo POO
// Importando paquetes
    
package com.mycompany.calculadorafinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Clase Calculadora

public class CalculadoraFinal extends JFrame implements ActionListener {
    private JTextField inputField;
    private String inputString = "";
    private double result = 0;
    private char operator = ' ';
    private boolean isDecimal = false;

// Estilo de la interfaz

    public CalculadoraFinal() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(300, 50));
        inputField.setHorizontalAlignment(JTextField.LEFT);
        inputField.setEditable(false);
        add(inputField, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 4));

        String[] buttonLabels = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "C", "0", "=", "/",
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(80, 80));
            button.addActionListener(this);
            buttonsPanel.add(button);
        }

        add(buttonsPanel, BorderLayout.CENTER);
    }

// Lógica del input

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("=")) {
            calculateResult();
        } else if (command.equals("C")) {
            clearInput();
        } else if (command.equals(".")) {
            if (!isDecimal) {
                inputString += command;
                isDecimal = true;
            }
            inputField.setText(inputString);
        } else {
            if (Character.isDigit(command.charAt(0))) {
                inputString += command;
            } else {
                if (!inputString.isEmpty()) {
                    inputString += " " + command + " ";
                    isDecimal = false;
                }
            }
            inputField.setText(inputString);
        }
    }

// División entre 0 = Error, mostrar números enteros si el resultado es entero y viceversa

    private void calculateResult() {
        try {
            result = evaluateExpression(inputString);
            if (Double.isInfinite(result) || Double.isNaN(result)) {
                inputField.setText("Error");
            } else if (result % 1 == 0) {
                inputField.setText(String.valueOf((int) result));
            } else {
                inputField.setText(String.valueOf(result));
            }
            inputString = "";
            isDecimal = false;
        } catch (ArithmeticException e) {
            inputField.setText("Error");
            inputString = "";
            isDecimal = false;
        }
    }
    
// Lógica de las operaciones
    
    private double evaluateExpression(String expression) {
        String[] elements = expression.split(" ");
        double operand1 = Double.parseDouble(elements[0]);
        char operator = elements[1].charAt(0);
        double operand2 = Double.parseDouble(elements[2]);

        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                return operand1 / operand2;
            default:
                throw new ArithmeticException("Operador no válido");
        }
    }

// Limpiar el input

    private void clearInput() {
        inputString = "";
        inputField.setText("");
        isDecimal = false;
    }
// Método MAIN
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraFinal calculator = new CalculadoraFinal();
            calculator.setVisible(true);
        });
    }
}
