package de.unisiegen.gtitool.core.entities;


import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


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
  private NonterminalSymbol nonterminalSymbol;
  
  /**
   * The initial {@link NonterminalSymbol}
   */
  private NonterminalSymbol initialNonterminalSymbol;


  /**
   * The {@link ProductionWord}
   */
  private ProductionWord productionWord;
  
  /**
   * The initial {@link ProductionWord}.
   */
  private ProductionWord initialProductionWord;

  /**
   * Allocates a new {@link DefaultProduction}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol}.
   * @param productionWord The {@link ProductionWord}.
   */
  public DefaultProduction ( NonterminalSymbol nonterminalSymbol,
      ProductionWord productionWord )
  {
    this.nonterminalSymbol = nonterminalSymbol;
    this.productionWord = productionWord;
    this.initialNonterminalSymbol = nonterminalSymbol.clone ();
    this.initialProductionWord = productionWord.clone ();
  }


  /**
   * Allocates a new {@link DefaultProduction}.
   * 
   * @param element The {@link Element}.
   * @throws StoreException If the {@link Element} can not be parsed.
   * @throws TerminalSymbolException If something with the
   *           {@link TerminalSymbol} is not correct.
   * @throws NonterminalSymbolException If something with the
   *           {@link NonterminalSymbol} is not correct.
   */
  public DefaultProduction ( Element element ) throws StoreException,
      TerminalSymbolException, NonterminalSymbolException
  {

    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "NonterminalSymbol" ) ) //$NON-NLS-1$
      {
        this.nonterminalSymbol = new DefaultNonterminalSymbol ( current );
      }

      else if ( current.getName ().equals ( "ProductionWord" ) ) //$NON-NLS-1$
      {
        this.productionWord = new DefaultProductionWord ( current );
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }

    if ( this.nonterminalSymbol == null || this.productionWord == null )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }
    this.initialNonterminalSymbol = this.nonterminalSymbol.clone ();
    this.initialProductionWord = this.productionWord.clone ();
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
        this.nonterminalSymbol.clone (), this.productionWord.clone () );
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
          && this.nonterminalSymbol.equals ( defaultProduction
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
    Element newElement = new Element ( "Production" ); //$NON-NLS-1$
    newElement.addElement ( this.nonterminalSymbol.getElement () );
    newElement.addElement ( this.productionWord.getElement () );
    return newElement;
  }


  /**
   * {@inheritDoc}
   */
  public NonterminalSymbol getNonterminalSymbol ()
  {
    return this.nonterminalSymbol;
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
   */
  public final ProductionWord getProductionWord ()
  {
    return this.productionWord;
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
    if(!this.productionWord.equals ( this.initialProductionWord )){
      return true;
    }
    if (!this.nonterminalSymbol.equals ( this.initialNonterminalSymbol ))
    {
      return true;
    }
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
   this.initialNonterminalSymbol = this.nonterminalSymbol.clone ();
   this.productionWord.resetModify ();
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
    PrettyString prettyString = new PrettyString ();
    prettyString.addPrettyPrintable ( this.nonterminalSymbol );
    prettyString.addPrettyToken ( new PrettyToken ( " ", Style.NONE ) ); //$NON-NLS-1$
    prettyString.addPrettyToken ( new PrettyToken ( "\u2192", Style.NONE ) ); //$NON-NLS-1$
    prettyString.addPrettyToken ( new PrettyToken ( " ", Style.NONE ) ); //$NON-NLS-1$
    prettyString.addPrettyPrintable ( this.productionWord );
    return prettyString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  @Override
  public final String toString ()
  {
    return this.nonterminalSymbol.toString () + " \u2192 " //$NON-NLS-1$
        + this.productionWord.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toStringDebug()
   */
  public final String toStringDebug ()
  {
    return this.nonterminalSymbol.toStringDebug () + " \u2192 " + //$NON-NLS-1$
        this.productionWord.toStringDebug ();
  }


  /**
   * {@inheritDoc}
   * 
   * @param nonterminalSymbol
   * @see de.unisiegen.gtitool.core.entities.Production#setNonterminalSymbol(de.unisiegen.gtitool.core.entities.NonterminalSymbol)
   */
  public void setNonterminalSymbol ( NonterminalSymbol nonterminalSymbol )
  {
    this.nonterminalSymbol = nonterminalSymbol;
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @param productionWord
   * @see de.unisiegen.gtitool.core.entities.Production#setProductionWord(de.unisiegen.gtitool.core.entities.ProductionWord)
   */
  public void setProductionWord ( ProductionWord productionWord )
  {
    this.productionWord = productionWord;
    System.err.println(productionWord);
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Production#contains(de.unisiegen.gtitool.core.entities.NonterminalSymbol)
   */
  public boolean contains ( NonterminalSymbol symbol )
  {
    for (ProductionWordMember current : this.productionWord){
      if (current.equals ( symbol )){
        return true;
      }
    }
    return this.nonterminalSymbol.equals ( symbol );
  }


  /**
   * {@inheritDoc}
   *
   * @see de.unisiegen.gtitool.core.entities.Production#contains(de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public boolean contains ( TerminalSymbol symbol )
  {
    for (ProductionWordMember current : this.productionWord){
      if (current.equals ( symbol )){
        return true;
      }
    }
    return false;
  }
}
