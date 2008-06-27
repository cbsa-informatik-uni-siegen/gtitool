package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.exceptions.symbol.SymbolEmptyNameException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolIllegalNameException;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
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
   * @throws SymbolException If something with the {@link DefaultSymbol} is not
   *           correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultSymbol ( Element element ) throws SymbolException,
      StoreException
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
   * @throws SymbolException If something with the {@link DefaultSymbol} is not
   *           correct.
   */
  public DefaultSymbol ( String name ) throws SymbolException
  {
    // Name
    setName ( name );
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
   * @see Symbol#setActive(boolean)
   */
  public final void setActive ( boolean active )
  {
    this.active = active;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Symbol#setError(boolean)
   */
  public final void setError ( boolean error )
  {
    this.error = error;
  }


  /**
   * Sets the name of this symbol.
   * 
   * @param name The name to set.
   * @throws SymbolException If something with the {@link DefaultSymbol} is not
   *           correct.
   */
  private final void setName ( String name ) throws SymbolException
  {
    if ( this.epsilon )
    {
      throw new RuntimeException (
          "this symbol is a epsilon symbol without a name" ); //$NON-NLS-1$
    }

    // Name
    if ( name == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( name.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new SymbolEmptyNameException ();
    }
    if ( name.startsWith ( "\"" ) ) //$NON-NLS-1$
    {
      if ( !name.endsWith ( "\"" ) ) //$NON-NLS-1$
      {
        throw new SymbolIllegalNameException ( name );
      }
      if ( name.length () <= 2 )
      {
        throw new SymbolIllegalNameException ( name );
      }
      String tmpName = name.substring ( 1, name.length () - 1 );
      for ( int i = 0 ; i < tmpName.length () ; i++ )
      {
        if ( !Character.isJavaIdentifierPart ( tmpName.charAt ( i ) ) )
        {
          throw new SymbolIllegalNameException ( name );
        }
      }
    }
    else if ( name.length () == 1 )
    {
      if ( name.equals ( "\u03B5" ) ) //$NON-NLS-1$
      {
        throw new SymbolIllegalNameException ( name );
      }
      if ( !Character.isJavaIdentifierPart ( name.charAt ( 0 ) ) )
      {
        throw new SymbolIllegalNameException ( name );
      }
    }
    else
    {
      throw new SymbolIllegalNameException ( name );
    }
    this.name = name;
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
    PrettyString prettyString = new PrettyString ();

    if ( this.epsilon )
    {
      if ( this.error )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u03B5", //$NON-NLS-1$
            Style.SYMBOL_ERROR ) );
      }
      else if ( this.active )
      {
        prettyString.addPrettyToken ( new PrettyToken ( "\u03B5", //$NON-NLS-1$
            Style.SYMBOL_ACTIVE ) );
      }
      else
      {
        prettyString
            .addPrettyToken ( new PrettyToken ( "\u03B5", Style.SYMBOL ) ); //$NON-NLS-1$
      }
    }
    else
    {
      if ( this.error )
      {
        prettyString.addPrettyToken ( new PrettyToken ( this.name,
            Style.SYMBOL_ERROR ) );
      }
      else if ( this.active )
      {
        prettyString.addPrettyToken ( new PrettyToken ( this.name,
            Style.SYMBOL_ACTIVE ) );
      }
      else
      {
        prettyString
            .addPrettyToken ( new PrettyToken ( this.name, Style.SYMBOL ) );
      }
    }

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
    if ( this.epsilon )
    {
      return "\u03B5"; //$NON-NLS-1$
    }
    return this.name;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public final String toStringDebug ()
  {
    if ( this.epsilon )
    {
      return "\u03B5"; //$NON-NLS-1$
    }
    return this.name;
  }
}
