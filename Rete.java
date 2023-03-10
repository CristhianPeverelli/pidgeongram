package Pidgeongram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* OVERVIEW: classe che definisce una rete di scambio di messaggi */
public class Rete {

    /* CAMPI */
    /* Un set di utenti all'interno della rete */
    private final Set<Utente> utenti;
    /* L'utente attualmente loggato dentro la rete */
    private Utente current;

    /* AF(c): una rete è rappresentata da un elenco dei suoi utenti */

    /* IR(c):   utenti != null &&
     *          ogni elemento di utenti != null &&
     *          nessun elemento di utenti ha il nome uguale ad un altro
     */

    /* COSTRUTTORE
     * EFFECTS: costruisce un'istanza vuota di this
     */
    public Rete(){
        utenti = new HashSet<>();
        current = null;
    }

    /* OVERVIEW: classe interna che definisce uno scambio di messaggio tra due utenti presenti nella rete */
    public class Conversazione {

        /* CAMPI */
        /* Lista (per importanza di ordine) mutabile dei messaggi della conversazione */
        private final List<Messaggio> messaggi;
        /* Indice dell'ultimo messaggio letto */
        private int lastRead;

        /* AF(c): una conversazione è rappresentata dall'elenco dei suoi messaggi */

        /* IR(c):   messaggi != null &&
         *          ogni elemento in messaggi != null
        */

        /* COSTRUTTORE PRIVATO
         * EFFECTS: costruisce una conversazione tra due utenti
        */
        private Conversazione(List<Messaggio> messaggi){
            this.messaggi = messaggi;
            lastRead = 0;
        }

        /* MODIFIES: this
         * EFFECTS: aggiunge il messaggio alla conversazione
         * Lancia NullPointerException se il messaggio è null
         */
        public void aggiungi(Messaggio m){
            messaggi.add(Objects.requireNonNull(m,"Il messaggio non può essere null"));
            lastRead = messaggi.size();
        }

        /* EFFECTS: restituisce una stringa contenente i messaggi non letti, separati
         * da un \n
         */
        public String messaggiNonLetti(){
            StringBuilder str = new StringBuilder("Messaggi non letti nella conversazione di "+current.nome+":\n");
            for(int i = lastRead; i < messaggi.size(); i++){
                str.append(messaggi.get(i)+"\n");
            }
            lastRead = messaggi.size();
            return str.toString();
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            for(Messaggio m : messaggi) str.append(m).append("\n");
            return str.toString();
        }
    }

    /* EFFECTS: registra all'interno della rete un nuovo utente, con nome e password specificati.
     * Qualora ci fosse un utente con lo stesso nome, la rete non lo registrerà
     * Lancia NullPointerException se nome e/o password sono null
     * Lancia IllegalArgumentException se nome e/o password sono vuote
     */
    public void registra(final String nome, final String password){
        if(Objects.requireNonNull(nome,"Il nome utente non può essere null").length() == 0) throw new IllegalArgumentException("Il nome utente non può essere vuoto");
        if(Objects.requireNonNull(password,"La password non può essere null").length() == 0) throw new IllegalArgumentException("La password non può essere vuota");
        for(Utente u : utenti) if(u.nome.equals(nome)) return;
        utenti.add(new Utente(nome, password));
    }

    /* EFFECTS: registra all'interno della rete un nuovo utente, con nome e password specificati.
     * Qualora ci fosse un utente con lo stesso nome, la rete non lo registrerà
     * Lancia NullPointerException se nome e/o password sono null
     * Lancia IllegalArgumentException se nome e/o password sono vuote
     */
    public void registra(final Utente u){
        Objects.requireNonNull(u,"L'utente non può essere null");
        for(Utente ut : utenti) if(ut.nome.equals(u.nome)) return;
        utenti.add(u);
    }

    /* EFFECTS: restituisce true se il login va a buon fine (l'utente col nome specificato ha la password specificata).
     * Restituisce false altrimenti (restituisce false anche se l'utente non è presente nella rete)
     * Lancia NullPointerException se nome e/o password sono null
     * Lancia IllegalArgumentException se nome e/o password sono vuote
    */
    public boolean login(final String nome, final String password){
        if(Objects.requireNonNull(nome,"Il nome utente non può essere null").length() == 0) throw new IllegalArgumentException("Il nome utente non può essere vuoto");
        if(Objects.requireNonNull(password,"La password non può essere null").length() == 0) throw new IllegalArgumentException("La password non può essere vuota");
        for(Utente u : utenti){
            if(u.nome.equals(nome)) {
                if(u.checkPsw(password)){
                    current = u;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /* EFFECTS: invia un messaggio all'interno della rete qualora ci sia un mittente 
     * Lancia NullPointerException se messaggio è null
     */
    public void invia(Messaggio messaggio){
        Objects.requireNonNull(messaggio,"Il messaggio non può essere null");
        if(!checkSecure(messaggio.getMittente(), messaggio.getDestinatario())) return;
        gestisciConversazione(current, messaggio.getDestinatario()).aggiungi(messaggio);
    }

    /* Metodo di servizio che crea instaura una conversazione qualora essa non fosse presente
     * e la ritorna. Altrimenti riprende la conversazione già esistente e la ritorna
     */
    private Conversazione gestisciConversazione(Utente mittente, Utente destinatario){
        Conversazione conv = mittente.riprendiConversazione(destinatario);
        if(conv == null){
            ArrayList<Messaggio> canale = new ArrayList<>();
            mittente.iniziaConversazione(destinatario, new Conversazione(canale));
            destinatario.iniziaConversazione(mittente, new Conversazione(canale));
            conv = mittente.riprendiConversazione(destinatario);
        }
        return conv;
    }

    /* Metodo di servizio che controlla prima di inviare un messaggio, se c'è un utente loggato e
     * se mittente e destinatario sono entrambi presenti nella rete
     */
    private boolean checkSecure(Utente mittente, Utente destinatario){
        if(current == null) return false;
        if(!utenti.contains(mittente)) return false;
        if(!utenti.contains(destinatario)) return false;
        return true;
    }

    public static void main(String[] args) {
        Rete rete = new Rete();
        Utente a = new Utente("Peve", "admin");
        Utente b = new Utente("Ste", "cecilia");
        rete.registra(a);
        rete.registra(b);
        rete.login(a.nome, "admin");
        Messaggio m = new Messaggio(a, b, new Testuale("Ciao sium"));
        rete.invia(m);
        rete.login(b.nome, "cecilia");
        m = new Messaggio(b, a, new Testuale("Ciao, ho preso 30"));
        rete.invia(m);
        m = new Messaggio(b, a, new Testuale("sono un clown"));
        rete.invia(m);
        m = new Messaggio(b, a, new Testuale("sium"));
        rete.invia(m);
        System.out.println(a.riprendiConversazione(b).messaggiNonLetti());
        rete.login(a.nome, "admin");
        m = new Messaggio(a, b, new Testuale("lo so"));
        rete.invia(m);
        m = new Messaggio(a, b, new Musicale("this is so sad, alexa play despacito"));
        rete.invia(m);
        System.out.println(a.riprendiConversazione(b));
    }

}
