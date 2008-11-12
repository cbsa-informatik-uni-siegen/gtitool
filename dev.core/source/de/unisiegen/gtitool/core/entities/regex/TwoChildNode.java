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
}
