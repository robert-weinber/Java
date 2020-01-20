package AdatModell;


/**
 *
 * @author Wolfram
 */

@SuppressWarnings("serial")
public class Játékos{
    private int id;
    private int pontszám;
    private boolean aktív = false;
    


    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
    
    public void setPontszám(int pontszám){
        this.pontszám = pontszám;
    }

    public int getPontszám(){
        return this.pontszám;
    }


    public void setAktív(boolean aktív){
        this.aktív = aktív;
    }

    public boolean getAktív(){
        return this.aktív;
    }
}