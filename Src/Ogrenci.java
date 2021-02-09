
public class Ogrenci extends Kullanici {
	int no;
	String bolum;
	static int adet;
	Ogrenci(String a,String soya,String tcKimli,int n,String bolu,String dogumTarih,String ePost,String telefo){
		super(a,soya,tcKimli,dogumTarih,ePost,telefo);
		no=n;
		bolum=bolu;
		adet++;
	}
	public String toString() {
		return tcKimlik+" - "+no+" - "+ad+" - "+soyad+" - "+dogumTarihi+" - "+ePosta+" - "+telefon+" - "+bolum;
	}
	public void finalize() throws Throwable{
		adet--;
		super.finalize();
	}
}
