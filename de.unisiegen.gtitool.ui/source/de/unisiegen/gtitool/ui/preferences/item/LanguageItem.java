package de.unisiegen.gtitool.ui.preferences.item;


import java.util.Locale;


/**
 * The locale item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class LanguageItem implements Cloneable,
    Comparable < LanguageItem >
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
   * @param title The title of this item.
   * @param locale The {@link Locale} of this item.
   */
  public LanguageItem ( String title, Locale locale )
  {
    // Title
    if ( title == null )
    {
      throw new NullPointerException ( "title is null" ); //$NON-NLS-1$
    }
    this.title = title;
    // Locale
    if ( locale == null )
    {
      throw new NullPointerException ( "locale is null" ); //$NON-NLS-1$
    }
    this.locale = locale;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final LanguageItem clone ()
  {
    return new LanguageItem ( this.title, this.locale );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( LanguageItem other )
  {
    return this.title.compareTo ( other.title );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof LanguageItem )
    {
      LanguageItem languageItem = ( LanguageItem ) other;
      return ( this.title.equals ( languageItem.title ) )
          && ( this.locale.getLanguage ().equals ( languageItem.locale
              .getLanguage () ) );
    }
    return false;
  }


  /**
   * Returns the {@link Locale}.
   * 
   * @return The {@link Locale}.
   * @see #locale
   */
  public final Locale getLocale ()
  {
    return this.locale;
  }


  /**
   * Returns the title.
   * 
   * @return The title.
   * @see #title
   */
  public final String getTitle ()
  {
    return this.title;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.title.hashCode () + this.locale.getLanguage ().hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.title
        + ( this.locale == null ? "" : ( " (" + this.locale.getLanguage () + ")" ) ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  }
}
