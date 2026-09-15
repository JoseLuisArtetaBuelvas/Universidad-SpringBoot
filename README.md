# 🎓 Universidad — Spring Boot MVC

Aplicación web desarrollada en **Spring Boot MVC + Thymeleaf + Spring Data JPA + PostgreSQL**, correspondiente al **Ejercicio 2 (Universidad)** de la actividad "Spring Boot MVC con Thymeleaf: desarrollo web basado en framework" — Desarrollo Web, Unidad 2.

Gestiona dos entidades — **Usuario** (autenticación y roles) y **Universidad** (nombre, categoría, web, rector, email, acceso, teléfono, ciudad, número de carreras, número de sedes) — con CRUD completo, reportes parametrizados, autenticación con Spring Security y recuperación de clave por correo electrónico.

> La aplicación es una app MVC tradicional: los controladores retornan vistas Thymeleaf renderizadas en el servidor, **no** es una API REST.

---

## 📋 Tabla de contenido

- [Requisitos](#-requisitos)
- [Configuración de la base de datos](#️-configuración-de-la-base-de-datos)
- [Variables de entorno](#️-variables-de-entorno)
- [Cómo ejecutar la aplicación](#-cómo-ejecutar-la-aplicación)
- [Despliegue con Docker](#-despliegue-con-docker)
- [Credenciales de prueba](#-credenciales-de-prueba)
- [Funcionalidades](#-funcionalidades)
- [Estructura del proyecto](#️-estructura-del-proyecto)
- [Despliegue](#-despliegue)

---

## 📋 Requisitos

* **Java Development Kit (JDK):** versión 21 LTS.
* **Gestor de construcción:** [Apache Maven 3.8+](https://maven.apache.org/) (el proyecto incluye también los wrappers `mvnw` / `mvnw.cmd`, no es necesario tener Maven instalado).
* **Motor de base de datos:** [PostgreSQL 14+](https://www.postgresql.org/) (instalación local) o un servicio en la nube ([Supabase](https://supabase.com/), [Neon.tech](https://neon.tech/), Render PostgreSQL, AWS RDS, etc.).
* **Cuenta gratuita en [Brevo](https://www.brevo.com)** para el envío de correos de recuperación de clave (API HTTP, no SMTP).
* *(Opcional)* **Docker**, si prefieres ejecutar la app en contenedor en vez de con Maven directo.

---

## 🗄️ Configuración de la base de datos

El proyecto usa PostgreSQL. Toda la estructura y los datos de prueba están versionados en [`src/main/resources/db/`](./src/main/resources/db/).

### 1. Parámetros por defecto de conexión

Definidos en `src/main/resources/application.properties` (sobrescribibles por variable de entorno, ver más abajo):

| Parámetro | Valor por defecto (local) |
| :--- | :--- |
| **Host / Puerto** | `localhost:5432` |
| **Base de datos** | `7502523005_2_Universidad` |
| **Usuario** | `postgres` |
| **Contraseña** | `admin` |

### 2. Creación y carga de datos

#### Opción A: PostgreSQL local (`psql`)

```bash
# 1. Crear la base de datos
createdb -U postgres 7502523005_2_Universidad

# 2. Ejecutar el script consolidado (DDL + datos de prueba)
psql -U postgres -d 7502523005_2_Universidad -f src/main/resources/db/init_db.sql
```

*(También puedes ejecutar por separado [`01_crear_tablas.sql`](./src/main/resources/db/01_crear_tablas.sql) y luego [`02_datos_iniciales.sql`](./src/main/resources/db/02_datos_iniciales.sql) — ver [`db/README.md`](./src/main/resources/db/README.md)).*

#### Opción B: Servicio en la nube (Supabase, Neon, Render)

1. Crea la base de datos en el proveedor elegido.
2. Entra al **SQL Editor** (o conéctate con `psql` a la URL que te den).
3. Pega el contenido completo de [`init_db.sql`](./src/main/resources/db/init_db.sql) y ejecútalo.
4. Configura `DB_URL`, `DB_USER`, `DB_PASSWORD` como variables de entorno (ver siguiente sección) apuntando a esa base de datos.

> Nota: si corres la app con `spring.jpa.hibernate.ddl-auto=update` (configuración por defecto), Hibernate también crea las tablas automáticamente al arrancar — pero igual necesitas cargar el usuario administrador inicial para poder loguearte, así que ejecuta al menos `02_datos_iniciales.sql` una vez.

---

## ⚙️ Variables de entorno

Copia `.env.example` como `.env` en la raíz del proyecto:

```bash
cp .env.example .env
```

El archivo `.env` **nunca** se sube al repositorio (está en `.gitignore`). En un despliegue (Render, Docker, etc.) estas mismas variables se configuran directamente en el panel del servicio — nunca se escriben en el código fuente.

| Variable | Descripción | Valor por defecto (fallback local) |
| :--- | :--- | :--- |
| `DB_URL` | URL JDBC completa de conexión | `jdbc:postgresql://localhost:5432/7502523005_2_Universidad` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `admin` |
| `PORT` | Puerto en el que escucha la app | `8085` (Render lo asigna automáticamente) |
| `BREVO_API_KEY` | API key de tu cuenta Brevo (Settings → SMTP & API → API Keys) | *(vacío — obligatorio para que salgan los correos)* |
| `BREVO_SENDER_EMAIL` | Correo remitente, ya verificado en Brevo | `josex.developer@gmail.com` |
| `APP_BASE_URL` | URL pública de la app, usada para armar el enlace de recuperación de clave | `http://localhost:8085` |

Todas tienen valor por defecto para desarrollo local **excepto `BREVO_API_KEY`**, que si no se configura simplemente hace que el envío de correos falle silenciosamente (queda registrado en el log), sin romper el resto de la aplicación.

---

## 🚀 Cómo ejecutar la aplicación

### Opción 1: Con Maven (desarrollo local)

```bash
git clone <url-del-repositorio>
cd "Universidad - Spring Boot"
cp .env.example .env   # completa BREVO_API_KEY como mínimo
./mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8085`. El login está en `http://localhost:8085/login`.

### Opción 2: Desde IntelliJ IDEA

1. Abre la carpeta del proyecto en IntelliJ.
2. Verifica el JDK 21 en `File > Project Structure > Project > SDK`.
3. En el Run Configuration de Spring Boot, agrega las variables de entorno (`Environment variables`) en vez de depender del `.env` — evita problemas si el directorio de trabajo del IDE no coincide con la raíz del proyecto.
4. Ejecuta `UniversidadSpringBootApplication`.

---

## 🐳 Despliegue con Docker

El proyecto incluye un [`Dockerfile`](./Dockerfile) multi-etapa que compila con Maven y empaqueta un `.jar` ejecutable sobre una imagen liviana de JRE 21 (Spring Boot trae su propio servidor Tomcat embebido, no necesita un Tomcat externo).

1. **Construir la imagen:**
   ```bash
   docker build -t universidad-springboot .
   ```
2. **Ejecutar el contenedor**, pasando las variables de entorno (base de datos remota + Brevo):
   ```bash
   docker run -d -p 8085:8085 --env-file .env --name universidad-app universidad-springboot
   ```
3. **Acceso:**
   ```
   http://localhost:8085
   ```

*(En plataformas como Render, solo conectas el repositorio de GitHub y el servicio detecta el `Dockerfile` automáticamente — no hace falta tener Docker instalado localmente. Render inyecta su propio `PORT`, que la app ya lee mediante `server.port=${PORT:8085}`).*

---

## 🔑 Credenciales de prueba

Datos semilla incluidos (ver [`02_datos_iniciales.sql`](./src/main/resources/db/02_datos_iniciales.sql)), solo para fines académicos:

| Rol | Identificador (cédula) | Clave |
| :--- | :--- | :--- |
| Administrador | `1` | `admin` |
| Usuario regular | `10010002` | `LauraG!2024` |

Con la cuenta **Administrador** se ven y usan los botones de Agregar/Modificar/Eliminar; con la cuenta regular, solo lectura y reportes. El script de datos iniciales también incluye usuarios con rol `Docente` y `Estudiante`, y 16 universidades de prueba (públicas y privadas, de distintas ciudades).

---

## ✨ Funcionalidades

1. **Autenticación y control de acceso**
   * Login validado contra base de datos (Spring Security), con roles `ADMIN` / `USER`.
   * Rutas de creación/edición/eliminación restringidas a administradores.
   * Recuperación de clave por correo electrónico (API HTTP de Brevo — no SMTP, porque plataformas gratuitas como Render bloquean esos puertos salientes).
2. **Módulo de Usuarios**
   * CRUD completo (crear, listar, modificar, eliminar).
   * Reportes parametrizados: por rol, por nombre (búsqueda parcial insensible a mayúsculas).
3. **Módulo de Universidades**
   * CRUD completo (crear, listar, modificar, eliminar).
   * Reportes parametrizados: por ciudad, por categoría + número mínimo de sedes.
4. **Internacionalización** de todos los textos vía `messages.properties`.
5. **Validaciones** con Bean Validation en ambas entidades, con mensajes de error personalizados.

---

## 🏛️ Estructura del proyecto

```text
Universidad - Spring Boot/
├── .env.example                     # Plantilla pública de variables de entorno
├── .dockerignore                    # Archivos excluidos del contexto Docker
├── Dockerfile                       # Build multi-etapa (Maven 3.9 + JRE 21)
├── src/
│   ├── main/
│   │   ├── java/com/universidad/
│   │   │   ├── configuracion/       # SecurityConfig, WebConfig (i18n)
│   │   │   ├── controladores/       # Controllers MVC (retornan vistas, no JSON)
│   │   │   ├── servicio/            # Lógica de negocio (Service) + envío de correo
│   │   │   ├── modelo/              # Entidades JPA (Entity)
│   │   │   └── I*Crud.java          # Repositorios Spring Data JPA
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── messages.properties  # Textos de la UI (i18n)
│   │       ├── db/                  # Scripts SQL (DDL, DML y consolidado)
│   │       │   ├── 01_crear_tablas.sql
│   │       │   ├── 02_datos_iniciales.sql
│   │       │   ├── init_db.sql
│   │       │   └── README.md
│   │       └── templates/           # Vistas Thymeleaf
├── pom.xml
└── README.md
```

---

## 🌐 Despliegue

— URL pública: `https://universidad-springboot.onrender.com/`

## 📖 Guía de referencia

Guía de Spring Web MVC utilizada como referencia: *(completar)*
