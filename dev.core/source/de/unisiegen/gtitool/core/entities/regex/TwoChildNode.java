package de.unisiegen.gtitool.core.entities.regex;


/**
 * TODO
 */
public abstract class TwoChildNode extends RegexNode
{

  /**
   * Flag that indicates if Node is already used in NFA construction
   */
  private boolean marked = false;


  /**
   * The first {@link RegexNode}
   */
  protected RegexNode regex1;


  /**
   * The second {@link RegexNode}
   */
  protected RegexNode regex2;


  /**
   * Constructor for a {@link RegexNode} with two direct children
   * 
   * @param regex1 The first {@link RegexNode}
   * @param regex2 The second {@link RegexNode}
   */
  public TwoChildNode ( RegexNode regex1, RegexNode regex2 )
  {
    this.regex1 = regex1;
    this.regex2 = regex2;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getHeight()
   */
  @Override
  public int getHeight ()
  {
    if ( this.regex1.getHeight () > this.regex2.getHeight () )
    {
      return 1 + this.regex1.getHeight ();
    }
    return 1 + this.regex2.getHeight ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getNextNodeForNFA()
   */
  @Override
  public RegexNode getNextNodeForNFA ()
  {
    if ( !this.regex1.isMarked () )
    {
      return this.regex1.getNextNodeForNFA ();
    }
    if ( !this.regex2.isMarked () )
    {
      return this.regex2.getNextNodeForNFA ();
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
    return 1 + this.regex1.getWidth () + this.regex2.getWidth ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode ()
  {
    return ( this.regex1.hashCode () * this.regex2.hashCode () * 23 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#isInCoreSyntax()
   */
  @Override
  public boolean isInCoreSyntax ()
  {
    return this.regex1.isInCoreSyntax () && this.regex2.isInCoreSyntax ();
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
    this.regex1.unmarkAll ();
    this.regex2.unmarkAll ();
  }
}
