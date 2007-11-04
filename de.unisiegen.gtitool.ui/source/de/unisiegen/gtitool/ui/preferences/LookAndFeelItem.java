package de.unisiegen.gtitool.ui.preferences;


/**
 * The look and feel item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class LookAndFeelItem implements Cloneable,
    Comparable < LookAndFeelItem >
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
   * @see Object#clone()
   */
  @Override
  public final LookAndFeelItem clone ()
  {
    return new LookAndFeelItem ( this.name, this.className );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( LookAndFeelItem pOther )
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
  public final String getClassName ()
  {
    return this.className;
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
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.name.hashCode () + this.className.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.name;
  }
}
