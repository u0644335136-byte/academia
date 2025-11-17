document.addEventListener('DOMContentLoaded', () => {
    // Cargar las plantillas de formularios
    loadTemplate('alumno-form-template', 'alumno-view');
    loadTemplate('matricula-form-template', 'matricula-view');
    loadTemplate('convocatoria-form-template', 'convocatoria-view');

    // Cargar datos iniciales
    loadAlumnos();
    loadMatriculas();
    loadConvocatorias();
});

function loadTemplate(templateId, containerId) {
    const template = document.getElementById(templateId);
    const container = document.getElementById(containerId);
    if (template && container) {
        // Clonar el contenido del template e insertarlo
        const clone = template.content.cloneNode(true);
        container.appendChild(clone);
    }
}

// --- UTILIDADES GLOBALES ---

function openTab(evt, tabName) {
    // Lógica para cambiar de pestaña (se mantiene igual)
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tab-button");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "grid";
    evt.currentTarget.className += " active";
}

async function fetchGraphQL(query, variables = {}) {
    const res = await fetch("/graphql", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ query, variables })
    });
    return res.json();
}

async function fetchRest(path, method = "GET", body = null) {
    const options = {
        method,
        headers: { "Content-Type": "application/json" }
    };
    if (body) {
        options.body = JSON.stringify(body);
    }
    const res = await fetch(path, options);
    if (method === 'DELETE') {
        return { status: res.status, ok: res.ok };
    }
    if (!res.ok) {
         const error = await res.text();
         throw new Error(`Error ${res.status}: ${error}`);
    }
    // Manejar el caso de respuesta vacía (204 No Content)
    return res.status !== 204 ? res.json() : {};
}


// --- FUNCIONES ALUMNO (GraphQL) ---

function getAlumnoInput() {
     return {
        nombre: document.getElementById("nombre").value,
        apellidos: document.getElementById("apellidos").value,
        telefono: document.getElementById("telefono").value,
        email: document.getElementById("email").value,
        contrasenia: document.getElementById("contrasenia").value,
        // GraphQL espera String! en AlumnoInput
        fecha_nacimiento: document.getElementById("fechaNacimiento").value,
        localidad: document.getElementById("localidad").value,
        provincia: document.getElementById("provincia").value,
        activo: document.getElementById("activo").checked
     };
}

async function loadAlumnos() {
    const query = `query {
        allAlumnos {
            id_alumno nombre apellidos email activo
        }
    }`;
    // Lógica de visualización omitida por brevedad, se mantiene la misma
    const json = await fetchGraphQL(query);
    const lista = document.getElementById("alumnoList");
    const count = document.getElementById("alumnoCount");

    lista.innerHTML = "";
    const alumnos = json.data && json.data.allAlumnos ? json.data.allAlumnos : [];
    count.textContent = alumnos.length;

    if (alumnos.length === 0) {
        lista.innerHTML = '<small style="color:var(--muted)">No hay alumnos activos...</small>';
    }

    alumnos.forEach(a => {
        const item = document.createElement("div");
        item.className = "list-item";
        item.innerHTML = `
            <div>
                <strong>ID: ${a.id_alumno} - ${a.nombre} ${a.apellidos}</strong><br>
                <small style="color:#738496">${a.email}</small>
            </div>
            <span class="pill">${a.activo ? "Activo" : "Inactivo"}</span>`;
        lista.appendChild(item);
    });
}

async function createAlumno() {
    const input = getAlumnoInput();
    const query = `mutation ($input: AlumnoInput!) {
        createAlumno(input: $input) {
            id_alumno nombre email
        }
    }`;
    const json = await fetchGraphQL(query, { input });
    document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    await loadAlumnos();
}

async function updateAlumno() {
    const id = document.getElementById("alumnoId").value;
    if (!id) {
        document.getElementById("alumnoResult").textContent = "ID de Alumno es obligatorio para actualizar.";
        return;
    }
    const input = getAlumnoInput();
    const query = `mutation ($id: ID!, $input: AlumnoInput!) {
        updateAlumno(id: $id, input: $input) {
            id_alumno nombre email
        }
    }`;
    // 💡 AÑADE ESTO para ver las variables enviadas
    console.log("Variables enviadas a GraphQL:", { id, input });
    const json = await fetchGraphQL(query, { id, input });
    document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    await loadAlumnos();
}

async function getAlumnoById() {
    const id = document.getElementById("alumnoId").value;
    if (!id) {
        document.getElementById("alumnoResult").textContent = "ID de Alumno es obligatorio para buscar.";
        return;
    }
    const query = `query ($id: ID!) {
        alumnoById(id: $id) {
            id_alumno nombre apellidos email telefono fecha_nacimiento localidad provincia activo
        }
    }`;
    const json = await fetchGraphQL(query, { id });
    document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
}

async function deleteAlumno() {
    const id = document.getElementById("alumnoId").value;
    if (!id) {
        document.getElementById("alumnoResult").textContent = "ID de Alumno es obligatorio para eliminar.";
        return;
    }
    const query = `mutation ($id: ID!) {
        deleteAlumno(id: $id)
    }`;
    const json = await fetchGraphQL(query, { id });
    document.getElementById("alumnoResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    await loadAlumnos();
}

// --- FUNCIONES MATRÍCULA (REST) ---

function getMatriculaInput() {
    return {
        fecha: document.getElementById("matriculaFecha").value,
        codigo: document.getElementById("matriculaCodigo").value,
        precio: parseInt(document.getElementById("matriculaPrecio").value),
        idConvocatoria: parseInt(document.getElementById("matriculaIdConvocatoria").value),
        idAlumno: parseInt(document.getElementById("matriculaIdAlumno").value)
    };
}

async function loadMatriculas() {
    try {
        const matriculas = await fetchRest("/api/matriculas");
        const lista = document.getElementById("matriculaList");
        const count = document.getElementById("matriculaCount");

        lista.innerHTML = "";
        count.textContent = matriculas.length;

        // ... (Lógica de visualización omitida por brevedad)
        if (matriculas.length === 0) {
            lista.innerHTML = '<small style="color:var(--muted)">No hay matrículas activas...</small>';
        }

        matriculas.forEach(m => {
            const item = document.createElement("div");
            item.className = "list-item";
            item.innerHTML = `
                <div>
                    <strong>ID: ${m.id} - ${m.codigo} (${m.precio}€)</strong><br>
                    <small style="color:#738496">Alumno: ${m.alumnoEmail} | Conv: ${m.convocatoriaCodigo}</small>
                </div>
                <span class="pill">${m.fecha}</span>`;
            lista.appendChild(item);
        });

    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al cargar matrículas: " + error.message;
    }
}

async function createMatricula() {
    try {
        const input = getMatriculaInput();
        const result = await fetchRest("/api/matriculas", "POST", input);
        document.getElementById("matriculaResult").textContent = JSON.stringify(result, null, 2);
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al crear matrícula: " + error.message;
    }
}

async function updateMatricula() {
    const id = document.getElementById("matriculaId").value;
    if (!id) {
        document.getElementById("matriculaResult").textContent = "ID de Matrícula es obligatorio para actualizar.";
        return;
    }
    try {
        const input = getMatriculaInput();
        const result = await fetchRest(`/api/matriculas/${id}`, "PUT", input);
        document.getElementById("matriculaResult").textContent = JSON.stringify(result, null, 2);
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al actualizar matrícula: " + error.message;
    }
}

async function getMatriculaById() {
    const id = document.getElementById("matriculaId").value;
    if (!id) {
        document.getElementById("matriculaResult").textContent = "ID de Matrícula es obligatorio para buscar.";
        return;
    }
    try {
        const result = await fetchRest(`/api/matriculas/${id}`);
        document.getElementById("matriculaResult").textContent = JSON.stringify(result, null, 2);
    } catch (error) {
         document.getElementById("matriculaResult").textContent = "Error al buscar matrícula: " + error.message;
    }
}

async function deleteMatricula() {
    const id = document.getElementById("matriculaId").value;
    if (!id) {
        document.getElementById("matriculaResult").textContent = "ID de Matrícula es obligatorio para eliminar.";
        return;
    }
    try {
        const result = await fetchRest(`/api/matriculas/${id}`, "DELETE");
        document.getElementById("matriculaResult").textContent = result.ok ? "Matrícula eliminada (soft delete) con éxito." : "Error al eliminar matrícula.";
        await loadMatriculas();
    } catch (error) {
        document.getElementById("matriculaResult").textContent = "Error al eliminar matrícula: " + error.message;
    }
}

// --- FUNCIONES CONVOCATORIA (GraphQL) ---

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

async function loadConvocatorias() {
    const query = `query {
        retornarTodasConvocatorias {
            id codigo fechaInicio fechaFin centroNombre profesorEmail
        }
    }`;
    // Lógica de visualización omitida por brevedad, se mantiene la misma
    const json = await fetchGraphQL(query);
    const lista = document.getElementById("convocatoriaList");
    const count = document.getElementById("convocatoriaCount");

    lista.innerHTML = "";
    const convocatorias = json.data && json.data.retornarTodasConvocatorias ? json.data.retornarTodasConvocatorias : [];
    count.textContent = convocatorias.length;

    if (convocatorias.length === 0) {
        lista.innerHTML = '<small style="color:var(--muted)">No hay convocatorias activas...</small>';
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
}

async function createConvocatoria() {
    const input = getConvocatoriaInput();
    const query = `mutation ($input: ConvocatoriaInput!) {
        crearConvocatoria(input: $input) {
            id codigo fechaInicio
        }
    }`;
    const json = await fetchGraphQL(query, { input });
    document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    await loadConvocatorias();
}

async function updateConvocatoria() {
    const id = document.getElementById("convocatoriaId").value;
    if (!id) {
        document.getElementById("convocatoriaResult").textContent = "ID de Convocatoria es obligatorio para actualizar.";
        return;
    }
    const input = getConvocatoriaInput();
    const query = `mutation ($id: ID!, $input: ConvocatoriaInput!) {
        actualizarConvocatoria(id: $id, input: $input) {
            id codigo fechaInicio
        }
    }`;
    const json = await fetchGraphQL(query, { id, input });
    document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    await loadConvocatorias();
}

async function getConvocatoriaById() {
    const id = document.getElementById("convocatoriaId").value;
    if (!id) {
        document.getElementById("convocatoriaResult").textContent = "ID de Convocatoria es obligatorio para buscar.";
        return;
    }
    const query = `query ($id: ID!) {
        convocatoriaPorId(id: $id) {
            id codigo fechaInicio fechaFin idCurso cursoNombre idProfesor profesorEmail idCentro centroNombre
        }
    }`;
    const json = await fetchGraphQL(query, { id });
    document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
}

async function deleteConvocatoria() {
    const id = document.getElementById("convocatoriaId").value;
    if (!id) {
        document.getElementById("convocatoriaResult").textContent = "ID de Convocatoria es obligatorio para eliminar.";
        return;
    }
    const query = `mutation ($id: ID!) {
        eliminarConvocatoria(id: $id)
    }`;
    const json = await fetchGraphQL(query, { id });
    document.getElementById("convocatoriaResult").textContent = JSON.stringify(json.data || json.errors, null, 2);
    await loadConvocatorias();
}