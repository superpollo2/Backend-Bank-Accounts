# Proyecto Banck Account - prueba técnica Nivel 1 Bancolombia ![Project Version](https://img.shields.io/badge/tag-v1.0-pink) [![GitHub commits](https://badgen.net/github/commits/superpollo2/Backend-associate-cryptocurrency)](https://GitHub.com/superpollo2/Backend-associate-cryptocurrency/commit/)
## Desarrolladora - Laura Vanesa Tascón Cataño - Ing se sistemas

![Java Version](https://img.shields.io/badge/java-21-blue.svg)
![Springboot Version](https://img.shields.io/badge/springboot-3.4.3-orange)
![Jaccoco Version](https://img.shields.io/badge/jacoco-1.15.0-pink)
![Clean Architecture Version](https://img.shields.io/badge/cleanarchitecture-3.20.10-blue)
![Pitest Version](https://img.shields.io/badge/pitest-1.15.0-blue)

> [!NOTE]
> Este es un proyecto creado para una prueba técnica

## Resumen
El sistema de gestión de cuentas bancarias permite administrar y operar cuentas de manera segura, garantizando 
la integridad de las transacciones. Cada cuenta bancaria tiene un saldo asociado y solo puede realizar operaciones si dispone de 
fondos suficientes. Para ello, se ha diseñado una API REST que expone endpoints para la creación de cuentas, 
consulta de saldo, depósitos, retiros y consulta del historial de transacciones y la creación de clientes asiciados.

La solución sigue una arquitectura limpia, asegurando la separación de responsabilidades y la independencia de 
los detalles de infraestructura. Esto permite mantener un código modular, escalable y fácil de probar, garantizando 
que la lógica de negocio se mantenga pura y desacoplada de la tecnología utilizada.

## Objetivos del proyecto ✨
Desarrollar una API REST que permita la gestión eficiente y segura de cuentas bancarias, garantizando la integridad de las transacciones y el acceso controlado a la información.

Los objetivos específicos de la API incluyen:

✅ Administrar cuentas bancarias, permitiendo su creación y consulta. 

✅ Consultar el saldo disponible de una cuenta en cualquier momento.

✅ Realizar depósitos y retiros, asegurando que los retiros solo se procesen si hay fondos suficientes.

✅ Registrar y consultar el historial de transacciones de cada cuenta.

✅ Proporcionar respuestas claras y estandarizadas, manejando errores de forma adecuada.

✅ Exponer endpoints REST con una estructura clara y siguiendo buenas prácticas de diseño de API.


## Consideraciones en el Análisis y Diseño del Proyecto
Durante la fase de análisis y diseño del proyecto, se establecieron una serie de reglas y lineamientos para garantizar la integridad y seguridad de las operaciones bancarias. A continuación, se detallan los principales aspectos considerados:

🔹 Control de Saldos y Transacciones:
Para evitar inconsistencias financieras, se estableció que una cuenta no puede realizar transferencias ni retiros si no cuenta con saldo suficiente. Esto asegura que las operaciones reflejen con precisión la disponibilidad de fondos en cada cuenta.

🔹 Validación de Existencia de Cuentas:
No se permite realizar una transferencia si alguna de las cuentas involucradas no existe. Esta validación es fundamental para evitar transacciones hacia cuentas inexistentes o erróneas.

🔹 Unicidad de Cuentas y Relación con Usuarios:
Un usuario puede poseer múltiples cuentas de ahorro o corrientes, sin embargo, cada número de cuenta debe ser único en el sistema. Este principio garantiza la identificación inequívoca de cada cuenta bancaria.

🔹 Identidad Única de Usuarios:
Cada usuario debe existir una única vez en el sistema, evitando duplicados y asegurando la correcta asociación de sus cuentas bancarias.

🔹 Vinculación Obligatoria entre Cuentas y Usuarios:
Toda cuenta debe estar asociada a un usuario. Esto permite un mejor control sobre las operaciones y facilita la administración de los clientes del banco.

🔹 Restricción en Montos de Transacción:
No se permite realizar ninguna transacción donde el monto sea cero. Esto previene errores en la operativa y mantiene la coherencia de las operaciones financieras.

🔹 Manejo Eficiente del Historial de Transacciones:
Con el fin de optimizar el rendimiento del sistema y permitir la consulta eficiente del historial de transacciones a largo plazo, se implementó un sistema de paginación. Esto permite manejar grandes volúmenes de datos sin afectar el desempeño de la aplicación.

🔹 Estructura del Historial de Transacciones:
En el registro de historial de transacciones se establecieron los campos originAccount y destinationAccount. La única excepción permitida es que destinationAccount pueda ser null, lo cual aplica en ciertos tipos de transacciones donde no es necesario especificar un destino.

Estas consideraciones fueron clave en el diseño del sistema, asegurando una operación segura, eficiente y alineada con las mejores prácticas del sector bancario.


## Arquitectura
Este proyecto se costruye usando arquitectura limpia mediante el patron o arquitectura
creada por Bancolombia llamado Scaffold, este modelo de arquitectura es un tipo especifico
de arquitectura hexagonal dado que delimita las funciones y responsabilidades de forma clara 
y eficiente.

Siguiendo los principios de arquitectura limpia, el código se estructura en capas bien definidas:

🔹 Dominio: Contiene las reglas de negocio puras.

🔹 Aplicación: Orquesta los casos de uso sin depender de detalles externos.

🔹 Infraestructura: Implementa adaptadores para interactuar con bases de datos, controladores y servicios externos.

![img_1.png](utils/diagrama_arquitectura.png)
**documentación** https://bancolombia.github.io/scaffold-clean-architecture/docs/intro

## Diagram Entidad-Relación (DB)
![img.png](utils/diagrama_entidad_relacion.png)

## 🚨 Códigos de errores 🚨
La API utiliza códigos de error propios para el manejo de excepciones con el objetivo de proporcionar respuestas 
más claras y estructuradas. Aunque se emplean códigos de estado HTTP estándar, los códigos internos permiten identificar con precisión la causa del error, facilitando la depuración y la interpretación por parte de los clientes de la API. Esto asegura una gestión más eficiente de 
los errores y una mejor experiencia para los desarrolladores que integren el servicio.

| Código de Error | Descripción del Error | HTTP Status |
|---------------|----------------------|-------------|
| BC-B000 | ERROR QUERY, DOES NOT EXIST OR IS NOT AVAILABLE | 404 Not Found |
| BC-B01 | FORMAT ERROR | 400 Bad Request |
| BC-B02 | REQUIRED FIELD ERROR | 400 Bad Request |
| BC-B03 | INSUFFICIENT FUNDS IN ACCOUNT | 400 Bad Request |
| BR-I00 | INTERNAL API ERROR | 500 Internal Server Error |
| BR-002 | JSON FORMAT ERROR | 400 Bad Request |


## ⚠️ Matriz de Errores ⚠️
La Matriz de Errores define los distintos códigos de error que puede devolver la API, 
proporcionando una referencia clara sobre el motivo de cada fallo. Cada error incluye un código único, 
un título descriptivo, un detalle sobre la causa del error y una explicación del porqué ocurre. 
Esto permite una gestión eficiente de excepciones y facilita la depuración e integración con otros sistemas.

| Código de Error | HTTP Status (code) | Título | Detalle | Descripción / Por qué sucede |
|---------------|------------------|--------|---------|-------------------------------|
| BC-B03 | 400 Bad Request | INSUFFICIENT FUNDS | Insufficient funds in account {accountNumber} | Ocurre cuando se intenta realizar una transacción, pero no hay saldo suficiente en la cuenta. |
| BC-B000 | 404 Not Found | ACCOUNT NOT FOUND | Account does not exist | Se busca una cuenta por número de cuenta, pero esta no existe. |
| BC-B000 | 404 Not Found | DESTINATION ACCOUNT NOT FOUND | Destination account does not exist | La cuenta a la que se quiere enviar dinero no existe. |
| BC-B000 | 404 Not Found | ORIGIN ACCOUNT NOT FOUND | Origin account does not exist | La cuenta desde la que se quiere enviar dinero no existe. |
| BC-B000 | 409 Conflict | ACCOUNT ALREADY EXISTS | Account already exists | Se intenta crear una cuenta que ya existe. |
| BC-B000 | 404 Not Found | USER NOT FOUND | User not found | Se intenta ligar una cuenta a un usuario que no existe. |
| BC-B000 | 409 Conflict | USER ALREADY EXISTS | User already exists | Se intenta registrar un usuario que ya está registrado. |
| BC-B01 | 400 Bad Request | INVALID PAGE SIZE | Page size must be greater than zero | Ocurre cuando el tamaño de la página es menor o igual a cero. |
| BC-B01 | 400 Bad Request | INVALID PAGE INDEX | Page index must be zero or greater | Ocurre cuando el índice de la página es menor que cero. |
| BC-B01 | 400 Bad Request | INSUFFICIENT BALANCE | Initial balance cannot be less than {MINIMUM_BALANCE} | No es posible realizar una transacción debido a un saldo insuficiente en la cuenta. |


## Swagger 👻
La documentación de la API está disponible en formato Swagger. Puedes encontrar de manera online en:
https://editor.swagger.io/
La documentación de la API está disponible en formato Swagger. Puedes encontrar el archivo YAML en la siguiente ubicación:
[Ver Swagger YAML](utils/swagger.yaml)

## Base de Datos H2 📃
Para ejecutar el proyecto, no se requiere configuración adicional de base de datos. Basta con clonar el repositorio, abrirlo en un entorno como IntelliJ IDEA y ejecutarlo.

La API utiliza H2 en memoria, lo que significa que la base de datos se carga en caché durante la ejecución del proyecto. Si bien esta solución tiene limitaciones en persistencia y concurrencia, ofrece una ventaja clave en este tipo de proyectos (pruebas técnicas), ya que permite a los evaluadores ejecutar y probar la API fácilmente, sin necesidad de configurar un entorno de base de datos externo.

Es importante tener en cuenta que, debido a la naturaleza volátil de H2 en memoria, todos los datos se perderán cada vez que el proyecto se vuelva a ejecutar o reconstruir. 🚀


## Colección Endpoints 📢
En este apartado, encontrarás una colección de Postman que incluye todos los endpoints de la API, permitiéndote probar fácilmente sus funcionalidades sin necesidad de configurar manualmente cada petición.

La colección contiene ejemplos predefinidos con los parámetros necesarios para interactuar con los servicios de gestión de cuentas bancarias, incluyendo la creación de cuentas, consulta de saldo, depósitos, retiros y revisión del historial de transacciones.

Para utilizarla, simplemente importa la colección en Postman y asegúrate de configurar la URL base del servicio según tu entorno de ejecución.
Puedes encontrar la colección de **Postman** con todos los endpoints de la API en el siguiente archivo:

📂 [`utils/Bank Account API REST.postman_collection.json`](utils/Bank%20Account%20API%20REST.postman_collection.json) 

## Cobertura de pruebas unitarias
La cobertura de pruebas unitarias y el análisis de código estático (sintaxis, calidad y seguridad) se realizaron con SonarCloud, una herramienta dinámica que proporciona métricas detalladas en tiempo real. SonarCloud se integró con GitHub mediante GitHub Actions, permitiendo un monitoreo continuo de la calidad del código en cada cambio del repositorio.
Puedes encontrar el repo en sonarcloud en el siguiente link
https://sonarcloud.io/project/overview?id=superpollo2_Backend-Bank-Accounts
