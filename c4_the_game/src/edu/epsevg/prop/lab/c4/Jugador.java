package edu.epsevg.prop.lab.c4;

public class Jugador
  implements IAuto
{
  private String nom;
  
  public Jugador()
  {
    nom = "MiniMaxPlayer";
  }
  
  // hem de afegir per aqui al minimax
  public int moviment(Tauler t, int column, int color, int profunditat)
  {
    int valor = Integer.MIN_VALUE;
    int millorMoviment = -1;
    for (int i = 0; i < t.getMida(); i++){
      if (t.movpossible(i)){
        //Solucio
        if (t.solucio(column, color) || !t.espotmoure()) {
        
        }
        else {
          int candidat = ValorMin(t, i, , profunditat-1);
          if ( valor  < candidat ){
              valor = candidat;
              millorMoviment = i;
          }
        }
      }  
    }
    return millorMoviment;
  }
  
  public String nom()
  {
    return nom;
  }
}

private int ValorMin (Tauler t, int column, int color, int profunditat) {
  
    if ( )
}

private int ValorMax () {

  
}

