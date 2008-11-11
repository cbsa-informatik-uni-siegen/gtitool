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
 * Representation of a Kleene Closure in the Regex
 * 
 * @author Simon Meurer
 * @version
 */
public class KleeneNode extends OneChildNode
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = 1206649719571638011L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link KleeneNode} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * Constructor for a {@link KleeneNode}
   * 
   * @param content The {@link RegexNode} of the Kleene Closure
   */
  public KleeneNode ( RegexNode content )
  {
    super ( content );
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
    if ( obj instanceof KleeneNode )
    {
      KleeneNode node = ( KleeneNode ) obj;
      return this.regex.equals ( node.regex );
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
    return this.regex.firstPos ();
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
    nodes.addAll ( this.regex.getAllChildren () );
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
    nodes.add ( this.regex );
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
    return this.regex.getLeftChildrenCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getNodeString()
   */
  @Override
  public PrettyString getNodeString ()
  {
    return new PrettyString ( new PrettyToken ( "*", Style.REGEX_SYMBOL ) ); //$NON-NLS-1$
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
    return this.regex.getRightChildrenCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#getAllChildren()
   */
  @Override
  public ArrayList < LeafNode > getTokenNodes ()
  {
    return this.regex.getTokenNodes ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#lastPos()
   */
  @Override
  public ArrayList < RegexNode > lastPos ()
  {
    return this.regex.lastPos ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RegexNode#nullable()
   */
  @Override
  public boolean nullable ()
  {
    return true;
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
   * {@inheritDoc}
   * 
   * @see RegexNode#toCoreSyntax()
   */
  @Override
  public RegexNode toCoreSyntax ()
  {
    return new KleeneNode ( this.regex.toCoreSyntax () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public PrettyString toPrettyString ()
  {
    PrettyString string = this.regex.toPrettyString ();
    string
        .add ( new PrettyString ( new PrettyToken ( "*", Style.REGEX_SYMBOL ) ) ); //$NON-NLS-1$
    return string;
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
    DefaultENFA nfa =new DefaultENFA(a, a, false);
    DefaultENFA nfaRegex1 = this.regex.toNFA ( a );
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
    
    State startRegex = null;
    State endRegex = null;
    
    for(State s : nfa.getState ()) {
      if(s.isStartState ()) {
        startRegex = s;
      } else if(s.isFinalState ()){
        endRegex = s;
      }
    }
    
    if(startRegex == null || endRegex == null) {
      throw new IllegalArgumentException("States are wrong");
    }
    
    DefaultState start = new DefaultState("s");
    start.setStartState ( true );
    nfa.addState ( start );

    DefaultState fin = new DefaultState("f");
    fin.setFinalState ( true );
    nfa.addState ( fin );
    

    // From start to final
    Transition t = new DefaultTransition();
    t.setStateBegin ( start );
    t.setStateEnd ( fin );
    nfa.addTransition ( t );

    // From start to begin of N(s)
    t = new DefaultTransition();
    t.setStateBegin ( start );
    t.setStateEnd ( startRegex );
    nfa.addTransition ( t );
    
    // From end to begin of N(s)
    t = new DefaultTransition();
    t.setStateBegin ( endRegex );
    t.setStateEnd ( startRegex );
    nfa.addTransition ( t );

    // From end of N(s) to final
    t = new DefaultTransition();
    t.setStateBegin ( endRegex );
    t.setStateEnd ( fin );
    nfa.addTransition ( t );
    
    startRegex.setStartState ( false );
    endRegex.setFinalState ( false );
    
    return nfa;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public String toString ()
  {
    return this.regex.toString () + "*"; //$NON-NLS-1$
  }
}
