package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.listener.StackChangedListener;


/**
 * The test class of the {@link StyledStateParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class StyledStateParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "StackPanelTest" );
    StyledStackParserPanel styledStackParserPanel = new StyledStackParserPanel ();
    styledStackParserPanel
        .addStackChangedListener ( new StackChangedListener ()
        {

          public void stackChanged ( Stack stack )
          {
            if ( stack != null )
            {
              System.out.println ( stack );
            }
          }
        } );
    jFrame.add ( styledStackParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
