package frontend;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import CRUD.DB_querys;

public class NewUser extends JFrame implements MouseListener
{
	public static NewUser nu;

	JLabel basePanel = new JLabel();
	JLabel userText = new JLabel("Nombre de usuario:");
	JLabel pwdText = new JLabel("Contraseña:");
	JTextArea user = new JTextArea();
	JTextArea pwd = new JTextArea();
	JButton register = new JButton("Registrar");
	JButton cancel = new JButton("Cancelar");

	public NewUser()
	{
		Container basePanel = this.getContentPane();

		this.setBounds(400 , 60, 300, 300);

		register.addMouseListener(this);
		cancel.addMouseListener(this);

		basePanel.setLayout(new GridLayout(3, 2));
		basePanel.add(userText);
		basePanel.add(user);
		basePanel.add(pwdText);
		basePanel.add(pwd);
		basePanel.add(register);
		basePanel.add(cancel);
		basePanel.setVisible(true);


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		nu = new NewUser();
	}

	public boolean comprobar()
	{
		ResultSet rs = DB_querys.getUserPwd();
		try {
			while (rs.next())
			{
				if (rs.getString("nombre").equals(user.getText()))
				{
					rs.close();
					return true;
				}
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (((JButton)e.getSource()).getText().equals("Registrar"))
		{
			if (comprobar())
			{
				JOptionPane.showMessageDialog(null, "Nombre de usuario ya registrado");
			}
			else
			{
				DB_querys.createUser(user.getText(), pwd.getText());
				this.dispose();
				UserPwd.up = new UserPwd();
			}

		}
		
		if (((JButton)e.getSource()).getText().equals("Cancelar"))
		{
			this.dispose();
			UserPwd.up = new UserPwd();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
