import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@SuppressWarnings("metode deprecated")
public class App {

    public static void main(String[] args){

        ArrayList<Maquina> llistaMaquines = new ArrayList<Maquina>();
        int opcions, indexMaquina = -1;
        String nomMaquina, marcaProducte;


        do {
            opcions = menuEntrada();

            if (opcions == 1) { //Sortir programa
                System.out.println("Has sortit del programa.");

            } else {
                crearMaquina(llistaMaquines);
                switch (opcions) {

                    case 2://Afegir producte
                        nomMaquina = introInfo("Introdueix nom de la maquina: ");
                        indexMaquina = buscarMaquina(nomMaquina, llistaMaquines);

                        if (indexMaquina == -1) {
                            System.out.println("Maquina no existeix.");
                        } else {
                            crearProducte(indexMaquina,llistaMaquines);
                            modificarStock(indexMaquina,llistaMaquines );
                        }
                        break;

                    case 3: //Mostrar productes d'una màquina
                        nomMaquina = introInfo("Introdueix nom de la maquina: ");
                        indexMaquina = buscarMaquina(nomMaquina, llistaMaquines);
                        try {
                            mostraProductesMaquina(indexMaquina, llistaMaquines);
                        }catch (Exception error){
                            System.out.println(error.getMessage());
                        }
                        break;

                    case 4: //Mostrar producte d'una màquina
                        nomMaquina = introInfo("Introdueix nom de la màquina: ");
                        indexMaquina = buscarMaquina(nomMaquina, llistaMaquines);
                        if(indexMaquina==-1){
                            System.out.println("Màquina no existeix");
                        }else {
                            marcaProducte = introInfo("Introdueix marca que vols buscar en la màquina: ");
                            llistaMaquines.get(indexMaquina).producteMarca(marcaProducte);
                        }
                        break;

                    case 5: //Mostrar stock d'un producte de la màquina
                        nomMaquina = introInfo("Introdueix nom de la màquina: ");
                        indexMaquina = buscarMaquina(nomMaquina, llistaMaquines);
                        if(indexMaquina==-1){
                            System.out.println("Màquina no existeix");
                        }else {
                            marcaProducte = introInfo("Introdueix marca que vols buscar de la màquina: ");
                            llistaMaquines.get(indexMaquina).quantitatProducteMarca(marcaProducte);
                        }
                        break;
                    case 6: //Metode obsolet
                        nomMaquina = introInfo("Introdueix nom de la màquina: ");
                        indexMaquina = buscarMaquina(nomMaquina, llistaMaquines);
                        llistaMaquines.get(indexMaquina).getLlistaProductes().get(1).retornarPreu();
                        break;
                    case 7: //Metode mostra total de stock
                        mostrarStockTotal(llistaMaquines);
                        break;
                    case 8: //Metode crear en un arxiu
                        crearArxiu(llistaMaquines);
                        nomMaquina = introInfo("Introdueix nom de la màquina que vols crear l'arxiu: ");
                        indexMaquina = buscarMaquina(nomMaquina, llistaMaquines);
                        Maquina maquina = llistaMaquines.get(indexMaquina);
                        crearArxiuMaquina(maquina);
                        break;
                }
            }
        } while (opcions != 1);

    }
    //Metodo crear arxiu
    static void crearArxiu (ArrayList<Maquina> llistaMaquines) {

        try{
            FileWriter file = new FileWriter("maquines.csv");
            PrintWriter printWriter = new PrintWriter(file);

            for (int i = 0; i < llistaMaquines.size(); i++) {
             printWriter.println(llistaMaquines.get(i).toString());
            }
            printWriter.close();
        }catch (Exception error ){

        }
    }
    //Metodo crear arxiu
    static void crearArxiuMaquina (Maquina maquina) {

        final String CSV_SEPARATOR = ",";
        {
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("productes.csv"), "UTF-8"));
                for (Producte producte : maquina.getLlistaProductes()) {
                    StringBuffer oneLine = new StringBuffer();
                    oneLine.append(producte.getNomProducte().trim().length() == 0 ? "" : producte.getNomProducte());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(producte.getMarcaProducte().trim().length() == 0 ? "" : producte.getMarcaProducte());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(producte.getPreuProducte() < 0 ? "" : producte.getPreuProducte());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(producte.getStock() < 0 ? "" : producte.getStock());
                    oneLine.append(CSV_SEPARATOR);
                    bw.write(oneLine.toString());
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            } catch (UnsupportedEncodingException e) {
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
    }
    //Metode mostrar stock total
    static void mostrarStockTotal(ArrayList<Maquina> llistaMaquines){
        int total=0;
        for (int i=0; i<llistaMaquines.size(); i++){
            total += llistaMaquines.get(i).stockTotal();
        }
        System.out.println("Total de productes de les màquines: "+total);
    }

    //Mostrar productes d'una màquina si no hi ha productes salta exception
    static void mostraProductesMaquina(int indexMaquina, ArrayList<Maquina> llistaMaquines) throws NoProducte {

        if(llistaMaquines.get(indexMaquina).getLlistaProductes().isEmpty()){
            throw new NoProducte("La màquina no té productes disponibles.");
        }else{
            System.out.println(llistaMaquines.get(indexMaquina).getLlistaProductes());
        }
    }

    //Metode buscar màquina
    static int buscarMaquina(String nomMaquina, ArrayList<Maquina> llistaMaquines){
        int i=0;
        int indexMaquina=-1;
        boolean encontrado =false;

        while( i<llistaMaquines.size() && encontrado== false){
            if(llistaMaquines.get(i).getNomMaquina().equalsIgnoreCase(nomMaquina)){
                indexMaquina = i;
                encontrado = true;
            }
            i++;
        }
        return indexMaquina;
    }
    //Metode modificar stock
    static void modificarStock (int indexMaquina, ArrayList<Maquina> llistaMaquines) {
        int  indexProducte;
        String nomProducte, marcaProducte;

        nomProducte = introInfo("Introdueix nom del producte: ");
        marcaProducte = introInfo("Introdueix marca del producte: ");
        //Comprovem que el producte existeix a la màquina
        indexProducte = llistaMaquines.get(indexMaquina).buscarProducte(nomProducte,marcaProducte);

        if(indexProducte!=-1){ // si el producte existeix introduïm producte
            //Comprovem stock producte
            llistaMaquines.get(indexMaquina).controlStock(nomProducte, marcaProducte);
        }else{
            System.out.println("Sha de crear un nou producte en aquesta màquina");
        }
    }
    //Metode crear Producte
    static void crearProducte (int indexMaquina, ArrayList<Maquina> llistaMaquines) {
        //Afegim producte
        Producte producte1= new Producte("patates","matutano",1,6);
        Producte producte2= new Producte("sandwich","fresc",1,0);
        Producte producte3 = new Producte("cafe","cafe",1,10);
        Producte producte4= new Producte("xocolata","nestle",1,9);
        Producte producte5 = new Producte("bombons","nestle",2,7);
        llistaMaquines.get(1).afegirProducte(producte1);
        llistaMaquines.get(1).afegirProducte(producte2);
        llistaMaquines.get(2).afegirProducte(producte3);
        llistaMaquines.get(2).afegirProducte(producte4);
        llistaMaquines.get(2).afegirProducte(producte5);
    }
    //Metode crear màquina
    static void crearMaquina (ArrayList<Maquina> llistaMaquines ) {
        llistaMaquines.add(new Maquina("maquina1"));
        llistaMaquines.add(new Maquina("maquina2"));
        llistaMaquines.add(new Maquina("maquina3"));
    }
    //Metode menu info entrada
    static int menuEntrada() {
        int opcions = introInfoInt("Escull:\n1.Sortir de l'aplicació"
                + "\n2.Afegir producte a l'estoc"
                + "\n3.Mostrar productes d'una màquina"
                + "\n4.Mostrar primera marca d'un producte d'una màquina"
                + "\n5.Mostrar quantitat de productes d'una marca que hi ha en una màquina"
                + "\n6.Metode obsolet"
                + "\n7.Metode mostrar total de stock de totes les màquines"
                + "\n8.Crear un arxiu"
        );
        return opcions;
    }
    /////ENTRADA DE DATOS

    //Metodes introduïr informació
    static String introInfo(String missatge) {
        Scanner input = new Scanner(System.in);
        System.out.println(missatge);
        return input.nextLine();//Retorna String
    }

    static int introInfoInt(String missatge) {
        Scanner input = new Scanner(System.in);
        System.out.println(missatge);
        return input.nextInt();//Retorna Int
    }
    static double introInfoDouble(String missatge) {
        Scanner input = new Scanner(System.in);
        System.out.println(missatge);
        return input.nextDouble();//Retorna double
    }

}