package de.unisiegen.gtitool.ui.preferences;


/**
 * The look and feel item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class LookAndFeelItem
{

  /**
   * The name of this item.
   */
  private String name;


  /**
   * The className of this item.
   */
  private String className;


  /**
   * Allocates a new <code>LookAndFeelItem</code>.
   * 
   * @param pName The name of this item.
   * @param pClassName The className of this item.
   */
  public LookAndFeelItem ( String pName, String pClassName )
  {
    // Name
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    this.name = pName;
    // ClassName
    if ( pClassName == null )
    {
      throw new NullPointerException ( "class name is null" ); //$NON-NLS-1$
    }
    this.className = pClassName;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals ( Object pOther )
  {
    if ( pOther instanceof LookAndFeelItem )
    {
      LookAndFeelItem other = ( LookAndFeelItem ) pOther;
      return ( this.name.equals ( other.name ) )
          && ( this.className.equals ( other.className ) );
    }
    return false;
  }


  /**
   * Returns the className.
   * 
   * @return The className.
   * @see #className
   */
  public String getClassName ()
  {
    return this.className;
  }


  /**
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public String getName ()
  {
    return this.name;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return this.name.hashCode () + this.className.hashCode ();
  }


  /**
   * Sets the className.
   * 
   * @param pClassName The className to set.
   */
  public void setClassName ( String pClassName )
  {
    if ( pClassName == null )
    {
      throw new NullPointerException ( "class name is null" ); //$NON-NLS-1$
    }
    this.className = pClassName;
  }


  /**
   * Sets the name.
   * 
   * @param pName The name to set.
   */
  public void setName ( String pName )
  {
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    this.name = pName;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.name;
  }
}
