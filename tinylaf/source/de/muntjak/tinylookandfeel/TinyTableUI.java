/*******************************************************************************
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Tiny Look and Feel * * (C) Copyright 2003 - 2007 Hans Bickel * * For
 * licensing information and credits, please refer to the * comment in file
 * de.muntjak.tinylookandfeel.TinyLookAndFeel * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package de.muntjak.tinylookandfeel;


import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;


/**
 * TinyTableUI
 * 
 * @version 1.0
 * @author Hans Bickel
 */
@SuppressWarnings (
{ "all" } )
public class TinyTableUI extends BasicTableUI
{

  JTable table;


  public TinyTableUI ()
  {
    super ();
  }


  public TinyTableUI ( JComponent table )
  {
    super ();
    this.table = ( JTable ) table;
  }


  public static ComponentUI createUI ( JComponent table )
  {
    return new TinyTableUI ( table );
  }


  protected void installDefaults ()
  {
    super.installDefaults ();
  }
}