package pt;

public class StackCesta{

    BodCesty top;


    void add(BodCesty novyBod){
        novyBod.next = top;
        top = novyBod;
    }



}
