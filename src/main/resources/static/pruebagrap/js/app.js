/**
 * Archivo principal de la aplicación
 * Inicializa la aplicación y carga los módulos necesarios
 */

document.addEventListener('DOMContentLoaded', () => {
    // Cargar las plantillas de formularios
    loadTemplate('alumno-form-template', 'alumno-view');
    loadTemplate('matricula-form-template', 'matricula-view');
    loadTemplate('convocatoria-form-template', 'convocatoria-view');

    // Cargar datos iniciales
    loadAlumnos();
    loadMatriculas();
    loadConvocatorias();

    // Configurar eventos de búsqueda y filtros
    setTimeout(() => {
        handleSearchAlumnos();
        handleFilterActivo();
    }, 100);
});

