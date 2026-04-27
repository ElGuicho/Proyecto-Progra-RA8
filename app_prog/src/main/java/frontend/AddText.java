package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddText extends JFrame implements MouseListener {

    JTextField enunciado = new JTextField();
    JTextArea respuesta = new JTextArea();

    JButton guardar = new JButton("Guardar");
    JButton volver = new JButton("Volver");

    public AddText() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 350, 300);
        panel.setLayout(new GridLayout(6, 1));

        panel.add(new JLabel("Enunciado"));
        panel.add(enunciado);
        panel.add(new JLabel("Respuesta"));
        panel.add(respuesta);
        panel.add(guardar);
        panel.add(volver);

        guardar.addMouseListener(this);
        volver.addMouseListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == guardar) {
            JOptionPane.showMessageDialog(null, "Pregunta DESARROLLO creada");
            new ChooseWind();
            this.dispose();
        }

        if (e.getSource() == volver) {
            new ChooseQuest();
            this.dispose();
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}