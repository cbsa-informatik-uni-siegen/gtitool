package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * Node that has only one direct child
 */
public abstract class OneChildNode extends RegexNode
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3109784857649152942L;


  /**
   * Cached {@link ArrayList} for firstPos
   */
  private transient ArrayList < LeafNode > firstPosCache = null;


  /**
   * Cached {@link ArrayList} for lastPos
   */
  private transient ArrayList < LeafNode > lastPosCache = null;


  /**
   * Flag that indicates if Node is already used in NFA construction
   */
  private boolean marked = false;


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
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#firstPos()
   */
  @Override
  public ArrayList < LeafNode > firstPos ()
  {
    if ( this.firstPosCache == null )
    {
      this.firstPosCache = this.regex.firstPos ();
    }
    return this.firstPosCache;
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
    if ( !this.marked )
    {
      this.marked = true;
      return this;
    }
    return this.regex.getNextNodeForNFA ();
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


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#isMarkedAll()
   */
  @Override
  public boolean isMarkedAll ()
  {
    return this.marked && this.regex.isMarkedAll ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#lastPos()
   */
  @Override
  public ArrayList < LeafNode > lastPos ()
  {
    if ( this.lastPosCache == null )
    {
      this.lastPosCache = this.regex.lastPos ();
    }
    return this.lastPosCache;
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
    this.regex.unmarkAll ();
  }

}
