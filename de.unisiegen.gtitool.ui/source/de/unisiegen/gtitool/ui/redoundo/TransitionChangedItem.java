package de.unisiegen.gtitool.ui.redoundo;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;


/**
 * Representation of {@link RedoUndoItem} for {@link Transition} changed action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class TransitionChangedItem extends RedoUndoItem
{

  /**
   * The {@link Transition}.
   */
  private Transition transition;


  /**
   * The old {@link Symbol}s of the {@link Transition}.
   */
  private TreeSet < Symbol > oldSymbols;


  /**
   * The old {@link Word} which is read from the {@link Stack}.
   */
  private Word oldPushDownWordRead;


  /**
   * The old {@link Word} which is written from the {@link Stack}.
   */
  private Word oldPushDownWordWrite;


  /**
   * The new {@link Symbol}s of the {@link Transition}.
   */
  private TreeSet < Symbol > newSymbols;


  /**
   * The new {@link Word} which is read from the {@link Stack}.
   */
  private Word newPushDownWordRead;


  /**
   * The new {@link Word} which is written from the {@link Stack}.
   */
  private Word newPushDownWordWrite;


  /**
   * Allocates a new {@link TransitionChangedItem}.
   * 
   * @param transition The {@link Transition}.
   * @param oldPushDownWordRead The old {@link Word} which is read from the
   *          {@link Stack}.
   * @param oldPushDownWordWrite The old {@link Word} which is written from the
   *          {@link Stack}.
   * @param oldSymbols The old {@link Symbol}s of the {@link Transition}.
   */
  public TransitionChangedItem ( Transition transition,
      Word oldPushDownWordRead, Word oldPushDownWordWrite,
      TreeSet < Symbol > oldSymbols )
  {
    super ();
    this.transition = transition;
    this.oldSymbols = oldSymbols;
    this.oldPushDownWordRead = oldPushDownWordRead;
    this.oldPushDownWordWrite = oldPushDownWordWrite;

    this.newSymbols = new TreeSet < Symbol > ();
    this.newSymbols.addAll ( transition.getSymbol () );

    this.newPushDownWordRead = new DefaultWord ();
    this.newPushDownWordRead.add ( transition.getPushDownWordRead () );

    this.newPushDownWordWrite = new DefaultWord ();
    this.newPushDownWordWrite.add ( transition.getPushDownWordWrite () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    this.transition.setPushDownWordRead ( this.newPushDownWordRead );
    this.transition.setPushDownWordWrite ( this.newPushDownWordWrite );
    try
    {
      this.transition.replace ( this.newSymbols );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public final void undo ()
  {
    this.transition.setPushDownWordRead ( this.oldPushDownWordRead );
    this.transition.setPushDownWordWrite ( this.oldPushDownWordWrite );
    try
    {
      this.transition.replace ( this.oldSymbols );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
