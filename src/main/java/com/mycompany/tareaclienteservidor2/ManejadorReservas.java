/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareaclienteservidor2;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ManejadorReservas {

    private final ArrayList<Reserva> listaReservas = new ArrayList<>();
    private static final int TAMANIO_BLOQUE = 5;

    public void agregarReservaConcurrente(Usuario usuario) {

        String fechaComida;
        do {
            fechaComida = JOptionPane.showInputDialog("Ingrese la fecha de la comida (DD/MM/YYYY): ");
            if (!validarFormatoFecha(fechaComida)) {
                JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Ingrese la fecha en formato DD/MM/YYYY.");
                continue;
            }
            if (!validarFechaUnica(fechaComida)) {
                JOptionPane.showMessageDialog(null, "Ya existe una reserva para la fecha ingresada. Ingrese una fecha diferente.");
                continue;
            }
            break;
        } while (true);

        String[] tiposComida = {"Desayuno", "Almuerzo", "Cena"};
        String tipoComida = (String) JOptionPane.showInputDialog(null,
                "Seleccione el tipo de comida:",
                "Tipo de Comida",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tiposComida,
                tiposComida[0]);

        String[] guarniciones = {"Arroz", "Frijoles", "Pancakes", "Frutas"};
        String guarnicion1 = (String) JOptionPane.showInputDialog(null,
                "Seleccione la primera guarnición:",
                "Guarnición 1",
                JOptionPane.QUESTION_MESSAGE,
                null,
                guarniciones,
                guarniciones[0]);

        String guarnicion2 = (String) JOptionPane.showInputDialog(null,
                "Seleccione la segunda guarnición:",
                "Guarnición 2",
                JOptionPane.QUESTION_MESSAGE,
                null,
                guarniciones,
                guarniciones[0]);

        String[] proteinas = {"Carne", "Pescado", "Pollo", "Huevos"};
        String proteina = (String) JOptionPane.showInputDialog(null,
                "Seleccione la proteína:",
                "Proteína",
                JOptionPane.QUESTION_MESSAGE,
                null,
                proteinas,
                proteinas[0]);

        String[] ensaladas = {"Verde", "Rusa"};
        String ensalada = (String) JOptionPane.showInputDialog(null,
                "Seleccione el tipo de ensalada:",
                "Ensalada",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ensaladas,
                ensaladas[0]);

        Reserva reserva = new Reserva(usuario.getCedula(), fechaComida, tipoComida, guarnicion1, guarnicion2, proteina, ensalada);

        synchronized (listaReservas) {
            listaReservas.add(reserva);
        }

        guardarReservaEnArchivo(reserva);
    }

    public void agregarBloqueReservasConcurrentes(Usuario usuario, int cantidadReservas) {
        for (int i = 0; i < cantidadReservas; i++) {
            agregarReservaConcurrente(usuario);
        }
    }

    public void agregarBloqueReservasConcurrentesEnBloques(Usuario usuario, int cantidadTotalReservas) {
        int bloques = cantidadTotalReservas / TAMANIO_BLOQUE;
        int resto = cantidadTotalReservas % TAMANIO_BLOQUE;

        for (int i = 0; i < bloques; i++) {
            agregarBloqueReservasConcurrentes(usuario, TAMANIO_BLOQUE);
        }

        if (resto > 0) {
            agregarBloqueReservasConcurrentes(usuario, resto);
        }
    }

    private void guardarReservaEnArchivo(Reserva reserva) {
        try (FileWriter writer = new FileWriter("reservas.txt", true)) {
            writer.write("Cédula de usuario: " + reserva.getCedulaUsuario() + "\n");
            writer.write("Fecha de comida: " + reserva.getFechaComida() + "\n");
            writer.write("Tipo de comida: " + reserva.getTipoComida() + "\n");
            writer.write("Guarnición 1: " + reserva.getGuarnicion1() + "\n");
            writer.write("Guarnición 2: " + reserva.getGuarnicion2() + "\n");
            writer.write("Proteína: " + reserva.getProteina() + "\n");
            writer.write("Ensalada: " + reserva.getEnsalada() + "\n\n");
            JOptionPane.showMessageDialog(null, "Reserva guardada exitosamente en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validarFormatoFecha(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private synchronized boolean validarFechaUnica(String fecha) {
        for (Reserva reserva : listaReservas) {
            if (reserva.getFechaComida().equals(fecha)) {
                return false; 
            }
        }
        return true; 
    }
}
