package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.PrettyString.PrettyStringMode;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The {@link DefaultProductionWord} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultProductionWord implements ProductionWord
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7575345248390546325L;


  /**
   * The {@link EventListenerList}.
   */
  private final EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DefaultAlphabet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The {@link ProductionWordMember} list.
   */
  private final ArrayList < ProductionWordMember > productionWordMemberList;


  /**
   * The initial {@link ProductionWordMember} list.
   */
  private final ArrayList < ProductionWordMember > initialProductionWordMemberList;


  /**
   * The cached {@link PrettyString}.
   */
  private PrettyString cachedPrettyString = null;


  /**
   * The {@link PrettyStringChangedListener}.
   */
  private final PrettyStringChangedListener prettyStringChangedListener;


  /**
   * Allocates a new {@link DefaultProductionWord}.
   */
  public DefaultProductionWord ()
  {
    this.prettyStringChangedListener = new PrettyStringChangedListener ()
    {

      public void prettyStringChanged ()
      {
        firePrettyStringChanged ();
      }
    };

    this.productionWordMemberList = new ArrayList < ProductionWordMember > ();
    this.initialProductionWordMemberList = new ArrayList < ProductionWordMember > ();

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultProductionWord}.
   * 
   * @param element The {@link Element}.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultProductionWord ( Element element ) throws StoreException
  {
    this ();

    // Check if the element is correct
    if ( !element.getName ().equals ( "ProductionWord" ) ) //$NON-NLS-1$
      throw new IllegalArgumentException ( "element " + Messages.QUOTE //$NON-NLS-1$
          + element.getName () + Messages.QUOTE + " is not a production word" ); //$NON-NLS-1$

    // Element
    for ( Element current : element.getElement () )
      if ( current.getName ().equals ( "TerminalSymbol" ) ) //$NON-NLS-1$
        add ( new DefaultTerminalSymbol ( current ) );
      else if ( current.getName ().equals ( "NonterminalSymbol" ) ) //$NON-NLS-1$
        add ( new DefaultNonterminalSymbol ( current ) );
      else
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$

    // Attribute
    if ( element.getAttribute ().size () > 0 )
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultProductionWord}.
   * 
   * @param productionWordMembers The array of {@link ProductionWordMember}s.
   */
  public DefaultProductionWord (
      Iterable < ProductionWordMember > productionWordMembers )
  {
    this ();

    // ProductionWordMember
    if ( productionWordMembers == null )
      throw new NullPointerException ( "production word members is null" ); //$NON-NLS-1$
    add ( productionWordMembers );

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultProductionWord}.
   * 
   * @param productionWordMembers The array of {@link ProductionWordMember}s.
   */
  public DefaultProductionWord ( ProductionWordMember ... productionWordMembers )
  {
    this ();
    // ProductionWordMember
    if ( productionWordMembers == null )
      throw new NullPointerException ( "production word members is null" ); //$NON-NLS-1$
    add ( productionWordMembers );

    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionWord#add(Iterable)
   */
  public final void add (
      Iterable < ProductionWordMember > productionWordMembers )
  {
    if ( productionWordMembers == null )
      throw new NullPointerException ( "production word members is null" ); //$NON-NLS-1$
    for ( ProductionWordMember current : productionWordMembers )
      add ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionWord#add(ProductionWordMember)
   */
  public final void add ( ProductionWordMember productionWordMember )
  {
    // ProductionWordMember
    if ( productionWordMember == null )
      throw new NullPointerException ( "production word member is null" ); //$NON-NLS-1$

    productionWordMember
        .addPrettyStringChangedListener ( this.prettyStringChangedListener );

    this.productionWordMemberList.add ( productionWordMember );

    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionWord#add(ProductionWordMember[])
   */
  public final void add ( ProductionWordMember ... productionWordMembers )
  {
    if ( productionWordMembers == null )
      throw new NullPointerException ( "production word members is null" ); //$NON-NLS-1$
    for ( ProductionWordMember current : productionWordMembers )
      add ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#addPrettyStringChangedListener(PrettyStringChangedListener)
   */
  public final void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.add ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( ProductionWord other )
  {
    ArrayList < ProductionWordMember > firstList = new ArrayList < ProductionWordMember > ();
    ArrayList < ProductionWordMember > secondList = new ArrayList < ProductionWordMember > ();

    firstList.addAll ( this.productionWordMemberList );
    secondList.addAll ( other.get () );

    int minSize = firstList.size () < secondList.size () ? firstList.size ()
        : secondList.size ();

    for ( int i = 0 ; i < minSize ; i++ )
    {
      int compare = firstList.get ( i ).getName ().compareTo (
          secondList.get ( i ).getName () );
      if ( compare != 0 )
        return compare;
    }

    if ( firstList.size () == secondList.size () )
      return 0;

    return firstList.size () < secondList.size () ? -1 : 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultProductionWord )
    {
      DefaultProductionWord defaultProductionWord = ( DefaultProductionWord ) other;
      return this.productionWordMemberList
          .equals ( defaultProductionWord.productionWordMemberList );
    }
    return false;
  }


  /**
   * Let the listeners know that the {@link PrettyString} has changed.
   */
  protected final void firePrettyStringChanged ()
  {
    this.cachedPrettyString = null;

    PrettyStringChangedListener [] listeners = this.listenerList
        .getListeners ( PrettyStringChangedListener.class );
    for ( PrettyStringChangedListener current : listeners )
      current.prettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionWord#get()
   */
  public final ArrayList < ProductionWordMember > get ()
  {
    return this.productionWordMemberList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionWord#get(int)
   */
  public final ProductionWordMember get ( int index )
  {
    return this.productionWordMemberList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "ProductionWord" ); //$NON-NLS-1$
    for ( ProductionWordMember current : this.productionWordMemberList )
      newElement.addElement ( current );
    return newElement;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionWord#getNonterminals()
   */
  public ArrayList < NonterminalSymbol > getNonterminals ()
  {
    ArrayList < NonterminalSymbol > symbols = new ArrayList < NonterminalSymbol > ();
    for ( ProductionWordMember m : this.productionWordMemberList )
      if ( m instanceof NonterminalSymbol )
        symbols.add ( ( NonterminalSymbol ) m );
    return symbols;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#getParserOffset()
   */
  public final ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.productionWordMemberList.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    if ( !this.initialProductionWordMemberList
        .equals ( this.productionWordMemberList ) )
      return true;
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < ProductionWordMember > iterator ()
  {
    return this.productionWordMemberList.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#removePrettyStringChangedListener(PrettyStringChangedListener)
   */
  public final void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.remove ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialProductionWordMemberList.clear ();
    this.initialProductionWordMemberList
        .addAll ( this.productionWordMemberList );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#setParserOffset(ParserOffset)
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see ProductionWord#size()
   */
  public final int size ()
  {
    return this.productionWordMemberList.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public final PrettyString toPrettyString ()
  {
    if ( ( this.cachedPrettyString == null )
        || PrettyString.MODE.equals ( PrettyStringMode.CACHING_OFF ) )
    {
      this.cachedPrettyString = new PrettyString ();
      if ( this.productionWordMemberList.size () == 0 )
        this.cachedPrettyString.add ( new PrettyToken (
            "\u03B5", Style.TERMINAL_SYMBOL ) ); //$NON-NLS-1$
      else
        for ( ProductionWordMember current : this.productionWordMemberList )
          this.cachedPrettyString.add ( current );
    }

    return this.cachedPrettyString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  @Override
  public final String toString ()
  {
    StringBuilder result = new StringBuilder ();
    if ( this.productionWordMemberList.size () == 0 )
      result.append ( "\u03B5" ); //$NON-NLS-1$
    else
      for ( ProductionWordMember current : this.productionWordMemberList )
        result.append ( current.getName () );
    return result.toString ();
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionWord#epsilon()
   */
  public final boolean epsilon(){
    return this.productionWordMemberList.size() == 0;
  }
  
  
  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.ProductionWord#contains(de.unisiegen.gtitool.core.entities.ProductionWordMember)
   */
  public boolean contains(ProductionWordMember pwm)
  {
    for(ProductionWordMember p : this.productionWordMemberList)
      if(p.equals ( pwm ))
        return true;
    return false;
  }
}
