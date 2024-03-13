document.addEventListener( "DOMContentLoaded", function () {
    fetch( "/api/category/all" )
        .then( response => response.json() )
        .then( categories => {
            const checkboxesContainer = document.getElementById( "categoryCheckboxes" );
            categories.forEach( category => {
                const checkboxContainer = document.createElement( "div" );
                checkboxContainer.classList.add( "checkbox-container" );

                const checkbox = document.createElement( "input" );
                checkbox.type = "checkbox";
                checkbox.name = "selectedCategories";
                checkbox.value = category.id;
                checkbox.id = `category_${category.id}`;
                checkbox.classList.add( "checkbox-input" );

                const label = document.createElement( "label" );
                label.htmlFor = `category_${category.id}`;
                label.classList.add( "checkbox-label" );
                label.appendChild( document.createTextNode( category.name ) );

                checkboxContainer.appendChild( checkbox );
                checkboxContainer.appendChild( label );
                checkboxesContainer.appendChild( checkboxContainer );
            });
        })
        .catch( error => console.error( "Error fetching categories:", error ) );

    const checkboxesContainer = document.getElementById( "categoryCheckboxes" );
    const categoriesLabel = document.querySelector( 'label[for="categoryCheckboxes"]' );
    const arrowIcon = document.createElement( "span" );
    arrowIcon.classList.add( "arrow-icon" );
    categoriesLabel.appendChild( arrowIcon );

    categoriesLabel.addEventListener( "click", function () {
        checkboxesContainer.classList.toggle( "hidden" );
        arrowIcon.classList.toggle( "rotate" );
    });

    checkboxesContainer.addEventListener( "change", function () {
        const selectedCheckboxValues = Array.from( document.querySelectorAll('input[name="selectedCategories"]:checked' ) )
            .map( checkbox => checkbox.value );
        document.getElementById( "selectedCategoryIds" ).value = selectedCheckboxValues.join( "," );
    });

    const addButton = document.querySelector( '.administration-button-accept' );
    addButton.addEventListener( 'click', function ( event ) {
        const selectedCheckboxValues = Array.from( document.querySelectorAll('input[name="selectedCategories"]:checked') )
            .map( checkbox => checkbox.value );

        if ( selectedCheckboxValues.length === 0 ) {
            alert("Proszę wybrać przynajmniej jedną kategorię przed dodaniem restauracji.");
            event.preventDefault();
        }
    });
});
