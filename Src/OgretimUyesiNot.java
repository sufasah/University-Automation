import javax.swing.*;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class OgretimUyesiNot extends JPanel{
	private static final long serialVersionUID = -1460853972850015374L;
	JComboBox<String> dersler,ogrenciler;
	JLabel lVize,lVize2,lFinal;
	JTextField vize,vize2,finall;
	JButton kaydet;
	ArrayList<Ders> vDersList;
	ArrayList<Ogrenci> ogrenciList;
	String not;
	OgretimUyesi ou;
	public OgretimUyesiNot(OgretimUyesi ou) {
		super();
		
		setLayout(null);
		
		this.ou=ou;
		
		vDersList= new ArrayList<Ders>();
		try {
			vDersList=FileHandler.verilenDersleriGetir(ou.tcKimlik);
		} catch (IOException e) {
			vDersList.add(new Ders("Verilere Ulasilamiyor -"," - Daha Sonra Tekrar Giriþ Yapýnýz","",""));
		}
		
		dersler = new JComboBox<String>(new String[] {"--- Öðrencileri Görüntülenecek Dersi Seçiniz ---"}) ;
			for(int i=0;i<vDersList.size();i++) {
				Ders d= vDersList.get(i);
				String yazi=d.kod+" | "+d.ad;
				dersler.insertItemAt(yazi, i+1);
			}
			OuNotDerslerItemListener dIL = new OuNotDerslerItemListener(this);
			dersler.addItemListener(dIL);
			dersler.setFocusable(false);
			dersler.setRequestFocusEnabled(false);
			dersler.setBackground(new Color(220,220,250));
		
		ogrenciler = new JComboBox<String>(new String[] {"--- Notu Görüntülenecek Ögrenciyi Seçiniz ---"}) ;
			ogrenciler.setFocusable(false);
			ogrenciler.setRequestFocusEnabled(false);
			ogrenciler.setBackground(new Color(220,220,250));
			OuNotOgrencilerItemListener oIL = new OuNotOgrencilerItemListener(this);
			ogrenciler.addItemListener(oIL);
			ogrenciler.setEnabled(false);
		
		Font yazilar= new Font("ArialBlack",Font.ITALIC|Font.PLAIN|Font.BOLD,15);
			
		lVize= new JLabel("Vize 1: ");
			lVize.setEnabled(false);
		lVize2= new JLabel("Vize 2: ");
			lVize2.setEnabled(false);
		lFinal= new JLabel("Final: ");
			lFinal.setEnabled(false);
			
		
		vize= new JTextField();
			vize.setEnabled(false);
		vize2=new JTextField();
			vize2.setEnabled(false);
		finall= new JTextField();
			finall.setEnabled(false);
			
		final Color tfcolor=new Color(220,230,255);
		vize.setBackground(tfcolor);
		vize2.setBackground(tfcolor);
		finall.setBackground(tfcolor);
		
		kaydet = new JButton("Kaydet");
			kaydet.setFont(yazilar);
			kaydet.setEnabled(false);
		OuNotKaydetActionListener kaydetAL= new OuNotKaydetActionListener(this);
			kaydet.addActionListener(kaydetAL);
			
		add(lVize);
		add(lVize2);
		add(lFinal);
		add(vize);
		add(vize2);
		add(finall);
		add(kaydet);
		add(ogrenciler);
		add(dersler);
	}
	
}