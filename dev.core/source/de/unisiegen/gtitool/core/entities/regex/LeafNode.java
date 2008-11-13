package de.unisiegen.gtitool.core.entities.regex;



/**
 * Representation of a LeafNode in the Regex
 */
public abstract class LeafNode extends RegexNode
{

  /**
   * Returns the Position of the {@link LeafNode}
   * 
   * @return The Position of the {@link LeafNode}
   */
  public abstract int getPosition ();


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getWidth()
   */
  @Override
  public int getWidth ()
  {
    return 1;
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getHeight()
   */
  @Override
  public int getHeight ()
  {
    return 1;
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
    return getPosition ();
  }
  
  /**
   * Sets the Position of the {@link LeafNode}
   * 
   * @param p The Position of the {@link LeafNode}
   */
  public abstract void setPosition ( int p );
  
  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getNextNodeForNFA()
   */
  @Override
  public RegexNode getNextNodeForNFA ()
  {
    this.marked = true;
    return this;
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
  
}
