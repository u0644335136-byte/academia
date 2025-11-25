# 📚 Explicación: LAZY vs EAGER Loading en JPA/Hibernate

## 🎯 Resumen Ejecutivo

**LAZY (Carga Diferida)** = Carga las relaciones SOLO cuando las necesitas
**EAGER (Carga Inmediata)** = Carga las relaciones SIEMPRE, incluso si no las necesitas

## 🔍 ¿Cuándo usar LAZY? (Recomendado en la mayoría de casos)

### ✅ Casos donde LAZY es MEJOR:

#### 1. **Listas de entidades (más común)**
```java
// Escenario: Mostrar lista de 100 convocatorias en una tabla
List<Convocatoria> convocatorias = repository.findAll();

// Con LAZY:
// - 1 query SQL: SELECT * FROM convocatoria
// - Solo carga los datos básicos (id, código, fechas)
// - NO carga curso, profesor, centro (no los necesitas en la lista)
// - Resultado: Query rápida, menos memoria, mejor rendimiento

// Con EAGER:
// - 1 query SQL con 3 JOINs: SELECT c.*, curso.*, profesor.*, centro.* FROM convocatoria c...
// - Carga TODAS las relaciones para TODAS las convocatorias
// - Si hay 100 convocatorias, carga 100 cursos, 100 profesores, 100 centros
// - Resultado: Query lenta, mucha memoria, peor rendimiento
```

**Ejemplo real:**
- Frontend muestra: "ID: 215 - CONV-2025-001"
- NO necesita: nombre del curso, email del profesor, nombre del centro
- Con LAZY: Solo carga lo necesario ✅
- Con EAGER: Carga todo innecesariamente ❌

#### 2. **APIs REST con endpoints específicos**
```java
// Endpoint 1: GET /convocatorias (lista)
// - Solo necesitas: id, código, fechas
// - NO necesitas: curso, profesor, centro
// - LAZY es perfecto aquí ✅

// Endpoint 2: GET /convocatorias/{id} (detalle)
// - Necesitas: TODO (incluyendo curso, profesor, centro)
// - Aquí cargas explícitamente con @EntityGraph o JOIN FETCH
// - Mejor control sobre QUÉ cargar y CUÁNDO ✅
```

#### 3. **GraphQL (tu caso actual)**
```graphql
# Query 1: Solo necesitas datos básicos
query {
  retornarTodasConvocatorias {
    id
    codigo
    fechaInicio
    # NO pides curso, profesor, centro
  }
}
# Con LAZY: Solo carga lo necesario ✅
# Con EAGER: Carga todo innecesariamente ❌

# Query 2: Necesitas relaciones
query {
  convocatoriaPorId(id: "215") {
    codigo
    cursoNombre      # ← Necesitas curso
    profesorEmail    # ← Necesitas profesor
    centroNombre     # ← Necesitas centro
  }
}
# Con LAZY + @EntityGraph: Carga solo cuando lo necesitas ✅
# Con EAGER: Carga siempre, incluso cuando no lo necesitas ❌
```

#### 4. **Relaciones con muchas entidades hijas**
```java
@OneToMany(fetch = FetchType.LAZY)
private List<Matricula> matriculas;  // Una convocatoria puede tener 1000 matrículas

// Con LAZY:
// - Si solo necesitas la convocatoria: NO carga las 1000 matrículas ✅
// - Si necesitas las matrículas: Las cargas explícitamente cuando las necesitas ✅

// Con EAGER:
// - SIEMPRE carga las 1000 matrículas, incluso si solo necesitas el código de la convocatoria ❌
// - Resultado: Query extremadamente lenta, mucha memoria
```

## ⚠️ ¿Cuándo usar EAGER? (Casos muy específicos)

### ❌ Casos donde EAGER podría ser útil (pero raro):

#### 1. **Relación siempre necesaria y pequeña**
```java
// Ejemplo: Un Usuario SIEMPRE tiene un Perfil (1 a 1, pequeño)
@OneToOne(fetch = FetchType.EAGER)
private Perfil perfil;  // El perfil siempre se necesita y es pequeño

// Pero incluso aquí, LAZY + carga explícita es mejor para flexibilidad
```

#### 2. **Aplicaciones pequeñas con pocos datos**
```java
// Si tu aplicación tiene 10 convocatorias y siempre necesitas todo
// EAGER podría funcionar, pero NO escala bien
```

## 🚫 Problemas de EAGER

### 1. **Problema N+1 (sin control)**
```java
// Con EAGER, si haces:
List<Convocatoria> convocatorias = repository.findAll();

// JPA puede ejecutar:
// Query 1: SELECT * FROM convocatoria
// Query 2: SELECT * FROM curso WHERE id_curso = 1
// Query 3: SELECT * FROM curso WHERE id_curso = 2
// Query 4: SELECT * FROM profesor WHERE id_profesor = 1
// ... (N+1 queries)

// Con LAZY + @EntityGraph, controlas QUÉ cargar y CUÁNDO
```

### 2. **Carga innecesaria de datos**
```java
// Si solo necesitas el ID y código:
Convocatoria c = repository.findById(1L);

// Con EAGER: Carga curso, profesor, centro (innecesario)
// Con LAZY: Solo carga lo básico ✅
```

### 3. **No escala**
```java
// Si tienes 10,000 convocatorias y solo necesitas mostrar una lista
// Con EAGER: Carga 10,000 cursos, 10,000 profesores, 10,000 centros
// Con LAZY: Solo carga 10,000 convocatorias básicas
```

## ✅ Solución Implementada: LAZY + Carga Explícita

### Tu código actual (CORRECTO):

```java
// 1. Entidad con LAZY (por defecto, eficiente)
@ManyToOne(fetch = FetchType.LAZY)
private Curso curso;

// 2. Repositorio con método que carga explícitamente cuando lo necesitas
@EntityGraph(attributePaths = {"curso", "profesor", "centro"})
Optional<Convocatoria> findByIdWithRelations(Long id);

// 3. Servicio que usa el método explícito cuando necesita las relaciones
@Override
public Optional<ConvocatoriaResponseDTO> getById(Long id) {
    return convocatoriaRepository.findByIdWithRelations(id)  // ← Carga explícita
            .filter(Convocatoria::getActivo)
            .map(mapper::toDto);
}
```

### Ventajas de esta solución:

1. **Flexibilidad**: Decides QUÉ cargar y CUÁNDO
2. **Rendimiento**: Solo cargas lo que necesitas
3. **Escalabilidad**: Funciona bien con muchos datos
4. **Control**: Tienes control total sobre las queries

## 📊 Comparación Visual

### Escenario: Lista de 100 convocatorias

| Aspecto | LAZY | EAGER |
|---------|------|-------|
| Queries SQL | 1 query simple | 1 query con 3 JOINs |
| Datos cargados | Solo convocatorias básicas | Convocatorias + 100 cursos + 100 profesores + 100 centros |
| Tiempo de respuesta | ~50ms | ~200ms |
| Memoria usada | 1MB | 5MB |
| Escalabilidad | ✅ Excelente | ❌ Pobre |

### Escenario: Detalle de 1 convocatoria (con relaciones)

| Aspecto | LAZY + @EntityGraph | EAGER |
|---------|---------------------|-------|
| Queries SQL | 1 query con JOINs controlados | 1 query con JOINs (siempre) |
| Datos cargados | Solo cuando lo necesitas | Siempre, incluso si no lo necesitas |
| Control | ✅ Tú decides | ❌ Siempre carga todo |
| Flexibilidad | ✅ Alta | ❌ Baja |

## 🎓 Regla de Oro

> **"Usa LAZY por defecto, y carga explícitamente solo cuando lo necesites"**

### Patrón recomendado:

1. **Define relaciones como LAZY** (eficiente por defecto)
2. **Crea métodos en el repositorio** con `@EntityGraph` o `JOIN FETCH` para cargar explícitamente
3. **Usa esos métodos** en el servicio cuando necesites las relaciones
4. **Mantén el lazy loading** para casos donde no necesitas las relaciones

## 🔧 Tu Implementación Actual (Perfecta)

```java
// ✅ Entidad: LAZY (eficiente por defecto)
@ManyToOne(fetch = FetchType.LAZY)
private Curso curso;

// ✅ Repositorio: Método para cargar explícitamente cuando lo necesitas
@EntityGraph(attributePaths = {"curso", "profesor", "centro"})
Optional<Convocatoria> findByIdWithRelations(Long id);

// ✅ Servicio: Usa carga explícita cuando necesita las relaciones
@Override
public Optional<ConvocatoriaResponseDTO> getById(Long id) {
    return convocatoriaRepository.findByIdWithRelations(id)  // ← Carga explícita
            .map(mapper::toDto);
}
```

**Esto es la MEJOR práctica** porque:
- Eficiente por defecto (LAZY)
- Flexible cuando lo necesitas (carga explícita)
- Control total sobre las queries
- Escala bien con muchos datos

