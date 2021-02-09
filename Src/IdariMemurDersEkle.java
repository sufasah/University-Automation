import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
public class IdariMemurDersEkle extends JPanel{
	private static final long serialVersionUID = -500027701947579662L;
	JLabel kod,ad,bolum,derslik;
	JTextField tfKod,tfAd,tfBolum,tfDerslik;
	JButton ekle;
	IdariMemur im;
	IdariMemurDersEkle(IdariMemur im){
		super();
		this.im=im;
		setLayout(null);
		
		
		
		kod= new JLabel("Kodu : ");
		
		ad=new JLabel("Adý : ");
		
		bolum = new JLabel("Bolumu : ");
		
		derslik = new JLabel("Dersliði : ");
		
		tfBolum= new JTextField();
		
		tfAd= new JTextField();
		
		tfKod= new JTextField();
		
		tfDerslik= new JTextField();
		
		ekle = new JButton("Dersi Ekle");

		final Color tfcolor=new Color(220,230,255);
		
		Color yazirenk = new Color(100,70,70);
		
		Font tfFont = new Font("ArialBlack",Font.BOLD,22);

		
		kod.setForeground(yazirenk);
		
		ad.setForeground(yazirenk);
		
		derslik.setForeground(yazirenk);
		
		bolum.setForeground(yazirenk);
		
		tfBolum.setBackground(tfcolor);

		tfAd.setBackground(tfcolor);
		
		tfKod.setBackground(tfcolor);
		
		tfDerslik.setBackground(tfcolor);
		
		tfBolum.setFont(tfFont);

		tfAd.setFont(tfFont);
		
		tfKod.setFont(tfFont);
		
		tfDerslik.setFont(tfFont);
		
		add(kod);
		add(ad);
		add(derslik);
		add(tfAd);
		add(tfKod);
		add(tfDerslik);
		add(bolum);
		add(tfBolum);
		add(ekle);
		
		IdariMemurDersEkle ths=this;

		
		ekle.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String skod = tfKod.getText(),
						sad = tfAd.getText().replace(' ', '_'),
						sbolum = tfBolum.getText().replace(' ', '_'),
						sderslik =tfDerslik.getText().replace(' ', '_');
				if(skod.contains(" ")) {
					JOptionPane.showMessageDialog(ths, "Kodun Ýçerisinde Boþluk Karakteri Bulunamaz.","Hata!",JOptionPane.ERROR_MESSAGE);
				}
				Ders d = new Ders(skod,sad,sbolum,sderslik);
				try {
					FileHandler.dersKaydet(d);
					JOptionPane.showMessageDialog(ths, "Ders Baþarýyla Kaydedildi.","Kaydedildi!",JOptionPane.INFORMATION_MESSAGE);
				}catch (IOException e2) {
					JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				}catch(KayitVar e3) {
					JOptionPane.showMessageDialog(ths, e3.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);						
				}
			}
		});
	}
}
