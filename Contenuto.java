package Pidgeongram;

import java.util.Objects;

/* OVERVIEW: classe astratta che definisce il contenuto di un messaggio. Esso potrà essere di vari
 * tipi, però avrà sempre una stringa identificativa utilizzabile nell'interfaccia testuale.
 * Le implementazioni potranno fornire specifiche aggiuntive per la rappresentazione testuale.
 */
public abstract class Contenuto {
    
    /* CAMPI */
    /* Stringa che definisce una informazione da trasmettere del messaggio */
    private final String informazione;

    /* IR(c):   informazione != null &&
     *          informazione.length() > 0
     */

    /* COSTRUTTORE
     * MODIFIES: this
     * EFFECTS: costruisce l'istanza di this usando l'informazione specificata
     * Lancia NullPointerException se informazione è null
     * Lancia IllegalArgumentException se informazione è vuota
    */ 
    public Contenuto(final String informazione){
        if(Objects.requireNonNull(informazione,"L'informazione non può essere null").length() == 0) throw new IllegalArgumentException("L'informazione non può essere vuota");
        this.informazione = informazione;
    }

    /* EFFECTS: restituisce l'informazione all'interno del contenuto */
    public String getInformazione(){
        return informazione;
    }

    /* EFFECTS: restituisce una stringa che rappresenta il tipo di contenuto */
    public abstract String getTipo();

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(getTipo()).append("]").append(" - "+informazione).toString();
    }
}
