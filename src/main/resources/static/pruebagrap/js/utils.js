/**
 * Utilidades globales para la aplicación
 */

/**
 * Cambia entre pestañas
 */
function openTab(evt, tabName) {
    const tabcontent = document.getElementsByClassName("tab-content");
    for (let i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    const tablinks = document.getElementsByClassName("tab-button");
    for (let i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "grid";
    evt.currentTarget.className += " active";
}

/**
 * Realiza una petición GraphQL
 */
async function fetchGraphQL(query, variables = {}) {
    const res = await fetch("/graphql", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ query, variables })
    });
    return res.json();
}

/**
 * Realiza una petición REST
 */
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
    return res.status !== 204 ? res.json() : {};
}

/**
 * Carga una plantilla en un contenedor
 */
function loadTemplate(templateId, containerId) {
    const template = document.getElementById(templateId);
    const container = document.getElementById(containerId);
    if (template && container) {
        const clone = template.content.cloneNode(true);
        container.appendChild(clone);
    }
}

