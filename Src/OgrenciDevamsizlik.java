import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
public class OgrenciDevamsizlik extends JPanel{
	private static final long serialVersionUID = -1460853972850015374L;
	JComboBox<String> dersler;
	JTable takvim;
	JTableHeader hdr;
	JLabel donemtoplam,aytoplam,secilengun;
	JPanel gosterge;
	ArrayList<Ders> dersList;
	ArrayList<String> tarihList;
	Ogrenci ogr;
	int tiklananAy=-1;
	int tiklananSutun=-1;
	int tiklananSatir=-1;
	public OgrenciDevamsizlik(Ogrenci ogr) {
		super();
		
		setLayout(null);
		
		this.ogr=ogr;
		
		tarihList=new ArrayList<String>();
		dersList=new ArrayList<Ders>();
			try {
				dersList=FileHandler.alinanDersleriGetir(ogr.tcKimlik);
			} catch (IOException e) {
				dersList.add(new Ders("Verilere Ulasilamiyor -"," - Daha Sonra Tekrar Giriþ Yapýnýz","",""));
			}
			
		dersler = new JComboBox<String>(new String[] {"--- Devamsýzlýðý Görüntülenecek Dersi Seçiniz ---"}) ;
			for(int i=0;i<dersList.size();i++) {
				Ders d= dersList.get(i);
				String yazi=d.kod+" | "+d.ad;
				dersler.insertItemAt(yazi, i+1);
			}
			DerslerItemListener dIL = new DerslerItemListener(this);
			dersler.setFocusable(false);
			dersler.setRequestFocusEnabled(false);
			dersler.setBackground(new Color(220,220,250));
			dersler.addItemListener(dIL);
			
		takvim = new JTable((new DefaultTableModel(11,27) {private static final long serialVersionUID = 7751702834321687095L;
		public boolean isCellEditable(int row, int column) {return false;}}));
			takvim.setBackground(new Color(230,230,230));
			takvim.setCellSelectionEnabled(false);
			takvim.setRequestFocusEnabled(false);
			takvim.setFocusable(false);
			takvim.setFont(new Font("ArialBlack", Font.BOLD, 20));
			takvim.setDefaultRenderer(takvim.getColumnClass(0), new CustomRenderer(this));
			
			
		TableColumn c0 = new TableColumn();
			c0.setResizable(false);
			c0.setHeaderValue("EYLÜL");
		TableColumn c1 = new TableColumn();
			c1.setResizable(false);
			c1.setHeaderValue("EKÝM");
		TableColumn c2 = new TableColumn();
			c2.setResizable(false);
			c2.setHeaderValue("KASIM");
		TableColumn c3 = new TableColumn();
			c3.setResizable(false);
			c3.setHeaderValue("ARALIK");
		TableColumn c4 = new TableColumn();
			c4.setResizable(false);
			c4.setHeaderValue("OCAK");
		TableColumn c5 = new TableColumn();
			c5.setResizable(false);
			c5.setHeaderValue("ÞUBAT");
		TableColumn c6 = new TableColumn();
			c6.setResizable(false);
			c6.setHeaderValue("MART");
		TableColumn c7 = new TableColumn();
			c7.setResizable(false);
			c7.setHeaderValue("NÝSAN");
		TableColumn c8 = new TableColumn();
			c8.setResizable(false);
			c8.setHeaderValue("MAYIS");
		
		
		hdr=new JTableHeader();
			hdr.getColumnModel().addColumn(c0);
			hdr.getColumnModel().addColumn(c1);
			hdr.getColumnModel().addColumn(c2);
			hdr.getColumnModel().addColumn(c3);
			hdr.getColumnModel().addColumn(c4);
			hdr.getColumnModel().addColumn(c5);
			hdr.getColumnModel().addColumn(c6);
			hdr.getColumnModel().addColumn(c7);
			hdr.getColumnModel().addColumn(c8);
			hdr.getColumnModel().setColumnSelectionAllowed(false);
			hdr.setReorderingAllowed(false);
			hdr.setBackground(new Color(210,210,210));
		
		Font yazilar= new Font("ArialBlack",Font.ITALIC|Font.PLAIN|Font.BOLD,15);
		aytoplam=new JLabel("Herhangi Bir Ders Seçip Herhangi Bir Ay'a Týklayýnýz.");
			aytoplam.setFont(yazilar);
		donemtoplam= new JLabel("Ders Seçiniz.");
			donemtoplam.setFont(yazilar);
		secilengun= new JLabel("Herhangi Bir Gün Seçin(Ders Seçilmiþ Olmalýdýr)");
			secilengun.setFont(yazilar);
		gosterge=new JPanel();
			gosterge.setBackground(new Color(245,230,205));
			gosterge.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,new Color(170,150,150),new Color(170,150,150)));
		MouseAdapter aySec = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				tiklananAy= hdr.getColumnModel().getColumnIndexAtX(e.getX());
				String Ay=null;
				int adet=0;
				if(tiklananAy==0)
					Ay="EYLÜL";
				else if(tiklananAy==1)
					Ay="EKÝM";
				else if(tiklananAy==2)
					Ay="KASIM";
				else if(tiklananAy==3)
					Ay="ARALIK";
				else if(tiklananAy==4)
					Ay="OCAK";
				else if(tiklananAy==5)
					Ay="ÞUBAT";
				else if(tiklananAy==6)
					Ay="MART";
				else if(tiklananAy==7)
					Ay="NÝSAN";
				else if(tiklananAy==8)
					Ay="MAYIS";
				for(int i=0;i<tarihList.size();i++)
					if(Ay.equals(tarihList.get(i).substring(2)))
						adet++;
				if(dersler.getSelectedIndex()!=0) {
					tiklananSatir=takvim.rowAtPoint(e.getPoint());
					tiklananSutun=takvim.columnAtPoint(e.getPoint());
					if(!(tiklananSatir==10 &&tiklananSutun%3!=0)&& e.getSource().getClass()!=hdr.getClass()){
						secilengun.setText(Integer.toString(1+(tiklananSatir*3+tiklananSutun%3))+" "+Ay);
						if(e.getButton()==MouseEvent.BUTTON3)
							takvim.repaint();
					}
					aytoplam.setText(Ay+" Ayýnda Toplamda "+adet+" Adet Devamsýzlýðýnýz Bulunmaktadýr.");				
					gosterge.setSize(hdr.getColumnModel().getColumn(tiklananAy).getWidth(),hdr.getHeight()/2);
					int genislik=0;
					for(int i=0;i<tiklananAy;i++)
						genislik+=hdr.getColumnModel().getColumn(i).getWidth();
					gosterge.setLocation(genislik, hdr.getY()-hdr.getHeight()/2);
				}
			}
		};
		hdr.addMouseListener(aySec);	
		
		takvim.addMouseListener(aySec);
			
		add(gosterge);
		add(donemtoplam);
		add(aytoplam);
		add(dersler);
		add(hdr);
		add(takvim);
		add(secilengun);
	}
	class CustomRenderer extends DefaultTableCellRenderer
	{
		OgrenciDevamsizlik pnl;
		public CustomRenderer(OgrenciDevamsizlik p) {
			pnl=p;
		}
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        JLabel c =(JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        if(pnl.dersler.getSelectedIndex()!=0) {
		        if(column==pnl.tiklananSutun&&row==pnl.tiklananSatir && !(pnl.tiklananSatir==10 &&pnl.tiklananSutun%3!=0)) {
	        		c.setBackground(pnl.gosterge.getBackground());
		        	c.setBorder(pnl.gosterge.getBorder());
		        }
		        else {
	        		c.setBackground(pnl.takvim.getBackground());
	        		c.setBorder(null);
		        }
	        }
	        return c;
	    }
	}
}
