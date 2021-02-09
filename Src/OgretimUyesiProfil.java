import java.awt.Color;
import java.io.File;

import javax.swing.*;
public class OgretimUyesiProfil extends JPanel{
	private static final long serialVersionUID = -500027701947579662L;
	JLabel foto,tc,unvan,adsoyad,bolum,dogumtarih,tel,email;
	OgretimUyesi ou;
	ImageIcon iFoto;

	OgretimUyesiProfil(OgretimUyesi ou){
		super();
		this.ou=ou;
		setLayout(null);
		
		iFoto = new ImageIcon(System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\"+ou.tcKimlik+".png");
		{
			File fFoto = new File(iFoto.toString());
			if(!fFoto.exists())
				iFoto = new ImageIcon(System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\default.png");
		}
		foto = new JLabel(iFoto);
		
		tc = new JLabel("Tc Kimlik: "+ou.tcKimlik);
		
		unvan = new JLabel("Ünvaný: "+ou.unvan);
		
		adsoyad = new JLabel("Ad-Soyad: "+ou.ad+" "+ou.soyad);
		
		bolum = new JLabel("Bölümü: "+ou.bolum);
		
		dogumtarih = new JLabel("Doðum Tarihi: "+ou.dogumTarihi);
		
		tel = new JLabel("Telefon Numarasý: "+ou.telefon);
		
		email = new JLabel("E-Posta Adresi: "+ou.ePosta);
		
		foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
		
		Color yazirenk = new Color(100,70,70);
		
		tc.setForeground(yazirenk);
		
		unvan.setForeground(yazirenk);

		adsoyad.setForeground(yazirenk);
		
		bolum.setForeground(yazirenk);
		
		dogumtarih.setForeground(yazirenk);
		
		email.setForeground(yazirenk);
		
		tel.setForeground(yazirenk);
		
		add(foto);
		add(tc);
		add(unvan);
		add(adsoyad);
		add(bolum);
		add(dogumtarih);
		add(tel);
		add(email);
	}
}
