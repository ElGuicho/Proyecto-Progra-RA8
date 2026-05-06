package View;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class NewQuest extends JFrame {

    private JRadioButton testOption = new JRadioButton("Pregunta tipo TEST");
    private JRadioButton desarrolloOption = new JRadioButton("Pregunta tipo DESARROLLO");
    private JButton continuar = new JButton("Continuar");

    public NewQuest() {
        UiUtils.setupFrame(this, "Tipo de pregunta", 380, 240);

        JPanel panel = UiUtils.createPanel();
        panel.add(UiUtils.createTitle("Selecciona el tipo de pregunta"), UiUtils.gbc(0, 0, 2));

        ButtonGroup group = new ButtonGroup();
        group.add(testOption);
        group.add(desarrolloOption);

        panel.add(testOption, UiUtils.gbc(0, 1, 2));
        panel.add(desarrolloOption, UiUtils.gbc(0, 2, 2));
        panel.add(continuar, UiUtils.gbc(0, 3, 2));

        UiUtils.styleButton(continuar);

        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        continuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (testOption.isSelected()) {
                    dispose();
                    new AddTest();
                } else if (desarrolloOption.isSelected()) {
                    dispose();
                    new AddText();
                } else {
                    UiUtils.showError(NewQuest.this, "Selecciona un tipo de pregunta.");
                }
            }
        });
    }
}
