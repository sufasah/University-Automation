import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;


import java.util.Iterator;


class EmptyAction extends AbstractAction{
	
	private static final long serialVersionUID = -1839317141140040223L;

	public void actionPerformed(ActionEvent e) {}
}


class EnteredTextFieldAction extends AbstractAction{
	private static final long serialVersionUID = -3692697441251805672L;
	JButton b;
	public void actionPerformed(ActionEvent e) {
		b.doClick();
	}
}


class GirisAction extends AbstractAction{
	private static final long serialVersionUID = -4403020275435114392L;
	JFrame frame;
	JTextField tc;
	JPasswordField sifre;
	public void actionPerformed(ActionEvent e) {
		Kullanici k;
		try {
			String pass=String.copyValueOf(sifre.getPassword());
			if(FileHandler.kayitliKullaniciMi(tc.getText(), pass))
			{
				
				k=FileHandler.kullaniciGetir(tc.getText());
			}
			else {
				JOptionPane.showMessageDialog(frame, "Girilen Tc-Kimlik Numarası veya Şifre Hatalı !","Giriş",JOptionPane.ERROR_MESSAGE);
					return;
			}
		}
		catch(IOException ex){
			JOptionPane.showMessageDialog(frame, "Veritabanı Hatası! Lütfen Daha Sonra Tekrar Deneyiniz.","Giriş",JOptionPane.ERROR_MESSAGE);
			return;
		}catch (KayitBulunamadi e2) {
			JOptionPane.showMessageDialog(frame, "Kullanici Kayitli Ama Gerekli Bilgiler Getirilemiyor! Lütfen Daha Sonra Tekrar Deneyiniz","Giriş",JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(frame, "Giriş Başarılı !","Giriş",JOptionPane.INFORMATION_MESSAGE);
		frame.dispose();
		if(k.getClass()==Ogrenci.class)
			new OgrenciForm((Ogrenci)k);
		else if(k.getClass()==OgretimUyesi.class)
			new OgretimUyesiForm((OgretimUyesi)k);
		else if(k.getClass()==IdariMemur.class)
			new IdariMemurForm((IdariMemur)k);
	}
}



class GuncelleActionListener implements ActionListener{
	KullaniciForm fr;
	public GuncelleActionListener(KullaniciForm f) {
		fr=f;
	}
	public void actionPerformed(ActionEvent e) {
			Kullanici k=null;
		try {
			k=FileHandler.kullaniciGetir(fr.k.tcKimlik);
		}catch (IOException e2) {
			JOptionPane.showMessageDialog(fr, "Veritabani Hatasi! Lütfen Daha Sonra Tekrar Giriş Yapmayı Deneyiniz.","HATA!",JOptionPane.ERROR_MESSAGE);
			fr.logout.doClick();
		}catch (KayitBulunamadi e3) {
			JOptionPane.showMessageDialog(fr, "Bilgileriniz Silinmiş! Sistem Sahibiyle Görüşünüz.","HATA!",JOptionPane.ERROR_MESSAGE);
			fr.logout.doClick();
		}
		fr.dispose();
		
		if(k.getClass()==Ogrenci.class &&fr.getClass()==OgrenciForm.class) {
			new OgrenciForm((Ogrenci)k);
		}
		else if(k.getClass()==OgretimUyesi.class && fr.getClass()==OgretimUyesiForm.class) {
			new OgretimUyesiForm((OgretimUyesi)k);			
		}
		else if(k.getClass()==IdariMemur.class && fr.getClass()==IdariMemurForm.class) {
			new IdariMemurForm((IdariMemur)k);
		}
		else {
			JOptionPane.showMessageDialog(fr, "Bilgileriniz Değiştirilmiş! Tekrar Giriş Yapmayı Deneyiniz.","HATA!",JOptionPane.ERROR_MESSAGE);
			fr.logout.doClick();
		}
		
	}
}



class LoginComponentAdapter extends ComponentAdapter {
	Login frame;
	JLabel tc,sifre;
	JTextField tftc;
	JPasswordField pfsifre;
	JButton giris;
	public void componentResized(ComponentEvent e) {
		
		tc.setSize(frame.getWidth()/6, frame.getHeight()/10);
		tc.setLocation(frame.getWidth()/5,frame.getHeight()/5);
		tc.setFont(new Font("ArialBlack",Font.BOLD,(tc.getWidth()/5<tc.getHeight()*6/10?tc.getWidth()/5:tc.getHeight()*6/10)));
		tc.setForeground(new Color((frame.getWidth()+122)%256,(frame.getHeight()+122)%256,(frame.getWidth()+frame.getHeight()+122)%256));
		tftc.setSize(tc.getWidth()*5/2, tc.getHeight()*8/5);
		tftc.setLocation(tc.getX()+tc.getWidth()+frame.getWidth()/30,tc.getY()-tftc.getHeight()/4);
		tftc.setFont(new Font("ArialBlack",0,(tftc.getWidth()/5<tftc.getHeight()*6/10?tftc.getWidth()/5:tftc.getHeight()*6/10)));

		sifre.setSize(tc.getSize());
		sifre.setLocation(tc.getX(),tc.getY()+tc.getHeight()+frame.getHeight()/10);
		sifre.setFont(new Font("ArialBlack",Font.BOLD,(sifre.getWidth()/5<sifre.getHeight()*6/10?sifre.getWidth()/5:sifre.getHeight()*6/10)));
		sifre.setForeground(new Color((frame.getWidth()+122)%256,(frame.getHeight()+122)%256,(frame.getWidth()+frame.getHeight()+122)%256));
		
		pfsifre.setSize(tftc.getSize());
		pfsifre.setLocation(tftc.getX(), sifre.getY()-pfsifre.getHeight()/4);
		pfsifre.setFont(new Font("ArialBlack",0,(tc.getWidth()/5<pfsifre.getHeight()*6/10?pfsifre.getWidth()/5:pfsifre.getHeight()*6/10)));

		giris.setSize(tftc.getWidth()/2,tftc.getHeight());
		giris.setLocation(tftc.getX()+tftc.getWidth()-giris.getWidth()/2, pfsifre.getY()+pfsifre.getHeight()+frame.getHeight()/20);
		giris.setFont(new Font("ArialBlack",Font.BOLD,(giris.getWidth()/5<giris.getHeight()*6/10?giris.getWidth()/5:giris.getHeight()*6/10)));
		giris.setForeground(new Color((frame.getWidth()+122)%256,(frame.getHeight()+122)%256,(frame.getWidth()+frame.getHeight()+122)%256));
		
	}
}



class LogoutActionListener implements ActionListener{
	KullaniciForm fr;
	public LogoutActionListener(KullaniciForm f) {
		fr=f;
	}
	public void actionPerformed(ActionEvent e) {
		fr.dispose();
		new Login();
	}
}



class OgrenciFormComponentAdapter extends ComponentAdapter {
	OgrenciForm fr;
	OgrenciFormComponentAdapter(OgrenciForm form){
		fr=form;
	}
	public static void adaptFont(JComponent comp,Font f,String text) {
		while(true) {
			int strWidth=comp.getFontMetrics(f).stringWidth(text);
			int strHeight=comp.getFontMetrics(f).getHeight();
			if(strWidth<comp.getWidth()-5 || strHeight <comp.getHeight()-5)
				f=new Font(f.getName(),f.getSize(),f.getSize()+2);
			else
				break;
		}
		while(true) {
			int strWidth=comp.getFontMetrics(f).stringWidth(text);
			int strHeight=comp.getFontMetrics(f).getHeight();
			if(strWidth>comp.getWidth()-5 || strHeight >comp.getHeight()-5)
				f=new Font(f.getName(),f.getSize(),f.getSize()-2);
			else
				break;
		}
		comp.setFont(f);
	}
	public void componentResized(ComponentEvent e) {

		Container cPane =fr.getContentPane();
		JPanel navP=(JPanel)fr.nav.getViewport().getView();
		
		fr.head.setSize(cPane.getWidth(), cPane.getHeight()/8);
		fr.head.setLocation(0,0);
		
		fr.title.setSize(fr.head.getWidth()*2/3,fr.head.getHeight()*80/100);
		fr.title.setLocation(fr.head.getWidth()/20, fr.head.getHeight()*15/100);
		adaptFont(fr.title,new Font("ArialBlack",Font.ITALIC,fr.title.getHeight()/2),fr.title.getText());

		fr.logout.setSize(fr.head.getWidth()/12,fr.head.getHeight()*80/100);
		fr.logout.setLocation(fr.head.getWidth()*13/15,fr.head.getHeight()*15/100 );
		adaptFont(fr.logout,new Font("ArialBlack",Font.BOLD,fr.logout.getHeight()/2),fr.guncelle.getText());

		fr.guncelle.setSize(fr.head.getWidth()/12,fr.head.getHeight()*80/100);
		fr.guncelle.setLocation(fr.head.getWidth()*11/15,fr.head.getHeight()*15/100 );
		adaptFont(fr.guncelle,new Font("ArialBlack",Font.BOLD,fr.guncelle.getHeight()/2),fr.guncelle.getText());

		fr.nav.setSize(cPane.getWidth()/6,cPane.getHeight()-fr.head.getSize().height);
		fr.nav.setLocation(0, fr.head.getHeight());
		
		
		boolean tasti=(fr.navBCount*50>fr.nav.getHeight()?true:false);
		if(!tasti)
			navP.setPreferredSize(new Dimension(fr.nav.getWidth()-10,fr.nav.getHeight()-10));
		else
			navP.setPreferredSize(new Dimension(fr.nav.getWidth()-fr.nav.getVerticalScrollBar().getSize().width,50*fr.navBCount));

		int minSize=Integer.MAX_VALUE;
		
		for(int i=0;i<fr.navBCount;i++) {
			JButton b= fr.navBList.get(i);
			if(!tasti)
				b.setSize(fr.nav.getWidth()-3, fr.nav.getHeight()/fr.navBCount);
			else
				b.setSize(fr.nav.getWidth()-3-fr.nav.getVerticalScrollBar().getSize().width,50);
			b.setLocation(0, i*b.getHeight());
			
			adaptFont(b, new Font("ArialBlack",Font.BOLD,b.getHeight()/7), b.getText());
			minSize=b.getFont().getSize()>minSize?minSize:b.getFont().getSize();
		}
		for(int i=0;i<fr.navBCount;i++)
			fr.navBList.get(i).setFont(new Font(fr.navBList.get(i).getFont().getName(),fr.navBList.get(i).getFont().getStyle(),minSize));

		if(!tasti)
			fr.navBList.get(fr.navBCount-1).setSize(fr.navBList.get(0).getWidth(),fr.navBList.get(0).getHeight()+fr.nav.getHeight()%fr.navBCount );
		fr.nav.getVerticalScrollBar().setUnitIncrement(fr.navBList.get(0).getHeight()/2);
		
		fr.contList.get(fr.selectedNavButton).setSize(cPane.getWidth()-fr.nav.getWidth(), cPane.getHeight()-fr.head.getHeight());
		fr.contList.get(fr.selectedNavButton).setLocation(fr.nav.getX()+fr.nav.getWidth(), fr.head.getY()+fr.head.getHeight());
		
		fr.revalidate();
		
		JScrollPane c = null;
		if(fr.selectedNavButton==0) {
			OgrenciProfil pnl = (OgrenciProfil)fr.contPList.get(0);
			c=fr.contList.get(0);
			Font yazilar = new Font("TimesNewRoman",Font.PLAIN,pnl.getWidth()/30);
			Color yazirenk = new Color(100,70,70);
			int mS=9999999;
			
			pnl.tc.setLocation(pnl.getWidth()/20, pnl.getHeight()/20);
			pnl.tc.setSize(pnl.getWidth()*7/12-pnl.tc.getX(),(pnl.getHeight()-7*pnl.getHeight()/20)/7);
			pnl.tc.setForeground(yazirenk);
			
			pnl.foto.setSize(pnl.iFoto.getIconWidth(),pnl.iFoto.getIconHeight());
			pnl.foto.setLocation(pnl.tc.getX()+pnl.tc.getWidth()+pnl.getWidth()/15, pnl.getHeight()/20);
			pnl.foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
			
			pnl.no.setSize(pnl.tc.getSize());
			pnl.no.setLocation(pnl.tc.getX(), pnl.tc.getY()+pnl.tc.getHeight()+pnl.getHeight()/20);
			pnl.no.setForeground(yazirenk);

			pnl.adsoyad.setSize(pnl.no.getSize());
			pnl.adsoyad.setLocation(pnl.no.getX(), pnl.no.getY()+pnl.no.getHeight()+pnl.getHeight()/20);
			pnl.adsoyad.setForeground(yazirenk);
			
			pnl.bolum.setSize(pnl.adsoyad.getSize());
			pnl.bolum.setLocation(pnl.adsoyad.getX(), pnl.adsoyad.getY()+pnl.adsoyad.getHeight()+pnl.getHeight()/20);
			pnl.bolum.setForeground(yazirenk);
			
			pnl.dogumtarih.setSize(pnl.bolum.getSize());
			pnl.dogumtarih.setLocation(pnl.bolum.getX(), pnl.bolum.getY()+pnl.bolum.getHeight()+pnl.getHeight()/20);
			pnl.dogumtarih.setForeground(yazirenk);
			
			pnl.email.setSize(pnl.dogumtarih.getSize());
			pnl.email.setLocation(pnl.dogumtarih.getX(), pnl.dogumtarih.getY()+pnl.dogumtarih.getHeight()+pnl.getHeight()/20);
			pnl.email.setForeground(yazirenk);
						
			pnl.tel.setSize(pnl.email.getSize());
			pnl.tel.setLocation(pnl.email.getX(), pnl.email.getY()+pnl.email.getHeight()+pnl.getHeight()/20);
			pnl.tel.setForeground(yazirenk);

			adaptFont(pnl.tc,yazilar,pnl.tc.getText());
			mS=mS>pnl.tc.getFont().getSize()?pnl.tc.getFont().getSize():mS;
			adaptFont(pnl.no,yazilar,pnl.no.getText());
			mS=mS>pnl.no.getFont().getSize()?pnl.no.getFont().getSize():mS;
			adaptFont(pnl.adsoyad,yazilar,pnl.adsoyad.getText());
			mS=mS>pnl.adsoyad.getFont().getSize()?pnl.adsoyad.getFont().getSize():mS;
			adaptFont(pnl.bolum,yazilar,pnl.bolum.getText());
			mS=mS>pnl.bolum.getFont().getSize()?pnl.bolum.getFont().getSize():mS;
			adaptFont(pnl.dogumtarih,yazilar,pnl.dogumtarih.getText());
			mS=mS>pnl.dogumtarih.getFont().getSize()?pnl.dogumtarih.getFont().getSize():mS;
			adaptFont(pnl.email,yazilar,pnl.email.getText());
			mS=mS>pnl.email.getFont().getSize()?pnl.email.getFont().getSize():mS;
			adaptFont(pnl.tel,yazilar,pnl.tel.getText());
			mS=mS>pnl.tel.getFont().getSize()?pnl.tel.getFont().getSize():mS;

			
			pnl.tc.setFont(new Font(pnl.tc.getFont().getName(),pnl.tc.getFont().getStyle() , mS));
			pnl.no.setFont(new Font(pnl.no.getFont().getName(),pnl.no.getFont().getStyle() , mS));
			pnl.adsoyad.setFont(new Font(pnl.adsoyad.getFont().getName(),pnl.adsoyad.getFont().getStyle() , mS));
			pnl.bolum.setFont(new Font(pnl.bolum.getFont().getName(),pnl.bolum.getFont().getStyle() , mS));
			pnl.dogumtarih.setFont(new Font(pnl.dogumtarih.getFont().getName(),pnl.dogumtarih.getFont().getStyle() , mS));
			pnl.email.setFont(new Font(pnl.email.getFont().getName(),pnl.email.getFont().getStyle() , mS));
			pnl.tel.setFont(new Font(pnl.tel.getFont().getName(),pnl.tel.getFont().getStyle() , mS));
			
		}else if(fr.selectedNavButton==1) {
			OgrenciDevamsizlik pnl = (OgrenciDevamsizlik)fr.contPList.get(1);
			c=fr.contList.get(1);
			pnl.takvim.setSize(pnl.getWidth(),pnl.getHeight()*3/5);
			pnl.takvim.setLocation(0, pnl.getHeight()*2/5);
			
			pnl.hdr.setSize(pnl.getWidth(), pnl.takvim.getHeight()/12);
			pnl.hdr.setLocation(0, pnl.takvim.getY()-pnl.hdr.getHeight());
			
			pnl.dersler.setSize(pnl.getWidth()/3,pnl.getHeight()/12);
			pnl.dersler.setLocation(pnl.dersler.getWidth(),pnl.getHeight()/30 );

			pnl.secilengun.setSize(pnl.getWidth()*5/6,pnl.getHeight()/20);
			pnl.secilengun.setLocation(0, pnl.hdr.getY()-pnl.hdr.getHeight()*5/3);
			
			pnl.aytoplam.setSize(pnl.getWidth(),pnl.getHeight()/20);
			pnl.aytoplam.setLocation(0, pnl.secilengun.getY()-pnl.secilengun.getHeight()*5/3);
			
			pnl.donemtoplam.setSize(pnl.getWidth(),pnl.getHeight()/20);
			pnl.donemtoplam.setLocation(0, pnl.aytoplam.getY()-pnl.aytoplam.getHeight()*5/3);
			
			
			fr.revalidate(); 
			
			for( int i =0;i<9;i++) {
				TableColumn cm = pnl.hdr.getColumnModel().getColumn(i);
				int cmwidth=0;
				for(int j=0;j<3;j++)
					cmwidth+=pnl.takvim.getColumnModel().getColumn(i*3+j).getWidth();
				cm.setWidth(cmwidth);
			}
			pnl.takvim.setRowHeight(pnl.takvim.getHeight()/pnl.takvim.getModel().getRowCount());
			
			if(pnl.tiklananAy!=-1)
			{
				pnl.gosterge.setSize(pnl.hdr.getColumnModel().getColumn(pnl.tiklananAy).getWidth(),pnl.hdr.getHeight()/2);
				int genislik=0;
				for(int i=0;i<pnl.tiklananAy;i++)
					genislik+=pnl.hdr.getColumnModel().getColumn(i).getWidth();
				pnl.gosterge.setLocation(genislik, pnl.hdr.getY()-pnl.hdr.getHeight()/2);
			}
		}
		else if(fr.selectedNavButton==2) {
			OgrenciNot pnl = (OgrenciNot)fr.contPList.get(2);
			c=fr.contList.get(2);
			pnl.hdr.setSize(6*500, pnl.getHeight()/20);
			pnl.hdr.setLocation(0,0);
				pnl.notlar.setSize(pnl.getWidth(),50*pnl.dersList.size());
			pnl.setPreferredSize(new Dimension(pnl.notlar.getWidth(),pnl.notlar.getHeight()+pnl.hdr.getHeight()));
			pnl.notlar.setLocation(0, pnl.hdr.getHeight());
			c.getVerticalScrollBar().setUnitIncrement(pnl.notlar.getRowHeight()/2);
			c.getHorizontalScrollBar().setUnitIncrement(pnl.notlar.getColumnModel().getColumn(1).getWidth()/2);

			fr.revalidate();
			if(pnl.notlar.getRowCount()!=0)
				pnl.notlar.setRowHeight(pnl.notlar.getHeight()/pnl.notlar.getModel().getRowCount());
							
		}
		else if(fr.selectedNavButton==3) {
			OgrenciDersProgrami pnl = (OgrenciDersProgrami)fr.contPList.get(3);
			c=fr.contList.get(3);
			pnl.hdr.setSize(pnl.getWidth()-17, pnl.getHeight()/20);
			pnl.hdr.setLocation(0, 0);
			
			pnl.program.setSize(pnl.getWidth()-17, 11*50);
			pnl.setPreferredSize(new Dimension(0,pnl.program.getHeight()+pnl.hdr.getHeight()));
			pnl.program.setLocation(0, pnl.hdr.getHeight());
			c.getVerticalScrollBar().setUnitIncrement(pnl.program.getRowHeight()/2);

			fr.revalidate();
			
			pnl.program.setRowHeight(pnl.program.getHeight()/pnl.program.getModel().getRowCount());
		}
		
		fr.revalidate();

	}
}



class OgretimUyesiFormComponentAdapter extends ComponentAdapter {
	OgretimUyesiForm fr;
	OgretimUyesiFormComponentAdapter(OgretimUyesiForm form){
		fr=form;
	}
	public void componentResized(ComponentEvent e) {

		Container cPane =fr.getContentPane();
		JPanel navP=(JPanel)fr.nav.getViewport().getView();
		
		fr.head.setSize(cPane.getWidth(), cPane.getHeight()/8);
		fr.head.setLocation(0,0);
		
		fr.title.setSize(fr.head.getWidth()*2/3,fr.head.getHeight()*80/100);
		fr.title.setLocation(fr.head.getWidth()/20, fr.head.getHeight()*15/100);
		OgrenciFormComponentAdapter.adaptFont(fr.title,new Font("ArialBlack",Font.ITALIC,fr.title.getHeight()/2),fr.title.getText());

		fr.logout.setSize(fr.head.getWidth()/12,fr.head.getHeight()*80/100);
		fr.logout.setLocation(fr.head.getWidth()*13/15,fr.head.getHeight()*15/100 );
		OgrenciFormComponentAdapter.adaptFont(fr.logout,new Font("ArialBlack",Font.BOLD,fr.logout.getHeight()/2),fr.guncelle.getText());

		fr.guncelle.setSize(fr.head.getWidth()/12,fr.head.getHeight()*80/100);
		fr.guncelle.setLocation(fr.head.getWidth()*11/15,fr.head.getHeight()*15/100 );
		OgrenciFormComponentAdapter.adaptFont(fr.guncelle,new Font("ArialBlack",Font.BOLD,fr.guncelle.getHeight()/2),fr.guncelle.getText());

		fr.nav.setSize(cPane.getWidth()/6,cPane.getHeight()-fr.head.getSize().height);
		fr.nav.setLocation(0, fr.head.getHeight());
		
		
		boolean tasti=(fr.navBCount*50>fr.nav.getHeight()?true:false);
		if(!tasti)
			navP.setPreferredSize(new Dimension(fr.nav.getWidth()-10,fr.nav.getHeight()-10));
		else
			navP.setPreferredSize(new Dimension(fr.nav.getWidth()-fr.nav.getVerticalScrollBar().getSize().width,50*fr.navBCount));

		//navP.setSize(navP.getPreferredSize()); validate ile düzeldi
		int minSize=Integer.MAX_VALUE;
		for(int i=0;i<fr.navBCount;i++) {
			JButton b= fr.navBList.get(i);
			if(!tasti)
				b.setSize(fr.nav.getWidth()-3, fr.nav.getHeight()/fr.navBCount);
			else
				b.setSize(fr.nav.getWidth()-3-fr.nav.getVerticalScrollBar().getSize().width,50);
			b.setLocation(0, i*b.getHeight());
			
			OgrenciFormComponentAdapter.adaptFont(b, new Font("ArialBlack",Font.BOLD,b.getHeight()/7), b.getText());
			minSize=b.getFont().getSize()>minSize?minSize:b.getFont().getSize();
		}
		for(int i=0;i<fr.navBCount;i++)
			fr.navBList.get(i).setFont(new Font(fr.navBList.get(i).getFont().getName(),fr.navBList.get(i).getFont().getStyle(),minSize));

		if(!tasti)
			fr.navBList.get(fr.navBCount-1).setSize(fr.navBList.get(0).getWidth(),fr.navBList.get(0).getHeight()+fr.nav.getHeight()%fr.navBCount );
		fr.nav.getVerticalScrollBar().setUnitIncrement(fr.navBList.get(0).getHeight()/2);
		
		fr.contList.get(fr.selectedNavButton).setSize(cPane.getWidth()-fr.nav.getWidth(), cPane.getHeight()-fr.head.getHeight());
		fr.contList.get(fr.selectedNavButton).setLocation(fr.nav.getX()+fr.nav.getWidth(), fr.head.getY()+fr.head.getHeight());
		
		fr.revalidate();
		
		JScrollPane c = null;
		if(fr.selectedNavButton==0) {
			OgretimUyesiProfil pnl = (OgretimUyesiProfil)fr.contPList.get(0);
			c=fr.contList.get(0);
			Font yazilar = new Font("TimesNewRoman",Font.PLAIN,pnl.getWidth()/30);
			Color yazirenk = new Color(100,70,70);
			int mS=Integer.MAX_VALUE;
			
			pnl.tc.setLocation(pnl.getWidth()/20, pnl.getHeight()/20);
			pnl.tc.setSize(pnl.getWidth()*7/12-pnl.tc.getX(),(pnl.getHeight()-7*pnl.getHeight()/20)/7);
			pnl.tc.setForeground(yazirenk);
			
			pnl.foto.setSize(pnl.iFoto.getIconWidth(),pnl.iFoto.getIconHeight());
			pnl.foto.setLocation(pnl.tc.getX()+pnl.tc.getWidth()+pnl.getWidth()/15, pnl.getHeight()/20);
			pnl.foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
			
			pnl.unvan.setSize(pnl.tc.getSize());
			pnl.unvan.setLocation(pnl.tc.getX(), pnl.tc.getY()+pnl.tc.getHeight()+pnl.getHeight()/20);
			pnl.unvan.setForeground(yazirenk);

			pnl.adsoyad.setSize(pnl.unvan.getSize());
			pnl.adsoyad.setLocation(pnl.unvan.getX(), pnl.unvan.getY()+pnl.unvan.getHeight()+pnl.getHeight()/20);
			pnl.adsoyad.setForeground(yazirenk);
			
			pnl.bolum.setSize(pnl.adsoyad.getSize());
			pnl.bolum.setLocation(pnl.adsoyad.getX(), pnl.adsoyad.getY()+pnl.adsoyad.getHeight()+pnl.getHeight()/20);
			pnl.bolum.setForeground(yazirenk);
			
			pnl.dogumtarih.setSize(pnl.bolum.getSize());
			pnl.dogumtarih.setLocation(pnl.bolum.getX(), pnl.bolum.getY()+pnl.bolum.getHeight()+pnl.getHeight()/20);
			pnl.dogumtarih.setForeground(yazirenk);
			
			pnl.email.setSize(pnl.dogumtarih.getSize());
			pnl.email.setLocation(pnl.dogumtarih.getX(), pnl.dogumtarih.getY()+pnl.dogumtarih.getHeight()+pnl.getHeight()/20);
			pnl.email.setForeground(yazirenk);
						
			pnl.tel.setSize(pnl.email.getSize());
			pnl.tel.setLocation(pnl.email.getX(), pnl.email.getY()+pnl.email.getHeight()+pnl.getHeight()/20);
			pnl.tel.setForeground(yazirenk);

			OgrenciFormComponentAdapter.adaptFont(pnl.tc,yazilar,pnl.tc.getText());
			mS=mS>pnl.tc.getFont().getSize()?pnl.tc.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.unvan,yazilar,pnl.unvan.getText());
			mS=mS>pnl.unvan.getFont().getSize()?pnl.unvan.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.adsoyad,yazilar,pnl.adsoyad.getText());
			mS=mS>pnl.adsoyad.getFont().getSize()?pnl.adsoyad.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.bolum,yazilar,pnl.bolum.getText());
			mS=mS>pnl.bolum.getFont().getSize()?pnl.bolum.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.dogumtarih,yazilar,pnl.dogumtarih.getText());
			mS=mS>pnl.dogumtarih.getFont().getSize()?pnl.dogumtarih.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.email,yazilar,pnl.email.getText());
			mS=mS>pnl.email.getFont().getSize()?pnl.email.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.tel,yazilar,pnl.tel.getText());
			mS=mS>pnl.tel.getFont().getSize()?pnl.tel.getFont().getSize():mS;

			
			pnl.tc.setFont(new Font(pnl.tc.getFont().getName(),pnl.tc.getFont().getStyle() , mS));
			pnl.unvan.setFont(new Font(pnl.unvan.getFont().getName(),pnl.unvan.getFont().getStyle() , mS));
			pnl.adsoyad.setFont(new Font(pnl.adsoyad.getFont().getName(),pnl.adsoyad.getFont().getStyle() , mS));
			pnl.bolum.setFont(new Font(pnl.bolum.getFont().getName(),pnl.bolum.getFont().getStyle() , mS));
			pnl.dogumtarih.setFont(new Font(pnl.dogumtarih.getFont().getName(),pnl.dogumtarih.getFont().getStyle() , mS));
			pnl.email.setFont(new Font(pnl.email.getFont().getName(),pnl.email.getFont().getStyle() , mS));
			pnl.tel.setFont(new Font(pnl.tel.getFont().getName(),pnl.tel.getFont().getStyle() , mS));
			
		}else if(fr.selectedNavButton==1) {
			OgretimUyesiDevamsizlik pnl = (OgretimUyesiDevamsizlik)fr.contPList.get(1);
			c=fr.contList.get(1);
			
			pnl.takvim.setSize(pnl.getWidth(),pnl.getHeight()*3/5);
			pnl.takvim.setLocation(0, pnl.getHeight()*2/5);
			
			pnl.hdr.setSize(pnl.getWidth(), pnl.takvim.getHeight()/12);
			pnl.hdr.setLocation(0, pnl.takvim.getY()-pnl.hdr.getHeight());
			
			pnl.dersler.setSize(pnl.getWidth()/3,pnl.getHeight()/12);
			pnl.dersler.setLocation(pnl.getWidth()/8,pnl.getHeight()/30 );
			
			pnl.ogrenciler.setSize(pnl.getWidth()/3,pnl.getHeight()/12);
			pnl.ogrenciler.setLocation(pnl.getWidth()-pnl.dersler.getWidth()-pnl.getWidth()/8,pnl.getHeight()/30 );
			
			pnl.secilengun.setSize(pnl.getWidth()*5/6,pnl.getHeight()/20);
			pnl.secilengun.setLocation(0, pnl.hdr.getY()-pnl.hdr.getHeight()*5/3);
			
			pnl.kaydet.setSize(pnl.getWidth()/6, pnl.secilengun.getHeight());
			pnl.kaydet.setLocation(pnl.getWidth()*5/6,pnl.secilengun.getY());
			
			pnl.aytoplam.setSize(pnl.getWidth(),pnl.getHeight()/20);
			pnl.aytoplam.setLocation(0, pnl.secilengun.getY()-pnl.secilengun.getHeight()*4/3);

			pnl.donemtoplam.setSize(pnl.getWidth(), pnl.getHeight()/20);
			pnl.donemtoplam.setLocation(0, pnl.aytoplam.getY()-pnl.donemtoplam.getHeight()*4/3);
			
			fr.revalidate(); 
			
			for( int i =0;i<9;i++) {
				TableColumn cm = pnl.hdr.getColumnModel().getColumn(i);
				int cmwidth=0;
				for(int j=0;j<3;j++)
					cmwidth+=pnl.takvim.getColumnModel().getColumn(i*3+j).getWidth();
				cm.setWidth(cmwidth);
			}
			pnl.takvim.setRowHeight(pnl.takvim.getHeight()/pnl.takvim.getModel().getRowCount());
			pnl.takvim.setSize(pnl.takvim.getWidth(), pnl.takvim.getHeight()-(pnl.takvim.getHeight()%pnl.takvim.getModel().getRowCount()));
			if(pnl.tiklananAy!=-1)
			{
				pnl.gosterge.setSize(pnl.hdr.getColumnModel().getColumn(pnl.tiklananAy).getWidth(),pnl.hdr.getHeight()/2);
				int genislik=0;
				for(int i=0;i<pnl.tiklananAy;i++)
					genislik+=pnl.hdr.getColumnModel().getColumn(i).getWidth();
				pnl.gosterge.setLocation(genislik, pnl.hdr.getY()-pnl.hdr.getHeight()/2);
				
			}
		}
		else if(fr.selectedNavButton==2) {
			OgretimUyesiNot pnl = (OgretimUyesiNot)fr.contPList.get(2);
			c=fr.contList.get(2);
			
			pnl.dersler.setSize(pnl.getWidth()/3,pnl.getHeight()/12);
			pnl.dersler.setLocation(pnl.getWidth()/8,pnl.getHeight()/30 );
			
			pnl.ogrenciler.setSize(pnl.getWidth()/3,pnl.getHeight()/12);
			pnl.ogrenciler.setLocation(pnl.getWidth()-pnl.dersler.getWidth()-pnl.getWidth()/8,pnl.getHeight()/30 );
			
			Font yazilar= new Font("ArialBlack",Font.ITALIC|Font.PLAIN|Font.BOLD,15);
			int mS=Integer.MAX_VALUE;
			
			pnl.lVize.setSize(pnl.getWidth()/6,pnl.getHeight()/10);
			pnl.lVize.setLocation(pnl.getWidth()/3, pnl.getHeight()/4);


			pnl.lVize2.setSize(pnl.lVize.getSize());
			pnl.lVize2.setLocation(pnl.lVize.getX(), pnl.lVize.getY()+pnl.lVize.getHeight()+pnl.getHeight()/8);

			pnl.lFinal.setSize(pnl.lVize2.getSize());
			pnl.lFinal.setLocation(pnl.lVize2.getX(), pnl.lVize2.getY()+pnl.lVize2.getHeight()+pnl.getHeight()/8);
			
			pnl.vize.setSize(pnl.lVize.getSize());
			pnl.vize.setLocation(pnl.lVize.getX()+pnl.lVize.getWidth(), pnl.lVize.getY());

			pnl.vize2.setSize(pnl.vize.getSize());
			pnl.vize2.setLocation(pnl.lVize2.getX()+pnl.lVize2.getWidth(), pnl.lVize2.getY());

			pnl.finall.setSize(pnl.vize.getSize());
			pnl.finall.setLocation(pnl.lFinal.getX()+pnl.lFinal.getWidth(), pnl.lFinal.getY());

			pnl.kaydet.setSize(pnl.finall.getSize());
			pnl.kaydet.setLocation(pnl.finall.getX()-pnl.kaydet.getWidth()/2,pnl.finall.getY()+pnl.finall.getHeight()+pnl.getHeight()/20);
			OgrenciFormComponentAdapter.adaptFont(pnl.kaydet, yazilar, "Kaydet");
			
			OgrenciFormComponentAdapter.adaptFont(pnl.lVize, yazilar, pnl.lVize.getText());
			mS=mS>pnl.lVize.getFont().getSize()?pnl.lVize.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.lVize2, yazilar, pnl.lVize2.getText());
			mS=mS>pnl.lVize2.getFont().getSize()?pnl.lVize2.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.lFinal, yazilar, pnl.lFinal.getText());
			mS=mS>pnl.lFinal.getFont().getSize()?pnl.lFinal.getFont().getSize():mS;
			
			pnl.lVize.setFont(new Font(yazilar.getName(),yazilar.getStyle(),mS));
			pnl.lVize2.setFont(new Font(yazilar.getName(),yazilar.getStyle(),mS));
			pnl.lFinal.setFont(new Font(yazilar.getName(),yazilar.getStyle(),mS));
			pnl.vize.setFont(new Font(yazilar.getName(),yazilar.getStyle()&Font.ITALIC,mS));
			pnl.vize2.setFont(new Font(yazilar.getName(),yazilar.getStyle()&Font.ITALIC,mS));
			pnl.finall.setFont(new Font(yazilar.getName(),yazilar.getStyle()&Font.ITALIC,mS));
		}
		else if(fr.selectedNavButton==3) {
			OgretimUyesiDersProgrami pnl = (OgretimUyesiDersProgrami)fr.contPList.get(3);
			c=fr.contList.get(3);
			pnl.hdr.setSize(pnl.getWidth()-17, pnl.getHeight()/20);
			pnl.hdr.setLocation(0, 0);
			
			pnl.program.setSize(pnl.getWidth()-17, 11*50);
			pnl.setPreferredSize(new Dimension(0,pnl.program.getHeight()+pnl.hdr.getHeight()));
			pnl.program.setLocation(0, pnl.hdr.getHeight());
			c.getVerticalScrollBar().setUnitIncrement(pnl.program.getRowHeight()/2);

			fr.revalidate();
			
			pnl.program.setRowHeight(pnl.program.getHeight()/pnl.program.getModel().getRowCount());
		}
		
		fr.revalidate();

	}
}



class IdariMemurFormComponentAdapter extends ComponentAdapter {
	IdariMemurForm fr;
	IdariMemurFormComponentAdapter(IdariMemurForm form){
		fr=form;
	}
	public void componentResized(ComponentEvent e) {

		Container cPane =fr.getContentPane();
		JPanel navP=(JPanel)fr.nav.getViewport().getView();
		
		fr.head.setSize(cPane.getWidth(), cPane.getHeight()/8);
		fr.head.setLocation(0,0);
		
		fr.title.setSize(fr.head.getWidth()*2/3,fr.head.getHeight()*80/100);
		fr.title.setLocation(fr.head.getWidth()/20, fr.head.getHeight()*15/100);
		OgrenciFormComponentAdapter.adaptFont(fr.title,new Font("ArialBlack",Font.ITALIC,fr.title.getHeight()/2),fr.title.getText());

		fr.logout.setSize(fr.head.getWidth()/12,fr.head.getHeight()*80/100);
		fr.logout.setLocation(fr.head.getWidth()*13/15,fr.head.getHeight()*15/100 );
		OgrenciFormComponentAdapter.adaptFont(fr.logout,new Font("ArialBlack",Font.BOLD,fr.logout.getHeight()/2),fr.guncelle.getText());

		fr.guncelle.setSize(fr.head.getWidth()/12,fr.head.getHeight()*80/100);
		fr.guncelle.setLocation(fr.head.getWidth()*11/15,fr.head.getHeight()*15/100 );
		OgrenciFormComponentAdapter.adaptFont(fr.guncelle,new Font("ArialBlack",Font.BOLD,fr.guncelle.getHeight()/2),fr.guncelle.getText());

		fr.nav.setSize(cPane.getWidth()/6,cPane.getHeight()-fr.head.getSize().height);
		fr.nav.setLocation(0, fr.head.getHeight());
		
		
		boolean tasti=(fr.navBCount*50>fr.nav.getHeight()?true:false);
		if(!tasti)
			navP.setPreferredSize(new Dimension(fr.nav.getWidth()-10,fr.nav.getHeight()-10));
		else
			navP.setPreferredSize(new Dimension(fr.nav.getWidth()-fr.nav.getVerticalScrollBar().getSize().width,50*fr.navBCount));

		int minSize=Integer.MAX_VALUE;
		
		for(int i=0;i<fr.navBCount;i++) {
			JButton b= fr.navBList.get(i);
			if(!tasti)
				b.setSize(fr.nav.getWidth()-3, fr.nav.getHeight()/fr.navBCount);
			else
				b.setSize(fr.nav.getWidth()-3-fr.nav.getVerticalScrollBar().getSize().width,50);
			b.setLocation(0, i*b.getHeight());
			
			OgrenciFormComponentAdapter.adaptFont(b, new Font("ArialBlack",Font.BOLD,b.getHeight()/7), b.getText());
			minSize=b.getFont().getSize()>minSize?minSize:b.getFont().getSize();
		}
		for(int i=0;i<fr.navBCount;i++)
			fr.navBList.get(i).setFont(new Font(fr.navBList.get(i).getFont().getName(),fr.navBList.get(i).getFont().getStyle(),minSize));

		if(!tasti)
			fr.navBList.get(fr.navBCount-1).setSize(fr.navBList.get(0).getWidth(),fr.navBList.get(0).getHeight()+fr.nav.getHeight()%fr.navBCount );
		fr.nav.getVerticalScrollBar().setUnitIncrement(fr.navBList.get(0).getHeight()/2);
		
		fr.contList.get(fr.selectedNavButton).setSize(cPane.getWidth()-fr.nav.getWidth(), cPane.getHeight()-fr.head.getHeight());
		fr.contList.get(fr.selectedNavButton).setLocation(fr.nav.getX()+fr.nav.getWidth(), fr.head.getY()+fr.head.getHeight());
		
		fr.revalidate();
		
		JScrollPane c = null;
		if(fr.selectedNavButton==0) {
			IdariMemurProfil pnl = (IdariMemurProfil)fr.contPList.get(0);
			c=fr.contList.get(0);
			Font yazilar = new Font("TimesNewRoman",Font.PLAIN,pnl.getWidth()/30);
			Color yazirenk = new Color(100,70,70);
			int mS=Integer.MAX_VALUE;
			
			pnl.tc.setLocation(pnl.getWidth()/20, pnl.getHeight()/20);
			pnl.tc.setSize(pnl.getWidth()*7/12-pnl.tc.getX(),(pnl.getHeight()-7*pnl.getHeight()/20)/7);
			pnl.tc.setForeground(yazirenk);
			
			pnl.foto.setSize(pnl.iFoto.getIconWidth(),pnl.iFoto.getIconHeight());
			pnl.foto.setLocation(pnl.tc.getX()+pnl.tc.getWidth()+pnl.getWidth()/15, pnl.getHeight()/20);
			pnl.foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
			
			pnl.adsoyad.setSize(pnl.tc.getSize());
			pnl.adsoyad.setLocation(pnl.tc.getX(), pnl.tc.getY()+pnl.tc.getHeight()+pnl.getHeight()/20);
			pnl.adsoyad.setForeground(yazirenk);
			
			pnl.dogumtarih.setSize(pnl.adsoyad.getSize());
			pnl.dogumtarih.setLocation(pnl.adsoyad.getX(), pnl.adsoyad.getY()+pnl.adsoyad.getHeight()+pnl.getHeight()/20);
			pnl.dogumtarih.setForeground(yazirenk);
			
			pnl.email.setSize(pnl.dogumtarih.getSize());
			pnl.email.setLocation(pnl.dogumtarih.getX(), pnl.dogumtarih.getY()+pnl.dogumtarih.getHeight()+pnl.getHeight()/20);
			pnl.email.setForeground(yazirenk);
						
			pnl.tel.setSize(pnl.email.getSize());
			pnl.tel.setLocation(pnl.email.getX(), pnl.email.getY()+pnl.email.getHeight()+pnl.getHeight()/20);
			pnl.tel.setForeground(yazirenk);

			OgrenciFormComponentAdapter.adaptFont(pnl.tc,yazilar,pnl.tc.getText());
			mS=mS>pnl.tc.getFont().getSize()?pnl.tc.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.adsoyad,yazilar,pnl.adsoyad.getText());
			mS=mS>pnl.adsoyad.getFont().getSize()?pnl.adsoyad.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.dogumtarih,yazilar,pnl.dogumtarih.getText());
			mS=mS>pnl.dogumtarih.getFont().getSize()?pnl.dogumtarih.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.email,yazilar,pnl.email.getText());
			mS=mS>pnl.email.getFont().getSize()?pnl.email.getFont().getSize():mS;
			OgrenciFormComponentAdapter.adaptFont(pnl.tel,yazilar,pnl.tel.getText());
			mS=mS>pnl.tel.getFont().getSize()?pnl.tel.getFont().getSize():mS;

			
			pnl.tc.setFont(new Font(pnl.tc.getFont().getName(),pnl.tc.getFont().getStyle() , mS));
			pnl.adsoyad.setFont(new Font(pnl.adsoyad.getFont().getName(),pnl.adsoyad.getFont().getStyle() , mS));
			pnl.dogumtarih.setFont(new Font(pnl.dogumtarih.getFont().getName(),pnl.dogumtarih.getFont().getStyle() , mS));
			pnl.email.setFont(new Font(pnl.email.getFont().getName(),pnl.email.getFont().getStyle() , mS));
			pnl.tel.setFont(new Font(pnl.tel.getFont().getName(),pnl.tel.getFont().getStyle() , mS));
			
		}else if(fr.selectedNavButton==1) {
			IdariMemurKullaniciEkle pnl = (IdariMemurKullaniciEkle)fr.contPList.get(1);
			c=fr.contList.get(1);
			
			pnl.ogrB.setLocation(44, 25);
			pnl.ogrB.setSize(90,25);

			pnl.ouB.setLocation(pnl.ogrB.getX()+pnl.ogrB.getWidth()+22, 25);
			pnl.ouB.setSize(pnl.ogrB.getWidth()+30,pnl.ogrB.getHeight());
			
			pnl.imB.setLocation(pnl.ouB.getX()+pnl.ouB.getWidth()+22, 25);
			pnl.imB.setSize(pnl.ouB.getWidth(),pnl.ouB.getHeight());

			pnl.foto.setSize(250,200);
			pnl.foto.setLocation(pnl.imB.getX()+pnl.imB.getWidth()+150, 25);
			pnl.foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));

			pnl.tc.setLocation(44,65);
			pnl.tc.setSize(111,50);

			pnl.sifre.setLocation(pnl.tc.getX(), pnl.tc.getY()+pnl.tc.getHeight()+15);
			pnl.sifre.setSize(pnl.tc.getWidth(),pnl.tc.getHeight());

			pnl.ad.setLocation(pnl.sifre.getX(), pnl.sifre.getY()+pnl.sifre.getHeight()+15);
			pnl.ad.setSize(pnl.sifre.getWidth(),pnl.sifre.getHeight());

			pnl.soyad.setLocation(pnl.ad.getX(), pnl.ad.getY()+pnl.ad.getHeight()+15);
			pnl.soyad.setSize(pnl.ad.getWidth(),pnl.ad.getHeight());

			pnl.dogumtarih.setLocation(pnl.soyad.getX(), pnl.soyad.getY()+pnl.soyad.getHeight()+15);
			pnl.dogumtarih.setSize(pnl.soyad.getWidth(),pnl.soyad.getHeight());

			pnl.tel.setLocation(pnl.dogumtarih.getX(), pnl.dogumtarih.getY()+pnl.dogumtarih.getHeight()+15);
			pnl.tel.setSize(pnl.dogumtarih.getWidth(),pnl.dogumtarih.getHeight());

			pnl.email.setLocation(pnl.tel.getX(), pnl.tel.getY()+pnl.tel.getHeight()+15);
			pnl.email.setSize(pnl.tel.getWidth(),pnl.tel.getHeight());
			
			pnl.bolum.setLocation(pnl.email.getX(), pnl.email.getY()+pnl.email.getHeight()+15);
			pnl.bolum.setSize(pnl.email.getWidth(),pnl.email.getHeight());
			
			pnl.no.setLocation(pnl.bolum.getX(), pnl.bolum.getY()+pnl.bolum.getHeight()+15);
			pnl.no.setSize(pnl.bolum.getWidth(),pnl.bolum.getHeight());
				
			pnl.unvan.setLocation(pnl.bolum.getX(), pnl.bolum.getY()+pnl.bolum.getHeight()+15);
			pnl.unvan.setSize(pnl.bolum.getWidth(),pnl.bolum.getHeight());
			
			pnl.tfTc.setLocation(pnl.tc.getX()+pnl.tc.getWidth(), pnl.tc.getY());
			pnl.tfTc.setSize(294,50);

			pnl.tfSifre.setLocation(pnl.sifre.getX()+pnl.sifre.getWidth(), pnl.sifre.getY());
			pnl.tfSifre.setSize(pnl.tfTc.getSize());

			pnl.tfAd.setLocation(pnl.ad.getX()+pnl.ad.getWidth(), pnl.ad.getY());
			pnl.tfAd.setSize(pnl.tfTc.getSize());

			pnl.tfSoyad.setLocation(pnl.soyad.getX()+pnl.soyad.getWidth(), pnl.soyad.getY());
			pnl.tfSoyad.setSize(pnl.tfTc.getSize());

			pnl.tfDogumTarih.setLocation(pnl.dogumtarih.getX()+pnl.dogumtarih.getWidth(), pnl.dogumtarih.getY());
			pnl.tfDogumTarih.setSize(pnl.tfTc.getSize());

			pnl.tfTel.setLocation(pnl.tel.getX()+pnl.tel.getWidth(), pnl.tel.getY());
			pnl.tfTel.setSize(pnl.tfTc.getSize());

			pnl.tfEmail.setLocation(pnl.email.getX()+pnl.email.getWidth(), pnl.email.getY());
			pnl.tfEmail.setSize(pnl.tfTc.getSize());

			pnl.tfBolum.setLocation(pnl.bolum.getX()+pnl.bolum.getWidth(), pnl.bolum.getY());
			pnl.tfBolum.setSize(pnl.tfTc.getSize());

			pnl.tfNo.setLocation(pnl.no.getX()+pnl.no.getWidth(), pnl.no.getY());
			pnl.tfNo.setSize(pnl.tfTc.getSize());

			pnl.tfUnvan.setLocation(pnl.unvan.getX()+pnl.unvan.getWidth(), pnl.unvan.getY());
			pnl.tfUnvan.setSize(pnl.tfTc.getSize());
			
			pnl.fotoekle.setSize(pnl.foto.getWidth()*10/24, 20);
			pnl.fotoekle.setLocation(pnl.foto.getX(), pnl.foto.getY()+pnl.foto.getHeight()+10);
			
			pnl.fotosil.setSize(pnl.foto.getWidth()*10/24, 20);
			pnl.fotosil.setLocation(pnl.foto.getX()+pnl.foto.getWidth()-pnl.fotosil.getWidth(), pnl.foto.getY()+pnl.foto.getHeight()+10);
			
			pnl.ekle.setSize(pnl.foto.getWidth()/2, 50);
			pnl.ekle.setLocation(pnl.foto.getX()+pnl.ekle.getWidth()/2, pnl.no.getY());
			
			pnl.setPreferredSize(new Dimension(870,pnl.no.getY()+pnl.no.getHeight()+10));
			
			c.getVerticalScrollBar().setUnitIncrement(pnl.getHeight()/30);

			
		}
		else if(fr.selectedNavButton==2) {
			IdariMemurKullaniciDuzenleSil pnl = (IdariMemurKullaniciDuzenleSil)fr.contPList.get(2);
			c=fr.contList.get(2);
			

			pnl.sifre.setLocation(44,75);
			pnl.sifre.setSize(111,50);

			pnl.foto.setSize(250,200);
			pnl.foto.setLocation(pnl.sifre.getX()+pnl.sifre.getWidth()+400, pnl.sifre.getY());
			pnl.foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));

			pnl.kullanici.setSize(pnl.foto.getX()+pnl.foto.getWidth()-pnl.sifre.getX(),40);
			pnl.kullanici.setLocation(pnl.sifre.getX(), 10);


			pnl.ad.setLocation(pnl.sifre.getX(), pnl.sifre.getY()+pnl.sifre.getHeight()+15);
			pnl.ad.setSize(pnl.sifre.getWidth(),pnl.sifre.getHeight());

			pnl.soyad.setLocation(pnl.ad.getX(), pnl.ad.getY()+pnl.ad.getHeight()+15);
			pnl.soyad.setSize(pnl.ad.getWidth(),pnl.ad.getHeight());

			pnl.dogumtarih.setLocation(pnl.soyad.getX(), pnl.soyad.getY()+pnl.soyad.getHeight()+15);
			pnl.dogumtarih.setSize(pnl.soyad.getWidth(),pnl.soyad.getHeight());

			pnl.tel.setLocation(pnl.dogumtarih.getX(), pnl.dogumtarih.getY()+pnl.dogumtarih.getHeight()+15);
			pnl.tel.setSize(pnl.dogumtarih.getWidth(),pnl.dogumtarih.getHeight());

			pnl.email.setLocation(pnl.tel.getX(), pnl.tel.getY()+pnl.tel.getHeight()+15);
			pnl.email.setSize(pnl.tel.getWidth(),pnl.tel.getHeight());
			
			pnl.bolum.setLocation(pnl.email.getX(), pnl.email.getY()+pnl.email.getHeight()+15);
			pnl.bolum.setSize(pnl.email.getWidth(),pnl.email.getHeight());
			
			pnl.no.setLocation(pnl.bolum.getX(), pnl.bolum.getY()+pnl.bolum.getHeight()+15);
			pnl.no.setSize(pnl.bolum.getWidth(),pnl.bolum.getHeight());
				
			pnl.unvan.setLocation(pnl.no.getX(), pnl.no.getY());
			pnl.unvan.setSize(pnl.no.getSize());
			
			pnl.tfSifre.setLocation(pnl.sifre.getX()+pnl.sifre.getWidth(), pnl.sifre.getY());
			pnl.tfSifre.setSize(294,50);

			pnl.tfAd.setLocation(pnl.ad.getX()+pnl.ad.getWidth(), pnl.ad.getY());
			pnl.tfAd.setSize(pnl.tfSifre.getSize());

			pnl.tfSoyad.setLocation(pnl.soyad.getX()+pnl.soyad.getWidth(), pnl.soyad.getY());
			pnl.tfSoyad.setSize(pnl.tfSifre.getSize());

			pnl.tfDogumTarih.setLocation(pnl.dogumtarih.getX()+pnl.dogumtarih.getWidth(), pnl.dogumtarih.getY());
			pnl.tfDogumTarih.setSize(pnl.tfSifre.getSize());

			pnl.tfTel.setLocation(pnl.tel.getX()+pnl.tel.getWidth(), pnl.tel.getY());
			pnl.tfTel.setSize(pnl.tfSifre.getSize());

			pnl.tfEmail.setLocation(pnl.email.getX()+pnl.email.getWidth(), pnl.email.getY());
			pnl.tfEmail.setSize(pnl.tfSifre.getSize());

			pnl.tfBolum.setLocation(pnl.bolum.getX()+pnl.bolum.getWidth(), pnl.bolum.getY());
			pnl.tfBolum.setSize(pnl.tfSifre.getSize());

			pnl.tfNo.setLocation(pnl.no.getX()+pnl.no.getWidth(), pnl.no.getY());
			pnl.tfNo.setSize(pnl.tfSifre.getSize());

			pnl.tfUnvan.setLocation(pnl.tfNo.getX(), pnl.unvan.getY());
			pnl.tfUnvan.setSize(pnl.tfSifre.getSize());
			
			pnl.fotoekle.setSize(pnl.foto.getWidth()*10/24, 20);
			pnl.fotoekle.setLocation(pnl.foto.getX(), pnl.foto.getY()+pnl.foto.getHeight()+10);
			
			pnl.fotosil.setSize(pnl.foto.getWidth()*10/24, 20);
			pnl.fotosil.setLocation(pnl.foto.getX()+pnl.foto.getWidth()-pnl.fotosil.getWidth(), pnl.foto.getY()+pnl.foto.getHeight()+10);
			
			pnl.kaydet.setSize(pnl.foto.getWidth()/3, 50);
			pnl.kaydet.setLocation(pnl.foto.getX()+30, pnl.no.getY());
			
			pnl.sil.setSize(pnl.kaydet.getSize());
			pnl.sil.setLocation(pnl.foto.getX()+pnl.foto.getWidth()-pnl.sil.getWidth()-30,pnl.kaydet.getY());
			
			pnl.setPreferredSize(new Dimension(870,pnl.no.getY()+pnl.no.getHeight()+10));
			
			c.getVerticalScrollBar().setUnitIncrement(pnl.getHeight()/30);
		}
		else if(fr.selectedNavButton==3) {
			IdariMemurDersEkle pnl = (IdariMemurDersEkle)fr.contPList.get(3);
			c=fr.contList.get(3);
			
			pnl.kod.setLocation(44,25);
			pnl.kod.setSize(111,50);

			pnl.ad.setLocation(pnl.kod.getX(), pnl.kod.getY()+pnl.kod.getHeight()+15);
			pnl.ad.setSize(pnl.kod.getWidth(),pnl.kod.getHeight());

			pnl.bolum.setLocation(pnl.ad.getX(), pnl.ad.getY()+pnl.ad.getHeight()+15);
			pnl.bolum.setSize(pnl.ad.getWidth(),pnl.ad.getHeight());

			pnl.derslik.setLocation(pnl.bolum.getX(), pnl.bolum.getY()+pnl.bolum.getHeight()+15);
			pnl.derslik.setSize(pnl.bolum.getWidth(),pnl.bolum.getHeight());

			pnl.tfKod.setLocation(pnl.kod.getX()+pnl.kod.getWidth(), pnl.kod.getY());
			pnl.tfKod.setSize(294,50);

			pnl.tfAd.setLocation(pnl.ad.getX()+pnl.ad.getWidth(), pnl.ad.getY());
			pnl.tfAd.setSize(pnl.tfKod.getSize());

			pnl.tfBolum.setLocation(pnl.bolum.getX()+pnl.bolum.getWidth(), pnl.bolum.getY());
			pnl.tfBolum.setSize(pnl.tfAd.getSize());

			pnl.tfDerslik.setLocation(pnl.derslik.getX()+pnl.derslik.getWidth(), pnl.derslik.getY());
			pnl.tfDerslik.setSize(pnl.tfBolum.getSize());
			
			pnl.ekle.setLocation((pnl.derslik.getX()+pnl.derslik.getWidth()+400), pnl.derslik.getY());
			pnl.ekle.setSize(250, 50);
			
			pnl.setPreferredSize(new Dimension(870,pnl.derslik.getY()+pnl.derslik.getHeight()+10));
			
			c.getVerticalScrollBar().setUnitIncrement(pnl.getHeight()/30);
		}
		else if(fr.selectedNavButton==4) {
			IdariMemurDersDuzenleSil pnl = (IdariMemurDersDuzenleSil)fr.contPList.get(4);
			c=fr.contList.get(4);
			

			pnl.ad.setLocation(44,75);
			pnl.ad.setSize(111,50);

			pnl.ders.setSize((pnl.ad.getX()+pnl.ad.getWidth()+400)+250-pnl.ad.getX(),40);
			pnl.ders.setLocation(pnl.ad.getX(), 10);


			pnl.bolum.setLocation(pnl.ad.getX(), pnl.ad.getY()+pnl.ad.getHeight()+15);
			pnl.bolum.setSize(pnl.ad.getWidth(),pnl.ad.getHeight());

			pnl.derslik.setLocation(pnl.bolum.getX(), pnl.bolum.getY()+pnl.bolum.getHeight()+15);
			pnl.derslik.setSize(pnl.bolum.getWidth(),pnl.bolum.getHeight());
			
			pnl.tfAd.setLocation(pnl.ad.getX()+pnl.ad.getWidth(), pnl.ad.getY());
			pnl.tfAd.setSize(294,50);

			pnl.tfBolum.setLocation(pnl.bolum.getX()+pnl.bolum.getWidth(), pnl.bolum.getY());
			pnl.tfBolum.setSize(pnl.tfAd.getSize());

			pnl.tfDerslik.setLocation(pnl.derslik.getX()+pnl.derslik.getWidth(), pnl.derslik.getY());
			pnl.tfDerslik.setSize(pnl.tfBolum.getSize());

			
			pnl.kaydet.setSize(250/3, 50);
			pnl.kaydet.setLocation((pnl.derslik.getX()+pnl.derslik.getWidth()+400)+30, pnl.derslik.getY());
			
			pnl.sil.setSize(pnl.kaydet.getSize());
			pnl.sil.setLocation((pnl.derslik.getX()+pnl.derslik.getWidth()+400)+250-pnl.sil.getWidth()-30,pnl.kaydet.getY());
			
			pnl.setPreferredSize(new Dimension(870,pnl.derslik.getY()+pnl.derslik.getHeight()+10));
			
			c.getVerticalScrollBar().setUnitIncrement(pnl.getHeight()/30);
		}else if(fr.selectedNavButton==5) {
			IdariMemurVerilenDersEkleSil pnl = (IdariMemurVerilenDersEkleSil)fr.contPList.get(5);
			c=fr.contList.get(5);
			
			pnl.ders.setSize(700,40);
			pnl.ders.setLocation(111, 10);
			
			pnl.ogretimuyesi.setSize(pnl.ders.getSize());
			pnl.ogretimuyesi.setLocation(pnl.ders.getX(), pnl.ders.getY()+pnl.ders.getHeight()+20);
			
			pnl.ekle.setSize(pnl.ogretimuyesi.getWidth()/4, 30);
			pnl.ekle.setLocation(pnl.ogretimuyesi.getX()+pnl.ogretimuyesi.getWidth()/2-pnl.ekle.getWidth()-10, pnl.ogretimuyesi.getY()+pnl.ogretimuyesi.getHeight()+20);

			pnl.sil.setSize(pnl.ekle.getSize());
			pnl.sil.setLocation(pnl.ekle.getX()+pnl.ekle.getWidth()+10,pnl.ekle.getY());
		}
		else if(fr.selectedNavButton==6) {
			IdariMemurAlinanDersEkleSil pnl = (IdariMemurAlinanDersEkleSil)fr.contPList.get(6);
			c=fr.contList.get(6);
			
			pnl.ders.setSize(700,40);
			pnl.ders.setLocation(111, 10);
			
			pnl.ogrenci.setSize(pnl.ders.getSize());
			pnl.ogrenci.setLocation(pnl.ders.getX(), pnl.ders.getY()+pnl.ders.getHeight()+20);
			
			pnl.ekle.setSize(pnl.ogrenci.getWidth()/4, 30);
			pnl.ekle.setLocation(pnl.ogrenci.getX()+pnl.ogrenci.getWidth()/2-pnl.ekle.getWidth()-10, pnl.ogrenci.getY()+pnl.ogrenci.getHeight()+20);

			pnl.sil.setSize(pnl.ekle.getSize());
			pnl.sil.setLocation(pnl.ekle.getX()+pnl.ekle.getWidth()+10,pnl.ekle.getY());
		}
		else if(fr.selectedNavButton==7) {
			IdariMemurProgramEkleSil pnl = (IdariMemurProgramEkleSil)fr.contPList.get(7);
			c=fr.contList.get(7);
			
			pnl.ders.setSize(pnl.getWidth()*7/10,pnl.getHeight()/10);
			pnl.ders.setLocation(pnl.getWidth()/8, pnl.getHeight()/50);
			
			pnl.program.setSize(pnl.getWidth(), 11*30);
			pnl.program.setLocation(0, pnl.getHeight()-pnl.program.getHeight());
			c.getVerticalScrollBar().setUnitIncrement(pnl.program.getRowHeight()/2);
			
			pnl.hdr.setSize(pnl.program.getWidth(),pnl.program.getHeight()/10);
			pnl.hdr.setLocation(0, pnl.program.getY()-pnl.hdr.getHeight());
			
			pnl.kaydet.setSize(pnl.getWidth()*63/256,pnl.getHeight()/20);
			pnl.kaydet.setLocation(pnl.program.getWidth()-pnl.kaydet.getWidth()-1, pnl.program.getY()-pnl.kaydet.getHeight()-40);
			fr.revalidate();
			
			pnl.program.setRowHeight(pnl.program.getHeight()/pnl.program.getModel().getRowCount());
			
		}
		
		fr.revalidate();
		fr.repaint();

	}
}



class NavButtonMouseAdapter extends MouseAdapter{
	KullaniciForm fr;
	static Color but=new Color(240,240,240),butClicked=new Color(225,225,225);
	static Border bBorder= BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.lightGray,Color.lightGray);
	static Border bBorderClicked=BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.GRAY,Color.GRAY);
	public NavButtonMouseAdapter(KullaniciForm frame) {
		fr=frame;
	}
	public void mousePressed(MouseEvent e) {
		JButton b= (JButton)e.getSource();
		if(fr.navBList.get(fr.selectedNavButton)!=e.getSource()||fr.selectedNavButton==0) {
			fr.navBList.get(fr.selectedNavButton).setBackground(but);
			fr.navBList.get(fr.selectedNavButton).setBorder(bBorder);
			fr.contList.get(fr.selectedNavButton).setVisible(false);
			
			b.setBorder(bBorderClicked);
			b.setBackground(butClicked);
			fr.selectedNavButton=fr.navBList.indexOf(b);
			fr.contList.get(fr.selectedNavButton).setVisible(true);
			fr.getComponentListeners()[0].componentResized(new ComponentEvent(fr,0));
		}
	}
}



class DerslerItemListener implements ItemListener{
	OgrenciDevamsizlik pnl;
	public DerslerItemListener(OgrenciDevamsizlik p) {
		pnl=p;
	}
	public void itemStateChanged(ItemEvent e) {
		if(ItemEvent.SELECTED==e.getStateChange()) {
			for(int i=0;i<pnl.takvim.getRowCount();i++) {
				for(int j=0;j<pnl.takvim.getColumnCount();j++) {
					pnl.takvim.setValueAt("", i, j);
				}
			}
			if(pnl.dersler.getSelectedIndex()!=0) {
				Ders d = pnl.dersList.get(pnl.dersler.getSelectedIndex()-1);
				try {
					pnl.tarihList=FileHandler.devamsizlikTarihleriniGetir(pnl.ogr.tcKimlik, d.kod);
				} catch (IOException e1) {JOptionPane.showConfirmDialog(pnl,"Verilere Ulaşırken Bir Sorun Yaşandı. Lütfen Daha Sonra Tekrar Giriş Yapınız.","Hata!",JOptionPane.OK_OPTION);return;}
				
				pnl.donemtoplam.setText("Bu Dönemde Toplamda "+pnl.tarihList.size()+" Adet Devamsızlığınız Bulunmaktadir.");
				pnl.aytoplam.setText("Herhangi Bir Ay'a Tıklayınız.");
				pnl.gosterge.setSize(0,0);
				
				for(int i =0;i<pnl.tarihList.size();i++) {
					int gun =Integer.valueOf(pnl.tarihList.get(i).substring(0,2)); 
					int r=gun/3-(gun%3==0?1:0);
					int c=(gun%3==0)?2:(gun%3==2)?1:0;
					String ay=pnl.tarihList.get(i).substring(2);
					if(ay.equals("EYLÜL")) c+=0;
					else if(ay.equals("EKİM")) c+=3;
					else if(ay.equals("KASIM")) c+=6;
					else if(ay.equals("ARALIK")) c+=9;
					else if(ay.equals("OCAK")) c+=12;
					else if(ay.equals("ŞUBAT")) c+=15;
					else if(ay.equals("MART")) c+=18;
					else if(ay.equals("NİSAN")) c+=21;
					else if(ay.equals("MAYIS")) c+=24;
					pnl.takvim.setValueAt("[X]", r, c);
				}
			}
			else {
				pnl.donemtoplam.setText("Herhangi Bir Ders Seçiniz.");
				pnl.aytoplam.setText("Herhangi Bir Ders Seçip Herhangi Bir Ay'a Tıklayınız.");
				pnl.gosterge.setSize(0,0);
			}
		}
	}
}



class OuDerslerItemListener implements ItemListener{
	OgretimUyesiDevamsizlik pnl;
	public OuDerslerItemListener(OgretimUyesiDevamsizlik p) {
		pnl=p;
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED ) {
			for(int i=0;i<pnl.takvim.getRowCount();i++) {
				for(int j=0;j<pnl.takvim.getColumnCount();j++) {
					pnl.takvim.setValueAt("", i, j);
				}
			}
			pnl.ogrenciler.removeAllItems();
			pnl.ogrenciler.addItem("--- Devamsızlığı Görüntülenecek Ögrenciyi Seçiniz ---");
			if(pnl.dersler.getSelectedIndex()!=0){
					pnl.donemtoplam.setText("Herhangi Bir Öğrenci Seçiniz.");
					pnl.aytoplam.setText("Herhangi Bir Öğrenci Seçip Herhangi Bir Ay'a Tıklayınız.");
					try {
						pnl.ogrenciList=FileHandler.dersiAlanlariGetir(pnl.dersList.get(pnl.dersler.getSelectedIndex()-1).kod);
					}catch(IOException e2) {
						pnl.ogrenciList.add(new Ogrenci("Verilere Ulasilamiyor -"," - Daha Sonra Tekrar Giriş Yapınız","",0,"","","",""));
					}
					for(int i=0;i<pnl.ogrenciList.size();i++) {
						Ogrenci d= pnl.ogrenciList.get(i);
						String yazi=d.no+" | "+d.ad+" "+d.soyad;
						pnl.ogrenciler.insertItemAt(yazi, i+1);
					}
					pnl.ogrenciler.setEnabled(true);
					
			}
			else {
				pnl.ogrenciList=new ArrayList<Ogrenci>();
				pnl.ogrenciler.setEnabled(false);
				pnl.donemtoplam.setText("Herhangi Bir Ders ve Herhangi Bir Öğrenci Seçiniz.");
				pnl.aytoplam.setText("Herhangi Bir Ders ve Öğrenci Seçip Herhangi Bir Ay'a Tıklayınız.");
			}
			pnl.secilengun.setText("Herhangi Bir Gün Seçin(Öğrenci Seçilmiş Olmalıdır)");
			pnl.gosterge.setSize(0,0);
			pnl.degisenSet=new HashSet<String>();
			pnl.kaydet.setEnabled(false);
			pnl.tiklananSatir=-1;
			pnl.tiklananSutun=-1;
		}
	}
}



class OuOgrencilerItemListener implements ItemListener{
	OgretimUyesiDevamsizlik pnl;
	public OuOgrencilerItemListener(OgretimUyesiDevamsizlik p) {
		pnl=p;
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED && pnl.ogrenciler.getItemCount()>1) {
			for(int i=0;i<pnl.takvim.getRowCount();i++) {
				for(int j=0;j<pnl.takvim.getColumnCount();j++) {
					pnl.takvim.setValueAt("", i, j);
				}
			}
			if(pnl.ogrenciler.getSelectedIndex()!=0){
					pnl.aytoplam.setText("Herhangi Bir Ay'a Tıklayınız.");
					try {
						pnl.tarihList=FileHandler.devamsizlikTarihleriniGetir(pnl.ogrenciList.get(pnl.ogrenciler.getSelectedIndex()-1).tcKimlik,pnl.dersList.get(pnl.dersler.getSelectedIndex()-1).kod);
					}catch(IOException e2) {
						JOptionPane.showConfirmDialog(pnl,"Verilere Ulaşırken Bir Sorun Yaşandı. Lütfen Daha Sonra Tekrar Giriş Yapınız.","Hata!",JOptionPane.OK_OPTION);return;	}
					for(int i =0;i<pnl.tarihList.size();i++) {
						int gun=Integer.valueOf(pnl.tarihList.get(i).substring(0,2));
						int r=gun/3-(gun%3==0?1:0);
						int c=(gun%3==0)?2:(gun%3==2)?1:0;
						String ay=pnl.tarihList.get(i).substring(2);
						if(ay.equals("EYLÜL")) c+=0;
						else if(ay.equals("EKİM")) c+=3;
						else if(ay.equals("KASIM")) c+=6;
						else if(ay.equals("ARALIK")) c+=9;
						else if(ay.equals("OCAK")) c+=12;
						else if(ay.equals("ŞUBAT")) c+=15;
						else if(ay.equals("MART")) c+=18;
						else if(ay.equals("NİSAN")) c+=21;
						else if(ay.equals("MAYIS")) c+=24;
						pnl.takvim.setValueAt("[X]", r, c);
					}
					pnl.donemtoplam.setText("Öğrencinin Bu Dönemde Toplamda "+pnl.tarihList.size()+" Adet Devamsızlığı Bulunmaktadır.");
					
			}
			else {
				pnl.tarihList = new ArrayList<String>();
				pnl.donemtoplam.setText("Herhangi Bir Öğrenci Seçiniz.");
				pnl.aytoplam.setText("Herhangi Bir Öğrenci Seçip Herhangi Bir Ay'a Tıklayınız.");
			}
			pnl.secilengun.setText("Herhangi Bir Gün Seçin(Öğrenci Seçilmiş Olmalıdır)");
			pnl.gosterge.setSize(0,0);
			pnl.kaydet.setEnabled(false);
			pnl.degisenSet=new HashSet<String>();
			pnl.tiklananSatir=-1;
			pnl.tiklananSutun=-1;
		}
	}
}



class OuTakvimMouseListener extends MouseAdapter{
	OgretimUyesiDevamsizlik pnl;
	public OuTakvimMouseListener(OgretimUyesiDevamsizlik p) {
		pnl=p;
	}
	
	public void mousePressed(MouseEvent e) {
		
		pnl.tiklananAy= pnl.hdr.getColumnModel().getColumnIndexAtX(e.getX());
		String Ay=null;
		int adet=0;
		if(pnl.tiklananAy==0)
			Ay="EYLÜL";
		else if(pnl.tiklananAy==1)
			Ay="EKİM";
		else if(pnl.tiklananAy==2)
			Ay="KASIM";
		else if(pnl.tiklananAy==3)
			Ay="ARALIK";
		else if(pnl.tiklananAy==4)
			Ay="OCAK";
		else if(pnl.tiklananAy==5)
			Ay="ŞUBAT";
		else if(pnl.tiklananAy==6)
			Ay="MART";
		else if(pnl.tiklananAy==7)
			Ay="NİSAN";
		else if(pnl.tiklananAy==8)
			Ay="MAYIS";
		for(int i=0;i<pnl.tarihList.size();i++)
			if(Ay.equals(pnl.tarihList.get(i).substring(2)))
				adet++;
		if(pnl.dersler.getSelectedIndex()!=0 && pnl.ogrenciler.getSelectedIndex()!=0 ) {
			pnl.aytoplam.setText("Öğrencinin "+Ay+" Ayında Toplamda "+adet+" Adet Devamsızlığınız Bulunmaktadır.");
			pnl.gosterge.setSize(pnl.hdr.getColumnModel().getColumn(pnl.tiklananAy).getWidth(),pnl.hdr.getHeight()/2);
			
			int genislik=0;
			for(int i=0;i<pnl.tiklananAy;i++)
				genislik+=pnl.hdr.getColumnModel().getColumn(i).getWidth();
			
			pnl.gosterge.setLocation(genislik, pnl.hdr.getY()-pnl.hdr.getHeight()/2);
			
			pnl.tiklananSatir=pnl.takvim.rowAtPoint(e.getPoint());
			pnl.tiklananSutun=pnl.takvim.columnAtPoint(e.getPoint());
			if(!(pnl.tiklananSatir==10 &&pnl.tiklananSutun%3!=0)&& e.getSource().getClass()!=pnl.hdr.getClass()) {
				pnl.secilengun.setText(Integer.toString(1+(pnl.tiklananSatir*3+pnl.tiklananSutun%3))+" "+Ay);
				
				if(e.getButton()==MouseEvent.BUTTON3&&pnl.takvim.getValueAt(pnl.tiklananSatir, pnl.tiklananSutun).equals("")) {
					pnl.takvim.setValueAt("[X]", pnl.tiklananSatir, pnl.tiklananSutun);
					if(pnl.degisenSet.contains(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun)))
						pnl.degisenSet.remove(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
					else
						pnl.degisenSet.add(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
				}
				else if(e.getButton()==MouseEvent.BUTTON3) {
					pnl.takvim.setValueAt("", pnl.tiklananSatir, pnl.tiklananSutun);
					if(pnl.degisenSet.contains(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun)))
						pnl.degisenSet.remove(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
					else
						pnl.degisenSet.add(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
				}
				if(e.getButton()==MouseEvent.BUTTON3 && pnl.degisenSet.size()!=0)
					pnl.kaydet.setEnabled(true);
				else if(pnl.degisenSet.size()==0)
					pnl.kaydet.setEnabled(false);
				
					
			}
		}
		if(!(pnl.tiklananSatir==10 &&pnl.tiklananSutun%3!=0)&&(e.getSource()).getClass()==JTable.class && e.getButton()==MouseEvent.BUTTON3) {
			pnl.takvim.repaint();
		}
	}
}



class ImTakvimMouseListener extends MouseAdapter{
	IdariMemurProgramEkleSil pnl;
	public ImTakvimMouseListener(IdariMemurProgramEkleSil p) {
		pnl=p;
	}
	
	public void mousePressed(MouseEvent e) {
		if(pnl.seciliDers!=null) {
			pnl.tiklananSutun= pnl.hdr.getColumnModel().getColumnIndexAtX(e.getX());
			pnl.tiklananSatir=pnl.program.rowAtPoint(e.getPoint());
			pnl.gosterge.setSize(pnl.hdr.getColumnModel().getColumn(pnl.tiklananSutun).getWidth(),pnl.hdr.getHeight()/2);
			
			int genislik=0;
			for(int i=0;i<pnl.tiklananSutun;i++)
				genislik+=pnl.hdr.getColumnModel().getColumn(i).getWidth();
			
			pnl.gosterge.setLocation(genislik, pnl.hdr.getY()-pnl.hdr.getHeight()/2);
			
			if(pnl.tiklananSutun!=0 && e.getSource().getClass()!=pnl.hdr.getClass() ) {
				String val=(String)pnl.program.getValueAt(pnl.tiklananSatir, pnl.tiklananSutun);
				if(e.getButton()==MouseEvent.BUTTON3&&(val==null ||val.equals(""))) {
					pnl.program.setValueAt(pnl.seciliDers.ad, pnl.tiklananSatir, pnl.tiklananSutun);
					if(pnl.degisenSet.contains(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun)))
						pnl.degisenSet.remove(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
					else
						pnl.degisenSet.add(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
				}
				else if(e.getButton()==MouseEvent.BUTTON3) {
					pnl.program.setValueAt("", pnl.tiklananSatir, pnl.tiklananSutun);
					if(pnl.degisenSet.contains(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun)))
						pnl.degisenSet.remove(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
					else
						pnl.degisenSet.add(Integer.toString(pnl.tiklananSatir)+"-"+Integer.toString(pnl.tiklananSutun));
				}
				if(e.getButton()==MouseEvent.BUTTON3 && pnl.degisenSet.size()!=0)
					pnl.kaydet.setEnabled(true);
				else if(pnl.degisenSet.size()==0)
					pnl.kaydet.setEnabled(false);			
			}
		
			if(pnl.tiklananSutun!=0&&(e.getSource()).getClass()==JTable.class && e.getButton()==MouseEvent.BUTTON3) {
				pnl.program.repaint();
			}
		}
	}
}



class OuNotDerslerItemListener implements ItemListener{
	OgretimUyesiNot pnl;
	
	public OuNotDerslerItemListener(OgretimUyesiNot p) {
		pnl=p;
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED && pnl.dersler.getItemCount()>1) {
			pnl.ogrenciler.removeAllItems();
			pnl.ogrenciler.addItem("--- Notu Görüntülenecek Ögrenciyi Seçiniz ---");
			if(pnl.dersler.getSelectedIndex()!=0){
					
					pnl.ogrenciler.setEnabled(true);						
					pnl.lVize.setEnabled(false);
					pnl.lVize2.setEnabled(false);
					pnl.lFinal.setEnabled(false);
					pnl.vize.setEnabled(false);
					pnl.vize2.setEnabled(false);
					pnl.finall.setEnabled(false);
					pnl.kaydet.setEnabled(false);
					try {
						pnl.ogrenciList=FileHandler.dersiAlanlariGetir(pnl.vDersList.get(pnl.dersler.getSelectedIndex()-1).kod);
					}catch(IOException e2) {
						JOptionPane.showConfirmDialog(pnl,"Verilere Ulaşırken Bir Sorun Yaşandı. Lütfen Daha Sonra Tekrar Deneyin.","Hata!",JOptionPane.OK_OPTION);return;
					}
					for(int i=0;i<pnl.ogrenciList.size();i++)
						pnl.ogrenciler.addItem(pnl.ogrenciList.get(i).tcKimlik+" | "+pnl.ogrenciList.get(i).ad+" "+pnl.ogrenciList.get(i).soyad);
			}else {
				pnl.ogrenciler.setEnabled(false);
				pnl.lVize.setEnabled(false);
				pnl.lVize2.setEnabled(false);
				pnl.lFinal.setEnabled(false);
				pnl.vize.setEnabled(false);
				pnl.vize2.setEnabled(false);
				pnl.finall.setEnabled(false);
				pnl.kaydet.setEnabled(false);
			}
		}
	}
}



class OuNotOgrencilerItemListener implements ItemListener{
	OgretimUyesiNot pnl;
	String sv="",sv2="",sf="";
	public OuNotOgrencilerItemListener(OgretimUyesiNot p) {
		pnl=p;
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED && pnl.ogrenciler.getItemCount() >1) {

			if(pnl.ogrenciler.getSelectedIndex()!=0){
				try {
					pnl.not=FileHandler.notGetir(
							pnl.ogrenciList.get(pnl.ogrenciler.getSelectedIndex()-1).tcKimlik,
							pnl.vDersList.get(pnl.dersler.getSelectedIndex()-1).kod);
				}catch(IOException e2) {
					JOptionPane.showConfirmDialog(pnl,"Verilere Ulaşırken Bir Sorun Yaşandı. Lütfen Daha Sonra Tekrar Deneyin.","Hata!",JOptionPane.OK_OPTION);return;
				}catch(KayitBulunamadi e3) {
					pnl.not="__";
				}
				
				String[] splitted=pnl.not.split("_");
				if(splitted.length>0) {
					pnl.vize.setText(splitted[0]);
					sv=splitted[0];
				}
				else
					pnl.vize.setText("");
				if(splitted.length>1) {
					pnl.vize2.setText(splitted[1]);
					sv2=splitted[1];
				}
				else
					pnl.vize2.setText("");
				
				if(splitted.length>2) {
					pnl.finall.setText(splitted[2]);
					sf=splitted[2];
				}
				else
					pnl.finall.setText("");
				pnl.dersler.setEnabled(true);
				pnl.lVize.setEnabled(true);
				pnl.lVize2.setEnabled(true);
				pnl.lFinal.setEnabled(true);
				pnl.vize.setEnabled(true);
				pnl.vize2.setEnabled(true);
				pnl.finall.setEnabled(true);
				pnl.kaydet.setEnabled(true);
			}
		}
	}
}
class OuNotKaydetActionListener implements ActionListener{
	OgretimUyesiNot pnl;
	
	public OuNotKaydetActionListener(OgretimUyesiNot p) {
		pnl=p;
	}
	public void actionPerformed(ActionEvent e) {
		String v1=pnl.vize.getText(),v2=pnl.vize2.getText(),f=pnl.finall.getText();
		if(!notMu(v1)) {
			JOptionPane.showMessageDialog(pnl, "Vize 1 Uygunsuz Girdi. Girdi 0-100 arasında bir sayı olmalıdır.","HATA!",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!notMu(v2))
		{
			JOptionPane.showMessageDialog(pnl, "Vize 2 Uygunsuz Girdi. Girdi 0-100 arasında bir sayı olmalıdır.","HATA!",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!notMu(f))
		{
			JOptionPane.showMessageDialog(pnl, "Final Uygunsuz Girdi. Girdi 0-100 arasında bir sayı olmalıdır.","HATA!",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		v1=notDuzenle(v1);
		v2=notDuzenle(v2);
		f=notDuzenle(f);
		try{
			FileHandler.notKaydet(pnl.ogrenciList.get(pnl.ogrenciler.getSelectedIndex()-1).tcKimlik, pnl.vDersList.get(pnl.dersler.getSelectedIndex()-1).kod, v1+"_"+v2+"_"+f);
		}catch(IOException e2) {
			JOptionPane.showConfirmDialog(pnl,"Verilere Ulaşırken Bir Sorun Yaşandı. Lütfen Daha Sonra Tekrar Deneyin.","Hata!",JOptionPane.OK_OPTION);
		}
		JOptionPane.showMessageDialog(pnl, "Not Başarıyla Kaydedildi.","Kaydet",JOptionPane.INFORMATION_MESSAGE);
		pnl.vize.setText(v1);
		pnl.vize2.setText(v2);
		pnl.finall.setText(f);
	}
	public boolean notMu(String s) {
		int noktaIndex=-1;
		for(int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if(!Character.isDigit(c)&&c!='.')
				return false;
			else if(c=='.'){
				if(noktaIndex!=-1|| i==0 || i==s.length()-1)
					return false;
				noktaIndex=i;
			}
		}
		int val=Integer.valueOf(s.substring(0,noktaIndex==-1?s.length():noktaIndex));
		if((noktaIndex!=-1&&val>=100) || (noktaIndex==-1&&val>100))
			return false;
		return true;
	}
	public String notDuzenle(String s) {
		int i=0;
		while(s.charAt(i)=='0'&&i+1<s.length()&&s.charAt(i+1)!='.')
			i++;
		int j=i;
		while(j<s.length()&&s.charAt(j)!='.')
			j++;
		if(s.length()-j>2)
			return s.substring(i,j+3);
		return s.substring(i);
		
	}
}

class OuKaydetActionListener implements ActionListener{
	OgretimUyesiDevamsizlik pnl;
	
	public OuKaydetActionListener(OgretimUyesiDevamsizlik p) {
		pnl=p;
	}
	public void actionPerformed(ActionEvent e) {
		Iterator<String> it = pnl.degisenSet.iterator();
		while(it.hasNext()) {
			String[] val=it.next().split("-");
			int satir=Integer.valueOf(val[0]),sutun=Integer.valueOf(val[1]);
			int tiklananAy=sutun/3;
			String Gun = Integer.toString(satir*3+1+(sutun%3));
			if(Gun.length()==1)
				Gun="0"+Gun;
			String Ay=null;
			boolean eklenecek=((String)pnl.takvim.getValueAt(satir, sutun)).equals("")?false:true;
			if(tiklananAy==0)
				Ay="EYLÜL";
			else if(tiklananAy==1)
				Ay="EKİM";
			else if(tiklananAy==2)
				Ay="KASIM";
			else if(tiklananAy==3)
				Ay="ARALIK";
			else if(tiklananAy==4)
				Ay="OCAK";
			else if(tiklananAy==5)
				Ay="ŞUBAT";
			else if(tiklananAy==6)
				Ay="MART";
			else if(tiklananAy==7)
				Ay="NİSAN";
			else if(tiklananAy==8)
				Ay="MAYIS";
			try {
				if(eklenecek) {
					FileHandler.devamsizlikKaydet(pnl.ogrenciList.get(pnl.ogrenciler.getSelectedIndex()-1).tcKimlik, pnl.dersList.get(pnl.dersler.getSelectedIndex()-1).kod, Gun+Ay);
					pnl.tarihList.add(Gun+Ay);
				}
				else {
					FileHandler.devamsizlikSil(pnl.ogrenciList.get(pnl.ogrenciler.getSelectedIndex()-1).tcKimlik, pnl.dersList.get(pnl.dersler.getSelectedIndex()-1).kod, Gun+Ay);
					pnl.tarihList.remove(Gun+Ay);
				}
			}catch (IOException e2) {
				JOptionPane.showMessageDialog(pnl.getParent().getParent(), "Veritabanı Hatası! Sistemden Kaynaklı Bir Hata Oluştu Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
			}catch (Exception e3) {}
		}
		pnl.degisenSet=new HashSet<String>();
		pnl.kaydet.setEnabled(false);
		pnl.takvim.repaint();
	}
}

class ImKaydetActionListener implements ActionListener{
	IdariMemurProgramEkleSil pnl;
	
	public ImKaydetActionListener(IdariMemurProgramEkleSil p) {
		pnl=p;
	}
	public void actionPerformed(ActionEvent e) {
		Iterator<String> it = pnl.degisenSet.iterator();
		while(it.hasNext()) {
			String[] val=it.next().split("-");
			int satir=Integer.valueOf(val[0]),sutun=Integer.valueOf(val[1]);
			String saatgun=((String)pnl.program.getModel().getValueAt(satir, 0)).substring(0,5)+pnl.hdr.getColumnModel().getColumn(sutun).getHeaderValue();
			boolean eklenecek=((String)pnl.program.getValueAt(satir, sutun)).equals("")?false:true;
			try {
				if(eklenecek) {
					FileHandler.dersPrograminaKaydet(pnl.seciliDers.kod, saatgun);
					pnl.vakitList.add(saatgun);
				}
				else {
					FileHandler.dersProgramindanSil(pnl.seciliDers.kod, saatgun);
					pnl.vakitList.remove(saatgun);
				}
			}catch (IOException e2) {
				JOptionPane.showMessageDialog(pnl.getParent().getParent(), "Veritabanı Hatası! Sistemden Kaynaklı Bir Hata Oluştu Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
			} catch (KayitVar e1) {
				JOptionPane.showMessageDialog(pnl.getParent().getParent(), e1.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);	
			} catch (KayitBulunamadi e3) {
				JOptionPane.showMessageDialog(pnl.getParent().getParent(), e3.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);
			}
		}
		pnl.degisenSet=new HashSet<String>();
		pnl.kaydet.setEnabled(false);
		pnl.program.repaint();
	}
}