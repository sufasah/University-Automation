import java.awt.Color;
import java.io.File;

import javax.swing.*;
public class OgrenciProfil extends JPanel{
	private static final long serialVersionUID = -500027701947579662L;
	JLabel foto,tc,no,adsoyad,bolum,dogumtarih,tel,email;
	Ogrenci ogr;
	ImageIcon iFoto;

	OgrenciProfil(Ogrenci ogr){
		super();
		this.ogr=ogr;
		setLayout(null);
		
		iFoto = new ImageIcon(System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\"+ogr.tcKimlik+".png");
		{
			File fFoto = new File(iFoto.toString());
			if(!fFoto.exists())
				iFoto = new ImageIcon(System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\default.png");
		}
		foto = new JLabel(iFoto);
		
		tc = new JLabel("Tc Kimlik: "+ogr.tcKimlik);
		
		no = new JLabel("Numara: "+Integer.toString(ogr.no));
		
		adsoyad = new JLabel("Ad-Soyad: "+ogr.ad+" "+ogr.soyad);
		
		bolum = new JLabel("Bölümü: "+ogr.bolum);
		
		dogumtarih = new JLabel("Doðum Tarihi: "+ogr.dogumTarihi);
		
		tel = new JLabel("Telefon Numarasý: "+ogr.telefon);
		
		email = new JLabel("E-Posta Adresi: "+ogr.ePosta);
		
		foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
		
		Color yazirenk = new Color(100,70,70);
		
		tc.setForeground(yazirenk);
		
		no.setForeground(yazirenk);

		adsoyad.setForeground(yazirenk);
		
		bolum.setForeground(yazirenk);
		
		dogumtarih.setForeground(yazirenk);
		
		email.setForeground(yazirenk);
		
		tel.setForeground(yazirenk);
		
		add(foto);
		add(tc);
		add(no);
		add(adsoyad);
		add(bolum);
		add(dogumtarih);
		add(tel);
		add(email);
	}
}
