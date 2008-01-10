package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Symbol</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Symbol extends ParseableEntity, Storable, Comparable < Symbol >
{

  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public Symbol clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( Symbol other );


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object other );


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public Element getElement ();


  /**
   * Returns the name of this symbol.
   * 
   * @return The name of this symbol.
   */
  public String getName ();


  /**
   * {@inheritDoc}
   */
  public int getParserEndOffset ();


  /**
   * {@inheritDoc}
   */
  public int getParserStartOffset ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
   */
  public int hashCode ();


  /**
   * {@inheritDoc}
   */
  public void setParserEndOffset ( int parserEndOffset );


  /**
   * {@inheritDoc}
   */
  public void setParserStartOffset ( int parserStartOffset );


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public String toString ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public String toStringDebug ();
}
