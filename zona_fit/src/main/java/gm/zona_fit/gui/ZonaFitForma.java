package gm.zona_fit.gui;

import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ZonaFitForma extends JFrame {
    private JPanel panel1;
    private JPanel panelPrincipal;

    @Autowired
    IClienteServicio clienteServicio;

    public ZonaFitForma() {

    }



}
