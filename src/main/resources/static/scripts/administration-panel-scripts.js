document.addEventListener('DOMContentLoaded', function ()
{
    let configContainer = document.querySelector( '.config-container' );
    let notificationContainer = document.querySelector( '.notyfication-container' );

    function hideConfigContainer()
    {
        configContainer.style.display = 'none';
    }

    function showConfigContainer()
    {
        configContainer.style.display = 'block';
    }

    function resetNotification()
    {
        notificationContainer.style.display = 'none';
        document.querySelector( '.notyfication-bar' ).innerText = '';
    }

    function loadConfigurationPage( url, containerSelector )
    {
        fetch( url )
            .then( response => response.text() )
            .then( data =>
            {
                document.querySelector( containerSelector ).innerHTML = data;

                let notificationBar = document.querySelector( '.notyfication-bar' );
                if ( notificationBar )
                {
                    let notificationText = notificationBar.innerText.trim();
                    if ( notificationText !== '' )
                    {
                        notificationContainer.style.display = 'block';
                    }
                    else
                    {
                        notificationContainer.style.display = 'none';
                    }
                }

                showConfigContainer();
            } )
            .catch( error => console.error( 'Error with getting data!', error ) );
    }

    hideConfigContainer();

    document.getElementById( 'add-category-link' ).addEventListener( 'click', function () {
        loadConfigurationPage( '/admin/add-category', '#config-container' );
        resetNotification();
    });

    document.getElementById( 'add-restaurant-link' ).addEventListener( 'click', function () {
        loadConfigurationPage( '/admin/add-restaurant', '#config-container' );
        resetNotification();
    });
});
