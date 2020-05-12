**Información útil**:
- **Wiki**: Situada en el menú izquierdo. Contiene tutoriales y desgloses de trabajos realizados.
- **Issues**: Situada en el menú izquierdo, contiene problemas, bugs y resoluciones de los mismos.


**Requisitos proyecto**:
- **Java (8+)**.
- **Maven**.

**Extras**:

- El jar compilado se sube a maven repository automáticmanete mediante TravisCI. Se puede descargar desde el siguiente enlace:
- El jar también se sube al repositorio bajo la carpeta /artifacts, con el número de versión, de manera automática con TravisCI.
- El jar tiene una versión automática 
- Las integraciones de TravisCI se han realizado en el fichero .travis.yml.
- Se han añadido entornos Maven, con configuraciones propias bajo src/main/resources/environment.{}.properties
- Por defecto se activara el entorno test
