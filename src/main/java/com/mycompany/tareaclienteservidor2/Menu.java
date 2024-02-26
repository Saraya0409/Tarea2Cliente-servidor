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

    int op;
    Usuario usuarioNuevo = new Usuario();
    ArrayList<Usuario> listaTemporal = new ArrayList<>();

    public void menu() {
        while (op != 3) {
            op = Integer.parseInt(JOptionPane.showInputDialog(null, "Menu Principal\n"
                    + "1.Agregar Usuario\n"
                    + "2.Agregar Reservas\n"
                    + "3.Salir\n"
                    + "\nDigite la opcion deseada: ", "Restaurante Administracion", JOptionPane.QUESTION_MESSAGE));
            switch (op) {
                case 1:
                    usuarioNuevo.agregarUsuario(listaTemporal);

                    break;
                case 2:

                    break;
                case 3:
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Error opcion invalida", "Eror",
                            JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
