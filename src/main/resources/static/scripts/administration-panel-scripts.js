$( document ).ready( function ()
{
    let configContainer = $( "#config-container" );
    let notificationContainer = $( ".notyfication-container" );

    function checkAndHideConfigContainer()
    {
        let isConfigContainerEmpty = configContainer.html().trim() === "";
        if ( isConfigContainerEmpty )
        {
            configContainer.hide();
        }
    }

    function checkAndShowConfigContainer()
    {
        let isConfigContainerEmpty = configContainer.html().trim() === "";
        if ( !isConfigContainerEmpty )
        {
            configContainer.show();
        }
    }

    function resetNotification()
    {
        notificationContainer.hide();
        $( ".notyfication-bar" ).text( "" );
    }

    function loadConfigurationPage( url, containerSelector )
    {
        $.get( url, function ( data )
        {
            $( containerSelector ).html(data);


            let notificationBar = $( ".notyfication-bar");
            if ( notificationBar.length > 0 )
            {
                let notificationText = notificationBar.text();
                if ( notificationText.trim() !== "" )
                {
                    notificationContainer.show();
                }
                else
                {
                    notificationContainer.hide();
                }
            }

            checkAndShowConfigContainer();
        });
    }

    checkAndHideConfigContainer();

    $( "#add-category-link" ).click( function ()
    {
        loadConfigurationPage( "/admin/add-category", "#config-container" );
        resetNotification();
    });

    $("#add-restaurant-link").click( function ()
    {
        loadConfigurationPage("/admin/add-restaurant", "#config-container" );
        resetNotification();
    });
});
