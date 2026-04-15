# Proyecto-Progra-RA8

## *PLANTEAMIENTO*

El IES Clara del Rey necesita desarrollar una aplicación para la gestión de exámenes.

Las necesidades a cubrir son las siguientes:

Por un lado se necesita gestionar un banco de preguntas de examen. Este estará
respaldado por una base de datos en la que se almacenarán preguntas de examen
generadas por los distintos profesores. Interesará poder dar de alta nuevas preguntas,
modificar preguntas ya existentes, eliminar preguntas y realizar consultas básicas
desde la aplicación.

De cada pregunta se quiere guardar código único identificativo, autor, curso, grupo,
módulo, RA, tema, enunciado, fecha de creación, palabras clave que la identifiquen
(para facilitar consultas), Hay dos tipos de preguntas: Tipo test y de desarrollo. De las
preguntas tipo test se quieren guardar las respuestas propuestas (4 por pregunta), de
las cuales sólo una será correcta. De las de desarrollo se puede guardar un texto que
describa la respuesta.

Como se ha señalado, desde las interfaces generadas con nuestro programa se
tendrán que poder dar de alta nuevas preguntas en la base de datos, eliminar las que
ya no se necesiten, modificar las existentes y realizar consultas sencillas por palabras
clave o cualquiera de los atributos de las preguntas que se consideren útiles (fecha de
creación, autor, curso, módulo,…).

Por otra parte, se quiere automatizar la generación de exámenes. Nuestra aplicación
proporcionará la interfaz necesaria para recoger los parámetros requeridos: número de
preguntas, su tipo (test o no), RA, módulo, tema,…. La aplicación podrá generar
exámenes compuestos íntegramente por preguntas escogidas al azar, preguntas
escogidas o una mezcla de ambas. Sería interesante obtener el resultado en algún
formato listo para impresión.

Por último, se necesitaría que no cualquiera pudiera acceder a la aplicación, por lo que
hay que establecer una política de contraseñas, que se asignan a los distintos
profesores, de forma que cuando alguien realiza una operación sobre alguna pregunta
esto quede registrado. Las contraseñas se deben guardar cifradas.
