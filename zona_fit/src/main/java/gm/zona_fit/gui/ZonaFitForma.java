package gm.zona_fit.gui;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class ZonaFitForma extends JFrame {
    private JPanel panel1;
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;

    private DefaultTableModel tableModelClientes;

    IClienteServicio clienteServicio;

    @Autowired
    public ZonaFitForma(IClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> {
            guardarCliente();
        });
    }

    private void iniciarForma() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void guardarCliente() {
        if (nombreTexto.getText().isEmpty()) {
            mostrarMensaje("Proporciona un nombre.");
            nombreTexto.requestFocus();
            return;
        }
        if (membresiaTexto.getText().isEmpty()) {
            mostrarMensaje("Proporciona un membresia.");
            membresiaTexto.requestFocus();
            return;
        }
        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());
        var cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setMembresia(membresia);
        this.clienteServicio.guardarCliente(cliente);
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tableModelClientes = new DefaultTableModel(0, 4);
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tableModelClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(this.tableModelClientes);
        //Listar los clientes
        listarClientes();
    }
    private void listarClientes() {
        tableModelClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] rengloCliente = {
                    cliente.getIdcliente(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tableModelClientes.addRow(rengloCliente);
        });
    }
}
