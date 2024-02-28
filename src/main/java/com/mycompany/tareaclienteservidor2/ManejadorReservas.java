/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareaclienteservidor2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author armi8
 */
public class ManejadorReservas {
    private final ArrayList<Reserva> listaReservas = new ArrayList<>();
    private static final int TAMANIO_BLOQUE = 5;

    public synchronized void agregarReservaConcurrente(Usuario usuario) {
        Thread reservaThread;
        reservaThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Obtener datos de la reserva
                String fechaComida = JOptionPane.showInputDialog("Ingrese la fecha de la comida (DD/MM/YYYY): ");
                String tipoComida = JOptionPane.showInputDialog("Ingrese el tipo de comida (Desayuno/Almuerzo/Cena): ");
                String guarnicion1 = JOptionPane.showInputDialog("Ingrese la primera guarnición (Arroz/Frijoles/Pancakes/Frutas): ");
                String guarnicion2 = JOptionPane.showInputDialog("Ingrese la segunda guarnición (Arroz/Frijoles/Pancakes/Frutas): ");
                String proteina = JOptionPane.showInputDialog("Ingrese la proteína (Carne/Pescado/Pollo/Huevos): ");
                String ensalada = JOptionPane.showInputDialog("Ingrese el tipo de ensalada (Verde/Rusa): ");
                
                // Crear reserva
                Reserva reserva = new Reserva(usuario.getCedula(), fechaComida, tipoComida, guarnicion1, guarnicion2, proteina, ensalada);
                
                // Agregar reserva a la lista
                synchronized (listaReservas) {
                    listaReservas.add(reserva);
                }
                
                // Guardar reserva en archivo
                guardarReservaEnArchivo(reserva);
            }
        });
        reservaThread.start();
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

    private synchronized void guardarReservaEnArchivo(Reserva reserva) {
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
    
}
