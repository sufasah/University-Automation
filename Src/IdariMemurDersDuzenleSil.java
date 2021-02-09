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
public class IdariMemurDersDuzenleSil extends JPanel implements Etkinlestirilebilir{
	private static final long serialVersionUID = -500027701947579662L;
	IdariMemurForm fr;
	JLabel ad,bolum,derslik;
	JTextField tfAd,tfBolum,tfDerslik;
	JButton kaydet,ders,sil;
	Ders seciliDers;
	IdariMemur im;
	IdariMemurDersDuzenleSil(IdariMemur im,IdariMemurForm form){
		super();
		this.im=im;
		fr=form;
		setLayout(null);
		
		
		ad=new JLabel("Adý : ");
		
		bolum = new JLabel("Bolumu : ");
		
		derslik = new JLabel("Dersliði : ");
		
		tfBolum= new JTextField();
		
		tfAd= new JTextField();
		
		
		tfDerslik= new JTextField();
		
		kaydet = new JButton("Kaydet");
		
		sil= new JButton("Sil");
		
		ders= new JButton("Düzenlenecek Dersi Seçmek Ýçin Týklayýnýz.");
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
			ders.addActionListener(new ListActionListener(this));
			
		Color yazirenk = new Color(100,70,70);

		final Color tfcolor=new Color(220,230,255);
		
		Font tfFont = new Font("ArialBlack",Font.BOLD,22);

		
		ad.setForeground(yazirenk);
		
		derslik.setForeground(yazirenk);
		
		bolum.setForeground(yazirenk);
		
		tfAd.setBackground(tfcolor);
		
		tfDerslik.setBackground(tfcolor);
		
		tfBolum.setBackground(tfcolor);
		
		tfAd.setFont(tfFont);
		
		tfDerslik.setFont(tfFont);
		
		tfBolum.setFont(tfFont);
		
		setAllEnabled(false);
		
		add(ad);
		add(derslik);
		add(tfAd);
		add(tfDerslik);
		add(bolum);
		add(tfBolum);
		add(kaydet);
		add(sil);
		add(ders);
		
		IdariMemurDersDuzenleSil ths=this;
		
		sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option=JOptionPane.showConfirmDialog(ths, "Bu Dersi Silmek Ýstediðinize Emin Misiniz ?","Silme Ýþlemi",JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION) {
					try {
						FileHandler.dersSil(seciliDers.kod);
						FileHandler.tumOgrencilerdenalinanDersiSil(seciliDers.kod);
						FileHandler.dersProgramindakileriSil(seciliDers.kod);
						FileHandler.derstekiTumDevamsizliklariSil(seciliDers.kod);
						FileHandler.derstekiTumNotlariSil(seciliDers.kod);
						FileHandler.verilenDerslerdenSil(seciliDers.kod);
						//Burada Silerken Tüm Baðlantýlarýný yani verilen derstekini veya ders alanlardakini
						//hep silmek lazým.
					}catch(KayitBulunamadi e2) {
						
					}catch(IOException e3) {
						JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
						return;
					}
					JOptionPane.showMessageDialog(ths, "Ders Baþarýyla Silindi.","Silindi!",JOptionPane.INFORMATION_MESSAGE);
					setAllEnabled(false);
					ders.setText("Düzenlenecek Dersi Seçmek Ýçin Týklayýnýz.");
					seciliDers=null;
				}

			}
		});
		
		kaydet.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String  sad = tfAd.getText().replace(' ', '_'),
						sbolum = tfBolum.getText().replace(' ', '_'),
						sderslik =tfDerslik.getText().replace(' ', '_');
				
				Ders d = new Ders(seciliDers.kod,sad,sbolum,sderslik);
				try {
					FileHandler.dersDuzenle(seciliDers.kod, d);
					JOptionPane.showMessageDialog(ths, "Ders Baþarýyla Düzenlendi.","Düzenlendi!",JOptionPane.INFORMATION_MESSAGE);
				}catch (IOException e2) {
					JOptionPane.showMessageDialog(ths, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				}catch(KayitBulunamadi e3) {
					JOptionPane.showMessageDialog(ths, e3.getMessage(),"Hata!",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	public void setAllEnabled(boolean flag) {
		ad.setEnabled(flag);
		bolum.setEnabled(flag);
		derslik.setEnabled(flag);
		tfAd.setEnabled(flag);
		tfBolum.setEnabled(flag);
		tfDerslik.setEnabled(flag);
		kaydet.setEnabled(flag);
		sil.setEnabled(flag);
		
	}
	class ListActionListener implements ActionListener{
		IdariMemurDersDuzenleSil pnl;
		ArrayList<Ders> dersList;
		DefaultListModel<Ders> listModel;
		public ListActionListener(IdariMemurDersDuzenleSil p) {
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
			
			Dimension screen = pnl.getToolkit().getScreenSize();
			
			lcr.setHorizontalAlignment(SwingConstants.CENTER);;
			lcr.setVerticalAlignment(SwingConstants.CENTER);;
			
			pane.add(tf);
			pane.add(sc);
			f.setSize(screen.width/2,screen.height/2);
			f.setLocation(screen.width/2-f.getWidth()/2,screen.height/2-f.getHeight()/2);
			f.setVisible(true);
			tf.setSize(pane.getWidth(),pane.getHeight()/6);
			tf.setLocation(0, 0);
			
			ArrayList<Ders> dersList=null ;
			try {
				dersList= FileHandler.dersleriGetir();
			}catch(IOException e2) {
				f.dispose();
				JOptionPane.showMessageDialog(pnl, "Veritabaný Hatasý ! Veritabanýndaki Veriye Ulaþýlamýyor Lütfen Daha Sonra Tekrar Deneyiniz.","Hata!",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			this.dersList=dersList;
			this.listModel=listModel;
			
			
			for(int i=0;i<dersList.size();i++) {
				listModel.addElement(dersList.get(i));
			}
			
			
			ListActionListener ths=this;
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
						pnl.setAllEnabled(true);
						
						pnl.tfAd.setText(d.ad);
						pnl.tfBolum.setText(d.bolum);
						pnl.tfDerslik.setText(d.derslik);						
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

interface Etkinlestirilebilir {
	void setAllEnabled(boolean flag);
}