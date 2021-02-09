import java.awt.Color;
import java.awt.Component;
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
import java.util.HashSet;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
public class IdariMemurProgramEkleSil extends JPanel{
	private static final long serialVersionUID = -500027701947579662L;
	IdariMemurForm fr;
	JTable program;
	JTableHeader hdr;
	JButton ders,kaydet;
	Ders seciliDers;
	ArrayList<String> vakitList;
	int tiklananSutun=-1;
	int tiklananSatir=-1;
	JPanel gosterge;
	HashSet<String> degisenSet;
	IdariMemur im;
	IdariMemurProgramEkleSil(IdariMemur im,IdariMemurForm form){
		super();
		this.im=im;
		fr=form;
		setLayout(null);
		
		vakitList=new ArrayList<String>();
		
		kaydet= new JButton("Kaydet");
		
		gosterge=new JPanel();
			gosterge.setBackground(new Color(245,230,205));
			gosterge.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,new Color(170,150,150),new Color(170,150,150)));
		
		degisenSet=new HashSet<String>();
		
		ders= new JButton("Dersi Seçmek Ýçin Týklayýnýz.");
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
		DefaultTableModel dtm = new DefaultTableModel(11,8) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		program=new JTable(dtm);
		program.setBackground(new Color(250,210,170));
		program.setForeground(new Color(70, 70,70));
		program.setCellSelectionEnabled(false);
		program.setRequestFocusEnabled(false);
		program.setFocusable(false);
		program.setFont(new Font("ArialBlack", Font.PLAIN|Font.BOLD|Font.ITALIC, 15));
		
		program.setValueAt("06:00-06:45", 0, 0);
		program.setValueAt("07:00-07:45", 1, 0);
		program.setValueAt("08:00-08:45", 2, 0);
		program.setValueAt("09:00-09:45", 3, 0);
		program.setValueAt("10:00-10:45", 4, 0);
		program.setValueAt("11:00-11:45", 5, 0);
		program.setValueAt("12:00-12:45", 6, 0);
		program.setValueAt("13:00-13:45", 7, 0);
		program.setValueAt("14:00-14:45", 8, 0);
		program.setValueAt("15:00-15:45", 9, 0);
		program.setValueAt("16:00-16:45", 10, 0);
		program.setDefaultRenderer(program.getColumnClass(0), new CustomRenderer(this));
		
	hdr = program.getTableHeader();
		hdr.setReorderingAllowed(false);
		hdr.setBackground(new Color(220,190,170));
		hdr.setFont(new Font("ArialBlack", Font.BOLD|Font.ITALIC, 20));
	

	TableColumn c0 = hdr.getColumnModel().getColumn(0);
	c0.setMaxWidth(130);
	c0.setPreferredWidth(130);
	c0.setHeaderValue("SAATLER");
	TableColumn c1 = hdr.getColumnModel().getColumn(1);
	c1.setHeaderValue("PAZARTESÝ");
	TableColumn c2 = hdr.getColumnModel().getColumn(2);
	c2.setHeaderValue("SALI");
	TableColumn c3 = hdr.getColumnModel().getColumn(3);
	c3.setHeaderValue("ÇARÞAMBA");
	TableColumn c4 =hdr.getColumnModel().getColumn(4);
	c4.setHeaderValue("PERÞEMBE");
	TableColumn c5 = hdr.getColumnModel().getColumn(5);
	c5.setHeaderValue("CUMA");
	TableColumn c6 = hdr.getColumnModel().getColumn(6);
	c6.setHeaderValue("CUMARTESÝ");
	TableColumn c7 = hdr.getColumnModel().getColumn(7);
	c7.setHeaderValue("PAZAR");
	
	program.addMouseListener(new MouseAdapter() {
		int fRow=-1,fCol=-1;
		public void mousePressed(MouseEvent e) {
			fRow=program.rowAtPoint(e.getPoint());
			fCol=program.columnAtPoint(e.getPoint());
		}
		public void mouseReleased(MouseEvent e) {
			int row=program.rowAtPoint(e.getPoint());
			int col=program.columnAtPoint(e.getPoint());
			if(row==fRow&&col==fCol)
			{
				Object o=program.getValueAt(row, col);
				if(o!=null&&o.getClass()==Ders.class) {
					Ders d =(Ders)o;
					JOptionPane.showMessageDialog(program.getParent().getParent(), "Ders Kodu: "+d.kod+"\nDers Adý: "+d.ad+"\nDers Bölümü: "+d.bolum+"\nDersin Dersliði: "+d.derslik,"DERS BÝLGÝSÝ",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	});
	
		ImKaydetActionListener kaydetAL= new ImKaydetActionListener(this);
		kaydet.addActionListener(kaydetAL);
		
		ImTakvimMouseListener takvimML = new ImTakvimMouseListener(this);
		
		hdr.addMouseListener(takvimML);	
		
		program.addMouseListener(takvimML);

	
		kaydet.setEnabled(false);
		
		add(hdr);
		add(ders);
		add(kaydet);
		add(program);
	
		
	}
	class DersListActionListener implements ActionListener{
		IdariMemurProgramEkleSil pnl;
		ArrayList<Ders> dersList;
		DefaultListModel<Ders> listModel;
		public DersListActionListener(IdariMemurProgramEkleSil p) {
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
						try {
							ArrayList<String> part = FileHandler.dersProgramindakileriGetir(seciliDers.kod);
							for(int j=0;j<part.size();j++)
								vakitList.add(part.get(j));
						}catch (IOException e2) {
							JOptionPane.showConfirmDialog(pnl, "Verilere Ulaþýlamýyor. Lütfen Daha Sonra Tekrar Giriþ Yapýnýz.","Hata!",JOptionPane.OK_OPTION);	
						}
						for(int i=0;i<vakitList.size();i++) {
							String gun = vakitList.get(i).substring(5).toUpperCase();
							String saat= vakitList.get(i).substring(0,5);
							int satir=Integer.valueOf(saat.substring(0,2))-6 ;
							int sutun=0;
							if(gun.equals("PAZARTESÝ"))
								sutun=1;
							else if(gun.equals("SALI"))
								sutun=2;
							else if(gun.equals("ÇARÞAMBA"))
								sutun=3;
							else if(gun.equals("PERÞEMBE"))
								sutun=4;
							else if(gun.equals("CUMA"))
								sutun=5;
							else if(gun.equals("CUMARTESÝ"))
								sutun=6;
							else if(gun.equals("PAZAR"))
								sutun=7;
							program.setValueAt(seciliDers.ad, satir, sutun);
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
	class CustomRenderer extends DefaultTableCellRenderer
	{
		IdariMemurProgramEkleSil pnl;
		public CustomRenderer(IdariMemurProgramEkleSil p) {
			pnl=p;
		}
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        JLabel c =(JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		        if(column==pnl.tiklananSutun&&row==pnl.tiklananSatir && !(pnl.tiklananSutun!=0)) {
		        	if(pnl.degisenSet.contains(Integer.toString(row)+"-"+Integer.toString(column))) {
	        			c.setBackground(Color.cyan);
		        	}
		        	else {
		        		c.setBackground(pnl.gosterge.getBackground());
		        	}
		        	c.setBorder(pnl.gosterge.getBorder());
		        }
		        else {
		        	if(pnl.degisenSet.contains(Integer.toString(row)+"-"+Integer.toString(column))) {
		        		if(pnl.program.getValueAt(row, column).equals("")) {
		        			c.setBackground(Color.red);
		        		}else {       			
		        			c.setBackground(Color.green);
		        		}
		        		c.setBorder(pnl.gosterge.getBorder());
		        	}
		        	else {
		        		c.setBackground(pnl.program.getBackground());
		        		c.setBorder(null);
		        	}
		        }
	        return c;
	    }
	}
}