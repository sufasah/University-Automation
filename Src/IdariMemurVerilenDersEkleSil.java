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
public class IdariMemurVerilenDersEkleSil extends JPanel implements Etkinlestirilebilir{
	private static final long serialVersionUID = -500027701947579662L;
	IdariMemurForm fr;
	JButton ekle,ders,ogretimuyesi,sil;
	Ders seciliDers;
	OgretimUyesi seciliOgretimUyesi;
	IdariMemur im;
	IdariMemurVerilenDersEkleSil(IdariMemur im,IdariMemurForm form){
		super();
		this.im=im;
		fr=form;
		setLayout(null);
		
		ekle = new JButton("Ekle");
		
		sil= new JButton("Sil");
		
		ders= new JButton("Verilecek Dersi Se�mek ��in T�klay�n�z.");
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
			
		ogretimuyesi= new JButton("Dersi Verecek ��retim �yesini Se�mek ��in T�klay�n�z.");
			ogretimuyesi.setBorder(NavButtonMouseAdapter.bBorder);
			ogretimuyesi.setBackground(NavButtonMouseAdapter.but);
			ogretimuyesi.setEnabled(false);
			for(MouseListener l : ogretimuyesi.getMouseListeners())
				ogretimuyesi.removeMouseListener(l);
			ogretimuyesi.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					ogretimuyesi.setBackground(NavButtonMouseAdapter.butClicked);
					ogretimuyesi.setBorder(NavButtonMouseAdapter.bBorderClicked);
				}
				public void mouseReleased(MouseEvent e) {
					ogretimuyesi.setBackground(NavButtonMouseAdapter.but);
					ogretimuyesi.setBorder(NavButtonMouseAdapter.bBorder);
					if(ogretimuyesi.contains(e.getPoint()))
						ogretimuyesi.doClick();
				}
			});
			ogretimuyesi.addActionListener(new OgretimUyesiListActionListener(this));
		
		
		setAllEnabled(false);
		
		add(ders);
		add(ogretimuyesi);
		add(sil);
		add(ekle);
		
		IdariMemurVerilenDersEkleSil ths=this;
		
		sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option=JOptionPane.showConfirmDialog(ths, "Bu ��retim �yesinin Bu Dersi Verme Kayd�n� Silmek �stedi�inize Emin Misiniz ?","Silme ��lemi",JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION) {
					try {
						FileHandler.verilenDersSil(seciliOgretimUyesi.tcKimlik, seciliDers.kod);
						//Burada Silerken T�m Ba�lant�lar�n� yani verilen derstekini veya ders alanlardakini
						//hep silmek laz�m.
					}catch(KayitBulunamadi e2) {
						JOptionPane.showMessageDialog(ths, e2.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}catch(IOException e3) {
						JOptionPane.showMessageDialog(ths, "Veritaban� Hatas� ! Veritaban�ndaki Veriye Ula��lam�yor L�tfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(ths, "Verilen Ders Kayd� Ba�ar�yla Silindi.","Silindi!",JOptionPane.INFORMATION_MESSAGE);
				
				}

			}
		});
		
		ekle.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					FileHandler.verilenDersKaydet(seciliOgretimUyesi.tcKimlik,seciliDers.kod);
					JOptionPane.showMessageDialog(ths, "��retim �yesinin Dersi Verme Kayd� Yap�ld�.","Kaydedildi!",JOptionPane.INFORMATION_MESSAGE);
				}catch (IOException e2) {
					JOptionPane.showMessageDialog(ths, "Veritaban� Hatas� ! Veritaban�ndaki Veriye Ula��lam�yor L�tfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
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
		IdariMemurVerilenDersEkleSil pnl;
		ArrayList<Ders> dersList;
		DefaultListModel<Ders> listModel;
		public DersListActionListener(IdariMemurVerilenDersEkleSil p) {
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
				JOptionPane.showMessageDialog(pnl, "Veritaban� Hatas� ! Veritaban�ndaki Veriye Ula��lam�yor L�tfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
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
						pnl.ders.setText("Se�ilen Dersin Kodu: "+d.kod+" | Ba�ka Bir Ders Se�mek ��in T�klay�n�z.");
						pnl.ogretimuyesi.setEnabled(true);			
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
	class OgretimUyesiListActionListener implements ActionListener{
		IdariMemurVerilenDersEkleSil pnl;
		ArrayList<OgretimUyesi> ogretimUyesiList;
		DefaultListModel<OgretimUyesi> listModel;
		public OgretimUyesiListActionListener(IdariMemurVerilenDersEkleSil p) {
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
			
			DefaultListModel<OgretimUyesi> listModel= new DefaultListModel<OgretimUyesi>();
			JList<OgretimUyesi> list = new JList<OgretimUyesi>(listModel);
			JScrollPane sc = new JScrollPane(list);
			
			DefaultListCellRenderer lcr=((DefaultListCellRenderer)list.getCellRenderer());
			
			JTextField tf = new JTextField();
				tf.setFont(new Font("Hei",Font.PLAIN,35));
				tf.setBackground(new Color(250,240,230));
			
			list.setBackground(new Color(230,230,200));
			list.setForeground(new Color(50,70,150));
			list.setFont(new Font("Hei",Font.BOLD|Font.ITALIC,20));
			try {
				ogretimUyesiList= FileHandler.ogretimUyeleriniGetir();
			}catch(IOException e2) {
				f.dispose();
				JOptionPane.showMessageDialog(pnl, "Veritaban� Hatas� ! Veritaban�ndaki Veriye Ula��lam�yor L�tfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				return;
			}
			for(int i=0;i<ogretimUyesiList.size();i++) {
				listModel.addElement(ogretimUyesiList.get(i));
			}
			
			Dimension screen = pnl.getToolkit().getScreenSize();
			
			lcr.setHorizontalAlignment(SwingConstants.CENTER);
			lcr.setVerticalAlignment(SwingConstants.CENTER);
			this.listModel=listModel;
			
			pane.add(tf);
			pane.add(sc);
			f.setSize(screen.width/2,screen.height/2);
			f.setLocation(screen.width/2-f.getWidth()/2,screen.height/2-f.getHeight()/2);
			f.setVisible(true);
			tf.setSize(pane.getWidth(),pane.getHeight()/6);
			tf.setLocation(0, 0);
			OgretimUyesiListActionListener ths=this;
			tf.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					listModel.removeAllElements();
					String[] searches=tf.getText().toLowerCase().split(" ");
					for(int i=0;i<ths.ogretimUyesiList.size();i++) {
						OgretimUyesi ou = ths.ogretimUyesiList.get(i);
						for(int j=0;j<searches.length;j++) {
							String search =searches[j];
							if( ou.ad.toLowerCase().contains(search)||ou.soyad.toLowerCase().contains(search)||ou.bolum.toLowerCase().contains(search)
									||ou.dogumTarihi.toLowerCase().contains(search) || ou.ePosta.toLowerCase().contains(search) || ou.tcKimlik.toLowerCase().contains(search) || ou.telefon.toLowerCase().contains(search)|| ou.unvan.toLowerCase().contains(search)) {
								listModel.addElement(ou);
							}
						}
					}
				}
			});
			list.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						OgretimUyesi ou= list.getSelectedValue();
						f.dispose();
						pnl.seciliOgretimUyesi=ou;
						pnl.ogretimuyesi.setText("Se�ilen ��retim �yesinin Tc Kimlik Numaras�: "+ou.tcKimlik+" | Ba�ka Bir ��retim �yesi Se�mek ��in T�klay�n�z.");
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