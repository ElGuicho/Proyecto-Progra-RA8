package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import CRUD.QuestionQuerys;
import backend.PreguntaDesarrollo;
import backend.PreguntaTest;
import backend.Pregunta;

public class DeleteQuest extends JFrame implements MouseListener {

    JTextField id = new JTextField();

    JButton eliminar = new JButton("Eliminar");
    JButton volver = new JButton("Volver");
    JButton tipo = new JButton("Tipo de pregunta");

	public DeleteQuest() {

        Container panel = this.getContentPane();
        this.setBounds(400, 100, 300, 150);
        panel.setLayout(new GridLayout(5, 1));

        panel.add(new JLabel("Filtros de búsqueda"));
        panel.add(tipo); // Dos opciones: desarrollo o test
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
		boolean isfalse = true;
		Pregunta delQuest;

        if (e.getSource() == eliminar) {
			if (id.getText().isEmpty()) {
				if (isfalse) // Seleccion de tipo de pregunta
					delQuest = new PreguntaTest(/*Atributos filtro*/);
				else
					delQuest =  new PreguntaDesarrollo(/*Atributos filtro*/);

				if (delQuest.coincideFiltro()) {
					QuestionQuerys.rmQuest(delQuest.getId());
					JOptionPane.showMessageDialog(null, "Pregunta eliminada");
					new ChoiceWin();
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Pregunta no encontrada");
				}
			} else {
				QuestionQuerys.rmQuest(Integer.parseInt(id.getText()));
				JOptionPane.showMessageDialog(null, "Pregunta eliminada");
				new ChoiceWin();
				this.dispose();
			}
        }

        if (e.getSource() == volver) {
            new ChoiceWin();
            this.dispose();
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}