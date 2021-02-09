import java.awt.Color;
import java.io.File;

import javax.swing.*;
public class IdariMemurProfil extends JPanel{
	private static final long serialVersionUID = -500027701947579662L;
	JLabel foto,tc,adsoyad,dogumtarih,tel,email;
	IdariMemur im;
	ImageIcon iFoto;

	IdariMemurProfil(IdariMemur im){
		super();
		this.im=im;
		setLayout(null);
		
		iFoto = new ImageIcon(System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\"+im.tcKimlik+".png");
		{
			File fFoto = new File(iFoto.toString());
			if(!fFoto.exists())
				iFoto = new ImageIcon(System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\default.png");
		}
		foto = new JLabel(iFoto);
		
		tc = new JLabel("Tc Kimlik: "+im.tcKimlik);
		
		adsoyad = new JLabel("Ad-Soyad: "+im.ad+" "+im.soyad);
		
		dogumtarih = new JLabel("Doðum Tarihi: "+im.dogumTarihi);
		
		tel = new JLabel("Telefon Numarasý: "+im.telefon);
		
		email = new JLabel("E-Posta Adresi: "+im.ePosta);
		
		foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
		
		Color yazirenk = new Color(100,70,70);
		
		tc.setForeground(yazirenk);
		
		adsoyad.setForeground(yazirenk);
		
		dogumtarih.setForeground(yazirenk);
		
		email.setForeground(yazirenk);
		
		tel.setForeground(yazirenk);
		
		add(foto);
		add(tc);
		add(adsoyad);
		add(dogumtarih);
		add(tel);
		add(email);
	}
}
