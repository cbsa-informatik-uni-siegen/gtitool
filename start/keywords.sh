#!/bin/sh
#
# $Id$
#
# Copyright (c) 2008 Christian Fehler
#

# core
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.core/source/ -name '*.java'`
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.core/source-impl/ -name '*.java'`
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.core/test/ -name '*.java'`

# ui
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.ui/source/ -name '*.java'`
svn propset svn:keywords 'Author Date Id Rev' `find ../de.unisiegen.gtitool.ui/test/ -name '*.java'`

# javacup
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.javacup/source/ -name '*.java'`

# jflex
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.jflex/source/ -name '*.java'`

# jgraph
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.jgraph/source/ -name '*.java'`

# logger
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.logger/source/ -name '*.java'`

# start
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.start/source/ -name '*.java'`
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.start/test/ -name '*.java'`

# tinylaf
svn propset svn:keywords 'Author Date Id Rev' `find ../gtitool.tinylaf/source/ -name '*.java'`
