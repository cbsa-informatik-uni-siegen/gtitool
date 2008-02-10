package de.unisiegen.gtitool.core.entities;


import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link DefaultProduction} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultProduction implements Production
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4383623142476990175L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DefaultProduction} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The {@link NonterminalSymbol}
   */
  NonterminalSymbol nonTerminalSymbol;


  /**
   * The {@link ProductionWord}
   */
  ProductionWord productionWord;


  /**
   * Allocates a new {@link DefaultProduction}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol}.
   * @param productionWord The {@link ProductionWord}.
   */
  public DefaultProduction ( NonterminalSymbol nonterminalSymbol,
      ProductionWord productionWord )
  {
    this.nonTerminalSymbol = nonterminalSymbol;
    this.productionWord = productionWord;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final DefaultProduction clone ()
  {
    DefaultProduction newDefaultProduction = new DefaultProduction (
        this.nonTerminalSymbol.clone (), this.productionWord.clone () );
    return newDefaultProduction;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( @SuppressWarnings ( "unused" )
  Production other )
  {
    // TODO implement me
    return 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( @SuppressWarnings ( "unused" )
  Object other )
  {
    if ( other instanceof DefaultProduction )
    {
      DefaultProduction defaultProduction = ( DefaultProduction ) other;
      if ( this.productionWord.equals ( defaultProduction.getProductionWord () )
          && this.nonTerminalSymbol.equals ( defaultProduction
              .getNonterminalSymbol () ) )
      {
        return true;
      }
    }
    return false;
  }


  /**
   * Let the listeners know that the modify status has changed.
   */
  @SuppressWarnings ( "unused" )
  private final void fireModifyStatusChanged ()
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    boolean newModifyStatus = isModified ();
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].modifyStatusChanged ( newModifyStatus );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "DefaultProduction" ); //$NON-NLS-1$
    Element nonTerminalSymbolElement = this.nonTerminalSymbol.getElement ();
    nonTerminalSymbolElement.setName ( "NonTerminalSymbol" ); //$NON-NLS-1$
    newElement.addElement ( nonTerminalSymbolElement );
    Element productionWordElement = this.productionWord.getElement ();
    productionWordElement.setName ( "ProductionWord" ); //$NON-NLS-1$
    newElement.addElement ( productionWordElement );
    return newElement;
  }


  /**
   * {@inheritDoc}
   */
  public final ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    // TODO implement me
    return 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    // TODO implement me
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    // TODO implement me
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public final PrettyString toPrettyString ()
  {
    // TODO implement me
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  @Override
  public final String toString ()
  {
    // TODO implement me
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toStringDebug()
   */
  public final String toStringDebug ()
  {
    // TODO implement me
    return null;
  }


  /**
   * {@inheritDoc}
   */
  public NonterminalSymbol getNonterminalSymbol ()
  {
    return this.nonTerminalSymbol;
  }


  /**
   * {@inheritDoc}
   */
  public ProductionWord getProductionWord ()
  {
    return this.productionWord;
  }
}
