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
jar cf tree.war -C tree WEB-INF 
echo "NB: you MUST copy the contents of tomcat-bin to \$tomcat_home/bin"
