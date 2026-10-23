# messengerJava
Proyecto de mensajería en Java desarrollado para la asignatura de Programación en UPSA, aplicando conceptos de Programación Orientada a Objetos 

## Descripción del Proyecto

El objetivo del proyecto es desarrollar una aplicación de mensajería que permita enviar mensajes entre diferentes usuarios y crear grupos

Desde el inicio se pueden identificar fácilmente las entidades básicas del proyecto: User, Mensaje y Chat. A lo largo del desarrollo probablemente identificaremos y crearemos algunas entidades más.  
Además, estas entidades permiten ampliar fácilmente la funcionalidad del proyecto, ya que podemos añadir en cualquier momento nuevas funciones o características para cada una de ellas

cada entidad con más de detalle

### [USER]
La entidad User puede contener información básica del usuario, por ejemplo id, nombre, estado y fecha de registro. También se le pueden añadir funciones como cambiar el nombre, bloquear a otro usuario o consultar sus chats


### [MENSAJE]
La entidad Mensaje puede contener el texto del mensaje y diferentes metadatos, como id, fecha/hora, usuario que lo ha enviado y chat al que pertenece. También podemos añadir características como saber si el mensaje ha sido leído, editado o eliminado


### [CHAT]
El Chat contendrá el id, los usuarios participantes y la lista de mensajes. En el caso de los grupos también se puede guardar el nombre del grupo o los administradores. Entre sus funciones pueden estar enviar mensajes, añadir o eliminar usuarios y mostrar el historial de la conversación
Hemos elegido este proyecto en primer lugar por su flexibilidad, ya que una aplicación de mensajería puede desarrollarse de muchas formas diferentes. Podemos empezar con una versión muy sencilla y educativa, e ir ampliándola poco a poco hasta convertirla en un proyecto personal más completo, profundizando en conceptos y herramientas más avanzadas

## Casos de uso
### Usuarios

  - [ ] Crear usuario

  - [ ] Iniciar sesión como usuario

  - [ ] Cerrar sesión

  - [ ] Eliminar usuario

  - [ ] Modificar el nombre del usuario

  - [ ] Mostrar los usuarios registrados

  - [ ] Bloquear y desbloquear usuarios

### Chats y grupos

  - [ ] Crear chat para dos usuarios

  - [ ] Crear grupo

- [ ] Eliminar chat o grupo

- [ ] Añadir usuarios a un grupo

- [ ] Eliminar usuarios de un grupo

- [ ] Modificar el nombre de un chat o grupo

- [ ] Mostrar la lista de chats de un usuario

- [ ] Crear administradores de grupo

### Mensajes

- [ ] Enviar mensajes

- [ ] Editar mensajes

- [ ] Eliminar mensajes

- [ ] Responder a mensajes

- [ ] Filtrar mensajes por usuario

- [ ] Filtrar mensajes por fecha

## Requisitos No Funcionales
- Rendimiento - la parte más importante de cualquier mensajeria
- Usabilidad - como una aplicación de mensajería no está dirigida a un tipo específico de usuario, es importante crear una interfaz clara y fácil de entender para cualquier persona
- Seguridad - una aplicación de mensajería puede manejar mucha información personal, por lo que es importante proteger los datos

