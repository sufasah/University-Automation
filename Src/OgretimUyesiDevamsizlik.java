import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
public class OgretimUyesiDevamsizlik extends JPanel{
	private static final long serialVersionUID = -1460853972850015374L;
	JComboBox<String> dersler,ogrenciler;
	JTable takvim;
	JTableHeader hdr;
	JLabel donemtoplam,aytoplam,secilengun;
	JPanel gosterge;
	JButton kaydet;
	ArrayList<Ders> dersList;
	ArrayList<String> tarihList;
	ArrayList<Ogrenci> ogrenciList;
	HashSet<String> degisenSet;
	OgretimUyesi ou;
	int tiklananAy=-1;
	int tiklananSutun=-1;
	int tiklananSatir=-1;
	public OgretimUyesiDevamsizlik(OgretimUyesi ou) {
		super();
		
		setLayout(null);
		
		this.ou=ou;
		
		tarihList=new ArrayList<String>();	
		dersList= new ArrayList<Ders>();
		degisenSet=new HashSet<String>();
		ogrenciList=new ArrayList<Ogrenci>();
			try {
				dersList=FileHandler.verilenDersleriGetir(ou.tcKimlik);
			} catch (IOException e) {
				dersList.add(new Ders("Verilere Ulasilamiyor -"," - Daha Sonra Tekrar Giriþ Yapýnýz","",""));
			}
			
		dersler = new JComboBox<String>(new String[] {"--- Devamsýzlýðý Görüntülenecek Dersi Seçiniz ---"}) ;
			for(int i=0;i<dersList.size();i++) {
				Ders d= dersList.get(i);
				String yazi=d.kod+" | "+d.ad;
				dersler.insertItemAt(yazi, i+1);
			}
			OuDerslerItemListener dIL = new OuDerslerItemListener(this);
			dersler.addItemListener(dIL);
			dersler.setFocusable(false);
			dersler.setRequestFocusEnabled(false);
			dersler.setBackground(new Color(220,220,250));
		
		ogrenciler = new JComboBox<String>(new String[] {"--- Devamsýzlýðý Görüntülenecek Ögrenciyi Seçiniz ---"}) ;
			for(int i=0;i<ogrenciList.size();i++) {
				Ogrenci d= ogrenciList.get(i);
				String yazi=d.tcKimlik+" | "+d.ad+" "+d.soyad;
				ogrenciler.insertItemAt(yazi, i+1);
			}
			OuOgrencilerItemListener oIL = new OuOgrencilerItemListener(this);
			ogrenciler.setFocusable(false);
			ogrenciler.setRequestFocusEnabled(false);
			ogrenciler.setBackground(new Color(220,220,250));
			ogrenciler.setEnabled(false);
			ogrenciler.addItemListener(oIL);
				
			
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

		aytoplam=new JLabel("Herhangi Bir Ders ve Öðrenci Seçip Herhangi Bir Ay'a Týklayýnýz.");
			aytoplam.setFont(yazilar);
		
		donemtoplam= new JLabel("Herhangi Bir Ders ve Herhangi Bir Öðrenci Seçiniz.");
			donemtoplam.setFont(yazilar);
		
		gosterge=new JPanel();
			gosterge.setBackground(new Color(245,230,205));
			gosterge.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,new Color(170,150,150),new Color(170,150,150)));
			
		secilengun= new JLabel("Herhangi Bir Gün Seçin(Öðrenci Seçilmiþ Olmalýdýr)");
			secilengun.setFont(yazilar);

		kaydet = new JButton("Kaydet");
			kaydet.setFont(yazilar);
			kaydet.setEnabled(false);
			
		OuKaydetActionListener kaydetAL= new OuKaydetActionListener(this);
			kaydet.addActionListener(kaydetAL);
			
		OuTakvimMouseListener takvimML = new OuTakvimMouseListener(this);
		
		hdr.addMouseListener(takvimML);	
		
		takvim.addMouseListener(takvimML);
			
		add(kaydet);
		add(secilengun);
		add(ogrenciler);
		add(gosterge);
		add(donemtoplam);
		add(aytoplam);
		add(dersler);
		add(hdr);
		add(takvim);
	}
	class CustomRenderer extends DefaultTableCellRenderer
	{
		OgretimUyesiDevamsizlik pnl;
		public CustomRenderer(OgretimUyesiDevamsizlik p) {
			pnl=p;
		}
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        JLabel c =(JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        if(pnl.dersler.getSelectedIndex()!=0 && pnl.ogrenciler.getSelectedIndex()!=0 ) {
		        if(column==pnl.tiklananSutun&&row==pnl.tiklananSatir && !(pnl.tiklananSatir==10 &&pnl.tiklananSutun%3!=0)) {
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
		        		if(((String)pnl.takvim.getValueAt(row, column)).equals(""))
		        			c.setBackground(Color.red);
		        		else
		        			c.setBackground(Color.green);
		        		c.setBorder(pnl.gosterge.getBorder());
		        	}
		        	else {
		        		c.setBackground(pnl.takvim.getBackground());
		        		c.setBorder(null);
		        	}
		        }
	        }
	        return c;
	    }
	}
}

