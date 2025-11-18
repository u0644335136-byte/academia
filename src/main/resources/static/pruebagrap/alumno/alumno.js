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
        document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
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
    const id = document.getElementById("alumnoId").value;
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
        document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    } catch (error) {
        document.getElementById("alumnoResult").textContent = `Error: ${error.message}`;
    }
}

/**
 * Elimina un alumno
 */
async function deleteAlumno() {
    const id = document.getElementById("alumnoId").value;
    if (!id) {
        document.getElementById("alumnoResult").textContent = "ID de Alumno es obligatorio para eliminar.";
        return;
    }
    try {
        const query = `mutation ($id: ID!) {
            deleteAlumno(id: $id)
        }`;
        const json = await fetchGraphQL(query, { id });
        document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
        await loadAlumnos();
    } catch (error) {
        document.getElementById("alumnoResult").textContent = `Error: ${error.message}`;
    }
}

