package de.unisiegen.gtitool.core.parser;


import java_cup.runtime.Symbol;


/**
 * The parser symbol class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ParserSymbol extends Symbol
{

  /**
   * The name of the symbol.
   */
  private String name;


  /**
   * Allocates a new <code>ParserSymbol</code>.
   * 
   * @param pName The name.
   * @param pId The id.
   * @param pLeft The left parser index.
   * @param pRight The right parser index.
   * @param pValue The value.
   */
  public ParserSymbol ( String pName, int pId, int pLeft, int pRight,
      Object pValue )
  {
    super ( pId, pLeft, pRight, pValue );
    this.name = pName;
  }


  /**
   * Returns the id.
   * 
   * @return The id.
   */
  public final int getId ()
  {
    return this.sym;
  }


  /**
   * Returns the left parser index.
   * 
   * @return The left parser index.
   */
  public final int getLeft ()
  {
    return this.left;
  }


  /**
   * Returns the name.
   * 
   * @return The name.
   */
  public final String getName ()
  {
    return this.name;
  }


  /**
   * Returns the right parser index.
   * 
   * @return The right parser index.
   */
  public final int getRight ()
  {
    return this.right;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Symbol#toString()
   */
  @Override
  public final String toString ()
  {
    return this.name;
  }
}
