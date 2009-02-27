package de.unisiegen.gtitool.core.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.regex.ConcatenationNode;
import de.unisiegen.gtitool.core.entities.regex.DisjunctionNode;
import de.unisiegen.gtitool.core.entities.regex.EpsilonNode;
import de.unisiegen.gtitool.core.entities.regex.KleeneNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.entities.regex.TokenNode;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;


/**
 * TODO
 */
public class MachineToRegexTester
{

  /**
   * TODO
   * 
   * @throws AlphabetException
   * @throws StateException
   * @throws TransitionSymbolOnlyOneTimeException
   * @throws TransitionSymbolNotInAlphabetException
   */
  public MachineToRegexTester () throws AlphabetException, StateException,
      TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException
  {
    DefaultSymbol nul = new DefaultSymbol ( "0" );
    DefaultSymbol one = new DefaultSymbol ( "1" );
    DefaultAlphabet alphabet = new DefaultAlphabet ( nul, one );
    DefaultDFA dfa = new DefaultDFA ( alphabet, alphabet, false );
    DefaultState s0 = new DefaultState ( "s0" );
    DefaultState s1 = new DefaultState ( "s1" );
    DefaultState s2 = new DefaultState ( "s2" );
    dfa.addState ( s0 );
    dfa.addState ( s1 );
    dfa.addState ( s2 );
    s0.setStartState ( true );
    s1.setFinalState ( true );
    s2.setFinalState ( true );
    dfa.addTransition ( new DefaultTransition ( alphabet, alphabet,
        new DefaultWord (), new DefaultWord (), s0, s1, one ) );
    dfa.addTransition ( new DefaultTransition ( alphabet, alphabet,
        new DefaultWord (), new DefaultWord (), s0, s0, nul ) );
    dfa.addTransition ( new DefaultTransition ( alphabet, alphabet,
        new DefaultWord (), new DefaultWord (), s1, s0, nul ) );
    dfa.addTransition ( new DefaultTransition ( alphabet, alphabet,
        new DefaultWord (), new DefaultWord (), s1, s2, one ) );
    dfa.addTransition ( new DefaultTransition ( alphabet, alphabet,
        new DefaultWord (), new DefaultWord (), s2, s2, one ) );
    dfa.addTransition ( new DefaultTransition ( alphabet, alphabet,
        new DefaultWord (), new DefaultWord (), s2, s0, nul ) );

    System.err.println ( toRegex ( dfa ) );
  }


  /**
   * TODO
   * 
   * @param dfa
   * @return
   */
  private RegexNode toRegex ( DFA dfa )
  {
    State start = null;
    ArrayList < State > finals = new ArrayList < State > ();
    for ( State s : dfa.getState () )
    {
      if ( s.isStartState () )
      {
        start = s;
      }
      if ( s.isFinalState () )
      {
        finals.add ( s );
      }
    }
    if ( start == null )
    {
      return null;
    }

    ArrayList < RegexNode > LA = new ArrayList < RegexNode > ();
    for ( State fin : finals )
    {
      LA.add ( getLK ( dfa, dfa.getState (), start, fin, dfa.getState ()
          .size () + 1 ) );
    }

    return createDisjunctionFromNodes ( LA );
  }


  private RegexNode getL1 ( DFA dfa, State s0, State s1 )
  {
    ArrayList < Symbol > l1 = new ArrayList < Symbol > ();
    if ( s0.equals ( s1 ) )
    {
      l1.add ( new DefaultSymbol () );
    }
    for ( Transition t : dfa.getTransition () )
    {
      if ( t.getStateBegin ().equals ( s0 ) && t.getStateEnd ().equals ( s1 ) )
      {
        l1.addAll ( t.getSymbol () );
      }
    }
    return createDisjunction ( l1 );
  }


  private RegexNode getLK ( DFA dfa, ArrayList < State > states, State s0,
      State s1, int k )
  {
    if ( k-- != 1 )
    {
      State s = states.get ( k-1 );

      RegexNode LIJ = getLK ( dfa, states, s0, s1, k );
      RegexNode LIK = getLK ( dfa, states, s0, s, k );
      RegexNode LKK = getLK ( dfa, states, s, s, k );
      RegexNode LKJ = getLK ( dfa, states, s, s1, k );

      System.err.println ( "s0: " + s0 + ", s1: " + s1 + ", s: " + s + ", k = "
          + ( k + 1 ) );
      System.err.println ( "LIJ: " + LIJ );
      System.err.println ( "LIK: " + LIK );
      System.err.println ( "LKK: " + LKK );
      System.err.println ( "LKJ: " + LKJ );

      return generateRegex ( LIJ, LIK, LKK, LKJ );

    }
    System.err.println ( "s0: " + s0 + ", s1: " + s1 + ", k = " + ( k + 1 ) );

    return getL1 ( dfa, s0, s1 );
  }


  private RegexNode generateRegex ( RegexNode LIJ, RegexNode LIK,
      RegexNode LKK, RegexNode LKJ )
  {
    RegexNode regex = generateRegex ( LIK, LKK, LKJ );
    if ( LIJ == null )
    {
      return regex;
    }
    if ( regex == null )
    {
      return LIJ;
    }
    return new DisjunctionNode ( LIJ, regex );
  }


  private RegexNode generateRegex ( RegexNode LIK, RegexNode LKK, RegexNode LKJ )
  {
    RegexNode regex = generateRegex ( LKK, LKJ );
    if ( LIK == null )
    {
      return regex;
    }
    if ( regex == null )
    {
      return LIK;
    }
    return new ConcatenationNode ( LIK, regex );
  }


  private RegexNode generateRegex ( RegexNode LKK, RegexNode LKJ )
  {
    RegexNode regex = generateRegex ( LKJ );
    if ( LKK == null )
    {
      return regex;
    }
    if ( regex == null )
    {
      return LKK;
    }
    return new ConcatenationNode ( new KleeneNode ( LKK ), LKJ );
  }


  private RegexNode generateRegex ( RegexNode LKJ )
  {
    return LKJ;
  }


  private RegexNode createDisjunctionFromNodes ( ArrayList < RegexNode > nodes )
  {
    if ( nodes.size () < 1 )
    {
      return null;
    }
    RegexNode r = nodes.remove ( 0 );

    if ( nodes.size () == 0 )
    {
      return r;
    }
    return new DisjunctionNode ( createDisjunctionFromNodes ( nodes ), r );
  }


  private RegexNode createDisjunction ( ArrayList < Symbol > symbols )
  {
    if ( symbols.size () < 1 )
    {
      return null;
    }
    Symbol s = symbols.remove ( 0 );
    RegexNode r;
    if ( s.equals ( new DefaultSymbol () ) )
    {
      r = new EpsilonNode ();
    }
    else
    {
      r = new TokenNode ( s.getName () );
    }
    if ( symbols.size () == 0 )
    {
      return r;
    }
    return new DisjunctionNode ( createDisjunction ( symbols ), r );
  }


  public static void main ( String [] args )
  {
    try
    {
      new MachineToRegexTester ();
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }
  }
}
