// La función fetchGraphQL está definida en js/utils.js y será utilizada aquí.

/**
 * Consulta todos los profesores y rellena el select 'convocatoriaIdProfesor'.
 */
async function loadProfesoresForSelect() {
    // 1. Definir la Query GraphQL
    const query = `
        query {
            profesores {
                idProfesor
                nombre
                apellidos
            }
        }
    `;

    try {
        // 2. Ejecutar la Query
        const result = await fetchGraphQL(query);
        const profesores = result.data.profesores || [];
        
        // 3. Obtener el elemento SELECT del DOM
        const selectElement = document.getElementById('convocatoriaIdProfesor');
        
        // Manejo de errores de conexión/DOM (en caso de que el elemento aún no exista)
        if (!selectElement) {
             console.warn("Elemento 'convocatoriaIdProfesor' no encontrado. La pestaña de Convocatoria aún no está cargada.");
             return; 
        }

        // Limpiar opciones existentes
        selectElement.innerHTML = ''; 
        
        if (profesores.length === 0) {
            selectElement.innerHTML = '<option value="" disabled>No hay profesores disponibles</option>';
            selectElement.disabled = true;
        } else {
            selectElement.disabled = false;
            
            // Opción por defecto
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Selecciona un Profesor';
            defaultOption.disabled = true;
            defaultOption.selected = true; 
            selectElement.appendChild(defaultOption);

            // Agregar opciones de profesores
            profesores.forEach(profesor => {
                const option = document.createElement('option');
                option.value = profesor.idProfesor; // El valor es el ID
                option.textContent = `[${profesor.idProfesor}] ${profesor.nombre} ${profesor.apellidos || ''}`; // Nombre visible
                selectElement.appendChild(option);
            });
        }

        console.log(`Profesores cargados: ${profesores.length}`);

    } catch (error) {
        console.error("Error al cargar los profesores:", error);
        const selectElement = document.getElementById('convocatoriaIdProfesor');
        if (selectElement) {
            selectElement.innerHTML = '<option value="" disabled>Error al cargar los profesores</option>';
            selectElement.disabled = true;
        }
    }
}