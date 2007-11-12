package de.unisiegen.gtitool.core.parser;


/**
 * The parser interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ParserInterface
{

  /**
   * Returns the parsed object.
   * 
   * @return The parsed object.
   * @throws Exception If an error occurs.
   */
  public Object parse () throws Exception;
}
