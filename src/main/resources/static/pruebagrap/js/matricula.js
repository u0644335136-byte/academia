/**
 * Módulo de gestión de matrículas (GraphQL)
 */

/**
 * Obtiene los datos del formulario de matrícula
 */
function getMatriculaInput() {
    const precio = document.getElementById("matriculaPrecio").value;
    const idConvocatoria = document.getElementById("matriculaIdConvocatoria").value;
    const idAlumno = document.getElementById("matriculaIdAlumno").value;
    
    return {
        fecha: document.getElementById("matriculaFecha").value,
        codigo: document.getElementById("matriculaCodigo").value,
        precio: precio ? parseInt(precio) : null,
        idConvocatoria: idConvocatoria ? parseInt(idConvocatoria) : null,
        idAlumno: idAlumno ? parseInt(idAlumno) : null
    };
}

/**
 * Carga todas las matrículas
 */
async function loadMatriculas() {
    try {
        const query = `query {
            retornarTodasMatriculas {
                id fecha codigo precio idConvocatoria convocatoriaCodigo idAlumno alumnoEmail nota
            }
        }`;
        const json = await fetchGraphQL(query);
        const lista = document.getElementById("matriculaList");
        const count = document.getElementById("matriculaCount");

        lista.innerHTML = "";
        
        if (json.errors) {
            lista.innerHTML = `<small style="color:red">Error: ${JSON.stringify(json.errors)}</small>`;
            return;
        }

        const matriculas = json.data && json.data.retornarTodasMatriculas ? json.data.retornarTodasMatriculas : [];
        count.textContent = matriculas.length;

        if (matriculas.length === 0) {
            lista.innerHTML = '<small style="color:var(--muted)">No hay matrículas activas...</small>';
            return;
        }

        matriculas.forEach(m => {
            const item = document.createElement("div");
            item.className = "list-item";
            item.innerHTML = `
                <div>
                    <strong>ID: ${m.id} - ${m.codigo} (${m.precio}€)</strong><br>
                    <small style="color:#738496">Alumno: ${m.alumnoEmail || 'N/A'} | Conv: ${m.convocatoriaCodigo || 'N/A'}</small>
                </div>
                <span class="pill">${m.fecha || 'Sin fecha'}</span>`;
            lista.appendChild(item);
        });
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al cargar matrículas: " + error.message;
    }
}

/**
 * Crea una nueva matrícula
 */
async function createMatricula() {
    try {
        const input = getMatriculaInput();
        const query = `mutation ($input: MatriculaInput!) {
            crearMatricula(input: $input) {
                id codigo fecha precio idConvocatoria idAlumno
            }
        }`;
        const json = await fetchGraphQL(query, { input });
        document.getElementById("matriculaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al crear matrícula: " + error.message;
    }
}

/**
 * Actualiza una matrícula existente
 */
async function updateMatricula() {
    const id = document.getElementById("matriculaId").value;
    if (!id) {
        document.getElementById("matriculaResult").textContent = "ID de Matrícula es obligatorio para actualizar.";
        return;
    }
    try {
        const input = getMatriculaInput();
        const query = `mutation ($id: ID!, $input: MatriculaInput!) {
            actualizarMatricula(id: $id, input: $input) {
                id codigo fecha precio idConvocatoria idAlumno
            }
        }`;
        const json = await fetchGraphQL(query, { id, input });
        document.getElementById("matriculaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al actualizar matrícula: " + error.message;
    }
}

/**
 * Obtiene una matrícula por ID
 */
async function getMatriculaById() {
    const id = document.getElementById("matriculaId").value;
    if (!id) {
        document.getElementById("matriculaResult").textContent = "ID de Matrícula es obligatorio para buscar.";
        return;
    }
    try {
        const query = `query ($id: ID!) {
            matriculaPorId(id: $id) {
                id fecha codigo precio idConvocatoria convocatoriaCodigo idAlumno alumnoEmail nota
            }
        }`;
        const json = await fetchGraphQL(query, { id });
        document.getElementById("matriculaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al buscar matrícula: " + error.message;
    }
}

/**
 * Elimina una matrícula
 */
async function deleteMatricula() {
    const id = document.getElementById("matriculaId").value;
    if (!id) {
        document.getElementById("matriculaResult").textContent = "ID de Matrícula es obligatorio para eliminar.";
        return;
    }
    try {
        const query = `mutation ($id: ID!) {
            eliminarMatricula(id: $id)
        }`;
        const json = await fetchGraphQL(query, { id });
        const result = json.data?.eliminarMatricula;
        document.getElementById("matriculaResult").textContent = result 
            ? "Matrícula eliminada (soft delete) con éxito." 
            : JSON.stringify(json.errors || json.data, null, 2);
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al eliminar matrícula: " + error.message;
    }
}

