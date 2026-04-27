package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChoiceWin extends JFrame implements MouseListener {

    JButton crear = new JButton("Crear");
    JButton modificar = new JButton("Modificar");
    JButton eliminar = new JButton("Eliminar");
    JButton buscar = new JButton("Buscar pregunta");
    JButton examen = new JButton("Crear examen");
    JButton logOut = new JButton("Cerrar sesion");

    public ChoiceWin() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 300, 250);
        panel.setLayout(new GridLayout(7, 1));

        panel.add(new JLabel("GESTIÓN DE PREGUNTAS"));
        panel.add(crear);
        panel.add(modificar);
        panel.add(eliminar);
        panel.add(buscar);
        panel.add(examen);
        panel.add(logOut);

        crear.addMouseListener(this);
        modificar.addMouseListener(this);
        eliminar.addMouseListener(this);
        examen.addMouseListener(this);
        buscar.addMouseListener(this);
        logOut.addMouseListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ChoiceWin();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == crear) {
            new NewQuest();
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

        if (e.getSource() == buscar) {
            new SearchQuest();
            this.dispose();
        }

        if (e.getSource() == examen) {
            new CreateExam();
            this.dispose();
        }

        if (e.getSource() == logOut) {
            new UserPwd();
            this.dispose();
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
