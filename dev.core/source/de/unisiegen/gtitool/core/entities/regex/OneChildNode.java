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
   * @see RegexNode#getHeight()
   */
  @Override
  public int getHeight ()
  {
    return 1 + this.regex.getHeight ();
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
  
  private boolean marked = false;
  
  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#isMarked()
   */
  @Override
  public boolean isMarked ()
  {
    return this.marked;
  }
  
  /**
   * TODO
   *
   * @return
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return this.regex.hashCode () * 41;
  }
  
  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getNextNodeForNFA()
   */
  @Override
  public RegexNode getNextNodeForNFA ()
  {
    if(!this.regex.isMarked ()) {
      return this.regex.getNextNodeForNFA ();
    }
    this.marked = true;
    return this;
  }
  
}
