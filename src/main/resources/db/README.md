# Scripts de Base de Datos — Universidad Spring Boot

Este directorio contiene los scripts SQL para crear la estructura y cargar los datos iniciales en **PostgreSQL** (sirve tanto para desarrollo local como para servicios en la nube: Supabase, Neon, Render, AWS RDS, etc.).

> Nota: si corres la aplicación con `spring.jpa.hibernate.ddl-auto=update` (configuración por defecto de este proyecto), Hibernate crea las tablas automáticamente al arrancar. Estos scripts existen para: (1) documentar la estructura exacta de forma explícita, (2) poder preparar una base de datos externa (Supabase, Render) sin necesidad de arrancar la app primero, y (3) cargar los datos de prueba.

---

## Archivos disponibles

1. **`01_crear_tablas.sql` (DDL)**
   Crea las tablas `usuarios`, `universidades` y `tokens_recuperacion`, con sus llaves primarias e índices para los reportes parametrizados.

2. **`02_datos_iniciales.sql` (DML)**
   Inserta el usuario administrador inicial, 10 usuarios de prueba con roles variados (`Administrador`, `Docente`, `Estudiante`) y 16 universidades de prueba (públicas y privadas, de distintas ciudades).

3. **`init_db.sql` (todo en uno)**
   Script consolidado (DDL + DML) para ejecutar de una sola vez, ideal para pegar en el SQL Editor de Supabase/Neon o en un solo comando de `psql`.

---

## Usuario administrador inicial (credenciales de acceso)

| Campo | Valor |
| :--- | :--- |
| **Identificación / ID** | `1` |
| **Contraseña** | `admin` |
| **Nombre** | `Administrador General` |
| **Rol** | `Administrador` |
| **Correo** | `admin@universidad.edu.co` |

> También se incluyen usuarios con rol `Docente` y `Estudiante` en `02_datos_iniciales.sql`, útiles para demostrar el control de acceso por rol durante la sustentación.

---

## Instrucciones de ejecución

### Opción A: PostgreSQL local, con `psql`

```bash
# 1. Crear la base de datos (si no existe)
createdb -U postgres 7502523005_2_Universidad

# 2. Ejecutar el script consolidado
psql -U postgres -d 7502523005_2_Universidad -f src/main/resources/db/init_db.sql
```

O por separado:
```bash
psql -U postgres -d 7502523005_2_Universidad -f src/main/resources/db/01_crear_tablas.sql
psql -U postgres -d 7502523005_2_Universidad -f src/main/resources/db/02_datos_iniciales.sql
```

### Opción B: Servicio en la nube (Supabase, Neon, Render PostgreSQL)

1. Crea la base de datos en el proveedor elegido.
2. Entra al **SQL Editor** (o conéctate con `psql` a la URL que te den).
3. Pega el contenido completo de [`init_db.sql`](./init_db.sql) y ejecútalo.
4. Actualiza `spring.datasource.url/username/password` (o las variables de entorno equivalentes) apuntando a esa base de datos.
