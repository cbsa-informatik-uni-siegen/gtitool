package de.unisiegen.gtitool.core.storage;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The <code>Attribute</code> class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Attribute
{

  /**
   * The name.
   */
  private String name;


  /**
   * The value.
   */
  private String value;


  /**
   * Allocates a new <code>Attribute</code>.
   * 
   * @param pName The name of this <code>Attribute</code>.
   * @param pValue The value of this <code>Attribute</code>.
   */
  public Attribute ( String pName, boolean pValue )
  {
    // Name
    setName ( pName );
    // Value
    setValue ( String.valueOf ( pValue ) );
  }


  /**
   * Allocates a new <code>Attribute</code>.
   * 
   * @param pName The name of this <code>Attribute</code>.
   * @param pValue The value of this <code>Attribute</code>.
   */
  public Attribute ( String pName, double pValue )
  {
    // Name
    setName ( pName );
    // Value
    setValue ( String.valueOf ( pValue ) );
  }


  /**
   * Allocates a new <code>Attribute</code>.
   * 
   * @param pName The name of this <code>Attribute</code>.
   * @param pValue The value of this <code>Attribute</code>.
   */
  public Attribute ( String pName, int pValue )
  {
    // Name
    setName ( pName );
    // Value
    setValue ( String.valueOf ( pValue ) );
  }


  /**
   * Allocates a new <code>Attribute</code>.
   * 
   * @param pName The name of this <code>Attribute</code>.
   * @param pValue The value of this <code>Attribute</code>.
   */
  public Attribute ( String pName, String pValue )
  {
    // Name
    setName ( pName );
    // Value
    setValue ( pValue );
  }


  /**
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public final String getName ()
  {
    return this.name;
  }


  /**
   * Returns the value.
   * 
   * @return The value.
   * @see #value
   */
  public final String getValue ()
  {
    return this.value;
  }


  /**
   * Returns the boolean value.
   * 
   * @return The boolean value.
   * @throws StoreException If the value is not a boolean.
   * @see #value
   */
  public final boolean getValueBoolean () throws StoreException
  {
    if ( this.value.equals ( "true" ) ) //$NON-NLS-1$
    {
      return true;
    }
    else if ( this.value.equals ( "false" ) ) //$NON-NLS-1$
    {
      return false;
    }
    else
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.WrongAttributeValueFormat" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Returns the double value.
   * 
   * @return The double value.
   * @throws StoreException If the value is not a double.
   * @see #value
   */
  public final double getValueDouble () throws StoreException
  {
    try
    {
      return Double.parseDouble ( this.value );
    }
    catch ( NumberFormatException exc )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.WrongAttributeValueFormat" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Returns the integer value.
   * 
   * @return The integer value.
   * @throws StoreException If the value is not a integer.
   * @see #value
   */
  public final int getValueInt () throws StoreException
  {
    try
    {
      return Integer.parseInt ( this.value );
    }
    catch ( NumberFormatException exc )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.WrongAttributeValueFormat" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Sets the name.
   * 
   * @param pName The name to set.
   * @see #name
   */
  public final void setName ( String pName )
  {
    if ( pName == null )
    {
      throw new NullPointerException ();
    }
    if ( pName.length () == 0 )
    {
      throw new IllegalArgumentException ( "name is empty" ); //$NON-NLS-1$
    }
    this.name = pName;
  }


  /**
   * Sets the value.
   * 
   * @param pValue The value to set.
   * @see #value
   */
  public final void setValue ( String pValue )
  {
    if ( pValue == null )
    {
      throw new NullPointerException ();
    }
    if ( pValue.length () == 0 )
    {
      throw new IllegalArgumentException ( "value is empty" ); //$NON-NLS-1$
    }
    this.value = pValue;
  }
}
