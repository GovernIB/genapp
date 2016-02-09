
call wsconsume -k http://localhost:8080/${name}/ws/v1/${fullname}HelloWorld?wsdl -s src/main/java -n -p ${package}.ws.api.v1

call wsconsume -k http://localhost:8080/${name}/ws/v1/${fullname}HelloWorldWithSecurity?wsdl -s src/main/java -n -p ${package}.ws.api.v1

REM call wsconsume -k http://localhost:8080/portafib/ws/v1/PortaFIBUsuariAplicacio?wsdl -s src/main/java -n -p es.caib.portafib.ws.api.v1

