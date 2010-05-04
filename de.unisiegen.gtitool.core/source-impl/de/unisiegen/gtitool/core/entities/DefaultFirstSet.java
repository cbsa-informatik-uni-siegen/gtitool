package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * Represents the FirstSet
 */
public class DefaultFirstSet implements FirstSet
{

  /**
   * serialversion
   */
  private static final long serialVersionUID = 2593661660846010642L;


  /**
   * set of terminal symbols
   */
  private final DefaultTerminalSymbolSet terminalSymbolSet;


  /**
   * defines whether epsilon is part of this set
   */
  private boolean epsilon;


  /**
   * modified status
   */
  private boolean modified;


  /**
   * Cached {@link PrettyString}
   */
  private PrettyString cachedPrettyString = null;


  /**
   * default ctor
   */
  public DefaultFirstSet ()
  {
    this.terminalSymbolSet = new DefaultTerminalSymbolSet ();
    this.epsilon = false;
    this.modified = false;
  }


  /**
   * default ctor
   * 
   * @param o The other {@link FirstSet}
   */
  public DefaultFirstSet ( final FirstSet o )
  {
    DefaultFirstSet other = ( DefaultFirstSet ) o;
    this.terminalSymbolSet = new DefaultTerminalSymbolSet (
        other.terminalSymbolSet );
    this.epsilon = other.epsilon;
    this.modified = other.modified;
  }


  /**
   * blub
   */
  public void unmarkAll ()
  {
    for ( TerminalSymbol ts : this.terminalSymbolSet )
      ts.setHighlighted ( false );
    this.cachedPrettyString = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#add(java.lang.Iterable)
   */
  public boolean add ( Iterable < TerminalSymbol > terminalSymbols )
  {
    this.modified = this.terminalSymbolSet.addIfNonexistent ( terminalSymbols );
    this.cachedPrettyString = null;
    return this.modified;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#add(de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public boolean add ( TerminalSymbol terminalSymbol )
  {
    this.modified = this.terminalSymbolSet.addIfNonexistent ( terminalSymbol );
    this.cachedPrettyString = null;
    return this.modified;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#modified()
   */
  public boolean modified ()
  {
    return this.modified;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#addTerminalSymbolSetChangedListener(de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener)
   */
  public void addTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.terminalSymbolSet.addTerminalSymbolSetChangedListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#clear()
   */
  public void clear ()
  {
    this.terminalSymbolSet.clear ();
    this.cachedPrettyString = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#contains(de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public boolean contains ( TerminalSymbol terminalSymbol )
  {
    return this.terminalSymbolSet.contains ( terminalSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#epsilon()
   */
  public boolean epsilon ()
  {
    return this.epsilon;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#epsilon(boolean)
   */
  public boolean epsilon ( @SuppressWarnings ( "hiding" ) final boolean epsilon )
  {
    if ( this.epsilon != epsilon )
    {
      this.epsilon = epsilon;
      this.modified = true;
      if ( this.epsilon )
        add ( new DefaultTerminalSymbol ( new DefaultSymbol () ) );
      else
      {
        TerminalSymbol epsilonSymbol = new DefaultTerminalSymbol (
            new DefaultSymbol () );
        for ( TerminalSymbol ts : this.terminalSymbolSet )
          if ( ts.getName ().equals ( epsilonSymbol.getName () ) )
            this.terminalSymbolSet.remove ( ts );
      }
      this.cachedPrettyString = null;
      return true;
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#get()
   */
  public TreeSet < TerminalSymbol > get ()
  {
    return this.terminalSymbolSet.get ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#get(int)
   */
  public TerminalSymbol get ( int index )
  {
    return this.terminalSymbolSet.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#remove(java.lang.Iterable)
   */
  public void remove ( Iterable < TerminalSymbol > terminalSymbols )
  {
    this.terminalSymbolSet.remove ( terminalSymbols );
    this.cachedPrettyString = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#remove(de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public void remove ( TerminalSymbol terminalSymbol )
  {
    this.terminalSymbolSet.remove ( terminalSymbol );
    this.cachedPrettyString = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#remove(de.unisiegen.gtitool.core.entities.TerminalSymbol[])
   */
  public void remove ( TerminalSymbol ... terminalSymbols )
  {
    this.terminalSymbolSet.remove ( terminalSymbols );
    this.cachedPrettyString = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#removeTerminalSymbolSetChangedListener(de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener)
   */
  public void removeTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.terminalSymbolSet.removeTerminalSymbolSetChangedListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#size()
   */
  public int size ()
  {
    return this.terminalSymbolSet.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return this.terminalSymbolSet.getParserOffset ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
    this.terminalSymbolSet.setParserOffset ( parserOffset );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.terminalSymbolSet.addPrettyStringChangedListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.terminalSymbolSet.removePrettyStringChangedListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( TerminalSymbolSet o )
  {
    return this.terminalSymbolSet.compareTo ( o );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    return this.terminalSymbolSet.getElement ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#addModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.terminalSymbolSet.addModifyStatusChangedListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return this.terminalSymbolSet.isModified ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#removeModifyStatusChangedListener(de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener)
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.terminalSymbolSet.removeModifyStatusChangedListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.terminalSymbolSet.resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Iterable#iterator()
   */
  public Iterator < TerminalSymbol > iterator ()
  {
    return this.terminalSymbolSet.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @SuppressWarnings ( "nls" )
  @Override
  public String toString ()
  {
    StringBuilder result = new StringBuilder ();
    result.append ( "{" );
    boolean deleteLast = false;
    for ( TerminalSymbol ts : this.terminalSymbolSet )
    {
      result.append ( ts );
      result.append ( "," );
      deleteLast = true;
    }
    if ( deleteLast )
      result.deleteCharAt ( result.length () - 1 );
    else
      result.append ( " " );
    result.append ( "}" );
    return result.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  @SuppressWarnings ( "nls" )
  public PrettyString toPrettyString ()
  {
    if ( this.cachedPrettyString == null )
    {
      this.cachedPrettyString = new PrettyString ();
      this.cachedPrettyString.add ( new PrettyToken ( "{", Style.NONE ) );
      boolean deleteLast = false;
      for ( TerminalSymbol ts : this.terminalSymbolSet )
      {
        this.cachedPrettyString.add ( ts );
        this.cachedPrettyString.add ( new PrettyToken ( ",", Style.NONE ) );
        deleteLast = true;
      }
      if ( deleteLast )
        this.cachedPrettyString.removeLastPrettyToken ();
      else
        this.cachedPrettyString.add ( new PrettyToken ( " ", Style.NONE ) );
      this.cachedPrettyString.add ( new PrettyToken ( "}", Style.NONE ) );
    }
    return this.cachedPrettyString;
  }
}
