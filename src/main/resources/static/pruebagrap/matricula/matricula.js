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
            const item = createMatriculaCard(m);
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
 * Crea una card desplegable para una matrícula
 */
function createMatriculaCard(matricula) {
    const card = document.createElement("div");
    card.className = "expandable-card";
    
    const fecha = matricula.fecha ? new Date(matricula.fecha).toLocaleDateString('es-ES') : 'N/A';
    const alumnoDisplay = matricula.alumnoEmail || 'N/A';
    
    card.innerHTML = `
        <div class="expandable-card-header" onclick="toggleCard(this)">
            <div class="expandable-card-title">
                <strong>ID: ${matricula.id} - ${matricula.codigo || 'N/A'}</strong>
                <small style="color:#738496;margin-left:0.5rem">${matricula.precio || 0}€ | ${fecha}</small>
            </div>
            <div class="expandable-card-actions">
                <span class="pill">${alumnoDisplay}</span>
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
                <div class="detail-row">
                    <span class="detail-label">Alumno:</span>
                    <span class="detail-value">${alumnoDisplay}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">ID Alumno:</span>
                    <span class="detail-value">${matricula.idAlumno || 'N/A'}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Convocatoria:</span>
                    <span class="detail-value">${matricula.convocatoriaCodigo || `ID: ${matricula.idConvocatoria || 'N/A'}`}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">ID Convocatoria:</span>
                    <span class="detail-value">${matricula.idConvocatoria || 'N/A'}</span>
                </div>
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
 * Carga convocatorias en el dropdown del formulario de matrícula
 */
async function loadConvocatoriasDropdown() {
    const dropdown = document.getElementById("matriculaIdConvocatoria");
    
    if (!dropdown) {
        console.warn("Dropdown de convocatorias no encontrado, reintentando...");
        setTimeout(loadConvocatoriasDropdown, 100);
        return;
    }

    try {
        console.log("Cargando convocatorias desde GraphQL...");

        const query = `query {
            retornarTodasConvocatorias {
                id
                codigo
            }
        }`;

        const json = await fetchGraphQL(query);

        console.log("Respuesta recibida:", json);

        if (json.errors) {
            console.error("Error en GraphQL:", json.errors);
            dropdown.innerHTML = '<option value="">-- Error cargando convocatorias --</option>';
            return;
        }

        const convocatorias = json.data?.retornarTodasConvocatorias || [];
        console.log(`Encontradas ${convocatorias.length} convocatorias`);

        dropdown.innerHTML = '';

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "-- Selecciona una convocatoria --";
        dropdown.appendChild(defaultOption);

        if (convocatorias.length === 0) {
            const noConvocatoriasOption = document.createElement("option");
            noConvocatoriasOption.value = "";
            noConvocatoriasOption.textContent = "-- No hay convocatorias disponibles --";
            dropdown.appendChild(noConvocatoriasOption);
        } else {
            convocatorias.forEach(convocatoria => {
                const option = document.createElement("option");
                option.value = convocatoria.id;
                option.textContent = `${convocatoria.id} - ${convocatoria.codigo}`;
                dropdown.appendChild(option);
            });
        }

        console.log("Desplegable de convocatorias cargado correctamente");

    } catch (error) {
        console.error("Error al cargar convocatorias:", error);
        if (dropdown) {
            dropdown.innerHTML = '<option value="">-- Error cargando convocatorias --</option>';
        }
    }
}

/**
 * Carga alumnos en el dropdown del formulario de matrícula
 */
async function loadAlumnosDropdown() {
    const dropdown = document.getElementById("matriculaIdAlumno");
    
    if (!dropdown) {
        console.warn("Dropdown de alumnos no encontrado, reintentando...");
        setTimeout(loadAlumnosDropdown, 100);
        return;
    }

    try {
        console.log("Cargando alumnos desde GraphQL...");

        const query = `query {
            searchAlumnos(search: "", activo: true, page: 0, size: 100) {
                alumnos {
                    id_alumno
                    nombre
                    apellidos
                }
                totalElements
            }
        }`;

        const json = await fetchGraphQL(query);

        console.log("Respuesta recibida:", json);

        if (json.errors) {
            console.error("Error en GraphQL:", json.errors);
            dropdown.innerHTML = '<option value="">-- Error cargando alumnos --</option>';
            return;
        }

        const pageData = json.data?.searchAlumnos;
        if (!pageData) {
            console.error("No se encontraron datos de alumnos");
            dropdown.innerHTML = '<option value="">-- No hay alumnos --</option>';
            return;
        }

        const alumnos = pageData.alumnos || [];
        console.log(`Encontrados ${alumnos.length} alumnos`);

        dropdown.innerHTML = '';

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "-- Selecciona un alumno --";
        dropdown.appendChild(defaultOption);

        if (alumnos.length === 0) {
            const noAlumnosOption = document.createElement("option");
            noAlumnosOption.value = "";
            noAlumnosOption.textContent = "-- No hay alumnos disponibles --";
            dropdown.appendChild(noAlumnosOption);
        } else {
            alumnos.forEach(alumno => {
                const option = document.createElement("option");
                option.value = alumno.id_alumno;
                option.textContent = `${alumno.id_alumno} - ${alumno.nombre} ${alumno.apellidos}`;
                dropdown.appendChild(option);
            });
        }

        console.log("Desplegable de alumnos cargado correctamente");

    } catch (error) {
        console.error("Error al cargar alumnos:", error);
        if (dropdown) {
            dropdown.innerHTML = '<option value="">-- Error cargando alumnos --</option>';
        }
    }
}

/**
 * Inicializa los dropdowns del formulario de matrícula
 */
function initMatriculaDropdowns() {
    // Esperar a que el HTML esté cargado
    const checkAndLoad = () => {
        const convocatoriaDropdown = document.getElementById("matriculaIdConvocatoria");
        const alumnoDropdown = document.getElementById("matriculaIdAlumno");
        
        if (convocatoriaDropdown && alumnoDropdown) {
            loadConvocatoriasDropdown();
            loadAlumnosDropdown();
        } else {
            setTimeout(checkAndLoad, 100);
        }
    };
    
    checkAndLoad();
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

