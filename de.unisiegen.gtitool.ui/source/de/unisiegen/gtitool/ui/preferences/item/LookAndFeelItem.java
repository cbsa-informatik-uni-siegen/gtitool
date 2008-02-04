package de.unisiegen.gtitool.ui.preferences.item;


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
   * Allocates a new {@link LookAndFeelItem}.
   * 
   * @param name The name of this item.
   * @param className The className of this item.
   */
  public LookAndFeelItem ( String name, String className )
  {
    // Name
    if ( name == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    this.name = name;
    // ClassName
    if ( className == null )
    {
      throw new NullPointerException ( "class name is null" ); //$NON-NLS-1$
    }
    this.className = className;
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
  public final int compareTo ( LookAndFeelItem other )
  {
    return this.name.compareTo ( other.name );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof LookAndFeelItem )
    {
      LookAndFeelItem lookAndFeelItem = ( LookAndFeelItem ) other;
      return ( this.name.equals ( lookAndFeelItem.name ) )
          && ( this.className.equals ( lookAndFeelItem.className ) );
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
