document.addEventListener( "DOMContentLoaded", function ()
{
    const countrySelect = document.getElementById( "countryName" );

    fetch( "https://restcountries.com/v3.1/all" )
        .then( response => response.json() )
        .then( countries => {
            countries.sort( ( a, b ) => a.translations.pol.common.localeCompare( b.translations.pol.common ) );

            countries.forEach( country => {
                const option = document.createElement( "option" );
                option.value = country.translations.pol.common;
                option.text = country.translations.pol.common;
                countrySelect.appendChild( option );
            });
        })
        .catch( error => console.error( "Error getting countries:", error ) );
});
