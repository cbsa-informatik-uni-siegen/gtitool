package de.unisiegen.gtitool.core.entities;


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
   * This {@link DefaultTerminalSymbol} is a error {@link DefaultTerminalSymbol}
   * .
   */
  private boolean error = false;


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
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultTerminalSymbol ( Element element ) throws StoreException
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
   */
  public DefaultTerminalSymbol ( String name )
  {
    setName ( name );
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
   * {@inheritDoc}
   * 
   * @see TerminalSymbol#getName()
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
    return this.name.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbol#isError()
   */
  public final boolean isError ()
  {
    return this.error;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbol#setError(boolean)
   */
  public final void setError ( boolean error )
  {
    this.error = error;
  }


  /**
   * Sets the name of this symbol.
   * 
   * @param name The name to set.
   */
  private final void setName ( String name )
  {
    if ( name == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
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
    if ( this.error )
    {
      prettyString.addPrettyToken ( new PrettyToken ( this.name,
          Style.TERMINAL_SYMBOL_ERROR ) );
    }
    else
    {
      prettyString.addPrettyToken ( new PrettyToken ( this.name,
          Style.TERMINAL_SYMBOL ) );
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
