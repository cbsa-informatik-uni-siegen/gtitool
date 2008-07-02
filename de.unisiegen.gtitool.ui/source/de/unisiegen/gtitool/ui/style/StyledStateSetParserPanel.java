package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.stateset.StateSetParseable;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link StateSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledStateSetParserPanel.java 946 2008-05-30 14:27:24Z fehler
 *          $
 */
public final class StyledStateSetParserPanel extends
    StyledParserPanel < StateSet >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -177852679422657040L;


  /**
   * Every {@link State} in the {@link StateSet} has to be in this {@link State}
   * list.
   */
  private ArrayList < State > stateList = null;


  /**
   * Allocates a new {@link StyledStateSetParserPanel}.
   */
  public StyledStateSetParserPanel ()
  {
    super ( new StateSetParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final StateSet checkParsedObject ( StateSet stateSet )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.stateList != null ) && ( stateSet != null ) )
    {
      for ( State current : stateSet )
      {
        boolean found = false;
        for ( State currentState : this.stateList )
        {
          if ( current.getName ().equals ( currentState.getName () ) )
          {
            found = true;
            break;
          }
        }

        if ( !found )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getString ( "StyledStateSetParserPanel.StateNotInStateSet", //$NON-NLS-1$
                  current.getName (), this.stateList ) ) );
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return stateSet;
  }


  /**
   * Sets the {@link State} list. Every {@link State} in the {@link StateSet}
   * has to be in the {@link State} list.
   * 
   * @param statesList The {@link State} list to set.
   */
  public final void setStateList ( ArrayList < State > statesList )
  {
    this.stateList = statesList;
  }
}
