package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChoiceWin extends JFrame {

    private JButton crear = new JButton("Crear pregunta");
    private JButton modificar = new JButton("Modificar pregunta");
    private JButton eliminar = new JButton("Eliminar pregunta");
    private JButton buscar = new JButton("Buscar pregunta");
    private JButton examen = new JButton("Crear examen");
    private JButton logOut = new JButton("Cerrar sesion");

    public ChoiceWin() {
        UiUtils.setupFrame(this, "Menu principal", 440, 460);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(new Color(240, 244, 250));
        outerPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel cardPanel = new JPanel(new BorderLayout(0, 14));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new EmptyBorder(20, 24, 24, 24));

        JLabel title = new JLabel("Gestión de preguntas", JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setForeground(new Color(34, 64, 128));

        JLabel subtitle = new JLabel("Selecciona una acción para continuar", JLabel.CENTER);
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 14f));
        subtitle.setForeground(new Color(100, 110, 130));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 6));
        header.setOpaque(false);
        header.add(title);
        header.add(subtitle);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 0, 12));
        buttonPanel.setOpaque(false);

        setupPrimaryButton(crear);
        setupPrimaryButton(modificar);
        setupPrimaryButton(eliminar);
        setupPrimaryButton(buscar);
        setupPrimaryButton(examen);
        setupPrimaryButton(logOut);

        buttonPanel.add(crear);
        buttonPanel.add(modificar);
        buttonPanel.add(eliminar);
        buttonPanel.add(buscar);
        buttonPanel.add(examen);
        buttonPanel.add(logOut);

        cardPanel.add(header, BorderLayout.NORTH);
        cardPanel.add(buttonPanel, BorderLayout.CENTER);

        outerPanel.add(cardPanel, BorderLayout.CENTER);

        setContentPane(outerPanel);
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

    private void setupPrimaryButton(JButton button) {
        UiUtils.styleButton(button);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 44));
    }

    public static void main(String[] args) {
        new ChoiceWin();
    }
}
