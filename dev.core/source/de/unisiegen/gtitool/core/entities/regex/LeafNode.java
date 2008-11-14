package de.unisiegen.gtitool.core.entities.regex;


/**
 * Representation of a LeafNode in the Regex
 */
public abstract class LeafNode extends RegexNode
{

  /**
   * Flag that indicates if Node is already used in NFA construction
   */
  private boolean marked = false;


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
   * {@inheritDoc}
   * 
   * @see RegexNode#getNextNodeForNFA()
   */
  @Override
  public RegexNode getNextNodeForNFA ()
  {
    this.marked = true;
    return this;
  }


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
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return getPosition ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#isMarked()
   */
  @Override
  public boolean isMarked ()
  {
    return this.marked;
  }


  /**
   * Sets the Position of the {@link LeafNode}
   * 
   * @param p The Position of the {@link LeafNode}
   */
  public abstract void setPosition ( int p );

}
