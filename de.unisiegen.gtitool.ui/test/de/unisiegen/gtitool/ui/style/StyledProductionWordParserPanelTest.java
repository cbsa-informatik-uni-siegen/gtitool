package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.listener.ProductionWordChangedListener;


/**
 * The test class of the {@link StyledProductionWordParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class StyledProductionWordParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "ProductionWordPanelTest" );
    StyledProductionWordParserPanel styledProductionWordParserPanel = new StyledProductionWordParserPanel ();
    styledProductionWordParserPanel
        .addProductionWordChangedListener ( new ProductionWordChangedListener ()
        {

          public void productionWordChanged ( ProductionWord productionWord )
          {
            if ( productionWord != null )
            {
              System.out.println ( productionWord );
            }
          }
        } );
    jFrame.add ( styledProductionWordParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setVisible ( true );
  }
}
