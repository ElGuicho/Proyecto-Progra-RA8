package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewQuest extends JFrame implements MouseListener {

    JRadioButton test = new JRadioButton("Tipo Test");
    JRadioButton desarrollo = new JRadioButton("Desarrollo");
    JButton continuar = new JButton("Continuar");

    public NewQuest() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 300, 200);
        panel.setLayout(new GridLayout(4, 1));

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(test);
        grupo.add(desarrollo);

        panel.add(new JLabel("CREAR PREGUNTA"));
        panel.add(test);
        panel.add(desarrollo);
        panel.add(continuar);

        continuar.addMouseListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == continuar) {

            if (test.isSelected()) {
                new AddTest();
                this.dispose();
            } else if (desarrollo.isSelected()) {
                new AddText();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un tipo");
            }
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}