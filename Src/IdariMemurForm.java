import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
public class IdariMemurForm extends KullaniciForm{

	private static final long serialVersionUID = 529279254865238701L;
	IdariMemur im;
	
	public IdariMemurForm(IdariMemur im) {
		super(im,"ÝdariMemur Paneli",8);

		this.im=im;
		
		Border eBorder=BorderFactory.createEtchedBorder(EtchedBorder.RAISED,Color.WHITE,null);
		
		navBList.get(0).setText("Profil");
		navBList.get(1).setText("Kullanýcý Ekle");
		navBList.get(2).setText("Kullanýcý Sil/Düzenle");
		navBList.get(3).setText("Ders Ekle");
		navBList.get(4).setText("Ders Sil/Düzenle");
		navBList.get(5).setText("Verilen Ders Ekle/Sil");
		navBList.get(6).setText("Alýnan Ders Ekle/Sil");
		navBList.get(7).setText("Programa Ders Ekle/Sil");

//////////////////////////////////////////////////////////////////////////////
		
		IdariMemurProfil contP0 = new IdariMemurProfil(im);
		IdariMemurKullaniciEkle contP1 = new IdariMemurKullaniciEkle(im);
		IdariMemurKullaniciDuzenleSil contP2 = new IdariMemurKullaniciDuzenleSil(im,this);
		IdariMemurDersEkle contP3 = new IdariMemurDersEkle(im);
		IdariMemurDersDuzenleSil contP4 = new IdariMemurDersDuzenleSil(im,this);
		IdariMemurVerilenDersEkleSil contP5 = new IdariMemurVerilenDersEkleSil(im,this);
		IdariMemurAlinanDersEkleSil contP6 = new IdariMemurAlinanDersEkleSil(im,this);
		IdariMemurProgramEkleSil contP7 = new IdariMemurProgramEkleSil(im,this);

		contPList.add(contP0);
		contPList.add(contP1);
		contPList.add(contP2);
		contPList.add(contP3);
		contPList.add(contP4);
		contPList.add(contP5);
		contPList.add(contP6);
		contPList.add(contP7);
		
		
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
		
		IdariMemurFormComponentAdapter ogrenciCL = new IdariMemurFormComponentAdapter(this);
			
//////////////////////////////////////////////////////////////////////////////
		
		addComponentListener(ogrenciCL);
		
		
//////////////////////////////////////////////////////////////////////////////
		
		setVisible(true);
	}
}
