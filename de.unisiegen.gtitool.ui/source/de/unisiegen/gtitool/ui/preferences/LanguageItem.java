package de.unisiegen.gtitool.ui.preferences;


import java.util.Locale;


/**
 * The locale item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class LanguageItem implements Cloneable, Comparable < LanguageItem >
{

  /**
   * The title of this item.
   */
  private String title;


  /**
   * The {@link Locale} of this item.
   */
  private Locale locale;


  /**
   * Allocates a new <code>LanguageItem</code>.
   * 
   * @param pTitle The title of this item.
   * @param pLocale The {@link Locale} of this item.
   */
  public LanguageItem ( String pTitle, Locale pLocale )
  {
    // Title
    if ( pTitle == null )
    {
      throw new NullPointerException ( "title is null" ); //$NON-NLS-1$
    }
    this.title = pTitle;
    // Locale
    if ( pLocale == null )
    {
      throw new NullPointerException ( "locale is null" ); //$NON-NLS-1$
    }
    this.locale = pLocale;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public LanguageItem clone ()
  {
    return new LanguageItem ( this.title, this.locale );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( LanguageItem pOther )
  {
    return this.title.compareTo ( pOther.title );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals ( Object pOther )
  {
    if ( pOther instanceof LanguageItem )
    {
      LanguageItem other = ( LanguageItem ) pOther;
      return ( this.title.equals ( other.title ) )
          && ( this.locale.equals ( other.locale ) );
    }
    return false;
  }


  /**
   * Returns the {@link Locale}.
   * 
   * @return The {@link Locale}.
   * @see #locale
   */
  public Locale getLocale ()
  {
    return this.locale;
  }


  /**
   * Returns the title.
   * 
   * @return The title.
   * @see #title
   */
  public String getTitle ()
  {
    return this.title;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return this.title.hashCode () + this.locale.hashCode ();
  }


  /**
   * Sets the {@link Locale}.
   * 
   * @param pLocale The {@link Locale} to set.
   */
  public void setLocale ( Locale pLocale )
  {
    // Locale
    if ( pLocale == null )
    {
      throw new NullPointerException ( "locale is null" ); //$NON-NLS-1$
    }
    this.locale = pLocale;
  }


  /**
   * Sets the title.
   * 
   * @param pTitle The title to set.
   */
  public void setTitle ( String pTitle )
  {
    if ( pTitle == null )
    {
      throw new NullPointerException ( "title is null" ); //$NON-NLS-1$
    }
    this.title = pTitle;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.title
        + ( this.locale == null ? "" : ( " (" + this.locale.toString () + ")" ) ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  }
}
