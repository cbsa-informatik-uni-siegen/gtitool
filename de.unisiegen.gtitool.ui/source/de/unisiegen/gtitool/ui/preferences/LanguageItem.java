package de.unisiegen.gtitool.ui.preferences;


/**
 * The language item class.
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
   * The language of this item.
   */
  private String language;


  /**
   * Allocates a new <code>LanguageItem</code>.
   * 
   * @param pTitle The title of this item.
   * @param pLanguage The language of this item.
   */
  public LanguageItem ( String pTitle, String pLanguage )
  {
    // Title
    if ( pTitle == null )
    {
      throw new NullPointerException ( "title is null" ); //$NON-NLS-1$
    }
    this.title = pTitle;
    // Language
    this.language = pLanguage;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public LanguageItem clone ()
  {
    return new LanguageItem ( this.title, this.language );
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
          && ( this.language == null ? other.language == null : this.language
              .equals ( other.language ) );
    }
    return false;
  }


  /**
   * Returns the language.
   * 
   * @return The language.
   * @see #language
   */
  public String getLanguage ()
  {
    return this.language;
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
    return this.title.hashCode () + this.language == null ? 0 : this.language
        .hashCode ();
  }


  /**
   * Sets the language.
   * 
   * @param pLanguage The language to set.
   */
  public void setLanguage ( String pLanguage )
  {
    this.language = pLanguage;
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
        + ( this.language == null ? "" : ( " (" + this.language + ")" ) ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  }
}
