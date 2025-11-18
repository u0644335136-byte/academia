// Variable global para almacenar el endpoint de la API GraphQL
const API_URL = '/graphql'; 

/**
 * Realiza una consulta GraphQL.
 * @param {string} query - La consulta GraphQL.
 * @param {Object} variables - Variables para la consulta.
 * @returns {Promise<Object>} - La respuesta de la API.
 */
async function graphqlQuery(query, variables = {}) {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body: JSON.stringify({
            query,
            variables
        }),
    });

    const result = await response.json();
    if (result.errors) {
        console.error("GraphQL Errors:", result.errors);
        throw new Error("Error en la consulta GraphQL.");
    }
    return result.data;
}

/**
 * Consulta todos los cursos y rellena el select en el formulario de Convocatoria.
 */
async function loadCursosForSelect() {
    const query = `
        query {
            allCursos {
                idCurso
                nombre
            }
        }
    `;

    try {
        const data = await graphqlQuery(query);
        const cursos = data.allCursos || [];
        const selectElement = document.getElementById('convocatoriaIdCurso');
        
        // Limpiar opciones existentes (excepto quizás una opción por defecto si existiera)
        selectElement.innerHTML = ''; 
        
        if (cursos.length === 0) {
            selectElement.innerHTML = '<option value="" disabled>No hay cursos disponibles</option>';
            selectElement.disabled = true;
        } else {
            selectElement.disabled = false;
            
            // Opción por defecto
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Selecciona un Curso';
            defaultOption.disabled = true;
            defaultOption.selected = true; // Establecer como seleccionado inicialmente
            selectElement.appendChild(defaultOption);

            // Agregar opciones de cursos
            cursos.forEach(curso => {
                const option = document.createElement('option');
                option.value = curso.idCurso; // El valor es el ID
                option.textContent = `[${curso.idCurso}] ${curso.nombre}`; // Texto visible
                selectElement.appendChild(option);
            });
        }

        console.log(`Cursos cargados: ${cursos.length}`);

    } catch (error) {
        console.error("Error al cargar los cursos:", error);
        const selectElement = document.getElementById('convocatoriaIdCurso');
        selectElement.innerHTML = '<option value="" disabled>Error al cargar los cursos</option>';
        selectElement.disabled = true;
    }
}

