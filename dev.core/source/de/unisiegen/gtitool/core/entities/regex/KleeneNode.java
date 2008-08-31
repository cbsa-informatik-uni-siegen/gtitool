package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * TODO
 */
public class KleeneNode extends RegexNode
{

  private RegexNode content;


  /**
   * TODO
   */
  public KleeneNode ( RegexNode content )
  {
    this.content = content;
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
    nodes.add ( this.content );
    nodes.addAll ( this.content.getChildren () );
    return nodes;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getTokenNodes ()
  {
    return this.content.getTokenNodes ();
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
    return this.content.firstPos ();
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
    return this.content.lastPos ();
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
    return true;
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
    return this.content.toString () + "*";
  }
}
