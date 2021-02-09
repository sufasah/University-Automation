
public class IdariMemur extends Kullanici {
	static int adet;
	IdariMemur(String a,String soya,String tcKimli,String dogumTarih,String ePost,String telefo){
		super(a,soya,tcKimli,dogumTarih,ePost,telefo);
		adet++;
	}
	public String toString() {
		return tcKimlik+" - "+ad+" - "+soyad+" - "+dogumTarihi+" - "+ePosta+" - "+telefon;
	}
	public void finalize() throws Throwable{
		adet--;
		super.finalize();
	}
}
