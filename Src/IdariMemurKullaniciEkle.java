import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
public class IdariMemurKullaniciEkle extends JPanel{
	private static final long serialVersionUID = -500027701947579662L;
	JLabel foto,tc,ad,soyad,dogumtarih,tel,email,sifre;
	JLabel no,bolum,unvan;
	JTextField tfTc,tfAd,tfSoyad,tfDogumTarih,tfTel,tfEmail,tfNo,tfBolum,tfUnvan,tfSifre;
	JButton ekle,fotoekle,fotosil;
	JRadioButton ogrB,ouB,imB;
	ButtonGroup turler;
	IdariMemur im;
	ImageIcon iFoto;
	File fotofile;
	IdariMemurKullaniciEkle(IdariMemur im){
		super();
		this.im=im;
		setLayout(null);
		
		
		foto = new JLabel(iFoto);
		
		tc = new JLabel("Tc Kimlik: ");
		
		ad = new JLabel("Ad: ");
		
		soyad = new JLabel("Soyad: ");
		
		dogumtarih = new JLabel("Doðum Tarihi: ");
		
		tel = new JLabel("Telefon Numarasý: ");
		
		email = new JLabel("E-Posta Adresi: ");
		
		no= new JLabel("Numarasý: ");
		
		bolum= new JLabel("Bölümü: ");
		
		unvan = new JLabel("Ünvaný: ");

		sifre = new JLabel("Þifre:");
		
		tfTc = new JTextField();
		
		tfAd= new JTextField();
		
		tfSoyad= new JTextField();
		
		tfDogumTarih = new JTextField();
		
		tfTel = new JTextField();
		
		tfEmail = new JTextField();
		
		tfNo= new JTextField();
		
		tfBolum= new JTextField();
		
		tfUnvan = new JTextField();
		
		tfSifre = new JTextField();
		
		ekle = new JButton("Kullanýcýyý Ekle");
		
		fotoekle = new JButton("Resim Yükle");
		
		fotosil = new JButton("Resmi Sil");

		
		ogrB = new JRadioButton("Öðrenci");
			ogrB.setSelected(true);
			
		ouB = new JRadioButton("Öðretim Üyesi");
		
		imB = new JRadioButton("Ýdari Memur");
		
		turler = new ButtonGroup();

		ActionListener radiolar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton s = (JRadioButton)e.getSource();
				if(s==ogrB) {
					bolum.setVisible(true);
					no.setVisible(true);
					unvan.setVisible(false);

					tfBolum.setVisible(true);
					tfNo.setVisible(true);
					tfUnvan.setVisible(false);

				}
				else if(s==imB) {
					bolum.setVisible(false);
					no.setVisible(false);
					unvan.setVisible(false);				

					tfBolum.setVisible(false);
					tfNo.setVisible(false);
					tfUnvan.setVisible(false);				
				}
				else {
					bolum.setVisible(true);
					no.setVisible(false);
					unvan.setVisible(true);

					tfBolum.setVisible(true);
					tfNo.setVisible(false);
					tfUnvan.setVisible(true);
				}
			}
		};
		ogrB.addActionListener(radiolar);
		imB.addActionListener(radiolar);
		ouB.addActionListener(radiolar);
			radiolar.actionPerformed(new ActionEvent(ogrB, 0, "click"));
		
		foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
		
		Color yazirenk = new Color(100,70,70);
		
		final Color tfcolor=new Color(220,230,255);
		
		Font tfFont = new Font("ArialBlack",Font.BOLD,22);
		
		tc.setForeground(yazirenk);
		
		ad.setForeground(yazirenk);
		
		soyad.setForeground(yazirenk);
		
		dogumtarih.setForeground(yazirenk);
		
		sifre.setForeground(yazirenk);
		
		email.setForeground(yazirenk);
		
		tel.setForeground(yazirenk);
		
		no.setForeground(yazirenk);
		
		bolum.setForeground(yazirenk);
		
		unvan.setForeground(yazirenk);
		
		tfTc.setBackground(tfcolor);
		
		tfAd.setBackground(tfcolor);
		
		tfSoyad.setBackground(tfcolor);
		
		tfDogumTarih.setBackground(tfcolor);
		
		tfSifre.setBackground(tfcolor);
		
		tfEmail.setBackground(tfcolor);
		
		tfTel.setBackground(tfcolor);
		
		tfNo.setBackground(tfcolor);
		
		tfBolum.setBackground(tfcolor);
		
		tfUnvan.setBackground(tfcolor);
		
		tfTc.setFont(tfFont);
		
		tfAd.setFont(tfFont);
		
		tfSoyad.setFont(tfFont);
		
		tfDogumTarih.setFont(tfFont);
		
		tfSifre.setFont(tfFont);
		
		tfEmail.setFont(tfFont);
		
		tfTel.setFont(tfFont);
		
		tfNo.setFont(tfFont);
		
		tfBolum.setFont(tfFont);
		
		tfUnvan.setFont(tfFont);
		
		turler.add(ogrB);
		turler.add(ouB);
		turler.add(imB);
		
		add(foto);
		add(tc);
		add(ad);
		add(soyad);
		add(dogumtarih);
		add(tel);
		add(no);
		add(email);
		add(bolum);
		add(unvan);
		add(sifre);
		add(tfTc);
		add(tfAd);
		add(tfSoyad);
		add(tfDogumTarih);
		add(tfTel);
		add(tfNo);
		add(tfEmail);
		add(tfBolum);
		add(tfUnvan);
		add(tfSifre);
		add(ogrB);
		add(ouB);
		add(imB);
		add(ekle);
		add(fotoekle);
		add(fotosil);
		
		IdariMemurKullaniciEkle ths=this;

		fotosil.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				fotofile=null;
				iFoto=null;
				foto.setIcon(null);
			}
		});
		
		fotoekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser tmp = new JFileChooser();
				tmp.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory().getAbsoluteFile());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGES", "jpg","jpeg","png");
				tmp.addChoosableFileFilter(filter);
				tmp.setFileSelectionMode(JFileChooser.FILES_ONLY);
				tmp.setFileFilter(filter);
				
				int res=tmp.showDialog(ths, "Select");

				if(res == JFileChooser.APPROVE_OPTION) {
					File tmpfile = tmp.getSelectedFile();
					
					String mimetype=new MimetypesFileTypeMap().getContentType(tmpfile);
					
					if(!(mimetype!=null && mimetype.split("/")[0].equals("image"))) {
						JOptionPane.showMessageDialog(ths, "Seçim Hatasý ! Seçilen Dosya Bir Resim Deðil.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
					fotofile=tmpfile;
					iFoto=new ImageIcon(fotofile.getAbsolutePath());
					foto.setIcon(iFoto);
				}
			}
		});
		
		ekle.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String stc = tfTc.getText(),
						ssifre = tfSifre.getText(),
						sad= tfAd.getText(),
						ssoyad=tfSoyad.getText(),
						sdogumtarih=tfDogumTarih.getText(),
						stel=tfTel.getText(),
						seposta= tfEmail.getText(),
						sbolum=tfBolum.getText(),
						sno=tfNo.getText(),
						sunvan=tfUnvan.getText();
				
				if(stc.equals("")||ssifre.equals("")||sad.equals("")||ssoyad.equals("")
						||sdogumtarih.equals("")||stel.equals("")||seposta.equals("")
						||(!imB.isSelected()&&(sbolum.equals("")||(!ouB.isSelected()&&sno.equals("")))&&(!ogrB.isSelected()&&sunvan.equals("")))
						||ssifre.contains(" ")||ssoyad.contains(" ")
						||sdogumtarih.contains(" ")||stel.contains(" ")||seposta.contains(" ")
						||(!imB.isSelected()&&(sbolum.contains(" ")||(!ouB.isSelected()&&sno.contains(" ")))&&(!ogrB.isSelected()&&sunvan.contains(" ")))
						||stc.contains(" ")) {
					JOptionPane.showMessageDialog(ths, "Hiçbir Alan Boþ Býrakýlmamalýdýr Ve Ad Alaný Hariç Hiçbir Alanda Boþluk Karakteri Kullanýlamaz.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(stc.length()!=11) {
					JOptionPane.showMessageDialog(ths, "Tc-Kimlik Numarasý 11 Haneli Olmalýdýr.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int i=0;i<stc.length();i++)
					if(!Character.isDigit(stc.charAt(i)))
					{
						JOptionPane.showMessageDialog(ths, "Tc-Kimlik Numarasý Sadece Rakamlardan Oluþabilir.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
				
				if(ssifre.length()<8||ssifre.length()>20 || ssifre.contains(" "))
				{
					JOptionPane.showMessageDialog(ths, "Þifre En Az 8 En Fazla 20 Haneli Olmalýdýr ve Bosluk Karakteri Iceremez.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int i=0;i<sad.length();i++) 
					if(!Character.isLetter(sad.charAt(i))&& sad.charAt(i)!=' ') {
						JOptionPane.showMessageDialog(ths, "Ad Kýsmý Sadece Harflerden ve Boþluk Karakterinden Oluþabilir.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
				for(int i=0;i<ssoyad.length();i++) 
					if(!Character.isLetter(ssoyad.charAt(i))) {
						JOptionPane.showMessageDialog(ths, "Soyad Kýsmý Sadece Harflerden Oluþabilir.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
				
				if(!sdogumtarih.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$"))
				{
					JOptionPane.showMessageDialog(ths, "Doðum Tarihi '01/01/1000' Gibi Bir Formatta Yazýlmalýdýr.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(stel.length()!=11)
				{
					JOptionPane.showMessageDialog(ths, "Telefon Numarasý 11 Haneli Olmalýdýr.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int i=0;i<11;i++)
					if(!Character.isDigit(stel.charAt(i))) {
						JOptionPane.showMessageDialog(ths, "Telefon Numarasý Sadece Rakamlardan Oluþabilir.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
				if(!seposta.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
					JOptionPane.showMessageDialog(ths, "Eposta Adresi 'username@servername.domain' Gibi Bir Formatta Olmalýdýr.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				sad=sad.replace(' ', '_');
				
				Kullanici kaydedilecek=null;
				if(ogrB.isSelected()) {					
					if(sno.length()!=10) {
						JOptionPane.showMessageDialog(ths, "Öðrenci Numarasý 10 Haneli Olmalýdýr.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
					for(int i=0;i<10;i++) 
						if(!Character.isDigit(sno.charAt(i))){
							JOptionPane.showMessageDialog(ths, "Öðrenci Numarasý Sadece Rakamlardan Oluþabilir.","Hata!",JOptionPane.ERROR_MESSAGE);
							return;
						}
					kaydedilecek = new Ogrenci(sad,ssoyad,stc,Integer.valueOf(sno),sbolum,sdogumtarih,seposta,stel);
				}
				else if(ouB.isSelected()) {
					kaydedilecek=new OgretimUyesi(sad, ssoyad, stc, sbolum, sdogumtarih, seposta, stel, sunvan);
				}
				else {
					kaydedilecek=new IdariMemur(sad,ssoyad,stc,sdogumtarih,seposta,stel);
				}
				try {
					if(fotofile!=null)
						FileHandler.profilFotografiKaydet(kaydedilecek.tcKimlik, fotofile);
					else
						FileHandler.profilFotografiSil(kaydedilecek.tcKimlik);
					FileHandler.kullaniciKaydet(kaydedilecek);
					FileHandler.loginKaydet(kaydedilecek.tcKimlik, ssifre);
					JOptionPane.showMessageDialog(ths, "Kullanýcý Baþarýyla Kaydedildi.","Kaydedildi!",JOptionPane.INFORMATION_MESSAGE);
				}catch (IOException e2) {
					JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				}catch(KayitVar e3) {
					JOptionPane.showMessageDialog(ths, e3.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);						
				}
			}
		});
	}
}
