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
   * Sets the Position of the {@link LeafNode}
   * 
   * @param p The Position of the {@link LeafNode}
   */
  public abstract void setPosition ( int p );
  
}
