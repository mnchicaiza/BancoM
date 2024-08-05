# PRUEBA TÉCNICA BANCO 

## Resumen 

La aplicación generada relaciona dos microservicios llamados CLIENTS y TRANSACTIONS, los cuales se pueden levantar e interactuar usando GRADLE o su IDE preferido. Se incluye también un archivo que permite lanzar la aplicación usando contenedores. 
* CLIENTS administra el ingreso, actualización y eliminación de datos del cliente. 
* TRANSACTIONS administra las cuentas y movimientos generados por los clientes. 

## Pasos para lanzar aplicación
*  Paso 1 - Configuración de la Base de Datos 

    Se requiere crear una base de datos (Postgres) de nombre **Bank**, la misma que luego de creada se debe configurar (host, puerto, usuario y contraseña) en el archivo **Aplication.yaml** de los dos microservicios.
    El archivo **BaseDatos.sql** contiene el esquema principal y las tablas necesarias para la aplicación.

    - Nota: Se han insertado dentro de la base un primer Cliente con una primera Cuenta y con su primera transacción.

* Paso 2.1 - Lanzamiento de Aplicación - Docker

    Abrir un terminal en la ubicación del archivo **docker-compose.yml** y  ejecutar el siguiente comando `docker-compose up --build`, este le permitirá levantar los dos microservicios e interactuar entre ellos.

* Paso 2.2 - Lanzamiento de Aplicación - Gradle 
    
    La aplicación esta configurada por defecto para ser lanzada mediante contenedores. Para este caso se requiere configurar previamente lo siguiente: 
    
    En el micro **transactions** ir al archivo **Aplication.yaml**, cambiar el url: `http://clients:9001/clients` por lo indicado a continuación.
    
    ```yaml
    transactions:
        services:
            clients:
                url:  `localhost:9001/clients`
    ```
   
    Una vez se haya realizado el cambio y cargado en un IDE los microservicios **clients** y **transactions** se deben construirlos insertando en las terminales el comando: 
     `gradle clean build`

* Paso 3 - Consumo de EndPoints

    Se ha preparado un archivo llamado **Bank.postman_collection.json** con el cual puede probar los endPoints de cada microservicio. Este archivo contiene JSON Body para ingreso de datos.  


---
>Magaly Chicaiza
(noemi-2305@hotmail.com)
