Link al Repositorio: https://github.com/Oyupa/Libreria_PDE/tree/master
# Librería de Novelas

## Descripción de la Aplicación

Esta aplicación es una biblioteca para gestionar novelas. Permite a los usuarios agregar nuevas novelas, eliminar novelas existentes, ver detalles de cada novela, marcarlas como favoritas y añadir reseñas. La interfaz es sencilla e intuitiva, diseñada para mejorar la experiencia del usuario al gestionar sus novelas.

## Cómo Funciona la Aplicación

1. **Pantalla Principal:** Muestra una lista de novelas en un RecyclerView.
2. **Agregar Novela:** Un botón en la parte inferior de la pantalla permite a los usuarios agregar nuevas novelas. Se solicita información como el título, autor, año y sinopsis.
3. **Detalles de Novela:** Al seleccionar una novela de la lista, se muestra una vista detallada con toda la información relevante.
4. **Favoritos:** Los usuarios pueden marcar novelas como favoritas y verlas en una sección designada.
5. **Reseñas:** Se pueden agregar reseñas para cada novela, proporcionando una mejor interacción con el contenido.

## Pasos Seguidos para Crear la Aplicación

1. **Planificación:** Definí los requisitos y funcionalidades deseadas para la aplicación.
2. **Configuración del Proyecto:**
    - Creé un nuevo proyecto en Android Studio utilizando Kotlin como lenguaje de programación.
    - Configuré las dependencias necesarias, incluyendo la biblioteca RecyclerView.
3. **Diseño de la Interfaz:**
    - Diseñé el archivo XML para la pantalla principal (`main_activity.xml`), que incluye un RecyclerView y un botón para agregar nuevas novelas.
    - Diseñé la vista de detalles de la novela (`novel_item.xml`) para mostrar la información de cada novela.
4. **Implementación de Funcionalidades:**
    - Implementé la clase `Novel` para representar la estructura de datos de las novelas.
    - Creé una clase `NovelAdapter` para gestionar la visualización de novelas en el RecyclerView.
    - Implementé métodos para agregar, eliminar y marcar novelas como favoritas, así como para gestionar las reseñas.
5. **Persistencia de Datos:**
    - Utilicé SharedPreferences para guardar las novelas y su estado (favoritas, reseñas) de manera persistente.
6. **Manejo de Errores:**
    - Añadí validaciones para asegurar que los datos introducidos por el usuario sean correctos y manejé posibles excepciones durante la ejecución.

## Funcionalidades Implementadas

- **Agregar Novelas:** Permite a los usuarios ingresar datos de una nueva novela.
- **Eliminar Novelas:** Posibilidad de eliminar novelas de la lista.
- **Ver Detalles:** Al hacer clic en una novela, se muestran sus detalles completos.
- **Marcar como Favorita:** Los usuarios pueden marcar novelas como favoritas.
- **Agregar Reseñas:** Posibilidad de agregar reseñas a las novelas.

## Problemas Encontrados

- **Errores de Inflación XML:** Tuve problemas iniciales al inflar los archivos de diseño, lo que se solucionó revisando el XML y asegurándome de que no hubiera errores de sintaxis.
- **Manejo de JSON:** Al cargar datos desde SharedPreferences, encontré excepciones de formato de número debido a entradas no válidas en el JSON. Esto se resolvió implementando validaciones antes de agregar datos.
- **Interfaz de Usuario:** Hubo desafíos al diseñar la interfaz de usuario, especialmente en la visualización de los detalles de las novelas. La solución consistió en ajustar los elementos del diseño para que se visualizaran correctamente.
