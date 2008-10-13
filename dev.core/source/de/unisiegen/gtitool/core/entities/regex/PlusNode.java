package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * Representation of a Plus in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class PlusNode extends RegexNode
{

  /**
   * The {@link RegexNode} in the Plus
   */
  RegexNode content;


  /**
   * Constructor for a {@link PlusNode}
   * 
   * @param regex The {@link RegexNode} in the Plus
   */
  public PlusNode ( RegexNode regex )
  {
    this.content = regex;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return new ConcatenationNode ( this.content.toCoreSyntax (),
        new KleeneNode ( this.content.toCoreSyntax () ) );
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
    return this.content.nullable ();
  }


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.content.toString () + "+"; //$NON-NLS-1$
  }
}
