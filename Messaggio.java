package Pidgeongram;

import java.util.Objects;

/* OVERVIEW: classe che definisce un messaggio tra due utenti, il quale avrà un contenuto
 * di vario tipo all'interno
 */
public class Messaggio {

    /* CAMPI */
    /* Riferimento all'utente che invia il messaggio */
    private final Utente mittente;
    /* Riferimento all'utente che riceve il messaggio */
    private final Utente destinatario;
    /* Oggetto che rappresenta il contenuto del messaggio */
    private final Contenuto contenuto;

    /* AF(c): un messaggio è rappresentato dal suo contenuto */

    /* IR(c):   mittente != null && 
                destinatario != null && 
                contenuto != null */

    /* COSTRUTTORE
     * EFFECTS: costruisce un'istanza di this dati il mittente, il destinatario ed il contenuto
     * Lancia NullPointerException se almeno uno dei parametri è null
     */
    public Messaggio(final Utente mittente, final Utente destinatario, final Contenuto contenuto){
        this.mittente = Objects.requireNonNull(mittente,"Il mittente non può essere null");
        this.destinatario = Objects.requireNonNull(destinatario,"Il destinatario non può essere null");
        this.contenuto = Objects.requireNonNull(contenuto,"Il contenuto non può essere null");
    }

    /* EFFECTS: restituisce il riferimento al mittente del messaggio */
    public Utente getMittente(){
        return mittente;
    }

    /* EFFECTS: restituisce il riferimento al destinatario del messaggio */
    public Utente getDestinatario(){
        return destinatario;
    }

    @Override
    public String toString() {
        return mittente+": "+contenuto.toString();
    }
}
