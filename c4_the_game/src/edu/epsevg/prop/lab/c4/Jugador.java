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
        int candidat = ValorMin(t, i, profunditat-1);
        if ( valor  < candidat ){
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
}

private int ValorMin (Tauler t, int column, int color, int profunditat) {
    
  
}



 unció Max-Valor(estat, joc, profunditat)
  si Terminal(estat) o profunditat==0 llavors retorna Eval(estat);
  Valor = -∞
  per cada s dins de Successor(estat) fer
  valor = Max(valor,Min-Valor(s,joc, profunditat-1))
  fper;
  retorna valor;



private int ValorMax (Tauler t, int column, int color, int profunditat) {

  if ((t.solucio(column, color) || !t.espotmoure()) || profunditat==0) return

    valor = Integer.MIN_VALUE;
  } 
  for ( ){

    
  }
    retorna Eval(estat);
}

