package de.unisiegen.gtitool.core.entities.regex;


/**
 * Representation of a LeafNode in the Regex
 */
public abstract class LeafNode extends RegexNode
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7062093234164837073L;


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
   * {@inheritDoc}
   * 
   * @see RegexNode#isMarkedAll()
   */
  @Override
  public boolean isMarkedAll ()
  {
    return this.marked;
  }


  /**
   * Sets the Position of the {@link LeafNode}
   * 
   * @param p The Position of the {@link LeafNode}
   */
  public abstract void setPosition ( int p );


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#unmark()
   */
  @Override
  public void unmark ()
  {
    this.marked = false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#unmarkAll()
   */
  @Override
  public void unmarkAll ()
  {
    unmark ();
  }

}
