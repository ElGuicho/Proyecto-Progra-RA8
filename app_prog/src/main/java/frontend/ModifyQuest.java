package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ModifyQuest extends JFrame implements MouseListener {

    JTextField id = new JTextField();
    JTextField nuevo = new JTextField();

    JButton modificar = new JButton("Modificar");
    JButton volver = new JButton("Volver");

    public ModifyQuest() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 300, 200);
        panel.setLayout(new GridLayout(5, 1));

        panel.add(new JLabel("ID Pregunta"));
        panel.add(id);
        panel.add(new JLabel("Nuevo enunciado"));
        panel.add(nuevo);
        panel.add(modificar);
        panel.add(volver);

        modificar.addMouseListener(this);
        volver.addMouseListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == modificar) {
            JOptionPane.showMessageDialog(null, "Pregunta modificada");
            new ChooseWind();
            this.dispose();
        }

        if (e.getSource() == volver) {
            new ChooseWind();
            this.dispose();
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}