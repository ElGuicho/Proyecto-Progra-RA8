package frontend;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class NewUser extends JFrame implements MouseListener, KeyListener
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
		user.addKeyListener(this);
		pwd.addKeyListener(this);

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

	public boolean verifyPwd(String passwd) {
		boolean spChars = passwd.matches(".*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]\\\\^_`{|}~].*");
		boolean lower = passwd.matches(".*[a-z].*");
		boolean upper = passwd.matches(".*[A-Z].*");
		boolean nums = passwd.matches(".*[0-9].*");

		if (passwd.length() < 12)
			JOptionPane.showMessageDialog(null, "La contraseña debe de tener 12 o más carácteres");
		else if (!lower)
			JOptionPane.showMessageDialog(null, "La contraseña debe de tener minúsculas");
		else if (!upper)
			JOptionPane.showMessageDialog(null, "La contraseña debe de tener mayúsculas");
		else if (!nums)
			JOptionPane.showMessageDialog(null, "La contraseña debe de tener números");
		else if (!spChars)
			JOptionPane.showMessageDialog(null, "La contraseña debe de tener carácteres especiales (!, #, $, %, &, etc.)");
		else
			return true;
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (((JButton)e.getSource()).getText().equals("Registrar"))
		{
			if (comprobar())
			{
				JOptionPane.showMessageDialog(null, "Nombre de usuario ya registrado");
				pwd.grabFocus();
			}
			else
			{
				if (verifyPwd(pwd.getText()))
				{
					DB_querys.createUser(user.getText(), pwd.getText());
					this.dispose();
					UserPwd.up = new UserPwd();
				}
				else
					pwd.grabFocus();
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

	@Override
	public void keyTyped(KeyEvent e) {
		if ((e.getKeyChar() == '\n'))
		{
			if (user.hasFocus())
			{
				user.setText(user.getText().replace("\n", ""));
				pwd.grabFocus();
			}
			else if (pwd.hasFocus())
			{
				pwd.setText(pwd.getText().replace("\n", ""));
				if (comprobar())
				{
					pwd.grabFocus();
					JOptionPane.showMessageDialog(null, "Nombre de usuario ya registrado");
				}
				else
				{
					if (verifyPwd(pwd.getText()))
					{
						DB_querys.createUser(user.getText(), pwd.getText());
						this.dispose();
						UserPwd.up = new UserPwd();
					}
					else
						pwd.grabFocus();
				}
			}
		}
		if (user.getText().length() >= 50 || pwd.getText().length() >= 50)
			e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
