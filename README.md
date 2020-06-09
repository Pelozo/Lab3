
# Lab3

Se realizará un sistema que permita a sus usuarios contratar un vuelo privado
desde un destino a otro. Este sistema tiene distintas acciones para sus usuarios.
Para contratar un avión dentro del sistema, un usuario deberá estar registrado.
Para completar este registro debe indicar los datos de: Nombre, Apellido, DNI y Edad .
La empresa AeroTaxi trabaja con una flota específica de aviones. Cuenta con tres
tipos diferentes de aviones: Gold , Silver y Bronze . La ficha técnica que comparten estos
aviones es:

 - Capacidad de combustible 
 - Costo por km (este valor ronda entre los $150 y $300) 
 - Capacidad máxima de pasajeros
 -  Velocidad máxima(en km/h)
 - Tipo de propulsión que tiene (motor a reacción, motor a hélice o
   motor de pistones).

Otros detalles importantes de la flota es que los aviones Gold y Silver tienen
servicio de catering y algunos aviones Gold tienen conexión continua de WIFI.
Para contratar un nuevo vuelo, el usuario deberá completar un cuestionario:
1. Inicialmente indicar la fecha deseada para realizar un vuelo.
2. Seleccionar el origen de su vuelo y posteriormente el destino.
3. El usuario debe indicar la cantidad de acompañantes que tendrá en el vuelo.
4. Ahora el usuario debe seleccionar un avión. El sistema se encargará de
mostrar los aviones disponibles para esa fecha y el usuario elige el deseado.
5. Por último, el sistema debe mostrar el costo total del vuelo y el usuario
deberá confirmar para generar el vuelo.

Es importante que el sistema realice algunas validaciones para su correcto
funcionamiento:

 - No se puede seleccionar la misma ciudad como origen y destino. 
 - El sistema debe tener en cuenta la cantidad de pasajeros por avión, en
   caso de que la cantidad de pasajeros es mayor que la capacidad de la
   flota disponible, el sistema debe mostrar un mensaje: “No tenemos
   aviones disponibles con esa capacidad de pasajeros”. 
 - Los aviones de la empresa SOLO realizan un vuelo por día, esto es
   por motivos técnicos. Si el avión tiene un vuelo programado para
   el día que    selecciona el usuario, no debe aparecer en la lista de
   aviones    disponibles.

Para calcular el total de un vuelo se deben tener en cuenta algunos aspectos:
AeroTaxi tiene una tarifa fija por cada pasajero de $3500 y una tarifa fija por el tipo de
avión ( Bronze : $3000, Silver : $4000 y Gold : $6000). De esta manera el cálculo total del
vuelo es:
(Cantidad de kms * Costo del km) + (cantidad de pasajeros * 3500) + (Tarifa del tipo
de avión)

AeroTaxi opera en las ciudades de: Buenos Aires, Córdoba, Santiago de Chile y
Montevideo . Las distancias de estas ciudades son:
Ciudades Distancia en Kms
BsAs – Cordoba 695
BsAs – Santiago 1400
BsAs – Montevideo 950
Córdoba – Montevideo 1190
Córdoba – Santiago 1050
Montevideo – Santiago 2100

Otra de las acciones que puede realizar el usuario es la de cancelar un vuelo . Un
vuelo sólo puede ser cancelado con más de un día de anticipación. En caso de que no
cumpla este requisito, el sistema debe retornar un mensaje:
“ No se puede cancelar un vuelo con menos de 24hs de anticipación ”.
Cuando un vuelo es cancelado, significa que es “ borrado ” del sistema.
El sistema realiza algunas salidas con información sobre vuelos y clientes. Se
solicita generar un método para listar todos los vuelos programados en una fecha dada y
generar otro método para listar todos los clientes indicando por cada uno:

 - Todos los datos personales. 
 - La categoría del mejor avión utilizado (Gold, Silver o Bronze). 
 - Total gastado de todos sus vuelos.

Algunos aspectos técnicos que se les pedirá para la aprobación
del trabajo:

 - El manejo de versiones (GIT) donde se vea el trabajo de cada miembro
   del grupo.   
 - Manejo de excepciones para controlar el correcto comportamiento del
   sistema.
 - Manejo de archivos “json” para almacenar toda la información del
   sistema
 - Código lo más claro posible, agregando comentarios de ser
   necesarios.
