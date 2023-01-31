package Pidgeongram;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* OVERVIEW: classe che definisce un utente identificato da nome e password. L'utente fa parte
 * di una rete di scambio di messaggi, quindi è in grade di inviare o riprendere uno scambio di messaggi
 */
public class Utente {
    
    /* CAMPI */
    /* Stringa rappresentante il nome dell'utente */
    public final String nome;
    /* Stringa rappresentante la password */
    private final String password;
    /* Una mappa che unisce un interlocutore (chiave) con la relativa conversazione (valore associato alla chiave) */
    private final Map<Utente,Rete.Conversazione> logConversazioni;

    /* AF(c): un utente è rappresentato dal suo nome */

    /* IR(c):   nome != null && nome.length() > 0 
     *          password != null && password.length() > 0
     *          interlocutori != null && ogni elemento di interlocutori != null
    */

    /* COSTRUTTORE:
     * EFFECTS: crea un'istanza di this tramite un nome e una password
     * Lancia NullPointerException se nome e/o password sono null
     * Lancia IllegalArgumentException se nome e/o password sono vuoti e/o nome contiene spazi
     */
    public Utente(final String nome, final String password){
        if(Objects.requireNonNull(nome,"Il nome utente non può essere null").length() == 0) throw new IllegalArgumentException("Il nome utente non può essere vuoto");
        if(Objects.requireNonNull(password,"La password non può essere null").length() == 0) throw new IllegalArgumentException("La password non può essere vuota");
        if(nome.trim().contains(" ")) throw new IllegalArgumentException("Il nome non può contenere spazi");
        this.nome = nome;
        this.password = password;
        logConversazioni = new HashMap<>();
    }

    /* EFFECTS: restituisce true se la password specificata è uguale a quella di this
     * Lancia NullPointerException se password è null
     * Lancia IllegalArgumentException se password è vuota
     */
    public boolean checkPsw(final String password){
        if(Objects.requireNonNull(password,"La password non può essere null").length() == 0) throw new IllegalArgumentException("La password non può essere vuota");
        return password.equals(this.password);
    }

    /* EFFECTS: restituisce un set immodificabile di Utenti con cui this ha avuto una scambio di messaggi */
    public Set<Utente> interlocutori(){
        return Collections.unmodifiableSet(logConversazioni.keySet());
    }

    /* EFFECTS: stabilisce una nuova conversazione con l'utente specificato. Qualora ci fosse già stata,
     * la funzione non fa nulla
     * Lancia NullPointerException se utente è null
     */
    public void iniziaConversazione(final Utente utente, final Rete.Conversazione conv){
        Objects.requireNonNull(utente,"L'utente non può essere null");
        if(!logConversazioni.containsKey(utente)) logConversazioni.put(utente, conv);
    }

    /* EFFECTS: restituisce la conversazione tra this e l'utente specificato. Qualora la conversazione non 
     * esistesse, restituisce null
     * Lancia NullPointerException se utente è null
     */
    public Rete.Conversazione riprendiConversazione(final Utente utente){
        Objects.requireNonNull(utente,"L'utente non può essere null");
        for(Utente u : logConversazioni.keySet()) if(u.nome.equals(utente.nome)) return logConversazioni.get(u);
        return null;
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Utente)) return false;
        Utente other = (Utente)obj;
        return other.nome.equals(nome);
    }

    @Override
    public String toString() {
        return nome;
    }

    public static void main(String[] args) {
        System.out.println(new Utente("Pippo","Pa"));
    }
}
