package gm.zona_fit.gui;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//@Component
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
    private Integer idCliente;

    private DefaultTableModel tableModelClientes;

    IClienteServicio clienteServicio;

    @Autowired
    public ZonaFitForma(IClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> {
            guardarCliente();
        });
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addActionListener(e -> {
            eliminarCliente();
        });
        limpiarButton.addActionListener(e -> {
            limpiarFormulario();
        });
    }

    private void iniciarForma() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //this.tableModelClientes = new DefaultTableModel(0, 4);
        //Evitamos la edición de los valores en la misma tabla
        this.tableModelClientes = new DefaultTableModel(0, 4){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tableModelClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(this.tableModelClientes);
        //Restringimos la seleccion de la tabla a un solo registro
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);
        this.clienteServicio.guardarCliente(cliente);

        if(this.idCliente == null)
            mostrarMensaje("Se guardo el nuevo cliente.");
        else
            mostrarMensaje("Se actualizo el cliente.");

        limpiarFormulario();
        listarClientes();
    }

    private void eliminarCliente() {
        var renglon = clientesTabla.getSelectedRow();

        if(renglon != -1) {
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.valueOf(idClienteStr);
            var cliente = new Cliente();
            cliente.setIdcliente(this.idCliente);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensaje("Cliente con id " + this.idCliente + " eliminado");
        }else
            mostrarMensaje("Seleccione un cliente.");

        limpiarFormulario();
        listarClientes();
    }

    private void cargarClienteSeleccionado() {
        var renglon = clientesTabla.getSelectedRow();
        if (renglon == -1) {
            mostrarMensaje("Seleccione un cliente.");
        } else {
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }

    private void limpiarFormulario() {
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
        //Limpiar el id cliente seleccionado
        this.idCliente = null;
        //Deseleccionamos el registro de la tabla
        this.clientesTabla.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
