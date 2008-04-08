package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolEmptyNameException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolIllegalNameException;
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
 * The {@link DefaultTerminalSymbol} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultTerminalSymbol implements TerminalSymbol
{

  /**
   * The serial verion uid.
   */
  private static final long serialVersionUID = -5069749430451559892L;


  /**
   * The name of this state.
   */
  private String name;


  /**
   * This {@link DefaultTerminalSymbol} is a error {@link DefaultTerminalSymbol}.
   */
  private boolean error = false;


  /**
   * This {@link DefaultTerminalSymbol} is a active
   * {@link DefaultTerminalSymbol}.
   */
  private boolean active = false;


  /**
   * The offset of this {@link DefaultTerminalSymbol} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Allocates a new {@link DefaultTerminalSymbol}.
   * 
   * @param element The {@link Element}.
   * @throws TerminalSymbolException If something with the
   *           {@link DefaultTerminalSymbol} is not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultTerminalSymbol ( Element element )
      throws TerminalSymbolException, StoreException
  {
    // Check if the element is correct
    if ( !element.getName ().equals ( "TerminalSymbol" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a terminal symbol" ); //$NON-NLS-1$
    }

    // Attribute
    boolean foundName = false;
    for ( Attribute current : element.getAttribute () )
    {
      if ( current.getName ().equals ( "name" ) ) //$NON-NLS-1$
      {
        setName ( current.getValue () );
        foundName = true;
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
      }
    }

    // Not all attribute values found
    if ( !foundName )
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
   * Allocates a new {@link DefaultTerminalSymbol}.
   * 
   * @param name The name of this {@link DefaultTerminalSymbol}.
   * @throws TerminalSymbolException If something with the
   *           {@link DefaultTerminalSymbol} is not correct.
   */
  public DefaultTerminalSymbol ( String name ) throws TerminalSymbolException
  {
    // Name
    setName ( name );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final DefaultTerminalSymbol clone ()
  {
    try
    {
      return new DefaultTerminalSymbol ( this.name );
    }
    catch ( TerminalSymbolException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( TerminalSymbol other )
  {
    return this.name.compareTo ( other.getName () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultTerminalSymbol )
    {
      DefaultTerminalSymbol defaultSymbol = ( DefaultTerminalSymbol ) other;
      return this.name.equals ( defaultSymbol.name );
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
    Element newElement = new Element ( "TerminalSymbol" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "name", this.name ) ); //$NON-NLS-1$
    return newElement;
  }


  /**
   * Returns the name of this {@link DefaultTerminalSymbol}.
   * 
   * @return The name of this {@link DefaultTerminalSymbol}.
   * @see #name
   */
  public final String getName ()
  {
    return this.name;
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
   * @see Entity#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.name.hashCode ();
  }


  /**
   * Returns true if this {@link TerminalSymbol} is a active
   * {@link TerminalSymbol}, otherwise false.
   * 
   * @return True if this {@link TerminalSymbol} is a active
   *         {@link TerminalSymbol}, otherwise false.
   */
  public final boolean isActive ()
  {
    return this.active;
  }


  /**
   * Returns true if this {@link TerminalSymbol} is a error
   * {@link TerminalSymbol}, otherwise false.
   * 
   * @return True if this {@link TerminalSymbol} is a error
   *         {@link TerminalSymbol}, otherwise false.
   * @see #error
   */
  public final boolean isError ()
  {
    return this.error;
  }


  /**
   * Sets the active value.
   * 
   * @param active The active value to set.
   */
  public final void setActive ( boolean active )
  {
    this.active = active;
  }


  /**
   * Sets the error value.
   * 
   * @param error The error value to set.
   * @see #error
   */
  public final void setError ( boolean error )
  {
    this.error = error;
  }


  /**
   * Sets the name of this symbol.
   * 
   * @param name The name to set.
   * @throws TerminalSymbolException If something with the
   *           {@link DefaultTerminalSymbol} is not correct.
   */
  private final void setName ( String name ) throws TerminalSymbolException
  {
    // Name
    if ( name == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( name.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new TerminalSymbolEmptyNameException ();
    }
    if ( name.startsWith ( "\"" ) ) //$NON-NLS-1$
    {
      if ( !name.endsWith ( "\"" ) ) //$NON-NLS-1$
      {
        throw new TerminalSymbolIllegalNameException ( name );
      }
      if ( name.length () <= 2 )
      {
        throw new TerminalSymbolIllegalNameException ( name );
      }
      String tmpName = name.substring ( 1, name.length () - 1 );
      for ( int i = 0 ; i < tmpName.length () ; i++ )
      {
        if ( !Character.isJavaIdentifierPart ( tmpName.charAt ( i ) ) )
        {
          throw new TerminalSymbolIllegalNameException ( name );
        }
      }
    }
    else if ( name.length () == 1 )
    {
      if ( name.equals ( "\u03B5" ) ) //$NON-NLS-1$
      {
        throw new TerminalSymbolIllegalNameException ( name );
      }
      if ( !Character.isJavaIdentifierPart ( name.charAt ( 0 ) ) )
      {
        throw new TerminalSymbolIllegalNameException ( name );
      }
    }
    else
    {
      throw new TerminalSymbolIllegalNameException ( name );
    }
    this.name = name;
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
    prettyString.addPrettyToken ( new PrettyToken ( this.name,
        Style.TERMINAL_SYMBOL ) );
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
    return this.name;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public final String toStringDebug ()
  {
    return this.name;
  }
}
