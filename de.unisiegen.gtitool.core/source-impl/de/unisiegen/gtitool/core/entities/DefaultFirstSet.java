package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
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
   * default ctor
   */
  public DefaultFirstSet ()
  {
    this.terminalSymbolSet = new DefaultTerminalSymbolSet ();
    this.epsilon = false;
    this.modified = false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#add(java.lang.Iterable)
   */
  public boolean add ( Iterable < TerminalSymbol > terminalSymbols )
  {
    this.modified = this.terminalSymbolSet.addIfNonexistent ( terminalSymbols );
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
  public void epsilon ( @SuppressWarnings ( "hiding" ) final boolean epsilon )
  {
    this.epsilon = epsilon;
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
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#remove(de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public void remove ( TerminalSymbol terminalSymbol )
  {
    this.terminalSymbolSet.remove ( terminalSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.FirstSet#remove(de.unisiegen.gtitool.core.entities.TerminalSymbol[])
   */
  public void remove ( TerminalSymbol ... terminalSymbols )
  {
    this.terminalSymbolSet.remove ( terminalSymbols );
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
  @Override
  public String toString ()
  {
    return this.terminalSymbolSet.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.parser.style.PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    return this.terminalSymbolSet.toPrettyString ();
  }
}
