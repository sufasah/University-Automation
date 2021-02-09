import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
public class OgrenciNot extends JPanel{
	
	private static final long serialVersionUID = 4810669376618505342L;
	JTableHeader hdr;
	JTable notlar;
	ArrayList<String> notList;
	ArrayList<Ders> dersList;
	Ogrenci ogr;
	public OgrenciNot(Ogrenci ogr) {
		super();
		
		setLayout(null);
		
		this.ogr=ogr;
		
		notList=new ArrayList<String>();
		dersList=new ArrayList<Ders>();
		try {
			dersList=FileHandler.alinanDersleriGetir(ogr.tcKimlik);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this, "Verilere Ulaþýlamýyor. Lütfen Daha Sonra Tekrar Giriþ Yapýnýz.","Hata!",JOptionPane.OK_OPTION);
		}
		{
		int i=0;
		while(i<dersList.size()) {
			try {
				notList.add(FileHandler.notGetir(ogr.tcKimlik, dersList.get(i).kod));
				if(notList.get(i).equals("__"))
				{
					notList.remove(i);
					dersList.remove(i);
					continue;
				}
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(this, "Verilere Ulaþýlamýyor. Lütfen Daha Sonra Tekrar Giriþ Yapýnýz.","Hata!",JOptionPane.OK_OPTION);
			}catch(KayitBulunamadi e2) {
				dersList.remove(i);
				continue;
			}
			i++;
		}
		}
		notlar = new JTable((new DefaultTableModel(dersList.size(),6) {private static final long serialVersionUID = 5092635188716304712L;
		public boolean isCellEditable(int row, int column) {return false;}}));
		notlar.setBackground(new Color(250,210,170));
		notlar.setForeground(new Color(70, 70,70));
		notlar.setCellSelectionEnabled(false);
		notlar.setRequestFocusEnabled(false);
		notlar.setFont(new Font("ArialBlack", Font.PLAIN|Font.BOLD|Font.ITALIC, 15));
		for(int i=0;i<dersList.size();i++)
		{
			notlar.setValueAt(dersList.get(i).kod, i, 0);
			notlar.setValueAt(dersList.get(i).ad, i, 1);
			String not=notList.get(i);
			int lastIndex=-1;
			int col=2;
			for(int j=0;j<not.length();j++) {
				if(not.charAt(j)=='_') {
					String parca=not.substring(lastIndex+1, j);
					notlar.setValueAt(parca, i, col);
					col++;
					lastIndex=j;
				}
			}
			String parca=not.substring(lastIndex+1, not.length());
			notlar.setValueAt(parca, i, col++);
			
			String v1=(String)notlar.getValueAt(i, 2),v2=(String)notlar.getValueAt(i, 3),f=(String)notlar.getValueAt(i, 4);
			if(!v1.equals("")&&!v2.equals("")&&!f.equals(""))
				notlar.setValueAt(String.format("%.2f",Float.valueOf(v1)*20/100+Float.valueOf(v2)*30/100+Float.valueOf(f)*50/100), i, col);
		}
		
	
	hdr=notlar.getTableHeader();
		hdr.setReorderingAllowed(false);
		hdr.setBackground(new Color(220,190,170));
		hdr.setFont(new Font("ArialBlack", Font.BOLD|Font.ITALIC, 20));
	TableColumn c0 = hdr.getColumnModel().getColumn(0);
	c0.setHeaderValue("Kod");
	TableColumn c1 = hdr.getColumnModel().getColumn(1);
	c1.setHeaderValue("Ders Adý");
	TableColumn c2 = hdr.getColumnModel().getColumn(2);
	c2.setHeaderValue("Vize-1");
	TableColumn c3 = hdr.getColumnModel().getColumn(3);
	c3.setHeaderValue("Vize-2");
	TableColumn c4 =hdr.getColumnModel().getColumn(4);
	c4.setHeaderValue("Final");
	TableColumn c5 = hdr.getColumnModel().getColumn(5);
	c5.setHeaderValue("Not");
	
	add(hdr);
	add(notlar);
	}
}
