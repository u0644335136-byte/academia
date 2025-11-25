/**
 * Módulo de gestión de convocatorias
 */

/**
 * Obtiene los datos del formulario de convocatoria
 */
function getConvocatoriaInput() {
    return {
        codigo: document.getElementById("convocatoriaCodigo").value,
        fechaInicio: document.getElementById("convocatoriaFechaInicio").value,
        fechaFin: document.getElementById("convocatoriaFechaFin").value,
        idCurso: document.getElementById("convocatoriaIdCurso").value,
        idCatalogo: document.getElementById("convocatoriaIdCatalogo").value,
        idProfesor: document.getElementById("convocatoriaIdProfesor").value,
        idCentro: document.getElementById("convocatoriaIdCentro").value,
    };
}

/**
 * Carga todas las convocatorias
 */
async function loadConvocatorias() {
    try {
        const query = `query {
            retornarTodasConvocatorias {
                id codigo fechaInicio fechaFin idCurso cursoNombre idProfesor profesorEmail idCentro centroNombre
            }
        }`;
        const json = await fetchGraphQL(query);
        const lista = document.getElementById("convocatoriaList");
        const count = document.getElementById("convocatoriaCount");

        lista.innerHTML = "";
        const convocatorias = json.data && json.data.retornarTodasConvocatorias ? json.data.retornarTodasConvocatorias : [];
        count.textContent = convocatorias.length;

        if (convocatorias.length === 0) {
            lista.innerHTML = '<small style="color:var(--muted)">No hay convocatorias activas...</small>';
            return;
        }

        convocatorias.forEach(c => {
            const item = createConvocatoriaCard(c);
            lista.appendChild(item);
        });
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = "Error al cargar convocatorias: " + error.message;
    }
}

/**
 * Limpia el formulario de convocatoria
 */
function clearConvocatoriaForm() {
    document.getElementById("convocatoriaId").value = "";
    document.getElementById("convocatoriaCodigo").value = "CONV-2025-001";
    document.getElementById("convocatoriaFechaInicio").value = "2026-01-15";
    document.getElementById("convocatoriaFechaFin").value = "2026-06-30";
    document.getElementById("convocatoriaIdCurso").value = "";
    document.getElementById("convocatoriaIdCatalogo").value = "1";
    document.getElementById("convocatoriaIdProfesor").value = "";
    document.getElementById("convocatoriaIdCentro").value = "";
    setConvocatoriaFormMode('create');
}

/**
 * Establece el modo del formulario (create/update)
 */
function setConvocatoriaFormMode(mode) {
    const formCard = document.getElementById("convocatoriaFormCard");
    const idField = document.getElementById("convocatoriaId");
    
    if (mode === 'create') {
        formCard.classList.remove('form-mode-update');
        formCard.classList.add('form-mode-create');
        idField.value = "";
    } else {
        formCard.classList.remove('form-mode-create');
        formCard.classList.add('form-mode-update');
        idField.disabled = false;
    }
}

/**
 * Carga los datos de una convocatoria en el formulario
 */
function loadConvocatoriaIntoForm(convocatoria) {
    if (!convocatoria) return;
    
    document.getElementById("convocatoriaId").value = convocatoria.id || "";
    document.getElementById("convocatoriaCodigo").value = convocatoria.codigo || "";
    document.getElementById("convocatoriaFechaInicio").value = convocatoria.fechaInicio || "2026-01-15";
    document.getElementById("convocatoriaFechaFin").value = convocatoria.fechaFin || "2026-06-30";
    document.getElementById("convocatoriaIdCurso").value = convocatoria.idCurso || "";
    document.getElementById("convocatoriaIdCatalogo").value = convocatoria.idCatalogo || "1";
    document.getElementById("convocatoriaIdProfesor").value = convocatoria.idProfesor || "";
    document.getElementById("convocatoriaIdCentro").value = convocatoria.idCentro || "";
    
    setConvocatoriaFormMode('update');
}

/**
 * Crea una nueva convocatoria
 */
async function createConvocatoria() {
    try {
        const input = getConvocatoriaInput();
        const query = `mutation ($input: ConvocatoriaInput!) {
            crearConvocatoria(input: $input) {
                id codigo fechaInicio
            }
        }`;
        const json = await fetchGraphQL(query, { input });
        
        if (json.errors) {
            document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data, null, 2);
        
        // Limpiar formulario si la creación fue exitosa
        if (json.data && json.data.crearConvocatoria) {
            clearConvocatoriaForm();
        }
        
        await loadConvocatorias();
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Actualiza una convocatoria existente
 */
async function updateConvocatoria() {
    const id = document.getElementById("convocatoriaId").value;
    if (!id) {
        document.getElementById("convocatoriaResult").textContent = "ID de Convocatoria es obligatorio para actualizar.";
        return;
    }
    try {
        const input = getConvocatoriaInput();
        const query = `mutation ($id: ID!, $input: ConvocatoriaInput!) {
            actualizarConvocatoria(id: $id, input: $input) {
                id codigo fechaInicio
            }
        }`;
        const json = await fetchGraphQL(query, { id, input });
        document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
        await loadConvocatorias();
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Obtiene una convocatoria por ID
 */
async function getConvocatoriaById() {
    const idSearch = document.getElementById("convocatoriaIdSearch");
    const id = idSearch ? idSearch.value : document.getElementById("convocatoriaId").value;
    
    if (!id) {
        document.getElementById("convocatoriaResult").textContent = "ID de Convocatoria es obligatorio para buscar.";
        return;
    }
    try {
        const query = `query ($id: ID!) {
            convocatoriaPorId(id: $id) {
                id codigo fechaInicio fechaFin idCurso cursoNombre idProfesor profesorEmail idCentro centroNombre
            }
        }`;
        const json = await fetchGraphQL(query, { id });
        
        if (json.errors) {
            document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        const convocatoria = json.data?.convocatoriaPorId;
        if (convocatoria) {
            // Cargar datos en el formulario
            loadConvocatoriaIntoForm(convocatoria);
            document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data, null, 2);
        } else {
            document.getElementById("convocatoriaResult").textContent = "Convocatoria no encontrada.";
        }
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Crea una card desplegable para una convocatoria
 */
function createConvocatoriaCard(convocatoria) {
    const card = document.createElement("div");
    card.className = "expandable-card";
    
    const fechaInicio = convocatoria.fechaInicio ? new Date(convocatoria.fechaInicio).toLocaleDateString('es-ES') : 'N/A';
    const fechaFin = convocatoria.fechaFin ? new Date(convocatoria.fechaFin).toLocaleDateString('es-ES') : 'N/A';
    const profesorNombre = convocatoria.profesorEmail ? convocatoria.profesorEmail.split('@')[0] : 'Sin Profesor';
    
    card.innerHTML = `
        <div class="expandable-card-header" onclick="toggleCard(this)">
            <div class="expandable-card-title">
                <strong>ID: ${convocatoria.id} - ${convocatoria.codigo || 'N/A'}</strong>
                <small style="color:#738496;margin-left:0.5rem">${fechaInicio} a ${fechaFin}</small>
            </div>
            <div class="expandable-card-actions">
                <span class="pill">${profesorNombre}</span>
                <span class="expand-icon">▼</span>
            </div>
        </div>
        <div class="expandable-card-content" style="display:none;">
            <div class="card-details">
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
                    <span class="detail-value">${convocatoria.cursoNombre || `ID: ${convocatoria.idCurso || 'N/A'}`}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Profesor:</span>
                    <span class="detail-value">${convocatoria.profesorEmail || 'Sin asignar'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Centro:</span>
                    <span class="detail-value">${convocatoria.centroNombre || `ID: ${convocatoria.idCentro || 'N/A'}`}</span>
                </div>
            </div>
        </div>
    `;
    
    return card;
}

/**
 * Elimina una convocatoria
 */
async function deleteConvocatoria() {
    const idSearch = document.getElementById("convocatoriaIdSearch");
    const id = idSearch ? idSearch.value : document.getElementById("convocatoriaId").value;
    
    if (!id) {
        document.getElementById("convocatoriaResult").textContent = "ID de Convocatoria es obligatorio para eliminar.";
        return;
    }
    try {
        const query = `mutation ($id: ID!) {
            eliminarConvocatoria(id: $id)
        }`;
        const json = await fetchGraphQL(query, { id });
        
        if (json.errors) {
            document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        const result = json.data?.eliminarConvocatoria;
        document.getElementById("convocatoriaResult").textContent = result 
            ? "Convocatoria eliminada con éxito." 
            : JSON.stringify(json.data, null, 2);
        
        // Limpiar campos después de eliminar
        if (idSearch) idSearch.value = "";
        if (result) {
            clearConvocatoriaForm();
        }
        
        await loadConvocatorias();
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = `Error: ${error.message}`;
    }
}

