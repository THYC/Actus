package net.teraoctet.actus.troc;

public enum EnumTransactType{
    SALE("VENTE"),
    BUY("ACHAT");

    private final String type;

    EnumTransactType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}

