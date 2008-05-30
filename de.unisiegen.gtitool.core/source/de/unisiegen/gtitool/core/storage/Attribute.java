package de.unisiegen.gtitool.core.storage;


import java.io.Serializable;

import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The {@link Attribute} class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Attribute implements Serializable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7909860204639971864L;


  /**
   * The name.
   */
  private String name;


  /**
   * The value.
   */
  private String value;


  /**
   * Allocates a new {@link Attribute}.
   * 
   * @param name The name of this {@link Attribute}.
   * @param value The value of this {@link Attribute}.
   */
  public Attribute ( String name, boolean value )
  {
    // Name
    setName ( name );
    // Value
    setValue ( String.valueOf ( value ) );
  }


  /**
   * Allocates a new {@link Attribute}.
   * 
   * @param name The name of this {@link Attribute}.
   * @param value The value of this {@link Attribute}.
   */
  public Attribute ( String name, double value )
  {
    // Name
    setName ( name );
    // Value
    setValue ( String.valueOf ( value ) );
  }


  /**
   * Allocates a new {@link Attribute}.
   * 
   * @param name The name of this {@link Attribute}.
   * @param value The value of this {@link Attribute}.
   */
  public Attribute ( String name, int value )
  {
    // Name
    setName ( name );
    // Value
    setValue ( String.valueOf ( value ) );
  }


  /**
   * Allocates a new {@link Attribute}.
   * 
   * @param name The name of this {@link Attribute}.
   * @param value The value of this {@link Attribute}.
   */
  public Attribute ( String name, String value )
  {
    // Name
    setName ( name );
    // Value
    setValue ( value );
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
   * @param name The name to set.
   * @see #name
   */
  public final void setName ( String name )
  {
    if ( name == null )
    {
      throw new NullPointerException ();
    }
    if ( name.length () == 0 )
    {
      throw new IllegalArgumentException ( "name is empty" ); //$NON-NLS-1$
    }
    this.name = name;
  }


  /**
   * Sets the value.
   * 
   * @param value The value to set.
   * @see #value
   */
  public final void setValue ( String value )
  {
    if ( value == null )
    {
      throw new NullPointerException ();
    }
    if ( value.length () == 0 )
    {
      throw new IllegalArgumentException ( "value is empty" ); //$NON-NLS-1$
    }
    this.value = value;
  }
}
