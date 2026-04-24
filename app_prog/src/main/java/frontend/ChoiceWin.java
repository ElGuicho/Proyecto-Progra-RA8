package frontend;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;


public class ChoiceWin extends JFrame implements MouseListener {
	//hay que dar opcion de añadir preguntas, modificar o eliminar
	JLabel title = new JLabel("Crear Pregunta");
	JLabel text = new JLabel("Seleccione el tipo de pregunta");

	JRadioButton TipoTest = new JRadioButton("Pregunta tipo Test");
	JRadioButton TipoDesarrollo = new JRadioButton("Pregunta tipo Dsarrollo");

	JRadioButton continueButton = new JRadioButton("Continuar");

	public ChoiceWin(){
		Container panel = this.getContentPane();
		this.setBounds(400,100,300,200);
		panel.setLayout(new GridLayout(5,1));

		ButtonGroup grupo = new ButtonGroup();
		grupo.add (TipoTest);
		grupo.add (TipoDesarrollo);

		continueButton.addMouseListener((MouseListener) this);

		panel.add(title);
		panel.add(text);
		panel.add(TipoTest);
		panel.add(TipoDesarrollo);
		panel.add(continueButton);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
		public static void main(String[] args) {
			new ChoiceWin();
		}
		@Override
		public void mouseClicked(MouseEvent e){
	if (((JButton)e.getSource()).getText().equals("Continuar")){
			if (TipoTest.isSelected()){
				JOptionPane.showMessageDialog(null,"Seleccionado Tipo Test");   
			}else if (TipoDesarrollo.isSelected()){
				JOptionPane.showMessageDialog(null, "Seleccionado Tipo Desarrollo");
			}else{
				JOptionPane.showMessageDialog(null, "Seleccione un Tipo");
			}
	}
	}
	@Override public void mousePressed(MouseEvent e){}
	@Override public void mouseReleased(MouseEvent e){}
	@Override public void mouseEntered(MouseEvent e){}
	@Override public void mouseExited(MouseEvent e){}
}