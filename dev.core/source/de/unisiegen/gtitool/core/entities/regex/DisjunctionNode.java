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
 * Representation of a Disjunction in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class DisjunctionNode extends TwoChildNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = 8726582926785501568L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DisjunctionNode} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Constructor for a {@link DisjunctionNode}
   * 
   * @param regex1 First element of the {@link DisjunctionNode}
   * @param regex2 Second element of the {@link DisjunctionNode}
   */
  public DisjunctionNode ( RegexNode regex1, RegexNode regex2 )
  {
    super ( regex1, regex2 );
  }


  /**
   * {@inheritdoc}
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
   * {inheritDoc}
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
    if ( obj instanceof DisjunctionNode )
    {
      DisjunctionNode dis = ( DisjunctionNode ) obj;
      return this.regex1.equals ( dis.regex1 )
          && this.regex2.equals ( dis.regex2 );
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
   * {inheritDoc}
   * 
   * @see RegexNode#getNodeString()
   */
  @Override
  public PrettyString getNodeString ()
  {
    return new PrettyString ( new PrettyToken ( "|", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
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
   * @see RegexNode#getAllChildren()
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
    return this.regex1.nullable () || this.regex2.nullable ();
  }


  /**
   * {@inheritdoc}
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
   * {@inheritDoc}
   * 
   * @see RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return new DisjunctionNode ( this.regex1.toCoreSyntax (), this.regex2
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
        .add ( new PrettyString ( new PrettyToken ( "|", Style.REGEX_SYMBOL ) ) ); //$NON-NLS-1$
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
    return "(" + this.regex1.toString () + "|" + this.regex2.toString () + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
    

    State start1 = null;
    State start2 = null;
    State final1 = null;
    State final2 = null;

    boolean first = false;
    boolean second = false;

    for ( State s : nfa.getState () )
    {
      if ( s.isStartState () )
      {
        if ( !first )
        {
          start1 = s;
          first = true;
        }
        else
        {
          start2 = s;
        }
      }
      else if ( s.isFinalState () )
      {
        if ( !second )
        {
          final1 = s;
          second = true;
        }
        else
        {
          final2 = s;
        }
      }
    }
    if(final1 == null || final2 == null || start1 == null || start2 == null) {
      throw new IllegalArgumentException("A State is null");
    }

    DefaultState start = new DefaultState ( "s" );
    start.setStartState ( true );
    nfa.addState ( start );

    DefaultState fin = new DefaultState ( "f" );
    fin.setFinalState ( true );
    nfa.addState ( fin );

    DefaultTransition startStart1 = new DefaultTransition();
    startStart1.setStateBegin ( start );
    startStart1.setStateEnd ( start1 );
    DefaultTransition startStart2 = new DefaultTransition();
    startStart2.setStateBegin ( start );
    startStart2.setStateEnd ( start2 );
    DefaultTransition final1Final = new DefaultTransition();
    final1Final.setStateBegin ( final1 );
    final1Final.setStateEnd ( fin );
    DefaultTransition final2Final = new DefaultTransition();
    final2Final.setStateBegin ( final2 );
    final2Final.setStateEnd ( fin );

    nfa.addTransition ( startStart1 );
    nfa.addTransition ( startStart2 );
    nfa.addTransition ( final1Final );
    nfa.addTransition ( final2Final );
    
    start1.setStartState ( false );
    start2.setStartState ( false );
    final1.setFinalState ( false );
    final2.setFinalState ( false );
    
    return nfa;
  }

}
