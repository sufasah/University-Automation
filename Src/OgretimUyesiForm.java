import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
public class OgretimUyesiForm extends KullaniciForm{

	private static final long serialVersionUID = 529279254865238701L;
	OgretimUyesi ou;
	
	public OgretimUyesiForm(OgretimUyesi ou) {
		super(ou,"Öðretim Üyesi Paneli",4);

		this.ou=ou;
		
		Border eBorder=BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.WHITE,null);
		
		navBList.get(0).setText("Profil");
		navBList.get(1).setText("Devamsýzlýk Gir");
		navBList.get(2).setText("Not Gir");
		navBList.get(3).setText("Ders Programý");

//////////////////////////////////////////////////////////////////////////////
		OgretimUyesiProfil contP0 = new OgretimUyesiProfil(ou);
		OgretimUyesiDevamsizlik contP1 = new OgretimUyesiDevamsizlik(ou);
		OgretimUyesiNot contP2 = new OgretimUyesiNot(ou);
		OgretimUyesiDersProgrami contP3 = new OgretimUyesiDersProgrami(ou);
		
		contPList.add(contP0);
		contPList.add(contP1);
		contPList.add(contP2);
		contPList.add(contP3);
		
		for(int i=0;i<navBCount;i++) {
			JPanel p = contPList.get(i);
			JScrollPane cont = new JScrollPane(p);
			cont.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			cont.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			cont.setBorder(eBorder);
			
			contList.add(cont);
			
			add(cont);
			cont.setVisible(false);
			
		}
		contList.get(0).setVisible(true);
		
		
//////////////////////////////////////////////////////////////////////////////
		
		OgretimUyesiFormComponentAdapter ogrenciCL = new OgretimUyesiFormComponentAdapter(this);
			
//////////////////////////////////////////////////////////////////////////////
		
		addComponentListener(ogrenciCL);
		
		
//////////////////////////////////////////////////////////////////////////////
		
		setVisible(true);
	}
}
