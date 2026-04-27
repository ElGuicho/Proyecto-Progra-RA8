package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddTest extends JFrame implements MouseListener {

    JTextField enunciado = new JTextField();
    JTextField r1 = new JTextField();
    JTextField r2 = new JTextField();
    JTextField r3 = new JTextField();
    JTextField r4 = new JTextField();

    JTextField correcta = new JTextField();

    JButton guardar = new JButton("Guardar");
    JButton volver = new JButton("Volver");

    public AddTest() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 350, 350);
        panel.setLayout(new GridLayout(9, 1));

        panel.add(new JLabel("Enunciado"));
        panel.add(enunciado);
        panel.add(r1);
        panel.add(r2);
        panel.add(r3);
        panel.add(r4);
        panel.add(new JLabel("Respuesta correcta (0-3)"));
        panel.add(correcta);
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
            JOptionPane.showMessageDialog(null, "Pregunta TEST creada");
            new ChoiceWin();
            this.dispose();
        }

        if (e.getSource() == volver) {
            new NewQuest();
            this.dispose();
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}