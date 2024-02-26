/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareaclienteservidor2;

import java.io.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 *
 * @author yagoa
 */
public class Usuario {

    private String cedula, nombre;
    private int numeroTelefono;
    

    public Usuario() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }



    public void agregarUsuario(ArrayList<Usuario> listaTemporal) {
        do {
             Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(JOptionPane.showInputDialog("Ingrese el nombre del cliente/usario: "));
            nuevoUsuario.setCedula(JOptionPane.showInputDialog("Ingrese la cedula del cliente/usario: "));
            try{
            nuevoUsuario.setNumeroTelefono(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero telefonico del cliente/usario: ")));
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Ingrese un valor valido para el numero");
                continue; 
            }

            listaTemporal.add(nuevoUsuario);
            int opc = JOptionPane.showConfirmDialog(null, "Desea ingresar otro usario?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (opc != JOptionPane.YES_OPTION) {
                guardarUsuarioArchivo(listaTemporal);
                break;
            }
        } while (true);
    }
    public void guardarUsuarioArchivo(ArrayList<Usuario> listaTemporal){
        try(BufferedWriter archivo = new BufferedWriter(new FileWriter("usuarios.txt", true))){
            for (Usuario usuario:listaTemporal) {
                String usuarioInfo = "Cedula de usuario: "+usuario.getCedula() + "," +"Nombre de usuario: "+ usuario.getNombre() + "," +" Numero de telefono: "+ usuario.getNumeroTelefono()+"\n";
               archivo.write(usuarioInfo);
            }
             JOptionPane.showMessageDialog(null, "Usuarios guardados exitosamente en el archivo.");
        }catch(IOException e){
            e.printStackTrace();
        } 
    }
}
