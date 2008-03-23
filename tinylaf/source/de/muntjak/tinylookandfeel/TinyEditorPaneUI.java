/*******************************************************************************
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Tiny Look and Feel * * (C) Copyright 2003 - 2007 Hans Bickel * * For
 * licensing information and credits, please refer to the * comment in file
 * de.muntjak.tinylookandfeel.TinyLookAndFeel * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package de.muntjak.tinylookandfeel;


import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicEditorPaneUI;
import javax.swing.text.JTextComponent;


/**
 * TinyEditorPaneUI
 * 
 * @version 1.0
 * @author Hans Bickel
 */
@SuppressWarnings (
{ "all" } )
public class TinyEditorPaneUI extends BasicEditorPaneUI
{

  JTextComponent editor;


  public static ComponentUI createUI ( JComponent c )
  {
    return new TinyEditorPaneUI ();
  }


  public void installUI ( JComponent c )
  {
    if ( c instanceof JTextComponent )
    {
      editor = ( JTextComponent ) c;
    }

    super.installUI ( c );
  }


  protected void installDefaults ()
  {
    super.installDefaults ();
  }
}
