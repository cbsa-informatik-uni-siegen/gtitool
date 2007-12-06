package de.unisiegen.gtitool.core.storage;


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
    this.value = pValue;
  }
}
