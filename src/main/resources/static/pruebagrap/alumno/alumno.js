/**
 * Módulo de gestión de alumnos
 */

let currentPage = 1;
const pageSize = 10;
let currentSearch = '';
let currentActivo = null;

/**
 * Obtiene los datos del formulario de alumno
 */
function getAlumnoInput() {
    return {
        nombre: document.getElementById("nombre").value,
        apellidos: document.getElementById("apellidos").value,
        telefono: document.getElementById("telefono").value,
        email: document.getElementById("email").value,
        contrasenia: document.getElementById("contrasenia").value,
        fecha_nacimiento: document.getElementById("fechaNacimiento").value,
        localidad: document.getElementById("localidad").value,
        provincia: document.getElementById("provincia").value,
        activo: document.getElementById("activo").checked
    };
}

/**
 * Carga alumnos con búsqueda, filtro y paginación
 */
async function loadAlumnos() {
    try {
        const query = `query ($search: String, $activo: Boolean, $page: Int, $size: Int) {
            searchAlumnos(search: $search, activo: $activo, page: $page, size: $size) {
                alumnos {
                    id_alumno nombre apellidos email telefono activo
                }
                totalElements
                totalPages
                currentPage
                pageSize
            }
        }`;
        const json = await fetchGraphQL(query, {
            search: currentSearch || null,
            activo: currentActivo,
            page: currentPage,
            size: pageSize
        });
        
        const lista = document.getElementById("alumnoList");
        const count = document.getElementById("alumnoCount");
        const paginationInfo = document.getElementById("paginationInfo");
        const paginationControls = document.getElementById("paginationControls");

        if (json.errors) {
            lista.innerHTML = `<small style="color:red">Error: ${JSON.stringify(json.errors)}</small>`;
            return;
        }

        const pageData = json.data?.searchAlumnos;
        if (!pageData) {
            lista.innerHTML = '<small style="color:var(--muted)">Error al cargar datos...</small>';
            return;
        }

        const alumnos = pageData.alumnos || [];
        lista.innerHTML = "";
        count.textContent = pageData.totalElements || 0;

        if (alumnos.length === 0) {
            lista.innerHTML = '<small style="color:var(--muted)">No hay alumnos encontrados...</small>';
        } else {
            alumnos.forEach(a => {
                const item = document.createElement("div");
                item.className = "list-item";
                item.innerHTML = `
                    <div>
                        <strong>ID: ${a.id_alumno} - ${a.nombre} ${a.apellidos}</strong><br>
                        <small style="color:#738496">${a.email} | ${a.telefono}</small>
                    </div>
                    <span class="pill">${a.activo ? "Activo" : "Inactivo"}</span>`;
                lista.appendChild(item);
            });
        }

        // Actualizar información de paginación
        if (paginationInfo) {
            paginationInfo.textContent = `Página ${pageData.currentPage} de ${pageData.totalPages} (${pageData.totalElements} total)`;
        }

        // Actualizar controles de paginación
        if (paginationControls) {
            updatePaginationControls(pageData.totalPages, pageData.currentPage);
        }
    } catch (error) {
        document.getElementById("alumnoList").innerHTML = 
            `<small style="color:red">Error: ${error.message}</small>`;
    }
}

/**
 * Actualiza los controles de paginación
 */
function updatePaginationControls(totalPages, currentPageNum) {
    const paginationControls = document.getElementById("paginationControls");
    if (!paginationControls) return;

    paginationControls.innerHTML = "";
    
    if (totalPages <= 1) return;

    // Botón anterior
    const prevBtn = document.createElement("button");
    prevBtn.className = "secondary";
    prevBtn.textContent = "← Anterior";
    prevBtn.disabled = currentPageNum === 1;
    prevBtn.onclick = () => {
        if (currentPageNum > 1) {
            currentPage = currentPageNum - 1;
            loadAlumnos();
        }
    };
    paginationControls.appendChild(prevBtn);

    // Información de página
    const pageInfo = document.createElement("span");
    pageInfo.style.margin = "0 1rem";
    pageInfo.textContent = `${currentPageNum} / ${totalPages}`;
    paginationControls.appendChild(pageInfo);

    // Botón siguiente
    const nextBtn = document.createElement("button");
    nextBtn.className = "secondary";
    nextBtn.textContent = "Siguiente →";
    nextBtn.disabled = currentPageNum === totalPages;
    nextBtn.onclick = () => {
        if (currentPageNum < totalPages) {
            currentPage = currentPageNum + 1;
            loadAlumnos();
        }
    };
    paginationControls.appendChild(nextBtn);
}

/**
 * Maneja la búsqueda de alumnos
 */
function handleSearchAlumnos() {
    const searchInput = document.getElementById("alumnoSearch");
    if (searchInput) {
        searchInput.addEventListener("input", (e) => {
            currentSearch = e.target.value;
            currentPage = 1;
            loadAlumnos();
        });
    }
}

/**
 * Maneja el filtro de activos/inactivos
 */
function handleFilterActivo() {
    const activoFilter = document.getElementById("alumnoActivoFilter");
    if (activoFilter) {
        activoFilter.addEventListener("change", (e) => {
            const value = e.target.value;
            currentActivo = value === "all" ? null : value === "true";
            currentPage = 1;
            loadAlumnos();
        });
    }
}

/**
 * Limpia el formulario de alumno
 */
function clearAlumnoForm() {
    document.getElementById("alumnoId").value = "";
    document.getElementById("nombre").value = "Juan";
    document.getElementById("apellidos").value = "Perez";
    document.getElementById("telefono").value = "600123456";
    document.getElementById("email").value = "juan.perez@test.es";
    document.getElementById("contrasenia").value = "pass123";
    document.getElementById("fechaNacimiento").value = "1990-01-01";
    document.getElementById("localidad").value = "Madrid";
    document.getElementById("provincia").value = "Madrid";
    document.getElementById("activo").checked = true;
    setAlumnoFormMode('create');
}

/**
 * Establece el modo del formulario (create/update)
 */
function setAlumnoFormMode(mode) {
    const formCard = document.getElementById("alumnoFormCard");
    const idField = document.getElementById("alumnoId");
    
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
 * Carga los datos de un alumno en el formulario
 */
function loadAlumnoIntoForm(alumno) {
    if (!alumno) return;
    
    document.getElementById("alumnoId").value = alumno.id_alumno || "";
    document.getElementById("nombre").value = alumno.nombre || "";
    document.getElementById("apellidos").value = alumno.apellidos || "";
    document.getElementById("telefono").value = alumno.telefono || "";
    document.getElementById("email").value = alumno.email || "";
    document.getElementById("fechaNacimiento").value = alumno.fecha_nacimiento || "1990-01-01";
    document.getElementById("localidad").value = alumno.localidad || "";
    document.getElementById("provincia").value = alumno.provincia || "";
    document.getElementById("activo").checked = alumno.activo !== false;
    
    setAlumnoFormMode('update');
}

/**
 * Crea un nuevo alumno
 */
async function createAlumno() {
    try {
        const input = getAlumnoInput();
        const query = `mutation ($input: AlumnoCreateInput!) {
            createAlumno(input: $input) {
                id_alumno nombre email
            }
        }`;
        const json = await fetchGraphQL(query, { input });
        
        if (json.errors) {
            document.getElementById("alumnoResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        document.getElementById("alumnoResult").textContent = JSON.stringify(json.data, null, 2);
        
        // Limpiar formulario si la creación fue exitosa
        if (json.data && json.data.createAlumno) {
            clearAlumnoForm();
        }
        
        currentPage = 1;
        await loadAlumnos();
    } catch (error) {
        document.getElementById("alumnoResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Actualiza un alumno existente
 */
async function updateAlumno() {
    const id = document.getElementById("alumnoId").value;
    if (!id) {
        document.getElementById("alumnoResult").textContent = "ID de Alumno es obligatorio para actualizar.";
        return;
    }
    try {
        const input = getAlumnoInput();
        const query = `mutation ($id: ID!, $input: AlumnoUpdateInput!) {
            updateAlumno(id: $id, input: $input) {
                id_alumno nombre email
            }
        }`;
        const json = await fetchGraphQL(query, { id, input });
        document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
        await loadAlumnos();
    } catch (error) {
        document.getElementById("alumnoResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Obtiene un alumno por ID
 */
async function getAlumnoById() {
    const idSearch = document.getElementById("alumnoIdSearch");
    const id = idSearch ? idSearch.value : document.getElementById("alumnoId").value;
    
    if (!id) {
        document.getElementById("alumnoResult").textContent = "ID de Alumno es obligatorio para buscar.";
        return;
    }
    try {
        const query = `query ($id: ID!) {
            alumnoById(id: $id) {
                id_alumno nombre apellidos email telefono fecha_nacimiento localidad provincia activo
            }
        }`;
        const json = await fetchGraphQL(query, { id });
        
        if (json.errors) {
            document.getElementById("alumnoResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        const alumno = json.data?.alumnoById;
        if (alumno) {
            // Cargar datos en el formulario
            loadAlumnoIntoForm(alumno);
            document.getElementById("alumnoResult").textContent = JSON.stringify(json.data, null, 2);
        } else {
            document.getElementById("alumnoResult").textContent = "Alumno no encontrado.";
        }
    } catch (error) {
        document.getElementById("alumnoResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Elimina un alumno
 */
async function deleteAlumno() {
    const idSearch = document.getElementById("alumnoIdSearch");
    const id = idSearch ? idSearch.value : document.getElementById("alumnoId").value;
    
    if (!id) {
        document.getElementById("alumnoResult").textContent = "ID de Alumno es obligatorio para eliminar.";
        return;
    }
    try {
        const query = `mutation ($id: ID!) {
            deleteAlumno(id: $id)
        }`;
        const json = await fetchGraphQL(query, { id });
        
        if (json.errors) {
            document.getElementById("alumnoResult").textContent = JSON.stringify(json.errors, null, 2);
            return;
        }
        
        const result = json.data?.deleteAlumno;
        document.getElementById("alumnoResult").textContent = result 
            ? "Alumno eliminado con éxito." 
            : JSON.stringify(json.data, null, 2);
        
        // Limpiar campos después de eliminar
        if (idSearch) idSearch.value = "";
        if (result) {
            clearAlumnoForm();
        }
        
        await loadAlumnos();
    } catch (error) {
        document.getElementById("alumnoResult").textContent = `Error: ${error.message}`;
    }
}

