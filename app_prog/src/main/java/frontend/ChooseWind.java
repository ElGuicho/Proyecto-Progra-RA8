package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChooseWind extends JFrame implements MouseListener {

    JButton crear = new JButton("Crear");
    JButton modificar = new JButton("Modificar");
    JButton eliminar = new JButton("Eliminar");
    JButton seleccionar = new JButton("Seleccionar");

    public ChooseWind() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 300, 250);
        panel.setLayout(new GridLayout(5, 1));

        panel.add(new JLabel("GESTIÓN DE PREGUNTAS"));
        panel.add(crear);
        panel.add(modificar);
        panel.add(eliminar);
        panel.add(seleccionar);

        crear.addMouseListener(this);
        modificar.addMouseListener(this);
        eliminar.addMouseListener(this);
        seleccionar.addMouseListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ChooseWind();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == crear) {
            new ChooseQuest();
            this.dispose();
        }

        if (e.getSource() == modificar) {
            new ModifyQuest();
            this.dispose();
        }

        if (e.getSource() == eliminar) {
            new DeleteQuest();
            this.dispose();
        }

        if (e.getSource() == seleccionar) {
            new ChooseQuest();
            this.dispose();
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}