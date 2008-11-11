package de.unisiegen.gtitool.core.entities.regex;


import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * Representation of a Concatenation in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class ConcatenationNode extends TwoChildNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = 8972113015715049815L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link ConcatenationNode} in the source code.
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Constructor for a {@link ConcatenationNode}
   * 
   * @param regex1 First element of the {@link ConcatenationNode}
   * @param regex2 Second element of the {@link ConcatenationNode}
   */
  public ConcatenationNode ( RegexNode regex1, RegexNode regex2 )
  {
    super ( regex1, regex2 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#addPrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.add ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( @SuppressWarnings ( "unused" )
  RegexNode o )
  {
    return 0;
  }


  /**
   * Counts the left Children of the {@link ConcatenationNode}
   * 
   * @return The leftChildren count
   */
  public int countLeftChildren ()
  {
    return 1 + this.regex1.getAllChildren ().size ();
  }


  /**
   * Counts the right Children of the {@link ConcatenationNode}
   * 
   * @return The rightChildren count
   */
  public int countRightChildren ()
  {
    return 1 + this.regex2.getAllChildren ().size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( Object obj )
  {
    if ( obj == this )
    {
      return true;
    }
    if ( obj instanceof ConcatenationNode )
    {
      ConcatenationNode con = ( ConcatenationNode ) obj;
      return this.regex1.equals ( con.regex1 )
          && this.regex2.equals ( con.regex2 );
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#firstPos()
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
   * {@inheritDoc}
   * 
   * @see RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < RegexNode > getAllChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this.regex1 );
    nodes.add ( this.regex2 );
    nodes.addAll ( this.regex1.getAllChildren () );
    nodes.addAll ( this.regex2.getAllChildren () );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getChildren()
   */
  @Override
  public ArrayList < RegexNode > getChildren ()
  {
    ArrayList < RegexNode > nodes = new ArrayList < RegexNode > ();
    nodes.add ( this.regex1 );
    nodes.add ( this.regex2 );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getLeftChildrenCount()
   */
  @Override
  public int getLeftChildrenCount ()
  {
    return 1 + this.regex1.getAllChildren ().size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getNodeString()
   */
  @Override
  public PrettyString getNodeString ()
  {
    return new PrettyString ( new PrettyToken ( "·", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#getParserOffset()
   */
  public ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getRightChildrenCount()
   */
  @Override
  public int getRightChildrenCount ()
  {
    return 1 + this.regex2.getAllChildren ().size ();
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
    nodes.addAll ( this.regex1.getTokenNodes () );
    nodes.addAll ( this.regex2.getTokenNodes () );
    return nodes;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#lastPos()
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
   * {@inheritDoc}
   * 
   * @see RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return this.regex1.nullable () && this.regex2.nullable ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#removePrettyStringChangedListener(de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener)
   */
  public void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.remove ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#setParserOffset(ParserOffset)
   */
  public void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * /** {@inheritDoc}
   * 
   * @see RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return new ConcatenationNode ( this.regex1.toCoreSyntax (), this.regex2
        .toCoreSyntax () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    PrettyString string = this.regex1.toPrettyString ();
    string
        .add ( new PrettyString ( new PrettyToken ( "·", Style.REGEX_SYMBOL ) ) ); //$NON-NLS-1$
    string.add ( this.regex2.toPrettyString () );
    return string;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.regex1.toString () + this.regex2.toString ();
  }


  /**
   * TODO
   * 
   * @return
   * @throws StateException
   * @see de.unisiegen.gtitool.core.entities.regex.RegexNode#toNFA()
   */
  @Override
  public DefaultENFA toNFA (Alphabet a) throws StateException
  {
    DefaultENFA nfa = new DefaultENFA ( a, a, false );
    DefaultENFA nfaRegex1 = this.regex1.toNFA (a);
    DefaultENFA nfaRegex2 = this.regex2.toNFA (a);
    HashMap<Integer, State> newStates = new HashMap < Integer, State >();
    for(State s : nfaRegex1.getState ()) {
      State newState = new DefaultState(s.getAlphabet (), s.getPushDownAlphabet (), s.getName (), s.isStartState (), s.isFinalState ());
      nfa.addState ( newState );
      newStates.put ( s.getId (), newState );
    }
    for(Transition t : nfaRegex1.getTransition ()) {
      try
      {
        nfa.addTransition ( new DefaultTransition(t.getAlphabet (), t.getPushDownAlphabet (), t.getPushDownWordRead (), t.getPushDownWordWrite (), newStates.get ( t.getStateBegin ().getId () ), newStates.get ( t.getStateEnd ().getId () ), t.getSymbol ()) );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace();
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
      {
        exc.printStackTrace();
      }
    }
    for(State s : nfaRegex2.getState ()) {
      State newState = new DefaultState(s.getAlphabet (), s.getPushDownAlphabet (), s.getName (), s.isStartState (), s.isFinalState ());
      nfa.addState ( newState );
      newStates.put ( s.getId (), newState );
    }
    for(Transition t : nfaRegex2.getTransition ()) {
      try
      {
        nfa.addTransition ( new DefaultTransition(t.getAlphabet (), t.getPushDownAlphabet (), t.getPushDownWordRead (), t.getPushDownWordWrite (), newStates.get ( t.getStateBegin ().getId () ), newStates.get ( t.getStateEnd ().getId () ), t.getSymbol ()) );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace();
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
      {
        exc.printStackTrace();
      }
    }
    State startState2 = null;
    State finalState1 = null;
    for ( State s : nfa.getState () )
    {
      if ( s.isFinalState () )
      {
        finalState1 = s;
        break;
      }
    }
    boolean b = false;
    for ( State s : nfa.getState () )
    {
      if ( s.isStartState () )
      {
        if ( !b )
        {
          b = true;
        }
        else
        {
          startState2 = s;
          break;
        }
      }
    }
    if ( finalState1 == null || startState2 == null )
    {
      throw new IllegalArgumentException (
          "Start state or final state does not exist" );
    }
    finalState1.setFinalState ( false );
    for ( Transition t : startState2.getTransitionBegin () )
    {
      try
      {
        nfa.addTransition ( new DefaultTransition(t.getAlphabet (), t.getPushDownAlphabet (), t.getPushDownWordRead (), t.getPushDownWordWrite (), finalState1, t.getStateEnd (), t.getSymbol ()) );
      }
      catch ( TransitionSymbolNotInAlphabetException exc )
      {
        exc.printStackTrace();
      }
      catch ( TransitionSymbolOnlyOneTimeException exc )
      {
        exc.printStackTrace();
      }
    }
    
    nfa.removeState ( startState2 );
    return nfa;
  }
}
