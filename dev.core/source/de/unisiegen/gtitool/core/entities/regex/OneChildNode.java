package de.unisiegen.gtitool.core.entities.regex;


/**
 * TODO
 */
public abstract class OneChildNode extends RegexNode
{

  /**
   * The Child of this {@link OneChildNode}
   */
  protected RegexNode regex;


  /**
   * Constructor for a Node with one direct child
   * 
   * @param regex Child of this {@link OneChildNode}
   * 
   */
  public OneChildNode ( RegexNode regex )
  {
    this.regex = regex;
  }

  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getWidth()
   */
  @Override
  public int getWidth ()
  {
    return this.regex.getWidth ();
  }
  
}