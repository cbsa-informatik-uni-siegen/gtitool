package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * Representation of a RegexNode with two children
 */
public abstract class TwoChildNode extends RegexNode
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3752945655164551118L;


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getParentNodeForNode(RegexNode)
   */
  @Override
  public RegexNode getParentNodeForNode ( RegexNode node )
  {
    if ( this.regex1.equals ( node ) || this.regex2.equals ( node ) )
    {
      return this;
    }
    RegexNode newNode = this.regex1.getParentNodeForNode ( node );
    if ( newNode == null )
    {
      newNode = this.regex2.getParentNodeForNode ( node );
    }
    return newNode;
  }


  /**
   * Sets the regex1.
   * 
   * @param regex1 The regex1 to set.
   * @see #regex1
   */
  public void setRegex1 ( RegexNode regex1 )
  {
    this.childrenCache = null;
    this.regex1 = regex1;
  }


  /**
   * Sets the regex2.
   * 
   * @param regex2 The regex2 to set.
   * @see #regex2
   */
  public void setRegex2 ( RegexNode regex2 )
  {
    this.childrenCache = null;
    this.regex2 = regex2;
  }


  /**
   * Cached {@link ArrayList} for the children
   */
  private transient ArrayList < RegexNode > childrenCache = null;


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
   * {@inheritDoc}
   * 
   * @see RegexNode#getNextUnfinishedNode()
   */
  @Override
  public UnfinishedNode getNextUnfinishedNode ()
  {
    UnfinishedNode node = this.regex1.getNextUnfinishedNode ();
    if ( node == null )
    {
      node = this.regex2.getNextUnfinishedNode ();
    }
    return node;
  }


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
   * @see RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    if ( this.childrenCache == null )
    {
      this.childrenCache = new ArrayList < RegexNode > ();
      this.childrenCache.add ( this.regex1 );
      this.childrenCache.add ( this.regex2 );
    }
    return this.childrenCache;
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
    if ( !this.marked )
    {
      this.marked = true;
      return this;
    }
    if ( !this.regex1.isMarkedAll () )
    {
      return this.regex1.getNextNodeForNFA ();
    }
    if ( !this.regex2.isMarkedAll () )
    {
      return this.regex2.getNextNodeForNFA ();
    }
    return null;
  }


  /**
   * Returns the regex1.
   * 
   * @return The regex1.
   * @see #regex1
   */
  public RegexNode getRegex1 ()
  {
    return this.regex1;
  }


  /**
   * Returns the regex2.
   * 
   * @return The regex2.
   * @see #regex2
   */
  public RegexNode getRegex2 ()
  {
    return this.regex2;
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
   * {@inheritDoc}
   * 
   * @see RegexNode#isMarkedAll()
   */
  @Override
  public boolean isMarkedAll ()
  {
    return this.marked && this.regex1.isMarkedAll ()
        && this.regex2.isMarkedAll ();
  }


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
    this.regex1.unmarkAll ();
    this.regex2.unmarkAll ();
  }
}
