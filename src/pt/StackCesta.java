package pt;

public class StackCesta implements Cloneable{

    private BodCesty top = null;

    public void pridej(Stanice novaStanice, double vzdalenost){// переписать чтобы принимало просто Stanice а BodCesty создавался внутри

        BodCesty novyBod = new BodCesty(novaStanice, vzdalenost, top);

        if(top == null){
            this.top = novyBod;
        }
        else{
            top = novyBod;
        }
    }

    BodCesty get(){
        return top;
    }
    public double getCelaDalka(){
        double dalka = 0;
        BodCesty bodCesty = top;

        while (bodCesty != null){
            dalka += bodCesty.vzdalenost;
            bodCesty = bodCesty.next;
        }
        return dalka;
    }
    public void removeLast(){
        if(top == null){
            return;
        }
        else{
            top = top.next;
        }
    }

    public void vypis(){
        BodCesty bodCesty = top;

        while(bodCesty != null){
            System.out.print("Cesta: " + bodCesty.zastavka.getId()  + " -> " + bodCesty.vzdalenost + " -> ");
            bodCesty = bodCesty.next;
        }
        System.out.println(getCelaDalka());
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
