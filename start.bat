set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
mvn jetty:run -Denv=dev. -Dfile.encoding=UTF-8