import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
public class OgretimUyesiDersProgrami extends JPanel{

	private static final long serialVersionUID = 2985577606752032485L;
	JTable program;
	JTableHeader hdr;
	ArrayList<Ders> dersList;
	ArrayList<Vakit> vakitList;
	OgretimUyesi ou;
	public OgretimUyesiDersProgrami(OgretimUyesi ou) {
		super();
		
		setLayout(null);
		
		this.ou=ou;
		
		vakitList=new ArrayList<Vakit>();
		dersList=new ArrayList<Ders>();
		
		try {
			dersList= FileHandler.verilenDersleriGetir(ou.tcKimlik);
		}catch (IOException e) {
			JOptionPane.showConfirmDialog(this, "Verilere Ulaþýlamýyor. Lütfen Daha Sonra Tekrar Giriþ Yapýnýz.","Hata!",JOptionPane.OK_OPTION);
		}
		
		{
		int i=0;
		while(i<dersList.size())
		{	
			try {
				ArrayList<String> part = FileHandler.dersProgramindakileriGetir(dersList.get(i).kod);
				for(int j=0;j<part.size();j++)
					vakitList.add(new Vakit(i,part.get(j)));
			}catch (IOException e) {
				JOptionPane.showConfirmDialog(this, "Verilere Ulaþýlamýyor. Lütfen Daha Sonra Tekrar Giriþ Yapýnýz.","Hata!",JOptionPane.OK_OPTION);	
			}
			i++;
		}
		}
		program= new JTable((new DefaultTableModel(11,8) {private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column) {return false;}}));
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
		for(int i=0;i<vakitList.size();i++) {
			String gun = vakitList.get(i).saatgun.substring(5).toUpperCase();
			String saat= vakitList.get(i).saatgun.substring(0,5);
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
			program.setValueAt(dersList.get(vakitList.get(i).index).ad, satir, sutun);
		}
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
					Object val = program.getValueAt(row, col);
					if(val!=null && col>0) {
						Ders d=null;
						for(Iterator<Ders> i = dersList.iterator();i.hasNext();) {
							d=i.next();
							if(d.kod.equals(((String)val).split("-")[0]))
									break;
						}
						if(d!=null)
							JOptionPane.showMessageDialog(program.getParent().getParent(), "Ders Kodu: "+d.kod+"\nDers Adý: "+d.ad+"\nDers Bölümü: "+d.bolum+"\nDersin Dersliði: "+d.derslik,"DERS BÝLGÝSÝ",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		add(hdr);
		add(program);
		
	}
}
