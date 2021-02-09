import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class OgrenciForm extends KullaniciForm{

	private static final long serialVersionUID = -2182816049667495860L;
	Ogrenci ogr;
	
	OgrenciForm(Ogrenci ogr){
		super(ogr,"Ogrenci Paneli",4);
		
		this.ogr=ogr;

		Border eBorder=BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.WHITE,null);
		
		navBList.get(0).setText("Profil");
		navBList.get(1).setText("Devamsýzlýk");
		navBList.get(2).setText("Notlar");
		navBList.get(3).setText("Ders Programý");

//////////////////////////////////////////////////////////////////////////////
		OgrenciProfil contP0 = new OgrenciProfil(ogr);
		OgrenciDevamsizlik contP1 = new OgrenciDevamsizlik(ogr);		
		OgrenciNot contP2 = new OgrenciNot(ogr);
		OgrenciDersProgrami contP3 = new OgrenciDersProgrami(ogr);
		
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
		
		OgrenciFormComponentAdapter ogrenciCL = new OgrenciFormComponentAdapter(this);
			
//////////////////////////////////////////////////////////////////////////////
		
		addComponentListener(ogrenciCL);
		
		
//////////////////////////////////////////////////////////////////////////////
		
		setVisible(true);
	}
	
}
