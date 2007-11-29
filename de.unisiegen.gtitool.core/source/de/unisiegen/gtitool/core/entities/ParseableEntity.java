package de.unisiegen.gtitool.core.entities;


/**
 * The interface for all parseable entities.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ParseableEntity extends Entity
{

  /**
   * The value if no parser offset is defined.
   */
  public static final int NO_PARSER_OFFSET = -1;


  /**
   * Returns the parserEndOffset.
   * 
   * @return The parserEndOffset.
   */
  public int getParserEndOffset ();


  /**
   * Returns the parserStartOffset.
   * 
   * @return The parserStartOffset.
   */
  public int getParserStartOffset ();


  /**
   * Sets the parser end offset.
   * 
   * @param pParserEndOffset The new parser end offset.
   */
  public void setParserEndOffset ( int pParserEndOffset );


  /**
   * Sets the parser start offset.
   * 
   * @param pParserStartOffset The new parser start offset.
   */
  public void setParserStartOffset ( int pParserStartOffset );
}
