package edu.epsevg.prop.lab.c4;

/**
 * Funció heurística proposada pel joc del Connecta 4.
 * 
 * @author Daniel Garcia i Lidia Gil
 */
public class HeuristicaC4 {

  // 1- Prioritzar columnes centrals
  // 2- No continuar apilant fitxes en columnes on ja no tens hueco 
  // 3- Prioritzar bloquejar doble amenaces del rival
  // 4- Prioritzar crear dobles amenaces ( guanyar des de dues columnes diferents ) 
  // 5- Prioritzar guanyar, no tapar al rival ( ja es té en compte en principi amb el minimax )
  // 6- Idea feliç: intentar fer trampes pel rival ( posar una fitxa que li faci pensar que pot guanyar en un lloc, i després guanyar per un altre lloc )

  private static int calculaHeuristica4(int pos[]) {
    //0:buit; -1:rival; 1:jugador
    int h = 0; 
  
    int buits = 0;
    int rival = 0;
    int jugador = 0;

    for (int i = 0; i < 4; i++) {
      if (pos[i] == 0) ++buits;
      else if (pos[i] == -1) ++rival;
      else ++jugador;
    }
    
    // Jugador
    if (jugador == 4) return 1000000;
    if (jugador == 3 && buits == 1) return 100;
    if (jugador == 2 && buits == 2) return 30; //30
    if (jugador == 1 && buits == 3) return 1;

    // Rival
    if (rival == 4) return -1000000;
    if (rival == 3 && buits == 1) return -100;
    if (rival == 2 && buits == 2) return -30; //-20
    if (rival == 1 && buits == 3) return -1;
        
    return h;
  }
  
  private static int evaluaHVD(Tauler t, int fila, int col, int sumaFila, int sumaCol, int colorJugadorIni) {

      int pos[] = new int[4];
      for (int i = 0; i < 4; i++) {
          if (colorJugadorIni == t.getColor(fila, col)) pos[i] = 1;
          else if (colorJugadorIni*-1 == t.getColor(fila, col)) pos[i] = -1;
          fila += sumaFila;
          col += sumaCol;
      }
      return calculaHeuristica4(pos);
  }

  /**
   * Avalua l'estat actual del tauler des de la perspectiva del jugador donat.
   * 
   * Examina totes les possibles línies de 4 posicions (horitzontals, verticals i diagonals)
   * i assigna una puntuació basada en les amenaces i oportunitats de cada jugador.
   * 
   * @param t Tauler actual del joc
   * @param color Color del jugador per al qual s'avalua l'estat
   * @param colorJugadorIni Color del jugador que va iniciar
   * @return Puntuació heurística de l'estat del tauler:<br>
   *         - Valors positius indiquen avantatge pel jugador<br>
   *         - Valors negatius indiquen avantatge pel rival<br>
   *         - 0 indica un estat equilibrat del tauler<br>
   *         - ±1000000 indica victòria immediata del jugador o rival
   */
  public static int evaluaEstat(Tauler t, int color, int colorJugadorIni) {
    
    int h = 0;
    
    //horitzontals
    for (int fila = 0; fila < t.getMida(); fila++) {
      for (int col = 0; col < t.getMida()-3; col++) {
        h += evaluaHVD(t, fila, col, 0, 1, colorJugadorIni);
        if (h >= 1000000) return h;
      }  
    }
    
    //verticals
    for (int col = 0; col < t.getMida(); col++) {
      for (int fila = 0; fila < t.getMida()-3; fila++) {
        h += evaluaHVD(t, fila, col, 1, 0, colorJugadorIni);
        if (h >= 1000000) return h;
      }  
    }

    //diagonals adalt - dreta
    for (int col = 0; col < t.getMida()-3; col++) {
      for (int fila = 0; fila < t.getMida()-3; fila++) {
        h += evaluaHVD(t, fila, col, 1, 1, colorJugadorIni);
        if (h >= 1000000) return h;
      }      
    }

    // diagonals adalt - esquerra
    for (int col = 3; col < t.getMida(); col++) {
      for (int fila = 0; fila < t.getMida()-3; fila++) {
        h += evaluaHVD(t, fila, col, 1, -1, colorJugadorIni);
        if (h >= 1000000) return h;
      }      
    }
    return h;
  }
}