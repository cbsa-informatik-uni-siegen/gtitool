package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;


/**
 * A representation of an Undo item for Regex.
 */
public class RegexUndoItem
{

  /**
   * The regex {@link String}
   */
  private String regexString;


  /**
   * The {@link DefaultRegexAlphabet}
   */
  private DefaultRegexAlphabet lastAlphabet;


  /**
   * Creates a new {@link RegexUndoItem} for a {@link DefaultRegexAlphabet}
   * 
   * @param lastAlphabet The {@link DefaultRegexAlphabet}
   */
  public RegexUndoItem ( DefaultRegexAlphabet lastAlphabet )
  {
    this.lastAlphabet = lastAlphabet;
  }


  /**
   * Creates a new {@link RegexUndoItem} for a RegexString
   * 
   * @param regexString The {@link String}
   */
  public RegexUndoItem ( String regexString )
  {
    this.regexString = regexString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals ( Object obj )
  {
    if ( obj == this )
    {
      return true;
    }
    if ( obj instanceof RegexUndoItem )
    {
      RegexUndoItem other = ( RegexUndoItem ) obj;
      if ( this.lastAlphabet == null )
      {
        return this.regexString.equals ( other.getRegexString () );
      }
      return this.lastAlphabet.equals ( other.getLastAlphabet () );
    }
    return false;
  }


  /**
   * Returns the lastAlphabet.
   * 
   * @return The lastAlphabet.
   * @see #lastAlphabet
   */
  public DefaultRegexAlphabet getLastAlphabet ()
  {
    return this.lastAlphabet;
  }


  /**
   * Returns the regexString.
   * 
   * @return The regexString.
   * @see #regexString
   */
  public String getRegexString ()
  {
    return this.regexString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    if ( this.lastAlphabet == null )
    {
      return this.regexString.hashCode ();
    }
    return this.lastAlphabet.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    if ( this.regexString != null )
    {
      return this.regexString;
    }
    return this.lastAlphabet.toString ();
  }
}
