package edu.epsevg.prop.lab.c4;

/**
 * Jugador que implementa MiniMax amb poda Alpha-Beta
 * 
 * @author Daniel Garcia i Lidia Gil
 */
public class Jugador1
  implements Jugador, IAuto
{
  private String nom;
  private int profunditat;
  private int contadorEvaluacions;
  private boolean podaAlphaBeta;
  
  /**
   * Constructor que crea un jugador amb una profunditat específica per a MiniMax.
   * 
   * @param profunditat Profunditat màxima de cerca per a l'algorisme MiniMax
   */
  public Jugador1(int profunditat, boolean podaAlphaBeta)
  {
    nom = "MiniMaxPlayer";
    this.profunditat = profunditat;
    this.podaAlphaBeta = podaAlphaBeta;
    this.contadorEvaluacions = 0;
  }
  
  /**
   * Calcula el millor moviment possible del jugador utilitzant l'algorisme MiniMax, donat un tauler i un color de fitxa que ha de possar.
   *
   * L'algorisme simula moviments alternats del jugador i el rival fins a una profunditat determinada,
   * buscant el moviment que condueix al millor resultat possible assumint jugada òptima del contrincant.
   * 
   * @param t Tauler actual del joc
   * @param color Color del jugador actual (1 o -1)
   * @return La columna seleccionada per a fer el moviment
   */
  public int moviment(Tauler t, int color) {
    int valor = Integer.MIN_VALUE;
    int millorMoviment = -1;
    for (int i = 0; i < t.getMida(); i++){
      if (t.movpossible(i)){
        Tauler taulerMov = new Tauler(t);
        taulerMov.afegeix(i, color);
        int prof = profunditat;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int colorJugadorIni = color;
        int candidat = MinValor(taulerMov, i, color*-1, alpha, beta, prof-1, colorJugadorIni);
        if (valor < candidat){
          valor = candidat;
          millorMoviment = i;
        }
      }    
    }
    System.out.println("Jugades finals explorades: " + contadorEvaluacions );
    return millorMoviment;
  }
  
  /**
    * Retorna el nom del jugador.
    * 
    * @return El nom identificatiu del jugador
    */
  public String nom()
  {
    return nom;
  }

  
  private int MinValor(Tauler t, int column, int color, int alpha, int beta, int prof, int colorJugadorIni) {
    if (t.solucio(column, color*-1) || prof==0){
      contadorEvaluacions++;
      return HeuristicaC4.evaluaEstat(t, color, colorJugadorIni);
    } 
    int valor = Integer.MAX_VALUE;
    for (int i = 0; i < t.getMida(); i++){
      if (t.movpossible(i)){
        Tauler taulerMov = new Tauler(t);
        taulerMov.afegeix(i, color);
        valor = Math.min(valor, MaxValor(taulerMov, i, color*-1, alpha, beta, prof-1, colorJugadorIni));
        if (podaAlphaBeta) {
          if ( valor <= alpha ) { // fem la poda
            return valor;
          }
          beta = Math.min(beta, valor); // actualitzem beta
        }
      } 
    } 
    return valor;
  }


  private int MaxValor (Tauler t, int column, int color, int alpha, int beta, int prof, int colorJugadorIni) {
    if (t.solucio(column, color*-1) || prof==0){
      contadorEvaluacions++;
      return HeuristicaC4.evaluaEstat(t, color, colorJugadorIni);
    } 
    int valor = Integer.MIN_VALUE;
    for (int i = 0; i < t.getMida(); i++){
      if (t.movpossible(i)){
        Tauler taulerMov = new Tauler(t);
        taulerMov.afegeix(i, color);
        valor = Math.max(valor, MinValor(taulerMov, i, color*-1, alpha, beta, prof-1, colorJugadorIni));
        if (podaAlphaBeta) {
          if ( beta <= valor ) { // fem la poda
            return valor;
          }
          alpha = Math.max(alpha, valor); // actualitzem alpha
        }
      } 
    } 
    return valor;
  }
}

   