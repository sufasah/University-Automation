import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class FileHandler {

	public static void profilFotografiKaydet(String tc,File photo) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\"+tc+".png";
		File f= new File(filePath);
		if(f.exists())f.delete();
		dosyaAyarla(f);

		FileOutputStream o = new FileOutputStream(f);
		FileInputStream i = new FileInputStream(photo);
		int gln=i.read();
		while(gln!=-1) {
			o.write((byte)gln);
			gln=i.read();
		}
		i.close();
		o.close();
	}
	public static void profilFotografiSil(String tc) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\"+tc+".png";
		File f= new File(filePath);
		if(f.exists())f.delete();
	}
	public static ImageIcon profilFotografiGetir(String tc) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\profil fotograflar\\"+tc+".png";
		File f= new File(filePath);
		if(f.exists()) {
			return new ImageIcon(f.getAbsolutePath());
		}
		return new ImageIcon(filePath.substring(0,filePath.lastIndexOf('\\'))+"\\default.png");
	}
	
	public static Ogrenci dersiAlaniGetir(String kod,String tc) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersi alanlar\\"+kod+".da";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			satirGetir(f, kod);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi("Kodu "+kod +" Olan Dersi "+tc+" Tc Kimlik Numaralý Öðrenci Zaten Almamaktadýr.");
		}
		try {
			return ogrenciGetir(tc);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi(tc+" Tc Kimlik Numaralý Öðrenci Kayitlarda Bulunamadi");
		}
	}
	
	
	public static ArrayList<Ogrenci> dersiAlanlariGetir(String kod) throws IOException {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersi alanlar\\"+kod+".da";
		File f= new File(filePath);
		dosyaAyarla(f);
		ArrayList<Ogrenci> ogrenciler= new ArrayList<Ogrenci>();
		FileReader i = new FileReader(f);
		StringBuilder g = new StringBuilder();
		while(satirAl(i, g)) {
			String gelen=g.toString();
			try{ogrenciler.add(ogrenciGetir(gelen));}catch(KayitBulunamadi e) {}
		}
		i.close();
		return ogrenciler;
	}
	
	public static ArrayList<String> dersProgramindakileriGetir(String kod) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\ders programi\\"+kod+".dp";
		File f= new File(filePath);
		dosyaAyarla(f);
		ArrayList<String> dersVakitleri =new ArrayList<String>();
		FileReader i = new FileReader(f);
		StringBuilder gelen=new StringBuilder();
		while(satirAl(i,gelen)) {
			dersVakitleri.add(gelen.toString());
		}
		i.close();
		return dersVakitleri;
	}
	
	public static void dersProgramindakiniDuzenle(String kod,String saatgun,String yenisaatgun) throws IOException,KayitBulunamadi,KayitVar{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\ders programi\\"+kod+".dp";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			satirSil(f, saatgun);
			dersPrograminaKaydet(kod, yenisaatgun);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi(kod+" Kodlu Dersin "+saatgun.substring(5)+" Günü "+saatgun.substring(0,5)+" Saatinde Ders Programýnda Kaydý Bulunamadi.");
		}
		catch (KayitVar e2) {
			try {dersPrograminaKaydet(kod, saatgun);}catch(KayitVar e) {}
			throw new KayitVar(kod+" Kodlu Dersin "+saatgun.substring(5)+" Günü "+yenisaatgun.substring(0,5)+" Saatinde Ders Programýnda Zaten Kaydý Bulunmaktadýr.");
		}
		
	}
	
	
	public static void dersProgramindanSil(String kod,String saatgun) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\ders programi\\"+kod+".dp";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			FileHandler.satirSil(f, saatgun);
		}catch(KayitBulunamadi e) {
			throw new KayitBulunamadi(kod+" Kodlu Dersin "+saatgun.substring(5)+" Günü "+saatgun.substring(0,5)+" Saatinde Ders Programýnda Kaydý Bulunamadi.");
		}
	}
	public static void dersProgramindakileriSil(String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\ders programi\\"+kod+".dp";
		File f= new File(filePath);
		dosyaAyarla(f);
		f.delete();
	}
	
	
	public static void dersPrograminaKaydet(String kod,String saatgun) throws IOException,KayitVar{
		try {
			dersProgramindanGetir(kod,saatgun);
			throw new KayitVar(kod+" Kodlu Dersin "+saatgun.substring(5)+" Günü "+saatgun.substring(0,5)+" Saatinde Ders Programýnda Zaten Kaydý Bulunmaktadýr.");
		}catch(KayitBulunamadi e) {}
		String filePath=System.getProperty("user.dir")+"\\veritabani\\ders programi\\"+kod+".dp";
		File f= new File(filePath);
		dosyaAyarla(f);
		FileWriter out = null;
		out = new FileWriter(f,true);
		out.append(saatgun+"\r\n");
		out.close();
	}
	public static String dersProgramindanGetir(String kod,String saatgun) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\ders programi\\"+kod+".dp";
		File f= new File(filePath);
		dosyaAyarla(f);
		
		try {
			return satirGetir(f, saatgun);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi(kod+" Kodlu Dersin "+saatgun.substring(5)+" Günü "+saatgun.substring(0,5)+" Saatinde Ders Programýnda Kaydý Bulunamadi.");
		}
	}
	public static ArrayList<String> devamsizlikTarihleriniGetir(String tc,String Kod) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\devamsizlik\\"+tc+".dev";
		File f= new File(filePath);
		dosyaAyarla(f);
		FileReader i = new FileReader(f);
		StringBuilder gelen=new StringBuilder();
		ArrayList<String> gunayList=new ArrayList<String>();
		while(satirAl(i,gelen)) {
			String g[] = gelen.toString().split(" ");
			if(g[0].equals(Kod))
				gunayList.add(g[1]);
		}
		i.close();
		return gunayList;
	}
	
	public static boolean devamsizlikVarMi(String tc,String kod,String gunay) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\devamsizlik\\"+tc+".dev";
		File f= new File(filePath);
		dosyaAyarla(f);
		FileReader i = new FileReader(f);
		StringBuilder gelen=new StringBuilder();
		while(satirAl(i,gelen)) {
			String g[] = gelen.toString().split(" ");
			if(g[0].equals(kod)&&g[1].equals(gunay)) {
				i.close();
				return true;
			}
		}			
		i.close();
		return false;
	}
	
	
	public static void devamsizlikSil(String tc,String kod,String gunay ) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\devamsizlik\\"+tc+".dev";
		File f= new File(filePath);
		File fyeni= new File(filePath+".yeni");
		dosyaAyarla(f);
		FileReader i = new FileReader(f);
		FileWriter o= new FileWriter(fyeni);
		StringBuilder gelen=new StringBuilder();
		boolean buldu=false;
		while(satirAl(i,gelen)) {
			String g[] = gelen.toString().split(" ");
			if(g[0].equals(kod)&&g[1].equals(gunay))
				buldu=true;
			else
				o.write(gelen.toString()+"\r\n");
		}
		i.close();
		o.close();
		if(!buldu) {
			fyeni.delete();
			throw new KayitBulunamadi("Tc Kimlik Numarasý "+tc+" Olan Öðrencinin "+kod+" Kodlu Derste "+gunay+" Tarihinde Devamsýzlýk Kaydý Bulunamadi.");
		}
		f.delete();
		fyeni.renameTo(f);
	}
	public static void devamsizliklariSil(String tc) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\devamsizlik\\"+tc+".dev";
		File f= new File(filePath);
		dosyaAyarla(f);
		f.delete();
	}
	public static void derstekiTumDevamsizliklariSil(String kod) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\devamsizlik\\";
		File dir= new File(filePath);
		dosyaAyarla(dir);
		File[] files =dir.listFiles();
		for(int i=0;i<files.length;i++) {
			FileReader in = new FileReader(files[i]);
			File yeni = new File(files[i].getAbsoluteFile()+".yeni");
			FileWriter out = new FileWriter(yeni,true);
			StringBuilder gelen = new StringBuilder();
			while(satirAl(in,gelen)) {
				String splitted[]=gelen.toString().split(" ");
				if(!splitted[0].equals(kod))
					out.append(gelen.toString()+"\r\n");
			}
			in.close();
			out.close();
			files[i].delete();
			yeni.renameTo(files[i]);
		}
	}
	public static void derstekiTumNotlariSil(String kod) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\notlar\\";
		File dir= new File(filePath);
		dosyaAyarla(dir);
		File[] files =dir.listFiles();
		for(int i=0;i<files.length;i++) {
			FileReader in = new FileReader(files[i]);
			File yeni = new File(files[i].getAbsoluteFile()+".yeni");
			FileWriter out = new FileWriter(yeni,true);
			StringBuilder gelen = new StringBuilder();
			while(satirAl(in,gelen)) {
				String splitted[]=gelen.toString().split(" ");
				if(!splitted[0].equals(kod))
					out.append(gelen.toString()+"\r\n");
			}
			in.close();
			out.close();
			files[i].delete();
			yeni.renameTo(files[i]);
		}
	}
	public static void verilenDerslerdenSil(String kod) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\verilen dersler\\";
		File dir= new File(filePath);
		dosyaAyarla(dir);
		File[] files =dir.listFiles();
		for(int i=0;i<files.length;i++) {
			try {
				satirSil(files[i], kod);
			} catch (KayitBulunamadi e) {}
		}
	}
	public static void devamsizlikKaydet(String tc,String kod,String gunay) throws IOException,KayitVar{
		if(devamsizlikVarMi(tc, kod, gunay))
			throw new KayitVar(tc+" Tc Kimlik Numaralý Öðrencinin "+kod+" Kodlu Ders Ýçin "+gunay+" Tarihinde Devamsýzlýk Kaydý zaten Var.");
		String filePath=System.getProperty("user.dir")+"\\veritabani\\devamsizlik\\"+tc+".dev";
		File f= new File(filePath);
		dosyaAyarla(f);
		FileWriter out = null;
		out = new FileWriter(f,true);
		out.append(kod+" "+gunay+"\r\n");
		out.close();
	}
	public static String notGetir(String tc,String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\notlar\\"+tc+".n";
		File f= new File(filePath);
		dosyaAyarla(f);
		String not;
		try {
			not =satirGetir(f, kod);
			not=not.substring(not.lastIndexOf(" ")+1);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi("Tc Kimlik Numarasý "+tc+" Olan Öðrenci "+kod+" Kodlu Dersine Henüz Not Girilmemiþ.");
		}
		return not;
	}
	
	
	
	public static void notSil(String tc,String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\notlar\\"+tc+".n";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			FileHandler.satirSil(f, kod);
		}catch(KayitBulunamadi e) {
			throw new KayitBulunamadi("Tc Kimlik Numarasý "+tc+" Olan Öðrenciye "+kod+" Kodlu Dersine Henüz Not Girilmemiþ.");
		}

	}
	public static void notlariSil(String tc) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\notlar\\"+tc+".n";
		File f= new File(filePath);
		dosyaAyarla(f);
		f.delete();
	}
	public static void notKaydet(String tc,String kod,String not) throws IOException{
		try {
			notSil(tc, kod);
		}catch(KayitBulunamadi e) {}
		String filePath=System.getProperty("user.dir")+"\\veritabani\\notlar\\"+tc+".n";
		File f= new File(filePath);
		dosyaAyarla(f);
		FileWriter out = null;
		out = new FileWriter(f,true);
		out.append(kod+" "+not+"\r\n");
		out.close();
	}
	
	public static Ders verilenDersGetir(String tc,String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\verilen dersler\\"+tc+".vd";
		File f= new File(filePath);
		dosyaAyarla(f);
		String[] gelen;
		try {
			satirGetir(f, kod);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi("Tc Kimlik Numarasý "+tc+" Öðretim Üyesi "+kod+" Kodlu Dersi Vermemektedir.");
		}
		filePath=System.getProperty("user.dir")+"\\veritabani\\dersler.d";
		f= new File(filePath);
		try {
			gelen= satirGetir(f, kod).split(" ");
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi(kod+" Kodlu Ders Kayitlarda Bulunamadi");
		}
		return new Ders(gelen[0],gelen[1],gelen[2],gelen[3]);				
	}
	
	
	public static void verilenDersSil(String tc,String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\verilen dersler\\"+tc+".vd";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			FileHandler.satirSil(f, kod);
		}catch(KayitBulunamadi e) {
			throw new KayitBulunamadi("Tc Kimlik Numarasý "+tc+" Olan Öðretim Üyesi "+kod+" Kodlu Derse Zaten Kaydedilmemiþ.");
		}

	}
	public static void verilenDersleriSil(String tc) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\verilen dersler\\"+tc+".vd";
		File f= new File(filePath);
		dosyaAyarla(f);
		f.delete();

	}
	public static void verilenDersKaydet(String tc,String kod) throws IOException,KayitVar{
		try {
			verilenDersGetir(tc, kod);
			throw new KayitVar(tc+" Tc Kimlik Numaralý Öðretim Üyesi "+kod+" Kodlu Dersi Zaten Vermektedir.");
		}catch(KayitBulunamadi e) {}
		String filePath=System.getProperty("user.dir")+"\\veritabani\\verilen dersler\\"+tc+".vd";
		File f= new File(filePath);
		dosyaAyarla(f);
		
		FileWriter out = null;
		out = new FileWriter(f,true);
		out.append(kod+"\r\n");
		out.close();
	}
	
	public static ArrayList<Ders> verilenDersleriGetir(String tc) throws IOException {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\verilen dersler\\"+tc+".vd";
		File f= new File(filePath);
		dosyaAyarla(f);
		ArrayList<Ders> dersler = new ArrayList<Ders>();
		FileReader i = new FileReader(f);
		StringBuilder g = new StringBuilder();
		while(satirAl(i, g)) {
			String gelen=g.toString();
			try{dersler.add(dersGetir(gelen));}catch(KayitBulunamadi e) {}
		}
		i.close();
		return dersler;
	}
	
	public static ArrayList<Ders> alinanDersleriGetir(String tc) throws IOException {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\alinan dersler\\"+tc+".ad";
		File f= new File(filePath);
		dosyaAyarla(f);
		ArrayList<Ders> dersler = new ArrayList<Ders>();
		FileReader i = new FileReader(f);
		StringBuilder g = new StringBuilder();
		while(satirAl(i, g)) {
			String gelen=g.toString();
			try{dersler.add(dersGetir(gelen));}catch(KayitBulunamadi e) {}
		}
		i.close();
		return dersler;
	}
	
	public static Ders alinanDersGetir(String tc,String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\alinan dersler\\"+tc+".ad";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			satirGetir(f, kod);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi("Tc Kimlik Numarasý "+tc+" Olan Öðrenci "+kod+" Kodlu Dersi Almamaktadir.");
		}
		try {
			return dersGetir(kod);
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi(kod+" Kodlu Ders Kayitlarda Bulunamadi");
		}
	}
	
	
	public static void alinanDersSil(String tc,String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\alinan dersler\\"+tc+".ad";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			FileHandler.satirSil(f, kod);
			try {
			FileHandler.satirSil(new File(System.getProperty("user.dir")+"\\veritabani\\dersi alanlar\\"+kod+".da"), tc);
			}catch(KayitBulunamadi e) {}
		}catch(KayitBulunamadi e) {
			throw new KayitBulunamadi("Tc Kimlik Numarasý "+tc+" Olan Öðrenci "+kod+" Kodlu Derse Zaten Kaydedilmemiþ.");
		}

	}
	public static void alinanDersleriSil(String tc) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\alinan dersler\\"+tc+".ad";
		File f= new File(filePath);
		dosyaAyarla(f);
		{
			File dir = new File(System.getProperty("user.dir")+"\\veritabani\\dersi alanlar");
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++) {
				try {
					satirSil(files[i],tc );
				} catch (KayitBulunamadi e) {}
			}
		}
		f.delete();
	}
	public static void tumOgrencilerdenalinanDersiSil(String kod) throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersi alanlar\\"+kod+".da";
		File f= new File(filePath);
		dosyaAyarla(f);
		f.delete();
		ArrayList<Ogrenci> ogrenciler = dersiAlanlariGetir(kod);
		for(int i=0;i<ogrenciler.size();i++) {
			try{
				alinanDersSil(ogrenciler.get(i).tcKimlik, kod);
			}catch(KayitBulunamadi e) {}
		}
	}
	
	public static void alinanDersKaydet(String tc,String kod) throws IOException,KayitVar{
		try {
			alinanDersGetir(tc, kod);
			throw new KayitVar(tc+" Tc Kimlikli Öðrenci "+kod+" Kodlu Dersi Zaten Almaktadýr.");
		}catch(KayitBulunamadi e) {}
		String filePath=System.getProperty("user.dir")+"\\veritabani\\alinan dersler\\"+tc+".ad";
		File f= new File(filePath);
		dosyaAyarla(f);
		FileWriter out = new FileWriter(f,true);
		out.append(kod+"\r\n");
		out.close();
		
		filePath=System.getProperty("user.dir")+"\\veritabani\\dersi alanlar\\"+kod+".da";
		f= new File(filePath);
		dosyaAyarla(f);
		out = new FileWriter(f,true);
		out.append(tc+"\r\n");
		out.close();
	}
	
	public static void dersDuzenle(String eskiKod,Ders yeni) throws IOException, KayitBulunamadi {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersler.d";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			FileHandler.satirSil(f, eskiKod);
			FileHandler.dersKaydet(yeni);
		}catch(KayitVar e) {}
		catch(KayitBulunamadi e) {throw new KayitBulunamadi("Düzenlenecek Ders Bulunamadi.");}
	}
	public static void dersSil(String kod) throws IOException,KayitBulunamadi {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersler.d";
		File f= new File(filePath);
		dosyaAyarla(f);
		try {
			FileHandler.satirSil(f, kod);
		}catch(KayitBulunamadi e) {
			throw new KayitBulunamadi("Silinecek Ders Bulunamadi.");
		}
	}
	public static Ders dersGetir(String kod) throws IOException,KayitBulunamadi{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersler.d";
		File f= new File(filePath);
		dosyaAyarla(f);
		String[] gelen;
		try {
			gelen= satirGetir(f, kod).split(" ");
		}
		catch(KayitBulunamadi e){
			throw new KayitBulunamadi("Aranan Ders Kaydý Bulunamadi.");
		}
		return new Ders(gelen[0],gelen[1],gelen[2],gelen[3]);				
	}
	public static ArrayList<Ders> dersleriGetir() throws IOException{
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersler.d";
		File f= new File(filePath);
		dosyaAyarla(f);
		ArrayList<Ders> dersler=new ArrayList<Ders>();
		FileReader i = new FileReader(f);
		StringBuilder gelen=new StringBuilder();
		while(satirAl(i,gelen)) {
			String splitted[]= gelen.toString().split(" ");
			dersler.add(new Ders(splitted[0],splitted[1],splitted[2],splitted[3]));
		}
		i.close();
		return dersler;
	}
	
	public static void dersKaydet(Ders d) throws IOException,KayitVar{
		try {
			dersGetir(d.kod);
			throw new KayitVar("Bu Ders Zaten Kayitli.");
		}catch(KayitBulunamadi ke) {}
		String filePath=System.getProperty("user.dir")+"\\veritabani\\dersler.d";
		File f= new File(filePath);
		CharSequence veri=d.kod+" "+d.ad.replace(" ", "_")+" "+d.bolum.replace(" ", "_")+" "+d.derslik.replace(" ", "_");
		dosyaAyarla(f);
		
		FileWriter out = null;
		out = new FileWriter(f,true);
		out.append(veri+"\r\n");
		out.close();
	}
	
	public static boolean kayitliKullaniciMi(String tc,String pass) throws IOException {
		File f= new File(System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\kayitli kullanicilar.k");
		dosyaAyarla(f);
		String gelen;
		try {
			gelen=FileHandler.satirGetir(f, tc);
		}catch(KayitBulunamadi e) {
			return false;
		}
		if(pass.equals(gelen.substring(gelen.indexOf(" ")+1)))
			return true;
		return false;
		
	}
	public static Ogrenci ogrenciGetir(String tc) throws IOException,KayitBulunamadi {
		String filePath = System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogrenciler.k";
		File f=new File(filePath);
		dosyaAyarla(f);
		String gelen[];
		try {
			gelen=FileHandler.satirGetir(f, tc).split(" ");
		}catch(KayitBulunamadi e) {
			throw new KayitBulunamadi("Aranan Ogrenci Kayitlarda Bulunamadi.");
		}
		return new Ogrenci(gelen[2], gelen[3], gelen[0], Integer.valueOf(gelen[1]), gelen[4], gelen[7], gelen[6], gelen[5]);				
	}
	
	public static OgretimUyesi ogretimUyesiGetir(String tc) throws IOException,KayitBulunamadi {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogretim uyeleri.k";
		File f=new File(filePath);
		dosyaAyarla(f);
		String[] gelen;
		try {
			gelen=FileHandler.satirGetir(f, tc).split(" ");
		}catch(KayitBulunamadi e){
			throw new KayitBulunamadi("Aranan Ogretim Uyesi Kayitlarda Bulunamadi.");
		}
		return new OgretimUyesi(gelen[1], gelen[2], gelen[0],gelen[3], gelen[6], gelen[5], gelen[4], gelen[7]);				
	}
	public static ArrayList<OgretimUyesi> ogretimUyeleriniGetir() throws IOException {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogretim uyeleri.k";
		File f=new File(filePath);
		dosyaAyarla(f);
		ArrayList<OgretimUyesi> ogretimUyesiList=new ArrayList<OgretimUyesi>();
		FileReader i = new FileReader(f);
		StringBuilder sgelen= new StringBuilder();
		while(satirAl(i, sgelen)) {
			String gelen[] = sgelen.toString().split(" ");
			ogretimUyesiList.add(new OgretimUyesi(gelen[1], gelen[2], gelen[0],gelen[3], gelen[6], gelen[5], gelen[4], gelen[7]));
		}
		i.close();
		return ogretimUyesiList;
	}

	public static ArrayList<Ogrenci> ogrencileriGetir() throws IOException {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogrenciler.k";
		File f=new File(filePath);
		dosyaAyarla(f);
		ArrayList<Ogrenci> OgrenciList=new ArrayList<Ogrenci>();
		FileReader i = new FileReader(f);
		StringBuilder sgelen= new StringBuilder();
		while(satirAl(i, sgelen)) {
			String gelen[] = sgelen.toString().split(" ");
			OgrenciList.add(new Ogrenci(gelen[2], gelen[3], gelen[0], Integer.valueOf(gelen[1]), gelen[4], gelen[7], gelen[6], gelen[5]));
		}
		i.close();
		return OgrenciList;
	}
	public static IdariMemur idariMemurGetir(String tc) throws IOException,KayitBulunamadi {
		String filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\idari memurlar.k";
		File f=new File(filePath);
		dosyaAyarla(f);
		String[] gelen;
		try {
		gelen =FileHandler.satirGetir(f, tc).split(" ");
		}catch(KayitBulunamadi e) {
			throw new KayitBulunamadi("Aranan Idari Memur Kayitlarda Bulunamadi.");
		}
		return new IdariMemur(gelen[1],gelen[2],gelen[0],gelen[5],gelen[4],gelen[3]);
	}
	public static Kullanici kullaniciGetir(String tc) throws IOException,KayitBulunamadi{
		Kullanici res;
		try {res=(Kullanici)ogrenciGetir(tc);return res;}catch(KayitBulunamadi ke) {}
		try {res=(Kullanici)ogretimUyesiGetir(tc);return res;}catch(KayitBulunamadi ke) {}
		try {res=(Kullanici)idariMemurGetir(tc);return res;}catch(KayitBulunamadi ke) {
			throw new KayitBulunamadi("Aranan Kullanici Kayitlarda Bulunamadi.");
		}
	}
	public static ArrayList<Kullanici> kullanicilariGetir() throws IOException{
		ArrayList<Kullanici> list = new ArrayList<Kullanici>();
		String filePath = System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogrenciler.k";
		File f = new File(filePath);
		dosyaAyarla(f);
		FileReader i = new FileReader(f);
		StringBuilder gelen=new StringBuilder();
		while(satirAl(i, gelen)) {
			String[] splitted=gelen.toString().split(" ");
			list.add(new Ogrenci(splitted[2], splitted[3], splitted[0], Integer.valueOf(splitted[1]), splitted[4], splitted[7], splitted[6], splitted[5]));
		}
		i.close();
		filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogretim uyeleri.k";
		f= new File(filePath);
		i = new FileReader(f);
		while(satirAl(i, gelen)) {
			String[] splitted=gelen.toString().split(" ");
			list.add(new OgretimUyesi(splitted[1], splitted[2], splitted[0], splitted[3], splitted[6], splitted[5], splitted[4], splitted[7]));
		}
		i.close();
		filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\idari memurlar.k";
		f= new File(filePath);
		i = new FileReader(f);
		while(satirAl(i, gelen)) {
			String[] splitted=gelen.toString().split(" ");
			list.add(new IdariMemur(splitted[1], splitted[2], splitted[0], splitted[5], splitted[4], splitted[3]));
		}
		i.close();
		return list;
	}
	public static void kullaniciDuzenle(String eskiTc,Kullanici yeni) throws IOException,KayitUyusmazligi,KayitBulunamadi{
		if(kullaniciGetir(eskiTc).getClass()!=yeni.getClass() || !eskiTc.equals(yeni.tcKimlik)) {
			throw new KayitUyusmazligi("Kullanici Turleri veya Tc Kimlikleri Uyusmamaktadir.");
		}
		String filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\";
		File f;
		String tur=null;
		if(yeni.getClass()==Ogrenci.class) {
			filePath+="ogrenciler.k";
			tur="Ogrenci";
		}
		else if(yeni.getClass()==OgretimUyesi.class) {
			filePath+="ogretim uyeleri.k";
			tur="Ogretim Uyesi";
		}
		else if(yeni.getClass()==IdariMemur.class) {
			filePath+="idari memurlar.k";
			tur="Idari Memur";
		}
		
		f=new File(filePath);
		dosyaAyarla(f);
		
		try {
		FileHandler.satirSil(f, eskiTc);
		FileHandler.kullaniciKaydet(yeni);
		}catch(KayitVar e) {}
		catch(KayitBulunamadi e) {throw new KayitBulunamadi("Düzenlenecek "+tur+" Bulunamadi");}
	}
	public static void kullaniciSil(String tc) throws IOException,KayitBulunamadi {
		Kullanici k;
		try {
			k = kullaniciGetir(tc);
		}catch(KayitBulunamadi e) 
		{throw new KayitBulunamadi("Silinecek Kullanici Kayitlarda Bulunamadi");}
		String filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\";
		File f;
		if(k.getClass()==Ogrenci.class) {
			filePath+="ogrenciler.k";
			alinanDersleriSil(tc);
			devamsizliklariSil(tc);
			notlariSil(tc);
		}
		else if(k.getClass()==OgretimUyesi.class) {
			filePath+="ogretim uyeleri.k";
			verilenDersleriSil(tc);
		}
		else if(k.getClass()==IdariMemur.class){
			filePath+="idari memurlar.k";
		}
		f=new File(filePath);
		dosyaAyarla(f);
		
		satirSil(f, tc);
		profilFotografiSil(tc);
		loginSil(tc);
	}
	public static void kullaniciKaydet(Kullanici k) throws IOException,KayitVar{
		try {
			kullaniciGetir(k.tcKimlik);
			throw new KayitVar("Bu Kullanici Zaten Kayitli.");
		}catch(KayitBulunamadi ke) {}
		String filePath=null;
		File f;
		CharSequence veri=null;
		if(k.getClass()==Ogrenci.class) {
			filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogrenciler.k";
			Ogrenci o=(Ogrenci)k;
			veri=(o.tcKimlik+" "+o.no+" "+o.ad+" "+o.soyad+" "+o.bolum+" "+o.telefon+" "+o.ePosta+" "+o.dogumTarihi);
		}
		else if(k.getClass()==OgretimUyesi.class) {
			filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\ogretim uyeleri.k";
			OgretimUyesi ou =(OgretimUyesi)k;
			veri=(ou.tcKimlik+" "+ou.ad+" "+ou.soyad+" "+ou.bolum+" "+ou.telefon+" "+ou.ePosta+" "+ou.dogumTarihi+" "+ou.unvan);
		}
		else if(k.getClass()==IdariMemur.class){
			filePath=System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\idari memurlar.k";
			IdariMemur i=(IdariMemur)k;
			veri=(i.tcKimlik+" "+i.ad+" "+i.soyad+" "+i.telefon+" "+i.ePosta+" "+i.dogumTarihi);
		}
		
		f=new File(filePath);
		dosyaAyarla(f);
		
		FileWriter out = new FileWriter(f,true);
		out.append(veri+"\r\n");
		out.close();
	}
	public static void loginDuzenle(String tc,String yeniPassword) throws IOException,KayitBulunamadi{
		String filePath= System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\kayitli kullanicilar.k";
		File f= new File(filePath);
		dosyaAyarla(f);
		
		loginSil(tc);
		loginKaydet(tc, yeniPassword);
	}
	public static void loginSil(String tc) throws IOException,KayitBulunamadi{
		String filePath= System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\kayitli kullanicilar.k";
		File f= new File(filePath);
		dosyaAyarla(f);
		
		try {
			satirSil(f, tc);
		}catch (KayitBulunamadi e) {
			throw new KayitBulunamadi(tc+" Tc Kimlikli Bir Kullanýcý Kayýtlarda Bulunamadi.");
		}
	}
	public static void loginKaydet(String tc,String password) throws IOException{
		String filePath= System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\kayitli kullanicilar.k";
		File f= new File(filePath);
		dosyaAyarla(f);
		
		FileWriter o = new FileWriter(f,true);
		o.append(tc+" "+password+"\r\n");
		o.close();
	}
	public static String loginGetir(String tc) throws IOException, KayitBulunamadi{
		String filePath= System.getProperty("user.dir")+"\\veritabani\\kullanicilar\\kayitli kullanicilar.k";
		File f= new File(filePath);
		dosyaAyarla(f);
		
		FileReader i = new FileReader(f);
		StringBuilder gelen=new StringBuilder();
		while(satirAl(i,gelen)) {
			String g[] = gelen.toString().split(" ");
			if(g[0].equals(tc)) {
				i.close();
				return g[1];
			}
		}
		i.close();
		throw new KayitBulunamadi("Bu Kullanici Kayitli Deðil.");
	}
	public static void dosyaAyarla(File dos) throws IOException {
		{
			File dir = new File(dos.getAbsolutePath().substring(0,dos.getAbsolutePath().lastIndexOf("\\")));
			if(!dir.exists())dir.mkdirs();
			if(!dos.exists())dos.createNewFile();
		}
	}
	public static String satirGetir(File dos, String primaryKey) throws IOException,KayitBulunamadi{
		FileReader i = new FileReader(dos);
		StringBuilder gelen = new StringBuilder();
		while(FileHandler.satirAl(i, gelen)) {
			String g=gelen.toString();
			if(primaryKey.equals(g.substring(0,g.indexOf(" ")==-1?g.length():g.indexOf(" ")))) {
				i.close();
				return g;
			}
		}
		i.close();
		throw new KayitBulunamadi("Aranan Kayit Bulunamadi");
	}
	public static void satirSil(File dos,String primaryKey) throws IOException,KayitBulunamadi{
		FileReader i = new FileReader(dos);
		File yeni = new File(dos.getAbsolutePath()+".yeni");
		FileWriter o = new FileWriter(yeni,true);
		StringBuilder gelen = new StringBuilder();
		boolean buldu=false;
		while(FileHandler.satirAl(i, gelen)) {
			String g=gelen.toString();
			int to=g.indexOf(" ");
			if(!primaryKey.equals(g.substring(0,to==-1?g.length():to))) {
				o.append(gelen.toString()+"\r\n");
			}
			else
				buldu=true;
		}
		i.close();
		o.close();
		if(!buldu)
		{
			yeni.delete();
			throw new KayitBulunamadi("Silenecek Kayit Bulunamadi");
		}
		dos.delete();
		yeni.renameTo(dos);
	}
	public static boolean kelimeAl(FileReader i,StringBuilder kelime) throws IOException {
		kelime.delete(0,kelime.length());
		int cr=i.read();
		while(cr!=-1) {
			if(cr==13) {
				cr=i.read();
				if(cr==10) {
					return true;
				}
				else if(cr==-1) {
					kelime.append((char)13);
					return false;
				}
			}
			else if(cr==' ') {
				return true;
			}
			else {
				kelime.append((char)cr);
			}
			cr=i.read();
		}
		return false;
		
	}
	public static boolean satirAl(FileReader i,StringBuilder satir) throws IOException {
		satir.delete(0, satir.length());
		int cr=i.read();
		while(cr!=-1) {
			if(cr==13) {
				cr=i.read();
				if(cr==10) {
					return true;
				}
				else if(cr==-1) {
					satir.append((char)13);
					return false;
				}
			}
			else {
				satir.append((char)cr);
			}
			cr=i.read();
		}
		return false;
	}
}
