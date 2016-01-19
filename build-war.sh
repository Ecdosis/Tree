#!/bin/bash
if [ ! -d tree ]; then
  mkdir tree
  if [ $? -ne 0 ] ; then
    echo "couldn't create tree directory"
    exit
  fi
fi
if [ ! -d tree/WEB-INF ]; then
  mkdir tree/WEB-INF
  if [ $? -ne 0 ] ; then
    echo "couldn't create tree/WEB-INF directory"
    exit
  fi
fi
if [ ! -d tree/WEB-INF/lib ]; then
  mkdir tree/WEB-INF/lib
  if [ $? -ne 0 ] ; then
    echo "couldn't create tree/WEB-INF/lib directory"
    exit
  fi
fi
rm -f tree/WEB-INF/lib/*.jar
cp dist/Tree.jar tree/WEB-INF/lib/
cp web.xml tree/WEB-INF/
cp font* tree/WEB-INF
cp lib/commons-beanutils-1.8.3.jar tree/WEB-INF/lib/
cp lib/commons-io-1.3.1.jar tree/WEB-INF/lib/
cp lib/commons-logging-1.1.1.jar tree/WEB-INF/lib/
cp lib/ghost4j-1.0.0.jar tree/WEB-INF/lib/
cp lib/itext-2.1.7.jar tree/WEB-INF/lib/
cp lib/xmlgraphics-commons-1.4.jar tree/WEB-INF/lib/
cp lib/jna-3.3.0.jar tree/WEB-INF/lib/
cp lib/log4j-1.2.15.jar tree/WEB-INF/lib/
cp missing.jpg tree/WEB-INF/
jar cf tree.war -C tree WEB-INF
echo "NB: you MUST copy the contents of tomcat-bin to \$tomcat_home/bin"
