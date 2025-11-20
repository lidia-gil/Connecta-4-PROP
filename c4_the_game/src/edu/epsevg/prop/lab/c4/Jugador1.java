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
      return evaluaEstat(t, color);
      //if (t.solucio(column, color*-1)) return Integer.MAX_VALUE;
      //if (prof==0) return 0;
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
      return evaluaEstat(t, color);
      //if (t.solucio(column, color*-1)) return Integer.MIN_VALUE;
      //if (prof==0) return 0;
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

  private int evaluaEstat(Tauler t, int color) {
    int h = 0;
    for (int fila = 0; fila < t.getMida(); fila++) {
      for (int col = 0; col < t.getMida(); col++) {
        
        int colorFitxa = t.getColor(fila, col);

        if (colorFitxa == color) {
          if (col==0 || col==7) h += 0;
          else if (col==1 || col==6) h += 2;
          else if (col==2 || col==5) h += 5;
          else if (col==3 || col==4) h += 15;
        } 
        
        if (colorFitxa != color){

          int rival = color*-1;
          if (col + 1 < (t.getMida()-1) && t.getColor(fila, col + 1) == rival && col != 0 ) { // mirar filesssss + prioritzar 3 fitxes del rival seguides

            boolean foratEsq = (col - 1 >= 0 && t.getColor(fila, col - 1) == 0);

            boolean foratDer = (col + 2 < t.getMida() && t.getColor(fila, col + 2) == 0);

            if (foratEsq && foratDer) {
              h -= 200;
            }
            
            else if (foratEsq || foratDer) {
              h -= 80;   
            }
          }
        }
      }
    }
    return h;
  }
}


// fer classe auxiliar per fer la heuristica 

/* 
if (i==0 || i==7) h = 0;
else if (i==1 || i==6) h = 2;
else if (i==2 || i==5) h = 5;
else if (i==3 || i==4) h = 10;

if (amenaça || diagonal || forçar resposta || evites triple amenaça) 
else {
  if (altura == 0 || altura == 7) h = 10;
  else if (altura == 1 || altura == 6) h = 5;
  else if (altura == 2 || altura == 5) h = 2;
  else if (altura == 3 || altura == 4) h = 0;
}  
*/


// 1- Prioritzar columnes centrals
// 2- No continuar apilant fitxes en columnes on ja no tens hueco 
// 3- Prioritzar bloquejar doble amenaces del rival
// 4- Prioritzar crear dobles amenaces ( guanyar des de dues columnes diferents ) 
// 5- Prioritzar guanyar, no tapar al rival ( ja es té en compte en principi amb el minimax )
// 6- Idea feliç: intentar fer trampes pel rival ( posar una fitxa que li faci pensar que pot guanyar en un lloc, i després guanyar per un altre lloc )
