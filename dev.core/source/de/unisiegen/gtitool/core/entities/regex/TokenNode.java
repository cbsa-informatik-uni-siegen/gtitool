package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * TODO
 */
public class TokenNode extends RegexNode
{

  private String node;


  private int position;


  /**
   * TODO
   */
  public TokenNode ( String name )
  {
    this.node = name;
  }


  /**
   * TODO
   * 
   * @return
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
   * TODO
   * 
   * @return
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
   * TODO
   * 
   * @return
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
   * TODO
   * 
   * @return
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return false;
  }


  /**
   * TODO
   * 
   * @return
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.node + "<" + getPosition () + ">";
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
