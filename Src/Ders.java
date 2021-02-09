
public class Ders {
	String kod;
	String ad;
	String bolum;
	String derslik;
	Ders(String ko,String a,String bolu,String dersli){
		kod=ko;
		ad=a;
		bolum=bolu;
		derslik=dersli;
	}
	
	public String toString(){
		return kod+" - "+ad+" - "+bolum+" - "+derslik;
	}
}
