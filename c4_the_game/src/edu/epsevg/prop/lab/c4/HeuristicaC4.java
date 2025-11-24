package edu.epsevg.prop.lab.c4;

/**
 * Funció heurística proposada pel joc del Connecta 4.
 * 
 * @author Daniel Garcia i Lidia Gil
 */
public class HeuristicaC4 {

  /**
   * Calcula el valor heurístic d'una línia de 4 posicions ja analitzades,
   * representades al vector {@code pos}, on cada element indica l'ocupació
   * d'una casella:
   * <ul>
   *   <li>1  → casella amb fitxa del jugador</li>
   *   <li>-1 → casella amb fitxa del rival</li>
   *   <li>0  → casella buida</li>
   * </ul>
   *
   * La funció compta quantes fitxes del jugador, del rival i quants buits
   * hi ha a la línia i assigna una puntuació segons la força de la posició:
   * <ul>
   *   <li>4 fitxes del jugador → victòria immediata (+1.000.000)</li>
   *   <li>3 fitxes del jugador + 1 buit → amenaça forta (+100)</li>
   *   <li>2 fitxes del jugador + 2 buits → oportunitat moderada (+30)</li>
   *   <li>1 fitxa del jugador + 3 buits → lleuger avantatge (+1)</li>
   *   <li>4 fitxes del rival → derrota immediata (-1.000.000)</li>
   *   <li>3 fitxes del rival + 1 buit → amenaça forta rival (-100)</li>
   *   <li>2 fitxes del rival + 2 buits → oportunitat rival moderada (-30)</li>
   *   <li>1 fitxa del rival + 3 buits → lleuger desavantatge (-1)</li>
   * </ul>
   *
   * Si la línia no encaixa en cap d'aquests casos, retorna 0.
   *
   * @param pos Vector de 4 posicions amb els valors {-1, 0, 1} indicant l'estat de cada casella.
   * @return Un valor heurístic que mesura la qualitat d'aquesta línia per al jugador.
   */
  private static int calculaHeuristica4(int pos[]) {
    //0:Buit; -1:Rival; 1:Jugador
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
    if (jugador == 2 && buits == 2) return 30; 
    if (jugador == 1 && buits == 3) return 1;

    // Rival
    if (rival == 4) return -1000000;
    if (rival == 3 && buits == 1) return -100;
    if (rival == 2 && buits == 2) return -30; 
    if (rival == 1 && buits == 3) return -1;
        
    return h;
  }
  
  /**
   * Avalua una línia de 4 posicions consecutives del tauler, començant a la 
   * posició indicada (fila, col) i avançant segons els increments (sumaFila, sumaCol).
   * 
   * Per a cada una de les 4 caselles:
   *   - assigna 1 si la casella conté una fitxa del jugador inicial,
   *   - assigna -1 si conté una fitxa del rival,
   *   - assigna 0 si és buida.
   * 
   * A continuació, passa aquest vector de 4 posicions a la funció 
   * {@code calculaHeuristica4(pos)} per obtenir el valor heurístic 
   * d’aquesta seqüència.
   * 
   * @param t                El tauler de joc.
   * @param fila             La fila inicial des de la qual comença la línia.
   * @param col              La columna inicial des de la qual comença la línia.
   * @param sumaFila         Increment de fila per cada pas (direcció vertical/diagonal).
   * @param sumaCol          Increment de columna per cada pas (direcció horitzontal/diagonal).
   * @param colorJugadorIni  El color del jugador que estem avaluant (1 o -1).
   * 
   * @return El valor heurístic corresponent a aquesta línia de 4 caselles.
   */
  private static int evaluaLinea4(Tauler t, int fila, int col, int sumaFila, int sumaCol, int colorJugadorIni) {

    int pos[] = new int[4]; // Buit
    for (int i = 0; i < 4; i++) {
      if (colorJugadorIni == t.getColor(fila, col)) pos[i] = 1; // Jugador
      else if (colorJugadorIni*-1 == t.getColor(fila, col)) pos[i] = -1; // Rival
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
   * @param colorJugadorIni Color del jugador que va iniciar
   * @return Puntuació heurística de l'estat del tauler:<br>
   *         - Valors positius indiquen avantatge pel jugador<br>
   *         - Valors negatius indiquen avantatge pel rival<br>
   *         - 0 indica un estat equilibrat del tauler<br>
   */
  public static int evaluaEstat(Tauler t, int colorJugadorIni) {
    
    int h = 0;
    
    //horitzontals
    for (int fila = 0; fila < t.getMida(); fila++) {
      for (int col = 0; col < t.getMida()-3; col++) {
        h += evaluaLinea4(t, fila, col, 0, 1, colorJugadorIni);
      }  
    }
    
    //verticals
    for (int col = 0; col < t.getMida(); col++) {
      for (int fila = 0; fila < t.getMida()-3; fila++) {
        h += evaluaLinea4(t, fila, col, 1, 0, colorJugadorIni);
      }  
    }

    //diagonals adalt - dreta
    for (int col = 0; col < t.getMida()-3; col++) {
      for (int fila = 0; fila < t.getMida()-3; fila++) {
        h += evaluaLinea4(t, fila, col, 1, 1, colorJugadorIni);
      }      
    }

    // diagonals adalt - esquerra
    for (int col = 3; col < t.getMida(); col++) {
      for (int fila = 0; fila < t.getMida()-3; fila++) {
        h += evaluaLinea4(t, fila, col, 1, -1, colorJugadorIni);
      }      
    }
    return h;
  }
}