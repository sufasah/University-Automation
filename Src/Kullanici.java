abstract class Kullanici {
	String ad;
	String soyad;
	String tcKimlik;
	String dogumTarihi;
	String ePosta;
	String telefon;
	static int adet;
	Kullanici(String a,String soya,String tcKimli,String dogumTarih,String ePost,String telefo){
		ad=a;
		soyad=soya;
		tcKimlik=tcKimli;
		dogumTarihi=dogumTarih;
		ePosta=ePost;
		telefon=telefo;
		adet++;
	}
	public void finalize() throws Throwable{
		adet--;
		super.finalize();
	}
}
