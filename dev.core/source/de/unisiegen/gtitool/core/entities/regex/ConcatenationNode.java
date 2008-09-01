package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;


/**
 * Representation of a Concatenation in the Regex
 * 
 * @author Simon Meurer
 */
public class ConcatenationNode extends RegexNode
{

  /**
   * First element of the Concatenation
   */
  private RegexNode regex1;


  /**
   * Second element of the Concatenation
   */
  private RegexNode regex2;


  /**
   * Constructor for a {@link ConcatenationNode}
   * 
   * @param regex1 First element of the {@link ConcatenationNode}
   * @param regex2 Second element of the {@link ConcatenationNode}
   */
  public ConcatenationNode ( RegexNode regex1, RegexNode regex2 )
  {
    this.regex1 = regex1;
    this.regex2 = regex2;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return new ConcatenationNode ( this.regex1.toCoreSyntax (), this.regex2
        .toCoreSyntax () );
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this.regex1 );
    nodes.add ( this.regex2 );
    nodes.addAll ( this.regex1.getChildren () );
    nodes.addAll ( this.regex2.getChildren () );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#getTokenNodes()
   */
  @Override
  public ArrayList < RegexNode > getTokenNodes ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.addAll ( this.regex1.getTokenNodes () );
    nodes.addAll ( this.regex2.getTokenNodes () );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#firstPos()
   */
  @Override
  public ArrayList < RegexNode > firstPos ()
  {
    if ( !this.regex1.nullable () )
    {
      return this.regex1.firstPos ();
    }
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.addAll ( this.regex1.firstPos () );
    nodes.addAll ( this.regex2.firstPos () );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    if ( !this.regex2.nullable () )
    {
      return this.regex2.lastPos ();
    }
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.addAll ( this.regex1.lastPos () );
    nodes.addAll ( this.regex2.lastPos () );
    return nodes;
  }


  /**
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return this.regex1.nullable () && this.regex2.nullable ();
  }


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString ()
  {
    return "(" + this.regex1.toString () + this.regex2.toString () + ")"; //$NON-NLS-1$ //$NON-NLS-2$
  }
}
