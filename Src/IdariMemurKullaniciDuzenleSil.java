import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class IdariMemurKullaniciDuzenleSil extends JPanel implements Etkinlestirilebilir{
	private static final long serialVersionUID = -500027701947579662L;
	IdariMemurForm fr;
	JLabel foto,ad,soyad,dogumtarih,tel,email,sifre;
	JLabel no,bolum,unvan;
	JTextField tfAd,tfDogumTarih,tfSoyad,tfTel,tfEmail,tfNo,tfBolum,tfUnvan,tfSifre;
	JButton sil,kaydet,fotoekle,fotosil,kullanici;
	enum tur{OGRENCI,OGRETIMUYESI,IDARIMEMUR};
	Kullanici seciliKullanici;
	tur Tur;
	IdariMemur im;
	ImageIcon iFoto;
	File fotofile;
	IdariMemurKullaniciDuzenleSil(IdariMemur im,IdariMemurForm form){
		super();
		this.im=im;
		fr=form;
		setLayout(null);
		IdariMemurKullaniciDuzenleSil ths=this;
		
		
		foto = new JLabel(iFoto);
		
		ad = new JLabel("Ad: ");
		
		soyad= new JLabel("Soyad: ");
		
		dogumtarih = new JLabel("Doðum Tarihi: ");
		
		tel = new JLabel("Telefon Numarasý: ");
		
		email = new JLabel("E-Posta Adresi: ");
		
		no= new JLabel("Numarasý: ");
		
		bolum= new JLabel("Bölümü: ");
		
		unvan = new JLabel("Ünvaný: ");

		sifre = new JLabel("Þifre:");
		
		
		tfAd = new JTextField();
		
		tfSoyad = new JTextField();
		
		tfDogumTarih = new JTextField();
		
		tfTel = new JTextField();
		
		tfEmail = new JTextField();
		
		tfNo= new JTextField();
		
		tfBolum= new JTextField();
		
		tfUnvan = new JTextField();
		
		tfSifre = new JTextField();
		
		sil = new JButton("Sil");
		
		kaydet=new JButton("Kaydet");
		
		fotoekle = new JButton("Resim Yükle");
		
		fotosil = new JButton("Resmi Sil");
		
		
		kullanici = new JButton("Düzenlenecek Kullanýcýyý Seçmek Ýçin Týklayýnýz.");
			kullanici.setBorder(NavButtonMouseAdapter.bBorder);
			kullanici.setBackground(NavButtonMouseAdapter.but);
			for(MouseListener l : kullanici.getMouseListeners())
				kullanici.removeMouseListener(l);
			kullanici.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					kullanici.setBackground(NavButtonMouseAdapter.butClicked);
					kullanici.setBorder(NavButtonMouseAdapter.bBorderClicked);
				}
				public void mouseReleased(MouseEvent e) {
					kullanici.setBackground(NavButtonMouseAdapter.but);
					kullanici.setBorder(NavButtonMouseAdapter.bBorder);
					if(kullanici.contains(e.getPoint()))
						kullanici.doClick();
				}
			});
			kullanici.addActionListener(new ListActionListener(this));
		
		
		
		foto.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.lightGray));
		
		Color yazirenk = new Color(100,70,70);

		final Color tfcolor=new Color(220,230,255);
		
		Font tfFont = new Font("ArialBlack",Font.BOLD,22);
		
		ad.setForeground(yazirenk);
		
		soyad.setForeground(yazirenk);
		
		dogumtarih.setForeground(yazirenk);
		
		sifre.setForeground(yazirenk);
		
		email.setForeground(yazirenk);
		
		tel.setForeground(yazirenk);
		
		no.setForeground(yazirenk);
		
		bolum.setForeground(yazirenk);
		
		unvan.setForeground(yazirenk);

		tfAd.setBackground(tfcolor);
		
		tfSoyad.setBackground(tfcolor);
		
		tfDogumTarih.setBackground(tfcolor);
		
		tfSifre.setBackground(tfcolor);
		
		tfEmail.setBackground(tfcolor);
		
		tfTel.setBackground(tfcolor);
		
		tfNo.setBackground(tfcolor);
		
		tfBolum.setBackground(tfcolor);
		
		tfUnvan.setBackground(tfcolor);
		
		tfAd.setFont(tfFont);
		
		tfSoyad.setFont(tfFont);
		
		tfDogumTarih.setFont(tfFont);
		
		tfSifre.setFont(tfFont);
		
		tfEmail.setFont(tfFont);
		
		tfTel.setFont(tfFont);
		
		tfNo.setFont(tfFont);
		
		tfBolum.setFont(tfFont);
		
		tfUnvan.setFont(tfFont);
		
		unvan.setVisible(false);
		tfUnvan.setVisible(false);
		setAllEnabled(false);
	
		add(foto);
		add(ad);
		add(soyad);
		add(dogumtarih);
		add(tel);
		add(no);
		add(email);
		add(bolum);
		add(unvan);
		add(sifre);
		add(tfAd);
		add(tfSoyad);
		add(tfDogumTarih);
		add(tfTel);
		add(tfNo);
		add(tfEmail);
		add(tfBolum);
		add(tfUnvan);
		add(tfSifre);
		add(kaydet);
		add(sil);
		add(fotoekle);
		add(kullanici);
		add(fotosil);
		
		fotoekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fr.setEnabled(false);
				JFileChooser tmp = new JFileChooser();
				tmp.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory().getAbsoluteFile());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","jpeg","png","bmp");
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
				fr.setEnabled(true);
			}
		});
		fotosil.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				fotofile=null;
				iFoto=null;
				foto.setIcon(null);
			}
		});
		
		sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option=JOptionPane.showConfirmDialog(ths, "Bu Kullanýcýyý Silmek Ýstediðinize Emin Misiniz ?","Silme Ýþlemi",JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION) {
					try {
						FileHandler.kullaniciSil(seciliKullanici.tcKimlik);
						FileHandler.alinanDersleriSil(seciliKullanici.tcKimlik);
						FileHandler.devamsizliklariSil(seciliKullanici.tcKimlik);
						FileHandler.notlariSil(seciliKullanici.tcKimlik);
						FileHandler.profilFotografiSil(seciliKullanici.tcKimlik);
						//Burada Silerken Tüm Baðlantýlarýný yani verilen derstekini veya ders alanlardakini
						//hep silmek lazým.
					}catch(KayitBulunamadi e2) {
						
					}catch(IOException e3) {
						JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(ths, "Kullanýcý Baþarýyla Silindi.","Silindi!",JOptionPane.INFORMATION_MESSAGE);
					setAllEnabled(false);
					kullanici.setText("Düzenlenecek Kullanýcýyý Seçmek Ýçin Týklayýnýz.");
					Tur=null;
					seciliKullanici=null;
				}
			}
		});
		
		kaydet.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String stc = seciliKullanici.tcKimlik,
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
						||(seciliKullanici.getClass()!=IdariMemur.class&&(sbolum.equals("")||(seciliKullanici.getClass()!=OgretimUyesi.class&&sno.equals("")))&&(seciliKullanici.getClass()!=Ogrenci.class&&sunvan.equals("")))
						||ssifre.contains(" ")||ssoyad.contains(" ")
						||sdogumtarih.contains(" ")||stel.contains(" ")||seposta.contains(" ")
						||(seciliKullanici.getClass()!=IdariMemur.class&&(sbolum.contains(" ")||(seciliKullanici.getClass()!=OgretimUyesi.class&&sno.contains(" ")))&&(seciliKullanici.getClass()!=Ogrenci.class&&sunvan.contains(" ")))
						||stc.contains(" ")) {
					JOptionPane.showMessageDialog(ths, "Hiçbir Alan Boþ Býrakýlmamalýdýr Ve Ad Alaný Hariç Hiçbir Alanda Boþluk Karakteri Kullanýlamaz.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(ssifre.length()<8||ssifre.length()>20 || ssifre.contains(" "))
				{
					JOptionPane.showMessageDialog(ths, "Þifre En Az 8 En Fazla 20 Haneli Olmalýdýr ve Bosluk Karakteri Iceremez.","Hata!",JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int i=0;i<sad.length();i++) 
					if(!Character.isLetter(sad.charAt(i))&& sad.charAt(i)!=' ') {
						JOptionPane.showMessageDialog(ths, "AdKýsmý Sadece Harflerden ve Boþluk Karakterinden Oluþabilir.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
				for(int i=0;i<ssoyad.length();i++) 
					if(!Character.isLetter(sad.charAt(i))) {
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
				Kullanici kaydedilecek=null;
				if(Tur==tur.OGRENCI) {					
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
				else if(Tur==tur.OGRETIMUYESI) {
					kaydedilecek=new OgretimUyesi(sad, ssoyad, stc, sbolum, sdogumtarih, seposta, stel, sunvan);
				}
				else if(Tur==tur.IDARIMEMUR){
					kaydedilecek=new IdariMemur(sad,ssoyad,stc,sdogumtarih,seposta,stel);
				}
				try {
					if(fotofile!=null)
						FileHandler.profilFotografiKaydet(kaydedilecek.tcKimlik, fotofile);
					else
						FileHandler.profilFotografiSil(kaydedilecek.tcKimlik);
					FileHandler.kullaniciDuzenle(seciliKullanici.tcKimlik, kaydedilecek);
					FileHandler.loginKaydet(kaydedilecek.tcKimlik, tfSifre.getText());
					JOptionPane.showMessageDialog(ths, "Kullanýcý Baþarýyla Düzenlendi.","Düzenlendi!",JOptionPane.INFORMATION_MESSAGE);
				}catch (IOException e2) {
					JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				}catch(KayitUyusmazligi e4) {
					JOptionPane.showMessageDialog(ths,e4.getMessage() ,"Hata!",JOptionPane.ERROR_MESSAGE);
				} catch (KayitBulunamadi e1) {
					JOptionPane.showMessageDialog(ths,"Beklenmeyen Hata!","Hata!",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	public void setAllEnabled(boolean flag) {
		foto.setEnabled(flag);
		ad.setEnabled(flag);
		soyad.setEnabled(flag);
		dogumtarih.setEnabled(flag);
		tel.setEnabled(flag);
		email.setEnabled(flag);
		sifre.setEnabled(flag);
		no.setEnabled(flag);
		bolum.setEnabled(flag);
		unvan.setEnabled(flag);
		tfAd.setEnabled(flag);
		tfSoyad.setEnabled(flag);
		tfDogumTarih.setEnabled(flag);
		tfTel.setEnabled(flag);
		tfEmail.setEnabled(flag);
		tfNo.setEnabled(flag);
		tfBolum.setEnabled(flag);
		tfUnvan.setEnabled(flag);
		tfSifre.setEnabled(flag);
		sil.setEnabled(flag);
		kaydet.setEnabled(flag);
		fotoekle.setEnabled(flag);
		fotosil.setEnabled(flag);
	}
	class ListActionListener implements ActionListener{
		IdariMemurKullaniciDuzenleSil pnl;
		ArrayList<Kullanici> kullaniciList;
		DefaultListModel<Kullanici> listModel;
		public ListActionListener(IdariMemurKullaniciDuzenleSil p) {
			pnl=p;
		}
		public void actionPerformed(ActionEvent e) {
			pnl.fr.setEnabled(false);
			JFrame f = new JFrame();
			f.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					pnl.fr.setEnabled(true);
					pnl.fr.requestFocus();
				}
			});
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Container pane = f.getContentPane();
			f.setLayout(null);
			
			DefaultListModel<Kullanici> listModel= new DefaultListModel<Kullanici>();
			JList<Kullanici> list = new JList<Kullanici>(listModel);
			JScrollPane sc = new JScrollPane(list);
			
			DefaultListCellRenderer lcr=((DefaultListCellRenderer)list.getCellRenderer());
			
			JTextField tf = new JTextField();
				tf.setFont(new Font("Hei",Font.PLAIN,35));
				tf.setBackground(new Color(250,240,230));
			
			list.setBackground(new Color(230,230,200));
			list.setForeground(new Color(50,70,150));
			list.setFont(new Font("Hei",Font.BOLD|Font.ITALIC,20));
			ArrayList<Kullanici> kullaniciList=null ;
			try {
				kullaniciList= FileHandler.kullanicilariGetir();
			}catch(IOException e2) {
				f.dispose();
				JOptionPane.showMessageDialog(pnl, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				return;
			}
			for(int i=0;i<kullaniciList.size();i++) {
				listModel.addElement(kullaniciList.get(i));
			}
			
			Dimension screen = pnl.getToolkit().getScreenSize();
			
			lcr.setHorizontalAlignment(SwingConstants.CENTER);;
			lcr.setVerticalAlignment(SwingConstants.CENTER);;
			this.kullaniciList=kullaniciList;
			this.listModel=listModel;
			
			pane.add(tf);
			pane.add(sc);
			f.setSize(screen.width/2,screen.height/2);
			f.setLocation(screen.width/2-f.getWidth()/2,screen.height/2-f.getHeight()/2);
			f.setVisible(true);
			tf.setSize(pane.getWidth(),pane.getHeight()/6);
			tf.setLocation(0, 0);
			ListActionListener ths=this;
			tf.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					listModel.removeAllElements();
					String[] searches=tf.getText().toLowerCase().split(" ");
					for(int i=0;i<ths.kullaniciList.size();i++) {
						Kullanici k = ths.kullaniciList.get(i);
						for(int j=0;j<searches.length;j++) {
							String search =searches[j];
							if( k.ad.toLowerCase().contains(search)||k.soyad.toLowerCase().contains(search)||k.tcKimlik.toLowerCase().contains(search)
									||k.ePosta.toLowerCase().contains(search)||k.dogumTarihi.toLowerCase().contains(search)||k.telefon.toLowerCase().contains(search)
									||(k.getClass()==Ogrenci.class && (((Ogrenci)k).bolum.toLowerCase().contains(search) || Integer.toString(((Ogrenci)k).no).toLowerCase().contains(search)) )
									||(k.getClass()==OgretimUyesi.class && ((((OgretimUyesi)k).bolum).toLowerCase().contains(search) || ((OgretimUyesi)k).unvan.toLowerCase().contains(search)) )) {
								listModel.addElement(k);
							}
						}
					}
				}
			});
			list.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						Kullanici k= list.getSelectedValue();
						String sifre=null;
						ImageIcon fotograf;
						f.dispose();
						try {
							sifre=FileHandler.loginGetir(k.tcKimlik);
							fotograf=FileHandler.profilFotografiGetir(k.tcKimlik);
						} catch (IOException e1) {JOptionPane.showMessageDialog(pnl, "Veritabaný Hatasý! Verilere Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);return;} catch (KayitBulunamadi e1) {JOptionPane.showMessageDialog(pnl, "Beklenmeyen Hata!","Hata!",JOptionPane.ERROR_MESSAGE);return;}
						pnl.seciliKullanici=k;
						pnl.kullanici.setText("Seçilen Kullanýcýnýn Tc Kimlik Numarasý : "+k.tcKimlik+" | Baþka Bir Kiþi Seçmek Ýçin Týklayýnýz.");
						pnl.setAllEnabled(true);
						
						pnl.tfAd.setText(k.ad);
						pnl.tfSoyad.setText(k.soyad);
						pnl.tfDogumTarih.setText(k.dogumTarihi);
						pnl.tfEmail.setText(k.ePosta);
						pnl.tfSifre.setText(sifre);
						pnl.tfTel.setText(k.telefon);
						pnl.foto.setIcon(fotograf);

						if(k.getClass()==Ogrenci.class) {
							pnl.Tur=IdariMemurKullaniciDuzenleSil.tur.OGRENCI;
							Ogrenci o = (Ogrenci)k;
							pnl.bolum.setVisible(true);;
							pnl.tfBolum.setVisible(true);
							pnl.no.setVisible(true);
							pnl.tfNo.setVisible(true);
							pnl.tfUnvan.setVisible(false);
							pnl.unvan.setVisible(false);
							
							pnl.tfBolum.setText(o.bolum);
							pnl.tfNo.setText(Integer.toString(o.no));
						}
						else if(k.getClass()==OgretimUyesi.class) {
							pnl.Tur=IdariMemurKullaniciDuzenleSil.tur.OGRETIMUYESI;
							OgretimUyesi ou = (OgretimUyesi)k;
							pnl.bolum.setVisible(true);;
							pnl.tfBolum.setVisible(true);
							pnl.no.setVisible(false);
							pnl.tfNo.setVisible(false);
							pnl.tfUnvan.setVisible(true);
							pnl.unvan.setVisible(true);
							
							pnl.tfUnvan.setText(ou.unvan);
							pnl.tfBolum.setText(ou.bolum);
						}
						else {
							pnl.Tur=IdariMemurKullaniciDuzenleSil.tur.IDARIMEMUR;
							pnl.bolum.setVisible(false);;
							pnl.tfBolum.setVisible(false);
							pnl.no.setVisible(false);
							pnl.tfNo.setVisible(false);
							pnl.tfUnvan.setVisible(false);
							pnl.unvan.setVisible(false);
						}
						
					}
				}
			});
			f.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					tf.setSize(pane.getWidth(),tf.getHeight());
					sc.setSize(pane.getWidth(), pane.getHeight()-tf.getHeight());
					sc.setLocation(0, tf.getHeight());
					list.setSize(sc.getViewport().getPreferredSize());
				}
			});
			
		}
		
	}
}

