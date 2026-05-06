package View;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoiceWin extends JFrame {

    private JButton crear = new JButton("Crear pregunta");
    private JButton modificar = new JButton("Modificar pregunta");
    private JButton eliminar = new JButton("Eliminar pregunta");
    private JButton buscar = new JButton("Buscar pregunta");
    private JButton examen = new JButton("Crear examen");
    private JButton logOut = new JButton("Cerrar sesion");

    public ChoiceWin() {
        UiUtils.setupFrame(this, "Menu principal", 380, 380);

        JPanel panel = UiUtils.createPanel();
        JLabel title = UiUtils.createTitle("Gestion de preguntas");
        panel.add(title, UiUtils.gbc(0, 0, 2));

        panel.add(crear, UiUtils.gbc(0, 1, 2));
        panel.add(modificar, UiUtils.gbc(0, 2, 2));
        panel.add(eliminar, UiUtils.gbc(0, 3, 2));
        panel.add(buscar, UiUtils.gbc(0, 4, 2));
        panel.add(examen, UiUtils.gbc(0, 5, 2));
        panel.add(logOut, UiUtils.gbc(0, 6, 2));

        UiUtils.styleButton(crear);
        UiUtils.styleButton(modificar);
        UiUtils.styleButton(eliminar);
        UiUtils.styleButton(buscar);
        UiUtils.styleButton(examen);
        UiUtils.styleButton(logOut);

        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new NewQuest();
            }
        });

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ModifyQuest();
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DeleteQuest();
            }
        });

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SearchQuest();
            }
        });

        examen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateExam();
            }
        });

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserPwd();
            }
        });
    }

    public static void main(String[] args) {
        new ChoiceWin();
    }
}
