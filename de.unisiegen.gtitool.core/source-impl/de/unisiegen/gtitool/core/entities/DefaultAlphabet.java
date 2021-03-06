package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetMoreThanOneSymbolException;
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
 * The {@link DefaultAlphabet} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class DefaultAlphabet implements Alphabet
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4488353013296552669L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DefaultAlphabet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The set of {@link Symbol}s.
   */
  protected TreeSet < Symbol > symbolSet;


  /**
   * The initial set of {@link Symbol}s.
   */
  private TreeSet < Symbol > initialSymbolSet;


  /**
   * The cached {@link PrettyString}.
   */
  private PrettyString cachedPrettyString = null;


  /**
   * The {@link PrettyStringChangedListener}.
   */
  private PrettyStringChangedListener prettyStringChangedListener;


  /**
   * Allocates a new {@link DefaultAlphabet}.
   */
  public DefaultAlphabet ()
  {
    this.prettyStringChangedListener = new PrettyStringChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void prettyStringChanged ()
      {
        firePrettyStringChanged ();
      }
    };

    // SymbolSet
    this.symbolSet = new TreeSet < Symbol > ();
    this.initialSymbolSet = new TreeSet < Symbol > ();

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultAlphabet}.
   * 
   * @param element The {@link Element}.
   * @throws AlphabetException If something with the {@link DefaultAlphabet} is
   *           not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultAlphabet ( Element element ) throws AlphabetException,
      StoreException
  {
    this ();

    // Check if the element is correct
    if ( !element.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element " + Messages.QUOTE //$NON-NLS-1$
          + element.getName () + Messages.QUOTE + " is not a alphabet" ); //$NON-NLS-1$
    }

    // Attribute
    if ( element.getAttribute ().size () > 0 )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
    }

    // Element
    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
      {
        add ( new DefaultSymbol ( current ) );
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultAlphabet}.
   * 
   * @param symbols The array of {@link Symbol}s.
   * @throws AlphabetException If something with the {@link DefaultAlphabet} is
   *           not correct.
   */
  public DefaultAlphabet ( Iterable < Symbol > symbols )
      throws AlphabetException
  {
    this ();

    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( symbols );

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultAlphabet}.
   * 
   * @param symbols The array of {@link Symbol}s.
   * @throws AlphabetException If something with the {@link DefaultAlphabet} is
   *           not correct.
   */
  public DefaultAlphabet ( Symbol ... symbols ) throws AlphabetException
  {
    this ();

    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( symbols );

    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#add(Iterable)
   */
  public final void add ( Iterable < Symbol > symbols )
      throws AlphabetException
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( Symbol current : symbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#add(Symbol)
   */
  public void add ( Symbol symbol ) throws AlphabetException
  {
    // Symbol
    if ( symbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    /*
     * Throws an AlphabetException if the symbol which should be added is
     * already in this Alphabet.
     */
    if ( this.symbolSet.contains ( symbol ) )
    {
      ArrayList < Symbol > negativeSymbols = new ArrayList < Symbol > ();
      for ( Symbol current : this.symbolSet )
      {
        if ( symbol.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      negativeSymbols.add ( symbol );
      throw new AlphabetMoreThanOneSymbolException ( this, negativeSymbols );
    }

    symbol.addPrettyStringChangedListener ( this.prettyStringChangedListener );

    this.symbolSet.add ( symbol );

    fireAlphabetChanged ();
    fireModifyStatusChanged ();
    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#add(Symbol[])
   */
  public void add ( Symbol ... symbols ) throws AlphabetException
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( Symbol current : symbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#addAlphabetChangedListener(AlphabetChangedListener)
   */
  public final void addAlphabetChangedListener (
      AlphabetChangedListener listener )
  {
    this.listenerList.add ( AlphabetChangedListener.class, listener );
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
   * Checks the {@link Symbol} list for {@link Symbol}s with the same name.
   * 
   * @param symbols The {@link Symbol} list.
   * @throws AlphabetException If a {@link Symbol} is duplicated.
   */
  private final void checkDuplicated ( ArrayList < Symbol > symbols )
      throws AlphabetException
  {
    Symbol duplicated = null;
    loop : for ( int i = 0 ; i < symbols.size () ; i++ )
    {
      for ( int j = i + 1 ; j < symbols.size () ; j++ )
      {
        if ( symbols.get ( i ).equals ( symbols.get ( j ) ) )
        {
          duplicated = symbols.get ( i );
          break loop;
        }
      }
    }
    if ( duplicated != null )
    {
      ArrayList < Symbol > negativeSymbols = new ArrayList < Symbol > ();
      for ( Symbol current : symbols )
      {
        if ( duplicated.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      throw new AlphabetMoreThanOneSymbolException ( this, negativeSymbols );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#clear()
   */
  public final void clear ()
  {
    for ( Symbol current : this.symbolSet )
    {
      current
          .removePrettyStringChangedListener ( this.prettyStringChangedListener );
    }

    this.symbolSet.clear ();
    fireAlphabetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( Alphabet other )
  {
    ArrayList < Symbol > firstList = new ArrayList < Symbol > ();
    ArrayList < Symbol > secondList = new ArrayList < Symbol > ();

    firstList.addAll ( this.symbolSet );
    secondList.addAll ( other.get () );

    int minSize = firstList.size () < secondList.size () ? firstList.size ()
        : secondList.size ();

    for ( int i = 0 ; i < minSize ; i++ )
    {
      int compare = firstList.get ( i ).compareTo ( secondList.get ( i ) );
      if ( compare != 0 )
      {
        return compare;
      }
    }

    if ( firstList.size () == secondList.size () )
    {
      return 0;
    }

    return firstList.size () < secondList.size () ? -1 : 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#contains(Symbol)
   */
  public boolean contains ( Symbol symbol )
  {
    return this.symbolSet.contains ( symbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultAlphabet )
    {
      DefaultAlphabet defaultAlphabet = ( DefaultAlphabet ) other;
      return this.symbolSet.equals ( defaultAlphabet.symbolSet );
    }
    return false;
  }


  /**
   * Let the listeners know that the {@link Alphabet} has changed.
   */
  private final void fireAlphabetChanged ()
  {
    AlphabetChangedListener [] listeners = this.listenerList
        .getListeners ( AlphabetChangedListener.class );
    for ( AlphabetChangedListener current : listeners )
    {
      current.alphabetChanged ( this );
    }
  }


  /**
   * Let the listeners know that the modify status has changed.
   */
  private final void fireModifyStatusChanged ()
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    boolean newModifyStatus = isModified ();
    for ( ModifyStatusChangedListener current : listeners )
    {
      current.modifyStatusChanged ( newModifyStatus );
    }
  }


  /**
   * Let the listeners know that the {@link PrettyString} has changed.
   */
  private final void firePrettyStringChanged ()
  {
    this.cachedPrettyString = null;

    PrettyStringChangedListener [] listeners = this.listenerList
        .getListeners ( PrettyStringChangedListener.class );
    for ( PrettyStringChangedListener current : listeners )
    {
      current.prettyStringChanged ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#get()
   */
  public final TreeSet < Symbol > get ()
  {
    return this.symbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#get(int)
   */
  public final Symbol get ( int index )
  {
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    for ( int i = 0 ; i < index ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "Alphabet" ); //$NON-NLS-1$
    for ( Symbol current : this.symbolSet )
    {
      newElement.addElement ( current );
    }
    return newElement;
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
    return this.symbolSet.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( !this.symbolSet.equals ( this.initialSymbolSet ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolSet.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#remove(Iterable)
   */
  public final void remove ( Iterable < Symbol > symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      remove ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#remove(Symbol)
   */
  public final void remove ( Symbol symbol )
  {
    if ( symbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    if ( !this.symbolSet.contains ( symbol ) )
    {
      throw new IllegalArgumentException ( "symbol is not in this alphabet" ); //$NON-NLS-1$
    }

    symbol
        .removePrettyStringChangedListener ( this.prettyStringChangedListener );

    this.symbolSet.remove ( symbol );

    fireAlphabetChanged ();
    fireModifyStatusChanged ();
    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#remove(Symbol[])
   */
  public final void remove ( Symbol ... symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      remove ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Alphabet#removeAlphabetChangedListener(AlphabetChangedListener)
   */
  public final void removeAlphabetChangedListener (
      AlphabetChangedListener listener )
  {
    this.listenerList.remove ( AlphabetChangedListener.class, listener );
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
    this.initialSymbolSet.clear ();
    this.initialSymbolSet.addAll ( this.symbolSet );
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
   * @see Alphabet#size()
   */
  public final int size ()
  {
    return this.symbolSet.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    if ( ( this.cachedPrettyString == null )
        || PrettyString.MODE.equals ( PrettyStringMode.CACHING_OFF ) )
    {
      this.cachedPrettyString = new PrettyString ();
      this.cachedPrettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
      boolean first = true;

      ArrayList < Symbol > t = new ArrayList < Symbol > ();
      t.addAll ( this.symbolSet );
      while ( !t.isEmpty () )
      {
        ArrayList < Symbol > a = DefaultRegexAlphabet.checkForClass ( t );

        if ( !first )
        {
          this.cachedPrettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
        }
        first = false;

        if ( a.size () == 1 )
        {
          this.cachedPrettyString.add ( a.get ( 0 ) );
        }
        else if ( a.size () == 2 )
        {

          this.cachedPrettyString.add ( a.get ( 0 ) );
          this.cachedPrettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
          this.cachedPrettyString.add ( a.get ( 1 ) );
        }
        else
        {
          this.cachedPrettyString.add ( a.get ( 0 ) );
          this.cachedPrettyString.add ( new PrettyToken ( "..", Style.NONE ) ); //$NON-NLS-1$
          this.cachedPrettyString.add ( a.get ( a.size () - 1 ) );
        }
        t.removeAll ( a );
      }
      this.cachedPrettyString.add ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
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
    result.append ( "{" ); //$NON-NLS-1$
    boolean first = true;

    ArrayList < Symbol > t = new ArrayList < Symbol > ();
    t.addAll ( this.symbolSet );
    while ( !t.isEmpty () )
    {
      ArrayList < Symbol > a = DefaultRegexAlphabet.checkForClass ( t );

      if ( !first )
      {
        result.append ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      }
      first = false;

      if ( a.size () == 1 )
      {
        result.append ( a.get ( 0 ) );
      }
      else if ( a.size () == 2 )
      {

        result.append ( a.get ( 0 ) );
        result.append ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
        result.append ( a.get ( 1 ) );
      }
      else
      {
        result.append ( new PrettyToken ( "[", Style.SYMBOL ) ); //$NON-NLS-1$
        result.append ( a.get ( 0 ) );
        result.append ( new PrettyToken ( "-", Style.NONE ) ); //$NON-NLS-1$
        result.append ( a.get ( a.size () - 1 ) );
        result.append ( new PrettyToken ( "]", Style.SYMBOL ) ); //$NON-NLS-1$
      }
      t.removeAll ( a );
    }
    result.append ( "}" ); //$NON-NLS-1$
    return result.toString ();
  }
}
