/**
 * ============================================
 * MÓDULO: Detalles de Convocatoria con Queries Anidadas
 * ============================================
 * Este módulo demuestra las VENTAJAS de GraphQL sobre REST:
 * 
 * ANTES (REST - múltiples peticiones):
 *   1. GET /convocatoria/123
 *   2. GET /matriculas?convocatoriaId=123
 *   3. GET /alumnos?ids=1,2,3...
 * 
 * AHORA (GraphQL - una sola query anidada):
 *   query {
 *     convocatoriaPorId(id: "123") {
 *       # Datos de la convocatoria
 *       codigo
 *       fechaInicio
 *       cursoNombre
 *       
 *       # Todas las matrículas (resuelto automáticamente)
 *       matriculas {
 *         codigo
 *         precio
 *         fecha
 *         alumno {  # ← Navegación anidada
 *           nombre
 *           apellidos
 *         }
 *       }
 *       
 *       # Todos los alumnos únicos (resuelto automáticamente)
 *       alumnos {
 *         nombre
 *         apellidos
 *         email
 *       }
 *     }
 *   }
 * 
 * VENTAJAS:
 * - Una sola petición HTTP en lugar de 3+
 * - Menos latencia (especialmente importante en móviles)
 * - Menos ancho de banda (solo pides los campos que necesitas)
 * - El cliente decide qué necesita (flexibilidad)
 * ============================================
 */

/**
 * Carga el dropdown de convocatorias al iniciar
 */
async function initConvocatoriaDetalle() {
    const dropdown = document.getElementById("convocatoriaSelect");
    
    if (!dropdown) {
        setTimeout(initConvocatoriaDetalle, 100);
        return;
    }

    try {
        const query = `query {
            retornarTodasConvocatorias {
                id
                codigo
                fechaInicio
                fechaFin
            }
        }`;

        const json = await fetchGraphQL(query);

        if (json.errors) {
            dropdown.innerHTML = '<option value="">-- Error cargando convocatorias --</option>';
            return;
        }

        const convocatorias = json.data?.retornarTodasConvocatorias || [];
        dropdown.innerHTML = '';

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "-- Selecciona una convocatoria --";
        dropdown.appendChild(defaultOption);

        convocatorias.forEach(convocatoria => {
            const option = document.createElement("option");
            option.value = convocatoria.id;
            const fechaInicio = convocatoria.fechaInicio ? new Date(convocatoria.fechaInicio).toLocaleDateString('es-ES') : '';
            option.textContent = `${convocatoria.id} - ${convocatoria.codigo} (${fechaInicio})`;
            dropdown.appendChild(option);
        });
    } catch (error) {
        console.error("Error al cargar convocatorias:", error);
        dropdown.innerHTML = '<option value="">-- Error cargando convocatorias --</option>';
    }
}

/**
 * ============================================
 * FUNCIÓN PRINCIPAL: Query Anidada de GraphQL
 * ============================================
 * Esta función demuestra el PODER de GraphQL:
 * - Una sola query obtiene convocatoria + matrículas + alumnos
 * - Todo en una sola petición HTTP
 * - El backend resuelve las relaciones automáticamente
 * ============================================
 */
async function loadConvocatoriaDetalle() {
    const convocatoriaId = document.getElementById("convocatoriaSelect").value;
    
    if (!convocatoriaId) {
        alert("Por favor, selecciona una convocatoria");
        return;
    }

    try {
        // ============================================
        // QUERY ANIDADA: La ventaja principal de GraphQL
        // ============================================
        // Esta query obtiene TODO en una sola petición:
        // 1. Datos de la convocatoria
        // 2. Todas las matrículas (campo "matriculas" resuelto por @SchemaMapping)
        // 3. Todos los alumnos (campo "alumnos" resuelto por @SchemaMapping)
        //
        // En REST necesitarías 3+ peticiones HTTP separadas.
        // Con GraphQL: 1 petición, el backend resuelve todo automáticamente.
        // ============================================
        const query = `query ($id: ID!) {
            convocatoriaPorId(id: $id) {
                id
                codigo
                fechaInicio
                fechaFin
                cursoNombre
                profesorEmail
                centroNombre
                
                # Campo anidado: obtiene todas las matrículas de esta convocatoria
                # El resolver @SchemaMapping en ConvocatoriaGraphQLController se ejecuta automáticamente
                matriculas {
                    id
                    codigo
                    fecha
                    precio
                    nota
                    # Navegación anidada: de matrícula a alumno
                    # El resolver @SchemaMapping en MatriculaGraphQLController se ejecuta automáticamente
                    alumno {
                        id_alumno
                        nombre
                        apellidos
                        email
                        telefono
                    }
                }
                
                # Campo anidado: obtiene todos los alumnos únicos de esta convocatoria
                # El resolver @SchemaMapping en ConvocatoriaGraphQLController se ejecuta automáticamente
                alumnos {
                    id_alumno
                    nombre
                    apellidos
                    email
                    telefono
                    fecha_nacimiento
                    localidad
                    provincia
                    activo
                }
            }
        }`;

        const json = await fetchGraphQL(query, { id: convocatoriaId });

        if (json.errors) {
            console.error("Error en GraphQL:", json.errors);
            alert("Error al cargar los detalles: " + JSON.stringify(json.errors));
            return;
        }

        const convocatoria = json.data?.convocatoriaPorId;
        
        if (!convocatoria) {
            alert("Convocatoria no encontrada");
            return;
        }

        // Mostrar información de la convocatoria
        displayConvocatoriaInfo(convocatoria);
        
        // Mostrar matrículas
        displayMatriculas(convocatoria.matriculas || []);
        
        // Mostrar alumnos
        displayAlumnos(convocatoria.alumnos || []);

    } catch (error) {
        console.error("Error al cargar detalles:", error);
        alert("Error al cargar los detalles: " + error.message);
    }
}

/**
 * Muestra la información de la convocatoria
 */
function displayConvocatoriaInfo(convocatoria) {
    const card = document.getElementById("convocatoriaInfoCard");
    const info = document.getElementById("convocatoriaInfo");
    
    const fechaInicio = convocatoria.fechaInicio ? new Date(convocatoria.fechaInicio).toLocaleDateString('es-ES') : 'N/A';
    const fechaFin = convocatoria.fechaFin ? new Date(convocatoria.fechaFin).toLocaleDateString('es-ES') : 'N/A';
    
    info.innerHTML = `
        <div class="detail-row">
            <span class="detail-label">Código:</span>
            <span class="detail-value">${convocatoria.codigo || 'N/A'}</span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Fecha Inicio:</span>
            <span class="detail-value">${fechaInicio}</span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Fecha Fin:</span>
            <span class="detail-value">${fechaFin}</span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Curso:</span>
            <span class="detail-value">${convocatoria.cursoNombre || 'N/A'}</span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Profesor:</span>
            <span class="detail-value">${convocatoria.profesorEmail || 'Sin asignar'}</span>
        </div>
        <div class="detail-row">
            <span class="detail-label">Centro:</span>
            <span class="detail-value">${convocatoria.centroNombre || 'N/A'}</span>
        </div>
    `;
    
    card.style.display = 'block';
}

/**
 * Muestra las matrículas usando cards desplegables
 */
function displayMatriculas(matriculas) {
    const card = document.getElementById("matriculasCard");
    const list = document.getElementById("matriculasList");
    const count = document.getElementById("matriculasCount");
    
    count.textContent = matriculas.length;
    list.innerHTML = "";
    
    if (matriculas.length === 0) {
        list.innerHTML = '<small style="color:var(--muted)">No hay matrículas en esta convocatoria...</small>';
    } else {
        matriculas.forEach(matricula => {
            const item = createMatriculaCardDetalle(matricula);
            list.appendChild(item);
        });
    }
    
    card.style.display = 'block';
}

/**
 * Crea una card desplegable para una matrícula (versión detalle)
 */
function createMatriculaCardDetalle(matricula) {
    const card = document.createElement("div");
    card.className = "expandable-card";
    
    const fecha = matricula.fecha ? new Date(matricula.fecha).toLocaleDateString('es-ES') : 'N/A';
    const alumnoNombre = matricula.alumno 
        ? `${matricula.alumno.nombre} ${matricula.alumno.apellidos}` 
        : matricula.alumnoEmail || 'N/A';
    
    card.innerHTML = `
        <div class="expandable-card-header" onclick="toggleCard(this)">
            <div class="expandable-card-title">
                <strong>ID: ${matricula.id} - ${matricula.codigo || 'N/A'}</strong>
                <small style="color:#738496;margin-left:0.5rem">${matricula.precio || 0}€ | ${fecha}</small>
            </div>
            <div class="expandable-card-actions">
                <span class="pill">${alumnoNombre}</span>
                <span class="expand-icon">▼</span>
            </div>
        </div>
        <div class="expandable-card-content" style="display:none;">
            <div class="card-details">
                <div class="detail-row">
                    <span class="detail-label">Código:</span>
                    <span class="detail-value">${matricula.codigo || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Fecha:</span>
                    <span class="detail-value">${fecha}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Precio:</span>
                    <span class="detail-value">${matricula.precio || 0}€</span>
                </div>
                ${matricula.alumno ? `
                <div class="detail-row">
                    <span class="detail-label">Alumno:</span>
                    <span class="detail-value">${matricula.alumno.nombre} ${matricula.alumno.apellidos}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Email Alumno:</span>
                    <span class="detail-value">${matricula.alumno.email || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Teléfono Alumno:</span>
                    <span class="detail-value">${matricula.alumno.telefono || 'N/A'}</span>
                </div>
                ` : ''}
                ${matricula.nota !== null && matricula.nota !== undefined ? `
                <div class="detail-row">
                    <span class="detail-label">Nota:</span>
                    <span class="detail-value">${matricula.nota}</span>
                </div>
                ` : ''}
            </div>
        </div>
    `;
    
    return card;
}

/**
 * Muestra los alumnos usando cards desplegables
 */
function displayAlumnos(alumnos) {
    const card = document.getElementById("alumnosCard");
    const list = document.getElementById("alumnosList");
    const count = document.getElementById("alumnosCount");
    
    count.textContent = alumnos.length;
    list.innerHTML = "";
    
    if (alumnos.length === 0) {
        list.innerHTML = '<small style="color:var(--muted)">No hay alumnos en esta convocatoria...</small>';
    } else {
        alumnos.forEach(alumno => {
            const item = createAlumnoCardDetalle(alumno);
            list.appendChild(item);
        });
    }
    
    card.style.display = 'block';
}

/**
 * Crea una card desplegable para un alumno (versión detalle)
 */
function createAlumnoCardDetalle(alumno) {
    const card = document.createElement("div");
    card.className = "expandable-card";
    
    const fechaNac = alumno.fecha_nacimiento ? new Date(alumno.fecha_nacimiento).toLocaleDateString('es-ES') : 'N/A';
    
    card.innerHTML = `
        <div class="expandable-card-header" onclick="toggleCard(this)">
            <div class="expandable-card-title">
                <strong>ID: ${alumno.id_alumno} - ${alumno.nombre} ${alumno.apellidos}</strong>
                <small style="color:#738496;margin-left:0.5rem">${alumno.email} | ${alumno.telefono}</small>
            </div>
            <div class="expandable-card-actions">
                <span class="pill">${alumno.activo ? "Activo" : "Inactivo"}</span>
                <span class="expand-icon">▼</span>
            </div>
        </div>
        <div class="expandable-card-content" style="display:none;">
            <div class="card-details">
                <div class="detail-row">
                    <span class="detail-label">Email:</span>
                    <span class="detail-value">${alumno.email || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Teléfono:</span>
                    <span class="detail-value">${alumno.telefono || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Fecha de Nacimiento:</span>
                    <span class="detail-value">${fechaNac}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Localidad:</span>
                    <span class="detail-value">${alumno.localidad || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Provincia:</span>
                    <span class="detail-value">${alumno.provincia || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Estado:</span>
                    <span class="detail-value">${alumno.activo ? "Activo" : "Inactivo"}</span>
                </div>
            </div>
        </div>
    `;
    
    return card;
}

