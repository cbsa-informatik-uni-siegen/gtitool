package de.unisiegen.gtitool.core.entities;


import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.PrettyString.PrettyStringMode;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The {@link DefaultSymbol} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultSymbol implements Symbol
{

  /**
   * the serial verion uid.
   */
  private static final long serialVersionUID = 121593210993378021L;


  /**
   * The name of this state.
   */
  private String name = null;


  /**
   * This {@link Symbol} is a error {@link Symbol}.
   */
  private boolean error = false;


  /**
   * This {@link Symbol} is a active {@link Symbol}.
   */
  private boolean active = false;


  /**
   * The offset of this {@link DefaultSymbol} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Flag that indicates if this {@link Symbol} is a epsilon {@link Symbol}.
   */
  private boolean epsilon;


  /**
   * The cached {@link PrettyString}.
   */
  private PrettyString cachedPrettyString = null;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * Allocates a new {@link DefaultSymbol}.
   */
  public DefaultSymbol ()
  {
    this.epsilon = true;
  }


  /**
   * Allocates a new {@link DefaultSymbol}.
   * 
   * @param element The {@link Element}.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultSymbol ( Element element ) throws StoreException
  {
    // Check if the element is correct
    if ( !element.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a symbol" ); //$NON-NLS-1$
    }

    // Attribute
    boolean foundName = false;
    boolean foundEpsilon = false;
    for ( Attribute current : element.getAttribute () )
    {
      if ( current.getName ().equals ( "name" ) ) //$NON-NLS-1$
      {
        if ( !this.epsilon )
        {
          setName ( current.getValue () );
        }
        foundName = true;
      }
      else if ( current.getName ().equals ( "epsilon" ) ) //$NON-NLS-1$
      {
        this.epsilon = current.getValueBoolean ();
        foundEpsilon = true;
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
      }
    }

    // Not all attribute values found
    if ( !foundName || !foundEpsilon )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }

    // Element
    if ( element.getElement ().size () > 0 )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Allocates a new {@link DefaultSymbol}.
   * 
   * @param name The name of this {@link DefaultSymbol}.
   */
  public DefaultSymbol ( String name )
  {
    setName ( name );
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
  public final int compareTo ( Symbol other )
  {
    if ( !this.epsilon && !other.isEpsilon () )
    {
      if ( this.name.length () > other.getName ().length () )
      {
        return -1;
      }
      if ( this.name.length () < other.getName ().length () )
      {
        return 1;
      }
      return this.name.compareTo ( other.getName () );
    }
    if ( !this.epsilon && other.isEpsilon () )
    {
      return 1;
    }
    if ( this.epsilon && !other.isEpsilon () )
    {
      return -1;
    }
    return 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultSymbol )
    {
      DefaultSymbol symbol = ( DefaultSymbol ) other;

      if ( this.epsilon != symbol.epsilon )
      {
        return false;
      }

      if ( this.epsilon && symbol.epsilon )
      {
        return true;
      }

      return this.name.equals ( symbol.name );
    }
    return false;
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
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "Symbol" ); //$NON-NLS-1$

    if ( this.epsilon )
    {
      newElement.addAttribute ( new Attribute ( "name", "\"epsilon\"" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      newElement.addAttribute ( new Attribute ( "epsilon", true ) ); //$NON-NLS-1$
    }
    else
    {
      newElement.addAttribute ( new Attribute ( "name", this.name ) ); //$NON-NLS-1$
      newElement.addAttribute ( new Attribute ( "epsilon", false ) ); //$NON-NLS-1$
    }

    return newElement;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Symbol#getName()
   */
  public final String getName ()
  {
    return this.name;
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
    if ( this.epsilon )
    {
      return "epsilon".hashCode (); //$NON-NLS-1$
    }
    return this.name.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Symbol#isActive()
   */
  public final boolean isActive ()
  {
    return this.active;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Symbol#isEpsilon()
   */
  public final boolean isEpsilon ()
  {
    return this.epsilon;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Symbol#isError()
   */
  public final boolean isError ()
  {
    return this.error;
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
   * @see Symbol#setActive(boolean)
   */
  public final void setActive ( boolean active )
  {
    if ( this.active != active )
    {
      this.active = active;
      firePrettyStringChanged ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Symbol#setError(boolean)
   */
  public final void setError ( boolean error )
  {
    if ( this.error != error )
    {
      this.error = error;
      firePrettyStringChanged ();
    }
  }


  /**
   * Sets the name of this symbol.
   * 
   * @param name The name to set.
   */
  private final void setName ( String name )
  {
    if ( this.epsilon )
    {
      throw new RuntimeException (
          "this symbol is a epsilon symbol without a name" ); //$NON-NLS-1$
    }

    if ( name == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }

    if ( ( this.name == null ) || !this.name.equals ( name ) )
    {
      this.name = name;
      firePrettyStringChanged ();
    }
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
   * @see PrettyPrintable#toPrettyString()
   */
  public final PrettyString toPrettyString ()
  {
    if ( ( this.cachedPrettyString == null )
        || PrettyString.MODE.equals ( PrettyStringMode.CACHING_OFF ) )
    {
      this.cachedPrettyString = new PrettyString ();

      if ( this.epsilon )
      {
        if ( this.error )
        {
          this.cachedPrettyString.add ( new PrettyToken ( "\u03B5", //$NON-NLS-1$
              Style.SYMBOL_ERROR ) );
        }
        else if ( this.active )
        {
          this.cachedPrettyString.add ( new PrettyToken ( "\u03B5", //$NON-NLS-1$
              Style.SYMBOL_ACTIVE ) );
        }
        else
        {
          this.cachedPrettyString.add ( new PrettyToken (
              "\u03B5", Style.SYMBOL ) ); //$NON-NLS-1$
        }
      }
      else
      {
        if ( this.error )
        {
          this.cachedPrettyString.add ( new PrettyToken ( this.name,
              Style.SYMBOL_ERROR ) );
        }
        else if ( this.active )
        {
          this.cachedPrettyString.add ( new PrettyToken ( this.name,
              Style.SYMBOL_ACTIVE ) );
        }
        else
        {
          this.cachedPrettyString.add ( new PrettyToken ( this.name,
              Style.SYMBOL ) );
        }
      }
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
    if ( this.epsilon )
    {
      return "\u03B5"; //$NON-NLS-1$
    }
    return this.name;
  }
}
