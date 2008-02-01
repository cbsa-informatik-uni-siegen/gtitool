package de.unisiegen.gtitool.core.entities;


import java.io.Serializable;

import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;


/**
 * The interface for all entities.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Entity extends PrettyPrintable, Cloneable, Serializable
{

  /**
   * The value if no parser offset is defined.
   */
  public static final ParserOffset NO_PARSER_OFFSET = new ParserOffset ( -1, -1 );


  /**
   * Creates and returns a copy of this entity.
   * 
   * @see Object#clone()
   */
  public Entity clone ();


  /**
   * Returns the {@link ParserOffset}.
   * 
   * @return The {@link ParserOffset}.
   */
  public ParserOffset getParserOffset ();


  /**
   * Returns a hash code value for this entity.
   * 
   * @see Object#hashCode()
   */
  public int hashCode ();


  /**
   * Sets the {@link ParserOffset}.
   * 
   * @param parserOffset The new {@link ParserOffset}.
   */
  public void setParserOffset ( ParserOffset parserOffset );


  /**
   * Returns the string.
   * 
   * @return The string.
   */
  public String toString ();


  /**
   * Returns the debug string.
   * 
   * @return The debug string.
   */
  public String toStringDebug ();
}
