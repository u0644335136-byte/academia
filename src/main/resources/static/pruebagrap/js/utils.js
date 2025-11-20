// js/utils.js

const GRAPHQL_ENDPOINT = "/graphql";
const REST_ENDPOINT = "http://httpbin.org/post";

/**
 * Realiza una petición GraphQL POST.
 */
async function fetchGraphQL(query, variables = {}) {
    try {
        const response = await fetch(GRAPHQL_ENDPOINT, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ query, variables }),
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error("Error en fetchGraphQL:", error);
        return { errors: [{ message: `Error al conectar con GraphQL: ${error.message}` }] };
    }
}

/**
 * Realiza una petición REST simple (simulando creación/actualización).
 */
async function fetchRest(endpoint, method, data = {}) {
    try {
        const response = await fetch(endpoint, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        // httpbin.org siempre responde 200/201 a POST/PUT
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error("Error en fetchRest:", error);
        return { error: `Error al conectar con REST: ${error.message}` };
    }
}


// --- Lógica de Pestañas y Carga Dinámica ---

/**
 * Carga contenido HTML desde un archivo en un contenedor.
 * Esto es lo que reemplaza la necesidad de tener todo el HTML en index.html.
 * @param {string} filePath Ruta del archivo HTML a cargar (ej: 'alumno/alumno.html').
 * @param {string} containerId ID del contenedor donde se insertará el contenido (ej: 'Alumno').
 * @returns {Promise<void>}
 */
async function loadHtmlContent(filePath, containerId) {
    const container = document.getElementById(containerId);
    if (!container) return;
    
    // Si el contenedor ya tiene contenido, no lo recargamos (optimización simple)
    if (container.children.length > 0 && container.getAttribute('data-loaded')) {
        return;
    }

    try {
        const response = await fetch(filePath);
        if (!response.ok) {
            throw new Error(`Error al cargar el archivo ${filePath}: ${response.statusText}`);
        }
        const html = await response.text();
        container.innerHTML = html;
        container.setAttribute('data-loaded', 'true');
    } catch (error) {
        console.error(`Error loading content for ${containerId}:`, error);
        container.innerHTML = `<small style="color:red;">Error: No se pudo cargar la vista de ${containerId} desde ${filePath}.</small>`;
    }
}

/**
 * Muestra la pestaña seleccionada y oculta las demás.
 * @param {Event} evt Evento del botón de la pestaña.
 * @param {string} tabName ID del contenedor de la pestaña a mostrar.
 */
function openTab(evt, tabName) {
    // Oculta todos los contenidos de las pestañas
    document.querySelectorAll(".tab-content").forEach(tab => {
        tab.classList.remove("active");
    });

    // Desactiva todos los botones de las pestañas
    document.querySelectorAll(".tab-button").forEach(btn => {
        btn.classList.remove("active");
    });

    // Muestra el contenido de la pestaña actual
    const targetTab = document.getElementById(tabName);
    if (targetTab) {
        targetTab.classList.add("active");
        
        // Carga dinámica de datos si ya se cargó el HTML
        if (tabName === 'Alumno' && targetTab.getAttribute('data-loaded')) {
            loadAlumnos();
        } else if (tabName === 'Matricula' && targetTab.getAttribute('data-loaded')) {
            loadAlumnosDropdown();
            loadConvocatoriasDropdown();
            
        } else if (tabName === 'Convocatoria' && targetTab.getAttribute('data-loaded')) {
            loadConvocatorias();
            if (typeof loadCursosForSelect === 'function') {
                loadCursosForSelect();
            }
            if (typeof loadProfesoresForSelect === 'function') {
                loadProfesoresForSelect();
            }
            if (typeof loadCentrosForSelect === 'function') {
                loadCentrosForSelect();
            }
        }
    }
    

    // Activa el botón que fue clickeado
    if (evt && evt.currentTarget) {
        evt.currentTarget.classList.add("active");
    }
}