package de.unisiegen.gtitool.core.entities;


import java.io.Serializable;

import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;


/**
 * The interface for all entities.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The {@link Entity} type.
 */
public interface Entity < E > extends PrettyPrintable, Cloneable, Serializable,
    Comparable < E >
{

  /**
   * The value if no parser offset is defined.
   */
  public static final ParserOffset NO_PARSER_OFFSET = new ParserOffset ( -1, -1 );


  /**
   * Creates and returns a copy of this {@link Entity}.
   * 
   * @see Object#clone()
   */
  public E clone ();


  /**
   * Returns the {@link ParserOffset}.
   * 
   * @return The {@link ParserOffset}.
   */
  public ParserOffset getParserOffset ();


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
