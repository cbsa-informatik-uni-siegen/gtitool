#!/bin/sh
#
# $Id:gtitool.sh 57 2007-10-22 23:43:33Z fehler $
#
# Startscript for the GTITool application on Linux/Unix.
#

# Check if a JRE is available
if ! type java >/dev/null 2>&1; then
	cat >&2 <<EOF
No suitable JRE found. Please download and install a JRE and make sure the
java binary is in the PATH.
EOF
	exit 1
fi

# determine the real path of the shell script (somewhat hacky)
if echo "$0" | grep "^/" > /dev/null; then
	REALPATH=`dirname $0`;
else
	REALPATH=`pwd`
fi

# determine the $XDG_DATA_HOME directory
test x"$XDG_DATA_HOME" != x"" || export XDG_DATA_HOME="$HOME/.local/share"

# install the mime types
mkdir -p "$XDG_DATA_HOME/mime/packages"
cat > "$XDG_DATA_HOME/mime/packages/gtitool.xml" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<mime-info xmlns="http://www.freedesktop.org/standards/shared-mime-info">
  <mime-type type="text/x-gtitool">
    <sub-class-of type="text/plain" />
    <comment>GTITool source code</comment>
    <comment xml:lang="de">GTITool Quelltext</comment>
    <glob pattern="*.[Dd][Ff][Aa]" />
    <glob pattern="*.[Nn][Ff][Aa]" />
    <glob pattern="*.[Ee][Nn][Ff][Aa]" />
  </mime-type>
</mime-info>
EOF
update-mime-database "$XDG_DATA_HOME/mime" > /dev/null

# install the .desktop file
mkdir -p "$XDG_DATA_HOME/applications"
cat > "$XDG_DATA_HOME/applications/gtitool.desktop" <<EOF
[Desktop Entry]
Encoding=UTF-8
Version=1.0
Type=Application
NoDisplay=false
Name=GTITool @de.unisiegen.gtitool.ui.version@
Comment=GTI education tool
Comment[de]=GTI Lernwerkzeug
TryExec="$REALPATH/gtitool.sh"
Exec="$REALPATH/gtitool.sh" %F
MimeType=text/x-gtitool
StartupNotify=false
Categories=Education;Development;Java;
EOF
update-desktop-database "$XDG_DATA_HOME/applications" > /dev/null

# execute the application
exec java -jar "$REALPATH/de.unisiegen.gtitool.ui-@gtitool.version@.jar" "$@"
