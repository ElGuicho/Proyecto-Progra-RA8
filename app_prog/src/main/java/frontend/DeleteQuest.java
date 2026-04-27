package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DeleteQuest extends JFrame implements MouseListener {

    JTextField id = new JTextField();

    JButton eliminar = new JButton("Eliminar");
    JButton volver = new JButton("Volver");

    public DeleteQuest() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 300, 150);
        panel.setLayout(new GridLayout(4, 1));

        panel.add(new JLabel("ID Pregunta"));
        panel.add(id);
        panel.add(eliminar);
        panel.add(volver);

        eliminar.addMouseListener(this);
        volver.addMouseListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == eliminar) {
            JOptionPane.showMessageDialog(null, "Pregunta eliminada");
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