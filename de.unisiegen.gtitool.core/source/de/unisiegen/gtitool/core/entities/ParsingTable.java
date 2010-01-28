package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * the {@link ParsingTable} entity
 */
public interface ParsingTable extends Entity < ParsingTable >, Storable,
    Modifyable
{

  /**
   * Returns the set of productions at position (col,row)
   * 
   * @param col the column
   * @param row the row
   * @return set of productions at (col,row)
   */
  public TreeSet < Production > get ( int col, int row );


  /**
   * returns the set of productions at position (ts,ns)
   * 
   * @param ts the {@link TerminalSymbol} (the column identifier)
   * @param ns the {@link NonterminalSymbol} (the row identifier)
   * @return set of productions at (ts,ns)
   */
  public TreeSet < Production > get ( TerminalSymbol ts, NonterminalSymbol ns );
}
