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
                id codigo codigo
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
            const item = document.createElement("div");
            item.className = "list-item";
            item.innerHTML = `
                <div>
                    <strong>ID: ${c.id} - ${c.codigo}</strong><br>
                    <small style="color:#738496">${c.fechaInicio} a ${c.fechaFin} | Centro: ${c.centroNombre}</small>
                </div>
                <span class="pill">${c.profesorEmail ? c.profesorEmail.split('@')[0] : 'Sin Prof'}</span>`;
            lista.appendChild(item);
        });
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = "Error al cargar convocatorias: " + error.message;
    }
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
        document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
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
    const id = document.getElementById("convocatoriaId").value;
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
        document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Elimina una convocatoria
 */
async function deleteConvocatoria() {
    const id = document.getElementById("convocatoriaId").value;
    if (!id) {
        document.getElementById("convocatoriaResult").textContent = "ID de Convocatoria es obligatorio para eliminar.";
        return;
    }
    try {
        const query = `mutation ($id: ID!) {
            eliminarConvocatoria(id: $id)
        }`;
        const json = await fetchGraphQL(query, { id });
        document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
        await loadConvocatorias();
    } catch (error) {
        document.getElementById("convocatoriaResult").textContent = `Error: ${error.message}`;
    }
}

