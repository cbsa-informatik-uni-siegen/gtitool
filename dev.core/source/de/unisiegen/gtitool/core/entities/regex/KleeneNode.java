package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * Representation of a Kleene Closure in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class KleeneNode extends RegexNode
{

  /**
   * The {@link RegexNode} of the Kleene Closure
   */
  private RegexNode content;


  /**
   * Constructor for a {@link KleeneNode}
   * 
   * @param content The {@link RegexNode} of the Kleene Closure
   */
  public KleeneNode ( RegexNode content )
  {
    this.content = content;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return new KleeneNode ( this.content.toCoreSyntax () );
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this.content );
    nodes.addAll ( this.content.getChildren () );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getTokenNodes ()
  {
    return this.content.getTokenNodes ();
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#firstPos()
   */
  @Override
  public ArrayList < RegexNode > firstPos ()
  {
    return this.content.firstPos ();
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    return this.content.lastPos ();
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return true;
  }


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.content.toString () + "*"; //$NON-NLS-1$
  }
}
