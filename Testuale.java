package Pidgeongram;

/* OVERVIEW: classe che estende la classe astratta Contenuto con il compito di descrivere un contenuto
 * di un messaggio testuale
 */
public class Testuale extends Contenuto{

    /* AF(c): un messaggio testuale rappresentato dalla stringa [getTipo()] - informazione */

    /* IR(c): invariante di super() */

    /* COSTRUTTORE
     * EFFECTS: costruisce un'istanza di this usando l'informazione
     */
    public Testuale(final String informazione) {
        super(informazione);
    }

    @Override
    public String getTipo() {
        return "TESTUALE";
    }
    
}
