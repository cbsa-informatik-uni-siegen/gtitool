package de.unisiegen.gtitool.core.entities;


import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.listener.ParsingTableStepByStepListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * the {@link ParsingTable} entity
 */
public interface ParsingTable extends Entity < ParsingTable >, Storable
{

  /**
   * What causes a {@link Production} to be added as a {@link ParsingTable}
   * entry during the step by step creation
   * 
   * @author Christian Uhrhan
   */
  public enum EntryCause implements EntityType
  {
    /**
     * The current processed {@link TerminalSymbol} lies in the first set of the
     * {@link Production}s {@link ProductionWord}
     */
    TERMINAL_IN_FIRSTSET,

    /**
     * The current considered {@link Production}s {@link ProductionWord} derives
     * to epsilon and the current processed {@link TerminalSymbol} lies in the
     * follow set of the current processed {@link NonterminalSymbol}
     */
    EPSILON_DERIVATION_AND_FOLLOWSET,

    /**
     * No add action takes place
     */
    NOCAUSE;

    /**
     * {@inheritDoc}
     * 
     * @see Enum#toString()
     */
    @Override
    public final String toString ()
    {
      switch ( this )
      {
        case TERMINAL_IN_FIRSTSET :
          return "TERMINAL_IN_FIRSTSET"; //$NON-NLS-1$
        case EPSILON_DERIVATION_AND_FOLLOWSET :
          return "EPSILON_DERIVATION_AND_FOLLOWSET"; //$NON-NLS-1$
        case NOCAUSE :
          return "NOCAUSE"; //$NON-NLS-1$
      }
      throw new IllegalArgumentException ( "unsupported machine type" ); //$NON-NLS-1$
    }
  }


  /**
   * creates the {@link ParsingTable}
   * 
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  public void create () throws GrammarInvalidNonterminalException,
      TerminalSymbolSetException;


  /**
   * Returns the set of productions at position (col,row)
   * 
   * @param col the column
   * @param row the row
   * @return set of productions at (col,row)
   */
  public DefaultProductionSet get ( int row, int col );


  /**
   * returns the set of productions at position (ts,ns)
   * 
   * @param ts the {@link TerminalSymbol} (the column identifier)
   * @param ns the {@link NonterminalSymbol} (the row identifier)
   * @return set of productions at (ts,ns)
   */
  public DefaultProductionSet get ( NonterminalSymbol ns, TerminalSymbol ts );


  /**
   * returns number of columns
   * 
   * @return number of columns
   */
  public int getColumnCount ();


  /**
   * returns number of rows
   * 
   * @return number of rows
   */
  public int getRowCount ();


  /*
   * Methods for creating the parsing table stepwise
   */
  /**
   * starts creating the {@link ParsingTable} step by step
   */
  public void createParsingTableStart ();


  /**
   * Do the next step in creating the {@link ParsingTable} step by step
   * 
   * @return true if there is a next step, false otherwise
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  public boolean createParsingTableNextStep ()
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException;


  /**
   * Do the previous step in creating the {@link ParsingTable} step by step
   * 
   * @return true if there is still a previous step possible, false otherwise
   */
  public boolean createParsingTablePreviousStep ();


  /**
   * Returns the current {@link NonterminalSymbol} used in the step by step mode
   * 
   * @return The current {@link NonterminalSymbol}
   */
  public NonterminalSymbol getCurrentNonterminalSymbol ();


  /**
   * Returns the current {@link TerminalSymbol} used in the step by step mode
   * 
   * @return The current {@link TerminalSymbol}
   */
  public TerminalSymbol getCurrentTerminalSymbol ();


  /**
   * Adds a {@link ParsingTableStepByStepListener} to the
   * {@link EventListenerList}
   * 
   * @param listener The {@link ParsingTableStepByStepListener}
   */
  public void addParsingTableStepByStepListener (
      final ParsingTableStepByStepListener listener );
}
