/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareaclienteservidor2;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author yagoa
 */
public class Menu {
  private int op;
    private Usuario usuarioNuevo = new Usuario();
    private ArrayList<Usuario> listaTemporal = new ArrayList<>();

    public void menu() {
        while (op != 3) {
            String input = JOptionPane.showInputDialog(null, "Menu Principal\n"
                    + "1. Agregar Usuario\n"
                    + "2. Agregar Reservas\n"
                    + "3. Salir\n"
                    + "\nDigite la opción deseada: ", "Restaurante Administracion", JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                op = 3; 
            } else {
                op = Integer.parseInt(input);
                switch (op) {
                    case 1:
                        usuarioNuevo.agregarUsuario(listaTemporal);
                        break;
                    case 2:
                        if (!listaTemporal.isEmpty()) {
                            ManejadorReservas manejadorReservas = new ManejadorReservas();
                            for (Usuario usuario : listaTemporal) {
                                manejadorReservas.agregarReservaConcurrente(usuario);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No hay usuarios registrados para realizar reservas.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        JOptionPane.showMessageDialog(null, "Error opción inválida", "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
