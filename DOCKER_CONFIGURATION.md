# Guía de Configuración Docker - Academia

## 📋 Índice
1. [Cómo Funciona la Dockerización](#cómo-funciona-la-dockerización)
2. [Arquitectura del Proyecto](#arquitectura-del-proyecto)
3. [Configuración de Variables de Entorno](#configuración-de-variables-de-entorno)
4. [Problemas Comunes y Soluciones](#problemas-comunes-y-soluciones)
5. [Cómo Acceder desde Otros Equipos](#cómo-acceder-desde-otros-equipos)

---

## 🐳 Cómo Funciona la Dockerización

### Conceptos Básicos

**Docker** es una plataforma que permite empaquetar aplicaciones y sus dependencias en contenedores ligeros y portables. Cada contenedor es un entorno aislado que incluye todo lo necesario para ejecutar la aplicación.

### Arquitectura Multi-Etapa (Multi-Stage Build)

El `Dockerfile` del backend utiliza una arquitectura de **dos etapas**:

#### **Etapa 1: BUILD (Compilación)**
```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS build
```
- **Propósito**: Compilar el código Java y generar el JAR
- **Proceso**:
  1. Copia solo `pom.xml` primero (optimización de caché)
  2. Descarga dependencias Maven (`mvn dependency:go-offline`)
  3. Copia todo el código fuente (`src/`)
  4. Compila y empaqueta (`mvn clean package`)
- **Resultado**: Un archivo JAR en `/app/target/academia-0.0.1-SNAPSHOT.jar`

#### **Etapa 2: RUNTIME (Ejecución)**
```dockerfile
FROM eclipse-temurin:21-jre-alpine
```
- **Propósito**: Ejecutar la aplicación con el mínimo necesario
- **Proceso**:
  1. Usa una imagen más ligera (solo JRE, no JDK completo)
  2. Copia el JAR compilado desde la etapa anterior
  3. Expone el puerto 8080
  4. Ejecuta la aplicación con `java -jar app.jar`
- **Ventaja**: Imagen final mucho más pequeña (~100MB vs ~500MB)

### Docker Compose

`docker-compose.yml` orquesta múltiples contenedores:

```yaml
services:
  backend:    # Contenedor Spring Boot
  frontend:   # Contenedor React + Nginx
```

**Características importantes**:
- **Red interna**: Los contenedores pueden comunicarse por nombre (`backend`, `frontend`)
- **Puertos mapeados**: `"8080:8080"` mapea puerto del host al contenedor
- **Variables de entorno**: Se pasan desde el host al contenedor
- **Dependencias**: `depends_on` asegura el orden de inicio

---

## 🏗️ Arquitectura del Proyecto

```
┌─────────────────────────────────────────────────────────┐
│                    EQUIPO LOCAL                         │
│                  (192.168.0.14)                        │
│                                                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │         Docker Network (bridge)                  │  │
│  │                                                  │  │
│  │  ┌──────────────┐      ┌──────────────┐        │  │
│  │  │   Backend    │      │   Frontend   │        │  │
│  │  │  (Spring)    │◄─────┤  (React)     │        │  │
│  │  │  :8080       │      │  :80         │        │  │
│  │  └──────┬───────┘      └──────┬───────┘        │  │
│  │         │                     │                 │  │
│  └─────────┼─────────────────────┼─────────────────┘  │
│            │                     │                    │
│            ▼                     ▼                    │
│    ┌──────────────┐      ┌──────────────┐           │
│    │   Host:8080  │      │  Host:3000   │           │
│    └──────────────┘      └──────────────┘           │
└───────────────────────────────────────────────────────┘
            │
            │ (Internet/Red Local)
            │
            ▼
┌─────────────────────────────────────────────────────────┐
│              OTRO EQUIPO EN LA RED                      │
│                                                         │
│  Navegador: http://192.168.0.14:3000                  │
│            └─► Frontend (React)                         │
│            └─► Backend API: http://192.168.0.14:8080   │
└─────────────────────────────────────────────────────────┘
            │
            │ (Red)
            │
            ▼
┌─────────────────────────────────────────────────────────┐
│              BASE DE DATOS                               │
│         PostgreSQL (10.10.10.52:5436)                   │
└─────────────────────────────────────────────────────────┘
```

---

## ⚙️ Configuración de Variables de Entorno

### ¿Por qué usar Variables de Entorno?

Las variables de entorno permiten:
- ✅ Cambiar configuración sin recompilar
- ✅ Usar diferentes configuraciones en desarrollo/producción
- ✅ Mantener secretos fuera del código
- ✅ Facilitar el despliegue en diferentes entornos

### Variables Configuradas

#### **Backend (Spring Boot)**

| Variable | Descripción | Valor por Defecto | Ejemplo |
|----------|-------------|-------------------|---------|
| `ALLOWED_ORIGINS` | Orígenes permitidos para CORS | `http://localhost:5173,http://localhost:3000,http://localhost:8080` | `http://192.168.0.14:3000,http://192.168.0.14:8080` |
| `DB_HOST` | IP del servidor PostgreSQL | `10.10.10.52` | `10.10.10.52` |
| `DB_PORT` | Puerto de PostgreSQL | `5436` | `5436` |
| `DB_NAME` | Nombre de la base de datos | `escuela` | `escuela` |
| `DB_USERNAME` | Usuario de PostgreSQL | `admin` | `admin` |
| `DB_PASSWORD` | Contraseña de PostgreSQL | `admin123` | `admin123` |
| `SPRING_PROFILES_ACTIVE` | Perfil activo de Spring | `prod` | `prod` |

#### **Frontend (React)**

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `VITE_API_URL` | URL base del backend API | `http://192.168.0.14:8080` |

### Cómo se Usan las Variables

#### En `application.yml`:
```yaml
cors:
  allowed-origins: ${ALLOWED_ORIGINS:http://localhost:5173,http://localhost:3000,http://localhost:8080}
```

**Sintaxis**: `${VARIABLE:valor_por_defecto}`
- Si existe `ALLOWED_ORIGINS`, usa ese valor
- Si no existe, usa el valor por defecto

#### En `docker-compose.yml`:
```yaml
environment:
  - ALLOWED_ORIGINS=http://192.168.0.14:3000,http://192.168.0.14:8080
```

Estas variables se inyectan en el contenedor cuando se ejecuta.

---

## 🔧 Problemas Comunes y Soluciones

### Problema 1: CORS Error desde Otro Equipo

**Síntoma**: 
```
Access to fetch at 'http://192.168.0.14:8080/graphql' from origin 'http://192.168.0.14:3000' 
has been blocked by CORS policy
```

**Causa**: El backend no tiene la IP del equipo en la lista de orígenes permitidos.

**Solución**:
1. Identifica tu IP local: `ipconfig` (Windows) o `ifconfig` (Linux/Mac)
2. Actualiza `docker-compose.yml`:
```yaml
environment:
  - ALLOWED_ORIGINS=http://localhost:3000,http://192.168.0.14:3000,http://192.168.0.14:8080
```
3. Reinicia los contenedores:
```bash
docker-compose down
docker-compose up --build
```

### Problema 2: No se Conecta a la Base de Datos

**Síntoma**: 
```
Connection refused: connect
```

**Causa**: La IP de la base de datos está hardcodeada o incorrecta.

**Solución**:
1. Verifica que la DB esté accesible desde tu equipo:
```bash
ping 10.10.10.52
```
2. Verifica las variables en `docker-compose.yml`:
```yaml
environment:
  - DB_HOST=10.10.10.52
  - DB_PORT=5436
```
3. Si la DB está en otro servidor, asegúrate de que el firewall permita conexiones.

### Problema 3: El Frontend Llama a `localhost` en Lugar de la IP

**Síntoma**: El frontend funciona en `localhost:3000` pero no desde otro equipo.

**Causa**: El frontend está configurado con `localhost` hardcodeado.

**Solución**: 
- El frontend usa `VITE_API_URL` en tiempo de build
- Actualiza `docker-compose.yml`:
```yaml
frontend:
  build:
    args:
      - VITE_API_URL=http://192.168.0.14:8080
```

### Problema 4: Endpoint GraphQL Relativo (`/graphql`)

**Pregunta**: ¿Por qué usar `/graphql` en lugar de `http://192.168.0.14:8080/graphql`?

**Respuesta**: 
- ✅ **Ventaja**: Funciona automáticamente desde cualquier origen
- ✅ **Ventaja**: No necesitas cambiar código cuando cambias de entorno
- ✅ **Cómo funciona**: El navegador resuelve rutas relativas usando el mismo origen
  - Si estás en `http://192.168.0.14:3000`, `/graphql` → `http://192.168.0.14:3000/graphql` ❌
  - Pero si el frontend hace un proxy o el backend está en el mismo dominio, funciona ✅

**Nota**: En tu caso, como frontend y backend están en puertos diferentes, necesitas:
- Opción A: Usar la URL completa: `http://192.168.0.14:8080/graphql`
- Opción B: Configurar un proxy en Nginx del frontend

---

## 🌐 Cómo Acceder desde Otros Equipos

### Paso 1: Identificar tu IP Local

**Windows**:
```powershell
ipconfig
```
Busca "Dirección IPv4" en el adaptador activo (Wi-Fi o Ethernet).

**Ejemplo**: `192.168.0.14`

### Paso 2: Configurar Docker Compose

Edita `docker-compose.yml` y reemplaza `192.168.0.14` con tu IP real:

```yaml
backend:
  environment:
    - ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080,http://TU_IP:3000,http://TU_IP:8080

frontend:
  build:
    args:
      - VITE_API_URL=http://TU_IP:8080
```

### Paso 3: Verificar Firewall

**Windows**:
1. Abre "Firewall de Windows Defender"
2. Permite aplicaciones a través del firewall
3. Asegúrate de que Docker y Java estén permitidos

**O desde PowerShell (como Administrador)**:
```powershell
New-NetFirewallRule -DisplayName "Docker Backend" -Direction Inbound -LocalPort 8080 -Protocol TCP -Action Allow
New-NetFirewallRule -DisplayName "Docker Frontend" -Direction Inbound -LocalPort 3000 -Protocol TCP -Action Allow
```

### Paso 4: Reiniciar Contenedores

```bash
docker-compose down
docker-compose up --build -d
```

### Paso 5: Probar desde Otro Equipo

Desde otro equipo en la misma red:
- Frontend: `http://TU_IP:3000`
- Backend: `http://TU_IP:8080`
- GraphiQL: `http://TU_IP:8080/graphiql`

---

## 📝 Resumen de Archivos Modificados

### `Dockerfile`
- ✅ Multi-stage build optimizado
- ✅ Compila y empaqueta la aplicación
- ✅ Imagen final ligera con solo JRE

### `docker-compose.yml`
- ✅ Variables de entorno para CORS con tu IP (192.168.0.14)
- ✅ Variables de entorno para conexión a DB
- ✅ Configuración de puertos mapeados

### `application.yml`
- ✅ Usa variables de entorno con valores por defecto
- ✅ Configuración flexible para diferentes entornos

### `SecurityConfig.java`
- ✅ Parsea correctamente listas de CORS desde variables de entorno
- ✅ Maneja strings separados por comas

### `utils.js`
- ✅ Usa endpoint relativo `/graphql` (correcto para rutas relativas)

---

## 🚀 Comandos Útiles

```bash
# Construir y levantar contenedores
docker-compose up --build

# Levantar en segundo plano
docker-compose up -d

# Ver logs
docker-compose logs -f backend
docker-compose logs -f frontend

# Detener contenedores
docker-compose down

# Reconstruir solo un servicio
docker-compose build backend
docker-compose up -d backend

# Ver variables de entorno de un contenedor
docker exec spring-backend env | grep ALLOWED_ORIGINS
```

---

## ✅ Checklist de Configuración

- [ ] IP local identificada y configurada en `docker-compose.yml`
- [ ] Variables de entorno `ALLOWED_ORIGINS` incluyen tu IP
- [ ] Variables de DB configuradas correctamente
- [ ] Firewall permite conexiones en puertos 3000 y 8080
- [ ] Contenedores se levantan sin errores
- [ ] Backend responde en `http://TU_IP:8080`
- [ ] Frontend responde en `http://TU_IP:3000`
- [ ] CORS funciona desde otro equipo
- [ ] Conexión a DB funciona correctamente

---

**Última actualización**: Configuración para IP `192.168.0.14`

