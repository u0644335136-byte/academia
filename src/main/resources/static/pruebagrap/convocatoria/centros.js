// La función fetchGraphQL está definida en js/utils.js y será utilizada aquí.

/**
 * Consulta todos los centros y rellena el select 'convocatoriaIdCentro'.
 */
async function loadCentrosForSelect() {
    // 1. Definir la Query GraphQL
    const query = `
        query {
            getAllCentros {
                id_centro
                nombre
                codigo_centro
            }
        }
    `;

    try {
        // 2. Ejecutar la Query
        const result = await fetchGraphQL(query);
        const centros = result.data.getAllCentros || [];
        
        // 3. Obtener el elemento SELECT del DOM
        const selectElement = document.getElementById('convocatoriaIdCentro');
        
        // Manejo de errores de conexión/DOM
        if (!selectElement) {
             console.warn("Elemento 'convocatoriaIdCentro' no encontrado. La pestaña de Convocatoria aún no está cargada.");
             return; 
        }

        // Limpiar opciones existentes
        selectElement.innerHTML = ''; 
        
        if (centros.length === 0) {
            selectElement.innerHTML = '<option value="" disabled>No hay centros disponibles</option>';
            selectElement.disabled = true;
        } else {
            selectElement.disabled = false;
            
            // Opción por defecto
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Selecciona un Centro';
            defaultOption.disabled = true;
            defaultOption.selected = true; 
            selectElement.appendChild(defaultOption);

            // Agregar opciones de centros
            centros.forEach(centro => {
                const option = document.createElement('option');
                option.value = centro.id_centro; // El valor es el ID
                option.textContent = `[${centro.id_centro}] ${centro.nombre} (${centro.codigo_centro})`; // Nombre visible
                selectElement.appendChild(option);
            });
        }

        console.log(`Centros cargados: ${centros.length}`);

    } catch (error) {
        console.error("Error al cargar los centros:", error);
        const selectElement = document.getElementById('convocatoriaIdCentro');
        if (selectElement) {
            selectElement.innerHTML = '<option value="" disabled>Error al cargar los centros</option>';
            selectElement.disabled = true;
        }
    }
}