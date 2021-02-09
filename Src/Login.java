import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame{
	private static final long serialVersionUID = 1872893085359202187L;
	JLabel tc,sifre;
	JTextField tftc;
	JPasswordField pfsifre;
	JButton giris;
	Login(){
		super("Kullanici Girisi");
		Toolkit toolkit= getToolkit();
		Dimension screen = toolkit.getScreenSize();
		setSize(screen.width*30/100,screen.height*30/100);
		setLocation(screen.width/2-getWidth()/2, screen.height/2-getHeight()/2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		setMinimumSize(getSize());
		setMaximumSize(new Dimension(getWidth()*2,getHeight()*2));
		
//////////////////////////////////////////////////////////////////////////////
		
		tc = new JLabel("Tc-Kimlik");
		
		tftc=new JTextField();
		
		sifre = new JLabel("Þifre");
		
		pfsifre = new JPasswordField();
		
		giris = new JButton("GÝRÝÞ");
		
//////////////////////////////////////////////////////////////////////////////
		
		add(tc);
		add(tftc);
		add(sifre);
		add(pfsifre);
		add(giris);
		
//////////////////////////////////////////////////////////////////////////////
		
		EmptyAction empty= new EmptyAction();
		
//////////////////////////////////////////////////////////////////////////////
		
		EnteredTextFieldAction tftcAEnter = new EnteredTextFieldAction();
			tftcAEnter.b=giris;
		
		InputMap tftcI= tftc.getInputMap();
		ActionMap tftcA= tftc.getActionMap();
		tftcI.put(KeyStroke.getKeyStroke("pressed PASTE"), "empty");
		tftcI.put(KeyStroke.getKeyStroke("ctrl pressed V"), "empty");
		tftcI.put(KeyStroke.getKeyStroke("shift pressed INSERT"), "empty");
		tftcI.put(KeyStroke.getKeyStroke("pressed ENTER"),"TextFieldEntered");
		tftcA.put("empty", empty);
		tftcA.put("TextFieldEntered", tftcAEnter);
		
		tftc.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char k=e.getKeyChar();
				if(!Character.isDigit(k)||((JTextField)e.getSource()).getText().length()==11)
					e.consume();
			}
		});
		
//////////////////////////////////////////////////////////////////////////////
		
		InputMap pfsifreI = pfsifre.getInputMap();
		ActionMap pfsifreA= pfsifre.getActionMap();
		pfsifreI.put(KeyStroke.getKeyStroke("pressed PASTE"), "empty");
		pfsifreI.put(KeyStroke.getKeyStroke("ctrl pressed V"), "empty");
		pfsifreI.put(KeyStroke.getKeyStroke("shift pressed INSERT"), "empty");
		pfsifreI.put(KeyStroke.getKeyStroke("pressed ENTER"),"TextFieldEntered");
		pfsifreA.put("empty", empty);
		pfsifreA.put("TextFieldEntered", tftcAEnter);
		
//////////////////////////////////////////////////////////////////////////////		
		
		GirisAction girisAClick = new GirisAction();
			girisAClick.tc=tftc;
			girisAClick.sifre=pfsifre;
			girisAClick.frame=this;
		
		giris.getInputMap().put(KeyStroke.getKeyStroke("pressed ENTER"),"Entered");
		giris.getActionMap().put("Entered", tftcAEnter);

		giris.addActionListener(girisAClick);
		
//////////////////////////////////////////////////////////////////////////////
		
		LoginComponentAdapter loginCL = new LoginComponentAdapter();
			loginCL.frame=this;
			loginCL.giris=giris;
			loginCL.pfsifre=pfsifre;
			loginCL.sifre=sifre;
			loginCL.tc=tc;
			loginCL.tftc=tftc;
			
//////////////////////////////////////////////////////////////////////////////			
		
		addComponentListener(loginCL);
		
//////////////////////////////////////////////////////////////////////////////
		
		setVisible(true);
	}
}
