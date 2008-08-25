package de.unisiegen.gtitool.core.entities.regex;


/**
 * TODO
 *
 */
public class KleeneNode extends RegexNode
{
  
  private RegexNode content;
  
  /**
   * TODO
   *
   */
  public KleeneNode (RegexNode content)
  {
    this.content = content;
  }
  
  /**
   * @return the Content
   */
  @Override
  public RegexNode getContent ()
  {
    return this.content;
  }
}
