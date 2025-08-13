 esta es un app de un foro donde se puede>

 1 registra usuarios
 2 inciar seccion
 3 crear topicos
 4 actualizar estos tpicos
 5 listar todos los topicos 
 6 Eliminar topicos

 1 para registrase la app recibe un json con las clave valor username, email, password
 si es exitoso el registro te devolvera registro exitosamente y con eso quedara el usuario registrado en la base de datos

 2 para iniciar seccion se deve ingresar los tres campos username, email, password y nos devolvera el token para usar en la sigientes request 

 3 para crear un topico se deven entegar las clave valor title y content y usar el token para guardar este topico

 4 para actualizar un topico se deve ingresar el id del topico que desea actuslizar y en viart lod campos del topico como los desea modificar

 5 para listar los topicos el acceso es con token y traera todos los topicos con su id y autor

 6 para el eliminar un topico se deve iniciar seccion con el usuario autor del topico y con el token generado y el id del topico se puede eliminar el mismo
