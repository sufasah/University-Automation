
public class OgretimUyesi extends Kullanici {
	String unvan;
	String bolum;
	static int adet;
	OgretimUyesi(String a,String soya,String tcKimli,String bolu,String dogumTarih,String ePost,String telefo,String unva){
		super(a,soya,tcKimli,dogumTarih,ePost,telefo);
		bolum=bolu;
		unvan=unva;
		adet++;
	}
	public String toString() {
		return tcKimlik+" - "+ad+" - "+soyad+" - "+dogumTarihi+" - "+ePosta+" - "+telefon+" - "+unvan+" - "+bolum;
	}
	public void finalize() throws Throwable{
		adet--;
		super.finalize();
	}
}
