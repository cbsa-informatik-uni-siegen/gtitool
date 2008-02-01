package de.unisiegen.gtitool.core.parser;


/**
 * The parser offset class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class ParserOffset
{

  /**
   * The parser start offset.
   */
  private int start;


  /**
   * The parser end offset.
   */
  private int end;


  /**
   * Allocates a new {@link ParserOffset}.
   * 
   * @param start The parser start offset.
   * @param end The parser end offset.
   */
  public ParserOffset ( int start, int end )
  {
    this.start = start;
    this.end = end;
  }


  /**
   * Returns the parser end offset.
   * 
   * @return Theparser end offset.
   * @see #end
   */
  public final int getEnd ()
  {
    return this.end;
  }


  /**
   * Returns the parser start offset.
   * 
   * @return The parser start offset.
   * @see #start
   */
  public final int getStart ()
  {
    return this.start;
  }
}
