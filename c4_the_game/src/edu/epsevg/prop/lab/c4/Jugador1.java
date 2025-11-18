package edu.epsevg.prop.lab.c4;

public class Jugador1
  implements Jugador, IAuto
{
  private String nom;
  private int profunditat;
  
  public Jugador1(int profunditat)
  {
    nom = "MiniMaxPlayer";
    this.profunditat = profunditat;
  }
  
  // hem de afegir per aqui al minimax
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
        int candidat = MinValor(taulerMov, i, color*-1, alpha, beta, prof-1);
        if (valor < candidat || valor==Integer.MIN_VALUE){
          valor = candidat;
          millorMoviment = i;
        } 
      }    
    }
    return millorMoviment;
  }
  

  public String nom()
  {
    return nom;
  }


  private int MinValor(Tauler t, int column, int color, int alpha, int beta, int prof) {
    if (t.solucio(column, color*-1) || prof==0){
      //return evaluaEstat(t);
      if (t.solucio(column, color*-1)) return Integer.MAX_VALUE;
      if (prof==0) return 0;
    } 
    int valor = Integer.MAX_VALUE;
    for (int i = 0; i < t.getMida(); i++){
      if (t.movpossible(i)){
        Tauler taulerMov = new Tauler(t);
        taulerMov.afegeix(i, color);
        valor = Math.min(valor, MaxValor(taulerMov, i, color*-1, alpha, beta, prof-1));
        if ( valor <= alpha ) { // fem la poda
          return valor;
        }
        beta = Math.min(beta, valor); // actualitzem beta
      } 
    } 
    return valor;
  }


  private int MaxValor (Tauler t, int column, int color, int alpha, int beta, int prof) {
    if (t.solucio(column, color*-1) || prof==0){
      //return evaluaEstat(t);
      if (t.solucio(column, color*-1)) return Integer.MIN_VALUE;
      if (prof==0) return 0;
    } 
    int valor = Integer.MIN_VALUE;
    for (int i = 0; i < t.getMida(); i++){
      if (t.movpossible(i)){
        Tauler taulerMov = new Tauler(t);
        taulerMov.afegeix(i, color);
        valor = Math.max(valor, MinValor(taulerMov, i, color*-1, alpha, beta, prof-1));
        if ( beta <= valor ) { // fem la poda
          return valor;
        }
        alpha = Math.max(alpha, valor); // actualitzem alpha
      } 
    } 
    return valor;
  }
}