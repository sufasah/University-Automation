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
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
public class IdariMemurAlinanDersEkleSil extends JPanel implements Etkinlestirilebilir{
	private static final long serialVersionUID = -500027701947579662L;
	IdariMemurForm fr;
	JButton ekle,ders,ogrenci,sil;
	Ders seciliDers;
	Ogrenci seciliOgrenci;
	IdariMemur im;
	IdariMemurAlinanDersEkleSil(IdariMemur im,IdariMemurForm form){
		super();
		this.im=im;
		fr=form;
		setLayout(null);
		
		ekle = new JButton("Ekle");
		
		sil= new JButton("Sil");
		
		ders= new JButton("Verilecek Dersi Seçmek Ýçin Týklayýnýz.");
			ders.setBorder(NavButtonMouseAdapter.bBorder);
			ders.setBackground(NavButtonMouseAdapter.but);
			for(MouseListener l : ders.getMouseListeners())
				ders.removeMouseListener(l);
			ders.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					ders.setBackground(NavButtonMouseAdapter.butClicked);
					ders.setBorder(NavButtonMouseAdapter.bBorderClicked);
				}
				public void mouseReleased(MouseEvent e) {
					ders.setBackground(NavButtonMouseAdapter.but);
					ders.setBorder(NavButtonMouseAdapter.bBorder);
					if(ders.contains(e.getPoint()))
						ders.doClick();
				}
			});
			ders.addActionListener(new DersListActionListener(this));
			
		ogrenci= new JButton("Dersi Alacak Öðrenciyi Seçmek Ýçin Týklayýnýz.");
			ogrenci.setBorder(NavButtonMouseAdapter.bBorder);
			ogrenci.setBackground(NavButtonMouseAdapter.but);
			ogrenci.setEnabled(false);
			for(MouseListener l : ogrenci.getMouseListeners())
				ogrenci.removeMouseListener(l);
			ogrenci.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					ogrenci.setBackground(NavButtonMouseAdapter.butClicked);
					ogrenci.setBorder(NavButtonMouseAdapter.bBorderClicked);
				}
				public void mouseReleased(MouseEvent e) {
					ogrenci.setBackground(NavButtonMouseAdapter.but);
					ogrenci.setBorder(NavButtonMouseAdapter.bBorder);
					if(ogrenci.contains(e.getPoint()))
						ogrenci.doClick();
				}
			});
			ogrenci.addActionListener(new OgrenciListActionListener(this));
		
		
		setAllEnabled(false);
		
		add(ders);
		add(ogrenci);
		add(sil);
		add(ekle);
		
		IdariMemurAlinanDersEkleSil ths=this;
		
		sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option=JOptionPane.showConfirmDialog(ths, "Bu Öðrencinin Bu Dersi Alma Kaydýný Silmek Ýstediðinize Emin Misiniz ?","Silme Ýþlemi",JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION) {
					try {
						FileHandler.alinanDersSil(seciliOgrenci.tcKimlik, seciliDers.kod);
						//Burada Silerken Tüm Baðlantýlarýný yani verilen derstekini veya ders alanlardakini
						//hep silmek lazým.
					}catch(KayitBulunamadi e2) {
						JOptionPane.showMessageDialog(ths, e2.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}catch(IOException e3) {
						JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(ths, "Alýnan Ders Kaydý Baþarýyla Silindi.","Silindi!",JOptionPane.INFORMATION_MESSAGE);
				
				}

			}
		});
		
		ekle.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					FileHandler.alinanDersKaydet(seciliOgrenci.tcKimlik,seciliDers.kod);
					JOptionPane.showMessageDialog(ths, "Öðrencinin Dersi Verme Kaydý Yapýldý.","Kaydedildi!",JOptionPane.INFORMATION_MESSAGE);
				}catch (IOException e2) {
					JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				}catch(KayitVar e3) {
					JOptionPane.showMessageDialog(ths, e3.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	public void setAllEnabled(boolean flag) {
		ekle.setEnabled(flag);
		sil.setEnabled(flag);
	}
	class DersListActionListener implements ActionListener{
		IdariMemurAlinanDersEkleSil pnl;
		ArrayList<Ders> dersList;
		DefaultListModel<Ders> listModel;
		public DersListActionListener(IdariMemurAlinanDersEkleSil p) {
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
			
			DefaultListModel<Ders> listModel= new DefaultListModel<Ders>();
			JList<Ders> list = new JList<Ders>(listModel);
			JScrollPane sc = new JScrollPane(list);
			
			DefaultListCellRenderer lcr=((DefaultListCellRenderer)list.getCellRenderer());
			
			JTextField tf = new JTextField();
				tf.setFont(new Font("Hei",Font.PLAIN,35));
				tf.setBackground(new Color(250,240,230));
			
			list.setBackground(new Color(230,230,200));
			list.setForeground(new Color(50,70,150));
			list.setFont(new Font("Hei",Font.BOLD|Font.ITALIC,20));
			ArrayList<Ders> dersList=null;
			try {
				dersList= FileHandler.dersleriGetir();
			}catch(IOException e2) {
				f.dispose();
				JOptionPane.showMessageDialog(pnl, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
			}
			for(int i=0;i<dersList.size();i++) {
				listModel.addElement(dersList.get(i));
			}
			
			Dimension screen = pnl.getToolkit().getScreenSize();
			
			lcr.setHorizontalAlignment(SwingConstants.CENTER);;
			lcr.setVerticalAlignment(SwingConstants.CENTER);;
			this.dersList=dersList;
			this.listModel=listModel;
			
			pane.add(tf);
			pane.add(sc);
			f.setSize(screen.width/2,screen.height/2);
			f.setLocation(screen.width/2-f.getWidth()/2,screen.height/2-f.getHeight()/2);
			f.setVisible(true);
			tf.setSize(pane.getWidth(),pane.getHeight()/6);
			tf.setLocation(0, 0);
			DersListActionListener ths=this;
			tf.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					listModel.removeAllElements();
					String[] searches=tf.getText().toLowerCase().split(" ");
					for(int i=0;i<ths.dersList.size();i++) {
						Ders d = ths.dersList.get(i);
						for(int j=0;j<searches.length;j++) {
							String search =searches[j];
							if( d.kod.toLowerCase().contains(search)||d.ad.toLowerCase().contains(search)||d.bolum.toLowerCase().contains(search)
									||d.derslik.toLowerCase().contains(search)) {
								listModel.addElement(d);
							}
						}
					}
				}
			});
			list.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						Ders d= list.getSelectedValue();
						f.dispose();
						pnl.seciliDers=d;
						pnl.ders.setText("Seçilen Dersin Kodu: "+d.kod+" | Baþka Bir Ders Seçmek Ýçin Týklayýnýz.");
						pnl.ogrenci.setEnabled(true);			
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
	class OgrenciListActionListener implements ActionListener{
		IdariMemurAlinanDersEkleSil pnl;
		ArrayList<Ogrenci> ogrenciList;
		DefaultListModel<Ogrenci> listModel;
		public OgrenciListActionListener(IdariMemurAlinanDersEkleSil p) {
			pnl=p;
		}
		public void actionPerformed(ActionEvent e) {
			pnl.setEnabled(false);
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
			
			DefaultListModel<Ogrenci> listModel= new DefaultListModel<Ogrenci>();
			JList<Ogrenci> list = new JList<Ogrenci>(listModel);
			JScrollPane sc = new JScrollPane(list);
			
			DefaultListCellRenderer lcr=((DefaultListCellRenderer)list.getCellRenderer());
			
			JTextField tf = new JTextField();
				tf.setFont(new Font("Hei",Font.PLAIN,35));
				tf.setBackground(new Color(250,240,230));
			
			list.setBackground(new Color(230,230,200));
			list.setForeground(new Color(50,70,150));
			list.setFont(new Font("Hei",Font.BOLD|Font.ITALIC,20));
			try {
				ogrenciList= FileHandler.ogrencileriGetir();
			}catch(IOException e2) {
				f.dispose();
				JOptionPane.showMessageDialog(pnl, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				return;
			}
			for(int i=0;i<ogrenciList.size();i++) {
				listModel.addElement(ogrenciList.get(i));
			}
			
			Dimension screen = pnl.getToolkit().getScreenSize();
			
			lcr.setHorizontalAlignment(SwingConstants.CENTER);;
			lcr.setVerticalAlignment(SwingConstants.CENTER);;

			this.listModel=listModel;
			
			pane.add(tf);
			pane.add(sc);
			f.setSize(screen.width/2,screen.height/2);
			f.setLocation(screen.width/2-f.getWidth()/2,screen.height/2-f.getHeight()/2);
			f.setVisible(true);
			tf.setSize(pane.getWidth(),pane.getHeight()/6);
			tf.setLocation(0, 0);
			OgrenciListActionListener ths=this;
			tf.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					listModel.removeAllElements();
					String[] searches=tf.getText().toLowerCase().split(" ");
					for(int i=0;i<ths.ogrenciList.size();i++) {
						Ogrenci o= ths.ogrenciList.get(i);
						for(int j=0;j<searches.length;j++) {
							String search =searches[j];
							if( o.ad.toLowerCase().contains(search)||o.soyad.toLowerCase().contains(search)||o.bolum.toLowerCase().contains(search)
									||o.dogumTarihi.toLowerCase().contains(search) || o.ePosta.toLowerCase().contains(search) || o.tcKimlik.toLowerCase().contains(search) || o.telefon.toLowerCase().contains(search)|| Integer.toString(o.no).toLowerCase().contains(search)) {
								listModel.addElement(o);
							}
						}
					}
				}
			});
			list.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						Ogrenci o = list.getSelectedValue();
						f.dispose();
						pnl.seciliOgrenci=o;
						pnl.ogrenci.setText("Seçilen Öðrencinin Tc Kimlik Numarasý: "+o.tcKimlik+" | Baþka Bir Öðrenciyi Seçmek Ýçin Týklayýnýz.");
						setAllEnabled(true);
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