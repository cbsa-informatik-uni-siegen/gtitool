package de.unisiegen.gtitool.ui.style;


import java.awt.Color;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.parser.AbstractScanner;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.ParserInterface;
import de.unisiegen.gtitool.core.parser.ParserSymbol;
import de.unisiegen.gtitool.core.parser.PrettyStyle;
import de.unisiegen.gtitool.core.parser.ScannerInterface;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ParserMultiException;
import de.unisiegen.gtitool.core.parser.exceptions.ParserWarningException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.ExceptionsChangedListener;


/**
 * An implementation of the {@link StyledDocument} interface to enable syntax
 * highlighting using the lexer.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledParserDocument extends DefaultStyledDocument
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2546142812930077554L;


  /**
   * Empty array of {@link ScannerException}s.
   */
  private static final ScannerException [] EMPTY_ARRAY = new ScannerException [ 0 ];


  /**
   * The {@link Logger} for this class.
   * 
   * @see Logger
   */
  private static final Logger logger = Logger
      .getLogger ( StyledParserDocument.class );


  /**
   * The {@link Parseable} for which this document was allocated.
   */
  private Parseable parseable;


  /**
   * The current exceptions from the {@link #parseable}s scanner.
   * 
   * @see #getExceptions()
   */
  private ScannerException exceptions[];


  /**
   * The attributes default style.
   */
  private SimpleAttributeSet normalSet = new SimpleAttributeSet ();


  /**
   * The attributes for the various {@link PrettyStyle}s.
   */
  private HashMap < PrettyStyle, SimpleAttributeSet > attributes = new HashMap < PrettyStyle, SimpleAttributeSet > ();


  /**
   * The list of {@link ExceptionsChangedListener}.
   */
  private ArrayList < ExceptionsChangedListener > exceptionsChangedListenerList = new ArrayList < ExceptionsChangedListener > ();


  /**
   * Allocates a new <code>StyledParserDocument</code> for the given
   * <code>pParseable</code>, where the <code>pParseable</code> is used to
   * determine the scanner for the documents content and thereby dictates the
   * syntax highlighting.
   * 
   * @param pParseable The {@link Parseable} for which to allocate a document.
   * @throws NullPointerException if the <code>pParseable</code> is
   *           <code>null</code>.
   */
  public StyledParserDocument ( Parseable pParseable )
  {
    if ( pParseable == null )
    {
      throw new NullPointerException ( "parseable is null" ); //$NON-NLS-1$
    }
    this.parseable = pParseable;
    // NormalSet
    StyleConstants.setForeground ( this.normalSet, Color.BLACK );
    StyleConstants.setBold ( this.normalSet, false );
    // SymbolSet
    SimpleAttributeSet symbolSet = new SimpleAttributeSet ();
    StyleConstants.setBold ( symbolSet, true );
    StyleConstants.setForeground ( symbolSet, PreferenceManager.getInstance ()
        .getColorItemSymbol ().getColor () );
    this.attributes.put ( PrettyStyle.SYMBOL, symbolSet );
  }


  /**
   * Adds the given {@link ExceptionsChangedListener}.
   * 
   * @param pListener The {@link ExceptionsChangedListener}.
   */
  public final synchronized void addExceptionsChangedListener (
      ExceptionsChangedListener pListener )
  {
    this.exceptionsChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the exceptions has changed.
   */
  public final void fireExceptionsChanged ()
  {
    for ( ExceptionsChangedListener current : this.exceptionsChangedListenerList )
    {
      current.exceptionsChanged ();
    }
  }


  /**
   * Returns the current {@link ScannerException}s that were detected while
   * trying to interpret the token stream.
   * 
   * @return The exceptions.
   */
  public final ScannerException [] getExceptions ()
  {
    return ( this.exceptions != null ) ? this.exceptions : EMPTY_ARRAY;
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractDocument#insertString(int, String, AttributeSet)
   */
  @Override
  public final void insertString ( int pOffset, String pString,
      AttributeSet pAttributeSet ) throws BadLocationException
  {
    super.insertString ( pOffset, pString, pAttributeSet );
    processChanged ();
  }


  /**
   * Processes the document content after a change.
   * 
   * @throws BadLocationException if the processing failed.
   */
  @SuppressWarnings (
  { "unused", "null" } )
  public final void processChanged () throws BadLocationException
  {
    setCharacterAttributes ( 0, getLength (), this.normalSet, true );
    ScannerException [] tmpExceptions = null;
    try
    {
      int offset = 0;
      String content = getText ( offset, getLength () );
      final ScannerInterface scanner = this.parseable
          .newScanner ( new StringReader ( content ) );
      final LinkedList < ParserSymbol > symbols = new LinkedList < ParserSymbol > ();
      while ( true )
      {
        try
        {
          ParserSymbol symbol = scanner.nextSymbol ();
          if ( symbol == null )
          {
            break;
          }
          symbols.add ( symbol );
          SimpleAttributeSet set = this.attributes.get ( scanner
              .getStyleBySymbol ( symbol ) );
          if ( set == null )
          {
            set = this.normalSet;
          }
          setCharacterAttributes ( offset + symbol.getLeft (), symbol
              .getRight ()
              - symbol.getLeft (), set, true );
        }
        catch ( ScannerException e )
        {
          int newOffset = offset + e.getRight ();
          content = content.substring ( e.getRight () );
          e = new ScannerException ( offset + e.getLeft (), offset
              + e.getRight (), e.getMessage (), e.getCause () );
          SimpleAttributeSet errorSet = new SimpleAttributeSet ();
          StyleConstants.setForeground ( errorSet, Color.RED );
          StyleConstants.setUnderline ( errorSet, true );
          errorSet.addAttribute ( "exception", e ); //$NON-NLS-1$
          setCharacterAttributes ( e.getLeft (), e.getRight () - e.getLeft (),
              errorSet, false );
          offset = newOffset;
          scanner.restart ( new StringReader ( content ) );
          if ( tmpExceptions == null )
          {
            tmpExceptions = new ScannerException []
            { e };
          }
          else
          {
            ScannerException [] newExceptions = new ScannerException [ tmpExceptions.length + 1 ];
            System.arraycopy ( tmpExceptions, 0, newExceptions, 0,
                tmpExceptions.length );
            newExceptions [ tmpExceptions.length ] = e;
            tmpExceptions = newExceptions;
          }
        }
      }
      if ( tmpExceptions == null )
      {
        ParserInterface parser = this.parseable
            .newParser ( new AbstractScanner ()
            {

              @Override
              public PrettyStyle getStyleBySymbolId ( int id )
              {
                return ( ( AbstractScanner ) scanner ).getStyleBySymbolId ( id );
              }


              public ParserSymbol nextSymbol () throws IOException,
                  ScannerException
              {
                return ( !symbols.isEmpty () ) ? symbols.poll () : null;
              }


              public void restart ( Reader reader )
              {
                throw new UnsupportedOperationException ();
              }
            } );
        try
        {
          parser.parse ();
        }
        catch ( ParserMultiException e )
        {
          String [] message = e.getMessages ();
          int [] startOffset = e.getParserStartOffset ();
          int [] endOffset = e.getParserEndOffset ();
          tmpExceptions = new ParserException [ startOffset.length ];
          for ( int i = 0 ; i < startOffset.length ; i++ )
          {
            tmpExceptions [ i ] = new ParserException ( startOffset [ i ],
                endOffset [ i ], message [ i ] );
            SimpleAttributeSet errorSet = new SimpleAttributeSet ();
            StyleConstants.setForeground ( errorSet, Color.RED );
            StyleConstants.setUnderline ( errorSet, true );
            errorSet.addAttribute ( "exception", tmpExceptions [ i ] ); //$NON-NLS-1$
            setCharacterAttributes ( startOffset [ i ], endOffset [ i ]
                - startOffset [ i ], errorSet, false );
          }
        }
        catch ( ParserWarningException e )
        {
          SimpleAttributeSet errorSet = new SimpleAttributeSet ();
          StyleConstants.setBackground ( errorSet, Color.YELLOW );
          errorSet.addAttribute ( "warning", e ); //$NON-NLS-1$
          if ( e.getLeft () < 0 && e.getRight () < 0 )
          {
            setCharacterAttributes ( getLength (), getLength (), errorSet,
                false );
          }
          else
          {
            setCharacterAttributes ( e.getLeft (),
                e.getRight () - e.getLeft (), errorSet, false );
          }
          if ( tmpExceptions == null )
          {
            tmpExceptions = new ScannerException []
            { new ParserWarningException ( e.getRight (), e.getRight (), e
                .getMessage (), e.getInsertText () ) };
          }
          else
          {
            ScannerException [] newExceptions = new ScannerException [ tmpExceptions.length + 1 ];
            System.arraycopy ( tmpExceptions, 0, newExceptions, 0,
                tmpExceptions.length );
            newExceptions [ tmpExceptions.length ] = new ParserWarningException (
                e.getRight (), e.getRight (), e.getMessage (), e
                    .getInsertText () );
            tmpExceptions = newExceptions;
          }
        }
        catch ( ParserException e )
        {
          SimpleAttributeSet errorSet = new SimpleAttributeSet ();
          StyleConstants.setForeground ( errorSet, Color.RED );
          StyleConstants.setUnderline ( errorSet, true );
          errorSet.addAttribute ( "exception", e ); //$NON-NLS-1$
          if ( e.getLeft () < 0 && e.getRight () < 0 )
          {
            setCharacterAttributes ( getLength (), getLength (), errorSet,
                false );
          }
          else
          {
            setCharacterAttributes ( e.getLeft (),
                e.getRight () - e.getLeft (), errorSet, false );
          }
          if ( tmpExceptions == null )
          {
            tmpExceptions = new ScannerException []
            { e };
          }
          else
          {
            ScannerException [] newExceptions = new ScannerException [ tmpExceptions.length + 1 ];
            System.arraycopy ( tmpExceptions, 0, newExceptions, 0,
                tmpExceptions.length );
            newExceptions [ tmpExceptions.length ] = e;
            tmpExceptions = newExceptions;
          }
        }
      }
    }
    catch ( Exception exc )
    {
      logger.error ( "failed to process changes", exc ); //$NON-NLS-1$
    }
    if ( this.exceptions != tmpExceptions )
    {
      ScannerException [] oldExceptions = this.exceptions;
      this.exceptions = tmpExceptions;
      fireExceptionsChanged ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Document#remove(int, int)
   */
  @Override
  public final void remove ( int pOffset, int pLength )
      throws BadLocationException
  {
    super.remove ( pOffset, pLength );
    processChanged ();
  }


  /**
   * Removes the given {@link ExceptionsChangedListener}.
   * 
   * @param pListener The {@link ExceptionsChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeExceptionsChangedListener (
      ExceptionsChangedListener pListener )
  {
    return this.exceptionsChangedListenerList.remove ( pListener );
  }
}
