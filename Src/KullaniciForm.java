import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
public abstract class KullaniciForm extends JFrame{
	
	private static final long serialVersionUID = -3780053243382406289L;
	JPanel head;
	JScrollPane nav;
	JButton logout,guncelle;
	JPanel navP;
	JLabel title;
	Kullanici k;
	ArrayList<JButton> navBList;
	ArrayList<JPanel> contPList;
	ArrayList<JScrollPane> contList;
	int navBCount;
	int selectedNavButton=0;
	KullaniciForm(Kullanici k,String str,int navBcount){
		super(str);
		
		this.k=k;
		navBCount=navBcount;
		
		Toolkit toolkit= getToolkit();
		Dimension screen = toolkit.getScreenSize();
		setSize(screen.width*80/100,screen.height*80/100);
		setLocation(screen.width/2-getWidth()/2, screen.height/2-getHeight()/2);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		setMinimumSize(new Dimension(getWidth()*2/3,getHeight()*2/3));
//////////////////////////////////////////////////////////////////////////////

		head= new JPanel();
		String sTit=null;
		if(k.getClass()==Ogrenci.class)
			sTit="ÖÐRENCÝ";
		else if(k.getClass()==OgretimUyesi.class)
			sTit="ÖÐRETÝM ÜYESÝ";
		else if(k.getClass()==IdariMemur.class)
			sTit="ÝDARÝ MEMUR";
		title = new JLabel(sTit+" - "+k.ad.toUpperCase()+" "+k.soyad.toUpperCase());
		
		logout=new JButton("Çýkýþ");
		
		guncelle= new JButton("Güncelle");
		
		navP=new JPanel();
		
		nav= new JScrollPane(navP);
		
		contPList = new ArrayList<JPanel>();
		
		contList=new ArrayList<JScrollPane>();
		
		navBList= new ArrayList<JButton>();
		
		NavButtonMouseAdapter navBML = new NavButtonMouseAdapter(this);		
		Border eBorder=BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.WHITE,null);
		Border bBorder=NavButtonMouseAdapter.bBorder;
		Border bBorderClicked=NavButtonMouseAdapter.bBorderClicked;
		Color but=NavButtonMouseAdapter.but;
		Color butClicked=NavButtonMouseAdapter.butClicked;
		Color tColor = new Color(100,100,100);
//////////////////////////////////////////////////////////////////////////////
		
		head.setBorder(eBorder);
		head.setLayout(null);
		head.add(title);
		head.add(logout);
		head.add(guncelle);
//////////////////////////////////////////////////////////////////////////////
		
		logout.setBorder(bBorderClicked);
		logout.setBackground(but);
		logout.setFocusable(false);
		logout.setForeground(tColor);
		
		LogoutActionListener logoutAL = new LogoutActionListener(this);
		logout.addActionListener(logoutAL);
		
//////////////////////////////////////////////////////////////////////////////
		
		guncelle.setBorder(bBorderClicked);
		guncelle.setBackground(but);
		guncelle.setFocusable(false);
		guncelle.setForeground(tColor);
		
		GuncelleActionListener guncelleAL = new GuncelleActionListener(this);
		guncelle.addActionListener(guncelleAL);
		
		title.setForeground(tColor);

//////////////////////////////////////////////////////////////////////////////
		nav.setBorder(eBorder);
		nav.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		nav.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		navP.setLayout(null);
	
//////////////////////////////////////////////////////////////////////////////
		
		for(int i=0;i<navBCount;i++) {
			JButton b = new JButton();
			navBList.add(b);
			navP.add(b);
			b.setBackground(but);
			b.setBorder(bBorder);
			b.setFocusable(false);
			for(MouseListener ml:b.getMouseListeners())
				b.removeMouseListener(ml);

			b.addMouseListener(navBML);
		}
		navBList.get(0).setBorder(bBorderClicked);
		navBList.get(0).setBackground(butClicked);
		navBList.get(navBCount-1).setSize(navBList.get(navBCount-1).getWidth(),navBList.get(navBCount-1).getHeight()+nav.getHeight()%navBCount);
		
		add(head,BorderLayout.NORTH);
		add(nav,BorderLayout.WEST);
	}
	
}
