package frontend;

import java.awt.Container;
import java.awt.FlowLayout;
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

public class UserPwd extends JFrame implements MouseListener, KeyListener
{
	public static UserPwd up;

	JLabel upPanel = new JLabel();
	JLabel userText = new JLabel("Nombre de usuario:");
	JLabel pwdText = new JLabel("Contraseña:");
	JTextArea user = new JTextArea();
	JTextArea pwd = new JTextArea();
	JButton confirm = new JButton("Aceptar");
	JButton cancel = new JButton("Cancelar");
	JButton register = new JButton("Aun no registrado");
	JLabel entrada = new JLabel("Bienvenidos a la aplicacion !!!");

	public UserPwd()
	{
		Container basePanel = this.getContentPane();

		this.setBounds(400 , 60, 300, 300);

		upPanel.setLayout(new GridLayout(3, 2));
		upPanel.add(userText);
		upPanel.add(user);
		upPanel.add(pwdText);
		upPanel.add(pwd);
		upPanel.add(confirm);
		upPanel.add(cancel);
		user.addKeyListener(this);
		pwd.addKeyListener(this);
		confirm.addMouseListener(this);
		cancel.addMouseListener(this);
		upPanel.setVisible(true);
		
		basePanel.setLayout(new GridLayout(2, 1));
		basePanel.add(upPanel);
		basePanel.add(register);
		register.addMouseListener(this);
		basePanel.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		up = new UserPwd();
	}

	public boolean comprobar()
	{
		ResultSet rs = DB_querys.getUserPwd();
		try {
			while (rs.next())
			{
				if (rs.getString("nombre").equals(user.getText()) && rs.getString("password_hash").equals(Integer.toString(pwd.getText().hashCode())))
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
		if (((JButton)e.getSource()).getText().equals("Aceptar"))
		{
			if (comprobar())
			{
				this.dispose();
				Container basePanel = this.getContentPane();

				this.setBounds(400 , 60, 300, 300);
				basePanel.removeAll();
				basePanel.setLayout(new FlowLayout());
				basePanel.add(entrada);
				basePanel.setVisible(true);

				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.setVisible(true);
			}
			else
				JOptionPane.showMessageDialog(null, "Datos incorrectos");
		}
		
		if (((JButton)e.getSource()).getText().equals("Cancelar"))
		{
			this.dispose();
		}

		if (((JButton)e.getSource()).getText().equals("Aun no registrado"))
		{
			this.dispose();
			NewUser.nu = new NewUser();
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
				pwd.setText(user.getText().replace("\n", ""));
				if (comprobar())
				{
					this.dispose();
					Container basePanel = this.getContentPane();

					this.setBounds(400 , 60, 300, 300);
					basePanel.removeAll();
					basePanel.setLayout(new FlowLayout());
					basePanel.add(entrada);
					basePanel.setVisible(true);

					this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					this.setVisible(true);
				}
				else
				{
					user.grabFocus();
					JOptionPane.showMessageDialog(null, "Datos incorrectos");
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
