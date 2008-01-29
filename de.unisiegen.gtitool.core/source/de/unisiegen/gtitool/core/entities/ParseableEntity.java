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
   * The parser offset.
   * 
   * @author Christian Fehler
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


  /**
   * The value if no parser offset is defined.
   */
  public static final ParserOffset NO_PARSER_OFFSET = new ParserOffset ( -1, -1 );


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
}
