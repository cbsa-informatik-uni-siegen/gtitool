package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link Word} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Word extends Entity < Word >, Storable, Iterable < Symbol >
{

  /**
   * Appends the specified {@link Symbol}s to the end of this {@link Word}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this {@link Word}.
   */
  public void add ( Iterable < Symbol > symbols );


  /**
   * Appends the specified {@link Symbol} to the end of this {@link Word}.
   * 
   * @param symbol The {@link Symbol} to be appended to this {@link Word}.
   */
  public void add ( Symbol symbol );


  /**
   * Appends the specified {@link Symbol}s to the end of this {@link Word}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this {@link Word}.
   */
  public void add ( Symbol ... symbols );


  /**
   * Returns true if this {@link Word} contains the given {@link Symbol}.
   * Otherwise false.
   * 
   * @param symbol The {@link Symbol}.
   * @return True if this {@link Word} contains the given {@link Symbol}.
   *         Otherwise false.
   */
  public boolean contains ( Symbol symbol );


  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public ArrayList < Symbol > get ();


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   */
  public Symbol get ( int index );


  /**
   * Returns the readed {@link Symbol}s.
   * 
   * @return The readed {@link Symbol}s.
   * @throws WordFinishedException If something with the {@link DefaultWord} is
   *           not correct.
   * @throws WordResetedException If something with the {@link DefaultWord} is
   *           not correct.
   */
  public ArrayList < Symbol > getReadedSymbols () throws WordFinishedException,
      WordResetedException;


  /**
   * Returns true if this word is finished, otherwise false.
   * 
   * @return True if this word is finished, otherwise false.
   */
  public boolean isFinished ();


  /**
   * Returns true if this word is reseted, otherwise false.
   * 
   * @return True if this word is reseted, otherwise false.
   */
  public boolean isReseted ();


  /**
   * Returns the next {@link Symbol} and increments the current position.
   * 
   * @return The next {@link Symbol}.
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   */
  public Symbol nextSymbol () throws WordFinishedException;


  /**
   * Returns the previous {@link Symbol} and decrements the current position.
   * 
   * @return The previous {@link Symbol}.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   */
  public Symbol previousSymbol () throws WordResetedException;


  /**
   * Returns the number of {@link Symbol}s in this {@link Word}.
   * 
   * @return The number of {@link Symbol}s in this {@link Word}.
   */
  public int size ();


  /**
   * Resets the current position of this {@link Word}.
   */
  public void start ();
}
