// js/app.js
/**
 * Archivo principal de la aplicación.
 * Gestiona la carga inicial de los módulos HTML y de los datos.
 */

document.addEventListener('DOMContentLoaded', async () => {
    
    // 1. Carga el contenido HTML de cada módulo usando fetch.
    // Esto inyecta el HTML de alumno/alumno.html en <div id="Alumno">, etc.
    const loadingPromises = [
        loadHtmlContent('alumno/alumno.html', 'Alumno'),
        loadHtmlContent('matricula/matricula.html', 'Matricula'),
        loadHtmlContent('convocatoria/convocatoria.html', 'Convocatoria'),
        loadHtmlContent('convocatoria-detalle/convocatoria-detalle.html', 'ConvocatoriaDetalle'),
    ];

    // Espera a que todo el HTML se haya cargado antes de intentar cargar los datos.
    await Promise.all(loadingPromises);
    
    console.log("Módulos HTML cargados en el DOM.");

    // 2. Cargar datos iniciales y configurar eventos después de que el HTML está en el DOM
    // Importante: Debemos asegurarnos de que el HTML de cada módulo (donde están los IDs 
    // como 'alumnoSearch', 'alumnoActivoFilter', etc.) esté disponible antes de llamar
    // a estas funciones de inicialización.
    try {
        // Carga de datos para la pestaña inicialmente activa (Alumno)
        await loadAlumnos();
        
        // Inicialización de eventos para el módulo Alumno
        handleSearchAlumnos();
        handleFilterActivo();
        
        // Carga de datos para los otros módulos (si es necesario, aunque loadAlumnos es suficiente al inicio)
        loadMatriculas();
        loadConvocatorias();
        
        // Inicializar dropdowns del formulario de matrícula
        if (typeof initMatriculaDropdowns === 'function') {
            initMatriculaDropdowns();
        }
        
        // Inicializar el módulo de detalles de convocatoria
        if (typeof initConvocatoriaDetalle === 'function') {
            initConvocatoriaDetalle();
        }
        
    } catch(e) {
        console.error("Error durante la carga inicial de datos/eventos:", e);
    }
});