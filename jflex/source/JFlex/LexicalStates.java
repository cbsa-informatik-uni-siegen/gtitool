/*******************************************************************************
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * JFlex
 * 1.4.1 * Copyright (C) 1998-2004 Gerwin Klein <lsf@jflex.de> * All rights
 * reserved. * * This program is free software; you can redistribute it and/or
 * modify * it under the terms of the GNU General Public License. See the file *
 * COPYRIGHT for more information. * * This program is distributed in the hope
 * that it will be useful, * but WITHOUT ANY WARRANTY; without even the implied
 * warranty of * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the *
 * GNU General Public License for more details. * * You should have received a
 * copy of the GNU General Public License along * with this program; if not,
 * write to the Free Software Foundation, Inc., * 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package JFlex;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


/**
 * Simple symbol table, mapping lexical state names to integers.
 * 
 * @author Gerwin Klein
 * @version JFlex 1.4.1, $Revision$, $Date: 2007-11-30 00:20:03 +0100 (Fr,
 *          30 Nov 2007) $
 */
@SuppressWarnings (
{ "all" } )
public class LexicalStates
{

  /** maps state name to state number */
  Hashtable < String, Integer > states;


  /** codes of inclusive states (subset of states) */
  Vector < Integer > inclusive;


  /** number of declared states */
  int numStates;


  /**
   * constructs a new lexical state symbol table
   */
  public LexicalStates ()
  {
    states = new Hashtable < String, Integer > ();
    inclusive = new Vector < Integer > ();
  }


  /**
   * insert a new state declaration
   */
  public void insert ( String name, boolean is_inclusive )
  {
    if ( states.containsKey ( name ) )
      return;

    Integer code = new Integer ( numStates++ );
    states.put ( name, code );

    if ( is_inclusive )
      inclusive.addElement ( code );
  }


  /**
   * returns the number (code) of a declared state, <code>null</code> if no
   * such state has been declared.
   */
  public Integer getNumber ( String name )
  {
    return ( Integer ) states.get ( name );
  }


  /**
   * returns the number of declared states
   */
  public int number ()
  {
    return numStates;
  }


  /**
   * returns the names of all states
   */
  public Enumeration names ()
  {
    return states.keys ();
  }


  /**
   * returns the code of all inclusive states
   */
  public Enumeration getInclusiveStates ()
  {
    return inclusive.elements ();
  }
}
