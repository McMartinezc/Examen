import java.util.ArrayList;
import java.util.stream.Stream;

public class Maquina {

   private ArrayList<Producte> llistaProductes;


    private String nomMaquina;

    public Maquina(String nomMaquina) {
        this.nomMaquina = nomMaquina;
        llistaProductes = new ArrayList<Producte>();
    }

    public ArrayList<Producte> getLlistaProductes() {
        return llistaProductes;
    }

    public void setLlistaProducte(ArrayList<Producte> llistaProductes) {
        this.llistaProductes = llistaProductes;
    }

    public String getNomMaquina() {
        return nomMaquina;
    }

    public void setNomMaquina(String nomMaquina) {
        this.nomMaquina = nomMaquina;
    }
    //Metode afegir producte a la maquina
    public void afegirProducte(Producte producte){
        llistaProductes.add(producte);
    }

    //Metode control stock de producte d'una màquina
    public void controlStock(String nomProducte){
        int indexProducte=-1, i=0;
        int stock, stockInicial, stockFinal;

        indexProducte = buscarProducte(nomProducte);

            //Si existeix comprovem stock i el modifiquem
            if (llistaProductes.get(indexProducte).getStock() < 10) {
                stockInicial = llistaProductes.get(indexProducte).getStock();
                System.out.println("Stock actual de " + nomProducte + " " + stockInicial);
                stock = 10 - llistaProductes.get(indexProducte).getStock();
                System.out.println("Necessites omplir l'estock del producte: " + stock);
                stockFinal= stock + stockInicial;
                llistaProductes.get(indexProducte).setStock(stockFinal);
                System.out.println("Stock del producte actualitzat " +llistaProductes.get(indexProducte).getStock());
            } else if(llistaProductes.get(indexProducte).getStock()==10){ //Si el stock està ple no fem res
                System.out.println("Stock ple. Màxim 10 productes");
            }

    }
    //Metode buscar producte
    public int buscarProducte(String nomProducte){
        int indexProducte =-1;
        int i =0;
        boolean encontrado = false;

        while((i < llistaProductes.size()) && (encontrado == false)){
            if (llistaProductes.get(i).getNomProducte().equalsIgnoreCase(nomProducte)){
                indexProducte = i;
                encontrado = true;
            }
            i++;
        }
        if(!encontrado){
            System.out.println("Producte no existeix.");
        }
        return indexProducte;
    }

    //Metode buscar per marca amb lambda e imprimeix el producte trobat
    public void producteMarca(String marcaProducte){
        Stream<Producte> producteStream = llistaProductes.stream()
                .filter(producte -> producte.getMarcaProducte().equalsIgnoreCase(marcaProducte));

       // producteStream.findFirst().get();
        producteStream.forEach(producte -> System.out.println(producte.toString()));

    }
    //Mostra el numero de producte d'una marca concreta amb una lambda.



    //Metode total stock
    public int stockTotal(){
        int stockTotal=0;

        for(int i=0; i<llistaProductes.size(); i++){
            stockTotal += llistaProductes.get(i).getStock() ;
        }
        return stockTotal;
    }

}