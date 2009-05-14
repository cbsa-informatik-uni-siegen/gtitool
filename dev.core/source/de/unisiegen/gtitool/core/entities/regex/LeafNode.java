package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;
import java.util.HashSet;

import de.unisiegen.gtitool.core.util.ObjectPair;


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
   * {@inheritDoc}
   * 
   * @see RegexNode#getParentNodeForNode(RegexNode)
   */
  @Override
  public RegexNode getParentNodeForNode (
      @SuppressWarnings ( "unused" ) RegexNode node )
  {
    return null;
  }


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
   * Flag that indicates if the position should be shown.
   */
  private boolean positionShown = false;

  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#followPos()
   */
  @Override
  public HashSet < ObjectPair < LeafNode, LeafNode >> followPos ()
  {
    return new HashSet < ObjectPair<LeafNode,LeafNode> >();
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
      this.firstPosCache = new ArrayList < LeafNode > ();
      if ( ! ( this instanceof EpsilonNode ) )
      {
        this.firstPosCache.add ( this );
      }
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
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getPriority()
   */
  @Override
  public int getPriority ()
  {
    return 5;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < LeafNode > getTokenNodes ()
  {
    ArrayList < LeafNode > nodes = new ArrayList < LeafNode > ();
    nodes.add ( this );
    return nodes;
  }


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
   * Returns the positionShown.
   * 
   * @return The positionShown.
   * @see #positionShown
   */
  public boolean isPositionShown ()
  {
    return this.positionShown;
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
      this.lastPosCache = new ArrayList < LeafNode > ();
      if ( ! ( this instanceof EpsilonNode ) )
      {
        this.lastPosCache.add ( this );
      }
    }
    return this.lastPosCache;
  }


  /**
   * Sets the Position of the {@link LeafNode}
   * 
   * @param p The Position of the {@link LeafNode}
   */
  public abstract void setPosition ( int p );


  /**
   * Sets the positionShown.
   * 
   * @param positionShown The positionShown to set.
   * @see #positionShown
   */
  public void setPositionShown ( boolean positionShown )
  {
    this.positionShown = positionShown;
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
  }

}
