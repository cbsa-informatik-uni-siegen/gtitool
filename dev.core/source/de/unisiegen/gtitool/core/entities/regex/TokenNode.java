package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * Representation of a Token in the Regex
 */
public class TokenNode extends RegexNode
{

  /**
   * The name of the Token
   */
  private String name;


  /**
   * The position in the Syntaxtree
   */
  private int position;


  /**
   * Contructor for a Token in the Regex
   * 
   * @param name The name of the Token
   */
  public TokenNode ( String name )
  {
    this.name = name;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return this;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < RegexNode > getTokenNodes ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#firstPos()
   */
  @Override
  public ArrayList < RegexNode > firstPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return false;
  }


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.name;
  }


  /**
   * Sets the position.
   * 
   * @param position The position to set.
   * @see #position
   */
  public void setPosition ( int position )
  {
    this.position = position;
  }


  /**
   * Returns the position.
   * 
   * @return The position.
   * @see #position
   */
  public int getPosition ()
  {
    return this.position;
  }

}
