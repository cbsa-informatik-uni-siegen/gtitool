package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolEmptyNameException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolIllegalNameException;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The <code>DefaultSymbol</code> entity.
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
  private String name;


  /**
   * The offset of this {@link DefaultSymbol} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Allocates a new <code>DefaultSymbol</code>.
   * 
   * @param element The {@link Element}.
   * @throws SymbolException If something with the <code>DefaultSymbol</code>
   *           is not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultSymbol ( Element element ) throws SymbolException,
      StoreException
  {
    // Check if the element is correct
    if ( !element.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + element.getName () //$NON-NLS-1$
          + "\" is not a symbol" ); //$NON-NLS-1$
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
   * Allocates a new <code>DefaultSymbol</code>.
   * 
   * @param name The name of this symbol.
   * @throws SymbolException If something with the <code>DefaultSymbol</code>
   *           is not correct.
   */
  public DefaultSymbol ( String name ) throws SymbolException
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
  public final DefaultSymbol clone ()
  {
    try
    {
      return new DefaultSymbol ( this.name );
    }
    catch ( SymbolException e )
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
  public final int compareTo ( Symbol other )
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
    if ( other instanceof DefaultSymbol )
    {
      DefaultSymbol defaultSymbol = ( DefaultSymbol ) other;
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
    Element newElement = new Element ( "Symbol" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "name", this.name ) ); //$NON-NLS-1$
    return newElement;
  }


  /**
   * Returns the name of this symbol.
   * 
   * @return The name of this symbol.
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
   * @see PrettyPrintable#getPrettyString()
   */
  public final PrettyString getPrettyString ()
  {
    PrettyString prettyString = new PrettyString ();
    prettyString.addPrettyChar ( new PrettyToken ( this.name, Style.SYMBOL ) );
    return prettyString;
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
   * Sets the name of this symbol.
   * 
   * @param name The name to set.
   * @throws SymbolException If something with the <code>DefaultSymbol</code>
   *           is not correct.
   */
  private final void setName ( String name ) throws SymbolException
  {
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
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
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
