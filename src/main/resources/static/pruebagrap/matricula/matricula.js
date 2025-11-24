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
 * Limpia el formulario de matrícula
 */
function clearMatriculaForm() {
    document.getElementById("matriculaId").value = "";
    document.getElementById("matriculaFecha").value = "2025-11-17";
    document.getElementById("matriculaCodigo").value = "MATR-2025-001";
    document.getElementById("matriculaPrecio").value = "1500";
    document.getElementById("matriculaIdConvocatoria").value = "";
    document.getElementById("matriculaIdAlumno").value = "";
    setMatriculaFormMode('create');
}

/**
 * Establece el modo del formulario (create/update)
 */
function setMatriculaFormMode(mode) {
    const formCard = document.getElementById("matriculaFormCard");
    const idField = document.getElementById("matriculaId");
    
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
 * Carga los datos de una matrícula en el formulario
 */
function loadMatriculaIntoForm(matricula) {
    if (!matricula) return;
    
    document.getElementById("matriculaId").value = matricula.id || "";
    document.getElementById("matriculaFecha").value = matricula.fecha || "2025-11-17";
    document.getElementById("matriculaCodigo").value = matricula.codigo || "";
    document.getElementById("matriculaPrecio").value = matricula.precio || "";
    document.getElementById("matriculaIdConvocatoria").value = matricula.idConvocatoria || "";
    document.getElementById("matriculaIdAlumno").value = matricula.idAlumno || "";
    
    setMatriculaFormMode('update');
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
        
        if (json.errors) {
            document.getElementById("matriculaResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        document.getElementById("matriculaResult").textContent = JSON.stringify(json.data, null, 2);
        
        // Limpiar formulario si la creación fue exitosa
        if (json.data && json.data.crearMatricula) {
            clearMatriculaForm();
        }
        
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
        
        if (json.errors) {
            document.getElementById("matriculaResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        document.getElementById("matriculaResult").textContent = JSON.stringify(json.data, null, 2);
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al actualizar matrícula: " + error.message;
    }
}

/**
 * Obtiene una matrícula por ID
 */
async function getMatriculaById() {
    const idSearch = document.getElementById("matriculaIdSearch");
    const id = idSearch ? idSearch.value : document.getElementById("matriculaId").value;
    
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
        
        if (json.errors) {
            document.getElementById("matriculaResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        const matricula = json.data?.matriculaPorId;
        if (matricula) {
            // Cargar datos en el formulario
            loadMatriculaIntoForm(matricula);
            document.getElementById("matriculaResult").textContent = JSON.stringify(json.data, null, 2);
        } else {
            document.getElementById("matriculaResult").textContent = "Matrícula no encontrada.";
        }
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al buscar matrícula: " + error.message;
    }
}

/**
 * Elimina una matrícula
 */
async function deleteMatricula() {
    const idSearch = document.getElementById("matriculaIdSearch");
    const id = idSearch ? idSearch.value : document.getElementById("matriculaId").value;
    
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
        
        // Limpiar campos después de eliminar
        if (idSearch) idSearch.value = "";
        if (result) {
            clearMatriculaForm();
        }
        
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al eliminar matrícula: " + error.message;
    }
}

