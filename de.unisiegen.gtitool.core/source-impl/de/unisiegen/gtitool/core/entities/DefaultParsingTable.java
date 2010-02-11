package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * represents the parsing table
 */
public class DefaultParsingTable implements ParsingTable
{

  /**
   * the generated serial
   */
  private static final long serialVersionUID = -1602679123085565866L;


  /**
   * the set of terminals
   */
  private final TerminalSymbolSet terminals;


  /**
   * the set of nonterminals
   */
  private final NonterminalSymbolSet nonterminals;


  /**
   * the parsing table entries: [TerminalSymbol][NonterminalSymbol] =
   * TreeSet<Production>
   */
  private ArrayList < ArrayList < TreeSet < Production >>> parsingTable;


  /**
   * allocates a new {@link DefaultParsingTable}
   * 
   * @param cfg the CFG from which we're creating the parsing table
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  public DefaultParsingTable ( final CFG cfg )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    if ( cfg == null )
      throw new NullPointerException ( "cfg is null" ); //$NON-NLS-1$

    this.terminals = cfg.getTerminalSymbolSet ();
    this.nonterminals = cfg.getNonterminalSymbolSet ();
    createParsingTable ( cfg );
  }


  /**
   * creates the parsing table out of a context free grammar
   * 
   * @param cfg the {@link CFG} from which we're creating the parsing table
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  private void createParsingTable ( final CFG cfg )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    //TODO: implement
//    this.parsingTable = new ArrayList < ArrayList < TreeSet < Production >> > ();
//
//    int i = 0; // row counter
//    for ( NonterminalSymbol ns : this.nonterminals )
//    {
//      // create one row in our parsing table
//      this.parsingTable.add ( new ArrayList < TreeSet < Production > > () );
//
//      // calculate first set for each production ns -> a
//      ArrayList < Production > prods = cfg.getProductionForNonTerminal ( ns );
//      ArrayList < FirstSet > fsProds = new ArrayList < FirstSet > ();
//      for ( Production prod : prods )
//        fsProds.add ( cfg.first ( prod.getProductionWord () ) );
//
//      // and calculate set of productions for entry parsingTable[ns,ts]
//      for ( TerminalSymbol ts : this.terminals )
//      {
//        // create one column in our parsing table
//        this.parsingTable.get ( i ).add ( new TreeSet < Production > () );
//        int j = 0; // column counter
//
//        for ( Production p : prods )
//          if ( cfg.first ( p.getProductionWord () ).contains ( ts )
//              || ( cfg.first ( p.getProductionWord () ).epsilon () && cfg
//                  .follow ( ns ).contains ( ts ) ) )
//            this.parsingTable.get ( i ).get ( j ).add ( p );
//
//      }
//      ++i;
//    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#get(int, int)
   */
  public TreeSet < Production > get ( int row, int col )
  {
    return this.parsingTable.get ( row ).get ( col );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ParsingTable#get(de.unisiegen.gtitool.core.entities.NonterminalSymbol,
   *      de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public TreeSet < Production > get ( NonterminalSymbol ns, TerminalSymbol ts )
  {
    int col = getTerminalSymbolIndex ( ts );
    int row = getNonterminalSymbolIndex ( ns );
    if ( col == -1 || row == -1 )
      return new TreeSet < Production > ();
    return get ( row, col );
  }


  /**
   * returns the corresponding index of a terminal symbol
   * 
   * @param ts the terminal symbol for which we want to determin the index
   * @return index of the terminal symbol or -1 if it is not in the set
   */
  private int getTerminalSymbolIndex ( TerminalSymbol ts )
  {
    for ( int i = 0 ; i < this.terminals.size () ; ++i )
      if ( this.terminals.get ( i ).getName ().equals ( ts.getName () ) )
        return i;
    return -1;
  }


  /**
   * returns the corresponding index of a nonterminal symbol
   * 
   * @param ns the nonterminal symbol for which we want to determin the index
   * @return index of the nonterminal symbol or -1 if it is not in the set
   */
  private int getNonterminalSymbolIndex ( NonterminalSymbol ns )
  {
    for ( int i = 0 ; i < this.nonterminals.size () ; ++i )
      if ( this.nonterminals.get ( i ).getName ().equals ( ns.getName () ) )
        return i;
    return -1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( ParsingTable o )
  {
    return 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
  }

}
