package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolEmptyNameException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolIllegalNameException;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The <code>Symbol</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Symbol implements ParseableEntity, Storable,
    Comparable < Symbol >
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
   * The start offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserStartOffset()
   * @see #setParserStartOffset(int)
   */
  private int parserStartOffset = NO_PARSER_OFFSET;


  /**
   * The end offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserEndOffset()
   * @see #setParserEndOffset(int)
   */
  private int parserEndOffset = NO_PARSER_OFFSET;


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pElement The {@link Element}.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public Symbol ( Element pElement ) throws SymbolException, StoreException
  {
    // Check if the element is correct
    if ( !pElement.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + pElement.getName () //$NON-NLS-1$
          + "\" is not a symbol" ); //$NON-NLS-1$
    }

    // Attribute
    boolean foundName = false;
    boolean foundParserStartOffset = false;
    boolean foundParserEndOffset = false;
    for ( Attribute current : pElement.getAttribute () )
    {
      if ( current.getName ().equals ( "name" ) ) //$NON-NLS-1$
      {
        setName ( current.getValue () );
        foundName = true;
      }
      else if ( current.getName ().equals ( "parserStartOffset" ) ) //$NON-NLS-1$
      {
        setParserStartOffset ( current.getValueInt () );
        foundParserStartOffset = true;
      }
      else if ( current.getName ().equals ( "parserEndOffset" ) ) //$NON-NLS-1$
      {
        setParserEndOffset ( current.getValueInt () );
        foundParserEndOffset = true;
      }
      else
      {
        // TODO Warning
        throw new IllegalArgumentException ();
      }
    }

    // Not all attribute values found
    if ( ( !foundName ) || ( !foundParserStartOffset )
        || ( !foundParserEndOffset ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }

    // Element
    if ( pElement.getElement ().size () > 0 )
    {
      // TODO Warning
      throw new IllegalArgumentException ();
    }
  }


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pName The name of this symbol.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   */
  public Symbol ( String pName ) throws SymbolException
  {
    // Name
    setName ( pName );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final Symbol clone ()
  {
    try
    {
      return new Symbol ( this.name );
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
  public final int compareTo ( Symbol pOther )
  {
    return this.name.compareTo ( pOther.name );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof Symbol )
    {
      Symbol other = ( Symbol ) pOther;
      return this.name.equals ( other.name );
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
    newElement.addAttribute ( new Attribute ( "parserStartOffset", //$NON-NLS-1$
        this.parserStartOffset ) );
    newElement.addAttribute ( new Attribute ( "parserEndOffset", //$NON-NLS-1$
        this.parserEndOffset ) );
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
  public final int getParserEndOffset ()
  {
    return this.parserEndOffset;
  }


  /**
   * {@inheritDoc}
   */
  public final int getParserStartOffset ()
  {
    return this.parserStartOffset;
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
   * @param pName The name to set.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   */
  private final void setName ( String pName ) throws SymbolException
  {
    // Name
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( pName.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new SymbolEmptyNameException ();
    }
    if ( pName.startsWith ( "\"" ) ) //$NON-NLS-1$
    {
      if ( !pName.endsWith ( "\"" ) ) //$NON-NLS-1$
      {
        throw new SymbolIllegalNameException ( pName );
      }
      if ( pName.length () <= 2 )
      {
        throw new SymbolIllegalNameException ( pName );
      }
      String tmpName = pName.substring ( 1, pName.length () - 1 );
      for ( int i = 0 ; i < tmpName.length () ; i++ )
      {
        if ( !Character.isJavaIdentifierPart ( tmpName.charAt ( i ) ) )
        {
          throw new SymbolIllegalNameException ( pName );
        }
      }
    }
    else if ( pName.length () == 1 )
    {
      if ( !Character.isJavaIdentifierPart ( pName.charAt ( 0 ) ) )
      {
        throw new SymbolIllegalNameException ( pName );
      }
    }
    else
    {
      throw new SymbolIllegalNameException ( pName );
    }
    this.name = pName;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserEndOffset ( int pParserEndOffset )
  {
    this.parserEndOffset = pParserEndOffset;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserStartOffset ( int pParserStartOffset )
  {
    this.parserStartOffset = pParserStartOffset;
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
