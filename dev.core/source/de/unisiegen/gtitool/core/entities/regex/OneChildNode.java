package de.unisiegen.gtitool.core.entities.regex;


/**
 * Node that has only one direct child
 */
public abstract class OneChildNode extends RegexNode
{

  /**
   * Flag that indicates if Node is already used in NFA construction
   */
  private boolean marked = false;

  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#unmark()
   */
  @Override
  public void unmark ()
  {
    this.marked = false;
  }
  
  /**
   * TODO
   *
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#unmarkAll()
   */
  @Override
  public void unmarkAll ()
  {
    unmark ();
    this.regex.unmarkAll ();
  }

  /**
   * The Child of this {@link OneChildNode}
   */
  protected RegexNode regex;


  /**
   * Constructor for a Node with one direct child
   * 
   * @param regex Child of this {@link OneChildNode}
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
   * @see RegexNode#getNextNodeForNFA()
   */
  @Override
  public RegexNode getNextNodeForNFA ()
  {
    if ( !this.regex.isMarked () )
    {
      return this.regex.getNextNodeForNFA ();
    }
    this.marked = true;
    return this;
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


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return this.regex.hashCode () * 41;
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

}
