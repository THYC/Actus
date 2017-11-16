package net.teraoctet.actus.troc;

public class TotalTransact {
    int qte0;
    int qte1;
    int qte2;
    int qte3;
    int qte4;
    int qte5;
    int qte6;
    int qte7;
    int qte8;

    public TotalTransact() {
        this.qte0 = 0;
        this.qte1 = 0;
        this.qte2 = 0;
        this.qte3 = 0;
        this.qte4 = 0;
        this.qte5 = 0;
        this.qte6 = 0;
        this.qte7 = 0;
        this.qte8 = 0;
    }
    
    public void add(int slot, int qte){
        switch(slot){
            case 0:
                this.qte0 = this.qte0 + qte;
                break;
            case 1:
                this.qte1 = this.qte1 + qte;
                break;
            case 2:
                this.qte2 = this.qte2 + qte;
                break;
            case 3:
                this.qte3 = this.qte3 + qte;
                break;
            case 4:
                this.qte4 = this.qte4 + qte;
                break;
            case 5:
                this.qte5 = this.qte5 + qte;
                break;
            case 6:
                this.qte6 = this.qte6 + qte;
                break;
            case 7:
                this.qte7 = this.qte7 + qte;
                break;
            case 8:
                this.qte8 = this.qte8 + qte;
                break;
        }
    }  
    
    public int getQte0(){return qte0;}
    public int getQte1(){return qte1;}
    public int getQte2(){return qte2;}
    public int getQte3(){return qte3;}
    public int getQte4(){return qte4;}
    public int getQte5(){return qte5;}
    public int getQte6(){return qte6;}
    public int getQte7(){return qte7;}
    public int getQte8(){return qte8;}
    
}
