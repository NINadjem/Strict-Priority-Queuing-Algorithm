package spqapp;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Manno
 */
public class SPQ {

    private double current_time; // temp actuel== notre horloge
    private final double k;
    private ArrayList<InputElement> input; // le tableau 
    private ArrayList<ResultElement> result = new ArrayList<>(); // ce qu'on va afficher
    private ArrayList<Character> waiting_list = new ArrayList<>(); // la liste d'attente qui contiens les element qu'on a sauter ordonnée par temp d'arrivé
    private int inputListCurrentIndex; // un pointeur qui indique la colone courante
    private boolean done = false; // indicateur d'etat d'avancement du travail
    private static Comparator comparator=new Comparator<Character>() { // trier en ordre alphabetique
            @Override
            public int compare(Character o1, Character o2) {
                return Character.compare(o1, o2);
            }
        };
    
    
    //constructeur avec le tableau seulement
    public SPQ(ArrayList<InputElement> input) {
        this.input = input;
        k = 0.5; // fixé par defaut 0.5 
        execute();// pour commancer le travail
    }
    
    //constructeur avec tableur et k
    public SPQ(ArrayList<InputElement> input,double k) {
        this.input = input;
        this.k=k;
        execute();// pour commancer le travail
    }
    
    // cette methode est la recpencable d'ordonnacement
    private void execute() {
        if (!input.isEmpty()) { // on test d'abord que le tableau contiens des elements
            inputListCurrentIndex = 0; // on commance par la 1 ere colonne
            while (!done) { // tanq ke on a pas fini notre travail
                current_time = input.get(inputListCurrentIndex).time; // on va prendre le temps de la colonne courante
                getPassTheFirstWaitingElement(inputListCurrentIndex); // et passer son premier element
            }
        }
    }
    
    // cette methode va passer le 1 er element dans une colonne indiqué par son index qui est "elementIndexInInput" 
    private void getPassTheFirstWaitingElement(int elementIndexInInput) {
        InputElement inputElement = input.get(elementIndexInInput); // sauvgarder une reference de la colonne
        Character token_element = inputElement.getTheNextElement();// sauvgarder une reference de l'element qu'on va passer
        current_time += k; // incrémenter le temps
        result.add(new ResultElement(current_time, token_element)); // ajuter au resultat le fait qu'on a passer l'element a le temps courant
        inputElement.removeElementFromQueue(); // suprimer l'element de la liste (liste d'attente de la colonne)
        int nextColumn = getTheNextColumnIndex(); // prédire la prochaine colonne a executer
        System.out.println(inputElement.waiting_list); // khorti pour debugger
        if (nextColumn != elementIndexInInput || nextColumn == input.size() - 1) {
            // si on va par retomber sur la meme colone où on est dans la derniere colonne
            waiting_list.addAll(inputElement.waiting_list);//on va sauvgarder les elements restants dans la liste d'attente
        }
        inputListCurrentIndex = nextColumn; // ici la prochaine colonne a executé est mise a jour
        if (input.get(nextColumn).time == current_time) {
            // ici on a une priorité exemple : on est a 3:30 et il y a une demande dans le tableau a 3:30
            //on va r1 faire car de toute façon on va aller a la colonne suivante avec la boucle while dans la methode execute
        } else {// pas de priorité on est cool et libre
            if (input.get(nextColumn).time > current_time) {
                //si on a un peu du temps par exemple on est a 3:30h et la prochaine etape est a 4h on a 30 min libre
                getPassNextInWaitingList();//on va essayer de passer le 1 er qui attend depuis longtemps
            } else { // ici surement on a terminé notre travail car le tempscourant est superieur a la colone prochaine
                // fat l7al ya ibni
                getPassAllInWaitingList();// on va passer tous ceux qui attendaient msakin
                done = true;// aya w sayi kemlna ss
            }
        }

    }

    //cette method va nous dire qu'elle est la colonne prochaine a executer
    private int getTheNextColumnIndex() {
        int index = inputListCurrentIndex; // on va commancer de notre position actuelle
        for (int i = 0; i < input.size(); i++) {// parcourir le tableau
            if (input.get(i).time >= current_time) { // jusqu'a arrivé a le tempqui correspond
                index = i; // on va sauvgarder la positon
                break; // et sortir pas la peine de continuer tous le tableau on a rouver ce qu'on voulait
            }
        }// fin boucle
        System.out.println("index=" + index); // debug
        return index; // retourner la position
    }

    //cette methode va passer le 1 er element dans la liste d'attente
    private void getPassNextInWaitingList() {
        if (!waiting_list.isEmpty()) { // pas vide
            current_time += k; // incrementer
            System.out.println("took " + waiting_list.get(0) + " at " + current_time + " getPassNextInWaitingList()");//debug
            result.add(new ResultElement(current_time, waiting_list.get(0))); // ajouter au resultat
            waiting_list.remove(0);// supprimer car on a passer cet element
        }
    }
    
    // cette methode va passer tous les elements de la liste d'attente par ordre alphabetique
    private void getPassAllInWaitingList() {
        System.out.println("spqapp.SPQ.getPassAllInWaitingList()");//debug
        waiting_list.sort(comparator);
        for (Character character : waiting_list) { // parcourir
            current_time += k; // incrementer
            System.out.println("took " + character + " " + current_time);// debug
            result.add(new ResultElement(current_time, character));// ajouter au resultat
        }//fin boucle
        waiting_list = new ArrayList<>();// effacer tt
        input = new ArrayList<>(); // efacer le tableau
    }
    
    //cette methode nous donne le resultats obtenu en tableau
    public ArrayList<ResultElement> getTheResultSet() {
        return result;
    }
    
    // cette methode ecrit le resultat
    void printTheResult() {
        for (ResultElement re : result) {
            System.out.println(re.element + " at " + re.time);
        }
    }

    // la struture d'un element du resultat
    public static class ResultElement {

        private double time; // temps
        private char element; // a wela b wela c wela z wela

        public ResultElement(double time, char element) {
            this.time = time;
            this.element = element;
        }

        public char getElement() {
            return element;
        }

        public void setElement(char element) {
            this.element = element;
        }

    }
    
    // la struture d'un element du donnée (tableau)
    public static class InputElement {

        private double time; // temps
        private ArrayList<Character> waiting_list; // les elements qui ont entré

        public InputElement(double time, ArrayList<Character> waiting_list) {
            this.time = time;
            this.waiting_list = waiting_list;
            this.waiting_list.sort(comparator);

        }

        public double getTime() {
            return time;
        }

        public ArrayList<Character> getWaiting_list() {
            return waiting_list;
        }
        
        //pour supp 1 er element de la liste d attente
        public void removeElementFromQueue() {
            if (!waiting_list.isEmpty()) {
                waiting_list.remove(0);
            }
        }
        
        // pour savoir quel est l caractere  a wela b wela ....
        public Character getTheNextElement() {
            if (!waiting_list.isEmpty()) {
                return waiting_list.get(0);
            } else {
                return ' ';
            }
        }

    }

    
}
