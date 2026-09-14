# Universidad — Spring Boot MVC

Aplicación web desarrollada en **Spring Boot MVC + Thymeleaf + Spring Data JPA + PostgreSQL**, correspondiente al **Ejercicio 2 (Universidad)** de la actividad "Spring Boot MVC con Thymeleaf: desarrollo web basado en framework" — Desarrollo Web, Unidad 2.

Gestiona dos entidades — **Usuario** (autenticación y roles) y **Universidad** (nombre, categoría, web, rector, email, acceso, teléfono, ciudad, número de carreras, número de sedes) — con CRUD completo, reportes parametrizados, autenticación con Spring Security y recuperación de clave por correo electrónico.

> La aplicación es una app MVC tradicional: los controladores retornan vistas Thymeleaf renderizadas en el servidor, **no** es una API REST.

## Tabla de contenido

- [Requisitos](#requisitos)
- [Configuración de la base de datos](#configuración-de-la-base-de-datos)
- [Variables de entorno](#variables-de-entorno)
- [Cómo ejecutar la aplicación](#cómo-ejecutar-la-aplicación)
- [Credenciales de prueba](#credenciales-de-prueba)
- [Funcionalidades](#funcionalidades)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Despliegue](#despliegue)

## Requisitos

- **Java 21**
- **Maven** (el proyecto incluye Maven Wrapper — no es necesario tenerlo instalado, usa `./mvnw`)
- **PostgreSQL** 14 o superior
- Cuenta gratuita en [Brevo](https://www.brevo.com) (para el envío de correos de recuperación de clave vía API HTTP)

## Configuración de la base de datos

1. Crea una base de datos vacía en PostgreSQL, por ejemplo:
   ```sql
   CREATE DATABASE "7502523005_2_Universidad";
   ```
2. Ajusta las credenciales de conexión en `src/main/resources/application.properties` si difieren de las locales (usuario/clave de tu Postgres):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/7502523005_2_Universidad
   spring.datasource.username=postgres
   spring.datasource.password=admin
   ```
3. La tabla `usuarios` se crea automáticamente al arrancar la app (`spring.jpa.hibernate.ddl-auto=update`), pero queda **vacía** — necesitas insertar un usuario administrador inicial para poder iniciar sesión la primera vez:
   ```sql
   INSERT INTO usuarios (id, clave, nombre, rol, email)
   VALUES ('1', 'admin', 'Administrador General', 'Administrador', 'admin@universidad.edu.co');
   ```
   (El `PasswordEncoder` personalizado acepta claves en texto plano además de BCrypt, por eso este insert de arranque funciona directamente; cualquier clave que se guarde luego desde la aplicación se cifra automáticamente).
4. Para la tabla `universidades`, ejecuta el script incluido — crea la tabla con la estructura correcta y carga 15 universidades de ejemplo:
   ```bash
   psql -h localhost -U postgres -d 7502523005_2_Universidad -f src/main/resources/db/schema-universidades.sql
   ```

## Variables de entorno

La recuperación de clave por correo usa la **API HTTP de Brevo** (no SMTP, ya que servicios de despliegue gratuitos como Render bloquean los puertos SMTP salientes). Copia `.env.example` como `.env` en la raíz del proyecto y completa tus valores:

```bash
cp .env.example .env
```

| Variable | Descripción |
|---|---|
| `BREVO_API_KEY` | API key de tu cuenta Brevo (Settings → SMTP & API → API Keys) |
| `BREVO_SENDER_EMAIL` | Correo remitente, ya verificado en Brevo (Senders, Domains & Dedicated IPs → Senders) |
| `APP_BASE_URL` | URL pública donde corre la app, usada para armar el enlace de recuperación (`http://localhost:8085` en local) |

El archivo `.env` **nunca** se sube al repositorio (está en `.gitignore`). En un despliegue (por ejemplo Render), estas mismas variables se configuran directamente en el panel del servicio.

## Cómo ejecutar la aplicación

```bash
git clone <url-del-repositorio>
cd "Universidad - Spring Boot"
cp .env.example .env   # completa BREVO_API_KEY, BREVO_SENDER_EMAIL y APP_BASE_URL
./mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8085`. El login está en `http://localhost:8085/login`.

## Credenciales de prueba

Datos semilla incluidos, solo para fines académicos:

| Rol | Identificador (cédula) | Clave |
|---|---|---|
| Administrador | `1` | `admin` |
| Usuario regular | `10010002` | `LauraG!2024` |

Con la cuenta **Administrador** se ven y usan los botones de Agregar/Modificar/Eliminar; con la cuenta regular, solo lectura y reportes.

## Funcionalidades

- **CRUD completo** de Usuario y Universidad (crear, listar, modificar, eliminar).
- **Reportes parametrizados** (mínimo 2 por entidad):
  - Usuario: por rol, por nombre (búsqueda parcial).
  - Universidad: por ciudad, por categoría + número mínimo de sedes.
- **Autenticación y control de acceso** con Spring Security: roles `ADMIN`/`USER`, rutas de creación/edición/eliminación restringidas a administradores.
- **Recuperación de clave por correo**: solicitud en `/recuperar`, enlace de un solo uso válido 30 minutos, restablecimiento en `/restablecer`.
- **Internacionalización** de textos vía `messages.properties`.
- **Validaciones** con Bean Validation en ambas entidades, con mensajes de error personalizados.

## Estructura del proyecto

```
src/main/java/com/universidad/
├── configuracion/     → Spring Security, i18n
├── controladores/     → Controllers MVC (retornan vistas, no JSON)
├── servicio/          → lógica de negocio (Service)
├── modelo/            → entidades JPA (Entity)
└── I*Crud.java         → repositorios Spring Data JPA

src/main/resources/
├── application.properties
├── messages.properties     → textos de la UI
├── db/schema-universidades.sql
└── templates/              → vistas Thymeleaf
```

## Despliegue

*(pendiente)* — URL pública: `______`

## Guía de referencia

Guía de Spring Web MVC utilizada como referencia: *(completar)*
