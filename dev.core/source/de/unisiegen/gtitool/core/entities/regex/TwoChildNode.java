package de.unisiegen.gtitool.core.entities.regex;


/**
 * TODO
 */
public abstract class TwoChildNode extends RegexNode
{
  
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#isInCoreSyntax()
   */
  @Override
  public boolean isInCoreSyntax ()
  {
    return this.regex1.isInCoreSyntax () && this.regex2.isInCoreSyntax ();
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
   * @see RegexNode#getWidth()
   */
  @Override
  public int getWidth ()
  {
    return 1 + this.regex1.getWidth () + this.regex2.getWidth ();
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
    return (this.regex1.hashCode () * this.regex2.hashCode () * 23);
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
    if(!this.regex1.isMarked ()) {
      return this.regex1.getNextNodeForNFA ();
    }
    if(!this.regex2.isMarked ()) {
      return this.regex2.getNextNodeForNFA ();
    }
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
