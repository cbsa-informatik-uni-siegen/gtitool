package de.unisiegen.gtitool.core.entities.regex;

import java.util.ArrayList;


/**
 * TODO
 */
public class OptionalNode extends RegexNode
{

  private RegexNode content;


  /**
   * TODO
   */
  public OptionalNode ( RegexNode regex )
  {
    this.content = regex;
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
    return this.content.getChildren ();
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
    return null;
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
    return this.content.toString () + "?";
  }
}
