# Ingeniería de software para aplicaciones móviles

## Integrantes
|Integrante|Correo|
|---|---|
|Angie Roa|a.roap@uniandes.edu.co|
|Esteban Heredia|e.herediar@uniandes.edu.co|
|Oscar Torres|o.torress@uniandes.edu.co|
|Daniela Suárez|cd.suarez@uniandes.edu.co|

## Cómo construir la aplicación localmente

### Prerequisitos
Es importante te cuente localmente con estas herramientas:

|Prerequisito|Descripción|Versión|
|---|---|---|
|[Android Studio](https://developer.android.com/studio)|Permite construir la aplicación y probar si se requiere con un emulador de celular.|Meerkat 2024.3.1|
|[Docker](https://www.docker.com/)|Permite levantar localmente el ambiente backend, al cual se comunica la aplicación móvil|4.40|


### Paso a paso
    DISCLAIMER
    La app tiene asociado un backend desplegado en la nube de AWS, 
    sin embargo si el backend no funciona o esta caído por alguna 
    razón se recomienda seguir los pasos de despliegue de backend 
    para que el proyecto funcione correctamente

#### Desplegar Backend API
1. Clone el repositorio ubicado [aquí](https://github.com/TheSoftwareDesignLab/BackVynils).
2. Ingrese a la carpeta raiz en donde el repositorio quedo clonado (asegurese de que docker este corriendo) y ejecute `docker-compose up --build`. Esto construira y pondra en funcionamiento los contenedores de la API y la base de datos.
3. Cuando docker finalice, pruebe en algún cliente (puede ser un navegador o Postman) la url `http://localhost:3000/albums` y deberá obtener respuesta.

#### Desplegar APP móvil
1. Clone el repositorio en su maquina local, esto lo puede hacer con a través de SSH con `git clone git@github.com:angietroa/MISW4203-202512.git` o a través de HTTPS con `git clone https://github.com/angietroa/MISW4203-202512.git`.

2. En Android Studio agregue el proyecto clonado. 

3. En al archivo `gradle.properties` ubicado en la raiz del proyecto, existe la variable/propiedad `backendBaseUrl` allí deberá especificar la URL en la que esta desplegado el Backend API (explicado en la sección anterior). A continuación, algunos aspectos a tener en cuenta:

   * Si la API esta desplegada localmente asegurese de **no usar** una dirección como `http://localhost:3000/` o `http://127.0.0.0:3000/`, ya que el dispositivo movil al estar en un emulador o dispositivo fisico no reconoce la API como parte de su red local, y podrían fallar las consultas. Lo más recomendable es usar la IP de su dispositivo la cuál puede buscar en una consola digitando `ifconfig` o usar la dirección `http://10.0.2.2:3000/`.
   * Al final de la URL no olvide poner un slash final `/`, ya que sin esto las consultas no funcionaran. Por ejemplo `http://10.0.2.2:3000/`.

4. Valide que tenga creado en la raiz del proyecto un archivo llamado `local.properties`, este deberá contener la dirección en dónde se encuentra el sdk de Android. Por ejemplo, el archivo deberá verse así:

```
sdk.dir=/Users/danisuarez/Library/Android/sdk
```

*Nota: En caso de que no tenga el archivo, puede duplicar el archivo `local.properties.example`, renombrarlo a `local.properties` y remplazar la dirección de el SDK en su maquina.*

5. Compile el proyecto dirigiendose al menu *Build* y luego el submenu *Compile All Sources*; empezará a descargar las dependencias y hacer una construcción inicial de la aplicación. Si quiere monitorear cuando acaba la construcción puede hacerlo siguiendo estos [pasos](https://developer.android.com/studio/run). Al final debe aparecer en la consola de construcción algo como esto:

```
  BUILD SUCCESSFUL in 10s
```

6. Sincronize el el proyecto con los archivos gradle, yendo al menu *Files* y submenu *Sync Project with Gradle Files*, ejecute esta opción, el cuál abrirá una consola y mostrará al final algo como:

```
  BUILD SUCCESSFUL in 897ms
```

6. Asegurese de tener un dispositivo virtual o un dispositivo fisico en el cual pueda probar la aplicación.
    * Para crear y administrar un dispositivo virtual, puede seguir [esta guia](https://developer.android.com/studio/run/managing-avds).
    * Para correr la aplicación en un dispositivo local siga [esta guia](https://developer.android.com/studio/run/device).

